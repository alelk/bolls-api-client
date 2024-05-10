import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import model.*
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
    install(Logging) {
      level = LogLevel.INFO
    }
  }

  suspend fun getAllTranslations(): List<TranslationsByLanguage> =
    httpClient.get("static/bolls/app/views/languages.json").body()

  suspend fun getBooksInfoByTranslation(translationSlug: String): List<BookInfo> =
    httpClient.get("get-books/$translationSlug/").body()

  suspend fun getChapterText(translationSlug: String, bookId: Int, chapter: Int, withTranslatorComments: Boolean = true): List<Verse> =
    if (withTranslatorComments) httpClient.get("get-chapter/$translationSlug/$bookId/$chapter/").body()
    else httpClient.get("get-text/$translationSlug/$bookId/$chapter/").body()

  @Serializable
  private data class GetParallelTextsRequest(val translations: List<String>, val book: Int, val chapter: Int, val verses: List<Int>)

  suspend fun getParallelTexts(translations: List<String>, bookId: Int, chapter: Int, verses: List<Int>): ParallelTexts =
    httpClient.post("get-paralel-verses/") {
      setBody(GetParallelTextsRequest(translations, bookId, chapter, verses))
      contentType(ContentType.Application.Json)
    }.body<List<List<ParallelVerseInfo>>>().let(::ParallelTexts)

}