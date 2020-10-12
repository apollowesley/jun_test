<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="main13" component="$UI/system/components/justep/window/window"
  design="device:mobile;" xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:13px;top:202px;" onLoad="modelLoad"> 
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="audioData" idColumn="fID" onCustomRefresh="audioDataCustomRefresh"> 
      <column label="id" name="fID" type="String" xid="xid1"/>  
      <column label="标题" name="fTitle" type="String" xid="xid2"/>  
      <column label="图片" name="fImg" type="String" xid="xid3"/>  
      <column label="摘要" name="fOmit" type="String" xid="xid4"/>  
      <column label="跟帖量" name="fPostNumber" type="Integer" xid="xid5"/>  
      <rule xid="rule1"> 
        <col name="fPostNumber" xid="ruleCol1"> 
          <calculate xid="calculate1"> 
            <expr xid="default1">$row.val("fPostNumber")&gt;=0?$row.val("fPostNumber")+"跟贴":"";</expr>
          </calculate> 
        </col> 
      </rule>  
      <column label="时间" name="fTime" type="String" xid="xid6"/>  
      <column label="播放次数" name="fPlayNumber" type="String" xid="xid7"/>  
      <column label="分类ID" name="fClassID" type="String" xid="xid11"/>
    </div>  
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="classData" onCustomRefresh="classDataCustomRefresh" idColumn="fID">
      <column label="ID" name="fID" type="String" xid="xid8"/>  
      <column label="名称" name="fName" type="String" xid="xid9"/>  
      <column label="图片" name="fImg" type="String" xid="xid10"/>
    </div>
  </div>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-has-iosstatusbar"
    xid="panel1"> 
    <div class="x-panel-top" xid="top1" height="48"> 
       
    
  <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar" xid="titleBar">
   <div class="x-titlebar-left flex5" xid="left1"><div component="$UI/system/components/justep/button/buttonGroup" class="btn-group x-nav-guide" tabbed="true" xid="buttonGroup1"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label active" label="视频" xid="button1" target="audioContent1"> 
          <i xid="i1" />  
          <span xid="span1">视频</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label" label="电台" xid="button2" target="audioContent2"> 
          <i xid="i2" />  
          <span xid="span2">电台</span> 
        </a> 
      </div></div>
   <div class="x-titlebar-title" xid="title1"></div>
   <div class="x-titlebar-right reverse" xid="right1"></div></div></div>  
    <div class="x-panel-content" xid="content1"> 
      <div component="$UI/system/components/justep/contents/contents" class="x-contents x-full"
        active="0" xid="contents1"> 
        <div class="x-contents-content panel-body" xid="audioContent1"> 
          <div class="list-group"> 
            <div class="list-group-item"> 
              <div component="$UI/system/components/bootstrap/row/row" class="row text-center"
                xid="row1"> 
                <div class="col col-xs-3 x-nopadding" xid="col5"> 
                  <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/audio1.png')"
                    class="img4"/>  
                  <div bind-text="'奇葩'"/> 
                </div>  
                <div class="col col-xs-3 x-nopadding" xid="col6"> 
                  <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/audio2.png')"
                    class="img4"/>  
                  <div bind-text="'萌物'"/> 
                </div>  
                <div class="col col-xs-3 x-nopadding" xid="col7"> 
                  <img src="" alt="" xid="image4" bind-attr-src="$model.getImageUrl('./img/audio3.png')"
                    class="img4"/>  
                  <div bind-text="'美女'"/> 
                </div>  
                <div class="col col-xs-3 x-nopadding" xid="col8"> 
                  <img src="" alt="" xid="image4" bind-attr-src="$model.getImageUrl('./img/audio4.png')"
                    class="img4"/>  
                  <div bind-text="'精品'"/> 
                </div> 
              </div> 
            </div> 
          </div>  
          <div component="$UI/system/components/justep/list/list" class="x-list x-wangyi"
            xid="list2" data="audioData"> 
            <ul class="x-list-template x-min-height" xid="listTemplateUl1"
              componentname="$UI/system/components/justep/list/list#listTemplateUl"
              id="undefined_listTemplateUl1" bind-click="detailClick"> 
              <li xid="li2" class="x-min-height" componentname="li(html)"
                id="undefined_li2"> 
                <div class="list-group" xid="listGroup1"> 
                  <div class="list-group-item"> 
                    <a xid="a2"> 
                      <img alt="" xid="image3" class="img-responsive img1"
                        bind-attr-src="$model.getImageUrl(val('fImg'))"/> 
                    </a>
                    <div class="panel-heading"> 
                      <h5 style="text-align: justify;" class="x-flex1"
                        bind-text="ref('fTitle')"><![CDATA[]]></h5>
                      <span xid="span3" class="text-muted" bind-text="ref('fOmit')"><![CDATA[]]></span> 
                    </div>  
                    <div class="text-muted"> 
                      <span xid="span15" class="pull-right"> 
                        <i xid="i6" class="icon-compose"/>  
                        <span xid="span12" bind-text="ref('fPostNumber')"><![CDATA[]]></span>  
                        <i xid="i4" class="icon-forward btn"/> 
                      </span>  
                      <i xid="i10" class="icon-android-clock btn x-nopadding"/>  
                      <span xid="span13" bind-text="ref('fTime')"/>  
                      <i xid="i9" class="icon-arrow-right-b btn x-nopadding"/>  
                      <span xid="span14" bind-text="ref('fPlayNumber')"/> 
                    </div> 
                  </div> 
                </div> 
              </li> 
            </ul> 
          </div> 
        </div>  
        <div class="x-contents-content" xid="audioContent2"> 
          <div component="$UI/system/components/justep/panel/panel" class="panel panel-default x-card x-tuniu" xid="panel2">
   <div component="$UI/system/components/justep/button/buttonGroup" class="btn-group btn-group-justified x-audio" tabbed="true" xid="buttonGroup2">
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left text-black" label="我的下载" xid="button10" icon="icon-android-download"> 
              <i xid="i12" class="icon-android-download" />  
              <span xid="span16">我的下载</span>
            </a>  
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left text-black" label="最近播放" xid="button11" icon="icon-ios7-timer-outline"> 
              <i xid="i13" class="icon-ios7-timer-outline" />  
              <span xid="span17">最近播放</span>
            </a>
          </div></div>
          
          <div component="$UI/system/components/justep/panel/panel" class="panel panel-default x-card x-tuniu" xid="panel3">
   <div component="$UI/system/components/justep/list/list" class="x-list x-wangyi" xid="list1" data="classData"> 
            <ul class="x-list-template x-min-height" xid="listTemplateUl2" componentname="$UI/system/components/justep/list/list#listTemplateUl" id="undefined_listTemplateUl1"> 
              <li xid="li1" class="panel-body" componentname="li(html)" id="undefined_li2"> 
                <div bind-click="listClick" class="x-nopadding"> 
                  <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-sm btn-only-icon pull-right x-nopadding" label="button" xid="button6" icon="icon-ios7-arrow-forward"> 
                    <i xid="i16" class="icon-ios7-arrow-forward text-muted" />  
                    <span xid="span24" /> 
                  </a>  
                  <img alt="" xid="image17" class="img4 img-circle" bind-attr-src="$model.getImageUrl(val('fImg'))" />  
                  <span class="text-muted" xid="span26" bind-text="ref('fName')"><![CDATA[]]></span> 
                <div xid="div2" class="panel"></div></div>
                
              <div component="$UI/system/components/justep/list/list" class="x-list" xid="list3" data="audioData" filter="$row.val('fClassID')==$object.val('fID')">
   <ul class="x-list-template" xid="listTemplateUl3" bind-click="detailClick">
    <li xid="li3" class="col-xs-4 x-nopadding"><div class="list-group x-audio"> 
                  <div class="list-group-item  x-nopadding"> 
                    <a> 
                          <img alt="" xid="image15" class="img-responsive img3" bind-attr-src="$model.getImageUrl(val('fImg'))" height="60px" /> 
                        </a><div class="panel-body"> 
                        <h5 class="x-flex1" bind-text="ref('fTitle')"><![CDATA[]]></h5>  
                        <span class="text-muted" bind-text="ref('fOmit')"><![CDATA[54分钟前]]></span> 
                      </div></div> 
                </div></li></ul> </div></li> 
            </ul> 
          </div></div></div> 
      </div> 
    </div> 
  </div> 
</div>
