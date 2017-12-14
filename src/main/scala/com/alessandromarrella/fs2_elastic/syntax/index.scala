package com.alessandromarrella.fs2_elastic.syntax

import fs2.Stream
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.client.RestHighLevelClient
import com.alessandromarrella.fs2_elastic.io
import org.apache.http.Header

private[syntax] trait index {
  implicit class ElasticClientIndexOps[F[_]](
      val client: Stream[F, RestHighLevelClient]) {
    def index(indexRequest: IndexRequest, headers: Header*): Stream[F, IndexResponse] =
      client.through(io.index.index(indexRequest, headers:_*))
  }
}

object index extends index
