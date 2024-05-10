package model

import kotlinx.serialization.Serializable

/**
 * @param language language description
 * @param translations translations of this language
 */
@Serializable
data class TranslationsByLanguage(
  val language: String,
  val translations: List<Translation>
)
