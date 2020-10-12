defmodule FormlessWeb.ViewHelpers do
  use Phoenix.HTML

  def pagination_tag(conn, %{page: page, total: total, total_page: total_page, has_prev: has_prev, has_next: has_next}) do
    ~E"""
    <div class="d-flex justify-content-between align-items-center">
      <ul class="pagination m-0">
        <%= if has_prev do %>
          <li class="page-item">
            <%= link "上一页", to: Phoenix.Controller.current_path(conn, %{page: page - 1}), class: "page-link" %>
          </li>
        <% else %>
          <li class="page-item disabled"><span class="page-link">上一页</span></li>
        <% end %>

        <%= for n <- 1..total_page do %>
          <%= if n == page do %>
            <li class="page-item active"><span class="page-link"><%= n %></span></li>
          <% else %>
            <li class="page-item">
              <%= link n, to: Phoenix.Controller.current_path(conn, %{page: n}), class: "page-link" %>
            </li>
          <% end %>
        <% end %>

        <%= if has_next do %>
          <li class="page-item">
            <%= link "下一页", to: Phoenix.Controller.current_path(conn, %{page: page + 1}), class: "page-link" %>
          </li>
        <% else %>
          <li class="page-item disabled"><span class="page-link">下一页</span></li>
        <% end %>
      </ul>
      <div>
        <span class="mr-2">总数: <%= total %></span>
        <span>页码: <%= page %> / <%= total_page %></span>
      </div>
    </div>
    """
  end
end
