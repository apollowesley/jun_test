defmodule Formless.Repo do
  use Ecto.Repo,
    otp_app: :formless,
    adapter: Ecto.Adapters.Postgres
end
