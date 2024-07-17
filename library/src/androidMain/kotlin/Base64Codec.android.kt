import java.util.Base64

actual fun base64Encode(input: String): String =
    Base64.getEncoder().encodeToString(input.encodeToByteArray())

actual fun base64Decode(input: String): String =
    String(Base64.getDecoder().decode(input))
