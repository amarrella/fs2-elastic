package com.alessandromarrella.fs2_elastic.io

import fs2._
import org.apache.http.Header
import org.elasticsearch.action.get.{GetRequest, GetResponse}
import org.elasticsearch.client.RestHighLevelClient

private[io] trait get {
  def get[F[_]](
      getRequest: GetRequest,
      headers: Header*): Pipe[F, RestHighLevelClient, GetResponse] =
    client => client.map(_.get(getRequest, headers:_*))
}

object get extends get
