defmodule Formless.StoreTest do
  use Formless.DataCase

  alias Formless.Store

  describe "tables" do
    alias Formless.Store.Table

    @valid_attrs %{}
    @update_attrs %{}
    @invalid_attrs %{}

    def table_fixture(attrs \\ %{}) do
      {:ok, table} =
        attrs
        |> Enum.into(@valid_attrs)
        |> Store.create_table()

      table
    end

    test "list_tables/0 returns all tables" do
      table = table_fixture()
      assert Store.list_tables() == [table]
    end

    test "get_table!/1 returns the table with given id" do
      table = table_fixture()
      assert Store.get_table!(table.id) == table
    end

    test "create_table/1 with valid data creates a table" do
      assert {:ok, %Table{} = table} = Store.create_table(@valid_attrs)
    end

    test "create_table/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = Store.create_table(@invalid_attrs)
    end

    test "update_table/2 with valid data updates the table" do
      table = table_fixture()
      assert {:ok, %Table{} = table} = Store.update_table(table, @update_attrs)
    end

    test "update_table/2 with invalid data returns error changeset" do
      table = table_fixture()
      assert {:error, %Ecto.Changeset{}} = Store.update_table(table, @invalid_attrs)
      assert table == Store.get_table!(table.id)
    end

    test "delete_table/1 deletes the table" do
      table = table_fixture()
      assert {:ok, %Table{}} = Store.delete_table(table)
      assert_raise Ecto.NoResultsError, fn -> Store.get_table!(table.id) end
    end

    test "change_table/1 returns a table changeset" do
      table = table_fixture()
      assert %Ecto.Changeset{} = Store.change_table(table)
    end
  end

  describe "columns" do
    alias Formless.Store.Column

    @valid_attrs %{data_type: "some data_type", desc: "some desc", field_name: "some field_name", name: "some name"}
    @update_attrs %{data_type: "some updated data_type", desc: "some updated desc", field_name: "some updated field_name", name: "some updated name"}
    @invalid_attrs %{data_type: nil, desc: nil, field_name: nil, name: nil}

    def column_fixture(attrs \\ %{}) do
      {:ok, column} =
        attrs
        |> Enum.into(@valid_attrs)
        |> Store.create_column()

      column
    end

    test "list_columns/0 returns all columns" do
      column = column_fixture()
      assert Store.list_columns() == [column]
    end

    test "get_column!/1 returns the column with given id" do
      column = column_fixture()
      assert Store.get_column!(column.id) == column
    end

    test "create_column/1 with valid data creates a column" do
      assert {:ok, %Column{} = column} = Store.create_column(@valid_attrs)
      assert column.data_type == "some data_type"
      assert column.desc == "some desc"
      assert column.field_name == "some field_name"
      assert column.name == "some name"
    end

    test "create_column/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = Store.create_column(@invalid_attrs)
    end

    test "update_column/2 with valid data updates the column" do
      column = column_fixture()
      assert {:ok, %Column{} = column} = Store.update_column(column, @update_attrs)
      assert column.data_type == "some updated data_type"
      assert column.desc == "some updated desc"
      assert column.field_name == "some updated field_name"
      assert column.name == "some updated name"
    end

    test "update_column/2 with invalid data returns error changeset" do
      column = column_fixture()
      assert {:error, %Ecto.Changeset{}} = Store.update_column(column, @invalid_attrs)
      assert column == Store.get_column!(column.id)
    end

    test "delete_column/1 deletes the column" do
      column = column_fixture()
      assert {:ok, %Column{}} = Store.delete_column(column)
      assert_raise Ecto.NoResultsError, fn -> Store.get_column!(column.id) end
    end

    test "change_column/1 returns a column changeset" do
      column = column_fixture()
      assert %Ecto.Changeset{} = Store.change_column(column)
    end
  end

  describe "rows" do
    alias Formless.Store.Row

    @valid_attrs %{}
    @update_attrs %{}
    @invalid_attrs %{}

    def row_fixture(attrs \\ %{}) do
      {:ok, row} =
        attrs
        |> Enum.into(@valid_attrs)
        |> Store.create_row()

      row
    end

    test "list_rows/0 returns all rows" do
      row = row_fixture()
      assert Store.list_rows() == [row]
    end

    test "get_row!/1 returns the row with given id" do
      row = row_fixture()
      assert Store.get_row!(row.id) == row
    end

    test "create_row/1 with valid data creates a row" do
      assert {:ok, %Row{} = row} = Store.create_row(@valid_attrs)
    end

    test "create_row/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = Store.create_row(@invalid_attrs)
    end

    test "update_row/2 with valid data updates the row" do
      row = row_fixture()
      assert {:ok, %Row{} = row} = Store.update_row(row, @update_attrs)
    end

    test "update_row/2 with invalid data returns error changeset" do
      row = row_fixture()
      assert {:error, %Ecto.Changeset{}} = Store.update_row(row, @invalid_attrs)
      assert row == Store.get_row!(row.id)
    end

    test "delete_row/1 deletes the row" do
      row = row_fixture()
      assert {:ok, %Row{}} = Store.delete_row(row)
      assert_raise Ecto.NoResultsError, fn -> Store.get_row!(row.id) end
    end

    test "change_row/1 returns a row changeset" do
      row = row_fixture()
      assert %Ecto.Changeset{} = Store.change_row(row)
    end
  end
end
