defmodule Formless.Pagination do
  @moduledoc """
  分页器
  """

  import Ecto.Query, warn: false

  alias Formless.Repo

  @page_size 10


  def paginate(query, params) do
    %{page: page, page_size: page_size} = get_params(params)
    offset_number = (page - 1) * page_size

    list =
      query
      |> offset(^offset_number)
      |> limit(^page_size)
      |> Repo.all()

    total = Repo.aggregate(query, :count, :id)

    total_page = ceil(total / page_size)

    %{list: list,
      page: page,
      page_size: page_size,
      total: total,
      total_page: total_page,
      has_prev: page <= total_page && page > 1,
      has_next: page < total_page}
  end

  defp get_params(params) do
    types = %{page: :integer, page_size: :integer}

    {%{}, types}
    |> Ecto.Changeset.cast(params, Map.keys(types))
    |> Ecto.Changeset.apply_changes()
    |> Map.update(:page, 1, &max(&1, 1))
    |> Map.update(:page_size, @page_size, &max(&1, 1))
  end
end
