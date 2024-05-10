import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import model.TranslationsByLanguage
import java.net.URI
import java.net.URL

/** Bolls API http client.
 *
 * @param baseUrl Base url of the Bolls API.
 */
class BollsApiClient(private val baseUrl: URL = URI("https://bolls.life/").toURL()) {
  private val httpClient = HttpClient(CIO) {
    defaultRequest { url(baseUrl.toString()) }
    install(ContentNegotiation) {
      json()
    }
  }

  suspend fun getAllTranslations(): List<TranslationsByLanguage> =
    httpClient.get("static/bolls/app/views/languages.json").body()
}