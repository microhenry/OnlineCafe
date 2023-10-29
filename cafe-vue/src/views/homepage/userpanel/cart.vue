<script setup>

</script>

<template>
  <div>
    <el-row>
      <el-col :span="24">
        <!--Table-->
        <el-table
          :data="cartList"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center'}"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" min-width="3%"> </el-table-column>
          <el-table-column type="index" label="No." min-width="3%"></el-table-column>
          <el-table-column prop="productId" label="Product Id" min-width="10%"></el-table-column>
          <el-table-column prop="productName" label="Product Name" min-width="20%">
            <template slot-scope="scope">
              <el-link type="primary" @click.native="goToProductDetail(scope.row.productName)">{{ scope.row.productName }}</el-link>
            </template>
          </el-table-column>

          <el-table-column prop="productPrice" label="Product Price" min-width="10%">
            <template slot-scope="scope">{{ formattedPrice(scope.row.productPrice) }}</template>
          </el-table-column>
          <el-table-column prop="productNum" label="Product Num" min-width="15%">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.productNum" @change="changeProductNum(scope.row)" size="small" :min="1" :max="10" label="ProductNum"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column prop="productSize" label="Product Size" min-width="20%">
            <template slot-scope="scope">
              <el-radio-group v-model="scope.row.productSize" size="mini" fill="#33ccff" @change="changeProductSize(scope.row)">
                <el-radio-button label="S">Small</el-radio-button>
                <el-radio-button label="M">Medium</el-radio-button>
                <el-radio-button label="L">Large</el-radio-button>
              </el-radio-group>
            </template>
          </el-table-column>
          <el-table-column label="Delete" min-width="10%">
            <template slot-scope="scope">
<!--              &lt;!&ndash;Edit Btn&ndash;&gt;-->
<!--              <el-button-->
<!--                type="primary"-->
<!--                size="mini"-->
<!--                icon="el-icon-edit"-->
<!--                @click="showEditDialog(scope.row)"-->
<!--              ></el-button>-->
              <!--Delete Btn-->
              <el-button
                type="danger"
                size="mini"
                icon="el-icon-delete"
                @click="removeCartById(scope.row.cartId)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="24" style="margin-top: 20px; margin-bottom: 20px">
        <el-row :gutter="9" type="flex" justify="end">
          <el-col :span="2.5">
            <div >
              <el-tag type="info" effect="plain" style="height: 100%; line-height: 40px; font-size: 15px">
                <span style="color: red">Total: <span style="color: darkblue">{{ formattedPrice(totalPrice) }}</span></span>
              </el-tag>
            </div>
          </el-col>
          <el-col :span="2.5">
            <el-button type="primary" @click="" :disabled="multipleSelection.length === 0"
            >Submit Order</el-button>
          </el-col>
          <el-col :span="2.5" style="margin-left: 10px">
            <el-button type="danger" @click="batchDeleteCart" :disabled="multipleSelection.length === 0"
            >Batch Delete</el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-row>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNo"
        :page-sizes="[1, 2, 5, 10]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-row>

