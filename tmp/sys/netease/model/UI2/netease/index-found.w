<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="x-purpose window x-full-screen container-fluid main13" component="$UI/system/components/justep/window/window"
  design="device:mobile;" xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:13px;top:202px;"> 
    <div component="$UI/system/components/justep/data/data" autoLoad="true" xid="foundData" idColumn="fID" onCustomRefresh="foundDataCustomRefresh">
   <column label="ID" name="fID" type="String" xid="xid8"></column>
  <column label="店名称" name="fName" type="String" xid="xid9"></column>
  <column label="店标" name="fLogo" type="String" xid="xid10"></column>
  <column label="标题" name="fTitle" type="String" xid="xid1"></column>
  <column label="摘要" name="fOmit" type="String" xid="xid2"></column>
  <column label="图片" name="fImg" type="String" xid="xid3"></column>
  <column label="价格" name="fPrice" type="Float" xid="xid4"></column>
  <column label="以前价格" name="fOldPrice" type="Float" xid="xid5"></column></div></div>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-has-iosstatusbar" xid="panel1">
   <div class="x-panel-top" xid="top1" height="48">
  <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar" xid="titleBar">
   <div class="x-titlebar-left" xid="left1"><a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label text-white x-nav-guide" label="发现" xid="button1">
   <i xid="i1"></i>
   <span xid="span3">发现</span></a></div>
   <div class="x-titlebar-title" xid="title1"></div>
   <div class="x-titlebar-right reverse" xid="right1"></div></div></div>
   <div class="x-panel-content" xid="content1"><div component="$UI/system/components/bootstrap/carousel/carousel" class="x-carousel carousel x-banner" xid="carousel1">
   <ol class="carousel-indicators" xid="ol1"></ol>
   <div class="x-contents carousel-inner" role="listbox" component="$UI/system/components/justep/contents/contents" active="0" slidable="true" wrap="true" swipe="true" xid="bannerContents">
    <div class="x-contents-content" xid="content3">
     <img alt="" xid="image1" id="image1" class="image-wall img1" bind-attr-src="$model.getImageUrl('./img/1.jpg')"></img>
     </div> 
    <div class="x-contents-content" xid="content4">
     <img alt="" xid="image2" id="image2" class="image-wall img1" bind-attr-src="$model.getImageUrl('./img/2.jpg')"></img>
     </div> 
    <div class="x-contents-content" xid="content5">
     <img alt="" xid="image13" id="image13" class="image-wall img1" bind-attr-src="$model.getImageUrl('./img/3.jpg')"></img>
     </div> 
    <div class="x-contents-content" xid="content6">
     <img alt="" xid="image14" id="image14" class="image-wall img1" bind-attr-src="$model.getImageUrl('./img/4.jpg')"></img>
     </div> </div> </div><div component="$UI/system/components/justep/panel/panel" class="panel panel-default x-card" xid="panel2">
   
   <div component="$UI/system/components/bootstrap/row/row" class="row text-center panel-body x-nomargin x-nopadding" xid="row1">
   <div class="col col-xs-3 x-nopadding" xid="col5">
    <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/found1.png')" class="img4" xid="image5"></img>
    <div bind-text="'金币商城'" xid="div5"></div></div> 
   <div class="col col-xs-3 x-nopadding" xid="col6">
    <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/found2.png')" class="img4" xid="image6"></img>
    <div bind-text="'身边服务'" xid="div6"></div></div> 
   <div class="col col-xs-3 x-nopadding" xid="col7">
    <img src="" alt="" xid="image4" bind-attr-src="$model.getImageUrl('./img/found3.png')" class="img4"></img>
    <div bind-text="'活动广场'" xid="div7"></div></div> 
   <div class="col col-xs-3 x-nopadding" xid="col8">
    <img src="" alt="" xid="image4" bind-attr-src="$model.getImageUrl('./img/found4.png')" class="img4"></img>
    <div bind-text="'游戏中心'" xid="div8"></div></div> </div></div>
  <div component="$UI/system/components/justep/panel/panel" class="panel panel-default x-card" xid="panel3"><div component="$UI/system/components/justep/list/list" class="x-list" xid="list1" data="foundData">
   <ul class="x-list-template" xid="listTemplateUl1">
    <li xid="li1" class="panel-heading x-nopadding x-found"><div bind-click="listClick" class="x-nopadding x-found-logo" xid="div1">
   
   <img alt="" xid="image17" class="img4 img-rounded pull-left" bind-attr-src="$model.getImageUrl(val('fLogo'))"></img>
   <span class="text-muted pull-left" xid="span26" bind-text="ref('fName')"></span>
   </div>
  
  <div class="panel-body"></div><a xid="a2">
   <img alt="" xid="image3" class="img-responsive img1" bind-attr-src="$model.getImageUrl(val('fImg'))"></img></a><div class="panel-body" xid="div3">
   <h5 style="text-align: justify;" class="x-flex1" bind-text="ref('fTitle')" xid="h51"></h5>
   <span xid="span1" class="text-muted" bind-text="$model.foundData.ref('fOmit')"><![CDATA[]]></span><div class="text-muted" xid="div4">
   
   <div class="panel"></div><a class="btn x-gray text-danger"><span><![CDATA[购￥]]></span><span bind-text="ref('fPrice')"></span></a>
  <span class="x-delline"><![CDATA[￥]]></span><span bind-text="ref('fOldPrice')" class="x-delline"></span>
  </div></div>
  </li></ul> </div></div></div>
   </div>
  </div>
