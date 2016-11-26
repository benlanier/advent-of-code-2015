package year2015

import java.io.File
import java.util.*
import java.util.regex.Pattern


fun main(args: Array<String>) {
    val s = Scanner(File(args.first()))

    val off = Pattern.compile("turn off (\\d.*?),(\\d.*?) through (\\d.*?),(\\d.*?)$")
    val on = Pattern.compile("turn on (\\d.*?),(\\d.*?) through (\\d.*?),(\\d.*?)$")
    val toggle = Pattern.compile("toggle (\\d.*?),(\\d.*?) through (\\d.*?),(\\d.*?)$")

    val lights = Array(1000, { Array(1000, { 0 }) })

    while (s.hasNextLine()) {
        val line = s.nextLine()
        val (offMatcher, onMatcher, tMatcher) = arrayOf(off, on, toggle).map { it.matcher(line) }
        when {
            offMatcher.find() -> {
                val (x0, y0, x1, y1) = (1..4).map { offMatcher.group(it).toInt() }.toIntArray()
                for (row in y0..y1) {
                    (x0..x1)
                            .filter { lights[row][it] > 0 }
                            .forEach { lights[row][it]-- }
                }
            }
            onMatcher.find() -> {
                val (x0, y0, x1, y1) = (1..4).map { onMatcher.group(it).toInt() }.toIntArray()
                for (row in y0..y1) {
                    (x0..x1)
                            .forEach { lights[row][it]++ }
                }
            }
            tMatcher.find() -> {
                val (x0, y0, x1, y1) = (1..4).map { tMatcher.group(it).toInt() }.toIntArray()
                for (row in y0..y1) {
                    (x0..x1)
                            .forEach { lights[row][it] += 2 }
                }
            }
        }
    }

    println(lights.map{ it.sum() }.sum())
}