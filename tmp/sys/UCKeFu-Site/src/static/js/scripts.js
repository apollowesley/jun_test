$(window).load(function(){

  var body = $("body"),
      universe = $("#universe"),
      solarsys = $("#solar-system");

  var init = function() {
    body.removeClass('view-2D opening').addClass("view-3D").delay(2000).queue(function() {
      $(this).removeClass('hide-UI').addClass("set-speed");
      $(this).dequeue();
    });
  };

  var setView = function(view) { universe.removeClass().addClass(view); };

  $("#toggle-data").click(function(e) {
    body.toggleClass("data-open data-close");
    e.preventDefault();
  });
  init();

});