<template>
    <button :class="{'btn':true,'btn-default':type === 'default'}">{{value}}</button>
</template>

<script>
    export default {
        name: 'MButton',
        props: {
            value: {
                type: String,
                required: true
            },
            type: {
                type: String,
                default: 'default',
                validator(value) {
                    return [
                        'default',
                        'danger',
                        'primary'
                    ].indexOf(value) > -1;
                }
            }
        }
    }
</script>

<style lang="less" scoped>
    .btn {
        display: inline-block;
        width: 100%;
        height:47px;
        line-height: 47px;
        border: none;
        border-radius: 3px;
    }
    .btn-default{
        background: none;
        border:1px solid #f23030;
        color: #f23030;
    }
</style>