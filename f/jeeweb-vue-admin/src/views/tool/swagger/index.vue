<template>
  <div class="app-container">
    <iframe :src="swaggerUrl" frameborder="0" height="500" width="100%"/>
  </div>
</template>

<script>

export default{
  name: 'Swagger',
  data() {
    return {
      swaggerUrl: process.env.BASE_API + '/swagger-ui.html'
    }
  }
}
</script>
