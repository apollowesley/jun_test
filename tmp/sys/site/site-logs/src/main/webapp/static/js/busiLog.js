;
(function($, undefined) {
  $(function() {

    $("#queryButton").click(function() {
      jpage.ajaxPage('queryForm', 'busiLogBlock');
    });

    $("#queryAndDownload").click(function(event) {
      event.stopPropagation();
      $("#queryForm input").appendTo("#downloadAll");
      $("#downloadAll").submit();
    })

  });

})(jQuery);