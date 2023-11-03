<script setup>

</script>

<template>
  <el-result icon="error" title="404" subTitle="Sorry, page not found.">
    <template #extra>
      <el-button type="primary" @click="$router.push({path: '/'})">Home</el-button>
    </template>
  </el-result>
</template>

<style scoped>

</style>
