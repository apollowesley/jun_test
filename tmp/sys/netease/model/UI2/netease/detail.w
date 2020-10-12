<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="main13" component="$UI/system/components/justep/window/window"
  design="device:m;" xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;top:6px;left:139px;"
    onLoad="modelLoad">
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="postData" onCustomRefresh="postDataCustomRefresh" idColumn="fID" limit="20">
      <column label="id" name="fID" type="String" xid="xid1"/>  
      <column label="名称" name="fName" type="String" xid="xid2"/>  
      <column label="头像" name="fImg" type="String" xid="xid6"/>  
      <column label="来源" name="fSource" type="String" xid="xid3"/>  
      <column label="次数" name="fNumber" type="Integer" xid="xid4"/>  
      <column label="留言内容" name="fContent" type="String" xid="xid5"/>  
      <column label="时间" name="fTime" type="String" xid="xid7"/>
    </div>
  </div>  
  <div component="$UI/system/components/justep/popMenu/popMenu" class="x-popMenu"
    xid="popMenu" direction="right-bottom" anchor="popMenuBtn"> 
    <div class="x-popMenu-overlay" xid="div3"/>  
    <ul component="$UI/system/components/justep/menu/menu" class="x-menu dropdown-menu x-popMenu-content"
      xid="menu2">
      <li class="x-menu-item" xid="item1"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left"
          label="分享" xid="button11" icon="icon-ios7-redo"> 
          <i xid="i10" class="icon-ios7-redo"/>  
          <span xid="span21">分享</span>
        </a> 
      </li>  
      <li class="x-menu-item" xid="item2"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left"
          label="收藏" xid="button15" icon="icon-ios7-star-outline"> 
          <i xid="i14" class="icon-ios7-star-outline"/>  
          <span xid="span25">收藏</span>
        </a> 
      </li>
      <li class="x-menu-item" xid="item3"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left"
          label="截屏" xid="button13" icon="icon-ios7-albums-outline"> 
          <i xid="i12" class="icon-ios7-albums-outline"/>  
          <span xid="span23">截屏</span>
        </a> 
      </li>  
      <li class="x-menu-divider divider" xid="divider1"/>
      <li class="x-menu-item" xid="item4"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left"
          label="夜间模式" xid="button14" icon="icon-android-display"> 
          <i xid="i13" class="icon-android-display"/>  
          <span xid="span24">夜间模式</span>
        </a> 
      </li> 
    </ul>
  </div>
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-card x-has-iosstatusbar"> 
    <div class="x-panel-top" height="49"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"> 
        <div class="x-titlebar-left text-white"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon"
            label="搜索" xid="button6" icon="icon-arrow-left-c" onClick="{operation:'window.close'}"> 
            <i xid="i6" class="icon-arrow-left-c"/>  
            <span xid="span6">搜索</span> 
          </a> 
        </div>  
        <div class="x-titlebar-title"><a class="pull-right"><span bind-click="postClick" class="badge"><![CDATA[1915跟贴]]></span></a></div>  
        <div class="x-titlebar-right reverse text-white"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon" xid="popMenuBtn" icon="icon-android-more" onClick="popMenuBtnClick">
   <i xid="i1" class="icon-android-more"></i>
   <span xid="span1"></span></a>
        </div> 
      </div> 
    </div>  
    <div class="x-panel-content x-cards panel-body"> 
      <h3 class=" text-black"><![CDATA[全国约1亿人口跨省流动 迁移致养老压力失衡]]></h3>  
      <div class="h6 text-muted"> 
        <span>学习小组</span>  
        <span>10-12 09：31</span> 
      </div>  
      <div class="h6">
        <img alt="" class="img-responsive" bind-attr-src="$model.getImageUrl('./img/4.jpg')"/>
      </div>
      <div class="text-black lead">目前，全国约有一亿的人口在跨省流动，其中九成以上是劳动年龄人口。这种不平衡的人口迁移流动使得人口净流入地和净流出地的老龄化程度差异较大，给就业、养老带来了巨大的压力。10日举行的“人口、劳动力流动与空间集聚”全国学术研讨会探讨了这一困局，并提出了尽快提高养老和医疗保险统筹层次等建议。 生育、死亡、迁移是人口变化的三大主要特征。由于生育、死亡的变化相对稳定，中国未来人口变化更多取决于迁移。
        <br/>根据第六次人口普查数据，2010年全国跨省流动人口数量为8587万，约占总人口的38.8%。南开大学经济学院人口与发展研究所所长陈卫民认为，跨省流动人口正在持续增加中，2014年全国跨省流动人口数量估计达9830万人。 对跨省流动人口进行分析可以发现，90%以上流动人口是劳动年龄人口，65岁以上的人口仅占1.32%。 
        <br/>大量劳动年龄人口的迁移流动直接影响了各个省市的老龄化程度。广东、上海、浙江、北京、江苏等人口净流入地的老龄化程度因此减轻，同时河南、四川、安徽、贵州等人口净流出地的老龄化程度加剧。 这种人口迁移流动状况导致了目前养老保险的不公平：经济越发达的地区，吸引了更多更优质的劳动力流入，缴纳养老保险的人数更多，养老压力相对更低;而经济越落后的地区，更多劳动力流出，缴纳养老保险的人数减少，老年抚养比更高，养老压力更大。数据显示，2013年制度抚养比最低的后几位省市都是人口净流入较大的省市，而制度抚养比高的省市多为人口净流出的省市。 
        <br/>陈卫民认为，鉴于劳动力流动对流出地和流入地社会保障制度的不同影响，我国的养老社会保险需要继续提高统筹层次，直至实现全国统筹。由于医疗社会保险统筹层次更低，绝大部分是地市级，提高医疗保险统筹层次更具有紧迫性。
      </div>  
      <div class="h5">
        <span><![CDATA[来源：]]></span>  
        <span><![CDATA[新华社]]></span>
      </div>  
      <div class="list-group" xid="listGroup1"> 
        <div class="list-group-item">
          <div class="panel-heading o-nopadding" bind-text="'分享到'"/>  
          <div component="$UI/system/components/bootstrap/row/row" class="row text-center text-muted panel-body x-nomargin o-nopadding"
            xid="row1"> 
            <div class="col col-xs-3 o-nopadding" xid="col5"> 
              <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/detail1.png')"
                class="o-img4" xid="image5"/>  
              <div bind-text="'易信朋友圈'" xid="div5"/>
            </div>  
            <div class="col col-xs-3 o-nopadding" xid="col6"> 
              <img src="" alt="" bind-attr-src="$model.getImageUrl('./img/detail2.png')"
                class="o-img4" xid="image6"/>  
              <div bind-text="'微信朋友圈'" xid="div6"/>
            </div>  
            <div class="col col-xs-3 o-nopadding" xid="col7"> 
              <img src="" alt="" xid="image4" bind-attr-src="$model.getImageUrl('./img/detail3.png')"
                class="o-img4"/>  
              <div bind-text="'新浪微博'" xid="div7"/>
            </div>  
            <div class="col col-xs-3 o-nopadding" xid="col8"> 
              <img src="" alt="" xid="image4" bind-attr-src="$model.getImageUrl('./img/detail4.png')"
                class="o-img4"/>  
              <div bind-text="'更多'" xid="div8"/>
            </div> 
          </div>
        </div>
      </div>  
      <div class="list-group" xid="listGroup2"> 
        <div class="list-group-item">
          <div class="panel-heading o-nopadding" bind-text="'热门跟帖'"/>
          <div component="$UI/system/components/justep/list/list" class="x-list"
            xid="list1" data="postData" limit="2" disablePullToRefresh="true" disableInfiniteLoad="true"> 
            <ul class="x-list-template" xid="listTemplateUl1"> 
              <li xid="li1" class="panel-heading o-nopadding">
                <div class="media">
                  <div class="media-left">
                    <img src="" alt="" bind-attr-src="$model.getImageUrl(val(&quot;fImg&quot;))"
                      class="o-img4" xid="image2"/>
                  </div>  
                  <div class="media-body">
                    <span xid="span9" class="pull-right text-muted glyphicon glyphicon-thumbs-up"><![CDATA[]]></span>
                    <span xid="span7" bind-text="ref('fNumber')" class="pull-right text-muted"/>
                    <span xid="span5" bind-text="ref('fName')" class="text-primary"/>
                    <div class="text-muted">
                      <span bind-text="ref('fTime')"/>  
                      <span xid="span2"><![CDATA[前 网易]]></span>  
                      <span xid="span3" bind-text="ref('fSource')"/>  
                      <span xid="span4"><![CDATA[用户]]></span>
                    </div>  
                    <div bind-text=" $object.val(&quot;fContent&quot;)"/> 
                  </div>
                </div>
              </li>
            </ul> 
          </div>  
          <div component="$UI/system/components/justep/button/buttonGroup"
            class="btn-group btn-group-justified" tabbed="true" xid="buttonGroup1">
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label"
              label="查看更多跟帖" xid="button3" onClick="postClick"> 
              <i xid="i3"/>  
              <span xid="span12" class="text-muted">查看更多跟帖</span>
            </a>
          </div>
        </div> 
      </div>
    </div>  
    <div class="x-panel-bottom" xid="bottom1"> 
      <div class="input-group"> 
        <span class="input-group-addon"> 
          <i xid="i6" class="icon-edit text-muted"/> 
        </span>  
        <input component="$UI/system/components/justep/input/input" class="form-control x-inputText"
          xid="nameInput"/> 
      </div>  
      <span class="form-control-feedback" aria-hidden="true"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-label o-nopadding"
          label="发送" xid="button9"> 
          <i xid="i8"/>  
          <span class="text-muted">发送</span> 
        </a> 
      </span> 
    </div> 
  </div> 
</div>
