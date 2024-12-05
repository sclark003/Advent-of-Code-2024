package org.example.Day

import org.example.Extensions.parseColumns
import org.example.Extensions.parseDiagonalDownRight
import org.example.Extensions.parseDiagonalUpLeft
import org.example.Extensions.parseRows

class Four : Solve() {

    override fun solveA(input: String): Int {
        return searchWordSearch(input)
    }

    override fun solveB(input: String): Int {
        val rows = input.parseRows()
        val gridSize = rows.first().length
        val diagonalsUpLeft = input.parseDiagonalUpLeft(gridSize)
        val diagonalsDownRight = input.parseDiagonalDownRight(gridSize)
        val diagonal1As = findAsInMas(diagonalsUpLeft)
        val diagonal2As = findAsInMas(diagonalsDownRight)
        val aLocs = diagonal1As.filter { diagonal2As.contains(it) }
        return aLocs.size
    }

    private fun searchWordSearch(input: String): Int {
        val rows = input.parseRows()
        val gridSize = rows.first().length
        val rowsCount = countXmas(rows)
        val columnsCount = countXmas(input.parseColumns(gridSize))
        val diagonalsUpLeftCount = countXmas(input.parseDiagonalUpLeft(gridSize).map { it.first })
        val diagonalsDownRightCount = countXmas(input.parseDiagonalDownRight(gridSize).map { it.first })
        return rowsCount + columnsCount + diagonalsUpLeftCount + diagonalsDownRightCount
    }

    private fun countXmas(words: List<String>): Int {
        val pattern = Regex("XMAS")
        val patternBackwards = Regex("SAMX")
        return words.sumOf { word ->
            val x = pattern.findAll(word).map { it.value }.filter { it.isNotBlank() }.toList()
            val y = patternBackwards.findAll(word).map { it.value }.filter { it.isNotBlank() }.toList()
            x.size + y.size
        }
    }

    private fun findAsInMas(words: List<Pair<String, List<Pair<Int, Int>>>>): List<Pair<Int, Int>> {
        val aLocs = mutableListOf<Pair<Int, Int>>()
        words.forEach { word2Loc ->
            val word = word2Loc.first
            val loc = word2Loc.second
            for (i in 0..word.length - 1) {
                if (i < word.length - 2 && ((word[i] == 'M' && word[i + 1] == 'A' && word[i + 2] == 'S')
                            || (word[i] == 'S' && word[i + 1] == 'A' && word[i + 2] == 'M'))
                ) {
                    aLocs.add(loc[i+1])
                }
            }
        }
        return aLocs.toList()
    }

    override fun readInputTest(day: String) = """MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX"""

    override fun getDay(): String = "Four"

}
