import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun base64Encode(input: String): String =
    input
        .encodeToByteArray()
        .let { bytes ->
            bytes.usePinned {
                checkNotNull(NSData.create(bytes = it.addressOf(0), length = bytes.size.toULong()))
            }
        }
        .base64EncodedStringWithOptions(0u)

@OptIn(BetaInteropApi::class)
actual fun base64Decode(input: String): String =
    NSString
        .create(
            data = checkNotNull(NSData.create(base64Encoding = input)),
            encoding = NSUTF8StringEncoding
        )
        .toString()
