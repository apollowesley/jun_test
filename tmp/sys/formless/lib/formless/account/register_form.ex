defmodule Formless.Account.RegisterForm do
  use Ecto.Schema

  import Ecto.Changeset

  embedded_schema do
    field :username, :string
    field :password, :string
  end

  def form_changeset(attrs) do
    %__MODULE__{}
    |> cast(attrs, [:username, :password])
    |> validate_required([:username, :password])
    |> validate_length(:password, min: 6, max: 128)
    |> validate_format(:password, ~r/^[A-Za-z0-9]+$/)
    |> validate_confirmation(:password)
  end

  def check(attrs) do
    changeset = form_changeset(attrs)

    if changeset.valid? do
      {:ok, changeset}
    else
      {:error, changeset}
    end
  end
end
