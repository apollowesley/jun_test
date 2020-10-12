defmodule Formless.Store.Column do
  use Ecto.Schema

  import Ecto.Changeset

  alias Formless.Store.Table

  @data_types ["number", "string", "boolean"]

  schema "store_columns" do
    field :name, :string
    field :desc, :string
    field :field_name, :string
    field :data_type, :string
    field :sort, :integer, default: 0

    belongs_to :table, Table

    timestamps()
  end

  @doc false
  def changeset(column, attrs) do
    column
    |> cast(attrs, [:name, :desc, :field_name, :data_type])
    |> validate_required([:name, :field_name, :data_type])
    |> validate_length(:name, max: 64)
    |> unique_constraint(:field_name)
    |> validate_inclusion(:data_type, @data_types)
  end

  def support_data_types, do: @data_types

  def default_value("number"), do: 0
  def default_value("string"), do: ""
  def default_value("boolean"), do: false

  def convert_type("number", data) when is_binary(data) do
    case Float.parse(data) do
      {float_number, _} ->
        integer_number = trunc(float_number)
        if float_number == integer_number, do: integer_number, else: float_number

      _ ->
        default_value("number")
    end
  end
  def convert_type("number", data) when is_number(data), do: data
  def convert_type("number", _data), do: default_value("number")

  def convert_type("string", data) when is_binary(data), do: data
  def convert_type("string", _data), do: default_value("string")

  def convert_type("boolean", "true"), do: true
  def convert_type("boolean", "false"), do: false
  def convert_type("boolean", data) when is_boolean(data), do: data
  def convert_type("boolean", _data), do: default_value("boolean")
end
