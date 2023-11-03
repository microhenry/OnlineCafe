<template>
  <el-container class="home-container">
    <el-container>

      <el-aside width="10%">
        <el-menu
          :default-active="$route.path"
          router
          text-color="black"
          active-text-color="#3399ff"
        >
          <el-menu-item
            v-for="(item, i) in navList"
            :key="i"
            :index="item.name"
          >
            <i :class="item.icon"></i>
            {{ item.title }}
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>

        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      navList: [
        { name: "/userpanel/profile", title: "My Profile", icon: "el-icon-user" },
        { name: "/userpanel/cart", title: "My Cart",icon:"el-icon-shopping-cart-full" },
        { name: "/userpanel/order", title: "My Order",icon:"el-icon-tickets" },
      ],
    };
  },
  methods: {
    logout() {
      this.$router.push({path: '/loginstaff'});
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

</style>

