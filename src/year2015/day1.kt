package year2015

import io.kotlintest.specs.StringSpec
import java.io.File

private val String.whichFloor: Int
    get() = this.fold(0, { i, c -> when (c) {
        '(' -> i + 1
        ')' -> i - 1
        else -> i
    }})

private val String.whichBasementPosition: Int
    get() {
        var floor = 0
        this.forEachIndexed { i, c ->
            floor += when (c) {
                '(' -> 1
                ')' -> -1
                else -> 0
            }
            if (floor < 0) {
                return i + 1
            }
        }
        return -1
    }

fun main(args: Array<String>) {
    val input = File(args.first()).readLines().first()
    println(input.whichFloor)
    println(input.whichBasementPosition)
}

class WhichFloorSpec : StringSpec() {
    init {
        "string.year2015.getWhichFloor should pass the samples" {
            "(())".whichFloor shouldBe 0
            "()()".whichFloor shouldBe 0
            "(((".whichFloor shouldBe 3
            "(()(()(".whichFloor shouldBe 3
            "))(((((".whichFloor shouldBe 3
            "())".whichFloor shouldBe -1
            "))(".whichFloor shouldBe -1
            ")))".whichFloor shouldBe -3
            ")())())".whichFloor shouldBe -3
        }
        "string.year2015.getWhichBasementPosition should pass the samples" {
            ")".whichBasementPosition shouldBe 1
            "()())".whichBasementPosition shouldBe 5
        }
    }
}

