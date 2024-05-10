package model.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.ZoneOffset

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.time.LocalDateTime", PrimitiveKind.LONG)

  override fun serialize(encoder: Encoder, value: LocalDateTime) {
    encoder.encodeLong(value.toEpochSecond(ZoneOffset.UTC) * 1000)
  }

  override fun deserialize(decoder: Decoder): LocalDateTime {
    return LocalDateTime.ofEpochSecond(decoder.decodeLong() / 1000, 0, ZoneOffset.UTC)
  }
}