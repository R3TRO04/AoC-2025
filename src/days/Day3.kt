package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day3 : AoCDay {
    override val day: LocalDate = day(3)

    override fun executePart1(input: String): Any {
        return input
            .lineSequence()
            .map(String::trim)
            .filter(String::isNotEmpty)
            .map(::maxBankJoltage)
            .sumOf(Int::toLong)
    }

    private fun maxBankJoltage(bank: String): Int {
        require(bank.length >= 2) { "Bank must contain at least two batteries" }

        var best = 0
        var maxLeftDigit = bank[0].digitToInt()

        for (index in 1 until bank.length) {
            val currentDigit = bank[index].digitToInt()

            val candidate = maxLeftDigit * 10 + currentDigit

            if (candidate > best) best = candidate
            if (currentDigit > maxLeftDigit) maxLeftDigit = currentDigit
        }

        return best
    }

    override fun executePart2(input: String): Any {
        return input
            .lineSequence()
            .map(String::trim)
            .filter(String::isNotEmpty)
            .sumOf { maxBankJoltage(it, 12) }
    }

    private fun maxBankJoltage(bank: String, pick: Int = 12): Long {
        require(bank.length >= pick) {
            "Bank must contain at least $pick batteries, got: ${bank.length}"
        }

        val n = bank.length
        val k = 12
        val result = StringBuilder(k)

        var start = 0
        for (i in 0 until k) {
            val remainingToPick = k - i
            val maxStartIndex = n - remainingToPick

            var maxDigit = -1
            var maxIndex = start

            for (j in start..maxStartIndex) {
                val d = bank[j] - '0'
                if (d > maxDigit) {
                    maxDigit = d
                    maxIndex = j
                    if (maxDigit == 9) break
                }
            }

            result.append(maxDigit)
            start = maxIndex + 1
        }

        return result.toString().toLong()
    }
}
