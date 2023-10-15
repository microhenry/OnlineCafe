<script setup>

</script>

<template>
  <div class="index">
    <el-container>
      <el-header>
        <el-menu
          :default-active="$route.path"
          router
          text-color="black"
          active-text-color="red"
          mode="horizontal"
          style="display: flex;"
        >
          <el-menu-item
            v-for="(item, i) in navList"
            :key="i"
            :index="item.name"
          >
            <i :class="item.icon"></i>
            {{ item.title }}
          </el-menu-item>
          <div class="button-container">
            <el-button v-if="!isLoggedIn" type="primary" class="login-btn" @click="login">Login</el-button>
            <el-button v-if="!isLoggedIn" class="login-btn" @click="signup">Sign up</el-button>
            <span v-if="isLoggedIn" class="user-title" style="margin-right: 5px">Welcome,</span>
            <el-popover
              placement="bottom"
              trigger="hover">
              <span>Balance: <span style="color: red">{{formattedMoney}}</span></span>
              <span v-if="isLoggedIn" class="user-title" style="margin-right: 10px; color: dodgerblue" slot="reference">
            {{ this.$store.state.user.loginName }}
            </span>
            </el-popover>
            <el-button v-if="isLoggedIn" type="danger" class="logout-btn" @click="logout">Logout</el-button>
          </div>
        </el-menu>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer>
        © 2023 Online Coffee Shop. All rights reserved.
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  computed: {
    isLoggedIn() {
      // 使用$store来检测用户是否登录
      return !!this.$store.state.token;
    },
    formattedMoney() {
      const money = this.$store.state.user.money;
      // 使用 toFixed 方法为余额保留两位小数
      return '$'+parseFloat(money).toFixed(2);
    },
  },
  data() {
    return {
      navList: [
        { name: "/home", title: "Home", icon: "el-icon-s-home" },
        { name: "/products", title: "Products", icon: "el-icon-goods" },
        { name: "/about", title: "About", icon: "el-icon-help" },
      ],
    };
  },
  methods: {
    login() {
      this.$router.push({path: '/login'});
    },
    signup() {
      this.$router.push({path: '/register'});
    },
    logout() {
      // this.$router.replace({path: '/login'});
      this.$store.commit('REMOVE_INFO');
      this.$message({
        showClose: true,
        message: 'Logout successfully.',
        center: true,
        type: 'success'
      });
    }
  }
};
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: flex-end; /* 右对齐 */
  align-items: center; /* 垂直居中对齐，可选 */
  flex: 1;
}

.index {
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
}

.el-container {
  width: 100%;
  min-height: 100%;
  display: flex;
  flex-direction: column;
  justify-content:space-between;
}

.el-main {
  height: 100%;
  overflow: hidden;
}

.el-footer {
  display: flex;
  height: 30px !important;
  width: 100%;
  align-items: center;
  justify-content: center;
  background-color: #333333;
  color: #ffffff;
}

</style>
