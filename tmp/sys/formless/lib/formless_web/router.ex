defmodule FormlessWeb.Router do
  use FormlessWeb, :router

  pipeline :browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_flash
    plug :protect_from_forgery
    plug :put_secure_browser_headers
    plug FormlessWeb.Plug.Session
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", FormlessWeb do
    pipe_through :browser

    get "/", PageController, :index

    get "/register", UserController, :new
    post "/register", UserController, :create

    get "/reset_password", UserController, :reset_password
    post "/reset_password", UserController, :do_reset_password

    get "/login", SessionController, :new
    post "/login", SessionController, :create
    delete "/logout", SessionController, :delete

    resources "/tables", TableController do
      resources "/columns", ColumnController, only: [:new, :create, :edit, :update, :delete]
      get "/columns/reorder", ColumnController, :reorder
      post "/columns/reorder", ColumnController, :do_reorder

      resources "/rows", RowController, only: [:new, :create, :edit, :update, :delete]
      get "/rows/export_json", RowController, :export_json
    end
  end

  # Other scopes may use custom stacks.
  # scope "/api", FormlessWeb do
  #   pipe_through :api
  # end
end
