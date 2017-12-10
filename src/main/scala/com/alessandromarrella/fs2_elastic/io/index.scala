package com.alessandromarrella.fs2_elastic.io

import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import fs2._
import org.elasticsearch.client.RestHighLevelClient

private[io] trait index {
  def index[F[_]](
      indexRequest: IndexRequest): Pipe[F, RestHighLevelClient, IndexResponse] =
    client => client.map(_.index(indexRequest))
}

object index extends index
