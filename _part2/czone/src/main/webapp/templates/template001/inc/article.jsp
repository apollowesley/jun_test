<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<div class="col-sm-8 blog-main">
    <div class="blog-body overflow-initial fade-in">
        <div class="article-flag">
            <span class="article-blockquote article-blockquote-green"></span>
            <span class="article-original article-original-green">
								<a href="detail.html">原创</a>
							</span>
            <div class="blog-info-meta pull-right">
                <ul class="list-unstyled list-inline">
                    <li><i class="fa fa-clock-o fa-fw"></i>发表于 <fmt:formatDate value="${journalSchema.createTime}" pattern="yyyy年MM月dd日" /></li>
                    <li><i class="fa fa-eye fa-fw"></i>
                        <a class="pointer" data-original-title="${journalSchema.views}人浏览了该文章" data-toggle="tooltip" data-placement="bottom">浏览 (
                            <num>${journalSchema.views}</num>)</a>
                    </li>
                    <li>
                        <a href="detail.html#comment-box" data-original-title="0人评论了该文章" data-toggle="tooltip" data-placement="bottom"><i class="fa fa-comments-o fa-fw"></i>评论 (0)</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="blog-info overflow-initial">
            <div class="bottom-line">
                <h1 class="blog-info-title">
                    <strong>${journalSchema.title }</strong>
                </h1>
            </div>
            <div class="blog-info-body">
                <span id="menu_0" class="menu-point"></span>
                <h2 class="menu-title">前言</h2>
                <p>${journalSchema.detail }</p>
                <h2 class="menu-title"><span>参考资料</span></h2>
                <p><span>1.</span>
                    <a href="//www.cnblogs.com/lishuxue/p/6008678.html" target="_blank">Cordova入门系列（一）创建项目</a>
                </p>
                <p>2.
                    <a href="//www.cnblogs.com/a418120186/p/5226417.html" target="_blank">Cordova之如何用命令行创建一个项目（完整示例）</a>
                </p>
                <p>3.
                    <a href="http://cordova.axuer.com/docs/zh-cn/latest/guide/cli/index.html" target="_blank">Cordova中文文档</a>
                </p>
                <p><br></p>
            </div>
            <div class="separateline"><span>正文到此结束</span></div>
            <div id="social" style="margin-bottom: 45px;">
                <div class="social-main">
									<span class="like">
										<a href="javascript:;" data-id="92" title="点赞"><i class="fa fa-thumbs-up"></i>赞 <i class="count"> 1</i> </a>
									</span>
                    <div class="shang-p">
                        <div class="shang-empty"><span></span></div>
                        <span class="shang-s"><a onclick="PaymentUtils.show();" style="cursor:pointer">赏</a> </span>
                    </div>
                    <div class="share-sd">
                        <span class="share-s"><a href="javascript:void(0)" id="share-s" title="分享"><i class="fa fa-share-alt"></i>分享</a></span>
                        <div id="share" style="display: none">
                            <ul class="bdsharebuttonbox bdshare-button-style1-24" data-bd-bind="1523411015945">
                                <li>
                                    <a title="分享到人人网" class="fa fa-renren" data-cmd="renren" onclick="return false;" href="detail.html#"></a>
                                </li>
                                <li>
                                    <a title="分享到腾讯微博" class="fa fa-pinterest-square" data-cmd="tqq" onclick="return false;" href="detail.html#"></a>
                                </li>
                                <li>
                                    <a title="分享到QQ空间" class="fa fa-qq" data-cmd="qzone" onclick="return false;" href="detail.html#"></a>
                                </li>
                                <li>
                                    <a title="分享到新浪微博" class="fa fa-weibo" data-cmd="tsina" onclick="return false;" href="detail.html#"></a>
                                </li>
                                <li>
                                    <a title="分享到微信" class="fa fa-weixin" data-cmd="weixin" onclick="return false;" href="detail.html#"></a>
                                </li>
                                <li>
                                    <a title="更多" class="bds_more fa fa-plus-square" data-cmd="more" onclick="return false;" href="detail.html#"></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="article-footer overflow-initial">所属分类：
                <a href="//www.zhyd.me/type/2" data-original-title="点击查看后端技术分类的文章" data-toggle="tooltip" data-placement="bottom">后端技术</a>
            </div>
        </div>
    </div>
    <div class="blog-body article-tag">
        <div class="cat">
            <ul class="list-unstyled">
                <li>
                    <strong>本文标签：</strong>
                    <a href="//www.zhyd.me/tag/32" class="c-label" data-original-title="Cordova" data-toggle="tooltip" data-placement="bottom" target="_blank">${journalSchema.tags }</a>
                    <a href="//www.zhyd.me/tag/33" class="c-label" data-original-title="Android" data-toggle="tooltip" data-placement="bottom" target="_blank">Android</a>
                </li>
                <li>
                    <strong>版权声明：</strong> 本站原创文章，于2018年04月08日由
                    <a href="//www.zhyd.me/" target="_blank" data-original-title="张亚东博客" data-toggle="tooltip" data-placement="bottom"><strong>张亚东</strong></a>发布，转载请注明出处
                </li>
            </ul>
        </div>
    </div>
    <div class="blog-body">
        <a href="//promotion.aliyun.com/ntms/act/ambassador/sharetouser.html?userCode=wylo59db&amp;utm_source=wylo59db" target="_blank" rel="external nofollow">
            <img src="${templatePath}img/ad/aliyun_sale1000-60.png" alt="阿里云首购8折" class="img-responsive">
        </a>
    </div>
    <div class="blog-body prev-next">
        <nav class="nav-single wow" data-wow-delay="0.3s">
            <a href="detail.html" rel="prev">
								<span class="meta-nav" data-original-title="【漏洞公告】Spring Framework多个安全漏洞预警" data-toggle="tooltip" data-placement="bottom"><span class="post-nav"><i class="fa fa-angle-left"></i> 上一篇</span>
								<br>【漏洞公告】Spring Framework多个安全漏洞预警
								</span>
            </a>
            <a href="detail.html" rel="nofollow next">
								<span class="meta-nav" data-original-title="已经到最后一篇了" data-toggle="tooltip" data-placement="bottom"><span class="post-nav">下一篇 <i class="fa fa-angle-right"></i></span>
								<br>已经到最后一篇了
								</span>
            </a>
            <div class="clear"></div>
        </nav>
    </div>
    <div class="blog-body clear overflow-initial">
        <h4 class="bottom-line"><i class="fa fa-fire icon"></i><strong>热门推荐</strong></h4>
        <ul class="list-unstyled">
            <li class="line-li">
                <div class="line-container">
                    <div class="line-left">
                        <img class="lazy-img" data-original="http://p4c3z3uxb.bkt.clouddn.com/flyat/cover/20170405174038272.png" width="50" height="50" rel="external nofollow">
                    </div>
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="778人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                Springboot + Freemarker项目中使用自定义注解
                            </a>
                        </div>
                        <div class="text">
                            <span class="views" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(778)</span>
                            <span class="comment" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章评论次数">
												<a href="detail.html#comment-box" rel="external nofollow">
													<i class="fa fa-comments-o fa-fw"></i>评论(0)
												</a>
											</span>
                        </div>
                    </div>
                </div>
            </li>
            <li class="line-li">
                <div class="line-container">
                    <div class="line-left">
                        <img class="lazy-img" data-original="http://p4c3z3uxb.bkt.clouddn.com/flyat/cover/20170502143953315.jpg" width="50" height="50" rel="external nofollow">
                    </div>
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="297人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                Linux中ImageIO生成图片中文乱码
                            </a>
                        </div>
                        <div class="text">
                            <span class="views" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(297)</span>
                            <span class="comment" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章评论次数">
												<a href="detail.html#comment-box" rel="external nofollow">
													<i class="fa fa-comments-o fa-fw"></i>评论(0)
												</a>
											</span>
                        </div>
                    </div>
                </div>
            </li>
            <li class="line-li">
                <div class="line-container">
                    <div class="line-left">
                        <img class="lazy-img" data-original="http://p4c3z3uxb.bkt.clouddn.com/flyat%2Fcover%2F20180301205820.jpg" width="50" height="50" rel="external nofollow">
                    </div>
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="250人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                解决微信小程序无法下载非指定域名文件的方案
                            </a>
                        </div>
                        <div class="text">
                            <span class="views" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(250)</span>
                            <span class="comment" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章评论次数">
												<a href="detail.html#comment-box" rel="external nofollow">
													<i class="fa fa-comments-o fa-fw"></i>评论(0)
												</a>
											</span>
                        </div>
                    </div>
                </div>
            </li>
            <li class="line-li">
                <div class="line-container">
                    <div class="line-left">
                        <img class="lazy-img" data-original="http://p4c3z3uxb.bkt.clouddn.com/website/img/favicon.ico" width="50" height="50" rel="external nofollow">
                    </div>
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="229人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                记一次服务器日志查看及BUG维护
                            </a>
                        </div>
                        <div class="text">
                            <span class="views" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(229)</span>
                            <span class="comment" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章评论次数">
												<a href="detail.html#comment-box" rel="external nofollow">
													<i class="fa fa-comments-o fa-fw"></i>评论(0)
												</a>
											</span>
                        </div>
                    </div>
                </div>
            </li>
            <li class="line-li">
                <div class="line-container">
                    <div class="line-left">
                        <img class="lazy-img" data-original="http://p4c3z3uxb.bkt.clouddn.com/flyat%2Fcover%2F1518335776791.jpg" width="50" height="50" rel="external nofollow">
                    </div>
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="207人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                下半年起，谷歌浏览器将把所有HTTP网站标记为“不安全”
                            </a>
                        </div>
                        <div class="text">
                            <span class="views" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(207)</span>
                            <span class="comment" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章评论次数">
												<a href="detail.html#comment-box" rel="external nofollow">
													<i class="fa fa-comments-o fa-fw"></i>评论(0)
												</a>
											</span>
                        </div>
                    </div>
                </div>
            </li>
            <li class="line-li">
                <div class="line-container">
                    <div class="line-left">
                        <img class="lazy-img" data-original="http://p4c3z3uxb.bkt.clouddn.com/flyat%2Fcover%2F20180316171033.png" width="50" height="50" rel="external nofollow">
                    </div>
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="199人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                通过js注入实现单页面显示多条“一言”
                            </a>
                        </div>
                        <div class="text">
                            <span class="views" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(199)</span>
                            <span class="comment" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章评论次数">
												<a href="detail.html#comment-box" rel="external nofollow">
													<i class="fa fa-comments-o fa-fw"></i>评论(0)
												</a>
											</span>
                        </div>
                    </div>
                </div>
            </li>
            <li class="line-li">
                <div class="line-container">
                    <div class="line-left">
                        <img class="lazy-img" data-original="http://p4c3z3uxb.bkt.clouddn.com/flyat/cover/20170601134846507.png" width="50" height="50" rel="external nofollow">
                    </div>
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="190人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                配置MySQL开启远程连接的方法
                            </a>
                        </div>
                        <div class="text">
                            <span class="views" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(190)</span>
                            <span class="comment" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章评论次数">
												<a href="detail.html#comment-box" rel="external nofollow">
												<i class="fa fa-comments-o fa-fw"></i>评论(0)
												</a>
											</span>
                        </div>
                    </div>
                </div>
            </li>
            <li class="line-li">
                <div class="line-container">
                    <div class="line-left">
                        <img class="lazy-img" data-original="http://p4c3z3uxb.bkt.clouddn.com/website/img/favicon.ico" width="50" height="50" rel="external nofollow">
                    </div>
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="181人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                若有来世，我必褪去这身袈裟，陪你浪迹天涯
                            </a>
                        </div>
                        <div class="text">
                            <span class="views" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(181)</span>
                            <span class="comment" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="文章评论次数">
												<a href="detail.html#comment-box" rel="external nofollow">
												<i class="fa fa-comments-o fa-fw"></i>评论(0)
												</a>
											</span>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="blog-body clear overflow-initial">
        <h4 class="bottom-line"><i class="fa fa-google-wallet icon"></i><strong>相关文章</strong></h4>
        <ul class="list-unstyled">
            <li class="line-li">
                <div class="line-container">
                    <div class="line-right">
                        <div class="text">
                            <a href="detail.html" data-original-title="89人浏览了该文章" data-toggle="tooltip" data-placement="bottom">
                                Cordova入门（一）创建android项目
                            </a>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="blog-body clear overflow-initial expansion gray">
        <i class="fa fa-close fa-fw"></i>该篇文章的评论功能已被站长关闭
    </div>
</div>