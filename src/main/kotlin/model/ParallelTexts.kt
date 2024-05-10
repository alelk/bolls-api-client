package io.github.alelk.bolls_api_client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class ParallelTexts(
  val groups: List<List<ParallelVerseInfo>>
)

/**
 * @param verseId id of the verse in database
 * @param translation translation slug
 * @param book book number
 * @param chapter chapter number
 * @param verse verse number
 * @param text verse text
 */
@Serializable
data class ParallelVerseInfo(
  @SerialName("pk") val verseId: Int,
  val translation: String,
  val book: Int,
  val chapter: Int,
  val verse: Int,
  val text: String
)