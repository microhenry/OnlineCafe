<template>
  <el-row>
    <el-col :span="8" :key="o">
      <el-card :body-style="{ padding: '0px' }">
        test
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card :body-style="{ padding: '0px' }">
        test
      </el-card>
    </el-col>
  </el-row>
</template>

<style>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>

<script>
export default {
  data() {
    return {
      currentDate: new Date(),
    };
  },
};
</script>
