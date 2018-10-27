package com.alessandromarrella.fs2_elastic
import org.elasticsearch.client.{
  RestClient,
  RestClientBuilder,
  RestHighLevelClient
}
import cats.effect._
import fs2._
import org.apache.http.HttpHost

object Client {

  def fromHosts[F[_]](hosts: HttpHost*)(
      implicit F: Sync[F]): Stream[F, RestHighLevelClient] =
    fromClientBuilder(RestClient.builder(hosts: _*))

  def fromClientBuilder[F[_]](restClientBuilder: RestClientBuilder)(
      implicit F: Sync[F]): Stream[F, RestHighLevelClient] =
    Stream.bracket(F.delay(restClientBuilder.build()))(c => F.delay(c.close())).flatMap(
      restClient =>
        Stream.eval(F.delay(new RestHighLevelClient(restClient)))
    )
}