package com.souo.biplatform.services.cube

import java.util.UUID

import com.souo.biplatform.model.{CubeSchema, Dimension, Measure}

/**
 * @author souo
 */
object RequestParams {

  case class Add(name: String, schema: Schema)

  case class Update(name: String, schema: Schema)

  case class Schema(
    tableId:      String,
    dimensions:   List[Dimension],
    measures:     List[Dimension],
    dataSourceId: UUID
  )

  implicit def schemaToCubeSchema(schema: Schema): CubeSchema = {
    CubeSchema(schema.tableId, schema.dimensions, schema.measures.map { dim ⇒
      Measure(dimension = dim)
    }, schema.dataSourceId)
  }
}
