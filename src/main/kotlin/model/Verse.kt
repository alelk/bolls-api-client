package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Verse of chapter
 *
 * @param id id of the verse in database
 * @param verse verse number
 * @param text verse text
 * @param comment translator comment or references (html text)
 */
@Serializable
data class Verse(
  @SerialName("pk") val id: Int,
  val verse: Int,
  val text: String,
  val comment: String? = null
)
