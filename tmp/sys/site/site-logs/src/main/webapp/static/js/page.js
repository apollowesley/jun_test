;
(function($, window, document, undefined) {

  var jpage = window.jpage = {};

  jpage.ajaxPage = function(formId, tbodyId) {
    var url = $("#" + formId).attr("action");
    $.ajax({
      cache: true,
      type: "POST",
      url: url,
      data: $('#' + formId).serialize(),// 序列化的form
      async: false,
      error: function(data) {
        saveFlag = true;
        alert('分页查询失败');
      },
      success: function(data) {
        $("#" + tbodyId).empty();
        $("#" + tbodyId).append(data);
      }
    });
  };

  jpage.queryPage = function(formId, tbodyId) {
    $(".paging").click(function(event) {

      // get Parameter
      var totalPage = j_util.parseInt($("#" + formId + " input[name='totalPage']").val());
      var index = j_util.parseInt($("#" + formId + " input[name='curPage']").val());

      var currentPage = 1;

      // 当前页==最大页，不能跳转
      // 第一页时，不能向上一页

      if ($(this).hasClass("pagingIndex")) {
        // 指定页面跳转
        currentPage = j_util.parseInt($("#" + formId + " input[name='pagingIndex']").val());
        if (currentPage >= 1 && currentPage <= totalPage) {
          $("#" + formId + " input[name='pIndex']").val(currentPage);
          // $("#" + formId).submit();
          jpage.ajaxPage(formId, tbodyId);
        } else {
          alert("请输入合法有效的页码");
          $("#" + formId + " input[name='pagingIndex']").val("");
          return false;
        }
      } else {

        currentPage = j_util.parseInt($(this).attr("data-currentPage"));
        if (index == totalPage && currentPage == totalPage) {
          // 若当前页为最后一页，则不能“下一页”
          return false;
        }
        if (index == currentPage && index == 1) {
          // 当前为第一页，不能再“上一页”
          return false;
        }

        // 不合法
        if (currentPage > totalPage) { return false; }

        if (currentPage <= totalPage && currentPage >= 1) {
          $("#" + formId + " input[name='currentPage']").val(currentPage);
          // $("#" + formId).submit();
          jpage.ajaxPage(formId, tbodyId);
        }

      }

    });

    $(".selectPageSize").change(function() {
      $("#" + formId + " input[name='currentPage']").val($(this).attr("data-currentPage"));
      jpage.ajaxPage(formId, tbodyId);
    });

  };

})(jQuery, window, document);
