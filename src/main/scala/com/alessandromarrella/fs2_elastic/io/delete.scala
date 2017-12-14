package com.alessandromarrella.fs2_elastic.io
import org.elasticsearch.action.delete.{DeleteRequest, DeleteResponse}
import org.elasticsearch.client.RestHighLevelClient
import fs2.Pipe

private[io] trait delete {
  def delete[F[_]](deleteRequest: DeleteRequest)
    : Pipe[F, RestHighLevelClient, DeleteResponse] =
    client => client.map(_.delete(deleteRequest))
}

object delete extends delete
