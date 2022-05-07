package sh.nemo.string

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.math.BigInteger
import java.net.URLDecoder
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

// String casing

/**
 * Converts a string to specific [caseFormat] using the word splitting rules defined in [splitToWords].
 */
fun String.toCase(caseFormat: CaseFormat) = caseFormat.format(this)

/** Converts a string to SCREAMING_SNAKE_CASE using the word splitting rules defined in [splitToWords]. */
fun String.toScreamingSnakeCase() = toCase(CaseFormat.UPPER_UNDERSCORE)

/** Converts a string to snake_case using the word splitting rules defined in [splitToWords]. */
fun String.toSnakeCase() = toCase(CaseFormat.LOWER_UNDERSCORE)

/** Converts a string to PascalCase using the word splitting rules defined in [splitToWords]. */
fun String.toPascalCase() = toCase(CaseFormat.CAPITALIZED_CAMEL)

/** Converts a string to camelCase using the word splitting rules defined in [splitToWords]. */
fun String.toCamelCase() = toCase(CaseFormat.CAMEL)

/** Converts a string to TRAIN-CASE using the word splitting rules defined in [splitToWords]. */
fun String.toTrainCase() = toCase(CaseFormat.UPPER_HYPHEN)

/** Converts a string to kebab-case using the word splitting rules defined in [splitToWords]. */
fun String.toKebabCase() = toCase(CaseFormat.LOWER_HYPHEN)

/** Converts a string to UPPER SPACE CASE using the word splitting rules defined in [splitToWords]. */
fun String.toUpperSpaceCase() = toCase(CaseFormat.UPPER_SPACE)

/** Converts a string to Title Case using the word splitting rules defined in [splitToWords]. */
fun String.toTitleCase() = toCase(CaseFormat.CAPITALIZED_SPACE)

/** Converts a string to lower space case using the word splitting rules defined in [splitToWords]. */
fun String.toLowerSpaceCase() = toCase(CaseFormat.LOWER_SPACE)

/** Converts a string to UPPER.DOT.CASE using the word splitting rules defined in [splitToWords]. */
fun String.toUpperDotCase() = toCase(CaseFormat.UPPER_DOT)

/** Converts a string to dot.case using the word splitting rules defined in [splitToWords]. */
fun String.toDotCase() = toCase(CaseFormat.LOWER_DOT)

// Paths and FS

fun String.relativize(other: String) = this.relativize(other.toPath())
fun String.relativize(other: Path) = this.toPath().relativize(other).toString()

fun Any.toPath(): Path = Paths.get(this.toString())

fun String.asFile() = File(this)

// Bytes and encodings

fun secureByteArray(size: Int): ByteArray {
    return ByteArray(size).apply {
        SecureRandom().nextBytes(this)
    }
}

fun ByteArray.base64Encoded(): String {
    return Base64.getUrlEncoder().withoutPadding().encodeToString(this)
}

fun String.base64Encoded(): String {
    return this.toByteArray(Charset.defaultCharset()).base64Encoded()
}

fun String.base64Decoded(): String {
    return this.base64DecodedBytes().toString(Charset.defaultCharset())
}

fun String.base64DecodedBytes(): ByteArray {
    return Base64.getUrlDecoder().decode(this)
}

fun String.urlDecode(): String {
    return URLDecoder.decode(this, "UTF-8")
}

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}

fun secureRandomString(length: Int = 16): String {
    return secureByteArray(length).base64Encoded()
}

fun randomString(length: Int = 16) = List(length) {
    (('a'..'z') + ('A'..'Z')).random()
}.joinToString("")

val Throwable.stackTraceString: String
    get() {
        StringWriter().use { sw ->
            PrintWriter(sw).use { pw ->
                printStackTrace(pw)
                return sw.toString()
            }
        }
    }

@OptIn(ExperimentalUnsignedTypes::class)
fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

fun String.hexToBigint() = BigInteger(this.removePrefix("0x"), 16)
