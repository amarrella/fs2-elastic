package com.alessandromarrella.fs2_elastic.syntax
import fs2.Stream
import org.elasticsearch.action.update.{UpdateRequest, UpdateResponse}
import org.elasticsearch.client.RestHighLevelClient
import com.alessandromarrella.fs2_elastic.io

private[syntax] trait update {
  implicit class ElasticClientUpdateOps[F[_]](
      val client: Stream[F, RestHighLevelClient]) {
    def update(updateRequest: UpdateRequest): Stream[F, UpdateResponse] =
      client.through(io.update.update(updateRequest))
  }
}

object update extends update
