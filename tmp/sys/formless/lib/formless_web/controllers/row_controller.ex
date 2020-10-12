defmodule FormlessWeb.RowController do
  use FormlessWeb, :controller

  alias Formless.Store
  alias Formless.Store.{Column, Row}
  alias FormlessWeb.Plug.{RequireLogin, RequireTableOwner}

  plug RequireLogin
  plug RequireTableOwner, "table_id"


  def new(conn, _params) do
    columns = Store.list_columns(conn.assigns.table)
    changeset = Store.change_row(%Row{})

    render(conn, "new.html", changeset: changeset, columns: columns)
  end

  def create(conn, %{"row" => row_params}) do
    table = conn.assigns.table
    columns = Store.list_columns(table)

    case Store.create_row(table, row_params) do
      {:ok, _row} ->
        conn
        |> put_flash(:info, "数据创建成功")
        |> redirect(to: Routes.table_path(conn, :show, table))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_flash(:error, "数据创建失败")
        |> render("new.html", changeset: changeset, columns: columns)
    end
  end

  def edit(conn, %{"id" => id}) do
    columns = Store.list_columns(conn.assigns.table)
    row = Store.get_row!(id)
    changeset = Store.change_row(row)

    render(conn, "edit.html", row: row, changeset: changeset, columns: columns)
  end

  def update(conn, %{"id" => id, "row" => row_params}) do
    row = Store.get_row!(id)
    table = conn.assigns.table
    columns = Store.list_columns(table)

    case Store.update_row(row, row_params, columns) do
      {:ok, _row} ->
        conn
        |> put_flash(:info, "数据更新成功")
        |> redirect(to: Routes.table_path(conn, :show, table))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_flash(:error, "数据更新失败")
        |> render("edit.html", row: row, changeset: changeset, columns: columns)
    end
  end

  def delete(conn, %{"id" => id}) do
    row = Store.get_row!(id)
    {:ok, _row} = Store.delete_row(row)

    conn
    |> put_flash(:info, "数据删除成功")
    |> redirect(to: Routes.table_path(conn, :show, conn.assigns.table))
  end

  def export_json(conn, _params) do
    table = conn.assigns.table
    columns = Store.list_columns(table)
    rows = Store.export_rows(table)

    rows_map =
      for row <- rows do
        Enum.reduce(columns, %{}, fn column, acc ->
          key = column.field_name
          value = Map.get(row.data, key, Column.default_value(column.data_type))

          Map.put(acc, key, value)
        end)
      end

    send_download(conn, {:binary, Jason.encode!(rows_map)}, filename: "#{table.name}.json")
  end
end
