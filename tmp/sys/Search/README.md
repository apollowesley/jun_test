#Search
对搜索引擎的搜索结果进行抓取.

Maven坐标

```xml
<dependency>
  <groupId>net.cassite</groupId>
  <artifactId>search</artifactId>
  <version>0.0.1-RELEASE</version>
</dependency>
```

本工具使用SLF4J,如果需要日志输出请自行添加与配置日志实现

使用方式如下

```java
Search search = new Search(new BaiduEngine());
// 还可以选择 必应:BingEngine 360:SoEngine 搜狗:SogouEngine
// 其中百度和必应的UserAgent是安卓,360和搜狗的UserAgent是Mac OS X
/*
 * 使用如下方式搜索:
 */
List<Resut> results = search.search("开源中国");
// 或者
Request req = new Request();
req.setSite("my.oschina.net");
req.setPage(0); // 页码从0开始
req.getKeywords().add("wkgcass");
results = search.search(req);

// Engine对象可以指定一些参数,例如是否允许空cite之类.每种Engine可指定的参数不同.
// Search对象可以指定DocumentProvider (默认为JsoupDocProvider)
search = new Search(
    new BaiduEngine().allowEmptyCite(true)
).documentProvider(
    JsoupDocProvider.get()
);
```

下面为从4个搜索引擎抓取的"开源中国"第一页结果(有的记录为应用,推广等,没有进行提取. 正常的结果是能够取到的)

