<template>
  <div>
    <h2>商品列表</h2>
    <ul>
      <li :key="item.name" v-for="item of list">
        <nuxt-link :to="'/detail/' + item.id" >{{item.name}}</nuxt-link> 
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      list: [
        {id: "1", name: "iphone100", price: 20000 },
        {id: "2", name: "xiaomi100", price: 8000 },
        {id: "3", name: "huawei100", price: 18000 }
      ]
    };
  }
};
</script>

<style>
</style>