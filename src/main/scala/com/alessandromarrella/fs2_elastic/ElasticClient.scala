package com.alessandromarrella.fs2_elastic
import org.elasticsearch.client.{
  RestClient,
  RestClientBuilder,
  RestHighLevelClient
}
import fs2._
import cats.effect.Sync
import org.apache.http.HttpHost

object Client {

  def fromHosts[F[_]](hosts: HttpHost*)(
      implicit F: Sync[F]): Stream[F, RestHighLevelClient] =
    Stream.bracket(
      F.delay(new RestHighLevelClient(RestClient.builder(hosts: _*))))(
      c => F.delay(c.close())
    )

  def fromClientBuilder[F[_]](restClientBuilder: RestClientBuilder)(
      implicit F: Sync[F]): Stream[F, RestHighLevelClient] =
    Stream.bracket(F.delay(new RestHighLevelClient(restClientBuilder)))(
      c => F.delay(c.close())
    )
}
