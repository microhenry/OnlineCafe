<template>
  <body id="register-page">
  <!--内容主体区域-->
  <el-form :model="userForm" :rules="userRules" ref="userForm" label-width="auto" class="login-container">
    <div class="login-reg-header">
      <el-button
        icon="el-icon-back"
        size="small"
        circle
        class="back-btn"
        @click="back"
      ></el-button>
      <h2 class="register_title">Register</h2>
    </div>
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

    <el-row style="margin-bottom: 10px">
      <el-button style="width: 40%" type="primary" @click="addUser('userForm')">Register</el-button>
      <el-button style="width: 40%" type="danger" @click="resetForm('userForm')">Clear</el-button>
    </el-row>
    <el-row style="line-height: 25px">
      <span style="font-size: 16px">Already have an account? </span>
      <el-link type="primary" :underline="false" @click="SignIn" style="font-size: 16px">Login</el-link>
      <span style="font-size: 16px"> to your account.</span>
    </el-row>
  </el-form>
  </body>
</template>

<script>
import { userAdd } from "@/api/userNew";

export default {
  name: "Register",
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
    //this.getUserList();
  },
  methods: {
    // getUserList() {
    //   userList(this.queryInfo)
    //     .then((res) => {
    //       if (res.data.code === 200) {
    //         //用户列表
    //         this.userList = res.data.data.records;
    //         this.total = res.data.data.total;
    //       } else {
    //         this.$message.error(res.data.message);
    //       }
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
    addUser(formName) {
      console.log(formName)
      this.$refs[formName].validate((valid) => {
        console.log(valid)
        if (valid) {
          userAdd(this.userForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.addDialogVisible = false;
                //this.getUserList();
                this.$message({
                  message: "Registration successfully.",
                  type: "success",
                },
                this.$router.push({path:'/login'}));
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
    },
    SignIn(){
      this.$router.push({path:'/login'})
    },
    back(){
      this.$router.back();
      console.log('back')
    },
  },

};
</script>

<style scoped>
#register-page {
  background: url("../../assets/img/bg_login.jpg") no-repeat center;
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
  margin: 70px auto;
  width: 450px;
  padding: 35px 35px 35px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.login-reg-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  position: relative;
}
.back-btn {
  position: absolute;
}
.register_title {
  margin: 0 auto 10px auto;
  text-align: center;
  color: #505458;
  display: inline;
  position: relative;
}
</style>


