package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day2 : AoCDay {
    override val day: LocalDate = day(2)

    override fun executePart1(input: String): Any {
        val ranges = input.parseRanges()
        if (ranges.isEmpty()) return 0L

        val maxEnd = ranges.maxOf { it.last }
        val invalidIds = buildRepeatedDoubleNumbersUpTo(maxEnd)

        var sum = 0L

        for (range in ranges) {
            // Find first invalid ID >= range.first using binary search
            val startIndex = invalidIds.binarySearch(range.first).let { index ->
                if (index >= 0) index else index.inv()  // insertion point
            }

            var i = startIndex
            while (i < invalidIds.size && invalidIds[i] <= range.last) {
                sum += invalidIds[i]
                i++
            }
        }

        return sum
    }

    private fun String.parseRanges(): List<LongRange> =
        trim().split(',').asSequence()
            .map(String::trim)
            .filter(String::isNotEmpty)
            .map { token ->
                val (startStr, endStr) = token.split('-')
                val start = startStr.toLong()
                val end = endStr.toLong()
                start..end
            }
            .toList()

    private fun buildRepeatedDoubleNumbersUpTo(limit: Long): LongArray {
        if (limit <= 0L) return LongArray(0)

        val maxDigits = limit.toString().length
        val result = mutableListOf<Long>()

        for (halfDigits in 1..(maxDigits / 2)) {
            val pow10Half = halfDigits.pow10
            val startBase = (halfDigits - 1).pow10  // no leading zero
            val endBase = (halfDigits).pow10 - 1

            var base = startBase
            while (base <= endBase) {
                val candidate = base * pow10Half + base
                if (candidate > limit) break
                result += candidate
                base++
            }
        }

        return result.toLongArray()
    }

    private val Int.pow10: Long
        get() {
            var result = 1L
            repeat(this) {
                result *= 10
            }
            return result
        }

    override fun executePart2(input: String): Any {
        val ranges = input.parseRanges()
        if (ranges.isEmpty()) return 0L

        val maxEnd = ranges.maxOf { it.last }
        val invalidIds = buildRepeatedPatternNumbersUpTo(maxEnd)

        var sum = 0L

        for (range in ranges) {
            val startIndex = invalidIds.binarySearch(range.first).let { index ->
                if (index >= 0) index else index.inv()
            }

            var i = startIndex
            while (i < invalidIds.size && invalidIds[i] <= range.last) {
                sum += invalidIds[i]
                i++
            }
        }

        return sum
    }

    private fun buildRepeatedPatternNumbersUpTo(limit: Long): LongArray {
        if (limit <= 0L) return LongArray(0)

        val maxDigits = limit.toString().length
        val result = mutableSetOf<Long>()

        for (blockDigits in 1..(maxDigits / 2)) {
            val startBase = (blockDigits - 1).pow10
            val endBase = (blockDigits).pow10 - 1

            var base = startBase
            while (base <= endBase) {
                val baseStr = base.toString()

                var k = 2
                while (blockDigits * k <= maxDigits) {
                    val candidateStr = baseStr.repeat(k)
                    val candidate = candidateStr.toLong()

                    if (candidate > limit) break

                    result += candidate
                    k++
                }

                base++
            }
        }

        return result.toLongArray().also { it.sort() }
    }
}