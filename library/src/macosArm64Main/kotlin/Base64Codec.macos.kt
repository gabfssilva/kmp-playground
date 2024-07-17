import base64.*
import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class)
actual fun base64Encode(input: String): String {
    val expectedSize = (input.length + 2) / 3 * 4
    val encoded: CPointer<ByteVar> = checkNotNull(encode(input = input, size = expectedSize))
    return encoded.toKString()
}

@OptIn(ExperimentalForeignApi::class)
actual fun base64Decode(input: String): String {
    val expectedSize = (input.length + 2) / 3 * 4
    val encoded: CPointer<ByteVar> = checkNotNull(decode(input = input, size = expectedSize))
    return encoded.toKString()
}
