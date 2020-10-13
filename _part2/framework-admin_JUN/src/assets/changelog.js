export default [
  {
    time: '2016年10月13日',
    version: '1.1.5',
    changes: [
      '添加了<code>数据报表</code>模块与两个统计报表:<code>直播主题</code>和<code>直播主题用户明细</code>.',
      '添加了以下模块的数据导出:<code>年会参会名单</code>、<code>社群用户</code>、<code>直播主题统计</code>和<code>直播用户统计</code>.',
      '<code>社群</code>创建成功后, 现在会清除已输入的数据, 防止重复添加.',
      '记录<code>操作日志</code>时, 如果某个字段内容过长, 将以<code>省略号(...)</code>代替, 防止长度溢出导致的错误.'
    ]
  },
  {
    time: '2016年9月20日',
    version: '1.1.4',
    changes: ['添加了<code>学乎运营</code>下的<code>年会参会名单</code>模块.']
  },
  {
    time: '2016年9月20日',
    version: '1.1.3',
    changes: ['<code>直播主题</code>现在可以指定操作人员, 添加<code>直播主题 (实习人员)</code>菜单, 指定的人员才能操作对应的主题.']
  },
  {
    time: '2016年8月26日',
    version: '1.1.2',
    changes: [
      '添加<code>消息推送</code>功能。详见<code>学乎运营</code>下的<code>消息管理</code>与<code>消息类型</code>.',
      '<code>文件浏览器</code>现在可以记录与显示上传的原始文件名.',
      '修复与完善<code>学乎直播间</code>下<code>直播间</code>模块.'
    ]
  },
  {
    time: '2016年8月26日',
    version: '1.1.1',
    changes: ['添加直接间消息编辑功能。']
  },
  {
    time: '2016年7月20日',
    version: '1.1',
    changes: [
      '添加了<code>学乎运营</code>模块与<code>运营文章</code>、<code>公众号菜单</code>两个子模块。',
      '<code>数据字典</code>增加了整体描述:<code>description</code>, 单个键值对使用<code>comment</code>注释.',
      '新增<code>文件浏览器(File Browser)</code>功能, 可以对管理平台以及APP上传的文件进行管理.',
      '修复了直播主题，添加/编辑-角色/主题专场，保存后返回上一页，页面变灰，无法操作的BUG(<a href="http://192.168.1.141/zentao/bug-view-319.html" target="_blank">#319</a>)'
    ]
  },
  {
    time: '2016年6月30日',
    version: '1.0.1',
    changes: [
      '添加了<code>操作日志</code>与<code>直播</code>类型的课程配置',
      '增加了学乎直播间的管理配置功能。',
      '修复了<a href="/indexcourseconfig">课程配置</a>编辑分享时某些分享查询不到的BUG(<a href="http://192.168.1.141/zentao/bug-view-345.html" target="_blank">#345</a>)',
      '添加底部页脚的学乎版权信息。',
      '在系统一些主要位置添加了系统版本号显示。',
      '汉化了登录、注册、找回密码页面。',
      '添加了系统主页你正看到的系统更新日志与时间轴。',
      '其它一些界面上的调整。'
    ]
  },
  {
    time: '2016年5月21日',
    version: '1.0.0',
    changes: ['发布管理平台的第一个线上版本。']
  }
]
