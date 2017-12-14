package com.alessandromarrella.fs2_elastic.syntax

import com.alessandromarrella.fs2_elastic.io
import fs2.Stream
import org.apache.http.Header
import org.elasticsearch.action.main.MainResponse
import org.elasticsearch.client.RestHighLevelClient

private[syntax] trait info {
  implicit class ElasticClientIndexOps[F[_]](val client: Stream[F, RestHighLevelClient]) {
    def info(headers: Header*): Stream[F, MainResponse] =
      client.through(io.info.info(headers:_*))
  }
}

object info extends info