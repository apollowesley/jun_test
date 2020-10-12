defmodule FormlessWeb.TableController do
  use FormlessWeb, :controller

  alias Formless.Store
  alias Formless.Store.Table
  alias FormlessWeb.Plug.{RequireLogin, RequireTableOwner}

  plug RequireLogin
  plug RequireTableOwner when action in [:show, :edit, :update, :delete]


  def index(conn, params) do
    tables = Store.list_tables(conn.assigns.current_user, params)
    render(conn, "index.html", tables: tables)
  end

  def new(conn, _params) do
    changeset = Store.change_table(%Table{})
    render(conn, "new.html", changeset: changeset)
  end

  def create(conn, %{"table" => table_params}) do
    case Store.create_table(conn.assigns.current_user, table_params) do
      {:ok, table} ->
        conn
        |> put_flash(:info, "数据表创建成功")
        |> redirect(to: Routes.table_path(conn, :show, table))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_flash(:error, "数据表创建失败")
        |> render("new.html", changeset: changeset)
    end
  end

  def show(conn, params) do
    table = conn.assigns.table
    columns = Store.list_columns(table)
    rows = Store.list_rows(table, params)

    render(conn, "show.html", columns: columns, rows: rows)
  end

  def edit(conn, _params) do
    table = conn.assigns.table
    changeset = Store.change_table(table)

    render(conn, "edit.html", changeset: changeset)
  end

  def update(conn, %{"table" => table_params}) do
    table = conn.assigns.table

    case Store.update_table(table, table_params) do
      {:ok, table} ->
        conn
        |> put_flash(:info, "数据表更新成功")
        |> redirect(to: Routes.table_path(conn, :show, table))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_flash(:error, "数据表更新失败")
        render(conn, "edit.html", changeset: changeset)
    end
  end

  def delete(conn, _params) do
    table = conn.assigns.table
    {:ok, _table} = Store.delete_table(table)

    conn
    |> put_flash(:info, "数据表已删除")
    |> redirect(to: Routes.table_path(conn, :index))
  end
end
