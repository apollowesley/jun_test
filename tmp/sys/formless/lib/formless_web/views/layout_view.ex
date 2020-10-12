defmodule FormlessWeb.LayoutView do
  use FormlessWeb, :view

  def flash_messages(conn) do
    for {key, message} <- get_flash(conn) do
      ~E"""
      <div class="alert alert-<%= alert_type(key) %> alert-dismissible fade show">
        <button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
        <%= message %>
      </div>
      """
    end
  end

  defp alert_type("error"), do: "danger"
  defp alert_type(class), do: class
end
