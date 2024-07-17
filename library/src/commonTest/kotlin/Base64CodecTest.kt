import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Base64CodecTest : FunSpec({
    test("Ensure 'base64Encode' works as expected") {
        base64Encode("hello") shouldBe "aGVsbG8="
    }

    test("Ensure 'base64Decode' works as expected") {
        base64Decode("aGVsbG8=") shouldBe "hello"
    }
})
