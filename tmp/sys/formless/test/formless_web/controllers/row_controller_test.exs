defmodule FormlessWeb.RowControllerTest do
  use FormlessWeb.ConnCase

  alias Formless.Store

  @create_attrs %{}
  @update_attrs %{}
  @invalid_attrs %{}

  def fixture(:row) do
    {:ok, row} = Store.create_row(@create_attrs)
    row
  end

  describe "index" do
    test "lists all rows", %{conn: conn} do
      conn = get(conn, Routes.row_path(conn, :index))
      assert html_response(conn, 200) =~ "Listing Rows"
    end
  end

  describe "new row" do
    test "renders form", %{conn: conn} do
      conn = get(conn, Routes.row_path(conn, :new))
      assert html_response(conn, 200) =~ "New Row"
    end
  end

  describe "create row" do
    test "redirects to show when data is valid", %{conn: conn} do
      conn = post(conn, Routes.row_path(conn, :create), row: @create_attrs)

      assert %{id: id} = redirected_params(conn)
      assert redirected_to(conn) == Routes.row_path(conn, :show, id)

      conn = get(conn, Routes.row_path(conn, :show, id))
      assert html_response(conn, 200) =~ "Show Row"
    end

    test "renders errors when data is invalid", %{conn: conn} do
      conn = post(conn, Routes.row_path(conn, :create), row: @invalid_attrs)
      assert html_response(conn, 200) =~ "New Row"
    end
  end

  describe "edit row" do
    setup [:create_row]

    test "renders form for editing chosen row", %{conn: conn, row: row} do
      conn = get(conn, Routes.row_path(conn, :edit, row))
      assert html_response(conn, 200) =~ "Edit Row"
    end
  end

  describe "update row" do
    setup [:create_row]

    test "redirects when data is valid", %{conn: conn, row: row} do
      conn = put(conn, Routes.row_path(conn, :update, row), row: @update_attrs)
      assert redirected_to(conn) == Routes.row_path(conn, :show, row)

      conn = get(conn, Routes.row_path(conn, :show, row))
      assert html_response(conn, 200)
    end

    test "renders errors when data is invalid", %{conn: conn, row: row} do
      conn = put(conn, Routes.row_path(conn, :update, row), row: @invalid_attrs)
      assert html_response(conn, 200) =~ "Edit Row"
    end
  end

  describe "delete row" do
    setup [:create_row]

    test "deletes chosen row", %{conn: conn, row: row} do
      conn = delete(conn, Routes.row_path(conn, :delete, row))
      assert redirected_to(conn) == Routes.row_path(conn, :index)
      assert_error_sent 404, fn ->
        get(conn, Routes.row_path(conn, :show, row))
      end
    end
  end

  defp create_row(_) do
    row = fixture(:row)
    {:ok, row: row}
  end
end
