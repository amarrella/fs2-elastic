package com.alessandromarrella.fs2_elastic
import org.elasticsearch.client.{
  RestClient,
  RestClientBuilder,
  RestHighLevelClient
}
import cats.effect.{Sync, Resource}
import org.apache.http.HttpHost

object Client {

  def fromHosts[F[_]](hosts: HttpHost*)(
      implicit F: Sync[F]): Resource[F, RestHighLevelClient] =
    Resource.make(
      F.delay(new RestHighLevelClient(RestClient.builder(hosts: _*))))(
      c => F.delay(c.close())
    )

  def fromClientBuilder[F[_]](restClientBuilder: RestClientBuilder)(
      implicit F: Sync[F]): Resource[F, RestHighLevelClient] =
    Resource.make(F.delay(new RestHighLevelClient(restClientBuilder)))(
      c => F.delay(c.close())
    )

}
