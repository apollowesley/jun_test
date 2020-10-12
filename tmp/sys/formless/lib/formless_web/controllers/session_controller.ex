defmodule FormlessWeb.SessionController do
  use FormlessWeb, :controller

  alias Formless.Account
  alias Formless.Account.LoginForm
  alias FormlessWeb.Plug.{RequireLogin, RequireLogout}

  plug RequireLogin when action in [:delete]
  plug RequireLogout when action in [:new, :create]


  def new(conn, _params) do
    changeset = LoginForm.form_changeset(%{})

    conn
    |> put_layout("auth.html")
    |> render("new.html", changeset: changeset)
  end

  def create(conn, %{"login_form" => login_form_params}) do
    case Account.authorize(login_form_params) do
      {:ok, user} ->
        conn
        |> Account.login_user(user, LoginForm.remember_me?(login_form_params))
        |> put_flash(:info, "登录成功")
        |> redirect(to: Routes.table_path(conn, :index))

      {:error, %Ecto.Changeset{} = changeset} ->
        conn
        |> put_layout("auth.html")
        |> render("new.html", changeset: changeset)
    end
  end

  def delete(conn, _params) do
    conn
    |> Account.logout()
    |> redirect(to: Routes.page_path(conn, :index))
  end
end
