package com.alessandromarrella.fs2_elastic.syntax
import fs2.Stream
import org.elasticsearch.action.get.{GetRequest, GetResponse}
import org.elasticsearch.client.RestHighLevelClient

private[syntax] trait get {
  implicit class ElasticClientGetOps[F[_]](val client:Stream[F, RestHighLevelClient]) {
    def get(getRequest: GetRequest): Stream[F, GetResponse] =
      client.map(_.get(getRequest))
  }
}

object get extends get