package com.alessandromarrella.fs2_elastic.io

import cats.effect.Async
import fs2.{Pipe, Stream}
import org.elasticsearch.action.search.{
  SearchRequest,
  SearchResponse,
  SearchScrollRequest
}
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.SearchHits
import scala.collection.JavaConverters._

import scala.concurrent.duration.Duration

private[io] trait search {

  type SearchResultMaybe[A] = Option[(A, SearchResponse)]

  def search[F[_]](searchRequest: SearchRequest)
    : Pipe[F, RestHighLevelClient, SearchResponse] =
    client => client.map(_.search(searchRequest))

  def searchScroll[F[_]](searchRequest: SearchRequest, duration: Duration)(
      implicit F: Async[F])
    : Pipe[F, RestHighLevelClient, (RestHighLevelClient, SearchResponse)] =
    client =>
      client.map(c =>
        (c, c.search(searchRequest.scroll(durationToTimeValue(duration)))))

  def hits[F[_]]: Pipe[F, SearchResponse, SearchHits] =
    response => response.map(_.getHits)

  def hitsScroll[F[_]](implicit F: Async[F])
    : Pipe[F, (RestHighLevelClient, SearchResponse), SearchHits] =
    input =>
      input.flatMap {
        case (client, response) =>
          Stream.unfoldEval(response) { res =>
            F.delay[SearchResultMaybe[SearchHits]](
              Option[SearchHits](res.getHits).flatMap {
                case hits if hits.asScala.nonEmpty =>
                  val newRequest = new SearchScrollRequest(res.getScrollId)
                  val newHits = client.searchScroll(newRequest)
                  Some((hits, newHits))
                case _ =>
                  None
              })
          }
    }

}

object search extends search
