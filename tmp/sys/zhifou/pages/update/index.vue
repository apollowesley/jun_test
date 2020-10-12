<template>
    <view class="uni-page-body uni-flex" style="flex-direction: column;justify-content: center;background: #555555;height: 100%;align-items: center;">
        <view style="flex-direction: column;justify-content: center;background: #555555;height: 100%;align-items: center;width: 100%;">
            <view style="font-size:3em;color: #FAFAFA;padding: 20px;">
                {{width}}%
            </view>
            <view class="" style="width: 100%;position: relative;background: #C8C7CC;height:20px;border-radius:5px;align-self: flex-start;">
                <view style="position: absolute;z-index: 2;bottom:-1px">需下载:{{totalMb}}mb | 已完成:{{finishMb}}mb</view>
                <view style="position: absolute;background: #09BB07;height:16px;top:2px;border-radius:4px;align-self: flex-start;left: 0;"
                    :style="'width:'+width+'%'">

                </view>
            </view>
            <view style="padding-top: 20px;">

                <button style="width: 100%;" v-if="!isStop" @tap="stop">停止</button>
                <button v-if="isStop" style="width: 100%;" @tap="start">开始</button>
            </view>
            <view style="height: 50px;">

            </view>
        </view>

    </view>

</template>

<script>
    export default {
        data() {
            return {
                isStop: true,
                finishMb:0,//已完成
                totalMb:0,//总共
                width: 0
            }
        },
        computed: {
            // width() {
            //     return this.$store.getters.downloadDegree
            // }
        },
        methods: {
            stop() {
                this.isStop = !this.isStop;
                // console.log(this.isStop)
                this.downloadTask.abort();
            },
            start() {
                this.isStop = false;
                var info = this.query
                this.downloadTask = uni.downloadFile({
                    url: info.url, //仅为示例，并非真实的资源
                    success: (res) => {
                        this.isStop = true;
                        console.log(JSON.stringify(res))
                        if (res.statusCode === 200) {
                            // console.log('下载成功');

                            plus.runtime.install(res.tempFilePath);
                        } else {
                            // console.log("更新失败")
                            // 启动
                            // this.start()
                        }
                    }
                });
                var jindu = -1;
                this.downloadTask.onProgressUpdate((res) => {
                    if (jindu != res.progress) {
                        jindu = res.progress;
  
                        this.width = res.progress;
                        this.finishMb=parseInt(res.totalBytesWritten / 10000)/100;
                        this.totalMb=parseInt(res.totalBytesExpectedToWrite/ 10000)/100 ;
                        // console.log('已经下载的数据长度' + res.totalBytesWritten);
                        // console.log('预期需要下载的数据总长度' + res.totalBytesExpectedToWrite);
                        // this.$store.commit('downloadDegree',res.progress)
                    }

                });
            }

        },
        onLoad(event) {
            try {
                this.query = JSON.parse(decodeURIComponent(event.query));
            } catch (error) {
                this.query = JSON.parse(event.query);
            }
            // console.log(JSON.stringify(event))
            this.start()
        }
    }
</script>

<style>
</style>
