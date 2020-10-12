module.exports = {
    serviceWorker:true,
    title:"ova api/文档",
    description:"ova说明文档和api",
    port:8888,
    base:'/blog-test/',
    themeConfig: {
        nav: [
            {text: '主页', link: '/'},
            {text: 'api', link: '/api/'},
            {text: '需求文档', link: '/docs/'},
            {text: '进度', link: '/pro/'}
        ],
        sidebar: 'auto',
        sidebarDepth: 2,
        lastUpdated: 'Last Updated',
        editLinks: true,
        editLinkText: '编辑此页'
    },
}