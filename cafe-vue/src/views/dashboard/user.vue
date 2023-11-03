<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-row :gutter="20">
          <el-col :span="6">
            <!--search-->
            <el-input
              placeholder="Please enter user information"
              v-model="queryInfo.keyword"
              clearable
              @clear="getUserList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getUserList"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="2.5">
            <el-button type="primary" @click="addDialogVisible = true" icon="el-icon-plus">Add User</el-button>
          </el-col>
          <el-col :span="2.5">
            <el-button type="danger" @click="batchDeleteUser" :disabled="multipleSelection.length === 0"
                       :icon="multipleSelection.length === 0 ? 'el-icon-delete' : 'el-icon-delete-solid'"
            >Batch Delete</el-button
            >
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24">
        <!--Table-->
        <el-table
          :data="userList"
          border
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column type="index" label="No."></el-table-column>
          <el-table-column prop="userName" label="User Name"></el-table-column>
          <el-table-column prop="loginName" label="Login Name"></el-table-column>
          <el-table-column prop="sex" label="Gender"></el-table-column>
          <el-table-column prop="money" label="Balance">
            <template slot-scope="scope">
              {{formattedMoney(scope.row.money)}}
            </template>
          </el-table-column>
          <el-table-column prop="email" label="Email"></el-table-column>
          <el-table-column prop="address" label="Address"></el-table-column>
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
                @click="removeUserById(scope.row.id)"
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
    <!--Add User Dialog-->
    <el-dialog
      title="Add User"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogClosed"
      :top="`5vh`"
    >
      <el-form :model="userForm" :rules="userRules" ref="userForm" label-width="100px">
        <el-form-item label="Login Name" prop="loginName">
          <el-input v-model="userForm.loginName"></el-input>
        </el-form-item>
        <el-form-item label="User Name" prop="userName">
          <el-input v-model="userForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="userForm.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="Gender" prop="sex">
          <el-radio v-model="userForm.sex" label="Male">Male</el-radio>
          <el-radio v-model="userForm.sex" label="Female">Female</el-radio>
          <el-radio v-model="userForm.sex" label="Other">Other</el-radio>
        </el-form-item>
        <el-form-item label="Balance" prop="money">
          <el-input-number v-model="userForm.money" :min="0" :max="10000" :step="50"></el-input-number>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="userForm.email"></el-input>
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input v-model="userForm.address"></el-input>
        </el-form-item>
      </el-form>
      <!--Button-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addUser('userForm')">Confirm</el-button>
      </span>
    </el-dialog>

    <!--Edit User Dialog-->
    <el-dialog title="Edit User" :visible.sync="editDialogVisible" width="30%" @close="editDialogClosed">
      <el-form :model="editForm" :rules="editRules" ref="editForm" label-width="100px">
        <el-form-item label="Login Name" prop="loginName">
          <el-input v-model="editForm.loginName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="User Name" prop="userName">
          <el-input v-model="editForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="Gender" prop="sex">
          <el-radio v-model="editForm.sex" label="Male">Male</el-radio>
          <el-radio v-model="editForm.sex" label="Female">Female</el-radio>
          <el-radio v-model="editForm.sex" label="Other">Other</el-radio>
        </el-form-item>
        <el-form-item label="Balance" prop="money">
          <el-input-number v-model="editForm.money" :min="0" :max="10000" :step="50"></el-input-number>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input v-model="editForm.address"></el-input>
        </el-form-item>
      </el-form>
      <!--Button-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="editUser('editForm')">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { userList, userAdd, userUpdate, userDelete, userBatchDelete} from "@/api/user";
