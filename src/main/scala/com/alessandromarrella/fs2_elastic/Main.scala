package com.alessandromarrella.fs2_elastic
import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import cats.effect.IO
import org.elasticsearch.action.search.SearchRequest
import fs2._
import syntax.all._
import scala.concurrent.duration._

object Main extends App {
  val client = Client.fromHosts[IO](new HttpHost("localhost", 9200))

  client.index(new IndexRequest("posts", "doc", "1").source("user", "amarrella", "message", "trying out Elasticsearch"))

  client.searchScroll(new SearchRequest(), 10.seconds).hits.stream.evalMap(h => IO(println(h))).run.unsafeRunSync()


}
