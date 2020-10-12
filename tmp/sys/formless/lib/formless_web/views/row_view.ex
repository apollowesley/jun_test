defmodule FormlessWeb.RowView do
  use FormlessWeb, :view

  alias Formless.Store.Column


  def column_label(form, column) do
    ~E"""
    <div class="d-flex align-items-center mb-2">
      <%= content_tag :label, column.name, for: input_id(form, :data, column.field_name), class: "mb-0 mr-2" %>
      <span class="badge badge-info"><%= column.data_type %></span>
    </div>
    """
  end

  def column_input(form, column) do
    form_id = input_id(form, :data, column.field_name)
    form_name = input_name(form, :data) <> "[#{column.field_name}]"
    form_value =
      case input_value(form, :data) do
        nil -> nil
        data -> Map.get(data, column.field_name, Column.default_value(column.data_type))
      end

    render_column_input(column.data_type, form_id, form_name, form_value)
  end

  defp render_column_input("number", id, name, value) do
    tag :input,
        class: "form-control",
        id: id,
        name: name,
        value: value,
        type: "text"
  end
  defp render_column_input("string", id, name, value) do
    tag :input,
        class: "form-control",
        id: id,
        name: name,
        value: value,
        type: "text"
  end
  defp render_column_input("boolean", id, name, value) do
    ~E"""
    <div class="custom-control custom-switch">
      <%= tag :input, name: name, type: "hidden", value: "false" %>
      <%= tag :input,
          class: "custom-control-input",
          id: id,
          name: name,
          type: "checkbox",
          checked: value,
          value: "true" %>
      <%= content_tag :label, "", class: "custom-control-label", for: id %>
    </div>
    """
  end
end
