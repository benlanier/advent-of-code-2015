package year2015

import io.kotlintest.specs.StringSpec
import java.io.File
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest

private fun String.md5(): ByteArray {
    val digester = MessageDigest.getInstance("MD5")
    digester.update(this.toByteArray(Charset.defaultCharset()))
    return digester.digest()
}

private fun numsGivingZeroesForKey(key: String, isPartA: Boolean = false): String {
    var num = 1
    // TODO: figure out how to print leading zeroes
    while (BigInteger("$key$num".md5()).toString(16).format("%1$32x").length > 26 + if (isPartA) 1 else 0) {
        if (num % 1000 == 0) {
            // get some visual progress to show the program didn't hang
            println("tried $num")
            System.out.flush()
        }
        num++
    }
    return "$num"
}

fun main(args: Array<String>) {
    val key = File(args.first()).readLines().first()
    println(numsGivingZeroesForKey(key = key))
}

class MD5Spec : StringSpec() {
    init {
        "it should work for the samples" {
            numsGivingZeroesForKey(key = "abcdef", isPartA = true) shouldBe "609043"
            numsGivingZeroesForKey(key = "pqrstuv", isPartA = true) shouldBe "1048970"
        }
    }
}