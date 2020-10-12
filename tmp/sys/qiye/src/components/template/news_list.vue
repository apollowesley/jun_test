<template>
	<div  class="yc-flex" style="justify-content:space-around;flex-wrap: wrap;box-sizing: border-box;" :style="'width:'+screenWidth+'px'">
    <div  class="yc-card" v-for="(item, index) in newData" :key="index" style="padding-left: 12px;padding-right: 12px; margin-top:10px;box-sizing: border-box;" :style="'width:'+cardWidth+'px'">
      <!-- <div style="box-sizing: border-box;padding-left: 14px;flex:2;display: flex;flex-direction: column;align-items: flex-start;" > -->
				
        <!-- <div style="">{{item.title}}</div> -->
				<p style="width: 100%;text-align:left;height: 100%;overflow:hidden;box-sizing: border-box;" @click="getPage(item.id,item)">
					<img v-if="item.image.length>5" style="float:left;padding-right: 15px;box-sizing: border-box;" :style="'width:'+imgWidth+'px;'+'height:'+imgHeight+'px'" mode="cover" :src="item.image" class="image">
					<span style="font-size: 1.3em;font-weight: bold;">{{item.title}}</span><br>
					<span :style="'height:'+imgHeight+'px;overflow:hidden'">{{item.abstract}}</span><el-button type="text" class="button">更多…</el-button>
				</p>
        <div class="bottom clearfix">
          <time class="time">{{item.time}}</time>
          
        </div>
      <!-- </div> -->
    </div>
</div>
</template>
 
 
<script>
	
  export default {
		name:"templateNewsList",
		props:['itemData','screenWidth','widthNum'],
		// props:{"itemData":Object},
    data() {
      return {
// 					itemData:[{
// 							title:"第一电影人",
// 							image:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550830696537&di=9a5118351e9052be7a3d7a21c3612743&imgtype=0&src=http%3A%2F%2Fp1.meituan.net%2Fmovie%2F39b595c3eaad1ccf078caa6c89d05db42732032.jpg'
// 							}]
      };
    },
 
    mounted() {

    },computed:{
			newData(){
				var itemData=this.itemData;
				for(var i=0;i<itemData.length;i++){
					if(itemData[i].abstract.length>100){
						itemData[i].abstract=itemData[i].abstract.substring(0,100)
					}
				}
				return itemData
			},
			itemNum(){
				var itemNum= this.widthNum || 2;
				if(this.screenWidth<900){
					itemNum=1;
				}
				return itemNum;
			},cardWidth(){
				return  parseInt(this.screenWidth/this.itemNum)-20;
			},imgWidth(){
				var cardWidth=this.cardWidth;
				if(this.itemNum<3){
					cardWidth= parseInt(this.cardWidth/3);
				}
				return cardWidth;
			},imgHeight(){
				return parseInt(this.imgWidth/1.5);
			},contentWidth(){
				return this.cardWidth-this.imgWidth;
			}
	},
    methods: {
		getPage(id,item){
			// this.$router.push({name: 'newsDetail',params: {id: id}})
			  this.$router.push({
				path:'/detail/news',
                params: { userId: '123' },
				query:{id:item.id}})
			// console.log(item)
		}
    }
 
  }
</script>
 
 
<style>
	.yc-card{
		background: #fff;
	}
.yc-card:hover{
	/* border: 10px; */
	opacity: 0.7;
}
  .time {
    font-size: 13px;
    color: #999;
  }
  

/*  .image {
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  } */
</style>