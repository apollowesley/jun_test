<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" xid="window" class="window" component="$UI/system/components/justep/window/window"
  design="device:m;">  
  <div component="$UI/system/components/justep/model/model" xid="model"><div component="$UI/system/components/justep/data/data" autoLoad="true" xid="newsData" idColumn="fID" onCustomRefresh="productDataCustomRefresh">
   <column label="id" name="fID" type="String" xid="xid1"></column>
   <column label="标题" name="fTitle" type="String" xid="xid2"></column>
   <column label="图片" name="fImg" type="String" xid="xid3"></column>
   <column label="摘要" name="fOmit" type="String" xid="xid4"></column>
   <column label="跟帖" name="fPostNumber" type="Integer" xid="xid5"></column>
   <rule xid="rule1">
    <col name="fPostNumber" xid="ruleCol1">
     <calculate xid="calculate1">
      <expr xid="default1">$row.val(&quot;fPostNumber&quot;)&gt;=0?$row.val(&quot;fPostNumber&quot;)+&quot;跟贴&quot;:&quot;&quot;;</expr></calculate> </col> </rule> </div></div>  
  <div class="list-group-item x-seach" xid="div16"> 
    <input component="$UI/system/components/justep/input/input" class="form-control x-inputText"
      xid="keyInput"/>  
    <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"
      xid="span34"/>
  </div>  
  <div component="$UI/system/components/bootstrap/carousel/carousel" class="x-carousel carousel x-banner"
    xid="carousel1"> 
    <ol class="carousel-indicators" xid="ol1"/>  
    <div class="x-contents carousel-inner" role="listbox" component="$UI/system/components/justep/contents/contents"
      active="0" slidable="true" wrap="true" swipe="true" xid="bannerContents"> 
      <div class="x-contents-content" xid="banner1"> 
        <img alt="" xid="image1" id="image1" class="image-wall img1" bind-attr-src="$model.getImageUrl('./img/1.jpg')"/>  
        <div class="x-title" xid="div17"> 
          <img alt="" class="img_small" bind-attr-src="$model.getImageUrl('./img/icon1.png')"
            xid="image4"/>  
          <span xid="span35">一周图片：马来西亚青年躺河里纳凉</span>
        </div> 
      </div>  
      <div class="x-contents-content" xid="banner2"> 
        <img alt="" xid="image2" id="image2" class="image-wall img1" bind-attr-src="$model.getImageUrl('./img/2.jpg')"/>  
        <div class="x-title" xid="div10"> 
          <img alt="" class="img_small" xid="image8" bind-attr-src="$model.getImageUrl('./img/icon5.png')"/>  
          <span xid="span22">福特旗舰座驾金牛座开启预售</span>
        </div> 
      </div>  
      <div class="x-contents-content" xid="banner3"> 
        <img alt="" xid="image13" id="image13" class="image-wall img1" bind-attr-src="$model.getImageUrl('./img/3.jpg')"/>  
        <div class="x-title" xid="div11"> 
          <img alt="" class="img_small" xid="image9" bind-attr-src="$model.getImageUrl('./img/icon1.png')"/>  
          <span xid="span23">俯瞰上海迪士尼在建大城堡露真容</span>
        </div> 
      </div>  
      <div class="x-contents-content" xid="banner4"> 
        <img alt="" xid="image14" id="image14" class="image-wall img1" bind-attr-src="$model.getImageUrl('./img/4.jpg')"/>  
        <div class="x-title" xid="div12"> 
          <img alt="" class="img_small" xid="image10" bind-attr-src="$model.getImageUrl('./img/icon1.png')"/>  
          <span xid="span24">全新迈锐宝驾问世竞猜赢好礼</span>
        </div> 
      </div> 
    </div> 
  </div>  
  <div class="panel panel-default x-card x-wangyi" component="$UI/system/components/bootstrap/panel/panel"
    xid="panel5"> 
    <div component="$UI/system/components/justep/list/list" class="x-list"
      data="newsData" limit="3" xid="list1"> 
      <ul class="x-list-template" xid="listTemplateUl1" bind-click="detailClick"> 
        <li xid="li1" class="list-group-item x-flex"> 
          <div xid="div8" class="media-left"> 
            <a xid="a1"> 
              <img alt="" xid="image4" class="img-responsive img_new" bind-attr-src="$model.getImageUrl(val('fImg'))"/>
            </a> 
          </div>  
          <div class="x-flex1" xid="col26"> 
            <h5 bind-text="ref('fTitle')" class="o-nomargintop" xid="h51"/>  
            <span style="text-align: justify;" bind-text="ref('fOmit')" class="text-muted"
              xid="span36"/>  
            <span bind-text="ref('fPostNumber')" class="pull-right" xid="span37"/>
          </div> 
        </li> 
      </ul> 
    </div>  
    <div xid="div18"> 
      <h5 class="panel-heading" xid="h52">广州女子为追回爱犬贼轧双腿
        <span xid="span12" class="pull-right text-muted">16071跟帖</span>
      </h5>  
      <div component="$UI/system/components/bootstrap/row/row" class="row o-nomargin panel"
        xid="row1"> 
        <div class="col col-xs-4" xid="col1"> 
          <img src="" alt="" xid="image6" class="img-responsive" bind-attr-src="$model.getImageUrl('./img/a3.jpg')"/>
        </div>  
        <div class="col col-xs-4" xid="col2"> 
          <img src="" alt="" xid="image7" class="img-responsive" bind-attr-src="$model.getImageUrl('./img/a4.jpg')"/>
        </div>  
        <div class="col col-xs-4" xid="col3"> 
          <img src="" alt="" xid="image11" class="img-responsive" bind-attr-src="$model.getImageUrl('./img/a5.jpg')"/>
        </div> 
      </div> 
    </div>  
    <div component="$UI/system/components/justep/list/list" class="x-list"
      data="newsData" limit="3" xid="list2"> 
      <ul class="x-list-template" xid="listTemplateUl1" bind-click="detailClick"> 
        <li xid="li1" class="list-group-item x-flex"> 
          <div xid="div8" class="media-left"> 
            <a xid="a1"> 
              <img alt="" xid="image4" class="img-responsive img_new" bind-attr-src="$model.getImageUrl(val('fImg'))"/>
            </a> 
          </div>  
          <div class="x-flex1" xid="col26"> 
            <h5 bind-text="ref('fTitle')" class="o-nomargintop" xid="h53"/>  
            <span style="text-align: justify;" bind-text="ref('fOmit')" class="text-muted"
              xid="span38"/>  
            <span bind-text="ref('fPostNumber')" class="pull-right" xid="span39"/>
          </div> 
        </li> 
      </ul> 
    </div> 
  </div>
</div>
