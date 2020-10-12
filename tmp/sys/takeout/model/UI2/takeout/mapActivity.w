<?xml version="1.0" encoding="utf-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" xid="window" class="window" component="$UI/system/components/justep/window/window"
  design="device:m;">  
  <div component="$UI/system/components/justep/model/model" xid="model"/>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full"
    xid="mapPanel" style="z-index:1;"> 
    <div class="x-panel-top" xid="top3"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"
        xid="titleBar2" title="百度地图定位"> 
        <div class="x-titlebar-left" xid="hideMapPanel"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link"
            label="返回" xid="hideMapBtn" onClick="hideMapBtnClick"> 
            <i xid="i16"/>  
            <span xid="span23"/> 
          </a> 
        </div>  
        <div class="x-titlebar-title" xid="div12">百度地图定位</div>  
        <div class="x-titlebar-right reverse" xid="div13"/> 
      </div> 
    </div>  
    <div class="x-panel-content" xid="mapArea" style="font-size:0"> 
      <iframe style="width:100%;height:100%;border:0;" src="$UI/takeout/map.html"/> 
    </div> 
  </div>  
  <span component="$UI/system/components/justep/windowReceiver/windowReceiver"
    xid="mapDataReceiver" onReceive="mapDataReceiverReceive"/> 
</div>
