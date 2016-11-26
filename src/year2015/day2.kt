package year2015

import io.kotlintest.specs.StringSpec
import java.io.File

private data class Dimensions(val length: Int, val width: Int, val height: Int)
private fun String.parseDimensions(): Dimensions {
    val (l, w, h) = this.split("x").map(String::toInt)
    return Dimensions(l, w, h)
}

private fun paperNeededFor(dimensions: String): Int {
    val (l, w, h) = dimensions.parseDimensions()
    val sideAreas = listOf(l*w, w*h, h*l)
    return sideAreas.sumBy { it * 2 } + (sideAreas.min() ?: 0)
}

private fun ribbonNeededFor(dimensions: String): Int {
    val (l, w, h) = dimensions.parseDimensions()
    val perims = listOf(2*(l+w), 2*(w+h), 2*(h+l))
    return l*w*h + (perims.min() ?: 0)
}

fun main(args: Array<String>) {
    val lines = File(args.first()).readLines()
    val wrappingPaperNeeded = lines.sumBy { year2015.paperNeededFor(it) }
    println(wrappingPaperNeeded)
    val ribbonNeeded = lines.sumBy { year2015.ribbonNeededFor(it) }
    println(ribbonNeeded)
}

class WrappingPaperSpec : StringSpec() {
    init {
        "paperNeededFor should be correct for simple cases" {
            paperNeededFor(dimensions = "2x3x4") shouldBe 58
            paperNeededFor(dimensions = "1x1x10") shouldBe 43
        }
        "ribbonNeededFor should be correct for the sample cases" {
            ribbonNeededFor(dimensions = "2x3x4") shouldBe 34
            ribbonNeededFor(dimensions = "1x1x10") shouldBe 14
        }
    }
}