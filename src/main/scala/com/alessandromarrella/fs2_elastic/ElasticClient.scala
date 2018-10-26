package com.alessandromarrella.fs2_elastic
import org.elasticsearch.client.{
  RestClient,
  RestClientBuilder,
  RestHighLevelClient
}
import cats.effect._
import org.apache.http.HttpHost

object Client {

  def fromHosts[F[_]](hosts: HttpHost*)(
      implicit F: Sync[F]): Resource[F, RestHighLevelClient] =
    fromClientBuilder(RestClient.builder(hosts: _*))

  def fromClientBuilder[F[_]](restClientBuilder: RestClientBuilder)(
      implicit F: Sync[F]): Resource[F, RestHighLevelClient] =
    Resource.make(F.delay(restClientBuilder.build()))(c => F.delay(c.close())).flatMap(
      restClient =>
        Resource.liftF(F.delay(new RestHighLevelClient(restClient)))
    )
}