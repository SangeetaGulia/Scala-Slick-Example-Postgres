// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/sangeetg/Downloads/play-samples-2.8.x/play-samples-2.8.x/play-scala-slick-example - Copy/conf/routes
// @DATE:Mon Aug 17 18:25:54 IST 2020


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
