package com.alessandromarrella.fs2_elastic

import cats.effect.Async
import fs2.Stream

import org.elasticsearch.common.unit.TimeValue

import scala.concurrent.duration.Duration
import scala.collection.JavaConverters._

package object syntax {

  private[syntax] type IteratorResultMaybe[A] = Option[(A, Iterator[A])]

  private[syntax] def streamFromJavaIterable[F[_], A](
      inputStream: Stream[F, java.lang.Iterable[A]])(
      implicit F: Async[F]): Stream[F, A] =
    streamFromIterable(inputStream.map(_.asScala))

  private[syntax] def streamFromIterable[F[_], A](
      inputStream: Stream[F, Iterable[A]])(implicit F: Async[F]): Stream[F, A] =
    inputStream.flatMap(a =>
      Stream.unfoldEval(a.iterator) { i =>
        if (i.hasNext) F.delay[IteratorResultMaybe[A]](Some((i.next(), i)))
        else F.delay[IteratorResultMaybe[A]](Option.empty)
    })

  private[syntax] def durationToTimeValue(duration: Duration): TimeValue =
    TimeValue.timeValueNanos(duration.toNanos)

}
