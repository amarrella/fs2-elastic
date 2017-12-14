package com.alessandromarrella.fs2_elastic.io

import fs2.Pipe
import org.elasticsearch.action.bulk.{
  BulkRequest,
  BulkResponse
}
import org.elasticsearch.client.RestHighLevelClient

private[io] trait bulk {

  def bulk[F[_]](
      bulkRequest: BulkRequest): Pipe[F, RestHighLevelClient, BulkResponse] =
    client => client.map(_.bulk(bulkRequest))

}

object bulk extends bulk
