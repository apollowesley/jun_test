defmodule Formless.Store do
  @moduledoc """
  数据存储系统
  """

  import Ecto.Query, warn: false

  alias Formless.{Repo, Pagination}
  alias Formless.Account.User
  alias Formless.Store.{Table, Column, Row}


  # ================ Table ================

  def list_tables(%User{} = user, params) do
    user
    |> Ecto.assoc(:tables)
    |> order_by(desc: :inserted_at)
    |> Pagination.paginate(params)
  end

  def get_table!(id) do
    Table
    |> Repo.get!(id)
    |> Repo.preload(:user)
  end

  def create_table(%User{} = user, attrs \\ %{}) do
    user
    |> Ecto.build_assoc(:tables)
    |> Table.changeset(attrs)
    |> Repo.insert()
  end

  def update_table(%Table{} = table, attrs) do
    table
    |> Table.changeset(attrs)
    |> Repo.update()
  end

  def delete_table(%Table{} = table) do
    Repo.delete(table)
  end

  def change_table(%Table{} = table) do
    Table.changeset(table, %{})
  end


  # ================ Column ================

  def list_columns(%Table{} = table) do
    table
    |> Ecto.assoc(:columns)
    |> order_by(asc: :sort)
    |> Repo.all()
  end

  def get_column!(id), do: Repo.get!(Column, id)

  def create_column(%Table{} = table, attrs \\ %{}) do
    table
    |> Ecto.build_assoc(:columns)
    |> Column.changeset(attrs)
    |> Repo.insert()
  end

  def update_column(%Column{} = column, attrs) do
    column
    |> Column.changeset(attrs)
    |> Repo.update()
  end

  def delete_column(%Column{} = column) do
    Repo.delete(column)
  end

  def change_column(%Column{} = column) do
    Column.changeset(column, %{})
  end

  def reorder_columns(ids) do
    Enum.with_index(ids, 1)
    |> Enum.each(fn {id, idx} ->
      query = from c in Column, where: c.id == ^id

      Repo.update_all(query, [set: [sort: idx]])
    end)
  end


  # ================ Row ================

  def list_rows(%Table{} = table, params) do
    table
    |> Ecto.assoc(:rows)
    |> order_by(desc: :inserted_at)
    |> Pagination.paginate(params)
  end

  def get_row!(id), do: Repo.get!(Row, id)

  def create_row(%Table{} = table, attrs \\ %{}) do
    columns = list_columns(table)

    table
    |> Ecto.build_assoc(:rows)
    |> Row.changeset(attrs)
    |> Row.cast_data(columns)
    |> Repo.insert()
  end

  def update_row(%Row{} = row, attrs, columns) do
    row
    |> Row.changeset(attrs)
    |> Row.cast_data(columns)
    |> Repo.update()
  end

  def delete_row(%Row{} = row) do
    Repo.delete(row)
  end

  def change_row(%Row{} = row) do
    Row.changeset(row, %{})
  end

  def export_rows(%Table{} = table) do
    table
    |> Ecto.assoc(:rows)
    |> order_by(desc: :inserted_at)
    |> Repo.all()
  end
end
