<template>
  <body id="login-page">
  <el-form class="login-container" label-position="left" label-width="0px" :model="loginForm" :rules="rules" ref="loginForm">
    <h3 class="login_title">Login</h3>
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
    <el-form-item style="width: 100%">
      <el-button
        type="primary"
        style="width: 100%;  border: none"
        @click="login('loginForm')"
      >login</el-button
      >
    </el-form-item>
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
            console.log(code)
            if(code===200){
              let data=resp.data.data;
              let token=data.token;
              let user=data.user;
              //存储token
              _this.$store.commit('SET_TOKENN', token);
              //存储user，优雅一点的做法是token和user分开获取
              _this.$store.commit('SET_USER', user);
              console.log(_this.$store.state.token);
              var path = this.$route.query.redirect
              this.$router.replace({path: path === '/' || path === undefined ? '/' : path})
              console.log('success')
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
    }
  },
};
</script>

<style scoped>
#login-page {
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
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0 auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>


