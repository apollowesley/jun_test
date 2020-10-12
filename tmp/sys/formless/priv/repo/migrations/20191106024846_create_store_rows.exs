defmodule Formless.Repo.Migrations.CreateStoreRows do
  use Ecto.Migration

  def change do
    create table(:store_rows) do
      add :data, :map, null: false, comment: "数据"

      add :table_id, references(:store_tables, on_delete: :delete_all), null: false

      timestamps()
    end

    create index(:store_rows, [:table_id])
  end
end
