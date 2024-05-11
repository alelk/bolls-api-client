package io.github.alelk.bolls_api_client

import io.github.alelk.bolls_api_client.model.*
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
import java.net.URI
import java.net.URL
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

enum class LogLevel { ALL, HEADERS, BODY, INFO, NONE }

data class BollsApiClientConfig(
  val baseUrl: URL = URI("https://bolls.life/").toURL(),
  val connectTimeout: Duration = 10.seconds,
  val requestTimeout: Duration = 20.seconds,
  val socketTimeout: Duration = 20.seconds,
  val logLevel: LogLevel = LogLevel.INFO
)

/** Bolls API http client.
 *
 * @param baseUrl Base url of the Bolls API.
 */
class BollsApiClient(private val config: BollsApiClientConfig) {
  private val httpClient = HttpClient(CIO) {
    defaultRequest { url(config.baseUrl.toString()) }
    install(ContentNegotiation) {
      json()
    }
    install(Logging) {
      level = io.ktor.client.plugins.logging.LogLevel.valueOf(config.logLevel.name)
    }
    install(HttpTimeout) {
      requestTimeoutMillis = config.requestTimeout.inWholeMilliseconds
      connectTimeoutMillis = config.connectTimeout.inWholeMilliseconds
      socketTimeoutMillis = config.socketTimeout.inWholeMilliseconds
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