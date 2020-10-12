<template>
    <div class="">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> tab选项卡</el-breadcrumb-item>
            </el-breadcrumb>
            
        </div>
        <div class="container">
            <el-tabs v-model="message">
                <el-tab-pane :label="`未读消息(${unread.length})`" name="first">
                    <el-table :data="unread" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template slot-scope="scope" >
                                <span class="message-title">{{scope.row.msg}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="date" width="180"></el-table-column>
                        <el-table-column width="120" >
                            <template slot-scope="scope" >
                                <el-button size="small" @click="handleRead(scope.row.id)">标为已读</el-button>
                            </template>
                        </el-table-column>
                        
                    </el-table>
                    <div class="handle-row">
                        <el-button type="primary">全部标为已读</el-button>
                    </div>
                    <el-pagination
                        background
                        layout="prev, pager, next"
                        @current-change="handleCurrentChange"
                        :total="pageCount">
                    </el-pagination>
                </el-tab-pane>
                <el-tab-pane :label="`已读消息(${read.length})`" name="second">
                    <template v-if="message === 'second'">
                        <el-table :data="read" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span class="message-title">{{scope.row.msg}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="150"></el-table-column>
                            <el-table-column width="120">
                                <template slot-scope="scope">
                                   <!-- 这里可以写按钮 -->
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <!-- <el-button type="danger">删除全部</el-button> -->
                        </div>
                    </template>
                    <el-pagination
                        background
                        layout="prev, pager, next"
                        @current-change="handleCurrentChange"
                        :total="page2Count">
                    </el-pagination>
                </el-tab-pane>
                <!-- <el-tab-pane :label="`回收站(${recycle.length})`" name="third">
                    <template v-if="message === 'third'">
                        <el-table :data="recycle" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span class="message-title">{{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="150"></el-table-column>
                            <el-table-column width="120">
                                <template slot-scope="scope">
                                    <el-button @click="handleRestore(scope.$index)">还原</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button type="danger">清空回收站</el-button>
                        </div>
                    </template>
                </el-tab-pane> -->
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import {getMsgDesc,readMsg} from '@/api/user'
    import {timestampToTime} from '@/utils/toTimeStr'
    export default {
        name: 'tabs',
        data() {
            return {
                message: 'first',
                showHeader: false,
                pageCount:0,
                page2Count:0,
                unread: [],
                read: [],
                recycle: [{
                    date: '2018-04-19 20:00:00',
                    title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
                }]
            }
        },
        created(){
            this.getMsgDesc(1)
        },
        methods: {
            handleRead(id) {
                readMsg(id)
                .then(res=>{
                })
            },
            handleDel(index) {
                const item = this.read.splice(index, 1);
                this.recycle = item.concat(this.recycle);
            },
            handleRestore(index) {
                const item = this.recycle.splice(index, 1);
                this.read = item.concat(this.read);
            },
            getMsgDesc(page){
                getMsgDesc(page)
                .then(res=>{
                    
                    this.setUnread(res.result.data)
                })
            },
            setUnread(arr){
                for(let i = 0 ; i < arr.length ; i++){
                    arr[i].date = timestampToTime(arr[i].gmtUpdate)
                    if(arr[i].status===0){
                        this.unread.push(arr[i])
                    }else{
                        this.read.push(arr[i])
                    }
                }
                this.pageCount= Math.ceil(this.unread.length/10)*10
                this.page2Count= Math.ceil(this.read.length/10)*10
            },
            handleCurrentChange(val){
                this.getMsgDesc(val)
            }
        },
        computed: {
            unreadNum(){
                return this.unread.length;
            }
        }
    }

</script>

<style>
.message-title{
    cursor: pointer;
}
.handle-row{
    margin-top: 30px;
}
</style>

