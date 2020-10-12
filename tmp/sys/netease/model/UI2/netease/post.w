<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="main13" component="$UI/system/components/justep/window/window"
  design="device:m;" xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:361px;top:6px;" onLoad="modelLoad"><div component="$UI/system/components/justep/data/data" autoLoad="true" xid="postData" onCustomRefresh="postDataCustomRefresh" idColumn="fID" limit="2"><column label="id" name="fID" type="String" xid="xid1"></column>
  <column label="名称" name="fName" type="String" xid="xid2"></column>
  <column label="头像" name="fImg" type="String" xid="xid6"></column>
  <column label="来源" name="fSource" type="String" xid="xid3"></column>
  <column label="次数" name="fNumber" type="Integer" xid="xid4"></column>
  <column label="留言内容" name="fContent" type="String" xid="xid5"></column>
  <column label="时间" name="fTime" type="String" xid="xid7"></column></div></div>  
  <div component="$UI/system/components/justep/popMenu/popMenu" class="x-popMenu" xid="popMenu" direction="right-bottom" anchor="popMenuBtn">
   <div class="x-popMenu-overlay" xid="div3"></div>
   <ul component="$UI/system/components/justep/menu/menu" class="x-menu dropdown-menu x-popMenu-content" xid="menu2"><li class="x-menu-item" xid="item1">
   <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left" label="分享" xid="button11" icon="icon-ios7-redo">
    <i xid="i10" class="icon-ios7-redo"></i>
    <span xid="span21">分享</span></a> </li>
  <li class="x-menu-item" xid="item2">
   <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left" label="收藏" xid="button15" icon="icon-ios7-star-outline">
    <i xid="i14" class="icon-ios7-star-outline"></i>
    <span xid="span25">收藏</span></a> </li><li class="x-menu-item" xid="item3">
   <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left" label="截屏" xid="button13" icon="icon-ios7-albums-outline">
    <i xid="i12" class="icon-ios7-albums-outline"></i>
    <span xid="span23">截屏</span></a> </li>
  <li class="x-menu-item" xid="item4">
   <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left" label="夜间模式" xid="button14" icon="icon-android-display">
    <i xid="i13" class="icon-android-display"></i>
    <span xid="span24">夜间模式</span></a> </li>
  </ul></div><div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-card x-has-iosstatusbar"> 
    <div class="x-panel-top"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"> 
        <div class="x-titlebar-left text-white"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon"
            label="搜索" xid="button6" icon="icon-arrow-left-c" onClick="{operation:'window.close'}"> 
            <i xid="i6" class="icon-arrow-left-c"/>  
            <span xid="span6">搜索</span> 
          </a> 
        </div>  
        <div class="x-titlebar-title"/>  
        <div class="x-titlebar-right reverse text-white"> 
           
        </div> 
      </div> 
    </div>  
    <div class="x-panel-content x-cards panel-body"> 
      <span xid="span13" class="badge"><![CDATA[1915跟贴]]></span><div class="panel-body"></div><div component="$UI/system/components/justep/list/list" class="x-list" xid="list1" data="postData">
   <ul class="x-list-template" xid="listTemplateUl1">
    <li xid="li1" class="panel-heading x-nopadding"><div class="media"><div class="media-left"><img src="" alt="" bind-attr-src='$model.getImageUrl(val("fImg"))' class="img4" xid="image2"></img></div>
  <div class="media-body" xid="div1">
   <span xid="span9" class="pull-right text-muted glyphicon glyphicon-thumbs-up"></span>
   <span xid="span7" bind-text="ref('fNumber')" class="pull-right text-muted"></span>
   <span xid="span5" bind-text="ref('fName')" class="text-primary"></span>
   <div class="text-muted" xid="div2">
    <span bind-text="ref('fTime')" xid="span1"></span>
    <span xid="span2">前 网易</span>
    <span xid="span3" bind-text="ref('fSource')"></span>
    <span xid="span4">用户</span></div> 
   <div bind-text=' $object.val("fContent")' xid="div4"></div></div></div></li></ul> </div>
  </div>  
    <div class="x-panel-bottom" xid="bottom1"> 
      <div class="input-group"> 
        <span class="input-group-addon"> 
          <i xid="i6" class="icon-edit text-muted"/>
        </span>  
        <input component="$UI/system/components/justep/input/input" class="form-control x-inputText" xid="nameInput"/> 
      </div>  
      <span class="form-control-feedback" aria-hidden="true">
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label x-nopadding"
          label="发送" xid="button9"> 
          <i xid="i8"/>  
          <span class="text-muted">发送</span> 
        </a>
      </span> 
    </div> 
  </div> 
</div>
