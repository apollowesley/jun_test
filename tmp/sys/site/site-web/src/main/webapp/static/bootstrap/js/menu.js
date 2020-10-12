$(function() {
  $('#scroll_top').on('click', function() {
    this.disabled = true;
    $('body, html').animate({
      scrollTop: 0
    }, 800, $.proxy(function() {
      this.disabled = false;
    }, this));

    this.blur();
  });

  $('.dropdown > a[tabindex]').on('keydown', function(event) {
    if (event.keyCode == 13) {
      $(this).dropdown('toggle');
    }
  });

  $('.dropdown-menu > .disabled, .dropdown-header').on('click.bs.dropdown.data-api', function(event) {
    event.stopPropagation();
  });

  $('[data-submenu]').submenupicker();

  hljs.initHighlighting();
});