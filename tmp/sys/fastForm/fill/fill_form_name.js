$(document).ready(function() {
    var i=0;
    $('input[type=text],input[type=password],textarea').each(function() {
        //textbox
        if($(this).val()=="")
        {
            if($(this).attr('name').toLowerCase().indexOf('email')!=-1 || $(this).attr('name').toLowerCase().indexOf('e-mail')!=-1) $(this).val($(this).attr('name') + '@example.com');
            else if($(this).attr('name').toLowerCase().indexOf('zip')!=-1 || $(this).attr('name').toLowerCase().indexOf('postal')!=-1) { i=i+1; $(this).val(''+i+i+i+i+i); }
            else $(this).val($(this).attr('name'))
            
            if($(this).attr("maxlength")) $(this).val($(this).val().substring(0,$(this).attr("maxlength")));
            
        }
    })
   
});