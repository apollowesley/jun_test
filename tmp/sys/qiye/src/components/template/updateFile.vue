<template>
    <div class="row">           
        <ul v-if="fileList && typeof fileList=='object'" class="upload-list" style="display: flex;flex-wrap: wrap;">
			<li v-if="fileList.length<max"   class="upload-list-li" style="position: relative ;width: 100px;height: 100px;padding: 5px;">
			<input name="files" accept="image/x-png,image/gif,image/jpeg,image/bmp" placeholder="file" multiple type="file" id="files"  class=" file-btn" style="position: absolute;z-index: 2;" @change="tirggerFile($event)">
				<div style="position: absolute;display: flex;justify-content: center;width: 90%;height: 100%;top: 0;align-items: center;">
					<i style="font-size:5em;opacity: 0.7;color: #409EFF;" class="el-icon-plus"></i>
				</div>
			</input>
			</li>
            <li  v-for="(file, idx) in fileList" :key="idx" class="upload-list-li" style="position: relative;">
                <img  :src="getFileUrl(file,'image')" :style="0==idx?'background:#FFDEAD':''"  style="width: 100px;height: 100px;padding: 5px;" />
                <label class="upload-list-li-label"  @click="deleFile(idx,file.name)"><i class="el-icon-error" style="color: #F56C6C;"></i></label>
				<div v-if="openSort" style="width: 100%;bottom:10px;position: absolute;text-align: center;display: flex;justify-content: center;"  @click="setIndex(idx,file.name)">
                    <el-button round v-if="0!=idx" type="primary" style="padding: 3px;">设为主图</el-button>
                    <el-button v-else type="success" round  style="padding: 6px;font-weight: bold;">主图</el-button>
               </div>
            </li>
        </ul>
    </div>    
</template>
<script type="text/javascript">
	// import File from "../../common/utils/File.js"
    import Request from "../../request/index.js"
    import yc from "yc-js"
    var file =yc.File;
    export default {
		props:{
            openSort:{
                default:false
            },
            imgList:{
                //接收的图片列表[{src:12.jpg}]
                type:Array,
                default:function(){return[]}
            },
            controlledAdd:{
                type:Function,
                default:null
            },controlledDelete:{
                type:Function,
                default:null
            },
            quality:{
                //质量
                type:Number,
                default:1
            },
            maxwh:{
                //最大宽高尺寸像素
                type:Number,
                default:1920
            },
			max:{
                //最多数量
				type:Number,
				default:5
			},change:{
                //监听变化
				type:Function,
				default:null
			}
		},
        name:'upload-file',
        data() {
            return {
                // list:[],
				// fileList:[]
            }
        },
        computed:{
            fileList(){
                if(this.imgList && typeof this.imgList =='object' ){
                    return this.imgList;
                }else{
                    return [];
                }
            }
        },
		watch:{
            max(e){
              if(e==0){
                 this.fileList=[];
              }  
            },
			fileList(e){
				this.change(e)
			}
		},
        methods:{
            tirggerFile(e){
                var list=e.target.files;       
				  if((list.length+this.fileList.length)>this.max){
					   this.$message({
						  showClose: true,
						  message: '警告哦，最多只能上传'+this.max+'个文件！',
						  type: 'warning'
						});
						return;
				  }
                for(var i=0;i<list.length;i++){
                    // 通过canvas压缩图片
                   file.fileToBase64(list[i],e=>{
                       // 判断是否是否被接管上传
                       if(this.controlledAdd && typeof this.controlledAdd=='function'){
                           this.controlledAdd(e);
                       }else{
                           if(!this.imgList || typeof this.imgList !='object'){
                               this.imgList=[];
                           }
                           this.imgList.push(e)
                       }
                   },{maxwh:this.maxwh,quality:this.quality});
                }
            },
            getFileUrl(file,type){
              var url =file.src  || 'logo.png';
              if(url){
                 if(url.substring(0, 10) == "data:image" && url.length>500){
                    return url;
                 }else{
                    return this.$config.getFileUrl(url,type);   
                 }   
              }else{
                  return false;
              }
            },
			setIndex(i,name){
				var temp=this.fileList[i];
				this.fileList[i]=this.fileList[0];
				this.$set(this.fileList,0,temp);

			},
            deleFile(i,name){
                
                if(this.controlledDelete && typeof this.controlledDelete=='function'){
                    this.controlledDelete(i,this.fileList[i]);
                }else{
                    this.fileList.splice(i,1);
                }
               
            }        

        }
    }
</script>
<style scoped>
.upload-list{margin:0;padding:0;list-style:none;}
.upload-list-li{position: relative;margin-top:5px;line-height: 1.8}
.upload-list-li:hover{background-color: #f5f7fa;}
.upload-list-li-name{position:relative;margin-right: 40px;display: block;}
.fa-file-text-o{margin-right:7px;}
.upload-list-li-label{position:absolute;right:5px;top:0;}
.file-box{
    padding:5px 15px;
    border-radius:3px;
    background-color:#409eff;
    border-color:#409eff;
    color:#000;display:inline-block;position: relative;
}
.file-btn{
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    outline: none;
    background-color: transparent;
    filter:alpha(opacity=0);
    -moz-opacity:0;
    -khtml-opacity: 0;
    opacity: 0;
}
</style>
