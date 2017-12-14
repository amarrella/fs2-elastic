package com.alessandromarrella.fs2_elastic.io
import org.elasticsearch.action.delete.{DeleteRequest, DeleteResponse}
import org.elasticsearch.client.RestHighLevelClient
import fs2.Pipe
import org.apache.http.Header

private[io] trait delete {
  def delete[F[_]](deleteRequest: DeleteRequest, headers: Header*)
    : Pipe[F, RestHighLevelClient, DeleteResponse] =
    client => client.map(_.delete(deleteRequest, headers:_*))
}

object delete extends delete
