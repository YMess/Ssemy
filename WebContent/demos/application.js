$(function() {

  $('#switch-me').switchy();

  $('.register').on('click', function(){
    $('#switch-me').val($(this).attr('register')).change();
  });

  $('#switch-me').on('change', function(){
    
    // Animate Switchy Bar background color
    var bgColor = '#ccb3dc';
    
    if ($(this).val() == 'Company'){
	  $('#register_type').val($(this).val());
      bgColor = '#ed7ab0';
    } else if ($(this).val() == 'Individual'){
	  $('#register_type').val($(this).val());
      bgColor = '#7fcbea';
    }else{
      $('#register_type').val(null);
    	}

    $('.switchy-bar').animate({
      backgroundColor: bgColor
    });

    // Display action in console
	if ($(this).val() == 'Company'){
    var log =  'You have selected  "'+$(this).val()+'"';
    } else if ($(this).val() == 'Individual'){
	 var log =  'You have selected  "'+$(this).val()+'"';
    }else{
   	var log = 'Please select registration type';
	}
	
	var hv = $('#register_type').val();
    //alert(hv);

    $('#console').html(log).hide().fadeIn();
  });
});