<template>
	<div :style="'width:'+screenWidth+'px'" style="background: #fff;padding: 30px;box-sizing: border-box;"  >
	<div v-if="itemData.images">
        <div  v-if="typeof itemData.images==='string' && itemData.images.length>5" style="box-sizing: border-box;" >
        		<img mode="cover" :style="'height:'+imgHeight+'px;width:100%'" style="box-sizing: border-box;padding: 10px;" :src="itemData.images">
        </div>
          <el-carousel  v-else-if="typeof itemData.images==='object'"  style="flex:1;width: 100%;" :interval="4000" arrow="always" :indicator-position="screenMode?'outside':'none'"  :height="imgHeight+'px'" >
        		<el-carousel-item  v-for="(item,key) in itemData.images" style="" :key="key">
        				<img mode="cover" :style="'height:'+imgHeight+'px;width:width:100%'" style="box-sizing: border-box;padding: 10px;" :src="item.src">
        		</el-carousel-item>
          </el-carousel>
    </div>

	  <div style="width: 100%;">
		  <h3>{{itemData.title}}</h3>
		  <div>{{itemData.time}}</div>
		  <div style="text-align: left;text-indent:35px">{{itemData.content}}</div>
	  </div>
	</div>
</template>
 
 
<script>
  export default {
        name:"newsDetail",
		props:['itemData','screenWidth'],
    data() {
      return {
				// imgType:"",
// 					itemData:[{
// 							title:"第一电影人",
// 							src:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550830696537&di=9a5118351e9052be7a3d7a21c3612743&imgtype=0&src=http%3A%2F%2Fp1.meituan.net%2Fmovie%2F39b595c3eaad1ccf078caa6c89d05db42732032.jpg'
// 							},{
// 							title:"第一电影人",
// 							src:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550829969838&di=36fcfa73f7758212c5bdffea393e00da&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F117%2Fw2048h2869%2F20180412%2F7c2f-fyzeyqc2155697.jpg'
// 							},{
// 							title:"第一电影人",
// 							src:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550830008780&di=9c5ef27ff906b9679eec66ef8e07edb6&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn10%2F115%2Fw2048h2867%2F20180706%2F7eff-hexfcvk3715675.jpg'
// 							},{
// 							title:"第一电影人",
// 							src:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551424786&di=01925c2563339b7b108a73f2be9a18da&imgtype=jpg&er=1&src=http%3A%2F%2Fd.ifengimg.com%2Fq100%2Fimg1.ugc.ifeng.com%2Fnewugc%2F20170819%2F19%2Fwemedia%2Fba2ce1f28360f15751aa4e06249aa7e4b57d731e_size1951_w1397_h2245.jpg'
// 					}]
      };
    },
 
    mounted() {
        console.log(this.$config)
    },computed:{
            
			imgHeight(){
				return parseInt(this.imgWidth/1.8);
			},
			imgWidth(){
				var imgWidth=this.screenWidth;
				if(this.screenMode){
					imgWidth=parseInt(imgWidth)
				}
				return imgWidth;
			},
			screenMode(){
				return this.$store.getters.screenMode;
			}
	},
    methods: {
        
    }
 
  }
</script>
 
 
<style>

/* 图片轮播 */
/* .el-carousel{
		background:#FF7F24;
} */
	
/*  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  } */
</style>