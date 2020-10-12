defmodule FormlessWeb.Plug.RequireLogout do
  import Plug.Conn
  import Phoenix.Controller, only: [put_flash: 3, redirect: 2]

  alias FormlessWeb.Router.Helpers, as: Routes

  def init(opts), do: opts

  def call(conn, _opts) do
    if conn.assigns[:current_user] do
      conn
      |> put_flash(:error, "用户已登录")
      |> redirect(to: Routes.page_path(conn, :index))
      |> halt()
    else
      conn
    end
  end
end
