 $(document).ready(function() {
    $('input').each(function() {
        //radio button
        if($(this).attr('type').toLowerCase()=="radio")
        {
            //check the first one of this "group"
            if(!$("input[name=" + $(this).attr("name") + "]:checked").val()) $("input[name=" + $(this).attr("name") + "]").eq(0).click();
        }
        //checkbox
        if($(this).attr('type').toLowerCase()=="checkbox")
        {
            if($(this).prop("checked")==false)
                $(this).click();
            
        }
     });
    $('select').each(function() {
        if($("select[name=" + $(this).attr("name") + "] option:selected").index()==0)
            $(this).find('option').eq(1).attr("selected", "selected");
    });
    
 });
