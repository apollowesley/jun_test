<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="main13" component="$UI/system/components/justep/window/window"
  design="device:m;" xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:13px;top:202px;"
    onLoad="modelLoad"> 
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="newsData" idColumn="fID" onCustomRefresh="productDataCustomRefresh"> 
      <column label="id" name="fID" type="String" xid="xid1"/>  
      <column label="标题" name="fTitle" type="String" xid="xid2"/>  
      <column label="图片" name="fImg" type="String" xid="xid3"/>  
      <column label="摘要" name="fOmit" type="String" xid="xid4"/>  
      <column label="跟帖" name="fPostNumber" type="Integer" xid="xid5"/>  
      <rule xid="rule1"> 
        <col name="fPostNumber" xid="ruleCol1"> 
          <calculate xid="calculate1"> 
            <expr xid="default1">$row.val("fPostNumber")&gt;=0?$row.val("fPostNumber")+"跟贴":"";</expr> 
          </calculate> 
        </col> 
      </rule> 
    </div>  
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="navData" onCustomRefresh="navDataCustomRefresh" idColumn="fID"> 
      <column label="id" name="fID" type="String" xid="xid6"/>  
      <column label="名称" name="fName" type="String" xid="xid7"/>  
      <column label="链接地址" name="fUrl" type="String" xid="xid8"/> 
    </div> 
  </div>  
  <div component="$UI/system/components/justep/popOver/popOver" class="x-popOver x-wangyiOver"
    xid="popOver" style="height:100%;" anchor="navMenuTop" direction="right-bottom"
    opacity="0.5"> 
    <div class="x-popOver-overlay" xid="div2"/>  
    <div xid="div4" class="x-full x-popOver-content panel"> 
      <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon pull-right"
        icon="icon-ios7-arrow-up" onClick="{operation:'popOver.hide'}" xid="button1"> 
        <i xid="i9" class="text-muted icon-ios7-arrow-up"/> 
      </a>  
      <h5 class="text-muted">切换栏目</h5>  
      <div component="$UI/system/components/justep/list/list" class="x-list o-nomargin x-navGroup panel-body"
        xid="list1" data="navData" dataItemAlias="navRow" bind-click="navLiClick"> 
        <ul class="x-list-template" xid="listTemplateUl1"> 
          <li xid="li1" class="col col-xs-3"> 
            <div class="btn btn-link" bind-css="{'active':navRow.val('fID')==$model.navData.val('fID')}">
              <span bind-text="ref('fName')" xid="span1"/>
            </div> 
          </li> 
        </ul> 
      </div>
      <div xid="div9" class="panel-heading" bind-text="'点击添加更多栏目'"/>  
      <div component="$UI/system/components/bootstrap/row/row" class="row o-nomargin x-navGroup panel-body bg-muted"
        xid="row3"> 
        <div class="col col-xs-3" xid="col9"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="NBA" xid="button11" target=""> 
            <i xid="i25"/>  
            <span xid="span31">NBA</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="社会" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">社会</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col11"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="哒哒" xid="button8" target=""> 
            <i xid="i27"/>  
            <span xid="span28">哒哒</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col10"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="影视" xid="button9" target=""> 
            <i xid="i24"/>  
            <span xid="span30">影视</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="彩票" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">彩票</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label o-nopadding"
            label="中国足球" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">中国足球</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="CBA" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">CBA</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="跑步" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">跑步</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="手机" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">手机</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
            label="数码" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">数码</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label o-nopadding"
            label="移动互联" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">移动互联</span> 
          </a> 
        </div>  
        <div class="col col-xs-3" xid="col8"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label o-nopadding"
            label="云课堂" xid="button10" target=""> 
            <i xid="i26"/>  
            <span xid="span29">云课堂</span> 
          </a> 
        </div> 
      </div> 
    </div> 
  </div>  
  <div component="$UI/system/components/justep/popOver/popOver" class="x-popOver"
    xid="popOverMore" anchor="titleBar" position="center" style="height:100%;"> 
    <div class="x-popOver-overlay" xid="div13"/>  
    <div class="x-full x-popOver-content panel" xid="div14"> 
      <div xid="div15" class="panel-body"> 
        <div xid="div19" class="col-xs-6 pull-left media"> 
          <h1 xid="h11" class="media-left text-danger"><![CDATA[21]]></h1>  
          <div xid="div27" class="media-body"> 
            <span xid="span14" class="text-danger"><![CDATA[℃]]></span>  
            <p xid="p1" bind-text="'10/22℃'"/> 
          </div> 
        </div>  
        <div xid="div26" class="col-xs-6 pull-left text-right"> 
          <img src="" alt="" xid="image15" bind-attr-src="$model.getImageUrl('./img/cloud.png')"
            class="img_cloud"/>  
          <div xid="div28" bind-text="'多云 微风'"/>  
          <div xid="div29" bind-text="'昆明'"/> 
        </div>  
        <div xid="div30" class="col-xs-12 pull-left"> 
          <span xid="span21"><![CDATA[2015/10/14 星期五]]></span>  
          <p xid="p2" bind-text="'PM2.5 24 优'"/> 
        </div> 
      </div>  
      <div component="$UI/system/components/bootstrap/row/row" class="row text-center panel-body o-nomargin o-nopadding"
        xid="row4"> 
        <div class="col col-xs-4 o-nopadding" xid="col12"> 
          <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/more1.png')"
            class="img3" xid="image5"/>  
          <div bind-text="'搜索'" xid="div25" class="text-muted h6"/> 
        </div>  
        <div class="col col-xs-4 o-nopadding" xid="col14"> 
          <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/more2.png')"
            class="img3" xid="image12"/>  
          <div bind-text="'上头条'" xid="div24" class="text-muted h6"/> 
        </div>  
        <div class="col col-xs-4 o-nopadding" xid="col13"> 
          <img src="" alt="" xid="image5" bind-attr-src="$model.getImageUrl('./img/more3.png')"
            class="img3"/>  
          <div bind-text="'离线'" xid="div23" class="text-muted h6"/> 
        </div>  
        <div class="col col-xs-4 o-nopadding" xid="col12"> 
          <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/more4.png')"
            class="img3" xid="image5"/>  
          <div bind-text="'夜间'" xid="div25" class="text-muted h6"/> 
        </div>  
        <div class="col col-xs-4 o-nopadding" xid="col12"> 
          <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/more5.png')"
            class="img3" xid="image5"/>  
          <div bind-text="'扫一扫'" xid="div25" class="text-muted h6"/> 
        </div>  
        <div class="col col-xs-4 o-nopadding" xid="col12"> 
          <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/more6.png')"
            class="img3" xid="image5"/>  
          <div bind-text="'邀请好友'" xid="div25" class="text-muted h6"/> 
        </div> 
      </div> 
    </div> 
  </div>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-card top-trans"
    xid="panel1"> 
    <div class="x-panel-content x-cards" xid="content"> 
      <div component="$UI/system/components/justep/contents/contents" class="x-contents x-full"
        active="0" xid="contents1" wrap="false" swipe="false" onActiveChange="contents1ActiveChange"> 
        <div class="x-contents-content" xid="newsContent" onActive="newsContentActive"> 
          <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-has-iosstatusbar"
            xid="panel7"> 
            <div class="x-panel-top" xid="top1" height="48"> 
              <div component="$UI/system/components/justep/titleBar/titleBar"
                class="x-titlebar" xid="titleBar"> 
                <div class="x-titlebar-left" xid="div1"> 
                  <img src="./img/navbar_logo.png" alt="" xid="image3" height="26px"/> 
                </div>  
                <div class="x-titlebar-title"> 
                  <!--<div class="form-group has-feedback text-muted"> 
            <input type="text" class="form-control" id="inputSuccess2" aria-describedby="inputSuccess2Status"
              xid="key"/>  
            <span class="glyphicon glyphicon glyphicon-search form-control-feedback"
              aria-hidden="true"/> 
          </div>   --> 
                </div>  
                <div class="x-titlebar-right reverse" xid="div3"> 
                  <a component="$UI/system/components/justep/button/button"
                    class="btn btn-link btn-only-icon" label="button" xid="moreBtn"
                    icon="icon-android-more" onClick="{operation:'popOverMore.show'}"> 
                    <i xid="i2" class="icon-android-more"/>  
                    <span xid="span3"/> 
                  </a>  
                  <img src="./img/icon24.png" alt="" xid="importantBtn" height="26px" bind-click="importantBtnClick" /> 
                </div> 
              </div> 
            </div>  
            <div class="x-panel-content" xid="content1"> 
              <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full"
                xid="panel2"> 
                <div class="x-panel-top" xid="navMenuTop"> 
                  <div component="$UI/system/components/justep/row/row" class="x-row x-nav"
                    xid="row5"> 
                    <div class="x-col" xid="col15"> 
                      <div class="x-navbox"> 
                        <div component="$UI/system/components/justep/list/list"
                          class="x-list" xid="navList" data="navData" dataItemAlias="navRow"
                          bind-click="navLiClick"> 
                          <ul class="x-list-template" xid="listTemplateUl2"> 
                            <li xid="li2" class="btn btn-link" bind-css="{'current':navRow.val('fID')==$model.navData.val('fID')}"> 
                              <span bind-text="ref('fName')"><![CDATA[]]></span> 
                            </li> 
                          </ul> 
                        </div> 
                      </div> 
                    </div>  
                    <div class="x-col x-col-fixed" xid="col16"> 
                      <a component="$UI/system/components/justep/button/button"
                        class="btn btn-link btn-only-icon pull-right center-block navMoreBtn"
                        label="button" xid="button19" icon="icon-ios7-arrow-down"
                        onClick="{operation:'popOver.show'}"> 
                        <i xid="i15" class="text-muted icon-ios7-arrow-down"/>  
                        <span xid="span7"/> 
                      </a> 
                    </div> 
                  </div> 
                </div>  
                <div class="x-panel-content" xid="content2"> 
                  <div component="$UI/system/components/justep/windowContainer/windowContainer"
                    class="x-window-container" xid="windowContainer1" src="./index_main1.w"/>
                </div> 
              </div> 
            </div> 
          </div> 
        </div>  
        <div class="x-contents-content" xid="readContent"> 
          <div component="$UI/system/components/justep/windowContainer/windowContainer"
            class="x-window-container" xid="mainContainer2" style="height:100%;width:100%;"
            src="./index-read.w" autoLoad="false"/> 
        </div>  
        <div class="x-contents-content" xid="audioContent"> 
          <div component="$UI/system/components/justep/windowContainer/windowContainer"
            class="x-window-container" xid="mainContainer3" src="./index-audio.w"
            autoLoad="false"/> 
        </div>  
        <div class="x-contents-content" xid="foundContent"> 
          <div component="$UI/system/components/justep/windowContainer/windowContainer"
            class="x-window-container" xid="mainContainer4" src="./index-found.w"
            autoLoad="false"/> 
        </div>  
        <div class="x-contents-content" xid="userContent"> 
          <div component="$UI/system/components/justep/windowContainer/windowContainer"
            class="x-window-container" xid="mainContainer5" src="./index-user.w" autoLoad="false"/> 
        </div> 
      </div> 
    </div>  
    <div class="x-panel-bottom" xid="bottom1"> 
      <div component="$UI/system/components/justep/button/buttonGroup" class="btn-group btn-group-justified x-card x-wangyi"
        tabbed="true" xid="buttonGroup1"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top active"
          label="新闻" xid="newsBtn" icon="icon-android-note" target="newsContent"> 
          <i xid="i4" class="icon icon-android-note"/>  
          <span xid="span6">新闻</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top"
          label="阅读" xid="readBtn" icon="icon-ios7-bookmarks-outline" target="readContent"> 
          <i xid="i3" class="icon icon-ios7-bookmarks-outline"/>  
          <span xid="span5">阅读</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top"
          label="视听" xid="audioBtn" icon="icon-arrow-right-b" target="audioContent"> 
          <i xid="i5" class="icon icon-arrow-right-b"/>  
          <span xid="span4">视听</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top"
          label="发现" xid="foundBtn" icon="icon-android-locate" target="foundContent"> 
          <i xid="i3" class="icon icon-android-locate"/>  
          <span xid="span4">发现</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top"
          label="我的" xid="userBtn" icon="icon-ios7-contact-outline" target="userContent"> 
          <i xid="i6" class="icon icon-ios7-contact-outline"/>  
          <span xid="span71">我的</span> 
        </a> 
      </div> 
    </div> 
  </div> 
</div>
