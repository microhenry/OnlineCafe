<script setup>

</script>

<template>

  <div>
    <el-card>
      <el-row>
        <el-col :span="18" :offset="3">
          <el-descriptions title="My Profile" direction="horizontal" :column="1" border :label-style="labelStyle" :contentStyle="contentStyle">
            <el-descriptions-item label="UID">{{ editForm.id }}</el-descriptions-item>
            <el-descriptions-item label="Login Name">{{ editForm.loginName }}</el-descriptions-item>
            <el-descriptions-item label="User Name">{{ editForm.userName }}</el-descriptions-item>
            <el-descriptions-item label="Gender">{{ editForm.sex }}</el-descriptions-item>
            <el-descriptions-item label="Balance">{{ formattedMoney }}</el-descriptions-item>
            <el-descriptions-item label="Email">{{ editForm.email }}</el-descriptions-item>
            <el-descriptions-item label="Address">{{ editForm.address }}</el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10" :offset="2">
          <el-button class="profile-btn" type="primary" @click="showEditDialog">Edit Profile</el-button>
        </el-col>
        <el-col :span="10" :offset="0">
          <el-button class="profile-btn" type="danger" @click="showChangePasswordDialog">Change Password</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!--Edit User Dialog-->
    <el-dialog title="Edit Profile" :visible.sync="editDialogVisible" width="30%">
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

    <!--Change Password Dialog-->
    <el-dialog title="Change Password" :visible.sync="changePasswordDialogVisible" width="30%">
      <el-form :model="passwordForm" :rules="changePasswordRules" ref="passwordForm" label-width="140px">
        <el-form-item label="Old Password" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item label="New Password" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item label="Confirm Password" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password></el-input>
        </el-form-item>
      </el-form>
      <!--Button-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="changePasswordDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="changePassword('passwordForm')">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { userDetail, userUpdate } from "@/api/user";
export default {
  data() {
    let loginName = this.$store.state.user.loginName;
    return {
      editDialogVisible: false,
      changePasswordDialogVisible: false,
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
      passwordForm: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: "",
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
      changePasswordRules: {
        oldPassword: [
          { required: true, message: 'Please enter the old password.', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.editForm.password) {
                callback(new Error('Old password is incorrect.'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ],
        newPassword: [
          { required: true, message: 'Please enter your new password.', trigger: 'blur' },
        ],
        confirmPassword: [
          { required: true, message: 'Please confirm your new password.', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.passwordForm.newPassword) {
                callback(new Error('Confirm password do not match new password.'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ],
      },
      labelStyle: {
        width: '25%'
      },
      contentStyle: {
        width: '75%'
      },
    }
  },
  created() {
    this.loginName = this.$store.state.user.loginName;
    this.getUserDetails();
  },
  methods:{
    showEditDialog() {
      this.editDialogVisible = true;
    },
    showChangePasswordDialog() {
      this.changePasswordDialogVisible = true;
    },
    clearPasswordForm() {
      this.passwordForm.oldPassword = "";
      this.passwordForm.newPassword = "";
      this.passwordForm.confirmPassword = "";
    },
    getUserDetails() {
      userDetail("", this.loginName)
        .then((res) => {
          if (res.data.code === 200) {
            this.editForm = res.data.data;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    editUser(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          userUpdate(this.editForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.editDialogVisible = false;
                this.getUserDetails();
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
              console.log(err);
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    changePassword(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // Validate old password
          if (this.editForm.password !== this.passwordForm.oldPassword) {
            this.$message.error('Old password is incorrect.');
            return;
          }
          // Validate new password and confirm password
          if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
            this.$message.error('New password and confirm password do not match.');
            return;
          }

          // Update password
          this.editForm.password = this.passwordForm.newPassword;
          userUpdate(this.editForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.editDialogVisible = false;
                this.clearPasswordForm();
                this.getUserDetails();
                this.$message({
                  message: 'Password changed successfully.',
                  type: 'success',
                });
              } else {
                this.$message.error("Failed to change password.");
              }
            })
            .catch((err) => {
              this.$message.error("Failed to change password.");
              console.log(err);
            });

          this.changePasswordDialogVisible = false;

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  },
  computed: {
    formattedMoney() {
      // Reserve two decimal places for the balance
      let money = this.editForm.money;
      return '$'+parseFloat(money).toFixed(2);
    },
  }
}
</script>

<style scoped>
.el-card {
  width: 60%;
  margin: 0 auto;
}
.el-row {
  margin-bottom: 20px;
}
.profile-btn{
  width: 50%;
}
</style>
