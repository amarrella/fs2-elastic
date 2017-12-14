package com.alessandromarrella.fs2_elastic.syntax

import cats.effect.Async
import fs2.Stream
import org.elasticsearch.action.search.{SearchRequest, SearchResponse, SearchScrollRequest}
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.{SearchHit, SearchHits}

import scala.concurrent.duration.Duration
import com.alessandromarrella.fs2_elastic.io
import org.apache.http.Header

private[syntax] trait search {

  type SearchResultMaybe[A] =
    Option[(A, (RestHighLevelClient, SearchScrollRequest, SearchResponse))]

  implicit class ElasticClientSearchOps[F[_]](
      val client: Stream[F, RestHighLevelClient]) {

    def search(searchRequest: SearchRequest, headers:Header*): Stream[F, SearchResponse] =
      client.through(io.search.search(searchRequest))

    def searchScroll(searchRequest: SearchRequest, duration: Duration, headers:Header*)(
        implicit F: Async[F])
      : Stream[F, (RestHighLevelClient, SearchResponse)] =
      client.through(io.search.searchScroll(searchRequest, duration, headers:_*))
  }

  implicit class SearchResponseOps[F[_]](
      val searchResponse: Stream[F, SearchResponse]) {
    def hits: Stream[F, SearchHits] =
      searchResponse.through(io.search.hits[F])
  }

  implicit class SearchScrollResponseOps[F[_]](
      val searchResponse: Stream[F, (RestHighLevelClient, SearchResponse)]) {
    def hitsScroll(implicit F: Async[F]): Stream[F, SearchHits] =
      searchResponse.through(io.search.hitsScroll[F])
  }

  implicit class SearchHitOps[F[_]](val hitsStream: Stream[F, SearchHits]) {
    def stream(implicit F: Async[F]): Stream[F, SearchHit] =
      streamFromJavaIterable(hitsStream)
  }

}

object search extends search
