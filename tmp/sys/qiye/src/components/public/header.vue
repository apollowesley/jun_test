<template>
	<div class="" style="display: flex;box-sizing: border-box;margin: 0;padding: 0;">
        <el-menu v-if="screenMode" :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" style="box-sizing: border-box;justify-content: center;text-align: center;display: flex;flex: 1;width: 100%;">

				<div   style="line-height: 60px;height: 60px;display: flex;align-items: center;pointer-events: none;padding-right: 10vw;">
					<img :src="logo" mode=""  style="height: 60%;" />
				</div>
                <div v-for="(item,key) in menu" :key="key" style="width: auto;position: relative;">
                        <el-submenu v-if="item.children" :index="''+item.id+''" >
                        <template slot="title">{{item.name}}</template>
                        <el-tree accordion  :data="item.children" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
                        </el-submenu>
                      <el-menu-item v-else :index="''+item.id+''" >{{item.name}}</el-menu-item>
                </div>
		</el-menu>
        <div v-else  style="background: #fff;box-sizing: border-box; padding-right:20px;padding-left:10px;display: flex;height: 50px;line-height: 50px;justify-content: space-between;width: 100%;">
            <div style="line-height: 50px;height: 50px;display: flex;align-items: center;">
                <img :src="logo" mode=""  style="height: 60%;" />
            </div>
            <el-dropdown style="" unique-opened="true" @command="handleSelect">
                  <span class="el-dropdown-link">
                    菜单<i class="el-icon-arrow-down el-icon--right"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                      <div v-for="(item,key) in menu" :key="key" style="width: auto;position: relative;">
                              <el-dropdown-item v-if="item.children" :index="''+item.id+''" >
                              <template slot="title">{{item.name}}</template>
                              <el-tree accordion  :data="item.children" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
                              </el-dropdown-item>
                            <el-dropdown-item v-else :command="''+item.id+''" >{{item.name}}</el-dropdown-item>
                      </div>
                  </el-dropdown-menu>
            </el-dropdown>
    </div>
<div class="line"></div>
	</div>
</template>
 
 
<script>
    import Request from "../../request/index.js";
    import yc from 'yc-js';
    var Arr=yc.Arr;
  export default {
		props:{
//            screenMode:{
//                default:true
//            } 
        },
    data() {
      return {
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
				activeIndex: '1',
				logo:require("../../assets/images/logo.png"),
                NavList:[],//处理后的数据用于显示的
                list:{},//原始数据obj{key=id}
				// footer:"营口大唐圣殿文化发展有限公司",
       screen:{
                 width:0,
                 height:0,
                 mode:true
             }
         }
     },
     computed: {
        screenMode(){
            return this.$store.getters.screen.mode
        },
        menu(){
          return this.NavList;
        }  
    },
    created(){

    },
    mounted() {
        this.getParams();
    },
    methods: {
            goPage(page){
                console.log(page)
//               this.$route({
//                   path:page
//               })  
            },
            handleNodeClick(e){
                // console.log(e);
                if(!e.children && e.url){
                    this.handleSelect(e.id)
                }
            },
			getParams() {

            Request('NavList').then(e=>{
                if(e.data.code==200){
                    var data=e.data.data;
                   
                   var Obj={};
                   // 转成obj 用id作为key
                   for (let i=0; i<data.length;i++) {
                       Obj[data[i].id+'']=data[i];
                   }
                   this.list= JSON.parse(JSON.stringify(Obj));
                  var NavList= Arr.getTree(Obj);
                    this.NavList=Arr.getTreeSortBy(NavList,'ranking','children',false);
                }


            })
      },handleSelect(key) {
        if(this.list[key]){
            // console.log(this.list[key])
            if(this.list[key].url){
                var url=this.list[key].url;
                    if(url && url.slice(0, 4) != "http"){
                           this.$router.push({
                               path:url
                           }) 
                    }else{
                        window.open(url);
                    }
  
            }else{
                this.$router.push({
                    
                   path:'/list/news?id='+this.list[key].id
                }) 
            }
        }
      }
    }
  }
</script>
 
 
<style>

/* 菜单下拉 */
   .el-dropdown-link {
    cursor: pointer;
    color: #DCDCDC;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>