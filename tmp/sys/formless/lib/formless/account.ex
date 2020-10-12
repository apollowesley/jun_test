defmodule Formless.Account do
  @moduledoc """
  账号系统
  """

  import Ecto.Query, warn: false

  alias Formless.Repo
  alias Formless.Account.{User, RegisterForm, LoginForm, ResetPasswordForm}


  def get_user!(id), do: Repo.get!(User, id)


  @doc """
  注册用户
  """
  def register_user(attrs \\ %{}) do
    with {:ok, changeset} <- RegisterForm.check(attrs),
         {:ok, user} <- insert_user(changeset)
    do
      {:ok, user}
    else
      {:error, %Ecto.Changeset{} = changeset} ->
        {:error, %{changeset | action: :register}}
    end
  end

  defp insert_user(form_changeset) do
    register_form = Ecto.Changeset.apply_changes(form_changeset)

    %User{}
    |> User.changeset(%{username: register_form.username, password_hash: Bcrypt.hash_pwd_salt(register_form.password)})
    |> Repo.insert()
    |> case do
      {:ok, user} -> {:ok, user}

      {:error, user_changeset} ->
        {:error, transform_error(user_changeset, form_changeset, [:username])}
    end
  end

  defp transform_error(from_changeset, to_changeset, fields) do
    Enum.reduce(fields, to_changeset, fn field, changeset ->
      error = Keyword.get(from_changeset.errors, field)

      if error do
        {msg, opts} = error
        Ecto.Changeset.add_error(changeset, field, msg, opts)
      else
        changeset
      end
    end)
  end


  @doc """
  重置密码
  """
  def reset_password(%User{} = user, attrs \\ %{}) do
    with {:ok, changeset} <- ResetPasswordForm.check(attrs) do
      update_password(user, changeset)
    else
      {:error, %Ecto.Changeset{} = changeset} ->
        {:error, %{changeset | action: :reset_password}}
    end
  end

  defp update_password(user, form_changeset) do
    reset_passowrd_form = Ecto.Changeset.apply_changes(form_changeset)

    user
    |> User.changeset(%{password_hash: Bcrypt.hash_pwd_salt(reset_passowrd_form.password)})
    |> Repo.update!()

    :ok
  end


  @doc """
  验证用户
  """
  def authorize(attrs \\ %{}) do
    with {:ok, changeset} <- LoginForm.check(attrs),
         {:ok, user} <- authorize_find_user(changeset),
         :ok <- authorize_verify_password(changeset, user)
    do
      {:ok, user}
    else
      {:error, %Ecto.Changeset{} = changeset} ->
        {:error, %{changeset | action: :login}}
    end
  end

  defp authorize_find_user(changeset) do
    login_form = Ecto.Changeset.apply_changes(changeset)
    query = from u in User, where: u.username == ^login_form.username

    case Repo.one(query) do
      nil ->
        Bcrypt.no_user_verify()
        {:error, Ecto.Changeset.add_error(changeset, :username, "用户不存在")}
      user ->
        {:ok, user}
    end
  end

  defp authorize_verify_password(changeset, user) do
    login_form = Ecto.Changeset.apply_changes(changeset)

    if Bcrypt.verify_pass(login_form.password, user.password_hash) do
      :ok
    else
      {:error, Ecto.Changeset.add_error(changeset, :password, "密码错误")}
    end
  end


  @doc """
  登录用户
  """
  def login_user(%Plug.Conn{} = conn, %User{} = user, remember_me \\ false) do
    conn =
      conn
      |> Plug.Conn.configure_session(renew: true)
      |> Plug.Conn.put_session(:current_user_id, user.id)

    if remember_me do
      Plug.Conn.put_resp_cookie(conn, "remember_token", Phoenix.Token.sign(conn, "current_user_id", user.id), max_age: 7 * 86400)
    else
      conn
    end
  end


  @doc """
  注销当前账号
  """
  def logout(%Plug.Conn{} = conn) do
    conn
    |> Plug.Conn.configure_session(drop: true)
    |> Plug.Conn.delete_resp_cookie("remember_token")
  end
end
