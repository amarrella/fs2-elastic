# fs2-elastic

[![Latest version](https://index.scala-lang.org/amarrella/fs2-elastic/latest.svg)](https://index.scala-lang.org/amarrella/fs2-elastic)

# Looking for Maintainer
I'm not using ElasticSearch any longer and I no longer have time to maintain this repository. I'm looking for a reliable maintainer to transfer ownership to. 

This library offers an interface based on [fs2](https://github.com/functional-streams-for-scala/fs2)
to stream results to / from ElasticSearch.

It provides a safe handling of the client and nice lightweight syntax to interact with the [ElasticSearch high
level api](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.0/java-rest-high-supported-apis.html).

The library can be used in conjuntion with the [elastic4s dsl](https://github.com/sksamuel/elastic4s) to create queries.
(You need to add elastic4s as a dependency for that).

## Compatibility
The library is compatible with `fs2 1.0.x` and `scala 2.12.x`.

At the moment, the library supports only ElasticSearch 5.6.x and 6.x.x. I plan to support the previous 5.x.x version in the
near future (feel free to open a PR).

### Versioning
Versioning is done with the classic ES_MAJOR.ES_MINOR.LIBRARY_VERSION
You should use the same ES_MAJOR.ES_MINOR version as your ElasticSearch cluster and the latest LIBRARY_VERSION version available (this
doesn't track ElasticSearch patch versions and could contain breaking changes. In case of non-breaking changes we might append a .PATCH). The Master branch always tracks the latest version.

## Installation
Add the following to your `build.sbt`:

```scala
libraryDependencies += "com.alessandromarrella" %% "fs2-elastic" % "fs2ElasticVersion"
```

## Usage

For the client, use:
```import com.alessandromarrella.fs2_elastic.Client```

To import all the pipes, use:
```import com.alessandromarrella.fs2_elastic.io.all._```

To import the syntax, use:
```import com.alessandromarrella.fs2_elastic.io.syntax._```


## Example

### Creating the client

```scala
import fs2._
import org.apache.http.HttpHost
import com.alessandromarrella.fs2_elastic.Client
import cats.effect.IO

val client = Client.fromHosts[IO](new HttpHost("localhost", 9200))

```

### Using the pipes
```scala
import fs2._
import com.alessandromarrella.fs2_elastic.io.all._
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest

val indexRequest = new IndexRequest("posts", "doc", "1").source("user", "amarrella", "message", "trying out Elasticsearch")

val indexResponse = client through index(request)

```

### Using the syntax
```scala
import fs2._
import com.alessandromarrella.fs2_elastic.syntax.all._
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest

val indexRequest = new IndexRequest("posts", "doc", "1").source("user", "amarrella", "message", "trying out Elasticsearch")

val indexResponse = client.index(request)

```

## Credits
This library has been inspired by https://github.com/fiadliel/fs2-mongodb
