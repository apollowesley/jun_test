defmodule Formless.Account.LoginForm do
  use Ecto.Schema

  import Ecto.Changeset

  embedded_schema do
    field :username, :string
    field :password, :string
    field :remember_me, :boolean, default: false
  end

  def form_changeset(attrs) do
    %__MODULE__{}
    |> cast(attrs, [:username, :password, :remember_me])
    |> validate_required([:username, :password])
  end

  def check(attrs) do
    changeset = form_changeset(attrs)

    if changeset.valid? do
      {:ok, changeset}
    else
      {:error, changeset}
    end
  end

  def remember_me?(attrs) do
    with {:ok, changeset} <- check(attrs),
         %{remember_me: remember_me} <- apply_changes(changeset)
    do
      remember_me
    else
      _ -> false
    end
  end
end
