package com.alessandromarrella.fs2_elastic.io

import fs2.Pipe
import org.elasticsearch.action.update.{UpdateRequest, UpdateResponse}
import org.elasticsearch.client.RestHighLevelClient

private[io] trait update {

  def update[F[_]](updateRequest: UpdateRequest)
    : Pipe[F, RestHighLevelClient, UpdateResponse] =
    client => client.map(_.update(updateRequest))

}

object update extends update
