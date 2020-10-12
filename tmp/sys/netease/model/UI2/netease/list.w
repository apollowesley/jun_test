<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="main" component="$UI/system/components/justep/window/window" design="device:m;"
  xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:308px;top:194px;"> 
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="newsData" idColumn="fID" onCustomRefresh="productDataCustomRefresh"> 
      <column label="id" name="fID" type="String" xid="column4"/>  
      <column label="标题" name="fTitle" type="String" xid="column5"/>  
      <column label="图片" name="fImg" type="String" xid="column1"/>  
      <column label="摘要" name="fOmit" type="String" xid="column2"/>  
      <column label="跟帖" name="fPostNumber" type="Integer" xid="column3"/>  
      <rule xid="rule1"> 
        <col name="fPostNumber" xid="ruleCol1"> 
          <calculate xid="calculate1"> 
            <expr xid="default1">$row.val("fPostNumber")&gt;=0?$row.val("fPostNumber")+"跟贴":"";</expr>
          </calculate> 
        </col> 
      </rule> 
    </div>
  </div>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-card x-has-iosstatusbar"> 
    <div class="x-panel-top" height="48"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"> 
        <div class="x-titlebar-left" xid="div6"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left"
            xid="backBtn" icon="icon-arrow-left-c" label="趣医网" onClick="{operation:'window.close'}"> 
            <i xid="i6" class="icon-arrow-left-c"/>  
            <span xid="span6">趣医网</span> 
          </a>  
          </div>  
        <div class="x-titlebar-title"></div>  
        <div class="x-titlebar-right reverse text-success" xid="div5"></div> 
      </div>
    </div>  
    <div class="x-panel-content x-cards"> 
      <img alt="" xid="image1" class="img-responsive" bind-attr-src="$model.getImageUrl('./img/img.jpg')"/>
      <div component="$UI/system/components/justep/list/list" class="x-list  panel-body"
        xid="list2" data="newsData"> 
        <ul class="x-list-template x-min-height" xid="listTemplateUl2" bind-click="detailClick"
          componentname="$UI/system/components/justep/list/list#listTemplateUl" id="undefined_listTemplateUl2"> 
          <li xid="li2" class="list-group" componentname="li(html)" id="undefined_li2"> 
            <div class="list-group-item">
              <div class="media-heading"> 
                <div class="media-left"> 
                  <a xid="a3"> 
                    <img alt="" xid="image4" class="img-responsive img_new"
                      bind-attr-src="$model.getImageUrl(val('fImg'))"/>
                  </a> 
                </div>  
                <div class="media-right"> 
                  <a xid="a5"> 
                    <h5 style="text-align: justify;" class="x-flex1" xid="h52"
                      bind-text="ref('fTitle')"><![CDATA[]]></h5>
                  </a>  
                  <span bind-text="ref('fPostNumber')" class="text-muted" xid="span2"/>
                </div> 
              </div>
            </div>
          </li> 
        </ul> 
      </div> 
    </div> 
  </div> 
</div>
