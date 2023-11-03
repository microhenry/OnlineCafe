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
            <template slot-scope="scope">{{ formattedPrice(getAdjustedPrice(scope.row.productPrice, scope.row.productSize)) }}</template>
          </el-table-column>
          <el-table-column prop="productNum" label="Product Num" min-width="15%">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.productNum" @change="changeProductNum(scope.row)" size="small" :min="1" :max="10" label="ProductNum"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column prop="productSize" label="Product Size" min-width="25%">
            <template slot-scope="scope">
              <el-radio-group v-model="scope.row.productSize" size="medium" fill="#33ccff" @change="changeProductSize(scope.row)">
                <el-radio-button label="S"><i class="el-icon-coffee el-icon-coffee-small"></i> Small</el-radio-button>
                <el-radio-button label="M"><i class="el-icon-coffee el-icon-coffee-medium"></i> Medium</el-radio-button>
                <el-radio-button label="L"><i class="el-icon-coffee el-icon-coffee-large"></i> Large</el-radio-button>
              </el-radio-group>
            </template>
          </el-table-column>
          <el-table-column label="Delete" min-width="10%">
            <template slot-scope="scope">
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
            <div>
              <el-tag type="info" effect="plain" style="height: 100%; line-height: 40px; font-size: 15px">
                <span style="color: red">Total: <span style="color: darkblue">{{ formattedPrice(totalPrice) }}</span></span>
              </el-tag>
            </div>
          </el-col>
          <el-col :span="2.5">
            <el-button type="primary" @click="showSubmitDialog()" :disabled="multipleSelection.length === 0"
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
        :page-sizes="[1, 5, 10, 20]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-row>

    <!--Confirm Order Dialog-->
    <el-dialog title="Confirm Order" :visible.sync="submitDialogVisible" width="35%" :top="`5vh`">
      <el-descriptions :column="2" border :label-style="labelStyle" :contentStyle="contentStyle" style="margin: 0 10px 20px 10px">
        <el-descriptions-item label="Total Product(s):">{{ multipleSelection.length }}</el-descriptions-item>
        <el-descriptions-item label="Total Price:">{{ formattedPrice(totalPrice) }}</el-descriptions-item>
      </el-descriptions>
      <el-form :model="orderForm" :rules="submitOrderRules" ref="orderForm" label-width="130px">
        <el-form-item label="Delivery Address" prop="orderAddress">
          <el-input v-model="orderForm.orderAddress" placeholder="Please enter your delivery address"></el-input>
        </el-form-item>
        <el-form-item label="Order Comment">
          <el-input v-model="orderForm.orderComment" type="textarea" placeholder="Please enter your order comment"></el-input>
        </el-form-item>
      </el-form>
      <!--Button-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="submitDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitOrder('orderForm')">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { cartNumber, cartList, cartNewList, addCart, updateCart, deleteCart, batchDeleteCart } from "@/api/cart";
import homepage from "../homepage.vue";
import { addOrder } from "@/api/order";
export default {
  data() {
    return {
      cartList: [],
      total: 0,
      totalPrice: 0,
      queryInfo: {
        keyword: "",
        pageNo: 1,
        pageSize: 10,
      },
      submitDialogVisible: false,
      editForm: {
        cartId: "",
        productId: "",
        productNum: "",
        userId: "",
        productName: "",
        productSize: "",
      },
      orderForm: {
        cartIds: [],
        userId: "",
        orderComment: "",
        orderAddress: "",
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
    this.getCartList();
  },
  methods: {
    getCartList() {
      this.queryInfo.keyword = this.$store.state.user.id;
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
    // Listen the event of changing pageSize
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getCartList();
    },
    // Listen the event of changing pageNo
    handleCurrentChange(newPage) {
      this.queryInfo.pageNo = newPage;
      this.getCartList();
    },

    showSubmitDialog(cartInfo) {
      this.submitDialogVisible = true;
    },
    submitOrder(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.orderForm.cartIds = this.cartIds;
          this.orderForm.userId = this.$store.state.user.id;
          addOrder(this.orderForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message({
                  showClose: true,
                  message: "Submitted order successfully.",
                  type: "success",
                });
                this.submitDialogVisible = false;
                this.getCartList();
                this.callGetCartNumber();
              } else if (res.data.code === 400) {
                this.$message.error(res.data.message);
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
    postUpdateRequest(successMessage){
      updateCart(this.editForm)
        .then((res) => {
          if (res.data.code === 200) {
            this.getCartList();
            this.$message({
              showClose: true,
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
    getAdjustedPrice(price, size) {
      let adjustedPrice = parseFloat(price);
      if (size === 'M') {
        adjustedPrice += 2;
      } else if (size === 'L') {
        adjustedPrice += 4;
      }
      return adjustedPrice;
    },
    goToProductDetail(productId) {
      this.$router.push({ name: 'productDetail', params: { productId } });
    },
    // Delete cart by id
    async removeCartById(cartId) {
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
                showClose: true,
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
    // Listen the event of changing selection
    handleSelectionChange(val) {
      this.multipleSelection = val;
      const newCartIds = [];
      this.multipleSelection.forEach((item) => {
        newCartIds.push(item.cartId);
      });
      this.cartIds = newCartIds;
      // Calculate the total price
      this.totalPrice = 0;
      this.multipleSelection.forEach((item) => {
        this.totalPrice += this.getAdjustedPrice(item.productPrice, item.productSize) * item.productNum;
      });
    },
    // Batch delete cart
    async batchDeleteCart(){
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
        batchDeleteCart(this.cartIds)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                showClose: true,
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
.el-icon-coffee-small{
  font-size: 10px;
}

.el-icon-coffee-medium{
  font-size: 12px;
}

.el-icon-coffee-large{
  font-size: 14px;
}
</style>
