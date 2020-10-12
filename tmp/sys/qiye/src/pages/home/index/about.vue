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
			return this.$store.getters.screenWidth;
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
			// newsListWidth:100,
			// itemData:[{
// 					"id":"4",
// 					title:"第一电影人",
// 					abstract:"印度神油店老板程勇日子过得窝囊，店里没生意，老父病危，手术费筹不齐。前妻跟有钱人怀上了孩子，还要把他儿子的抚养权给拿走。一日，店里来了一个白血病患者，求他从印度带回一批仿制的特效药，好让买不起天价正版药的患者保住一线生机。百般不愿却走投无路的程勇，意外因此一夕翻身，平价特效药救人无数，让他被病患封为“药神”，但随着利益而来的，是一场让他的生活以及贫穷病患性命都陷入危机的多方拉锯战",
// 					src:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551424786&di=01925c2563339b7b108a73f2be9a18da&imgtype=jpg&er=1&src=http%3A%2F%2Fd.ifengimg.com%2Fq100%2Fimg1.ugc.ifeng.com%2Fnewugc%2F20170819%2F19%2Fwemedia%2Fba2ce1f28360f15751aa4e06249aa7e4b57d731e_size1951_w1397_h2245.jpg'
// 			},
            // ]
		}
	},created() {
        this.getParams();

	},methods:{
        getParams(){
            // 取到路由带过来的参数
            var routerParams = this.$route.query
             // routerParams=this.routerParams;
            var category_id=routerParams.category_id||'0';
            Request('newsList',{params:{category_id:category_id}}).then(e=>{
                
                if(e.code=200){
                    var item=this.itemData;
                    this.data=e.data.data;
                    // console.log(e.data.data)
                }
                // console.log(e)
            })
        }
    }
}
</script>