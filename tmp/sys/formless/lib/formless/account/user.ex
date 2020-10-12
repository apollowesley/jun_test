defmodule Formless.Account.User do
  use Ecto.Schema

  import Ecto.Changeset

  alias Formless.Store.Table

  schema "users" do
    field :username, :string
    field :password_hash, :string

    has_many :tables, Table

    timestamps()
  end

  @doc false
  def changeset(user, attrs) do
    user
    |> cast(attrs, [:username, :password_hash])
    |> validate_required([:username, :password_hash])
    |> validate_length(:username, min: 3, max: 32)
    |> validate_format(:username, ~r/^[A-Za-z0-9]+$/)
    |> unique_constraint(:username)
  end
end
