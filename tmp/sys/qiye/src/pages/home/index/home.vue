<template>
		<ad-max :itemData="itemData" :screenWidth="screenWidth" :dialog="dialog"></ad-max>
</template>
 
 
<script>
    import adMax from "../../../components/template/adMax.vue";
    import Request from "../../../request/index.js";
  export default {
	components: {
		adMax
	},watch:{
		$router(to,from){
			// console.log(to.path);
		}
	},
    data() {
      return {
				// windowMode:true,
				data:[{
					title:"第一电影人",
					image:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550830696537&di=9a5118351e9052be7a3d7a21c3612743&imgtype=0&src=http%3A%2F%2Fp1.meituan.net%2Fmovie%2F39b595c3eaad1ccf078caa6c89d05db42732032.jpg'
					},{
					title:"第一电影人",
					image:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550829969838&di=36fcfa73f7758212c5bdffea393e00da&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F117%2Fw2048h2869%2F20180412%2F7c2f-fyzeyqc2155697.jpg'
					},{
					title:"第一电影人",
					image:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550830008780&di=9c5ef27ff906b9679eec66ef8e07edb6&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn10%2F115%2Fw2048h2867%2F20180706%2F7eff-hexfcvk3715675.jpg'
					}]
      };
    },
 
    mounted() {
      this.getParams();
    },computed:{
      itemData(){
        return this.data;
      },
			screenMode(){
				return this.$store.getters.screen.mode;
			},
			screenWidth(){
				return this.$store.getters.screen.width;
			},
		},
    methods: {
      dialog(e){
        var url =e.url;
         if(url.slice(0, 4) != "http"){
          this.$router.push({
            path:e.url
          })
        }else{
          window.open(url); 
        }
      },
      getParams(){
          // 取到路由带过来的参数
          var routerParams = this.$route.query
           // routerParams=this.routerParams;
          var category_id=routerParams.category_id||'0';
          Request('adList',{params:{category_id:category_id}})
          .then(e=>{
              // console.log(e)
              if(e.code=200){
                 var data=e.data.data;
                  for (let p in data) {
                  	if(data[p].image){
                       data[p].image= this.$config.getFileUrl(data[p].image,'image');   
                    }
                  }
                  this.data=data;
              }
              // console.log(e)
          })
      }
    }
 
  }
</script>
 
 
<style>

</style>