<!--    &lt;!&ndash;Edit Cart Dialog&ndash;&gt;-->
<!--    <el-dialog title="Edit Cart" :visible.sync="editDialogVisible" width="35%" :top="`5vh`">-->
<!--      &lt;!&ndash;内容主体区域&ndash;&gt;-->
<!--      <el-form :model="editForm" :rules="editRules" ref="editForm" label-width="25%">-->
<!--        <el-form-item label="Product Num" prop="productName">-->
<!--          <el-input v-model="editForm.productNum"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="Product Size" prop="productSize">-->
<!--          <el-radio v-model="editForm.productSize" label="S">S</el-radio>-->
<!--          <el-radio v-model="editForm.productSize" label="M">M</el-radio>-->
<!--          <el-radio v-model="editForm.productSize" label="L">L</el-radio>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      &lt;!&ndash;底部按钮区域&ndash;&gt;-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--        <el-button @click="editDialogVisible = false">Cancel</el-button>-->
<!--        <el-button type="primary" @click="editCart('editForm')">Confirm</el-button>-->
<!--      </span>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
import { cartNumber, cartList, cartNewList, addCart, updateCart, deleteCart, batchDeleteCart } from "@/api/cart";
import homepage from "../homepage.vue";
export default {
  data() {
    return {
      cartList: [],
      total: 0,
      totalPrice: 0,
      queryInfo: {
        keyword: "",
        pageNo: 1,
        pageSize: 5,
      },
      editDialogVisible: false,
      editForm: {
        cartId: "",
        productId: "",
        productNum: "",
        userId: "",
        productName: "",
        productSize: "",
      },
      multipleSelection: [],
      cartIds: [],
      editRules: {
        productNum : [
          { required: true, message: 'Product num cannot be empty!', trigger: 'change' }
        ],
        productSize : [
          { required: true, message: 'Product size cannot be empty!', trigger: 'change' }
        ],
      },
    }
  },
  created() {
    this.getCartList();
  },
  methods: {
    getCartList() {
      this.queryInfo.keyword = this.$store.state.user.id;
      console.log(this.queryInfo);
      cartNewList(this.queryInfo)
        .then((res) => {
          if (res.data.code === 200) {
            // Get the cart list
            this.cartList = res.data.data;
            // this.cartList.productPrice = 1;
            this.total = res.data.totalRecords;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 监听 pageSize 改变的事件
    // Listen the event of changing pageSize
    handleSizeChange(newSize) {
      // console.log(newSize)
      this.queryInfo.pageSize = newSize;
      this.getCartList();
    },
    // 监听 当前页码值 改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage)
      this.queryInfo.pageNo = newPage;
      this.getCartList();
    },
    // Listen the event of clicking edit button
    showEditDialog(cartInfo) {
      this.editDialogVisible = true;
      this.editForm = cartInfo;
    },
    // // Edit cart
    // editCart(formName) {
    //   console.log("editForm: ");
    //   console.log(this.editForm);
    //   console.log(formName);
    //   this.$refs[formName].validate((valid) => {
    //     if (valid) {
    //       this.postUpdateRequest('Edited cart successfully.');
    //     } else {
    //       console.log('error submit!!');
    //       return false;
    //     }
    //   });
    // },
    postUpdateRequest(successMessage){
      updateCart(this.editForm)
        .then((res) => {
          if (res.data.code === 200) {
            this.editDialogVisible = false;
            this.getCartList();
            this.$message({
              message: successMessage,
              type: "success",
            });
          } else {
            this.$message.error("Failed to edit cart.");
          }
        })
        .catch((err) => {
          this.$message.error("Failed to edit cart.");
          console.log(err);
        });
    },
    callGetCartNumber() {
      homepage.methods.getCartNumber.call(this);
    },
    changeProductNum(cartInfo){
      this.editForm = cartInfo;
      this.postUpdateRequest('Edited product quantity successfully.');
    },
    changeProductSize(cartInfo){
      this.editForm = cartInfo;
      this.postUpdateRequest('Edited product size successfully.');
    },
    formattedPrice(price) {
      return '$'+parseFloat(price).toFixed(2);
    },
    goToProductDetail(productId) {
      // 使用 Vue Router 导航到商品详情页面，并传递商品的唯一标识
      console.log(productId);
      this.$router.push({ name: 'productDetail', params: { productId } });
    },
    // Delete cart by id
    async removeCartById(cartId) {
      console.log("cartId: "+cartId);
      // Confirm dialog
      const confirmResult = await this.$confirm(
        "This operation will permanently delete the cart. Do you want to continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmResult === "confirm") {
        deleteCart(cartId)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "Deleted cart successfully.",
                type: "success",
              });
              this.getCartList();
              this.callGetCartNumber();
            } else {
              this.$message.error("Failed to delete cart.");
            }
          })
          .catch((err) => {
            this.$message.error("Failed to delete cart.");
            console.log(err);
          });
      }
    },
    //批量选中事件处理
    handleSelectionChange(val) {
      this.multipleSelection = val;
      const newCartIds = [];
      //向被删除的ids赋值
      this.multipleSelection.forEach((item) => {
        newCartIds.push(item.cartId);
      });
      this.cartIds = newCartIds;
      // Calculate the total price
      this.totalPrice = 0;
      this.multipleSelection.forEach((item) => {
        this.totalPrice += item.productPrice * item.productNum;
      });
    },
    // Batch delete cart
    async batchDeleteCart(){
      // 弹框 询问用户是否删除
      const confirmResult = await this.$confirm(
        "This operation will permanently delete the cart. Do you want to continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmResult === "confirm") {
        batchDeleteCart(this.cartIds)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "Deleted cart successfully.",
                type: "success",
              });
              this.getCartList();
              this.callGetCartNumber();
            } else {
              this.$message.error("Failed to delete cart.");
            }
          })
          .catch((err) => {
            this.$message.error("Failed to delete cart.");
            console.log(err);
          });
      }
    },
  },
}
</script>

<style scoped>

</style>
