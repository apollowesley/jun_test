defmodule Formless.Store.Table do
  use Ecto.Schema

  import Ecto.Changeset

  alias Formless.Account.User
  alias Formless.Store.{Column, Row}

  schema "store_tables" do
    field :name, :string
    field :desc, :string

    belongs_to :user, User

    has_many :columns, Column
    has_many :rows, Row

    timestamps()
  end

  @doc false
  def changeset(table, attrs) do
    table
    |> cast(attrs, [:name, :desc])
    |> validate_required([:name])
    |> validate_length(:name, max: 64)
  end
end
