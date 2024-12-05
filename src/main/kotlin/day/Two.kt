package org.example.Day

import org.example.Extensions.parseRowsAsInts
import kotlin.math.abs

class Two: Solve() {

    override fun solveA(input: String): Int  = calculateNoOfSafe(input)

    override fun solveB(input: String): Int = calculateNoOfSafeWithDampener(input)

    private fun calculateNoOfSafe(input: String): Int {
        val rows = input.parseRowsAsInts()
        var safe = 0
        rows.forEach { row ->
            when {
                isIncreasing(row) && isSafeLevelDifference(row)-> safe++
                isDecreasing(row) && isSafeLevelDifference(row) -> safe++
                else -> {}
            }
        }
        return safe
    }

    private fun calculateNoOfSafeWithDampener(input: String): Int {
        val inputRows = input.parseRowsAsInts()
        var rowsList = mutableListOf<List<Int>>()
        val safeList = inputRows.map { inputRow ->
            rowsList.clear()
            var row = inputRow.toMutableList()
            for (x in 0..<inputRow.size) {
                row.removeAt(x)
                val tempRow = row
                rowsList.add(tempRow)
                row = inputRow.toMutableList()
            }
            rowsList.add(inputRow)
            findDampenedSafeRows(rowsList)
        }
        return safeList.count { it }
    }

    private fun findDampenedSafeRows(rows: List<List<Int>>): Boolean {
        rows.forEach { row ->
            when {
                isIncreasing(row) && isSafeLevelDifference(row)-> return true
                isDecreasing(row) && isSafeLevelDifference(row) -> return true
                else -> {}
            }
        }
        return false
    }

    private fun isIncreasing(row: List<Int>): Boolean {
        for (x in 0..row.size - 2) {
            if (row[x] >= row[x+1]) return false
        }
        return true
    }

    private fun isDecreasing(row: List<Int>): Boolean {
        for (x in 0..row.size - 2) {
            if (row[x] <= row[x+1]) return false
        }
        return true
    }

    private fun isSafeLevelDifference(row: List<Int>): Boolean {
        for (x in 0..row.size - 2) {
            if (abs(row[x] - row[x+1]) > 3) return false
        }
        return true
    }

    override fun getDay(): String = "Two"

    override fun readInputTest(day: String): String = """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9"""

}
