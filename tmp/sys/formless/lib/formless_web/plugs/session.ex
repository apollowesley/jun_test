defmodule FormlessWeb.Plug.Session do
  import Plug.Conn

  alias Formless.Repo
  alias Formless.Account.User

  def init(opts), do: opts

  def call(conn, _opts) do
    {conn, user_id} = get_user_id(conn)
    current_user = user_id && Repo.get(User, user_id)

    assign(conn, :current_user, current_user)
  end

  defp get_user_id(conn) do
    case get_session(conn, :current_user_id) do
      nil -> get_user_id_from_cookie(conn)
      user_id -> {conn, user_id}
    end
  end

  defp get_user_id_from_cookie(conn) do
    with {:ok, token} <- Map.fetch(conn.cookies, "remember_token"),
         {:ok, user_id} <- Phoenix.Token.verify(conn, "current_user_id", token, max_age: 7 * 86400)
    do
      {put_session(conn, :current_user_id, user_id), user_id}
    else
      {:error, _} ->
        {delete_resp_cookie(conn, "remember_token"), nil}

      _ ->
        {conn, nil}
    end
  end
end
