<script setup>

</script>

<template>
  <div>
    <el-page-header @back="goBack" :content="productParamName"></el-page-header>
    <el-row class="productRow">
      <el-col :span="10" class="productColImg">
        <el-image
          style="width: 70%; height: 70%;"
          fit="fill"
          :src="requireProductImage(productDetail.productPicUrl)"
          :preview-src-list="[requireProductImage(productDetail.productPicUrl)]"
        ></el-image>
        <h3>Category: <el-tag :style="getCategoryClass(productDetail.productCategory)">{{ productDetail.productCategory}}</el-tag></h3>
      </el-col>
      <el-col :span="14" class="productColInfo">
        <el-card>
          <h1>{{ productDetail.productName }}</h1>
          <p>{{ productDetail.productDescription }}</p>
          <h2>${{ productDetail.productPrice }} / cup</h2>

          <el-rate
            v-model="productDetail.productRate"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}">
          </el-rate>
        </el-card>
        <el-row>
          <el-form ref="orderForm" :model="orderForm">
            <h2>Customize:</h2>
            <el-form-item>
              <el-radio-group v-model="orderForm.size" fill="#33ccff">
                <el-radio-button label="S">Small</el-radio-button>
                <el-radio-button label="M">Medium</el-radio-button>
                <el-radio-button label="L">Large</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <el-input-number v-model="orderForm.num" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
          <el-button type="primary" @click="addToCart">Add to Cart</el-button>
        </el-row>

      </el-col>
    </el-row>

  </div>

</template>

<style scoped>
.productRow {
  padding: 20px;
}
.productColImg {
  justify-content: center;
  align-items: center;
}

.productColInfo > :first-child {
  background: #f3f3f3;
}
</style>

<script>
import { getProductDetail } from "../../api/productMenu";
import {addCart} from "../../api/cart";
import homepage from './homepage.vue';
export default {
  data() {
    return {
      productParamName: "",
      productDetail: {
        productId: "",
        productName: "",
        productDescription: "",
        productCategory: "",
        productPicURL: "",
        productPrice: "",
        productRate: 0,
      },
      cartForm: {
        productId: "",
        productNum: "",
        userId: "",
        productName: "",
        productSize: "",
      },
      orderForm: {
        size: "M",
        num: 0,
      },
      categoryColors: {
        "Featured Beverages": "color: #fff; background-color: #ff3366;",
        "Barista Favourites": "color: #fff; background-color: #ff9966;",
        "Hot Coffees": "color: #fff; background-color: #ff9966;",
        "Cold Coffees": "color: #fff; background-color: #ff9966;",
        "Coffee Blended Frappuccino": "color: #fff; background-color: #ff9966;",
        "Cream Blended Frappuccino": "color: #fff; background-color: #ff9966;",
        "Hot Teas": "color: #fff; background-color: #33cc00;",
        "Iced Teas": "color: #fff; background-color: #33cc00;",
        "Other Hot Beverages": "color: #fff; background-color: #ff3366;",
        "Other Cold Beverages": "color: #fff; background-color: #ff3366;"
      },
    };
  },
  computed: {

  },
  created() {
    this.productParamName = this.$route.params.productId;
    this.getProduct();
  },
  methods: {
    goBack() {
      this.$router.back();
    },
    getProduct() {
      getProductDetail("", this.productParamName)
        .then((res) => {
          if (res.data.code === 200) {
            this.productDetail = res.data.data;
            //TODO: following value should get from backend
            this.productDetail.productRate = 4.5;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    requireProductImage(productPicUrl) {
      try {
        // Try to require the image
        return require(`../../assets/img/products/${productPicUrl}.jpg`);
      } catch (e) {
        // If it fails, return the default image
        return require("../../assets/img/default.jpg");
      }
    },
    handleChange(val) {
      console.log(val);
    },
    getCategoryClass(category) {
      console.log(this.categoryColors[category]);
      return this.categoryColors[category];
    },
    addToCart(){
      console.log(this.isLoggedIn());
      if (this.isLoggedIn()) {
        this.cartForm.userId = this.$store.state.user.id;
        this.cartForm.productId = this.productDetail.productId;
        this.cartForm.productNum = this.orderForm.num;
        this.cartForm.productSize = this.orderForm.size;
        this.cartForm.productName = this.productDetail.productName;
        addCart(this.cartForm)
          .then((res) => {
            if (res.data.code === 200) {
              this.callGetCartNumber();
              this.$message({
                showClose: true,
                message: 'Add to cart successfully.',
                center: true,
                type: 'success'
              });
            } else {
              this.$message.error(res.data.message);
            }
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        console.log("Please login first.");
        this.$router.push({
          path: '/login',
          query: {
            redirect: this.$route.fullPath // 将当前路径作为参数传递给登录页面
          }
        });
        this.$message({
          type: 'info',
          message: 'Please login first.'
        });
      }
    },
    isLoggedIn() {
      console.log(this.$store.state);
      // 使用$store来检测用户是否登录
      if(!this.$store.state.token || this.$store.state.user === undefined || this.$store.state.user === ''
        || this.$store.state.isStaff === undefined || this.$store.state.isStaff === true ) {
        console.log("未登录");
        // 登录状态：未登录
        return false;
      } else {
        return true;
      }
      // return !!this.$store.state.token;
    },
    callGetCartNumber() {
      homepage.methods.getCartNumber.call(this);
    },
  }
}
</script>
