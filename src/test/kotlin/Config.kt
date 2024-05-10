import io.kotest.core.Tag
import java.net.URI

object Config {
  val BOOLS_API_BASE_URL = URI(System.getProperty("BOOLS_API_BASE_URL", "https://bolls.life/")).toURL()

  object Tags {
    val integrationTest = Tag("IntegrationTest")
    val realBollsApi = Tag("RealBollsApi")
  }
}