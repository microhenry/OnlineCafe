<script setup>

</script>

<template>
  <el-col>
    <el-row>
      <el-carousel :interval="4000" type="card" height="360px">
        <el-carousel-item v-for="item in 6" :key="item" class="carousel-item">
          <img :src="require(`@/assets/img/banners/${item}.jpg`)">
        </el-carousel-item>
      </el-carousel>
    </el-row>

    <el-row class="homeRow">
      <el-col :span="12" class="homeColImg">
        <el-image
          :src="requireProductImage('logo')"
        >
        </el-image>
      </el-col>
      <el-col :span="12">
        <h2 class="Title">Co-coffee Shop</h2>
        <p>Welcome to our online Coffee Shop!<br/>
          We are trying to do the best Online Coffee Shop in Auckland. In our Coffee Shop, you can find all the coffee and other drinks you want.
          We use the latest grinding technology to create new coffee flavours and cutting-edge packaging styles.
         <br/> <br/>We guarantee you the most unique online shopping experience and have our own delivery staff to deliver your drinks to you as soon as possible!
          </p>
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
 margin-top: -6%;
}

.Title{
  font-family: "Brush Script MT", serif;
  color: #6F4E37;
  font-size: 250%;

}
.carousel-item img {
  object-fit: cover;
  width: 100%;
  height: 100%;
}
</style>

<script>
export default {
  methods: {
    requireProductImage(picUrl) {
      try {
        // Try to require the image
        return require(`../../assets/img/banners/${picUrl}.png`);
      } catch (e) {
        // If it fails, return the default image
        return require("../../assets/img/default.jpg");
      }
    },
  }
}
</script>
