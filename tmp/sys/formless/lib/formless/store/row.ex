defmodule Formless.Store.Row do
  use Ecto.Schema

  import Ecto.Changeset

  alias Formless.Store.{Table, Column}

  schema "store_rows" do
    field :data, :map

    belongs_to :table, Table

    timestamps()
  end

  @doc false
  def changeset(row, attrs) do
    row
    |> cast(attrs, [:data])
    |> validate_required([:data])
  end

  def cast_data(%Ecto.Changeset{valid?: false} = changeset, _columns), do: changeset
  def cast_data(%Ecto.Changeset{} = changeset, columns) do
    case get_change(changeset, :data) do
      nil -> changeset

      changes ->
        data =
          Enum.reduce(columns, %{}, fn column, acc ->
            key = column.field_name
            value = Column.convert_type(column.data_type, Map.get(changes, key))

            Map.put(acc, key, value)
          end)

        put_change(changeset, :data, data)
    end
  end
end
