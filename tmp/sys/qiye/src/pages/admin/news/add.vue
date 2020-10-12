<template>

	<div class="" style="width: 80%;">
		<div class="form-box" style="align-self: center;">
			<el-form ref="form" :model="form" label-width="200px" style="padding-top: 20px;">
				<el-form-item label="标题" prop="username">
					<el-input v-model="form.title"></el-input>
				</el-form-item>
				<el-form-item  label="添加图片" prop="username">
					<update-file :max="max"  :maxwh="maxwh" :quality="quality" :change="fileChange"></update-file>	
				</el-form-item>

				<el-form-item label="栏目板块" style="text-align: left;">
					<el-cascader :options="categoryList" :props="props" change-on-select  @change="cascaderChange">
					</el-cascader>
				</el-form-item>
				<el-form-item label="状态" style="text-align: left;">
					<el-select v-model="form.state" prop="rank" placeholder="请选择" style=";background: #;">
						<el-option :key="item.id" v-for="(item) in newsState" :label="item.name" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="详细内容" style="text-align: left;">
					<el-input type="textarea" :rows="4" placeholder="请输入内容" v-model="form.content">
					</el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="onSubmit('form')">提交</el-button>
					<el-button>取消</el-button>
				</el-form-item>

			</el-form>
		</div>
	</div>
</template>

<script>
	import Request from "../../../request/index.js"
	import updateFile from "../../../components/template/updateFile.vue"
    import newsState from "../../../common/data/newsState.js"
    import yc from 'yc-js'
   var  $file= yc.File
    var Arr=yc.Arr;
	export default {
		name: 'adminUserFrom',
		components:{
			updateFile
		},
		data: function() {

			return {
                maxwh:1920,//图片尺寸
                quality:1,//图片质量
                max:5,//图片数量
                categoryList: [],
                props: {
                  label: 'name',
                  value:'id',
                  children: 'children'
                },
				image:null,
				uploadUrl: "https://jsonplaceholder.typicode.com/posts/",//上传地址
				newsState:newsState,
				// selectedOptions: [],
				form: {
					title: '',//标题
					content: '',//内容
					category_id: '',//分类
					state: ""//状态
				}
			}
		},
        comments:{

        },
        created(){
          Request('NewsCategory_List').then(e=>{

              if(e.data.code==200){
                   var data=e.data.data;
                   
                   var Obj={};
                   // 转成obj 用id作为key
                   for (let i=0; i<data.length;i++) {
                       Obj[data[i].id+'']=data[i];
                   }
                    this.list=Obj;//原始数据obj id=index
                    var categoryList= Arr.getTree(Obj);
                    this.categoryList=Arr.getTreeSortBy(categoryList,'ranking','children',false);
                  
              }
          })  
        },
		methods: {
			fileChange(e){
				this.image=e;
			},
			cascaderChange(arr) {
//                 console.log(arr)
                this.form.category_id=arr[arr.length-1];
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
                        var arr=[];
                        for(let i in this.image){
                            var src=this.image[i].src ||this.image[i].url;
                            arr.push(src)
                        }
                        this.form.image=arr;
						var data={form:this.form};
                        Request('AdminNews_Add',{data:data})
                        .then((e)=>{

                                if(e.data.code==200){
                                    this.$message.success('提交成功！');
                                }else{
                                    this.$message.error(e.data.message);
                                }
                        })
					} else {

						return false;
					}
				});

			},

		}
	}
</script>
<style>
	.el-form-item {
		text-align: left;
	}
</style>
