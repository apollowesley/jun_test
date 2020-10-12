<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" class="main13" component="$UI/system/components/justep/window/window"
  design="device:mobile;" xid="window">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:13px;top:202px;"
    onLoad="modelLoad" onunLoad="modelUnLoad"/>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-has-iosstatusbar"
    xid="panel"> 
    <div class="x-panel-content" xid="content1"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"> 
        <div class="x-titlebar-left" xid="div2"/>  
        <div class="x-titlebar-title" xid="div3"/>  
        <div class="x-titlebar-right reverse" xid="div4"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-xs btn-only-label"
            label="设置" xid="button1"> 
            <i xid="i1"/>  
            <span xid="span8">设置</span> 
          </a> 
        </div> 
      </div>  
      <div class="text-center o-user"> 
        <img alt="" xid="image1" class="img1" bind-attr-src="$model.getImageUrl('./img/user.png')"/>  
        <div xid="div1" class="h4"> 
          <a xid="a1" class="text-white" bind-click="switchBtnClick(event)"
            bind-text=" $model.switchText()"><![CDATA[立即登录]]></a> 
        </div>  
        <div xid="div5" bind-text="&quot;羸积分 抢大礼&quot;" class="h5 text-white"/> 
      </div>  
      <div component="$UI/system/components/justep/panel/panel" class="panel panel-default x-card"> 
        <div component="$UI/system/components/justep/button/buttonGroup" class="btn-group btn-group-justified"
          tabbed="true" xid="buttonGroup3"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top"
            label="阅读" xid="button5" icon="icon-ios7-bookmarks-outline"> 
            <i xid="i13" class="icon text-muted icon-ios7-bookmarks-outline"/>  
            <span xid="span38" class="text-muted">阅读</span> 
          </a>  
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top"
            label="收藏" xid="button7" icon="icon-ios7-star"> 
            <i xid="i14" class="icon text-muted icon-ios7-star"/>  
            <span xid="span39" class="text-muted">收藏</span> 
          </a>  
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top"
            label="跟帖" xid="button9" icon="icon-edit"> 
            <i xid="i15" class="icon text-muted icon-edit"/>  
            <span xid="span40" class="text-muted">跟帖</span> 
          </a>  
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-top"
            label="金币" xid="button10" icon="icon-social-buffer"> 
            <i xid="i16" class="icon text-muted icon-social-buffer"/>  
            <span xid="span41" class="text-muted">金币</span> 
          </a> 
        </div> 
      </div>  
      <div component="$UI/system/components/justep/panel/panel" class="panel panel-default x-card"> 
        <div class="list-group-item"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon pull-right"
            label="button" xid="button11" icon="icon-ios7-arrow-right"> 
            <i xid="i17" class="icon-ios7-arrow-right text-muted"/>  
            <span xid="span14"/> 
          </a>  
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon"
            icon="icon-chatbox-working"> 
            <i xid="i6" class="icon-chatbox-working text-danger"/>  
            <span xid="span13"/> 
          </a>  
          <span xid="span15"><![CDATA[我的消息]]></span> 
        </div> 
      </div>  
      <div component="$UI/system/components/justep/panel/panel" class="panel panel-default x-card"> 
        <ul xid="ul1"> 
          <li class="list-group-item" xid="li1"> 
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon pull-right"
              label="button" xid="button12" icon="icon-ios7-arrow-right"> 
              <i xid="i18" class="icon-ios7-arrow-right text-muted"/>  
              <span xid="span17"/> 
            </a>  
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon"
              icon="icon-bag" xid="button13"> 
              <i xid="i26" class="icon-bag text-warning"/>  
              <span xid="span18"/> 
            </a>  
            <span xid="span16">金币商城</span> 
          </li>  
          <li class="list-group-item" xid="li1"> 
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon pull-right"
              label="button" xid="button14" icon="icon-ios7-arrow-right"> 
              <i xid="i27" class="icon-ios7-arrow-right text-muted"/>  
              <span xid="span20"/> 
            </a>  
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon"
              icon="icon-clipboard" xid="button15"> 
              <i xid="i28" class="icon-clipboard"/>  
              <span xid="span21"/> 
            </a>  
            <span xid="span19">我的任务</span> 
          </li>  
          <li class="list-group-item" xid="li1"> 
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon pull-right"
              label="button" xid="button16" icon="icon-ios7-arrow-right"> 
              <i xid="i29" class="icon-ios7-arrow-right text-muted"/>  
              <span xid="span23"/> 
            </a>  
            <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon"
              icon="icon-ios7-briefcase-outline" xid="button17"> 
              <i xid="i30" class="icon-ios7-briefcase-outline text-danger"/>  
              <span xid="span24"/> 
            </a>  
            <span xid="span22">我的钱包</span> 
          </li> 
        </ul> 
      </div>  
      <div component="$UI/system/components/justep/panel/panel" class="panel panel-default x-card"> 
        <div class="list-group-item" xid="div10"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon pull-right"
            label="button" xid="button18" icon="icon-ios7-arrow-right"> 
            <i xid="i31" class="icon-ios7-arrow-right text-muted"/>  
            <span xid="span26"/> 
          </a>  
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon"
            icon="icon-email" xid="button19"> 
            <i xid="i32" class="icon-email text-success"/>  
            <span xid="span27"/> 
          </a>  
          <span xid="span25"><![CDATA[我的邮箱]]></span> 
        </div> 
      </div> 
    </div> 
  </div> 
</div>
