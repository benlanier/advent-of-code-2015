package year2015

import io.kotlintest.specs.StringSpec
import java.io.File

// being a data class, Position is copied by value.
private data class Position(var x: Int = 0, var y: Int = 0) {
    fun move(token: Char) {
        when (token) {
            '>' -> x++
            '<' -> x--
            'v' -> y++
            '^' -> y--
        }
    }
}

private val String.housesVisited: Int
    get() {
        val pos = Position()
        val visited: MutableSet<Position> = mutableSetOf(pos)
        this.forEach {
            pos.move(it)
            visited.add(pos)
        }
        return visited.size
    }

private val String.roboHousesVisited: Int
    get() {
        val pos = Position()
        val rpos = Position()
        val visited: MutableSet<Position> = mutableSetOf(pos)
        val (santaHouses, roboHouses) = this.withIndex().partition { it.index % 2 == 0 }
        santaHouses.forEach {
            pos.move(it.value)
            visited.add(pos)
        }
        roboHouses.forEach {
            rpos.move(it.value)
            visited.add(rpos)
        }
        return visited.size
    }

fun main(args: Array<String>) {
    val input = File(args.first()).readLines().first()
    println(message = input.housesVisited)
    println(message = input.roboHousesVisited)
}

class HousesSpec : StringSpec() {
    init {
        "houses visited must be correct for the samples" {
            ">".housesVisited shouldBe 2
            "^>v<".housesVisited shouldBe 4
            "^v^v^v^v^v".housesVisited shouldBe 2
        }
        "roboHousesVisited must be correct for the samples" {
            "^v".roboHousesVisited shouldBe 3
            "^>v<".roboHousesVisited shouldBe 3
            "^v^v^v^v^v".roboHousesVisited shouldBe 11
        }
    }
}
