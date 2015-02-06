$(function() {

  $('#switch-me').switchy();

  $('.gender').on('click', function(){
    $('#switch-me').val($(this).attr('gender')).change();
  });

  $('#switch-me').on('change', function(){
    
    // Animate Switchy Bar background color
    var bgColor = '#ccb3dc';

    if ($(this).val() == 'female'){
      bgColor = '#ed7ab0';
    } else if ($(this).val() == 'male'){
      bgColor = '#7fcbea';
    }

    $('.switchy-bar').animate({
      backgroundColor: bgColor
    });

    // Display action in console
    var log =  'Selected value is "'+$(this).val()+'"';
    $('#console').html(log).hide().fadeIn();
  });
});