<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-row :gutter="20">
          <el-col :span="6">
            <!--search-->
            <el-input
              placeholder="Please enter product information"
              v-model="queryInfo.keyword"
              clearable
              @clear="getProductList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getProductList"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="2.5">
            <el-button type="primary" @click="addDialogVisible = true"
            >Add Product</el-button
            >
          </el-col>
          <el-col :span="2.5">
            <el-button type="danger" @click="batchDeleteProduct"
            >Batch Delete</el-button
            >
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24">
        <!--Table-->
        <el-table
          :data="productList"
          border
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column type="index" label="No."></el-table-column>
          <el-table-column prop="productCategory" label="Product Category"></el-table-column>
          <el-table-column prop="productName" label="Product Name"></el-table-column>
          <el-table-column prop="productSize" label="Product Size"></el-table-column>
          <el-table-column prop="productPrice" label="Product Price"></el-table-column>
          <el-table-column prop="productPicUrl" label="Picture Url"></el-table-column>
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
                @click="removeProductById(scope.row.productId)"
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
    <!--Add Product Dialog-->
    <el-dialog
      title="Add Product"
      :visible.sync="addDialogVisible"
      width="35%"
      @close="addDialogClosed"
      :top="`5vh`"
    >
      <!--内容主体区域-->
      <el-form :model="productForm" :rules="productRules" ref="productForm" label-width="25%">
        <el-form-item label="Product Name" prop="productName">
          <el-input v-model="productForm.productName"></el-input>
        </el-form-item>
        <el-form-item label="Category" prop="productCategory">
          <el-select v-model="productForm.productCategory" placeholder="Please select a product category.">
            <el-option v-for="item in categoryOpinions" :key="item.name" :label="item.label" :value="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Product Size" prop="productSize">
          <el-radio v-model="productForm.productSize" label="S">S</el-radio>
          <el-radio v-model="productForm.productSize" label="M">M</el-radio>
          <el-radio v-model="productForm.productSize" label="L">L</el-radio>
        </el-form-item>
        <el-form-item label="Product Price" prop="productPrice">
          <el-input-number
            v-model="productForm.productPrice"
            :precision="2"
            :step="1"
            :min="0.1"
            :max="100"
            label="Product Price"
          />
        </el-form-item>
        <el-form-item label="Picture Url" prop="productPicUrl">
          <el-input v-model="productForm.productPicUrl"></el-input>
        </el-form-item>
        <el-form-item label="Description" prop="productDescription">
          <el-input type="textarea" :rows="3" v-model="productForm.productDescription"></el-input>
        </el-form-item>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addProduct('productForm')">Confirm</el-button>
      </span>
    </el-dialog>

    <!--Edit Product Dialog-->
    <el-dialog title="Edit Product" :visible.sync="editDialogVisible" width="35%" :top="`5vh`">
      <!--内容主体区域-->
      <el-form :model="editForm" :rules="editRules" ref="editForm" label-width="25%">
        <el-form-item label="Product Name" prop="productName">
          <el-input v-model="editForm.productName"></el-input>
        </el-form-item>
        <el-form-item label="Category" prop="productCategory">
          <el-select v-model="editForm.productCategory" placeholder="Please select a product category.">
            <el-option v-for="item in categoryOpinions" :key="item.name" :label="item.label" :value="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Product Size" prop="productSize">
          <el-radio v-model="editForm.productSize" label="S">S</el-radio>
          <el-radio v-model="editForm.productSize" label="M">M</el-radio>
          <el-radio v-model="editForm.productSize" label="L">L</el-radio>
        </el-form-item>
        <el-form-item label="Product Price" prop="productPrice">
          <el-input-number
            v-model="editForm.productPrice"
            :precision="2"
            :step="1"
            :min="0.1"
            :max="100"
            label="Product Price"
          />
        </el-form-item>
        <el-form-item label="Picture Url" prop="productPicUrl">
          <el-input v-model="editForm.productPicUrl"></el-input>
        </el-form-item>
        <el-form-item label="Description" prop="productDescription">
          <el-input type="textarea" :rows="3" v-model="editForm.productDescription"></el-input>
        </el-form-item>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="editProduct('editForm')">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { productList, getProductDetail, addProduct, updateProduct, deleteProduct, batchDeleteProduct } from "@/api/productEdit";
