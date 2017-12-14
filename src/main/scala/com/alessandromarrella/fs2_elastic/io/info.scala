package com.alessandromarrella.fs2_elastic.io
import fs2.Pipe
import org.apache.http.Header
import org.elasticsearch.action.main.MainResponse
import org.elasticsearch.client.RestHighLevelClient

private[io] trait info {
  def info[F[_]](headers: Header*)
  : Pipe[F, RestHighLevelClient, MainResponse] =
    client => client.map(_.info(headers:_*))
}

object info extends info
