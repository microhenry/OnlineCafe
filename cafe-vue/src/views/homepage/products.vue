<script setup>

</script>

<template>
  <el-tabs v-model="category" @tab-click="getProductMenuList" type="border-card" tab-position="left" >
    <el-tab-pane v-for="(tab, index) in tabs" :label="tab.label" :name="tab.name" :key="index">
      <el-empty :image-size="200" v-if="productMenuList.length === 0"></el-empty>
      <el-row :gutter="20" v-else class="scrollable-row">
        <el-backtop target=".scrollable-row" :visibility-height="100" />
        <el-col style="padding-top: 10px; padding-bottom: 10px" :span="5" v-for="(product, index) in productMenuList" :key="index" class="hover-up">
          <el-card :body-style="{ padding: '10px'}">
            <el-image
              style="width: 100%; height: 100%; cursor: pointer"
              fit="fill"
              :src="requireProductImage(product.productPicUrl)"
              @click.native="goToProductDetail(product.productName)"
            >
            </el-image>
            <div style="padding: 3px; min-height: 40px; align-items: center; display: flex; justify-content: center;" >
              <span class="bottom">{{ product.productName }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-tab-pane>
  </el-tabs>
</template>

<style scoped>
.scrollable-row {
  height: calc(100vh - 162px); /* Adjust the height */
  overflow-y: auto; /* Set vertical scroll */
}
.hover-up {
  transition: transform 0.2s ease-in;
}
.hover-up:hover {
  transform: translateY(-5px);
}
</style>

<script>
  import { productMenuList} from "../../api/productMenu";
  export default {
    data() {
      return {
        productMenuList: [],
        category: "all",
        tabs: [
          {
            label: "All Products",
            name: "all",
          },
          {
            label: "Featured Beverages",
            name: "Featured Beverages",
          },
          {
            label: "Barista Favourites",
            name: "Barista Favourites",
          },
          {
            label: "Hot Coffees",
            name: "Hot Coffees",
          },
          {
            label: "Cold Coffees",
            name: "Cold Coffees",
          },
          {
            label: "Coffee Blended Frappuccino",
            name: "Coffee Blended Frappuccino",
          },
          {
            label: "Cream Blended Frappuccino",
            name: "Cream Blended Frappuccino",
          },
          {
            label: "Hot Teas",
            name: "Hot Teas",
          },
          {
            label: "Iced Teas",
            name: "Iced Teas",
          },
          {
            label: "Other Hot Beverages",
            name: "Other Hot Beverages",
          },
          {
            label: "Other Cold Beverages",
            name: "Other Cold Beverages",
          },
        ],
      };
    },
    created() {
      this.getProductMenuList();
    },
    methods: {
      getProductMenuList() {
        productMenuList("", this.category)
          .then((res) => {
            if (res.data.code === 200) {
              this.productMenuList = res.data.data.records;
              console.log(this.productMenuList);
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
      goToProductDetail(productId) {
        // 使用 Vue Router 导航到商品详情页面，并传递商品的唯一标识
        console.log(productId);
        this.$router.push({ name: 'productDetail', params: { productId } });
      },
    },
  }
</script>
