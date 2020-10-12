/****/
(function($) {
    var _setting={
        paddingTop:0,paddingLeft:0,paddingRight:0,paddingBottom:0,
        borderTop:0,borderLeft:0,borderRight:0,borderBottom:0,
        borderStyle:"dotted"
    };

    $.fn.fixedpicSet = function(config){
        for(var i in config){
            _setting[i]=config[i];
        }
        return this;
    }

    $.fn.autoDivWidth=function(){
        //给那些没有宽度的DIV根据里面的图片加div宽度
        var _self = this;
        var children = _self.find('img');
        console.log(children);
        children.each(function(i,em){
            $(this).parent('div').not(_self).css('width',$(this).width());
            $(this).width('100%').height('100%');
        });
        return _self;
    }

    function _fixsize(obj,height,width,lineWidth){
        var children=obj.children();
        var len = children.length;
        var staticWidth = (_setting.paddingRight+_setting.paddingLeft+_setting.borderLeft+_setting.borderRight)*len;//边距不参与变化,这部一直不变
        var percent = (width-staticWidth)/(lineWidth-staticWidth);//参与的缩放比例
        children.css({"float":"left",
            "padding-top":_setting.paddingTop+'px',
            "padding-left":_setting.paddingLeft+'px',
            "padding-bottom":_setting.paddingBottom+'px',
            "padding-right":_setting.paddingRight+'px',
            "border-width":_setting.borderTop+'px '+_setting.borderRight+'px '+_setting.borderBottom+'px '+_setting.borderLeft+'px',
            "border-style":_setting.borderStyle
        });
        
        var lastHeight = Math.round(height*percent)+_setting.paddingBottom+_setting.paddingTop+_setting.borderTop+_setting.borderBottom;
        var lastBoxWidth = width-staticWidth;
        
        children.each(function(index,em){
            var emObj = $(em);
            emObj.height(lastHeight);
            if(index == len-1){
                emObj.width(lastBoxWidth);
            }else{
                var boxWidth = Math.round(emObj.width()*percent);
                lastBoxWidth=lastBoxWidth-boxWidth;
                emObj.width(boxWidth);
            }
            
        });
    }

    $.fn.fixedpic = function(height,width) {
        _self = this;
         width = width || this.width();
         height = height||300;
         var children = this.children();
         len = children.length;
         var lineWidth = 0;
         var aline = $('<div/>').width(width);
         children.each(function(index,em){
             var emObj = $(em);
             var percent = emObj.width()/emObj.height();
             var fixWidth = Math.round(percent*height) ; //保持比例
             aline.append(emObj.clone().width(fixWidth));
             emObj.remove();
             lineWidth += fixWidth+_setting.paddingLeft+_setting.paddingRight+_setting.borderLeft+_setting.borderRight;

             if(lineWidth>width){
                 _fixsize(aline,height,width,lineWidth)
                 _self.append(aline.clone());
                 aline.empty();
                 trueWidth = 0;
                lineWidth= 0;
             }else if(index == len-1){
                 _self.append(aline.clone());
                 aline.empty();
             }

         })
 
    };


})(jQuery)