```json
[
    [
        {
            "title": {
                "title": "开源中国 - 找到您想要的开源项目,分享和交流",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国 www.oschina.net 是目前中国最大的开源技术社区。我们传播开源的理念,推广开源项目,为 IT 开发者...",
                "keywords": {
                    "0": "开源中国"
                }
            },
            "url": {
                "link": "/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=1/tc?ref=www_iphone&lid=9628103814910827333&order=1&vit=osres&tj=www_normal_1_0_10_title&waput=6&waplogo=1&cltj=normal_title&dict=-1&title=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD-%E6%89%BE%E5%88%B0%E6%82%A8%E6%83%B3%E8%A6%81%E7%9A%84%E5%BC%80%E6%BA%90%E9%A1%B9%E7%9B%AE%2C%E5%88%86%E4%BA%AB%E5%92%8C%E4%BA%A4%E6%B5%81&sec=12818&di=ffd943d7875c0364&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IGtiORitQ_zSv953ybrWxBa",
                "cite": "m.oschina.net"
            }
        },
        {
            "title": {
                "title": "开源项目大全 - 开源中国社区",
                "keyWord": {
                    "0": "开源",
                    "9": "开源中国"
                }
            },
            "content": {
                "content": "开源中国社区 www.oschina.net 成立于2008年8月,其目的是为中国的IT技术人员提供一个全面的、快捷更新的...",
                "keywords": {
                    "0": "开源中国"
                }
            },
            "url": {
                "link": "/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=3/tc?ref=www_iphone&lid=9628103814910827333&order=2&vit=osres&tj=www_normal_2_0_10_title&m=8&srd=1&cltj=cloud_title&dict=32&title=%E5%BC%80%E6%BA%90%E9%A1%B9%E7%9B%AE%E5%A4%A7%E5%85%A8-%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD%E7%A4%BE%E5%8C%BA&sec=12818&di=dd0baa090b8b01b7&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IEQGG_y2H1T_8o5zthPXrZQRAUiH5KnqZF9Cb9a",
                "cite": "www.oschina.net"
            }
        },
        {
            "title": {
                "title": "开源中国_百度百科",
                "keyWord": {
                    "1": "开源中国"
                }
            },
            "content": {
                "content": "简介：开源中国成立于2008年8月，是目前国内最大的开源技术社区，拥有超过200万会员，形成了由开源软件库、代码分享、资讯、协作翻译、讨论区和博客等几大频道内容，为IT开发者提供了一个发现、使用、并交流开源技术的平台。2013年，开源中国建立大型综合性的云开发平台-中国源，为中国广大开发者提供团队协作、源码托管、代码质量分析、代码评审、测试、代码演示平台等功能。开源中国一直不遗余力地推动国内开源软件的应用和发展，为本土开源能力的提高、开源生态环境的优化提供长期推进的平台。开源中国客户端（Andorid 客户端、iPhone 客户端、Windows Phone 客户端）为用户提供了新闻,问答,动弹,动态以及消息中心的快捷操作方式,让用户能随时随地的在开源中国社区上与其他朋友互动。 Git@OSC 客户端（Andorid 客户端 和 iPhone 客户端）为用户提供了浏览项目、查看源码、发布 Is 简介 开源中国旗下子站点 中国源 Duke选择奖",
                "keywords": {
                    "3": "开源中国",
                    "26": "开源",
                    "48": "开源",
                    "101": "开源",
                    "115": "开源中国",
                    "133": "中国",
                    "138": "中国",
                    "182": "开源中国",
                    "197": "开源",
                    "211": "开源",
                    "219": "开源",
                    "238": "开源中国",
                    "328": "开源中国"
                }
            },
            "url": {
                "link": "http://m.baidu.com/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%401%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=1/tc?ref=www_iphone&lid=9628103814910827333&order=3&waplogo=1&fm=albk&tj=Xv_3_0_10_title&sec=12818&di=0eb1b26ae5789a47&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IJBOMLikK1De8mVjte4viZQRAVDbqRzrIBZyhbDHLthUPaDD73mNUjtBOrhg-fzprjyS8",
                "cite": ""
            }
        },
        {
            "title": {
                "title": "中国开源现状如何? - 开源软件 - 知乎",
                "keyWord": {
                    "0": "中国开源",
                    "12": "开源"
                }
            },
            "content": {
                "content": "2015年12月3日 - 小码农||大神都是“世界是平的”最好的践行者,他们已经越过边界看到了远方,你为什么还要问“中国”开源...",
                "keywords": {
                    "58": "中国",
                    "61": "开源"
                }
            },
            "url": {
                "link": "/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=1/tc?ref=www_iphone&lid=9628103814910827333&order=5&vit=osres&tj=www_normal_5_0_10_title&waput=1&waplogo=1&cltj=normal_title&dict=-1&title=%E4%B8%AD%E5%9B%BD%E5%BC%80%E6%BA%90%E7%8E%B0%E7%8A%B6%E5%A6%82%E4%BD%95%3F-%E5%BC%80%E6%BA%90%E8%BD%AF%E4%BB%B6-%E7%9F%A5%E4%B9%8E&sec=12818&di=3b278eb247597edc&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IEQGG_zRQ_z_b95qshbWxBcVvZyDbKXSMZpPPdj4XtxoHx87",
                "cite": "www.zhihu.com"
            }
        },
        {
            "title": {
                "title": "手机客户端 - 开源中国社区",
                "keyWord": {
                    "8": "开源中国"
                }
            },
            "content": {
                "content": "开源中国Android、iPhone、WP7客户端,为你提供触手可及的OSChina.NET开源信息",
                "keywords": {
                    "0": "开源中国"
                }
            },
            "url": {
                "link": "/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=3/tc?ref=www_iphone&lid=9628103814910827333&order=6&vit=osres&tj=www_normal_6_0_10_title&m=8&srd=1&cltj=cloud_title&dict=21&nt=wnor&title=%E6%89%8B%E6%9C%BA%E5%AE%A2%E6%88%B7%E7%AB%AF-%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD%E7%A4%BE%E5%8C%BA&sec=12818&di=565ee8d866d1f498&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IEQGG_y2H1T_8o5zthPXrZQRAYy4f",
                "cite": "www.oschina.net"
            }
        },
        {
            "title": {
                "title": "开源中国众包平台",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国众包平台 (zb.oschina.net)--共享经济下的软件发布、项目发包接包、悬赏开发、雇佣开发者的服务...",
                "keywords": {
                    "0": "开源中国"
                }
            },
            "url": {
                "link": "/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=3/tc?ref=www_iphone&lid=9628103814910827333&order=7&vit=osres&tj=www_normal_7_0_10_title&m=8&srd=1&cltj=cloud_title&dict=32&title=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD%E4%BC%97%E5%8C%85%E5%B9%B3%E5%8F%B0&sec=12818&di=ca52aab1dc98ea57&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IDBTPKjtX_j35nI3tfeSaUb3",
                "cite": "zb.oschina.net"
            }
        },
        {
            "title": {
                "title": "在线工具 —— 开源中国社区",
                "keyWord": {
                    "8": "开源中国"
                }
            },
            "content": {
                "content": "开源中国在线工具,ostools为开发设计人员提供在线工具,提供jsbin在线 CSS、JS 调试,在线 Java API文档,在线 ...",
                "keywords": {
                    "0": "开源中国"
                }
            },
            "url": {
                "link": "/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=3/tc?ref=www_iphone&lid=9628103814910827333&order=8&vit=osres&tj=www_normal_8_0_10_title&m=8&srd=1&cltj=cloud_title&dict=32&title=%E5%9C%A8%E7%BA%BF%E5%B7%A5%E5%85%B7%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD%E7%A4%BE%E5%8C%BA&sec=12818&di=e41149a369aa6b93&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IEhmOKWBLATm7pE37xPraFBEsR_",
                "cite": "tool.oschina.net"
            }
        },
        {
            "title": {
                "title": "Linux.中国-开源社区",
                "keyWord": {
                    "6": "中国",
                    "9": "开源"
                }
            },
            "content": {
                "content": "Linux中国是一家创立于2003年的国内开源社区,主要关注方向在 Linux 推广、Linux 技术研究、Linux 业界事件的...",
                "keywords": {
                    "5": "中国",
                    "21": "开源"
                }
            },
            "url": {
                "link": "/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=1/tc?ref=www_iphone&lid=9628103814910827333&order=9&vit=osres&tj=www_normal_9_0_10_title&waput=4&waplogo=1&cltj=normal_title&dict=-1&nt=wnor&title=Linux.%E4%B8%AD%E5%9B%BD-%E5%BC%80%E6%BA%90%E7%A4%BE%E5%8C%BA&sec=12818&di=3565c30a16ec37df&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IGhuPQDYK1TTrzye",
                "cite": "linux.cn"
            }
        },
        {
            "title": {
                "title": "开源硬件 - 开源中国社区",
                "keyWord": {
                    "0": "开源",
                    "7": "开源中国"
                }
            },
            "content": {
                "content": "开源硬件Arduino、kiwiboard、树莓派等开源硬件专区",
                "keywords": {
                    "0": "开源",
                    "26": "开源"
                }
            },
            "url": {
                "link": "/from=0/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.1_3_534/baiduid=2381A4DEC913D08F854D862DDCD8F197/w=0_10_%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD/t=iphone/l=3/tc?ref=www_iphone&lid=9628103814910827333&order=10&vit=osres&tj=www_normal_10_0_10_title&m=8&srd=1&cltj=cloud_title&dict=32&nt=wnor&title=%E5%BC%80%E6%BA%90%E7%A1%AC%E4%BB%B6-%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD%E7%A4%BE%E5%8C%BA&sec=12818&di=12a2e0ffce604b97&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IEQGG_y2H1T_8o5zthPXrZQRAWj0hNGyXEUC",
                "cite": "www.oschina.net"
            }
        }
    ],
    [
        {
            "title": {
                "title": "开源中国 - 找到您想要的开源项目，分享和交流",
                "keyWord": {
                    "0": "开源中国",
                    "13": "开源"
                }
            },
            "content": {
                "content": "开源中国 www.oschina.net 是目前中国最大的开源技术社区。我们传播开源的理念，推广开源项目，为 IT 开发者提供了一个发现、使用、并交流开源技术的平台。目前开源中国 ...",
                "keywords": {
                    "0": "开源中国",
                    "9": "oschina",
                    "24": "中国",
                    "29": "开源",
                    "40": "开源",
                    "48": "开源",
                    "75": "开源",
                    "85": "开源中国"
                }
            },
            "url": {
                "link": "http://m.oschina.net/",
                "cite": "www.oschina.net"
            }
        },
        {
            "title": {
                "title": "开源中国 - 开源中国社区",
                "keyWord": {
                    "0": "开源中国",
                    "7": "开源中国"
                }
            },
            "content": {
                "content": "新中国成立还没有70年。我们那里抗日战争时八路军住过的房子已经拿来当古董、当景点了，抗日战争距离现在也才70多年",
                "keywords": {
                    "0": "新中国"
                }
            },
            "url": {
                "link": "http://my.oschina.net/gaoyoubo",
                "cite": "my.oschina.net"
            }
        },
        {
            "title": {
                "title": "在线工具 —— 开源中国社区",
                "keyWord": {
                    "8": "开源中国"
                }
            },
            "content": {
                "content": "开源中国在线工具,ostools为开发设计人员提供在线工具，提供jsbin在线 CSS、JS 调试，在线 Java API文档,在线 PHP API文档,在线 Node.js API文档,Less CSS编译器 ...",
                "keywords": {
                    "0": "开源中国"
                }
            },
            "url": {
                "link": "http://tool.oschina.net/",
                "cite": "tool.oschina.net"
            }
        },
        {
            "title": {
                "title": "Java自由人 - 开源中国社区",
                "keyWord": {
                    "10": "开源中国"
                }
            },
            "content": {
                "content": "加入时间： 2008年8月31日 (Sun)，最近登录: 58分钟前 所在地区： 广东 深圳 工作单位： 开源中国社区 - 创始人 - 产品经理 城市圈： 沈阳， 武汉 ...",
                "keywords": {
                    "53": "开源中国"
                }
            },
            "url": {
                "link": "http://my.oschina.net/javayou",
                "cite": "my.oschina.net"
            }
        },
        {
            "title": {
                "title": "开源中国的微博_微博",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国，开源中国官方微博。开源中国的微博主页、个人资料、相册。新浪微博，随时随地分享身边的新鲜事儿。 ... 恒拓开源已经获得新三板的挂牌批准涵，股票代码 ...",
                "keywords": {
                    "0": "开源中国",
                    "5": "开源中国",
                    "14": "开源中国",
                    "58": "开源"
                }
            },
            "url": {
                "link": "http://weibo.cn/oschina2010",
                "cite": "www.weibo.com"
            }
        },
        {
            "title": {
                "title": "开源中国招聘 - 首页",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "手机版开源中国招聘job.oschina.net是一个以IT技术人员招聘的垂直领域招聘平台，依托开源中国社区oschina.net，为程序员、开发者量身定做属于程序员、开发者的简历，为开发者和 ...",
                "keywords": {
                    "3": "开源中国",
                    "13": "oschina",
                    "48": "开源中国",
                    "54": "oschina"
                }
            },
            "url": {
                "link": "http://job.oschina.net/",
                "cite": "job.oschina.net"
            }
        },
        {
            "title": {
                "title": "Team@OSC 团队协作开发 - 开源中国",
                "keyWord": {
                    "18": "开源中国"
                }
            },
            "content": {
                "content": "Team@OSC 是开源中国推出的专门针对开发者的轻量级的团队协作开发平台。 ... Team@OSC 是一个团队协作开发平台,轻松管理轻量级团队。代码运行平台(PaaS)、代码 ...",
                "keywords": {
                    "10": "开源中国"
                }
            },
            "url": {
                "link": "http://team.oschina.net/",
                "cite": "team.oschina.net"
            }
        },
        {
            "title": {
                "title": "PHP China-最棒的PHP中文社区",
                "keyWord": {
                    "4": "China"
                }
            },
            "content": {
                "content": "PHP China社区是中国PHP行业开源门户社区，这里汇集了全国各地的PHPer共同交流最新的PHP编程技术。PHPChina致力于打造最棒的PHP中文社区。",
                "keywords": {
                    "4": "China",
                    "12": "中国",
                    "19": "开源",
                    "31": "全国"
                }
            },
            "url": {
                "link": "http://www.phpchina.com/",
                "cite": "www.phpchina.com"
            }
        }
    ],
    [
        {
            "title": {
                "title": "开源中国 - 找到您想要的开源项目,分享和交流",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国 www.oschina.net 是目前中国最大的开源技术社区。我们传播开源的理念,推广开源项目,为 IT 开发者提供了一个发现、使用、并交流开源技术的平台。目前开源中国...",
                "keywords": {
                    "0": "开源中国",
                    "11": "china",
                    "24": "中国",
                    "29": "开源",
                    "85": "开源中国"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2Fwww.oschina.net%2F&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=3189177e6e012879611cb766236d822&src=haosou",
                "cite": "www.oschina.net"
            }
        },
        {
            "title": {
                "title": "开源中国_360百科",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国成立于2008年8月，是目前国内最大的开源技术社区，拥有超过200万会员，形成了由开源软件库、代码分享、资讯、协作翻译、讨论区和博客等几大频道内...",
                "keywords": {
                    "0": "开源中国",
                    "23": "开源"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2Fbaike.so.com%2Fdoc%2F7088099-7311012.html&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=e0e8aa109da5770feb080c67b947641&src=haosou",
                "cite": "baike.so.com/doc/7088099-731..."
            }
        },
        {
            "title": {
                "title": "【9秒社团】-中国最大的移动开源技术社区",
                "keyWord": {
                    "7": "中国",
                    "14": "开源"
                }
            },
            "content": {
                "content": "9秒社团是一个以“开源是一种精神”为宗旨的开源社区,社区致力于微信开发、游戏开发、游戏美术资源等技术分享与交流。",
                "keywords": {
                    "9": "开源",
                    "21": "开源"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2Fwww.9miao.com%2F&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=793b8a7445eaceb045c084e08076c40&src=haosou",
                "cite": "www.9miao.com"
            }
        },
        {
            "title": {
                "title": "开源项目大全 - 开源中国社区",
                "keyWord": {
                    "0": "开源",
                    "9": "开源中国"
                }
            },
            "content": {
                "content": "开源中国社区 www.oschina.net 成立于2008年8月,其目的是为中国的IT技术人员提供一个全面的、快捷更新的用来检索开源软件以及交流使用开源经验的平台,截止到 2009年10...",
                "keywords": {
                    "0": "开源中国",
                    "13": "china",
                    "39": "中国",
                    "65": "开源"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2Fwww.oschina.net%2Fproject%2F&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=f6691470d8be7298df09548883aec17&src=haosou",
                "cite": "www.oschina.net/project/"
            }
        },
        {
            "title": {
                "title": "在线工具 -- 开源中国社区",
                "keyWord": {
                    "8": "开源中国"
                }
            },
            "content": {
                "content": "开源中国在线工具,ostools为开发设计人员提供在线工具,提供jsbin在线 CSS、JS 调试,在线 Java API文档,在线 PHP API文档,在线 Node.js API文档,Less CSS编译器,Ma...",
                "keywords": {
                    "0": "开源中国"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2Ftool.oschina.net%2F&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=15dc83bd445c9508e9ba8ff7e591c12&src=haosou",
                "cite": "tool.oschina.net"
            }
        },
        {
            "title": {
                "title": "开源中国众包平台",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国众包平台 (zb.oschina.net)--共享经济下的软件发布、项目发包接包、悬赏开发、雇佣开发者的服务平台。",
                "keywords": {
                    "0": "开源中国",
                    "15": "china"
                }
            },
            "url": {
                "link": "https://zb.oschina.net/",
                "cite": "zb.oschina.net"
            }
        },
        {
            "title": {
                "title": "开源中国招聘 - 首页",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国招聘job.oschina.net是一个以IT技术人员招聘的垂直领域招聘平台,依托开源中国社区oschina.net,为程序员、开发者量身定做属于程序员、开发者的简历,为开发者和...",
                "keywords": {
                    "0": "开源中国",
                    "12": "china",
                    "45": "开源中国"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2Fjob.oschina.net%2F&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=cc18a91cff9b3ce69363a9763e9e021&src=haosou",
                "cite": "job.oschina.net"
            }
        },
        {
            "title": {
                "title": "Android 开发专区 - 开源中国社区",
                "keyWord": {
                    "15": "开源中国"
                }
            },
            "content": {
                "content": "06/03开源中国Android客户端v2.6.0发布 记得上一次发布版本,是上个月的事情了。 这一个月来,客户端团队的童鞋们任劳任怨,对社区客户端做了一次改进。 新功能大致如下: 「综...",
                "keywords": {
                    "5": "开源中国"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2Fwww.oschina.net%2Fandroid%2F&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=71dd93c16b2025796cc3fd7a137bd3c&src=haosou",
                "cite": "www.oschina.net/android/"
            }
        },
        {
            "title": {
                "title": "恒拓开源携“开源中国”挂牌新三板_36氪",
                "keyWord": {
                    "2": "开源",
                    "6": "开源中国"
                }
            },
            "content": {
                "content": "恒拓开源携“开源中国”近期可能挂牌新三板。恒拓开源2007年由马越创办,是一家开源技术服务企业。",
                "keywords": {
                    "2": "开源",
                    "6": "开源中国"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2F36kr.com%2Fp%2F5037310.html&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=9eb4a08da798535d05c05acfbe3555a&src=haosou",
                "cite": "36kr.com/p/5037310.html"
            }
        },
        {
            "title": {
                "title": "国产开源软件 - 开源中国社区",
                "keyWord": {
                    "2": "开源",
                    "9": "开源中国"
                }
            },
            "content": {
                "content": "目前开源中国已收录 6479 个国产开源软件 \"国产开源软件\"是指由国人发起或者国人参与的开源软件。我们深知开源无国界,技术无国界。这里讲“国产”只是为了推动国人参与开...",
                "keywords": {
                    "2": "开源中国",
                    "18": "开源"
                }
            },
            "url": {
                "link": "http://www.so.com/link?url=http%3A%2F%2Fwww.oschina.net%2Fproject%2Fzh%2F&q=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD&ts=1465267791&t=0b92c12134099bd98120e3de3ef64a5&src=haosou",
                "cite": "www.oschina.net>...>分类导航>国产开源软件"
            }
        }
    ],
    [
        {
            "title": {
                "title": "开源中国 - 找到您想要的开源项目,分享和交流",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国 www.oschina.net 是目前中国最大的开源技术社区。我们传播开源的理念，推广开源项目，为IT 开发者提供了一个发现、使用、并交流开...",
                "keywords": {
                    "0": "开源中国"
                }
            },
            "url": {
                "link": "http://www.sogou.com/link?url=DSOYnZeCC_qvobLwzgVpoFnhnIiBl3sq&query=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD",
                "cite": "开源中国社区 - www.oschina.net - \n\n 1天前"
            }
        },
        {
            "title": {
                "title": "开源项目大全 - 开源中国社区",
                "keyWord": {
                    "0": "开源",
                    "9": "开源中国"
                }
            },
            "content": {
                "content": "开源中国社区 www.oschina.net 成立于2008年8月，其目的是为中国的IT技术人员提供一个全面的、快捷更新的用来检索开源软件以及交流使用开源经验的平台，截止到 2009年10月...",
                "keywords": {
                    "1": "开源中国"
                }
            },
            "url": {
                "link": "http://www.sogou.com/link?url=DSOYnZeCC_qvobLwzgVpoLD0zENNRBonjFEQ5LTidr8.&query=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD",
                "cite": "开源中国社区 - www.oschina.net/pro... - 2015-12-19"
            }
        },
        {
            "title": {
                "title": "开源中国 - 搜狗百科",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "开源中国成立于2008年8月，是目前国内最大的开源技术社区，目前开源中国拥有130万会员，经过不断改进，已经形成了由开源软件库、代码分享、资讯、翻译、讨论...",
                "keywords": {
                    "0": "开源中国",
                    "32": "开源中国"
                }
            },
            "url": {
                "link": "http://www.sogou.com/link?url=DOb0bgH2eKjRiy6S-EyBciCDFRTZxEJgM1CgmFzsQIVVy6JvJfJZBvCJC-1zdrX6P3nlqjr_IGUYZ55de1avHwwBJaoC-fCQp-aPaidvnj5G27Y5k_1QxQ..&query=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD",
                "cite": "搜狗百科 - baike.sogou.com/v... - \n\n 2015-5-24"
            }
        },
        {
            "title": {
                "title": "在线工具 —— 开源中国社区",
                "keyWord": {
                    "8": "开源中国"
                }
            },
            "content": {
                "content": "开源中国在线工具，ostools为开发设计人员提供在线工具，提供jsbin在线 CSS、JS 调试，在线 Java API文档，在线 PHP API文档，在线 Node.js API文档，Less CSS编译器，...",
                "keywords": {
                    "1": "开源中国"
                }
            },
            "url": {
                "link": "http://www.sogou.com/link?url=a8xlm0X2uvfI0WaOXeukXCnOyEESjSdEreaVHN88tHY.&query=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD",
                "cite": "开源中国社区 - tool.oschina.net/ - 2016-5-30"
            }
        },
        {
            "title": {
                "title": "开源中国（OSChina.NET）",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "15小时前（0条评论） 众包悬赏 | 码云的 Visual Studio 扩展开发（开源） oschina 发布于 16小时前（0条评论） 微软声明： 没放弃 Windows 手机 PythonCode 发布于 16小时前（0...",
                "keywords": {
                    "43": "开源"
                }
            },
            "url": {
                "link": "http://www.sogou.com/link?url=58p16RfDRLtWuEtB0fSyrLL_BBbXp9AM&query=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD",
                "cite": "开源中国社区 - m.oschina.net/ - 2016-5-27"
            }
        },
        {
            "title": {
                "title": "开源中国_热门回答_知乎",
                "keyWord": {
                    "0": "开源中国"
                }
            },
            "content": {
                "content": "如何评价开源中国社区?如何评论开源中国的山寨github?",
                "keywords": {
                    "4": "开源中国",
                    "15": "开源中国"
                }
            },
            "url": {
                "link": "http://www.sogou.com/link?url=DSOYnZeCC_qbIE3Bv5WAGLmffmE_Sw6vXFizj1xksrBlo6gdLpfmmw..&query=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD",
                "cite": "知乎 - www.zhihu.com/t... - 2013-7-2"
            }
        },
        {
            "title": {
                "title": "开源学习园地 - 开源中国社区",
                "keyWord": {
                    "0": "开源",
                    "9": "开源中国"
                }
            },
            "content": {
                "content": "[图文]科长结论：学计算的 80%是傻逼 而是是纯纯的那种 自由以来 傻逼做不出来产品 就会上github开源中国 对中国进贡称之为属国 代表韩国人 中国人。自古以来 牛逼做不出来东西 ...",
                "keywords": {
                    "54": "开源中国"
                }
            },
            "url": {
                "link": "http://www.sogou.com/link?url=58p16RfDRLvsgRwZ6iNBPiugtGPXyE-Ymro26Oa23_c.&query=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD",
                "cite": "开源中国社区 - my.oschina.net/s... - 2015-11-20"
            }
        },
        {
            "title": {
                "title": "Linux.中国-开源社区",
                "keyWord": {
                    "6": "中国",
                    "9": "开源"
                }
            },
            "content": {
                "content": "Linux中国是一家创立于2003年的国内开源社区，主要关注方向在 Linux 推广、Linux 技术研究、Linux 业界事件的传播。Linux 中国包括网站、微博、微信和邮件列表等平台。",
                "keywords": {
                    "5": "中国",
                    "21": "开源",
                    "74": "中国"
                }
            },
            "url": {
                "link": "http://www.sogou.com/link?url=X4Yt6GhHd2rpvYfC4oohsa3mlRzfPLR2&query=%E5%BC%80%E6%BA%90%E4%B8%AD%E5%9B%BD",
                "cite": "linux.cn - 2016-5-29"
            }
        }
    ]
]
```