export default {
  data() {
    return {
      userList: [],
      total: 0,
      queryInfo: {
        keyword: "",
        pageNo: 1,
        pageSize: 5,
      },
      addDialogVisible: false,
      userForm: {
        loginName: "",
        userName: "",
        password: "",
        sex: "",
        money: "",
        email: "",
        address: "",
      },
      editDialogVisible: false,
      editForm: {
        id: "",
        loginName: "",
        userName: "",
        password: "",
        sex: "",
        money: "",
        email: "",
        address: "",
      },
      multipleSelection: [],
      ids: [],
      userRules: {
        loginName : [
          { required: true, message: 'Login name cannot be empty!', trigger: 'change' }
        ],
        userName : [
          { required: true, message: 'User name cannot be empty!', trigger: 'change' }
        ],
        password : [
          { required: true, message: 'Password cannot be empty!', trigger: 'change' }
        ],
        sex : [
          { required: true, message: 'Must select a gender!', trigger: 'change' }
        ],
        money : [
          { required: true, message: 'Balance cannot be empty!', trigger: 'change' }
        ],
        email : [
          { required: true, message: 'Email cannot be empty!', trigger: 'change' }
        ],
        address : [
          { required: true, message: 'Address cannot be empty!', trigger: 'change' }
        ]
      },
      editRules: {
        loginName : [
          { required: true, message: 'Login name cannot be empty!', trigger: 'change' }
        ],
        userName : [
          { required: true, message: 'User name cannot be empty!', trigger: 'change' }
        ],
        sex : [
          { required: true, message: 'Must select a gender!', trigger: 'change' }
        ],
        money : [
          { required: true, message: 'Balance cannot be empty!', trigger: 'change' }
        ],
        email : [
          { required: true, message: 'Email cannot be empty!', trigger: 'change' }
        ],
        address : [
          { required: true, message: 'Address cannot be empty!', trigger: 'change' }
        ]
      },
    };
  },
  created() {
    this.getUserList();
  },
  methods: {
    getUserList() {
      userList(this.queryInfo)
        .then((res) => {
          if (res.data.code === 200) {
            this.userList = res.data.data.records;
            this.total = res.data.data.total;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    formattedMoney(money) {
      return '$'+parseFloat(money).toFixed(2);
    },
    // Listen the event of changing pageSize
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getUserList();
    },
    // Listen the event of changing pageNo
    handleCurrentChange(newPage) {
      this.queryInfo.pageNo = newPage;
      this.getUserList();
    },
    // Add user
    addUser(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          userAdd(this.userForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.addDialogVisible = false;
                this.getUserList();
                this.$message({
                  message: "Added user successfully.",
                  type: "success",
                });
              } else {
                this.$message.error("Failed to add user.");
              }
            })
            .catch((err) => {
              this.$message.error("Failed to add user.");
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
      this.$refs.userForm.resetFields();
    },
    editDialogClosed() {
      this.$refs.editForm.resetFields();
    },

    showEditDialog(userinfo) {
      this.editDialogVisible = true;
      this.editForm = userinfo;
    },
    // Edit user
    editUser(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          userUpdate(this.editForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.editDialogVisible = false;
                this.getUserList();
                this.$message({
                  message: "Edited user successfully.",
                  type: "success",
                });
              } else {
                this.$message.error("Failed to edit user.");
              }
            })
            .catch((err) => {
              this.$message.error("Failed to edit user.");
              console.loge(err);
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // Delete user by id
    async removeUserById(id) {
      // Confirm dialog
      const confirmResult = await this.$confirm(
        "This operation will permanently delete the user. Do you want to continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmResult === "confirm") {
        userDelete(id)
          .then((res) => {
            if (res.data.code === 200) {
              this.getUserList();
              this.$message({
                message: "Deleted user successfully.",
                type: "success",
              });
            } else {
              this.$message.error("Failed to delete user.");
            }
          })
          .catch((err) => {
            this.$message.error("Failed to delete user.");
            console.log(err);
          });
      }
    },
    // Listen the event of changing selection
    handleSelectionChange(val) {
      this.multipleSelection = val;
      const newIds = [];
      this.multipleSelection.forEach((item) => {
        newIds.push(item.id);
      });
      this.ids = newIds;
    },
    // Batch delete user
    async batchDeleteUser(){
      // Confirm dialog
      const confirmResult = await this.$confirm(
        "This operation will permanently delete the user. Do you want to continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmResult === "confirm") {
        userBatchDelete(this.ids)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "Deleted user successfully.",
                type: "success",
              });
              this.getUserList();
            } else {
              this.$message.error("Failed to delete user.");
            }
          })
          .catch((err) => {
            this.$message.error("Failed to delete user.");
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
</style>
