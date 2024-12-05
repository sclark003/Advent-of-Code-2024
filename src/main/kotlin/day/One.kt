package org.example.Day

import org.example.Extensions.parseColumnsAsInts
import kotlin.math.abs

class One : Solve() {

    override fun solveA(input: String): Int = measureDistances(input)

    override fun solveB(input: String): Int = measureSimilarity(input)

    private fun measureDistances(input: String): Int {
        val columns = input.parseColumnsAsInts()
        var colA = columns.first().toMutableList()
        var colB = columns.last().toMutableList()
        val distanceList = mutableListOf<Int>()
        while (colA.size > 0) {
            val minA = colA.min()
            val minB = colB.min()
            val distance = abs(minB - minA)
            colA.remove(minA)
            colB.remove(minB)
            distanceList.add(distance)
        }
        return distanceList.sum()
    }


    private fun measureSimilarity(input: String): Int {
        val columns = input.parseColumnsAsInts()
        var colA = columns.first().toMutableList()
        val colB = columns.last().toMutableList()
        val similarityList = mutableListOf<Int>()
        while (colA.size > 0) {
            val x = colA[0]
            val noAppearances = colB.count { it == x }
            val similarity = noAppearances * x
            similarityList.add(similarity)
            colA.removeAt(0)
        }
        return similarityList.sum()
    }

    override fun getDay(): String = "One"

    override fun readInputTest(day: String): String = """3   4
4   3
2   5
1   3
3   9
3   3"""

}
