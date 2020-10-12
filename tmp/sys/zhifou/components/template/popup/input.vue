<template>
    <!-- :style="{top:pageStyle.top,bottom:pageStyle.bottom,right:pageStyle.right,left:pageStyle.left}" -->
    <view v-if="show" style="position: absolute;box-sizing: border-box;width: 100%;height: 100%;z-index: 99;justify-content: center;align-items: center;background: rgba(0,0,0,0.5)"
        class="uni-flex uni-column">
        <view style="border-radius: 20upx;overflow: auto;" :style="pageStyle?pageStyle:''">

            <view style="justify-content: center;align-items: center; " class="uni-flex" :class="pageClass?pageClass:''">
                <view class="uni-flex uni-column " style="position: relative;background: #FFFFFF;width: 100%;border-box;border-radius: 20upx;justify-content:space-between;align-items: center;padding: 20upx 40upx;box-sizing: border-box; ">
                    <view style="font-weight: 200;font-size: 2em;text-align: right;position: absolute; z-index: 99;top:0upx;right: 30upx;"
                        @click="onshow">
                        x
                    </view>
                    <view v-if="title" class="" style="font-weight: bold;font-size: 1.2em; height:3em;position:relative ;">
                        {{title}}
                    </view>

                    <view class="uni-flex uni-flex-item uni-column" style="width: 100%;justify-content:space-between; box-sizing: border-box;"
                        v-for="(items,idx) in param" :key="idx">

                        <view v-if="items.type=='input'" class="uni-flex-item  uni-flex uni-column" style="width: 100%;box-sizing: border-box;">
                            <view class="uni-flex " style="padding: 5upx;width: 100%;box-sizing: border-box; " v-for="(item,idxb) in items.content"
                                :key="idxb">
                                <view :class="items.class?items.class:''" :style="items.style?items.style:''" class="uni-flex uni-flex-item"
                                    style="justify-content: center;align-items: center;" v-if="items.type=='input'">

                                    <view v-if="item.text" class="uni-flex-item" :class="item.class?item.class:''">
                                        <text>{{item.text}}</text>
                                    </view>

                                    <input :maxlength="item.maxlength?item.maxlength:''" @click="tapBtn({idx,idxb,item,param})"
                                        :class="items.style?'':'uni-flex-item'" :placeholder="item.placeholder" v-model="item.value"
                                        style="font-size:0.8em; padding:0 5upx;box-sizing: border-box;box-shadow: 0px 0px 1px #888888;height: 2.2em;width: 100%;"
                                        :style="item.style?item.style:'flex:2'">

                                    </input>

                                </view>
                            </view>

                        </view>

                        <view class="uni-flex-item  uni-flex uni-column" style="width: 100%;box-sizing: border-box;"
                            v-else-if="items.type=='textarea'">
                            <view class="uni-flex " style="padding: 5upx;width: 100%;box-sizing: border-box; " v-for="(item,idxb) in items.content"
                                :key="idxb">
                                <view :class="items.class?items.class:''" :style="items.style?items.style:''" class="uni-flex uni-flex-item"
                                    style="justify-content: center;align-items: center;height: 100%;">
                                    <view v-if="item.text" class="uni-flex-item" :class="item.class?item.class:''">
                                        <text style="">{{item.text}} {{item.type}}</text>
                                    </view>
                                    <view style="box-sizing: border-box;min-height: 100upx;width: 100%;">
                                        <textarea @click="tapBtn({idx,idxb,item,param})" :class="items.style?'':'uni-flex-item'"
                                            :placeholder="item.placeholder" v-model="item.value" :style="item.style?item.style:'flex:2'"
                                            :maxlength="item.maxlength?item.maxlength:'200'" :auto-height="item.autoheight?true:false"
                                            style="width: 100%;height: 100upx;padding:0 5upx;box-sizing: border-box;box-shadow: 0px 0px 1px #888888;">
                                            </textarea>
                                    </view>

                                </view>
                            </view>
                        </view>

                        <view v-else :class="items.class?items.class:''" :style="items.style?items.style:''" class="uni-flex-item  uni-flex"
                            style="width: 100%;box-sizing: border-box;">
                            <view class="uni-flex " style="padding: 5upx;width: 100%;box-sizing: border-box; " v-for="(item,idxb) in items.content"
                                :key="idxb">


                                <button v-if="items.type=='button'" :style="item.style?item.style:''" :class="item.class?item.class:'uni-flex-item'"
                                    :type="item.type?item.type:''" :size="item.size?item.size:'mini'" @click="tapBtn({idx,idxb,item,param})">{{item.text}}</button>

                                <view v-else v-html="item.text" :style="item.style?item.style:''" @click="tapBtn({idx,idxb,item,param})"
                                    :class="item.class?item.class:'uni-flex-item'">

                                </view>
                            </view>
                        </view>

                    </view>
                    <view class="" style="font-weight: 100;font-size: 0.7em;padding:10upx;">
                        {{footer}}
                    </view>
                </view>

            </view>
        </view>
    </view>
