<template>
    <div class="tags" v-if="showTags" >
		<div style="display: flex;">
       <el-button size="mini" type="primary" class="tags-li"  v-for="(item,index) in tagsList" :class="{'active': isActive(item.path)}" :key="index">
                <router-link :to="item.path" class="tags-li-title" >
                    {{item.title}}
                </router-link>
                <span class="tags-li-icon" @click="closeTags(index)"><i class="el-icon-close"></i></span>
        </el-button>
		</div>
        <div class="tags-close-box">
            <el-dropdown @command="handleTags">
                <el-button size="mini" type="primary">
                    标签选项<i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu size="small" slot="dropdown">
                    <el-dropdown-item command="other">关闭其他</el-dropdown-item>
                    <el-dropdown-item command="all">关闭所有</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
    // import bus from './bus';
    export default {
        data() {
            return {
                tagsList: []
            }
        },
        methods: {
            isActive(path) {
                return path === this.$router.fullPath;
            },
            // 关闭单个标签
            closeTags(index) {
                const delItem = this.tagsList.splice(index, 1)[0];
                const item = this.tagsList[index] ? this.tagsList[index] : this.tagsList[index - 1];
                if (item) {
                    delItem.path === this.$router.fullPath && this.$router.push(item.path);
                }else{
                    this.$router.push('/admin');
                }
            },
            // 关闭全部标签
            closeAll(){
                this.tagsList = [];
                this.$router.push('/admin');
            },
            // 关闭其他标签
            closeOther(){
                const curItem = this.tagsList.filter(item => {
                    return item.path === this.$router.fullPath;
                })
                this.tagsList = curItem;
            },
            // 设置标签
            setTags(route){
				console.log(route)
                const isExist = this.tagsList.some(item => {
                    return item.path === route.fullPath;
                })
                if(!isExist){
                    if(this.tagsList.length >= 8){
                        this.tagsList.shift();
                    }
                    this.tagsList.push({
                        title: route.meta.title,
                        path: route.fullPath,
                        name: route.matched[1].components.default.name
                    })
                }
                // bus.$emit('tags', this.tagsList);
            },
            handleTags(command){
                command === 'other' ? this.closeOther() : this.closeAll();
            }
        },
        computed: {
            showTags() {
                return this.tagsList.length > 0;
            }
        },
        watch:{
            $router(newValue, oldValue){
                this.setTags(newValue);
            }
        },
        created(){
            // this.setTags(this.$route);
        }
    }

</script>


<style>
    .tags {
        height: 30px;
        background: #fff;
		color: #fff;
		display: flex;
		justify-content: space-between;
		width: 100%;
    }

    .tags-li {
        float: left;
        margin: 3px 5px 2px 3px;
        border-radius: 3px;
        font-size: 12px;
        height: 23px;
        line-height: 23px;
        padding: 0 5px 0 12px;
		color: #fff;
    }
	.tags-li-title{
		color: #fff;
	}
    .tags-li:not(.active):hover {
        opacity: 0.8;
    }

    .tags .active {
		opacity: 0.6;
        color: #fff;
    }

</style>
