function randomString(string_length) {
	var chars = "!@#$%^&*()_+{}[]:\";',.<>?/~`|\\ ";
	var randomstring = '';
	for (var i=0; i<string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum,rnum+1);
	}
	return randomstring;
}


$(document).ready(function() {
   
    $('input[type=text],input[type=password],textarea').each(function() {
        if($(this).val()=='')
        {
            if($(this).attr('name').toLowerCase().indexOf('email')!=-1 || $(this).attr('name').toLowerCase().indexOf('e-mail')!=-1) $(this).val(randomString(16) + '@example.com');
            else $(this).val(randomString(16))
            
            if($(this).attr("maxlength")) $(this).val($(this).val().substring(0,$(this).attr("maxlength")));
        }
    })
    
});