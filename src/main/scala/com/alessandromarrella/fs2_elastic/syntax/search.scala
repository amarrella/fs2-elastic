package com.alessandromarrella.fs2_elastic.syntax

import cats.effect.Async
import fs2.Stream
import org.elasticsearch.action.search.{ClearScrollRequest, SearchRequest, SearchResponse, SearchScrollRequest}
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.{SearchHit, SearchHits}

import scala.concurrent.duration.Duration
import scala.collection.JavaConverters._


trait search {

  type SearchResultMaybe[A] = Option[(A, (RestHighLevelClient, SearchScrollRequest, SearchResponse))]

  implicit class ElasticClientSearchOps[F[_]](val client:Stream[F, RestHighLevelClient]) {

    def search(searchRequest: SearchRequest): Stream[F, SearchResponse] =
      client.map(_.search(searchRequest))

    def searchScroll(searchRequest: SearchRequest, duration: Duration)(implicit F:Async[F]): Stream[F, (RestHighLevelClient, SearchScrollRequest, SearchResponse)] =
      client.map(c => (c, new SearchScrollRequest(), c.search(searchRequest.scroll(durationToTimeValue(duration)))))
  }

  implicit class SearchResponseOps[F[_]](val searchResponse:Stream[F, (RestHighLevelClient, SearchScrollRequest, SearchResponse)]) {
    def hits(implicit F:Async[F]): Stream[F, SearchHits] =
      searchResponse.flatMap{
        case (client, request, response) =>
          Stream.unfoldEval((client, request, response)) {
            case (c, req, res) =>
              F.delay[SearchResultMaybe[SearchHits]](Option[SearchHits](res.getHits).flatMap {
                case hits if hits.asScala.nonEmpty =>
                  val newRequest = new SearchScrollRequest(res.getScrollId)
                  val newHits = client.searchScroll(newRequest)
                  Some((hits, (client, newRequest, newHits)))
                case _ =>
                  None
              })
          }
      }
  }

  implicit class SearchHitOps[F[_]](val hitsStream:Stream[F, java.lang.Iterable[SearchHit]]) {
    def stream(implicit F: Async[F]): Stream[F, SearchHit] = streamFromJavaIterable(hitsStream)
  }

}

object search extends search