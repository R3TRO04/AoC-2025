package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day1 : AoCDay {
    override val day: LocalDate = day(1)

    private enum class Direction(directionKey: Char) {
        LEFT('L'),
        RIGHT('R');

        companion object {
            fun ofChar(directionKey: Char) = when (directionKey) {
                'L' -> LEFT
                'R' -> RIGHT
                else -> throw IllegalArgumentException("Unknown direction key: $directionKey")
            }
        }
    }

    override fun executePart1(input: String): Any {
        var result = 0
        var safeDail = Dail()
        input.lines().forEach { line ->
            val direction = Direction.ofChar(line.first())
            val steps = line.substring(1).trim().toInt()

            safeDail = safeDail.turn(steps, direction)
            if (safeDail.current == 0) result += 1
        }

        return result
    }

    private data class Dail(
        private val min: Int = 0,
        val current: Int = 50,
        private val max: Int = 99
    ) {
        fun turn(count: Int, direction: Direction): Dail {
            val rangeSize = max - min + 1

            val delta = when (direction) {
                Direction.LEFT  -> -count
                Direction.RIGHT ->  count
            }

            val raw = current + delta
            val wrapped = ((raw - min) % rangeSize + rangeSize) % rangeSize + min

            return copy(current = wrapped)
        }

        fun countZerosDuringTurn(count: Int, direction: Direction): Long {
            if (count <= 0) return 0L

            val rangeSize = max - min + 1
            val start = current

            val firstK = when (direction) {
                Direction.RIGHT -> {
                    val r = (rangeSize - (start % rangeSize)) % rangeSize
                    if (r == 0) rangeSize else r
                }
                Direction.LEFT -> {
                    val r = start % rangeSize
                    if (r == 0) rangeSize else r
                }
            }

            if (firstK > count) return 0L


            return 1L + (count - firstK) / rangeSize
        }
    }


    override fun executePart2(input: String): Any {
        var zeroHits = 0L
        var safeDial = Dail() // starts at 50

        input.lineSequence()
            .filter { it.isNotBlank() }
            .forEach { line ->
                val direction = Direction.ofChar(line.first())
                val steps = line.substring(1).trim().toInt()

                zeroHits += safeDial.countZerosDuringTurn(steps, direction)
                safeDial = safeDial.turn(steps, direction)
            }

        return zeroHits
    }
}
