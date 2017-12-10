package com.alessandromarrella.fs2_elastic
import org.elasticsearch.client.{RestClient, RestClientBuilder, RestHighLevelClient}
import fs2._
import cats.effect.Sync
import org.apache.http.HttpHost

object Client {

  def fromHosts[F[_]](hosts:HttpHost*)(implicit F: Sync[F]): Stream[F, RestHighLevelClient] =
    Stream.bracket(F.delay(new RestHighLevelClient(RestClient.builder(hosts:_*)))) (
      c => Stream.emit(c),
      c => F.delay(c.close())
    )

  def fromClientBuilder[F[_]](restClientBuilder: RestClientBuilder)(implicit F: Sync[F]): Stream[F, RestHighLevelClient] =
    Stream.bracket(F.delay(new RestHighLevelClient(restClientBuilder))) (
      c => Stream.emit(c),
      c => F.delay(c.close())
    )

}

object LowLevelClient {

  def fromHosts[F[_]](hosts:HttpHost*)(implicit F: Sync[F]): Stream[F, RestClient] =
    Stream.bracket(F.delay(RestClient.builder(hosts:_*).build())) (
      c => Stream.emit(c),
      c => F.delay(c.close())
    )

  def fromClientBuilder[F[_]](restClientBuilder: RestClientBuilder)(implicit F: Sync[F]): Stream[F, RestClient] =
    Stream.bracket(F.delay(restClientBuilder.build())) (
      c => Stream.emit(c),
      c => F.delay(c.close())
    )

}