<template>
  <div class="p-3">

    <div class="px-3 pb-2 text-md-left">
      <span class="align-middle"><i class="fa fa-bar-chart"></i> 报表、统计</span>
    </div>

    <div class="row row-gap-5 text-center">
      <div class="col-lg-2 col-sm-4 pointer">
        <div class="card mb-3 p-2" v-ripple>
          <div class="to-corner h5 m-0">
            <div class="bg-primary rad-15 px-2 py-1 text-white">615</div>
          </div>
          <div class="my-3"><i class="fa fa-flag fa-3x"></i></div>
          <div class="text-center h4 m-0 text-muted">直播主题</div>
        </div>
      </div>
      <div class="col-lg-2 col-sm-4 pointer">
        <div class="card mb-3 p-2" v-ripple>
          <div class="my-3"><i class="fa fa-list-alt fa-3x"></i></div>
          <div class="text-center h4 m-0 text-muted">直播用户</div>
        </div>
      </div>
      <div class="col-lg-2 col-sm-4 pointer">
        <div class="card mb-3 p-2" v-ripple>
          <div class="to-corner h5 m-0">
            <div class="bg-primary rad-15 px-2 py-1 text-white">138</div>
          </div>
          <div class="my-3"><i class="fa fa-users fa-3x"></i></div>
          <div class="text-center h4 m-0 text-muted">系统用户</div>
        </div>
      </div>
      <div class="col-lg-2 col-sm-4 pointer">
        <div class="card mb-3 p-2" v-ripple>
          <div class="to-corner h5 m-0">
            <div class="bg-warning rad-15 px-2 py-1 text-white">45</div>
          </div>
          <div class="my-3"><i class="fa fa-hdd-o fa-3x"></i></div>
          <div class="text-center h4 m-0 text-muted">Servers</div>
        </div>
      </div>
      <div class="col-lg-4 col-sm-8 pointer text-left">
        <div class="row row-gap-5">
          <div class="col-6">
            <div class="card mb-3 p-2 b-primary">
              <h2 class="m-0">$7,221</h2>
              <div class="text-muted mb-3">Budget</div>
              <div class="text-green">
                <i class="fa fa-long-arrow-up fa-2x"></i>
                <span class="h5 m-0">+15% +$45</span>
              </div>
            </div>
          </div>
          <div class="col-6">
            <div class="card mb-3 p-2 b-danger">
              <h2 class="m-0">15,259</h2>
              <div class="text-muted mb-3">Downloads</div>
              <div class="text-danger">
                <i class="fa fa-long-arrow-down fa-2x"></i>
                <span class="h5 m-0"> -0.5% -123</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row row-gap-5">
      <div class="col-md-12">
        <div class="card mb-3">
          <div class="card-header"><i class="fa fa-dashboard"></i> 欢迎使用学乎管理平台</div>
          <div class="card-body">
            <div class="pb-3">关于学乎管理平台:</div>
            <div class="lead mb-3">
              <h5>学乎管理平台是一个将 SPA (Single Page Application) 的理念应用到后台管理系统上，一个完全使用AJAX交互的系统平台。</h5>
            </div>
            <div class="progress progress-xs mb-3"><span class="progress-bar bg-success" style="width: 35%"></span></div>
            <div class="my-2 text-center">
              <a href="https://www.baidu.com/s?wd=Single%20page%20application" target="_blank" class="btn rad-30 btn-success">READ MORE</a>
            </div>
          </div>
        </div>

        <div class="card mb-3 b-0">
          <ul class="nav nav-tabs nav-line" role="tablist">
            <li class="nav-item"><a class="nav-link active" data-toggle="tab" data-target="#changlog" role="tab"><i class="fa fa-tag"></i> 最近更新</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" data-target="#timeline" role="tab"><i class="fa fa-clock-o"></i> 更新时间轴</a></li>
          </ul>
          <div class="tab-content p-3">
            <div class="tab-pane active" id="changlog" role="tabpanel">
              <h1 class="text-primary">v{{changelog[0].version}}</h1>
              <p>{{changelog[0].time}}日发布, 主要变动以及BUG修复如下：</p>
              <ul>
                <li class="mb-2" v-for="(fix, idx) in changelog[0].changes" v-html="fix"></li>
              </ul>
            </div>
            <div class="tab-pane" id="timeline" role="tabpanel">
              <div class="float-right">
                <label class="ui-checkbox">
                  <input type="checkbox" onclick="$('#changelog-timeline').toggleClass('center')"><i></i> 居中对齐
                </label>
                <label class="ui-checkbox">
                  <input type="checkbox" onclick="$('#changelog-timeline').toggleClass('blocked')"><i></i> 块级显示
                </label>
              </div>
              <div id="changelog-timeline" class="timeline center">
                <div class="timeline-heading"><span>今天</span></div>
                <div class="timeline-panel" v-for="(item,  index) in changelog">
                  <div class="timeline-content">
                    <div class="timeline-date">{{item.time}}</div>
                    <div class="card mb-3 p-3 b-0">
                      <h1 class="text-primary">v{{item.version}}</h1>
                      <p>{{item.time}}主要变动以及BUG修复如下：</p>
                      <ul>
                        <li class="mb-2" v-for="(fix, idx) in item.changes" v-html="fix"></li>
                      </ul>
                    </div>
                  </div>
                </div>
                <div class="timeline-heading">项目启动</div>
              </div>
            </div>
          </div>
        </div>

        <div class="card mb-3 b-warning">
          <div class="card-header"><i class="fa fa-cubes"></i> 浏览器支持 / 技术特性</div>
          <div class="card-body">
            <dl class="row">
              <dt class="col-sm-2 text-truncate">浏览器支持</dt>
              <dd class="col-sm-10">
                IE 10, IE 11, Edge, Firefox, Google Chrome, Opera, Safari 等, 以及其它支持 <code>HTML5</code>的现代浏览器.<br>
                页面自适应, HTML编写遵守Bootstrap样式规范，适配不同屏幕大小的设备 .
              </dd>
              <dt class="col-sm-2 text-truncate">后端主要框架</dt>
              <dd class="col-sm-10">
                核心框架<code>Spring Framework 4</code> / 安全框架<code>Apache Shiro 1.2</code> / 视图框架<code>Spring MVC 4</code> / 持久框架<code>MyBatis 3</code>.
                验证框架<code>JSR303 Bean Validation</code>.
              </dd>
              <dt class="col-sm-2 text-truncate">前端主要框架</dt>
              <dd class="col-sm-10">
                核心框架<code>Vue.js v2.3.4</code> / 布局框架<code>Twiter Bootstrap v4.0.0-beta</code> / 验证框架<code>Parsley.js v2.3.11</code> / 树形控件<code>jstree</code> / 树形表格<code>jquery-treetable</code> 等.
              </dd>
            </dl>
          </div>
        </div>

        <div class="row row-gap-5 mb-3">
          <div class="col-md-8">
            <div class="card">
              <div class="card-header"><i class="fa fa-info-circle"></i> Main server #1</div>
              <div class="card-body py-0">
                <div class="row bordered-columns">
                  <div class="col-md-4 col-sm-6">
                    <div class="mb-5">
                      <span class="h4">Virtual #1</span><span class="h4 float-right text-green m-0">
                    <i class="fa fa-check-circle"></i> Healty</span>
                    </div>
                    <div class="mb-2">/path/<strong>virtual1</strong></div>
                    <div class="mb-2">
                      <span class="h1">2.72</span><span> TB</span>
                    </div>
                    <div class="mb-5">Volume size</div>
                    <div class="relative mb-2">
                      <div id="chart1" class="easy-chart" style="width: 140px;height: 140px" data-percent="55">
                        <span class="percent" style="line-height: 140px">55</span>
                      </div>
                    </div>
                    <table class="table table-sm table-no-border text-center mb-2">
                      <tbody>
                      <tr>
                        <td><i class="icon-circle text-green"></i> Example</td>
                        <td><i class="icon-circle" style="color:#f2f2f2"></i>Example</td>
                      </tr>
                      <tr>
                        <td><h4 class="m-0">0.81 TB</h4></td>
                        <td><h4 class="m-0">1.66 TB</h4></td>
                      </tr>
                      <tr>
                        <td><h4 class="m-0">29%</h4></td>
                        <td><h4 class="m-0">55%</h4></td>
                      </tr>
                      <tr>
                        <td colspan="2"><a href="#" class="btn btn-secondary btn-xs rounded">
                          <i class="fa fa-chevron-right"></i> ssh</a></td>
                      </tr>
                      </tbody>
                    </table>
                  </div>
                  <div class="col-md-4 col-sm-6">
                    <div class="mb-5">
                      <span class="h4">Virtual #2</span><span class="h4 float-right text-green m-0">
                    <i class="fa fa-check-circle"></i> Healty</span>
                    </div>
                    <div class="mb-2">
                      /path/<strong>virtual1</strong>
                    </div>
                    <div class="mb-2">
                      <span class="h1">1.05</span><span> TB</span>
                    </div>
                    <div class="mb-5">Volume size</div>
                    <div class="relative mb-2">
                      <div id="chart2" class="easy-chart" style="width: 140px;height: 140px" data-percent="75">
                        <span class="percent" style="line-height: 140px">75</span>
                      </div>
                    </div>
                    <table class="table table-sm table-no-border text-center mb-2">
                      <tbody>
                      <tr>
                        <td><i class="icon-circle text-green"></i> Example</td>
                        <td><i class="icon-circle" style="color:#f2f2f2"></i>Example</td>
                      </tr>
                      <tr>
                        <td><h4 class="m-0">0.81 TB</h4></td>
                        <td><h4 class="m-0">1.66 TB</h4></td>
                      </tr>
                      <tr>
                        <td><h4 class="m-0">29%</h4></td>
                        <td><h4 class="m-0">55%</h4></td>
                      </tr>
                      <tr>
                        <td colspan="2">
                          <a href="#" class="btn btn-secondary btn-xs rounded">
                            <i class="fa fa-chevron-right"></i> ssh</a>
                        </td>
                      </tr>
                      </tbody>
                    </table>
                  </div>
                  <div class="col-md-4">
                    <div class="mb-5">
                      <span class="h4">Virtual #3</span>
                      <span class="h4 float-right text-green m-0"><i class="fa fa-check-circle"></i> Healty</span>
                    </div>
                    <div class="mb-2">/path/<strong>virtual1</strong></div>
                    <div class="mb-2">
                      <span class="h1">5.67</span><span> TB</span>
                    </div>
                    <div class="mb-5">Volume size</div>
                    <div class="relative mb-2">
                      <div id="chart3" class="easy-chart" style="width: 140px; height: 140px" data-percent="95">
                        <span class="percent" style="line-height: 140px">95</span>
                      </div>
                    </div>
                    <table class="table table-sm table-no-border text-center mb-2">
                      <tbody>
                      <tr>
                        <td><i class="icon-circle text-green"></i> Example</td>
                        <td><i class="icon-circle" style="color:#f2f2f2"></i>Example</td>
                      </tr>
                      <tr>
                        <td><h4 class="m-0">0.81 TB</h4></td>
                        <td><h4 class="m-0">1.66 TB</h4></td>
                      </tr>
                      <tr>
                        <td><h4 class="m-0">29%</h4></td>
                        <td><h4 class="m-0">55%</h4></td>
                      </tr>
                      <tr>
                        <td colspan="2"><a href="#" class="btn btn-secondary btn-xs rounded">
                          <i class="fa fa-chevron-right"></i> ssh</a></td>
                      </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card mb-3">
              <form class="bb-l" method="post">
                <div class="input-group">
                  <input class="form-control form-control-lg b-0" name="content" placeholder="BUG or 建议">
                  <span class="input-group-btn bl-l">
                <button type="button" class="btn btn-outline-info btn-lg b-0 ml-0 rad-0">添加</button>
              </span>
                </div>
              </form>
              <ul class="list-unstyled m-0 p-0 b-0"></ul>
            </div>
            <div class="card-body p-0 py-3">
              <div class="datepicker-here" data-inline="true"></div>
            </div>
          </div>
        </div>

        <div class="card widget-weather">
          <div class="row">
            <div class="col-md-4">
              <div class="actual text-white" style="background:url(/static/img/london.jpg) center; background-size:cover">
                <p>Today</p>
                <h2 class="m-0"><i class="icon-map-marker"></i> London</h2>
                <h3 class="m-0">73°</h3>
              </div>
            </div>
            <div class="col-md-8">
              <div class="row days">
                <div class="col-md-3 col-sm-6" v-for="item in 4">
                  <p>Tue</p>
                  <h5>Partly Cloudy</h5>
                  <h3>78°</h3>
                  <h3>53°</h3>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
  import changelog from '../assets/changelog'
  export default {
    data: () => ({
      colors: ['success', 'info', 'danger', 'primary', 'warning', ''],
      changelog: changelog
    }),
    mounted () {
      this.$emit('loaded')
    }
  }
</script>
