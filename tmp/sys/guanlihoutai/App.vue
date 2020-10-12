<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
    @import url("/common/uni.css");
    @import url("/common/yc.css");
    
    /* @import url("/common/stylesheet.css"); */
    /* 统一设置自定义色彩 */
    .color-font {
        color: #F7F7F7;
    }
    
    /* 背景和字体色彩 */
    .color-background {
        color: #333333;
        background: #F7F7F7;
    }
    
    /* 导航和字体色 */
    .color-nav {
        background: #0A98D5;
        color: #ffffff;
    }
    
    page {
        position: absolute;
        padding: 0;
        margin: 0;
        background-color: #F4F5F6;
        height: 100%;
        /* height: 100vh; */
        font-size: 16px;
    }
    
    .uni-page-body {
        overflow-x: hidden;
        overflow-y: hidden;
        padding: 0;
        margin: 0;
        box-sizing: border-box;
        height: 100%;
    }
</style>
