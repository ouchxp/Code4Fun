package algorithms

import scala.io.Source

/**
  * This problem is from https://www.coursera.org/course/algo
  */
object Week1InversionCount extends App {
  val srcArr = Source
    .fromFile("IntegerArray.txt", "UTF-8")
    .getLines()
    .map { x => x.toInt }
    .toArray
  val start = System.currentTimeMillis()

  // InversionCount algorithm piggyback from Merge sort
  def sortAndCount(arr: Array[Int]): (Array[Int], Long) =
    arr match {
      case _ if arr.length < 2 => (arr, 0)
      case _ => {
        val (arr1, arr2) = arr.splitAt(arr.length / 2)
        val (sorted1, count1) = sortAndCount(arr1)
        val (sorted2, count2) = sortAndCount(arr2)
        val (sortedArr, countInv) = mergeAndCountInv(sorted1, sorted2)
        (sortedArr, count1 + count2 + countInv)
      }
    }

  // The core idea is we can count cross inversion when merging two array
  // like:  1 3 9 and 2 4 7, we found 3 > 2 and count+=2 (the remaining length of array1)
  // then found 9 > 4 count+=1, 9 > 7 count+=1. So the cross inversion is 4
  def mergeAndCountInv(
      sorted1: Array[Int],
      sorted2: Array[Int]
  ): (Array[Int], Long) = {
    val rLen = sorted1.length + sorted2.length
    val result = new Array[Int](rLen)
    var count: Long = 0;
    var i = 0
    var j = 0
    for (x <- 0 until rLen) {
      result.update(
        x,
        if (i == sorted1.length) {
          j += 1
          sorted2(j - 1)
        } else if (j == sorted2.length) {
          i += 1
          sorted1(i - 1)
        } else if (sorted1(i) < sorted2(j)) {
          i += 1
          sorted1(i - 1)
        } else {
          count += sorted1.length - i //Magic happens here, count the cross inversion
          j += 1
          sorted2(j - 1)
        }
      )
    }
    (result, count)
  }

  println(sortAndCount(srcArr)._2)
  println("finished in " + (System.currentTimeMillis() - start) + "ms")

  //Brutal force version takes 11s
  var count: Long = 0
  for {
    i <- 0 until 100000
    j <- i until 100000
  } if (srcArr(i) > srcArr(j)) count += 1
  println(count)
  println(
    "Brutal force version finished in " + (System
      .currentTimeMillis() - start) + "ms"
  )

}
