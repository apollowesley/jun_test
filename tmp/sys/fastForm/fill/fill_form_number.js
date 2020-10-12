$(document).ready(function() {
   
    var i = 0;
      
    $('input[type=text],input[type=password],textarea').each(function() {
        if($(this).val()=="")
        {
            i = i+1;
            
            if($(this).attr('name').toLowerCase().indexOf('email')!=-1 || $(this).attr('name').toLowerCase().indexOf('e-mail')!=-1) $(this).val('email_' + i + '@example.com');
            else $(this).val(i);
            
            if($(this).attr("maxlength")) $(this).val($(this).val().substring(0,$(this).attr("maxlength")));
         
        }
    }) 
});