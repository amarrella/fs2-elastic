package com.alessandromarrella.fs2_elastic

import org.elasticsearch.common.unit.TimeValue

import scala.concurrent.duration.Duration

package object io {

  private[io] def durationToTimeValue(duration: Duration): TimeValue =
    TimeValue.timeValueNanos(duration.toNanos)

}
