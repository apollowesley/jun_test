
  #去重复留一条数据
   delete from estate_base where id not in
    (
        select id from
        (
            select Max(id) id from estate_base group by estate_base.city_id,estate_base.province_id,estate_base.`name`
        ) as ids
    ) ;