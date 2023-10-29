<script setup>

</script>

<template>
  <div>
    <el-row>
      <el-col :span="24">
        <!--Table-->
        <el-table
          :data="orderList"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center'}"
          :default-sort="{prop: 'orderTime', order: 'descending'}"
          stripe
        >
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-card>
                <el-table
                  :data="props.row.orderInfos"
                  :header-cell-style="{'text-align':'center'}"
                  :cell-style="{'text-align':'center'}"
                  border
                  stripe
                >
                  <el-table-column type="index" label="No." min-width="3%"></el-table-column>
                  <el-table-column prop="name" label="Product Name" min-width="25%">
                    <template slot-scope="scope">
                      <el-link type="primary" @click="goToProductDetail(scope.row.name)">{{ scope.row.name }}</el-link>
                    </template>
                  </el-table-column>
                  <el-table-column prop="price" label="Product Price" min-width="15%">
                    <template slot-scope="scope">
                      {{ formattedPrice(scope.row.price) }}
                    </template>
                  </el-table-column>
                  <el-table-column prop="number" label="Product Quantity" min-width="15%"></el-table-column>
                  <el-table-column prop="size" label="Product Size" min-width="15%">
                    <template slot-scope="scope">
                      <el-tag v-if="scope.row.size === 'S'" effect="plain" type="success">Small</el-tag>
                      <el-tag v-if="scope.row.size === 'M'" effect="plain" type="warning">Medium</el-tag>
                      <el-tag v-if="scope.row.size === 'L'" effect="plain" type="danger">Large</el-tag>
                    </template>
                  </el-table-column>
                </el-table>
                <el-descriptions :column="2" border :label-style="labelStyle" :contentStyle="contentStyle" style="margin: 20px auto 0 auto; width: 50%">
                  <el-descriptions-item label="Total Product(s):">{{ props.row.orderInfos.length }}</el-descriptions-item>
                  <el-descriptions-item label="Total Price:">{{ formattedPrice(props.row.orderPrice) }}</el-descriptions-item>
                </el-descriptions>
              </el-card>
            </template>
          </el-table-column>
          <el-table-column type="index" label="No." min-width="3%"></el-table-column>
          <el-table-column prop="orderId" label="Order ID" min-width="10%"></el-table-column>
          <el-table-column prop="orderTime" label="Order Time" sortable min-width="15%"></el-table-column>
          <el-table-column prop="orderPrice" label="Order Price" min-width="10%">
            <template slot-scope="scope">
              {{ formattedPrice(scope.row.orderPrice) }}
            </template>
          </el-table-column>
          <el-table-column prop="orderAddress" label="Order Address" min-width="20%"></el-table-column>
          <el-table-column prop="orderComment" label="Order Comment" min-width="25%">
            <template slot-scope="scope">
              <span style="font-style: italic">{{ scope.row.orderComment }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="orderStatus" label="Order Status" sortable min-width="15%"
            :filters="[{ text: 'In Process', value: 'In Process' }, { text: 'In Delivery', value: 'In Delivery' }, { text: 'Done', value: 'Done' }]"
            :filter-method="filterTag">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.orderStatus === 'In Process'" type="success">In Process</el-tag>
              <el-tag v-if="scope.row.orderStatus === 'In Delivery'" type="warning">In Delivery</el-tag>
              <el-tag v-if="scope.row.orderStatus === 'Done'" type="danger">Done</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Edit" min-width="7%">
            <template slot-scope="scope">
              <!--Edit Btn-->
              <el-button
                type="primary"
                size="mini"
                icon="el-icon-edit"
                :disabled="scope.row.orderStatus !== 'In Process'"
                @click="showSubmitDialog(scope.row)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNo"
        :page-sizes="[1, 5, 10, 20]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-row>

    <!--Edit Order Dialog-->
    <el-dialog title="Edit Order" :visible.sync="submitDialogVisible" width="35%" :top="`5vh`">
      <!--内容主体区域-->
<!--      <el-descriptions :column="2" border :label-style="labelStyle" :contentStyle="contentStyle" style="margin: 0 10px 20px 10px">-->
<!--        <el-descriptions-item label="Total Product(s):">{{ multipleSelection.length }}</el-descriptions-item>-->
<!--        <el-descriptions-item label="Total Price:">{{ formattedPrice(totalPrice) }}</el-descriptions-item>-->
<!--      </el-descriptions>-->
      <el-form :model="editForm" :rules="submitOrderRules" ref="orderForm" label-width="130px">
        <el-form-item label="Delivery Address" prop="orderAddress">
          <el-input v-model="editForm.orderAddress" placeholder="Please enter your delivery address"></el-input>
        </el-form-item>
        <el-form-item label="Order Comment">
          <el-input v-model="editForm.orderComment" type="textarea" placeholder="Please enter your order comment"></el-input>
        </el-form-item>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="submitDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateOrder('orderForm')">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import homepage from "../homepage.vue";
import { getCustomerOrderList, getOrderDetail, updateOrder } from "@/api/order";
export default {
  data() {
    return {
      orderList: [],
      total: 0,
      queryInfo: {
        keyword: "",
        pageNo: 1,
        pageSize: 10,
      },
      submitDialogVisible: false,
      editForm: {
        orderAddress: "",
        orderComment: "",
      },
      submitOrderRules: {
        orderAddress : [
          { required: true, message: 'Order address cannot be empty!', trigger: 'change' }
        ],
      },
      labelStyle: {
        width: '30%',
        color: 'black',
        'font-weight': 'bold'
      },
      contentStyle: {
        width: '20%',
        color: 'red'
      },
    }
  },
  created() {
    this.getOrderList();
  },
  methods: {
    getOrderList() {
      this.queryInfo.keyword = this.$store.state.user.id;
      console.log(this.queryInfo);
      getCustomerOrderList(this.queryInfo)
        .then((res) => {
          if (res.data.code === 200) {
            // Get the order list
            console.log(res.data.data);
            this.orderList = res.data.data;
            this.total = res.data.totalRecords;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getSizeLabel(size) {
      if (size === 'S') {
        return 'Small';
      } else if (size === 'M') {
        return 'Medium';
      } else if (size === 'L') {
        return 'Large';
      } else {
        return size; // 如果需要处理其他情况
      }
    },
    // 监听 pageSize 改变的事件
    // Listen the event of changing pageSize
    handleSizeChange(newSize) {
      // console.log(newSize)
      this.queryInfo.pageSize = newSize;
      this.getOrderList();
    },
    // 监听 当前页码值 改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage)
      this.queryInfo.pageNo = newPage;
      this.getOrderList();
    },
    // Listen the event of clicking edit button
    showSubmitDialog(orderInfo) {
      this.submitDialogVisible = true;
      this.editForm = orderInfo;
    },
    updateOrder(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateOrder(this.editForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message({
                  showClose: true,
                  message: "Edit order successfully.",
                  type: "success",
                });
                this.submitDialogVisible = false;
                this.getOrderList();
                this.callGetCartNumber();
              } else {
                this.$message.error("Failed to submit order.");
              }
            })
            .catch((err) => {
              this.$message.error("Failed to submit order.");
              console.log(err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    callGetCartNumber() {
      homepage.methods.getCartNumber.call(this);
    },
    formattedPrice(price) {
      return '$'+parseFloat(price).toFixed(2);
    },
    goToProductDetail(productId) {
      // 使用 Vue Router 导航到商品详情页面，并传递商品的唯一标识
      console.log(productId);
      this.$router.push({ name: 'productDetail', params: { productId } });
    },
    filterTag(value, row) {
      return row.orderStatus === value;
    },
  },
}
</script>

<style scoped>
.el-card {
  width: 90%;
  margin: 0 auto;
}
</style>