</template>

<script>
    export default {
        props: {
            onClose: {
                type: Function,
                default: function() {
                    return;
                }
            },
            show: {
                type: Boolean,
                default: function() {
                    return false
                }
            },
            change: {
                type: Function,
                default: function() {
                    return;
                }
            },
            title: {
                type: String,
                default: function() {
                    return '';
                }
            },
            footer: {
                type: String,
                default: function() {
                    return '';
                }
            },
            pageClass: {
                type: String,
                default: function() {
                    return " "
                }
            },
            pageStyle: {
                type: String,
                default: function() {
                    return "min-height:50%;max-height:90%;width:85%"
                }
            },
            param: {
                type: Array,
                default: function() {
                    return [{
                        type: 'input',
                        style: '',
                        // class: 'uni-column uni-flex-item uni-flex',
                        content: [{
                            // style: 'flex:1',
                            // class: 'uni-flex uni-row uni-flex-item',
                            value: '挺好的',
                            name: 'queren',
                            text: '标题'
                        }, {
                            // style: 'flex:2',
                            // class: 'uni-flex uni-row uni-flex-item',
                            value: '挺好的',
                            name: 'queren',
                            text: '出生地区'
                        }]
                    }, {
                        type: 'textarea',
                        style: '',
                        class: 'uni-column uni-flex-item uni-flex',
                        content: [{
                            name: 'queren',
                            text: '内容'
                        }, {
                            style: 'flex:2',
                            // class: 'uni-flex uni-row uni-flex-item',
                            value: '挺好的',
                            name: 'queren',
                            text: '出生地区'
                        }]
                    }, {
                        style: 'white-space: nowrap ;overflow:auto;',
                        class: '',
                        content: [{
                            name: 'queren',
                            text: '图片'
                        }, {
                            name: 'queren',
                            text: '文字'
                        }, {
                            name: 'queren',
                            text: '图标'
                        }, {
                            name: 'queren',
                            text: '其他'
                        }, {
                            name: 'queren',
                            text: '未设置'
                        }]
                    }, {
                        style: 'justify-content:flex-end;',
                        type: 'button',
                        class: 'uni-column uni-flex',
                        content: [{
                            // style:'height:100upx;flex:2',
                            value: '',
                            name: 'reple',
                            text: '取消'
                        }, {
                            type: 'primary',
                            // style:'height:100upx;flex:2',
                            value: '',
                            name: 'reple',
                            text: '确认'
                        }]
                    }, {
                        style: 'justify-content:flex-end;',
                        type: 'button',
                        class: 'uni-flex',
                        content: [{
                                style: '',
                                // size: 'default',
                                class: '',
                                name: 'queren',
                                text: '确认'
                            },
                            {
                                class: 'uni-flex-item',
                                name: 'queren',
                                text: '确认'
                            }
                        ]
                    }]
                }
            },
        },
        data() {
            return {

            }
        },
        methods: {
            // focus(e) {
            //     console.log(e)
            // },
            // input(e) {
            //     console.log(e)
            // },
            onshow() {
                this.onClose()
            },
            tapBtn(item) {
                this.change(item)
                // console.log(item)
            }
        }
    }
</script>

<style>
</style>
