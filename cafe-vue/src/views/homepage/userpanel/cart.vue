<script setup>

</script>

<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-row :gutter="20">
          <el-col :span="2.5">
            <el-button type="primary" @click="" disabled
            >Submit Order</el-button>
          </el-col>
          <el-col :span="2.5">
            <el-button type="danger" @click="batchDeleteCart"
            >Batch Delete</el-button>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24">
        <!--Table-->
        <el-table
          :data="cartList"
          border
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column type="index" label="No."></el-table-column>
          <el-table-column prop="productId" label="Product Id"></el-table-column>
          <el-table-column prop="productName" label="Product Name"></el-table-column>
          <el-table-column prop="productPrice" label="Product Price"></el-table-column>
          <el-table-column prop="productNum" label="Product Num"></el-table-column>
          <el-table-column prop="productSize" label="Product Size"></el-table-column>
          <el-table-column label="Operation">
            <template slot-scope="scope">
              <!--Edit Btn-->
              <el-button
                type="primary"
                size="mini"
                icon="el-icon-edit"
                @click="showEditDialog(scope.row)"
              ></el-button>
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

    <!--Edit Cart Dialog-->
    <el-dialog title="Edit Cart" :visible.sync="editDialogVisible" width="35%" :top="`5vh`">
      <!--内容主体区域-->
      <el-form :model="editForm" :rules="editRules" ref="editForm" label-width="25%">
        <el-form-item label="Product Num" prop="productName">
          <el-input v-model="editForm.productNum"></el-input>
        </el-form-item>
        <el-form-item label="Product Size" prop="productSize">
          <el-radio v-model="editForm.productSize" label="S">S</el-radio>
          <el-radio v-model="editForm.productSize" label="M">M</el-radio>
          <el-radio v-model="editForm.productSize" label="L">L</el-radio>
        </el-form-item>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="editCart('editForm')">Confirm</el-button>
      </span>
    </el-dialog>
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
      // cartList(this.queryInfo)
      //   .then((res) => {
      //     if (res.data.code === 200) {
      //       console.log(this.queryInfo);
      //       // Get the cart list
      //       this.cartList = res.data.data;
      //       // this.cartList.productPrice = 1;
      //       this.total = res.data.totalRecords;
      //     } else {
      //       this.$message.error(res.data.message);
      //     }
      //   })
      //   .catch((err) => {
      //     console.log(err);
      //   });
      console.log(this.queryInfo);
      cartNewList(this.queryInfo)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            console.log(this.queryInfo);
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
      console.log(cartInfo);
      this.editDialogVisible = true;
      console.log(cartInfo);
      this.editForm = cartInfo;
    },
    // Edit cart
    editCart(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateCart(this.editForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.editDialogVisible = false;
                this.getCartList();
                this.$message({
                  message: "Edited cart successfully.",
                  type: "success",
                });
              } else {
                this.$message.error("Failed to edit cart.");
              }
            })
            .catch((err) => {
              this.$message.error("Failed to edit cart.");
              console.loge(err);
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    callGetCartNumber() {
      homepage.methods.getCartNumber.call(this);
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
      console.log(this.multipleSelection);
      //向被删除的ids赋值
      this.multipleSelection.forEach((item) => {
        this.cartIds.push(item.cartId);
        console.log(this.cartIds);
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
    }
  },
}
</script>

<style scoped>

</style>
