<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" xid="window" class="window takeout" component="$UI/system/components/justep/window/window"
  design="device:m;">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:243px;top:291px;"
    onLoad="modelLoad"> 
    <div component="$UI/system/components/justep/data/baasData" autoLoad="true" orderBy="fID:asc"
      xid="foodData" idColumn="fID" confirmDelete="true" 
      queryAction="queryFood" tableName="takeout_food" url="/justep/takeout"
      limit="6" confirmRefresh="false"> 
      <column label="fID" name="fID" type="String" xid="default1"/>  
      <column label="fName" name="fName" type="String" xid="default2"/>  
      <column label="fPrice" name="fPrice" type="Float" xid="default3"/>  
      <column label="fDescription" name="fDescription" type="String" xid="default4"/>  
      <column label="fImage" name="fImage" type="String" xid="default5"/>  
      <column label="calcPriceText" name="calcPriceText" type="String" isCalculate="true" xid="default23"/>  
      <column label="calcImageURL" name="calcImageURL" type="String" isCalculate="true" xid="default24"/>  
      <rule xid="rule2"> 
        <col name="calcPriceText" xid="ruleCol2"> 
          <calculate xid="calculate2"> 
            <expr xid="default30">js:'¥ ' + val('fPrice') + '元'</expr> 
          </calculate> 
        </col>  
        <col name="calcImageURL" xid="ruleCol7"> 
          <calculate xid="calculate7"> 
            <expr xid="default35"><![CDATA[js:$model.transURL('./img/'+val('fImage'))]]></expr> 
          </calculate> 
        </col> 
      </rule>  
      <data xid="default6">[]</data> 
    </div>  
    <div component="$UI/system/components/justep/data/baasData" autoLoad="false"
      xid="userData" idColumn="fID" autoNew="false" queryAction="queryUser" tableName="takeout_user"
      url="/justep/takeout" saveAction="save"> 
      <column label="col3" name="fID" type="String" xid="default13"/>  
      <column label="col0" name="fName" type="String" xid="default10"/>  
      <column label="col1" name="fPhoneNumber" type="String" xid="default11"/>  
      <column label="col2" name="fAddress" type="String" xid="default12"/> 
    <filter name="userFilter" xid="filter1"><![CDATA[fID = :user]]></filter></div>  
    <div component="$UI/system/components/justep/data/baasData" autoLoad="false"
      orderBy="fCreateTime:desc" xid="orderData" idColumn="fID" confirmRefresh="false"
      confirmDelete="false" queryAction="queryOrder" tableName="takeout_order" url="/justep/takeout"
      limit="10" saveAction="save" onSaveCreateParam="orderDataSaveCreateParam"> 
      <column label="col6" name="fID" type="String" xid="default21"/>  
      <column label="col0" name="fCreateTime" type="DateTime" xid="default15"/>  
      <column label="col1" name="fContent" type="String" xid="default16"/>  
      <column label="col2" name="fSum" type="Float" xid="default17"/>  
      <column label="col3" name="fUserID" type="String" xid="default18"/>  
      <column label="col7" name="fUserName" type="String" xid="default22"/>  
      <column label="col5" name="fPhoneNumber" type="String" xid="default20"/>  
      <column label="col4" name="fAddress" type="String" xid="default19"/>  
      <column label="col8" name="fPayState" type="Integer" xid="default7"/> 
    <filter name="userFilter" xid="filter2"><![CDATA[fUserID = :user]]></filter></div>  
    <div component="$UI/system/components/justep/data/data" autoLoad="false"
      xid="cartData" idColumn="fFoodID"> 
      <column label="col4" name="fFoodID" type="String" xid="default41"/>  
      <column label="col0" name="fName" type="String" xid="default37"/>  
      <column label="col1" name="fPrice" type="Float" xid="default38"/>  
      <column label="col2" name="fCount" type="Integer" xid="default39"/>  
      <column label="col3" name="calcMoney" type="Float" xid="default40"/>  
      <rule xid="rule4"> 
        <col name="calcMoney" xid="ruleCol4"> 
          <calculate xid="calculate4"> 
            <expr xid="default27">js:val('fPrice') * val('fCount')</expr> 
          </calculate> 
        </col> 
      </rule> 
    </div>  
    <div component="$UI/system/components/justep/data/data" autoLoad="false"
      xid="statusData" autoNew="true" idColumn="calcCanSaveOrder"> 
      <column label="col0" name="calcCartSumMoneyText" type="String" xid="default9"/>  
      <column label="col1" name="calcCartCountText" type="String" xid="default25"/>  
      <column label="col0" name="calcCanSaveOrder" type="String" xid="default33"/>  
      <column label="col1" name="calcCanClearCart" type="String" xid="default34"/>  
      <rule xid="rule3"> 
        <col name="calcCartSumMoneyText" xid="ruleCol1"> 
          <calculate xid="calculate1"> 
            <expr xid="default26">js:'¥ ' + $model.comp('cartData').sum('calcMoney') + '元'</expr> 
          </calculate> 
        </col>  
        <col name="calcCartCountText" xid="ruleCol3"> 
          <calculate xid="calculate3"> 
            <expr xid="default28">js:($model.comp('cartData').count() &gt; 0) ? '购物车(' + $model.comp('cartData').count() + ')' : '购物车'</expr> 
          </calculate> 
        </col>  
        <col name="calcCanSaveOrder" xid="ruleCol5"> 
          <calculate xid="calculate5"> 
            <expr xid="default31">js:$model.comp('cartData').sum("calcMoney") &gt; 0</expr> 
          </calculate> 
        </col>  
        <col name="calcCanClearCart" xid="ruleCol6"> 
          <calculate xid="calculate6"> 
            <expr xid="default32">js:$model.comp('cartData').count() &gt; 0</expr> 
          </calculate> 
        </col> 
      </rule> 
    </div>  
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="payData" idColumn="fID"> 
      <column name="fID" type="String" xid="default14"/>  
      <column label="支付方式" name="fPayType" type="String" xid="default8"/>  
      <column label="支付方式名字" name="fPayTypeLabel" type="String" xid="default29"/>  
      <data xid="default36">[{&quot;fID&quot;:&quot;1&quot;,&quot;fPayType&quot;:&quot;weixin&quot;,&quot;fPayTypeLabel&quot;:&quot;微信支付&quot;},{&quot;fID&quot;:&quot;2&quot;,&quot;fPayType&quot;:&quot;alipay&quot;,&quot;fPayTypeLabel&quot;:&quot;支付宝支付&quot;},{&quot;fID&quot;:&quot;3&quot;,&quot;fPayType&quot;:&quot;weixinJSSDK&quot;,&quot;fPayTypeLabel&quot;:&quot;微信内支付&quot;},{&quot;fID&quot;:&quot;4&quot;,&quot;fPayType&quot;:&quot;faceToFace&quot;,&quot;fPayTypeLabel&quot;:&quot;货到付款&quot;},{&quot;fID&quot;:&quot;5&quot;,&quot;fPayType&quot;:&quot;union&quot;,&quot;fPayTypeLabel&quot;:&quot;银联支付&quot;}]</data></div> 
  </div>  
  <span component="$UI/system/components/justep/windowDialog/windowDialog" xid="mapDialog"
    style="left:63px;top:16px;" routable="true" src="$UI/takeout/mapActivity.w"/>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full"
    xid="panel1"> 
    <div class="x-panel-top" xid="top1"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"
        xid="titleBar1" title="WeX5 外卖"> 
        <div class="x-titlebar-left" xid="div1"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon" style="display:none"
            label="" xid="backBtn" icon="icon-chevron-left" onClick="{&quot;operation&quot;:&quot;window.close&quot;}"
            bind-visible="justep.Portal.getPortal()"> 
            <i xid="i11" class="icon-chevron-left"/>  
            <span xid="span18"/> 
          </a> 
        </div>  
        <div class="x-titlebar-title" xid="div2">WeX5 外卖</div>  
        <div class="x-titlebar-right reverse" xid="div3"/> 
      </div> 
    </div>  
    <div class="x-panel-content" xid="content1"> 
      <div component="$UI/system/components/justep/contents/contents" class="x-contents x-full"
        active="0" xid="contents" slidable="true" swipe="true" onActiveChange="contentsActiveChange"> 
        <div class="x-contents-content active x-scroll-view" xid="foodContent"> 
          <div class="x-scroll" component="$UI/system/components/justep/scrollView/scrollView"
            xid="scrollView2"> 
            <div class="x-content-center x-pull-down container" xid="div4"> 
              <i class="x-pull-down-img glyphicon x-icon-pull-down" xid="i1"/>  
              <span class="x-pull-down-label" xid="span2">下拉刷新...</span> 
            </div>  
            <div class="x-scroll-content" xid="div5"> 
              <div component="$UI/system/components/justep/list/list" class="x-list x-cards"
                xid="foodList" data="foodData" limit="8"> 
                <ul class="x-list-template list-group" xid="listTemplateUl1"> 
                  <li xid="li1" class="list-group-item"> 
                    <div class="media"> 
                      <div class="media-left" > 
                        <img alt="" xid="image1" style="width:120px;height:89px;width:38vw;height: 25.333333333333vw;max-width:120px;max-height:89px;"
                          class="img-rounded media-object" bind-attr-src="val('calcImageURL')"/> 
                      </div>  
                      <div class="media-body"> 
                        <a component="$UI/system/components/justep/button/button"
                          style="padding: 0;margin: 10px 10px 0 0;" class="btn x-card btn-link pull-right"
                          label="来一份" xid="addCartBtn" onClick="addCartBtnClick"> 
                          <i xid="i14"/>  
                          <span xid="span10">来一份</span> 
                        </a>  
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output list-group-item-heading" xid="output1" bind-ref="ref('fName')" style="font-size:14px;font-weight:bold"/>  
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output list-group-item-text" xid="output2" bind-ref="ref('fDescription')"/>  
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output list-group-item-text" xid="output3" bind-ref="ref('calcPriceText')"
                          style="font-weight: bold;"/> 
                      </div> 
                    </div> 
                  </li> 
                </ul> 
              </div> 
            </div>  
            <div class="x-content-center x-pull-up" xid="div6"> 
              <span class="x-pull-up-label" xid="span5">加载更多...</span> 
            </div> 
          </div> 
        </div>  
        <div class="x-contents-content x-cards" xid="cartContent"> 
          <div class="panel panel-default panel-default x-card" component="$UI/system/components/bootstrap/panel/panel"
            xid="panel2"> 
            <div class="panel-heading" xid="heading2"> 
              <h4 class="panel-title" xid="h42"><![CDATA[清单]]></h4> 
            </div>  
            <div class="panel-body" xid="body2"> 
              <span style="color:#ddd" bind-visible="$model.cartData.getCount() == 0">空</span>  
              <div component="$UI/system/components/justep/list/list" class="x-list"
                xid="carList" data="cartData"> 
                <ul class="x-list-template" xid="listTemplateUl2"> 
                  <li xid="li2"> 
                    <div component="$UI/system/components/bootstrap/row/row"
                      class="row" xid="row1"> 
                      <div class="col col-xs-5" xid="col7"> 
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output" xid="output4" bind-ref="ref('fName')" style="font-weight:bold;"/> 
                      </div>  
                      <div class="col col-xs-2" xid="col8"> 
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output" xid="output11" bind-ref="ref('fPrice')"/> 
                      </div>  
                      <div class="col col-xs-5" xid="col9"> 
                        <a component="$UI/system/components/justep/button/button"
                          class="btn btn-xs btn-only-icon" label="button" xid="reduceCountBtn"
                          icon="icon-android-remove" onClick="reduceCountBtnClick"
                          style="color:white;background-color:#35b3e4;"> 
                          <i xid="i3" class="icon-android-remove"/>  
                          <span xid="span3"/> 
                        </a>  
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output" xid="output5" bind-ref="ref('fCount')"
                          style="text-align:center;margin-right:10px;margin-left:10px;display:inline-block"/>  
                        <a component="$UI/system/components/justep/button/button"
                          class="btn btn-xs btn-only-icon" label="button" xid="addCountBtn"
                          icon="icon-android-add" onClick="addCountBtnClick" style="color:white;background-color:#35b3e4;"> 
                          <i xid="i4" class="icon-android-add"/>  
                          <span xid="span4"/> 
                        </a> 
                      </div> 
                    </div> 
                  </li> 
                </ul> 
              </div> 
            </div>  
            <div class="panel-footer" xid="footer2"> 
              <div component="$UI/system/components/justep/row/row" class="x-row"
                xid="row6"> 
                <div class="x-col" xid="col14"/>  
                <div xid="col15"> 
                  <span xid="span11" style="font-weight:bold;"><![CDATA[合计：]]></span> 
                </div>  
                <div xid="col16"> 
                  <div component="$UI/system/components/justep/output/output"
                    class="x-output" xid="output6" style="padding-top: 0;margin-right:10px;margin-left:10px;font-weight:bold;"
                    bind-ref="statusData.ref('calcCartSumMoneyText')"/> 
                </div> 
              </div> 
            </div> 
          </div>  
          <div class="panel panel-default x-card" component="$UI/system/components/bootstrap/panel/panel"
            xid="panel13"> 
            <div class="panel-heading" xid="heading1"> 
              <h4 class="panel-title" xid="h41"><![CDATA[联系方式]]></h4> 
            </div>  
            <div class="panel-body" xid="body1"> 
              <form class="form-horizontal"> 
                <div class="form-group"> 
                  <label class="col-xs-3 control-label" style="word-spacing:10px;padding-top: 12px;"><![CDATA[姓名]]></label>  
                  <div class="col-xs-9"> 
                    <input component="$UI/system/components/justep/input/input"
                      class="form-control x-edit" xid="input1" bind-ref="userData.ref('fName')"/> 
                  </div> 
                </div>  
                <div class="form-group"> 
                  <label class="col-xs-3 control-label" style="word-spacing:10px;padding-top: 12px;"><![CDATA[电话]]></label>  
                  <div class="col-xs-9"> 
                    <input component="$UI/system/components/justep/input/input"
                      class="form-control x-edit" xid="input2" bind-ref="userData.ref('fPhoneNumber')"/> 
                  </div> 
                </div>  
                <div class="form-group"> 
                  <label class="col-xs-3 control-label" style="word-spacing:10px;padding-top: 12px;"><![CDATA[地址]]></label>  
                  <div class="col-xs-9"> 
                    <input component="$UI/system/components/justep/input/input"
                      class="form-control x-edit" xid="input3" bind-ref="userData.ref('fAddress')"/> 
                  </div> 
                </div> 
              </form>  
              <div xid="paySection" style="height:40px; padding-top: 5px;"> 
                <span component="$UI/system/components/justep/select/radioGroup"
                  class="x-radio-group" xid="payTypeSelect" bind-itemset="payData"
                  bind-itemsetValue="ref('fPayType')" bind-itemsetLabel="ref('fPayTypeLabel')"
                  style="float:right;margin-right:15px;"/> 
              </div> 
            </div>  
            <div class="panel-footer clearfix" xid="footer1"> 
              <a component="$UI/system/components/justep/button/button" class="btn btn-warning btn-sm"
                label="下单" xid="saveOrderBtn" onClick="saveOrderBtnClick" bind-enable="statusData.ref('calcCanSaveOrder')"
                style="float: right; margin-right:10px;width:100px;"> 
                <i xid="i9"/>  
                <span xid="span12">下单</span> 
              </a>  
              <a component="$UI/system/components/justep/button/button" class="btn card btn-link btn-sm"
                label="清空购物车" xid="cleanCartBtn" onClick="cleanCartBtnClick" style="float: right; margin-right:10px;width:100px;"
                bind-enable="statusData.ref('calcCanClearCart')"> 
                <i xid="i10"/>  
                <span xid="span13">清空购物车</span> 
              </a> 
            </div> 
          </div> 
        </div>  
        <div class="x-contents-content x-scroll-view" xid="orderContent"> 
          <div class="x-scroll" component="$UI/system/components/justep/scrollView/scrollView"
            xid="scrollView1"> 
            <div class="x-content-center x-pull-down container" xid="div8"> 
              <i class="x-pull-down-img glyphicon x-icon-pull-down" xid="i12"/>  
              <span class="x-pull-down-label" xid="span14">下拉刷新...</span> 
            </div>  
            <div class="x-scroll-content" xid="div9"> 
              <div component="$UI/system/components/justep/list/list" class="x-list"
                xid="orderList" data="orderData" style="margin: 5px;" limit="10" autoLoad="false"> 
                <ul class="x-list-template" xid="listTemplateUl3"> 
                  <li xid="li3" class="takeout-list-row"> 
                    <div component="$UI/system/components/justep/row/row" class="x-row"
                      xid="row7"> 
                      <div xid="col13"> 
                        <span xid="span17" style="font-weight:bold;" class="x-output">订餐时间：</span> 
                      </div>  
                      <div class="x-col" xid="col18"> 
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output" xid="output7" bind-ref="ref('fCreateTime')"/> 
                      </div> 
                    </div>  
                    <div component="$UI/system/components/justep/row/row" class="x-row"
                      xid="row8"> 
                      <div xid="col20"> 
                        <span xid="span16" style="margin-left:5px;color:#FF8040;"
                          class="x-output">订餐内容：</span> 
                      </div>  
                      <div class="x-col" xid="col21"> 
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output" xid="output8" bind-ref="ref('fContent')"/> 
                      </div> 
                    </div>  
                    <div component="$UI/system/components/justep/row/row" class="x-row"
                      xid="row9"> 
                      <div xid="col23"> 
                        <span xid="span116" style="margin-left:5px;color:#FF8040;"
                          class="x-output">配送信息：</span> 
                      </div>  
                      <div class="x-col" xid="col24"> 
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output" xid="output9" bind-text="val('fUserName') + '，' + val('fPhoneNumber') + '，' + val('fAddress')"/> 
                      </div> 
                    </div>  
                    <div component="$UI/system/components/justep/row/row" class="x-row"
                      xid="row10"> 
                      <div xid="col26" style="margin-left:5px;color:#FF8040;"> 
                        <span xid="span117" class="x-output">合计金额：</span> 
                      </div>  
                      <div class="x-col" xid="col27"> 
                        <div component="$UI/system/components/justep/output/output"
                          class="x-output" xid="output10" bind-text="'¥ ' + val('fSum') + '元'"
                          style="color:#FF0000;font-weight:bold;"/> 
                      </div> 
                    </div>  
                    <div component="$UI/system/components/justep/row/row" class="x-row"
                      xid="row11"> 
                      <div xid="col28" style="margin-left:5px;color:#FF8040;"> 
                        <span xid="span119" class="x-output">支付状态：</span> 
                      </div>  
                      <div class="x-col" xid="col29"> 
                        <span bind-text="(val('fPayState') &gt; 0 &amp;&amp; val('fPayState') &lt; 100) ? '已支付' : '货到付款'"
                          xid="span120" class="x-output"/> 
                      </div> 
                    </div>  
                    <div component="$UI/system/components/justep/row/row" class="x-row"
                      xid="row13"> 
                      <div class="x-col" xid="col31"/>  
                      <div class="x-col" xid="col32"/>  
                      <div class="x-col" xid="col33"/> 
                    </div> 
                  </li> 
                </ul> 
              </div> 
            </div>  
            <div class="x-content-center x-pull-up" xid="div10"> 
              <span class="x-pull-up-label" xid="span15">加载更多...</span> 
            </div> 
          </div> 
        </div>  
        <div class="x-contents-content x-cards" xid="ownContent"> 
          <div xid="photoDiv" style="height:128px;width:128px;margin:5px auto;"> 
            <img alt="" xid="photoImage" height="100%" style="width:100%;"/> 
          </div>  
          <div class="panel panel-default x-card"> 
            <div class="panel-heading clearfix"> 
              <h3 class="panel-title pull-left">默认联系方式</h3> 
            </div>  
            <div class="modal-body"> 
              <form class="form-horizontal"> 
                <div class="form-group"> 
                  <label class="col-xs-3 control-label" style="word-spacing:20px;padding-top: 12px;"><![CDATA[姓名]]></label>  
                  <div class="col-xs-9"> 
                    <input component="$UI/system/components/justep/input/input"
                      class="form-control x-edit" xid="input9" bind-ref="userData.ref('fName')"/> 
                  </div> 
                </div>  
                <div class="form-group"> 
                  <label class="col-xs-3 control-label" style="word-spacing:20px;padding-top: 12px;"><![CDATA[电话]]></label>  
                  <div class="col-xs-9"> 
                    <input component="$UI/system/components/justep/input/input"
                      class="form-control x-edit" xid="input8" bind-ref="userData.ref('fPhoneNumber')"/> 
                  </div> 
                </div>  
                <div class="form-group"> 
                  <label class="col-xs-3 control-label" style="word-spacing:20px;padding-top: 12px;"><![CDATA[地址]]></label>  
                  <div class="col-xs-9"> 
                    <div class="input-group" component="$UI/system/components/bootstrap/inputGroup/inputGroup"
                      xid="inputGroup2"> 
                      <input type="text" class="form-control" component="$UI/system/components/justep/input/input"
                        xid="input5" bind-ref="userData.ref('fAddress')"/>  
                      <div class="input-group-btn" xid="div14"> 
                        <a component="$UI/system/components/justep/button/button"
                          class="btn btn-link" label=" 定位" xid="location" onClick="locationClick"
                          icon="icon-android-location"> 
                          <i xid="i13" class="icon-android-location"/>  
                          <span xid="span19">定位</span> 
                        </a> 
                      </div> 
                    </div> 
                  </div> 
                </div> 
              </form> 
            </div>  
            <div class="panel-footer clearfix"> 
              <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-sm"
                label="保存用户信息" xid="saveUserBtn" onClick="saveUserBtnClick" style="float: right;width:100px;"> 
                <i xid="i2"/>  
                <span xid="span1">保存用户信息</span> 
              </a> 
            </div> 
          </div> 
        </div> 
      </div> 
    </div>  
    <div class="x-panel-bottom" xid="bottom1"> 
      <div component="$UI/system/components/justep/button/buttonGroup" class="btn-group x-card btn-group-justified"
        tabbed="true" xid="buttonGroup1" selected="foodBtn"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-default btn-icon-top"
          label="菜单" xid="foodBtn" icon="icon-beer" target="foodContent"> 
          <i xid="i5" class="icon-beer"/>  
          <span xid="span6">菜单</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-default btn-icon-top"
          xid="cartBtn" icon="icon-ios7-cart" target="cartContent" label="购物车"> 
          <i xid="i6" class="icon-ios7-cart"/>  
          <span xid="span7" bind-text="statusData.ref('calcCartCountText')">购物车</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-default btn-icon-top"
          label="订单" xid="orderBtn" icon="icon-ios7-copy" target="orderContent"> 
          <i xid="i7" class="icon-ios7-copy"/>  
          <span xid="span8">订单</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-default btn-icon-top"
          label="我的" xid="ownBtn" icon="icon-person" target="ownContent"> 
          <i xid="i8" class="icon-person"/>  
          <span xid="span9">我的</span> 
        </a> 
      </div> 
    </div> 
  </div> 
</div>
