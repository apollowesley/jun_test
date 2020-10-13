<template>
  <div class="app-container">
    <iframe :src="druidUrl" frameborder="0" height="500" width="100%"/>
  </div>
</template>

<script>

export default{
  name: 'Druid',
  data() {
    return {
      druidUrl: process.env.BASE_API + '/monitor/druid/index.html'
    }
  }
}
</script>
