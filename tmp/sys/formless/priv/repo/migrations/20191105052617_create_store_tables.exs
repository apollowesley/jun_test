defmodule Formless.Repo.Migrations.CreateStoreTables do
  use Ecto.Migration

  def change do
    create table(:store_tables) do
      add :name, :string, null: false, comment: "表名"
      add :desc, :text, comment: "表描述"

      add :user_id, references(:users, on_delete: :delete_all), null: false

      timestamps()
    end

    create index(:store_tables, [:user_id])
  end
end
