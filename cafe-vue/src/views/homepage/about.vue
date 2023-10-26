<script setup>

</script>

<template>
  <div >
    <el-card class="about-card">
      <div class="About_title">
      <h2 style=" text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);">Co-Coffee in Auckland</h2>
      <p style=" text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.8);">
        We hope to bring the unique and innovative coffee experience of Co-Coffee to our customers in every cup of coffee!
      </p>
      </div>
      <el-row>
        <el-col :span="12">
          <div class="image-container">
            <img class="rotating-image" src="../../assets/img/joinus.jpg" alt="Your Image">
          </div>
          <h3 class="biaoti">Our Mission and Values</h3>
          <p>
            Inspiring and nurturing the spirit of innovation, per person, per cup, per community
          </p>
          <p class="content">
            With our partners, coffee and customers at our core, we endeavour to live the following values:
          </p>
           <p class="content">
          •Create a warm and belonging culture that gladly accepts and happily welcomes everyone.
            </p>
          <p class="content">
        •Act positively, break stereotypes, and innovate to grow the company and our partners together.
          </p>
          <p class="content">
          •In every moment we connect with each other, we are focused and committed, open and honest, with mutual respect and honour.
          </p>
          <p class="content">
           •In everything we do, we do our best, do our best, and take responsibility.
          </p>
        </el-col>
        <el-col :span="12" class="limitheight">
          <div class="right-image-coinater">
            <img class="right-picture" src="../../assets/img/coffeeBean.jpg" alt="Your Image">
            <p class="des">The coffee maker is not the only factor in making great coffee. The four elements of coffee making are proportion, grind, water and freshness.</p>
          </div>
          <div class="right-image-coinater">
            <img class="right-picture" src="../../assets/img/shouchong.jpg" alt="Your Image">
            <p class="des">With this simple and easy-to-use method, you can get a cup of coffee that is rich and full-bodied, with a long aftertaste.</p>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<style scoped>
.about-card{
  width: 80%;
  margin: 0 auto;
  background-color: #EDEBE9;
}

.About_title{
  background-image: url("../../assets/img/background.jpg");
  background-size: cover; /* 控制背景图片的大小，cover 会填充整个 div */
  background-position: center; /* 控制背景图片的位置，这里将其居中 */
  color: white;
  text-align: center;
  height: 150px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.biaoti{
  text-shadow:  1px 1px 1px #fff,
  -1px -1px 1px #000;
  color: #000;
  font-size: 35px;
}
.image-container{
  width: 200px; /* 设置容器宽度 */
  height: 200px; /* 设置容器高度，以创建一个圆形容器 */
  border-radius: 50%; /* 将容器变为圆形 */
  overflow: hidden; /* 隐藏图片溢出的部分 */
  transition: transform 0.5s;
  margin-top: 10px;
  margin-left: 30%;
}
.rotating-image {
  width: 100%; /* 图片宽度占满容器 */
  height: 100%; /* 图片高度占满容器 */
}

.image-container:hover {
  transform: rotate(90deg); /* 鼠标悬停时旋转容器 */
}
.content{
  text-align: left;
  margin-top: -1px;
}
/**
* 这是设置右边的元素
*/
.limitheight{
  height: 700px;
}
.right-image-coinater{
  margin-top: 3%;
  margin-left: 10%;
  height:45%; /* 设置图片容器的高度为 el-col 的 33% */
  width: 80%;
  background: #ffffff;
  border-radius: 5px;
  overflow: hidden;
  box-shadow: 5px 5px 5px 2px hsl(0, 0%, 50%);
}
.des{
  padding: 1%;
}

.right-picture{

  width: 100%; /* 图片宽度占满容器 */
  height: 70%; /* 图片高度占满容器 */
  object-fit: cover; /* 保持图片的纵横比并填充容器 */
  transition: transform 1s; /* 添加变换效果 */
  /* 默认缩放为正常大小 */
  transform: scale(1);
}
.right-picture:hover {
  /* 鼠标悬停时放大 1.1 倍 */
  transform: scale(1.2); /* 可根据需要调整放大的程度 */
  transform-origin: center center;
}

</style>
