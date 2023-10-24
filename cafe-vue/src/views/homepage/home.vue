<script setup>

</script>

<template>
  <el-col>
    <el-row>
      <el-carousel :interval="4000" type="card" height="360px">
        <el-carousel-item v-for="item in 6" :key="item">
          <h3 class="medium">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </el-row>

    <el-row class="homeRow">
      <el-col :span="12" class="homeColImg">
        <el-image
          :src="requireProductImage()"
        >
        </el-image>
      </el-col>
      <el-col :span="12">
        <h2>Title</h2>
        <p>Content</p>
      </el-col>
    </el-row>
  </el-col>
</template>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.homeRow {
  padding: 20px;
  height: 400px;
  background-color: #f3f3f3;
}

.homeColImg {
  justify-content: center;
  align-items: center;
}
</style>

<script>
export default {
  methods: {
    requireProductImage(picUrl) {
      try {
        // Try to require the image
        return require(`../../assets/img/banners/${picUrl}.jpg`);
      } catch (e) {
        // If it fails, return the default image
        return require("../../assets/img/default.jpg");
      }
    },
  }
}
</script>
