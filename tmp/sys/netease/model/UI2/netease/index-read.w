<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="main13" component="$UI/system/components/justep/window/window"
  design="device:mobile;" xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:13px;top:202px;"
    onLoad="modelLoad"/>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-has-iosstatusbar"
    xid="panel1"> 
    <div class="x-panel-top x-has-iosstatusbar" xid="top1" height="48"> 
       
    <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar" xid="titleBar">
   
  <div class="x-titlebar-left flex5" xid="left1"><div component="$UI/system/components/justep/button/buttonGroup" class="btn-group x-nav-guide" tabbed="true" xid="buttonGroup1"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label active" label="推荐阅读" xid="button1" target="readContent1"> 
          <i xid="i1" />  
          <span xid="span1">推荐阅读</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label" label="我的订阅" xid="button2" target="readContent2"> 
          <i xid="i2" />  
          <span xid="span2">我的订阅</span> 
        </a> 
      </div></div><div class="x-titlebar-title" xid="title1"></div><div class="x-titlebar-right reverse" xid="right1"><a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon" label="button" xid="button4" icon="icon-plus">
   <i xid="i10" class="icon-plus"></i>
   <span xid="span6"></span></a></div></div></div>  
    <div class="x-panel-content" xid="content1"> 
      <div component="$UI/system/components/justep/contents/contents" class="x-contents x-full"
        active="0" xid="contents1"> 
        <div class="x-contents-content panel-body" xid="readContent1"> 
          <div class="text-center panel-body"><a component="$UI/system/components/justep/button/button" class="btn x-gray" label="立即登录" xid="loginBtn" style="width:70%;" onClick="loginBtnClick">
   <i xid="i4"></i>
   <span xid="span5" class="text-black">立即登录</span></a>
  <div xid="div3" bind-text="'获取更符合口味的推荐'" class="text-muted"></div></div><div class="list-group" xid="listGroup1"> 
            <div class="list-group-item" xid="div37"> 
              <div xid="div16" class="media-heading" bind-click="detailClick"> 
                <div class="media-left" xid="div17"> 
                  <a xid="a2"> 
                    <img alt="" xid="image3" class="img_new" bind-attr-src="$model.getImageUrl('./img/a1.jpg')"/> 
                  </a> 
                </div>  
                <div class="media-right" xid="div18"> 
                  <h5 class="x-flex1 o-nomargintop"
                    >睡前一件事让你肚子暴瘦</h5> 
                </div> 
              </div>  
              <div xid="div19"> 
                <i class="icon-android-close pull-right" xid="i7"/>  
                <span xid="span3" class="text-muted">三九养生网</span> 
              </div> 
            </div> 
          </div>  
          <div class="list-group" xid="listGroup2"> 
            <div class="list-group-item" xid="div25"> 
              <div xid="div21" class="media-heading" bind-click="detailClick"> 
                <div class="media-left" xid="div20"> 
                  <a xid="a3"> 
                    <img alt="" xid="image2" class="img_new" bind-attr-src="$model.getImageUrl('./img/a2.jpg')"/> 
                  </a> 
                </div>  
                <div class="media-right" xid="div23"> 
                  <a xid="a5"> 
                    <h5 class="x-flex1 o-nomargintop"
                      ><![CDATA[黑龙江黑河“95后”新兵头插国旗]]></h5> 
                  </a> 
                </div> 
              </div>  
              <div xid="div22"> 
                <i xid="i5" class="icon-android-close pull-right"/>  
                <span xid="span7" class="text-muted"><![CDATA[新华炫闻]]></span> 
              </div> 
            </div> 
          </div>  
          <div class="list-group" xid="listGroup3"> 
            <div class="list-group-item" xid="div31"> 
              <div xid="div27" class="media-heading" bind-click="detailClick"> 
                <div xid="div35" class="col-xs-5 pull-left"> 
                  <div class="media-left" xid="div26"> 
                    <img alt="" xid="image4" bind-attr-src="$model.getImageUrl('./img/b1.jpg')" class="img-responsive img_big1"/>  
                    <img alt="" xid="image6" bind-attr-src="$model.getImageUrl('./img/b2.jpg')" class="img-responsive img_big1"/> 
                  </div> 
                </div>  
                <div xid="div36" class="col-xs-7 pull-left"> 
                  <img alt="" xid="image7" class="img_big2" bind-attr-src="$model.getImageUrl('./img/b3.jpg')"/> 
                </div> 
              <div class="clearfix"></div></div>  
              <div xid="div34" class="media-heading"> 
                <img src="./img/icon2.png" alt="" xid="image8"
                  class="img_small"/>  
                <span xid="span9" class="text-danger">大家都在看</span>  
                <h5 style="text-align: justify;" class="x-flex1"
                  >以后，你可能看不到这些品牌了</h5> 
              </div>  
              <div xid="div28"> 
                <i xid="i6" class="icon-android-close pull-right"/>  
                <span xid="span8" class="text-muted">爱范儿</span> 
              </div> 
            </div> 
          </div>  
          <div class="list-group" xid="listGroup5"> 
            <div class="list-group-item" xid="div52"> 
              <div xid="div48" class="media-heading" bind-click="detailClick"> 
                <div class="media-left" xid="div47"> 
                  <a xid="a7"> 
                    <img alt="" xid="image13" class="img_new" bind-attr-src="$model.getImageUrl('./img/a3.jpg')"/> 
                  </a> 
                </div>  
                <div class="media-right" xid="div51"> 
                  <h5 class="x-flex1 o-nomargintop"
                    ><![CDATA[好久没有脑洞大开了]]></h5> 
                </div> 
              </div>  
              <div xid="div50"> 
                <i class="icon-android-close pull-right" xid="i8"/>  
                <span xid="span13" class="text-muted"><![CDATA[全球热门创意]]></span> 
              </div> 
            </div> 
          </div>  
          <div class="list-group"> 
            <div class="list-group-item"> 
              <div class="media-heading" bind-click="detailClick"> 
                <div class="media-left"> 
                  <a> 
                    <img alt="" class="img_new" bind-attr-src="$model.getImageUrl('./img/a4.jpg')"/> 
                  </a> 
                </div>  
                <div class="media-right" xid="div45"> 
                  <a> 
                    <h5 class="x-flex1 o-nomargintop"><![CDATA[4岁男孩难忍病痛求“把我埋了”]]></h5> 
                  </a> 
                </div> 
              </div>  
              <div> 
                <i class="icon-android-close pull-right"/>  
                <span class="text-muted">新华炫闻</span> 
              </div> 
            </div> 
          </div>  
          <div class="list-group"> 
            <div class="list-group-item"> 
              <div class="media-heading" bind-click="detailClick"> 
                <div class="col-xs-7 pull-left"> 
                  <img alt="" xid="image11" class="img-responsive img_big2" bind-attr-src="$model.getImageUrl('./img/b6.jpg')"/> 
                </div>  
                <div class="col-xs-5 pull-left"> 
                  <div class="media-right"> 
                    <img alt="" xid="image12" class="img-responsive img_big1" bind-attr-src="$model.getImageUrl('./img/b4.jpg')" style="width:132px;height:100px;"/>  
                    <img alt="" xid="image14" class="img-responsive img_big1" bind-attr-src="$model.getImageUrl('./img/b5.jpg')"/> 
                  </div> 
                </div> 
              <div class="clearfix"></div></div>  
              <div class="media-heading"> 
                <h5 class="x-flex1"><![CDATA[佟丽娅挺大肚在家待产 陈思成携马苏出镜]]></h5> 
              </div>  
              <div> 
                <i xid="i9" class="icon-android-close pull-right"/>  
                <span xid="span10" class="text-muted"><![CDATA[花边星闻]]></span> 
              </div> 
            </div> 
          </div> 
        </div>  
        <div class="x-contents-content panel-body" xid="readContent2"> 
          <div class="text-center panel-body" xid="div4">
   <a component="$UI/system/components/justep/button/button" class="btn x-gray" label="立即登录" xid="button5" style="width:70%;" onClick="loginBtnClick">
    <i xid="i11"></i>
    <span xid="span11" class="text-black">立即登录</span></a> 
   <div xid="div1" bind-text="'获取更符合口味的推荐'" class="text-muted"></div></div><div class="panel-heading"> 
            <img src="./img/icon3.png" alt="" class="pull-left media-left"
              height="20px"/>  
            <h4 class="panel-title x-flex1 text-danger"><![CDATA[我的订阅]]></h4> 
          </div>  
          <div class="list-group"> 
            <div class="list-group-item" bind-click="listClick">                
                <a component="$UI/system/components/justep/button/button"
                  class="btn btn-link btn-sm btn-only-icon pull-right x-noPadding"
                  label="button" xid="button6" icon="icon-ios7-arrow-forward"> 
                  <i xid="i16" class="icon-ios7-arrow-forward text-muted"/>  
                  <span xid="span24"/>
                </a>
                <img src="./img/order1.gif" alt="" xid="image17"
                  class="img_order"/>
                <span class="text-muted" xid="span26">我有车</span>               
            </div>  
            <div class="list-group-item"> 
              <div class="media-heading" bind-click="detailClick"> 
                <div class="media-left"> 
                  <a> 
                    <img alt="" xid="image15" class="img_new" bind-attr-src="$model.getImageUrl('./img/a3.jpg')"/> 
                  </a> 
                </div>  
                <div class="media-right"> 
                  <h5 class="x-flex1 o-nomargintop">颜值都不差！这些国产小SUV已经闹的不可开交了</h5>  
                  <span class="text-muted"><![CDATA[54分钟前]]></span> 
                </div> 
              </div>
              <div class="media-heading" bind-click="detailClick"> 
                <div class="media-left"> 
                  <a> 
                    <img alt="" xid="image18" class="img_new" bind-attr-src="$model.getImageUrl('./img/a4.jpg')"/> 
                  </a> 
                </div>  
                <div class="media-right"> 
                  <h5 class="x-flex1 o-nomargintop">国家又开恩了，团购简税减半后美了这几填款车</h5>  
                  <span class="text-muted"><![CDATA[56分钟前]]></span> 
                </div> 
              </div>
            </div> 
          </div>  
          <div class="list-group" xid="listGroup8"> 
            <div class="list-group-item" bind-click="listClick"> 

                <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-sm btn-only-icon pull-right x-noPadding" label="button" xid="button3" icon="icon-ios7-arrow-forward">
   <i xid="i3" class="icon-ios7-arrow-forward text-muted"></i>
   <span xid="span4"></span></a><img src="./img/order2.gif" alt="" class="img_order"
                  xid="image26"/>  
                <span class="text-muted" xid="span21">趣医网</span>

            </div>  
            <div class="list-group-item"> 
              <div class="media-heading" bind-click="detailClick"> 
                <div class="media-left" xid="div71"> 
                  <a xid="a17"> 
                    <img alt="" xid="image24" class="img_new"
                      bind-attr-src="$model.getImageUrl('./img/a5.jpg')"/>
                  </a> 
                </div>  
                <div class="media-right" xid="div84"> 
                  <h5 class="x-flex1 o-nomargintop"
                    >这些食物，万万不能放冰箱</h5>  
                  <span class="text-muted" xid="span22">2015-10-12</span>
                </div> 
              </div>  
              <div class="media-heading" bind-click="detailClick"> 
                <div class="media-left" xid="div72"> 
                  <a xid="a14"> 
                    <img alt="" class="img_new" bind-attr-src="$model.getImageUrl('./img/a6.jpg')"/>
                  </a> 
                </div>  
                <div class="media-right" xid="div86"> 
                  <h5 class="x-flex1 o-nomargintop"
                    >怀疑自己精神分裂的人赶紧来看看</h5>  
                  <span class="text-muted" xid="span23">2015-10-09</span>
                </div> 
              </div> 
            </div> 
          </div>
        </div> 
      </div> 
    </div> 
  </div> 
</div>
