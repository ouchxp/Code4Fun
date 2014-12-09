import scala.util.Random
object TestOption extends App {
  val opString = None //Some(1)
  def stringToInt(string: String) = Some(1)
  val result = opString flatMap stringToInt
  println(result) //None
  println(1)

  println(List(1).flatMap { x => List(x) })
  println(None map { Some(_) })

  val options = List(Some(1), Some(2), None, Some(3), None, Some(4))
  val res = options flatMap {
    //x => x flatMap { x => if (x % 2 == 0) Some(x) else None } // filter implementation
    x => x.filter { x => x % 2 == 0 } // we get None, Some(2), None, None, None and Some(4) here
  } // None plus m = m
  println(options flatMap { x => x })
  println(res)
  println(None map { x => "Not process None" })
  println(None flatMap { x => Some("Not process None") })

  object SysUtil {
    def getCoumputer() = if (Random.nextInt(10) > 1) Some(Computer) else None
  }

  object Computer {
    def getSoundCard() = if (Random.nextInt(10) > 1) Some(SoundCard) else None
  }

  object SoundCard {
    def getUSB() = if (Random.nextInt(10) > 1) Some(USB) else None
  }

  object USB {
    def getVersion() = if (Random.nextInt(10) > 1) Some("V1.0") else None
    //def getVersion() = "V1.0"
  }

  val version = SysUtil.getCoumputer
    .flatMap { _ getSoundCard }
    .flatMap { _ getUSB }
    .flatMap { _ getVersion }
    .getOrElse("UNKNOWN")
  println(version)

  val version1 =
    for {
      c <- SysUtil.getCoumputer
      s <- c.getSoundCard
      u <- s.getUSB
      v <- u.getVersion
    } yield v
  println(version1 collect { case "V1.0" => "V2.0" } getOrElse "UNKNOWN")

  //  val version = SysUtil.getCoumputer
  //    .flatMap { _ getSoundCard }
  //    .flatMap { _ getUSB }
  //    .map { _ getVersion }
  //    .getOrElse("UNKNOWN")
  //  println(version)
  //
  //  val version1 =
  //    for {
  //      c <- SysUtil.getCoumputer
  //      s <- c.getSoundCard
  //      u <- s.getUSB
  //    } yield u.getVersion
  //  println(version1 getOrElse "UNKNOWN")

}