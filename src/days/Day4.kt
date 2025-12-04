package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day4 : AoCDay {
    override val day: LocalDate = day(4)

    override fun executePart1(input: String): Any {
        val grid = input
            .lineSequence()
            .map(String::trimEnd)
            .filter { it.isNotEmpty() }
            .toList()

        if (grid.isEmpty()) return 0

        val height = grid.size
        val width = grid[0].length

        var accessibleCount = 0

        fun inBounds(r: Int, c: Int): Boolean =
            r in 0 until height && c in 0 until width

        for (r in 0 until height) {
            for (c in 0 until width) {
                if (grid[r][c] != '@') continue

                var neighborRolls = 0

                for ((dr, dc) in directions) {
                    val nr = r + dr
                    val nc = c + dc
                    if (inBounds(nr, nc) && grid[nr][nc] == '@') {
                        neighborRolls++
                    }
                }

                if (neighborRolls < 4) {
                    accessibleCount++
                }
            }
        }

        return accessibleCount
    }

    private val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1,          0 to 1,
        1 to -1,  1 to 0, 1 to 1
    )

    override fun executePart2(input: String): Any {
        val rows = input
            .lineSequence()
            .map(String::trimEnd)
            .filter { it.isNotEmpty() }
            .toList()

        if (rows.isEmpty()) return 0

        val height = rows.size
        val width = rows[0].length

        val grid = Array(height) { r -> rows[r].toCharArray() }
        val neighborCounts = Array(height) { IntArray(width) }

        fun inBounds(r: Int, c: Int): Boolean =
            r in 0 until height && c in 0 until width

        // 1) Initial neighbor counts for each roll of paper
        for (r in 0 until height) {
            for (c in 0 until width) {
                if (grid[r][c] != '@') continue
                var count = 0
                for ((dr, dc) in directions) {
                    val nr = r + dr
                    val nc = c + dc
                    if (inBounds(nr, nc) && grid[nr][nc] == '@') {
                        count++
                    }
                }
                neighborCounts[r][c] = count
            }
        }


        val queue = ArrayDeque<Pair<Int, Int>>()
        for (r in 0 until height) {
            for (c in 0 until width) {
                if (grid[r][c] == '@' && neighborCounts[r][c] < 4) {
                    queue.add(r to c)
                }
            }
        }

        var removed = 0

        // 3) Peel off all rolls that ever become accessible
        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeFirst()
            if (grid[r][c] != '@') continue // might have been removed already

            // Remove this roll
            grid[r][c] = '.'
            removed++

            // Update neighbors
            for ((dr, dc) in directions) {
                val nr = r + dr
                val nc = c + dc
                if (!inBounds(nr, nc)) continue
                if (grid[nr][nc] != '@') continue

                neighborCounts[nr][nc]--
                if (neighborCounts[nr][nc] < 4) {
                    queue.add(nr to nc)
                }
            }
        }

        return removed
    }

}
