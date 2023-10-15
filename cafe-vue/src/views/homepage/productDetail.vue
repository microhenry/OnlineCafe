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
                <el-radio-button label="small">Small</el-radio-button>
                <el-radio-button label="medium">Medium</el-radio-button>
                <el-radio-button label="large">Large</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <el-input-number v-model="orderForm.num" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
          <el-button type="primary">Add to Cart</el-button>
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
        productPrice: 0,
        productRate: 0,
      },
      orderForm: {
        size: "medium",
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
            this.productDetail.productPrice = 7.5;
            this.productDetail.productRate = 4.5;
            console.log(this.productDetail.productDescription);
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
  }
}
</script>
