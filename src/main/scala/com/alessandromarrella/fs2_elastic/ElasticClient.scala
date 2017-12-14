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

  private[this] def buildClient(hosts: Seq[HttpHost]): (RestHighLevelClient, RestClient) = {
    val restClient = RestClient.builder(hosts: _*).build()
    val restHighLevelClient = new RestHighLevelClient(restClient)
    (restHighLevelClient, restClient)
  }

  private[this] def buildClient(restClientBuilder: RestClientBuilder): (RestHighLevelClient, RestClient) = {
    val restClient = restClientBuilder.build()
    val restHighLevelClient = new RestHighLevelClient(restClient)
    (restHighLevelClient, restClient)
  }

  def fromHosts[F[_]](hosts: HttpHost*)(
      implicit F: Sync[F]): Stream[F, RestHighLevelClient] =
    fromClientBuilder(RestClient.builder(hosts: _*))

  def fromClientBuilder[F[_]](restClientBuilder: RestClientBuilder)(
      implicit F: Sync[F]): Stream[F, RestHighLevelClient] =
    Stream.bracket(F.delay(buildClient(restClientBuilder)))(
      c => Stream.emit(c._1),    // We use the high level client
      c => F.delay(c._2.close()) // But we need to close the underlying low level client when we are done
    )

}