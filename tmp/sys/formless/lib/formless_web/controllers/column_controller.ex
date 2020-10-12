defmodule FormlessWeb.ColumnController do
  use FormlessWeb, :controller

  alias Formless.Store
  alias Formless.Store.Column
  alias FormlessWeb.Plug.{RequireLogin, RequireTableOwner}

  plug RequireLogin
  plug RequireTableOwner, "table_id"


  def new(conn, _params) do
    changeset = Store.change_column(%Column{})
    render(conn, "new.html", changeset: changeset)
  end

  def create(conn, %{"column" => column_params}) do
    table = conn.assigns.table

    case Store.create_column(table, column_params) do
      {:ok, _column} ->
        conn
        |> put_flash(:info, "字段创建成功")
        |> redirect(to: Routes.table_path(conn, :show, table))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_flash(:error, "字段创建失败")
        |> render("new.html", changeset: changeset)
    end
  end

  def edit(conn, %{"id" => id}) do
    column = Store.get_column!(id)
    changeset = Store.change_column(column)
    render(conn, "edit.html", column: column, changeset: changeset)
  end

  def update(conn, %{"id" => id, "column" => column_params}) do
    table = conn.assigns.table
    column = Store.get_column!(id)

    case Store.update_column(column, column_params) do
      {:ok, _column} ->
        conn
        |> put_flash(:info, "更新字段成功")
        |> redirect(to: Routes.table_path(conn, :show, table))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_flash(:error, "更新字段失败")
        |> render("edit.html", column: column, changeset: changeset)
    end
  end

  def delete(conn, %{"id" => id}) do
    table = conn.assigns.table
    column = Store.get_column!(id)
    {:ok, _column} = Store.delete_column(column)

    conn
    |> put_flash(:info, "删除字段成功")
    |> redirect(to: Routes.table_path(conn, :show, table))
  end

  def reorder(conn, _params) do
    table = conn.assigns.table
    columns = Store.list_columns(table)

    render(conn, "reorder.html", columns: columns)
  end

  def do_reorder(conn, %{"column_ids" => column_ids}) do
    table = conn.assigns.table
    ids = Enum.map(column_ids, &String.to_integer/1)

    Store.reorder_columns(ids)

    conn
    |> put_flash(:info, "字段排序成功")
    |> redirect(to: Routes.table_path(conn, :show, table))
  end
end
