package com.alessandromarrella.fs2_elastic.io

import fs2._
import org.elasticsearch.action.get.{GetRequest, GetResponse}
import org.elasticsearch.client.RestHighLevelClient

private[io] trait get {
  def get[F[_]](
      getRequest: GetRequest): Pipe[F, RestHighLevelClient, GetResponse] =
    client => client.map(_.get(getRequest))
}

object get extends get
