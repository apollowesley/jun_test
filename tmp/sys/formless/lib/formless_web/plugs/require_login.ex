defmodule FormlessWeb.Plug.RequireLogin do
  import Plug.Conn
  import Phoenix.Controller, only: [put_flash: 3, redirect: 2]

  alias FormlessWeb.Router.Helpers, as: Routes

  def init(opts), do: opts

  def call(conn, _opts) do
    if conn.assigns[:current_user] do
      conn
    else
      conn
      |> put_flash(:error, "需要登录")
      |> redirect(to: Routes.session_path(conn, :new))
      |> halt()
    end
  end
end
