external fun btoa(input: String): String
external fun atob(input: String): String

actual fun base64Encode(input: String): String =
    btoa(input)

actual fun base64Decode(input: String): String =
    atob(input)
