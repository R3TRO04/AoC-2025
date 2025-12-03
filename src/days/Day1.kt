package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day1 : AoCDay {
    override val day: LocalDate = day(1)

    private enum class Direction constructor(directionKey: Char) {
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
            safeDail = when (val moveDirection = Direction.ofChar(line.first())) {
                Direction.RIGHT -> safeDail.turn(line.substring(1).toInt(), moveDirection)
                Direction.LEFT -> safeDail.turn(line.substring(1).toInt(), moveDirection)
            }
            if (safeDail.current == 0) result += 1
        }

        return result
    }

    private data class Dail(
        private val min: Int = 0,
        val current: Int = 50,
        private val max: Int = 99,
        var turnover: Int = 0
    ) {


        fun turn(count: Int, direction: Direction): Dail {
            return when(direction) {
                Direction.LEFT -> {
                    if(current - count < min) {
                        var foo = current
                        while(foo - count < min) {
                            foo += 99
                            if(foo - count == 0) turnover += 1
                            turnover += 1
                        }
                        this.copy(current = (current - count) % 100, turnover = turnover)
                    }
                    else {
                        if(current - count == 0) turnover += 1
                        this.copy(current = current - count, turnover = turnover)
                    }
                }

                Direction.RIGHT -> {
                    if(current + count > max) {
                        var foo = current
                        while(foo + count > max) {
                            foo -= 99
                            if(foo + count == 0) turnover += 1
                            turnover += 1
                        }
                        this.copy(current = (current + count) % 100, turnover = turnover)
                    }
                    else {
                        if(current + count == 0) turnover += 1
                        this.copy(current = current + count, turnover = turnover)
                    }
                }
            }
        }
    }


    override fun executePart2(input: String): Any {
        var safeDail = Dail()
        input.lines().forEach { line ->
            safeDail = when (val moveDirection = Direction.ofChar(line.first())) {
                Direction.RIGHT -> safeDail.turn(line.substring(1).toInt(), moveDirection)
                Direction.LEFT -> safeDail.turn(line.substring(1).toInt(), moveDirection)
            }
        }

        return safeDail.turnover
    }
}
