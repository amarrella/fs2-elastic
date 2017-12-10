package com.alessandromarrella.fs2_elastic.syntax

import fs2.Stream
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.client.RestHighLevelClient

trait index {
  implicit class ElasticClientIndexOps[F[_]](val client:Stream[F, RestHighLevelClient]) {
    def index(indexRequest: IndexRequest): Stream[F, IndexResponse] =
      client.map(_.index(indexRequest))
  }
}

object index extends index