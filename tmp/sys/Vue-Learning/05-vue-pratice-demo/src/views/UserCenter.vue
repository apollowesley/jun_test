<template>
  <div class="about">
    <h1>This is UserCenter.vue page</h1>
    <UserProfile v-bind="user"></UserProfile>
    <button @click="redirectToSendArticle">新添加博文</button>
  </div>
</template>
<script>
import storeUtil from '@/util/StoreageUtil';
import UserProfile from '@/components/UserProfile.vue';

export default {
  name: 'userCenter',
  computed: {
    user() {
      const loginUserName = sessionStorage.getItem('loginUserName');
      const user = storeUtil.getUser(loginUserName);
      return user;
    },
  },
  components: {
    UserProfile,
  },
  methods: {
    redirectToSendArticle() {
      this.$router.push('/articalSend');
    },
  },
};

</script>
