package com.alessandromarrella.fs2_elastic.syntax
import org.elasticsearch.action.delete.{DeleteRequest, DeleteResponse}
import org.elasticsearch.client.RestHighLevelClient
import fs2.Stream
import com.alessandromarrella.fs2_elastic.io
import org.apache.http.Header

private[syntax] trait delete {
  implicit class ElasticClientDeleteOps[F[_]](
      val client: Stream[F, RestHighLevelClient]) {
    def delete(deleteRequest: DeleteRequest, headers: Header*): Stream[F, DeleteResponse] =
      client.through(io.delete.delete(deleteRequest, headers:_*))
  }
}

object delete extends delete