import { tabs } from "../homepage/products.vue";
export default {
  data() {
    return {
      productList: [],
      total: 0,
      categoryOpinions: tabs.filter(tab => tab.name !== 'all'),
      queryInfo: {
        keyword: "",
        pageNo: 1,
        pageSize: 5,
      },
      addDialogVisible: false,
      productForm: {
        productName: "",
        productCategory: "",
        productDescription: "",
        productSize: "",
        productPrice: "9.90",
        productPicUrl: "",
      },
      editDialogVisible: false,
      editForm: {
        productId: "",
        productName: "",
        productCategory: "",
        productDescription: "",
        productSize: "",
        productPrice: "",
        productPicUrl: "",
      },
      multipleSelection: [],
      productIds: [],
      productRules: {
        productName : [
          { required: true, message: 'Login name cannot be empty!', trigger: 'change' }
        ],
        productCategory : [
          { required: true, message: 'User name cannot be empty!', trigger: 'change' }
        ],
        productDescription : [
          { required: true, message: 'Description cannot be empty!', trigger: 'change' }
        ],
        productSize : [
          { required: true, message: 'Must select a gender!', trigger: 'change' }
        ],
        productPrice : [
          { required: true, message: 'Balance cannot be empty!', trigger: 'change' }
        ],
        productPicUrl : [
          { required: true, message: 'picUrl cannot be empty!', trigger: 'change' }
        ],
      },
      editRules: {
        productName : [
          { required: true, message: 'Login name cannot be empty!', trigger: 'change' }
        ],
        productCategory : [
          { required: true, message: 'User name cannot be empty!', trigger: 'change' }
        ],
        productDescription : [
          { required: true, message: 'Description cannot be empty!', trigger: 'change' }
        ],
        productSize : [
          { required: true, message: 'Must select a gender!', trigger: 'change' }
        ],
        productPrice : [
          { required: true, message: 'Balance cannot be empty!', trigger: 'change' }
        ],
        productPicUrl : [
          { required: true, message: 'picUrl cannot be empty!', trigger: 'change' }
        ],
      },
    };
  },
  created() {
    this.getProductList();
  },
  methods: {
    getProductList() {
      productList(this.queryInfo)
        .then((res) => {
          if (res.data.code === 200) {
            // Get the product list
            this.productList = res.data.data;
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
      this.getProductList();
    },
    // 监听 当前页码值 改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage)
      this.queryInfo.pageNo = newPage;
      this.getProductList();
    },
    // Add product
    addProduct(formName) {
      console.log(formName)
      this.$refs[formName].validate((valid) => {
        console.log(valid)
        if (valid) {
          addProduct(this.productForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.addDialogVisible = false;
                this.getProductList();
                this.$message({
                  message: "Added product successfully.",
                  type: "success",
                });
              } else {
                this.$message.error("Failed to add product.");
              }
            })
            .catch((err) => {
              this.$message.error("Failed to add product.");
              console.log(err);
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    // Listen the event of closing add dialog
    addDialogClosed() {
      // 表单内容重置为空
      this.$refs.productForm.resetFields();
    },

    // Listen the event of clicking edit button
    showEditDialog(productInfo) {
      this.editDialogVisible = true;
      console.log(productInfo);
      this.editForm = productInfo;
    },
    // Edit product
    editProduct(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateProduct(this.editForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.editDialogVisible = false;
                this.getProductList();
                this.$message({
                  message: "Edited product successfully.",
                  type: "success",
                });
              } else {
                this.$message.error("Failed to edit product.");
              }
            })
            .catch((err) => {
              this.$message.error("Failed to edit product.");
              console.loge(err);
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // Delete product by id
    async removeProductById(productId) {
      // Confirm dialog
      const confirmResult = await this.$confirm(
        "This operation will permanently delete the product. Do you want to continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmResult === "confirm") {
        deleteProduct(productId)
          .then((res) => {
            if (res.data.code === 200) {
              this.getProductList();
              this.$message({
                message: "Deleted product successfully.",
                type: "success",
              });
            } else {
              this.$message.error("Failed to delete product.");
            }
          })
          .catch((err) => {
            this.$message.error("Failed to delete product.");
            console.log(err);
          });
      }
    },
    //批量选中事件处理
    handleSelectionChange(val) {
      // this.multipleSelection = val;
      // console.log(this.multipleSelection);
      // //向被删除的ids赋值
      // this.multipleSelection.forEach((item) => {
      //   this.productIds.push(item.productId);
      //   console.log(this.productIds);
      // });

      this.multipleSelection = val;
      const newProductIds = [];
      //向被删除的ids赋值
      this.multipleSelection.forEach((item) => {
        newProductIds.push(item.productId);
      });
      this.productIds = newProductIds;
    },
    // Batch delete product
    async batchDeleteProduct(){
      // 弹框 询问用户是否删除
      const confirmResult = await this.$confirm(
        "This operation will permanently delete the product. Do you want to continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmResult === "confirm") {
        console.log("productIds: ");
        console.log(this.productIds);
        batchDeleteProduct(this.productIds)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "Deleted product successfully.",
                type: "success",
              });
              this.getProductList();
            } else {
              this.$message.error("Failed to delete product.");
            }
          })
          .catch((err) => {
            this.$message.error("Failed to delete product.");
            console.log(err);
          });
      }
    }
  },
};
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.el-card {
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1) !important;
  height: 60pt;
}

.el-select {
  width: 100%;
}
</style>
