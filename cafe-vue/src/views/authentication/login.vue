<template>
  <body id="login-page">
  <el-form class="login-container" label-width="auto" :model="loginForm" :rules="rules" ref="loginForm">
    <div class="login-reg-header">
      <el-button
        icon="el-icon-back"
        size="small"
        circle
        class="back-btn"
        @click="back"
      ></el-button>
      <h2 class="login_title">Login</h2>
    </div>
    <el-form-item prop="loginName">
      <el-input
        type="text"
        v-model="loginForm.loginName"
        auto-complete="off"
        placeholder="login name"
        clearable
      ></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
        type="password"
        v-model="loginForm.password"
        auto-complete="off"
        placeholder="password"
        show-password
      ></el-input>
    </el-form-item>
    <el-row style="margin-bottom: 10px">
      <el-button
        type="primary"
        style="width: 80%"
        @click="login('loginForm')"
      >Login</el-button
      >
    </el-row>
    <el-row style="line-height: 25px">
      <span style="font-size: 16px">Do not have an account? </span>
      <el-link type="primary" :underline="false" @click="SignUp" style="font-size: 16px">Register</el-link>
      <span style="font-size: 16px"> a new one.</span>
    </el-row>
    <el-row style="line-height: 25px">
      <el-link type="primary" :underline="false" @click="SignInStaff" style="font-size: 14px">Staff Login</el-link>
    </el-row>
  </el-form>
  </body>
</template>

<script>
import { userLogin } from "@/api/user";

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        loginName: "",
        password: "",
      },
      rules: {
        loginName :[
          { required: true, message: 'Please input your login name.', trigger: 'blur' }
        ],
        password :[
          { required: true, message: 'Please input your password.', trigger: 'blur' }
        ]
      },
      responseResult: [],
    };
  },
  methods: {
    login (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var _this = this;
          userLogin({
            loginName: this.loginForm.loginName,
            password: this.loginForm.password,
          }).then((resp) => {
            let code=resp.data.code;
            if(code===200){
              let data=resp.data.data;
              let token=data.token;
              let user=data.user;
              // Store token
              _this.$store.commit('SET_TOKENN', token);
              // Store user info
              _this.$store.commit('SET_USER', user);
              var path = this.$route.query.redirect
              this.$router.push({path: path === '/' || path === undefined ? '/' : path})
              this.$message({
                showClose: true,
                message: 'Login successfully.',
                center: true,
                type: 'success'
              });
            } else {
              this.$message({
                showClose: true,
                message: 'Incorrect username or password.',
                center: true,
                type: 'error'
              });
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    SignUp(){
        this.$router.push({path:'/register'})
    },
    SignInStaff(){
      this.$router.push({path:'/loginstaff'})
    },
    back(){
      // Use the router to return to the previous page
      this.$router.back();
    },
  },

};
</script>

<style scoped>
#login-page {
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
  margin: 90px auto;
  width: 350px;
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
.login_title {
  margin: 0 auto 10px auto;
  text-align: center;
  color: #505458;
  display: inline;
  position: relative;
}
</style>


