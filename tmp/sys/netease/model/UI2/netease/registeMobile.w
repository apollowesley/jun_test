<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" xid="window" class="window" component="$UI/system/components/justep/window/window"
  design="device:m;">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:87px;top:63px;"
    onLoad="modelLoad"> 
    <!--     <div component="$UI/system/components/justep/data/baasData" autoLoad="true" -->  
    <!--       xid="baasData2" queryAction="queryUsers" tableName="taobao_user" url="/justep/taobao"> -->  
    <!--       <filter name="userfilter" xid="filter2"><![CDATA[fID=:user]]></filter></div>  -->  
   
   <div component="$UI/system/components/justep/data/baasData" autoLoad="false"
      xid="testData" queryAction="queryNetease_user" tableName="netease_user" url="/justep/netease"
      idColumn="fID" saveAction="saveNetease_user" autoNew="false" directDelete="true"> 
      <filter name="filter0" xid="neteaseFilter1"><![CDATA[fID=:usera]]></filter>  
      <column label="ID" name="fID" type="String" xid="default1"></column>
  <column label="QQ;weibo;weixin;sim" name="fType" type="String" xid="default2"></column>
  <column label="姓名" name="fName" type="String" xid="default3"></column>
  <column label="密码" name="passwd" type="String" xid="default4"></column>
  <column label="电话" name="fPhoneNumber" type="String" xid="default5"></column>
  <column label="地址" name="fAddress" type="String" xid="default11"></column></div>
  
  
  
   <div component="$UI/system/components/justep/data/baasData" autoLoad="false"
      xid="userData" queryAction="queryNetease_user" tableName="netease_user" url="/justep/netease"
      idColumn="fID" saveAction="saveNetease_user" autoNew="false"> 
      <!--   <column label="密码" name="passwd" type="String" xid="default17"></column> -->  
      <filter name="filter0" xid="filter5"><![CDATA[fID=:usera]]></filter>  
      <column label="ID" name="fID" type="String" xid="default6"></column>
  <column label="QQ;weibo;weixin;sim" name="fType" type="String" xid="default7"></column>
  <column label="姓名" name="fName" type="String" xid="default8"></column>
  <column label="密码" name="passwd" type="String" xid="default9"></column>
  <column label="电话" name="fPhoneNumber" type="String" xid="default10"></column>
  <column label="地址" name="fAddress" type="String" xid="default12"></column></div>
  
  </div>  
  <div component="$UI/system/components/justep/popOver/popOver" class="x-popOver"
    xid="popOver"> 
    <div class="x-popOver-overlay" xid="div9"/> 
  </div>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full x-card"
    xid="panel1"> 
   <div class="x-panel-top" xid="top1"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"
        xid="titleBar1"> 
        <div class="x-titlebar-left" xid="div2"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-icon-left"
            label="手机注册" xid="button6" icon="icon-arrow-left-c" onClick="{operation:'window.close'}"> 
            <i xid="i2" class="icon-arrow-left-c"/>  
            <span xid="span6">手机注册</span> 
          </a>  
          </div>  
        <div class="x-titlebar-title" xid="div3"/>  
        <div class="x-titlebar-right reverse" xid="div4"/> 
      </div> 
    </div>  
    <div class="x-panel-content x-cards" xid="content1"> 
      <div component="$UI/system/components/justep/panel/panel" class="panel panel-default tb-noborder x-card x-tuniu"
        xid="panel3"> 
        <div> 
          <div class="panel-heading"> 
            <div class="media-left media-middle"> 
              <span class="x-flex" style="width:42px;"><![CDATA[手机号]]></span> 
            </div>  
            <div class="media-body" style="width:119px;"> 
              <input component="$UI/system/components/justep/input/input" class="form-control input-sm tb-noborder text-muted"
                xid="phonenumber" bind-value="'18501978580'" style="width:202px;"
                bind-ref="userData.ref('fPhoneNumber')"/> 
            </div> 
          </div>  
          <div class="panel-heading" xid="div1"> 
            <div class="media-left media-middle" xid="div2" style="height:37px;"> 
              <span class="x-flex" style="width:44px;" xid="span2"><![CDATA[验证码]]></span> 
            </div>  
            <div class="media-body" xid="div3" style="height:28px;width:140px;"> 
              <input component="$UI/system/components/justep/input/input" class="form-control input-sm tb-noborder text-muted"
                xid="verifyCode" style="height:33px;width:127px;"/> 
            </div>  
            <div class="media-body" style="width:760px;" xid="div7"> 
              <a component="$UI/system/components/justep/button/button" class="btn x-black btn-only-label btn-block"
                label="获取验证码" xid="sendsmsbutton" onClick="sendsmsButton" style="height:33px;width:139px;"> 
                <i xid="i3"/>  
                <span xid="span5">获取验证码</span> 
              </a> 
            </div> 
          </div>  
          <div class="panel-heading" xid="div10">
   <div class="media-left media-middle" xid="div8">
    <span class="x-flex" style="width:44px;" xid="span7">密码</span></div> 
   <div class="media-body" xid="div5" style="width:133px;">
    <input component="$UI/system/components/justep/input/input" class="form-control" xid="password" style="width:128px;height:32px;" bind-ref="userData.ref('passwd')"></input></div> 
   <div class="media-body" style="width:60px;" xid="div4">
     </div> </div>
  <div class="panel-heading" xid="div6">
   
   
   <div class="media-body" style="width:119px;" xid="div13" align="justify">
    </div> <a component="$UI/system/components/justep/button/button" class="btn x-black btn-only-label btn-block" label="提交" xid="button2" onClick="verifyButton" >
     <i xid="i2"></i>
     <span xid="span4">提交</span></a></div>
  </div> 
      <span xid="span8" class="text-muted" bind-click="dbTestClick"><![CDATA[测试数据库连接]]></span></div> 
    </div> 
  </div> 
</div>
