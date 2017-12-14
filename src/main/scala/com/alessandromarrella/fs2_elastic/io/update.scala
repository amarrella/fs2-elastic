package com.alessandromarrella.fs2_elastic.io

import fs2.Pipe
import org.apache.http.Header
import org.elasticsearch.action.update.{UpdateRequest, UpdateResponse}
import org.elasticsearch.client.RestHighLevelClient

private[io] trait update {

  def update[F[_]](updateRequest: UpdateRequest, headers: Header*)
    : Pipe[F, RestHighLevelClient, UpdateResponse] =
    client => client.map(_.update(updateRequest, headers:_*))

}

object update extends update
