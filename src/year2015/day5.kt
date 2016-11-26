package year2015

import io.kotlintest.specs.StringSpec
import java.io.File

private val String.isNice: Boolean
    get() {
        val vowelChars = setOf('a', 'e', 'i', 'o', 'u')
        var vowels = 0
        var hasDouble = false
        for (i in 0 until this.length - 1) {
            if (vowelChars.contains(this[i])) {
                vowels++
            }
            if (this[i] == this[i + 1]) {
                hasDouble = true
            }
            when ("${this[i]}${this[i + 1]}") {
                "ab", "cd", "pq", "xy" -> return false
                else -> {
                }
            }
        }
        if (this.length > 1 && vowelChars.contains(this.last())) {
            vowels++
        }
        return hasDouble && vowels >= 3
    }

private data class LetterCouple(val letters: String, val startIdx: Int)

private val String.newNice: Boolean
    get() {
        val pairs = this.zip(this.drop(1)).mapIndexed {
            i: Int, pair: Pair<Char, Char> -> LetterCouple("${pair.first}${pair.second}", i) }
        val seenPairs: MutableSet<LetterCouple> = mutableSetOf()
        var hasDoubleDouble = false
        for (pair in pairs) {
            seenPairs.firstOrNull { it.letters == pair.letters }?.let {
                if (pair.startIdx - it.startIdx > 1) {
                    hasDoubleDouble = true
                }
            }
            if (hasDoubleDouble) {
                break
            }
            seenPairs.add(pair)
        }
        val hasSandwich = (0..this.length - 3).any {
            val slice = this.slice(it..it + 2)
            slice.firstOrNull() == slice.lastOrNull()
        }
        return hasDoubleDouble && hasSandwich
    }

fun main(args: Array<String>) {
    val strings = File(args.first()).readLines()
    println(strings.count(String::isNice))
    println(strings.count(String::newNice))
}

class NiceSpec : StringSpec() {
    init {
        "samples" {
            "ugknbfddgicrmopn".isNice shouldBe true
            "aaa".isNice shouldBe true
            "jchzalrnumimnmhp".isNice shouldBe false
            "haegwjzuvuyypxyu".isNice shouldBe false
            "dvszwmarrgswjxmb".isNice shouldBe false
        }
        "new samples" {
            "qjhvhtzxzqqjkmpb".newNice shouldBe true
            "xxyxx".newNice shouldBe true
            "uurcxstgmygtbstg".newNice shouldBe false
            "ieodomkazucvgmuy".newNice shouldBe false
        }
    }

}
