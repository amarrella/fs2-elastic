package com.alessandromarrella.fs2_elastic.syntax

import cats.effect.Async
import fs2.Stream
import org.elasticsearch.action.bulk.{BulkItemResponse, BulkRequest, BulkResponse}
import org.elasticsearch.client.RestHighLevelClient

trait bulk {
  implicit class ElasticClientBulkOps[F[_]](val client:Stream[F, RestHighLevelClient]) {
    def bulk(bulkRequest: BulkRequest): Stream[F, BulkResponse] =
      client.map(_.bulk(bulkRequest))
  }
  implicit class BulkOps[F[_]](val bulkResponseStream:Stream[F, BulkResponse]) {
    def stream(implicit F: Async[F]): Stream[F, BulkItemResponse] = streamFromJavaIterable(bulkResponseStream)
  }

}

object bulk extends bulk

