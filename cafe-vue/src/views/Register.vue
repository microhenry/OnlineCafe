<template>
  <body id="login-page">
  <div id="Register">

    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="0px" class="login-container" >
      <h3 class="login_title">Register</h3>
<!--      名字 prop是爆红的，v-model传参-->
      <el-form-item label="" prop="name">
        <el-input
                  v-model="ruleForm.name"
                  autocomplete="off"
                  placeholder="Name"
                  prefix-icon="el-icon-user"></el-input>
      </el-form-item>
      <!--      密码-->
      <el-form-item label="" prop="pass">
        <el-input type="password"
                  v-model="ruleForm.pass"
                  autocomplete="off"
                  placeholder="password"
                  prefix-icon="el-icon-lock"></el-input>
      </el-form-item>
      <!--      确认密码-->
      <el-form-item label="" prop="checkPass">
        <el-input type="password"
                  v-model="ruleForm.checkPass"
                  autocomplete="off"
                  placeholder="password check"
                  prefix-icon="el-icon-lock"></el-input>
      </el-form-item>
      <!--      电话号 -->
      <el-form-item label="" prop="phoneNumber">
        <el-input
                  v-model="ruleForm.phoneNumber"
                  autocomplete="off"
                  placeholder="phone number"
                  prefix-icon="el-icon-phone"></el-input>
      </el-form-item>

      <!--      邮箱 -->
      <el-form-item label="" prop="email">
        <el-input
                  v-model="ruleForm.email"
                  autocomplete="off"
                  placeholder="Email"
                  prefix-icon="el-icon-message"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">sign up</el-button>
        <el-button @click="resetForm('ruleForm')">reprovision</el-button>
      </el-form-item>
    </el-form>
  </div>
  </body>
</template>

<script>
export default {
  name: "Register",
  data() {
    var checkName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Empty name'));
      }
      setTimeout(() => {
          if (value.length >15 || value.length<3) {
            callback(new Error('name must between 3 and 15 chars'));
          } else {
            callback();
          }
      }, 1000);
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('please enter passnumber'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('please enter password again '));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('inconsistency between two password entries'));
      } else {
        callback();
      }
    };
    var validatePhoneNumber = (rule, value, callback)=>{
      console.log(value.length);
      if(value === ''){
        callback(new Error('please enter phone number'));
      }else if(value.length!==11){
        callback(new Error('please enter true 11-digit  phone-numbers'));
      }else {
        callback();
      }
    }
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
      ruleForm: {
        pass: '',
        checkPass: '',
        name: '',
        phoneNumber:'',
        email:'',
      },
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        name: [
          { validator: checkName, trigger: 'blur' }
        ],
        phoneNumber: [
          {validator:validatePhoneNumber,trigger:'blur'}
        ],
        email: [
          {validator:validateEail,trigger:'blur'}
        ]

      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('successful registration');
          this.$router.push({path:'/menu'});
        } else {
          alert('error happened,try again');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
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
