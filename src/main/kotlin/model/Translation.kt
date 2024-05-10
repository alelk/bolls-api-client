package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.serialization.LocalDateTimeSerializer
import java.time.LocalDateTime

/**
 * @param shortName Id of the translation in database. Use it for forming a url.
 * @param fullName Full name of the translation.
 * @param commentaries Indicates whether there is a translators commentary to the translation.
 * @param info May contain a link to translation description.
 * @param updated Date number when the translation was updated in the last time.
 * @param dir May specify rtl direction translations
 */
@Serializable
data class Translation(
  @SerialName("short_name") val shortName: String,
  @SerialName("full_name") val fullName: String,
  val commentaries: String? = null,
  val info: String? = null,
  @Serializable(with = LocalDateTimeSerializer::class) val updated: LocalDateTime,
  val dir: String? = null
) {
  init {
    require(shortName.isNotEmpty()) { "shortName must not be empty" }
    require(fullName.isNotEmpty()) { "fullName must not be empty" }
    require(updated < LocalDateTime.now()) { "updated must be in the past: $updated" }
  }
}
