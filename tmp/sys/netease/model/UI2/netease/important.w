<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="main13" component="$UI/system/components/justep/window/window"
  design="device:mobile;" xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:13px;top:202px;">
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
  </div>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-card top-trans"
    xid="panel2"> 
    <div class="x-panel-top x-has-iosstatusbar" xid="top1"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"
        xid="titleBar1"> 
        <div class="x-titlebar-left" xid="div2"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left"
            label="24小时要闻" xid="button6" icon="icon-arrow-left-c" onClick="{operation:'window.close'}"> 
            <i xid="i2" class="icon-arrow-left-c"/>  
            <span xid="span6">24小时要闻</span> 
          </a> 
        </div>  
        <div class="x-titlebar-title" xid="div3"/>  
        <div class="x-titlebar-right reverse" xid="div4"/> 
      </div>
    </div>  
    <div class="x-panel-content  x-cards" xid="content1"> 
      <div class="o-relative"> 
        <div class="text-center x-user" xid="logo"> 
          <img alt="" xid="image2" class="o-imgLogo img-rounded" bind-attr-src="$model.getImageUrl('./img/24News.gif')"/>  
          <div xid="div5" bind-text="'聚焦今日时事 浓缩新闻精华'" class="h5 text-white"/>
        </div>
        <img src="" alt="" xid="image1" bind-attr-src="$model.getImageUrl('./img/6.jpg')"
          class="img-responsive"/> 
      </div>
      <div class="panel panel-body o-relative"> 
        <a xid="a2"> 
          <img alt="" xid="image3" class="img-responsive o-img1" bind-attr-src="$model.getImageUrl('./img/a8.jpg')" bind-click="detailClick"/>
        </a>  
        <div xid="div6"> 
          <h5 style="text-align: justify;" class="x-flex1" xid="h51"><![CDATA[马航MH17最终报告：被山毛榉导弹击落]]></h5>  
          <span xid="span3" class="text-muted"><![CDATA[荷兰公布最终调查报告，并未指出是谁执行的攻击行动。]]></span>
        </div>  
        <div xid="div7"> 
          <span xid="span15" class="text-muted pull-right"><![CDATA[47795跟帖]]></span> 
        </div>  
        <h5 xid="span1" class="x-bubble "><![CDATA[1]]></h5>
      </div>
      <div component="$UI/system/components/justep/list/list" class="x-list"
        data="newsData" limit="-1" xid="list1"> 
        <ul class="x-list-template" xid="listTemplateUl1" bind-click="detailClick"> 
          <li xid="li1" class="list-group-item x-flex"> 
            <div xid="div8" class="media-left"> 
              <a xid="a1"> 
                <img alt="" xid="image4" class="img-responsive o-imgNew" bind-attr-src="$model.getImageUrl(val('fImg'))"/>
              </a> 
            </div>  
            <div class="x-flex1" xid="col26"> 
              <h5 bind-text="ref('fTitle')" xid="h52" class="o-nomargintop"/>  
              <span style="text-align: justify;" bind-text="ref('fOmit')" class="text-muted"
                xid="span36"/>  
              <span bind-text="ref('fPostNumber')" class="pull-right" xid="span37"/>
            </div> 
          </li> 
        </ul> 
      </div> 
    </div> 
  </div> 
</div>
