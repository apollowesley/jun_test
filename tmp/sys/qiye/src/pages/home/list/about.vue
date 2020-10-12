<template>
  <div class="about" style="display: flex;flex-direction: column;align-items: center;justify-content: space-around;min-height: 80vh;">
    <template-news-list :screenWidth="newsListWidth" :widthNum="1" :itemData="itemData"></template-news-list>
  </div>
</template>
<script>
    import templateNewsList from "../../../components/template/news_list.vue";
    import Request from "../../../request/index.js";
export default {
  name: 'news',
	components: {
		templateNewsList
	},
	computed:{
        itemData(){
         var data= this.data;
          return data;
        },
		screenWidth(){
			return this.$store.getters.screen.width;
		},newsListWidth(){
			var screenWidth=this.screenWidth;
			if(screenWidth>1000){
				screenWidth=1000;
			}
			return screenWidth-this.marginWidth;
						// return this.screenWidth-this.marginWidth;
		},marginWidth(){
			var marginWidth=10;
			return marginWidth;
		}
	},
	watch:{
          $route(e){
              // 将数据放在当前组件的数据内
              console.log("传来的参数newsList=="+JSON.stringify(this.routerParams))
              if(e.path=='/news_list'){
                  this.getParams();
              }
          }  
	},data(){
		return {
            routerParams:{},
            data:[],
		}
	},created() {
        this.getParams();

	},methods:{
        getFileUrl(url,type){
                return this.$config.getFileUrl(url,type);   
        },
        getParams(){
            // 取到路由带过来的参数
            var routerParams = this.$route.query
            // 将数据放在当前组件的数据内
            var category_id=routerParams.id||'0';
            Request('newsList',{params:{category_id:category_id}}).then(e=>{
                if(e.code=200){
                    // var item=this.itemData;
                    var data=e.data.data;
                    for(let i in data){
                        if(data[i].image){
                            data[i].image=this.getFileUrl(data[i].image,'image');
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