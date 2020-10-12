defmodule FormlessWeb.Plug.RequireTableOwner do
  import Plug.Conn
  import Phoenix.Controller, only: [put_flash: 3, redirect: 2]

  alias Formless.Store
  alias FormlessWeb.Router.Helpers, as: Routes


  def init(opts) when is_binary(opts), do: opts
  def init(_opts), do: "id"

  def call(conn, table_id) do
    user = conn.assigns.current_user
    table = Store.get_table!(conn.params[table_id])

    if user.id == table.user.id do
      assign(conn, :table, table)
    else
      conn
      |> put_flash(:error, "你不是当前数据表拥有者")
      |> redirect(to: Routes.table_path(conn, :index))
      |> halt()
    end
  end
end
