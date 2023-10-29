<script setup>

</script>

<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-row :gutter="20">
          <el-col :span="2.5">
            <el-button type="danger" @click="batchDeleteOrder" :disabled="multipleSelection.length === 0"
            >Batch Delete</el-button
            >
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24">
        <!--Table-->
        <el-table
          :data="orderList"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center', 'font-weight': 'bold'}"
          :default-sort="{prop: 'orderTime', order: 'descending'}"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" min-width="3%"> </el-table-column>
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-card>
                <el-steps :active="props.row.orderStatus === 'In Process' ? 1 : props.row.orderStatus === 'In Delivery' ? 2 : 4" finish-status="success" align-center>
                  <el-step title="Submitted" description="User has submitted the order"></el-step>
                  <el-step title="In Process" description="Order is being processed"></el-step>
                  <el-step title="In Delivery" description="Order is being delivered"></el-step>
                  <el-step title="Done" description="Order completed"></el-step>
                </el-steps>
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
          <el-table-column prop="orderId" label="Order ID" min-width="8%"></el-table-column>
          <el-table-column prop="orderTime" label="Order Time" sortable min-width="15%"></el-table-column>
          <el-table-column prop="orderCustomerId" label="CustomerId" min-width="10%"></el-table-column>
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
    <el-dialog title="Edit Order Status" :visible.sync="submitDialogVisible" width="35%" :top="`20vh`">
      <el-form :model="editForm" :rules="submitOrderRules" ref="orderForm" label-width="130px">
        <el-form-item label="Order Status" prop="orderStatus">
          <el-radio-group v-model="editForm.orderStatus">
            <el-radio-button label="In Process">In Process</el-radio-button>
            <el-radio-button label="In Delivery">In Delivery</el-radio-button>
            <el-radio-button label="Done">Done</el-radio-button>
          </el-radio-group>
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
import { getOrderList, getCustomerOrderList, getOrderDetail, updateOrder } from "@/api/order";
import {batchDeleteOrder, deleteOrder} from "../../api/order";
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
      multipleSelection: [],
      orderIds: [],
      submitOrderRules: {
        orderStatus : [
          { required: true, message: 'Order status cannot be empty!', trigger: 'change' }
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
      console.log(this.queryInfo);
      getOrderList(this.queryInfo)
        .then((res) => {
          console.log(res);
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
    // Delete order by id
    async removeOrderById(orderId) {
      const confirmResult = await this.$confirm(
        "Are you sure to delete this order?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmResult === "confirm") {
        deleteOrder(orderId)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                showClose: true,
                message: "Delete order successfully.",
                type: "success",
              });
              this.getOrderList();
            } else {
              this.$message.error("Failed to delete order.");
            }
          })
          .catch((err) => {
            this.$message.error("Failed to delete order.");
            console.log(err);
          });
      }
    },
    //批量选中事件处理
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.newOrderIds = [];
      this.multipleSelection.forEach((item) => {
        this.newOrderIds.push(item.orderId);
      });
      this.orderIds = this.newOrderIds;
    },
    // Batch delete order
    async batchDeleteOrder(){
      const confirmResult = await this.$confirm(
        "Are you sure to delete these orders?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmResult === "confirm") {
        batchDeleteOrder(this.orderIds)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                showClose: true,
                message: "Delete orders successfully.",
                type: "success",
              });
              this.getOrderList();
            } else {
              this.$message.error("Failed to delete orders.");
            }
          })
          .catch((err) => {
            this.$message.error("Failed to delete orders.");
            console.log(err);
          });
      }
    },
  },
}
</script>

<style scoped>
.el-card {
  width: 90%;
  margin: 0 auto 20px;
}
</style>
