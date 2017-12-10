package com.alessandromarrella.fs2_elastic.syntax
import org.elasticsearch.action.delete.{DeleteRequest, DeleteResponse}
import org.elasticsearch.client.RestHighLevelClient
import fs2.Stream
import com.alessandromarrella.fs2_elastic.io

private[syntax] trait delete {
  implicit class ElasticClientDeleteOps[F[_]](
      val client: Stream[F, RestHighLevelClient]) {
    def delete(deleteRequest: DeleteRequest): Stream[F, DeleteResponse] =
      client.through(io.delete.delete(deleteRequest))
  }
}

object delete extends delete
