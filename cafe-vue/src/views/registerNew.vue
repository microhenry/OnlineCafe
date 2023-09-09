<template>
  <body id="register-page">
  <!--内容主体区域-->
  <el-form :model="userForm" :rules="userRules" ref="userForm" label-width="0px" class="login-container">
    <h3 class="login_title">Register</h3>
    <el-form-item label="LoginName:" prop="loginName">
      <el-input
        type="text"
        v-model="userForm.loginName"
        auto-complete="off"
        placeholder="The name to login to your account"
        minlength="3"
        maxlength="15"
        show-word-limit
        clearable
      ></el-input>
    </el-form-item>
    <el-form-item label="UserName:" prop="userName">
      <el-input
        type="text"
        v-model="userForm.userName"
        auto-complete="off"
        placeholder="The name to display on your account"
        minlength="3"
        maxlength="15"
        show-word-limit
        clearable
      ></el-input>
    </el-form-item>
    <el-form-item label="Password:" prop="password">
      <el-input
        type="password"
        v-model="userForm.password"
        auto-complete="off"
        placeholder="Please enter your password"
        minlength="3"
        show-password
      ></el-input>
    </el-form-item>
    <el-form-item label="Gender:" prop="sex">
      <el-radio v-model="userForm.sex" label="Male">Male</el-radio>
      <el-radio v-model="userForm.sex" label="Female">Female</el-radio>
      <el-radio v-model="userForm.sex" label="Other">Other</el-radio>
    </el-form-item>
    <el-form-item label="Email:" prop="email">
      <el-input v-model="userForm.email" placeholder="Please enter your email address"></el-input>
    </el-form-item>
    <el-form-item label="Address:" prop="address">
      <el-input v-model="userForm.address" placeholder="Please enter your physical address"></el-input>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="addUser('userForm')">Register</el-button>
      <el-button @click="resetForm('userForm')">Clear</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
import { userLogin } from "@/api/user";
import { userList, userAdd } from "@/api/userNew";

export default {
  name: "Login",
  data() {
    var validateEail = (rule, value, callback)=>{
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
      if (!value) {
        return callback(new Error('please enter email'))
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          callback()
        } else {
          callback(new Error('please enter true email'))
        }
      }, 100)
    }
    return {
      userList: [], // 用户列表
      userForm: {
        //用户
        loginName: "",
        userName: "",
        password: "",
        sex: "",
        email: "",
        address: "",
      },
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
        email : [
          { validator:validateEail, required: true, message: 'Wrong Email Format!', trigger: 'change' }
        ],
        address : [
          { required: true, message: 'Address cannot be empty!', trigger: 'change' }
        ]
      },
      responseResult: [],
    };
  },
  created() {
    // 生命周期函数
    this.getUserList();
  },
  methods: {
    getUserList() {
      userList(this.queryInfo)
        .then((res) => {
          if (res.data.code === 200) {
            //用户列表
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
    addUser(formName) {
      console.log(formName)
      this.$refs[formName].validate((valid) => {
        console.log(valid)
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
                this.$message.error("E01: Failed to add user.");
              }
            })
            .catch((err) => {
              this.$message.error("E02: Failed to add user.");
              console.log(err);
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },

};
</script>

<style scoped>
#register-page {
  background: url("../assets/img/bg_login.jpg") no-repeat center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  top: 0;
  left: 0;
}
body {
  margin: 0;
}
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 10px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0 auto 0px auto;
  text-align: center;
  color: #505458;
}
</style>


