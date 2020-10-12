defmodule FormlessWeb.UserController do
  use FormlessWeb, :controller

  alias Formless.Account
  alias Formless.Account.{RegisterForm, ResetPasswordForm}
  alias FormlessWeb.Plug.{RequireLogin, RequireLogout}

  plug RequireLogin when action in [:reset_password, :do_reset_password]
  plug RequireLogout when action in [:new, :create]


  def new(conn, _params) do
    changeset = RegisterForm.form_changeset(%{})

    conn
    |> put_layout("auth.html")
    |> render("new.html", changeset: changeset)
  end

  def create(conn, %{"register_form" => register_form_params}) do
    case Account.register_user(register_form_params) do
      {:ok, user} ->
        conn
        |> put_flash(:info, "注册成功")
        |> Account.login_user(user)
        |> redirect(to: Routes.table_path(conn, :index))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_layout("auth.html")
        |> render("new.html", changeset: changeset)
    end
  end

  def reset_password(conn, _params) do
    changeset = ResetPasswordForm.form_changeset(%{})

    render(conn, "reset_password.html", changeset: changeset)
  end

  def do_reset_password(conn, %{"reset_password_form" => form_params}) do
    case Account.reset_password(conn.assigns.current_user, form_params) do
      :ok ->
        conn
        |> Account.logout()
        |> redirect(to: Routes.session_path(conn, :new))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_flash(:error, "修改密码失败")
        |> render("reset_password.html", changeset: changeset)
    end
  end
end
