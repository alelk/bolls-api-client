package io.github.alelk.bolls_api_client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Book details
 *
 * @param bookId id of the book in database
 * @param chronOrder chronological order book number according to Robert Young
 * @param name name of the book
 * @param countChapters number of chapters in the book
 */
@Serializable
data class BookInfo(
  @SerialName("bookid") val bookId: Int,
  @SerialName("chronorder") val chronOrder: Int,
  val name: String,
  @SerialName("chapters") val countChapters: Int
)
