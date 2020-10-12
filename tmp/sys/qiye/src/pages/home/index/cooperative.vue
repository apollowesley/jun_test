<template>
  <div class="about" style="display: flex;flex-direction: column;align-items: center;justify-content: space-around;min-height:60vh;">
    <news-detail :screenWidth="newsDetailWidth"  :itemData="itemData"></news-detail>
  </div>
</template>
<script>
	import newsDetail from "../../../components/template/news_detail.vue";
export default {
  name: 'news',
	components: {
		newsDetail
	},
	computed:{
		screenWidth(){
			return this.$store.getters.screenWidth;
		},newsDetailWidth(){
			var screenWidth=this.screenWidth;
			if(screenWidth>1000){
				screenWidth=1000;
			}
			return screenWidth-this.marginWidth;
		},marginWidth(){
			var marginWidth=10;
// 			//外边距宽度像素
// 			if(this.screenWidth>700){
// 				marginWidth=700;
// 			}
			return marginWidth;
		}
	},
	watch:{
		
		$route(to,from){
			console.log(to.path);
		}
	},data(){
		return {
			// newsListWidth:100,
			itemData:{
					title:"我不是药神",
					abstract:"印度神油店老板程勇日子过得窝囊，店里没生意，老父病危，手术费筹不齐。前妻跟有钱人怀上了孩子，还要把他儿子的抚养权给拿走。一日，店里来了一个白血病患者，求他从印度带回一批仿制的特效药，好让买不起天价正版药的患者保住一线生机。百般不愿却走投无路的程勇，意外因此一夕翻身，平价特效药救人无数，让他被病患封为“药神”，但随着利益而来的，是一场让他的生活以及贫穷病患性命都陷入危机的多方拉锯战",
					images:[
						'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550830696537&di=9a5118351e9052be7a3d7a21c3612743&imgtype=0&src=http%3A%2F%2Fp1.meituan.net%2Fmovie%2F39b595c3eaad1ccf078caa6c89d05db42732032.jpg',
						'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550830008780&di=9c5ef27ff906b9679eec66ef8e07edb6&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn10%2F115%2Fw2048h2867%2F20180706%2F7eff-hexfcvk3715675.jpg',
						'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550829969838&di=36fcfa73f7758212c5bdffea393e00da&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F117%2Fw2048h2869%2F20180412%2F7c2f-fyzeyqc2155697.jpg'
						
					],
					content:"印度神油店老板程勇日子过得窝囊，店里没生意，老父病危，手术费筹不齐。前妻跟有钱人怀上了孩子，还要把他儿子的抚养权给拿走。一日，店里来了一个白血病患者，求他从印度带回一批仿制的特效药，好让买不起天价正版药的患者保住一线生机。百般不愿却走投无路的程勇，意外因此一夕翻身，平价特效药救人无数，让他被病患封为“药神”，但随着利益而来的，是一场让他的生活以及贫穷病患性命都陷入危机的多方拉锯战"
					}
		}
	}
}
</script>