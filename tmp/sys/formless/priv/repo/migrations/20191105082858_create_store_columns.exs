defmodule Formless.Repo.Migrations.CreateStoreColumns do
  use Ecto.Migration

  def change do
    create table(:store_columns) do
      add :name, :string, null: false, comment: "列名"
      add :desc, :text, comment: "列描述"
      add :field_name, :string, null: false, comment: "字段名"
      add :data_type, :string, null: false, comment: "数据类型"
      add :sort, :integer, null: false, comment: "顺序"

      add :table_id, references(:store_tables, on_delete: :delete_all), null: false

      timestamps()
    end

    create unique_index(:store_columns, [:field_name, :table_id], name: :store_columns_field_name_index)
    create index(:store_columns, [:table_id])
    create index(:store_columns, [:sort])
  end
end
