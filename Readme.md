# fs2-elastic
This library offers an interface to stream results to / from ElasticSearch.

It provides a safe handling of the client and nice lightweight syntax to interact with the ElasticSearch high
level api. For this reason, the library supports only ElasticSearch 6.0+.

## Installation
Add the following to your `build.sbt`:

```
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "com.alessandromarrella" %% "fs2-elastic" % "0.0.1-SNAPSHOT"
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

```
import fs2._
import org.apache.http.HttpHost
import com.alessandromarrella.fs2_elastic.Client
import cats.effect.IO

val client = Client.fromHosts[IO](new HttpHost("localhost", 9200))

```

### Using the pipes
```
import fs2._
import com.alessandromarrella.fs2_elastic.io.all._
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest

val indexRequest = new IndexRequest("posts", "doc", "1").source("user", "amarrella", "message", "trying out Elasticsearch")

val indexResponse = client through index(request)

```

### Using the syntax
```
import fs2._
import com.alessandromarrella.fs2_elastic.syntax.all._
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest

val indexRequest = new IndexRequest("posts", "doc", "1").source("user", "amarrella", "message", "trying out Elasticsearch")

val indexResponse = client.index(request)

```

## Credits
This library has been inspired by https://github.com/fiadliel/fs2-mongodb
