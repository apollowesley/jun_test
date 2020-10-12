    import {
        Time,Storage
    } from '@/common/yc_js/index.js';
export default {
    StorageSyncSet(state,Arr) {
        Storage.Sync.set(Arr[0],Arr[1],Arr[2]||0)
    },
    pages(state, pageName) {
        var pages = this.getters.pages || [];
        var pageUp = [pageName];
        if (pages.length > 10) {
            for (var i = 0; i < 10; i++) {
                pageUp.push(pages[i])
            }

        } else {
            pageUp = pages;
            pageUp.unshift(pageName)
        }
        state.pages = pageUp
    },
    windowSize(state, windowSize) {
        // 可视屏幕宽度
        var screenWidth = windowSize.screenWidth || 720;
        var screenHeight = windowSize.screenHeight || 1440;
        // 内容窗口宽度
        var windowWidth = windowSize.windowWidth || screenWidth;
        var windowHeight = windowSize.windowHeight || screenHeight;
        var mode = true;
        if (windowWidth < windowHeight) {
            mode = false;
        }
        state.windowSize = {
            mode,
            screenWidth,
            screenHeight,
            windowWidth,
            windowHeight
        };
    },
    systemInfo(state, systemInfo) {
        state.systemInfo = systemInfo
    },
    downloadDegree(state, val) {
        // 下载
        state.downloadDegree = val
    }


}
