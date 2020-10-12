<template>
	  <el-carousel    style="flex:1;width: 100%;" :interval="4000" arrow="always" :indicator-position="screenMode?'outside':'none'" :type="screenMode?'card':''" :height="imgHeight+'px'" >
			<el-carousel-item v-for="(item,key) in itemData"  :key="key"  style="display: flex;justify-content: center;" >
					<img @click="dialog(item)" mode="cover" :style="'height:'+imgHeight+'px;width:'+imgWidth+'px;'" style="box-sizing: border-box;padding: 10px;" :src="item.image" />
			</el-carousel-item>
		</el-carousel>
        
<!--    <el-carousel :interval="4000"    arrow="always" :indicator-position="screenMode?'outside':'none'" :type="screenMode?'card':''" :height="imgHeight+'px'">
        <el-carousel-item v-for="item in 6" :key="item"  :style="'height:'+imgHeight+'px;'" style="display: flex;justify-content: center;">
          <img mode="cover" :style="'height:'+imgHeight+'px;width:'+imgWidth+'px;'" style="box-sizing: border-box;padding: 10px;" :src="item.src"/>
        </el-carousel-item>
    </el-carousel> -->
</template>
 <style>
  .el-carousel__item img {

  }
  
  .el-carousel__item:nth-child(2n) {
    /* background-color: #99a9bf; */
  }
  
  .el-carousel__item:nth-child(2n+1) {
    /* background-color: #d3dce6; */
  }
</style>
 
<script>
  export default {
		props:{
      itemData:{
        type: Array
      },
      screenWidth:{
        default: 500,
        type:Number
      },
      dialog:{
        default:function(e){
           var url =e.url;
           if(url.slice(0, 4) != "http"){
            this.$router.push({
              path:e.url
            })
          }else{
            window.open(url); 
          }
        },
        type: Function
      }
      },
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

    },computed:{
      screenMode(){
          return this.$store.getters.screen.mode
      },
			imgHeight(){
				return parseInt(this.imgWidth*1.4);
			},
			imgWidth(){
				var imgWidth=this.screenWidth;
				if(this.screenMode){
					imgWidth=parseInt(imgWidth/3.6)
				}
				return imgWidth;
			}
	},
    methods: {
      
    }
 
  }
</script>
 