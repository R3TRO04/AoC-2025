package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day5 : AoCDay {
    override val day: LocalDate = day(5)

    override fun executePart1(input: String): Any {
        val lines = input.lines()

        val indexSeparator = lines.indexOfFirst(String::isBlank)
            .let { if (it == -1) lines.size else it }

        val ranges = lines
            .subList(0, indexSeparator)
            .toLongRange()
            .ifEmpty { return 0L }

        return lines.subList(indexSeparator + 1, lines.size)
            .map(String::toLong)
            .count { entry -> ranges.any { entry in it } }
    }

    private fun List<String>.toLongRange(): List<LongRange> =
        filter { it.isNotBlank() }
            .map { line ->
                val (start, end) = line.split('-')
                start.toLong()..end.toLong()
            }

    override fun executePart2(input: String): Any {
        val lines = input.lines()

        val indexSeparator = lines.indexOfFirst(String::isBlank)
            .let { if (it == -1) lines.size else it }

        return lines.subList(0, indexSeparator)
            .toLongRange()
            .ifEmpty { return 0L }
            .sortedBy(LongRange::first)
            .fold<LongRange, MutableList<LongRange>>(mutableListOf()) { acc, range ->
                if (acc.isEmpty()) acc += range
                else {
                    val last = acc.last()
                    if (range.first <= last.last + 1) {
                        acc[acc.lastIndex] = last.first..maxOf(last.last, range.last)
                    } else acc += range
                }
                acc
            }.sumOf { it.last - it.first + 1 }
    }
}
