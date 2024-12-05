package org.example.Day

import java.io.File
import java.io.InputStream

abstract class Solve {

    fun solveTest(): Pair<Int, Int> {
        val day = getDay()
        val input = readInputTest(day)
        val inputA = readInputTestA(day)
        val inputB = readInputTestB(day)
        val outputA = solveA(input ?: inputA.orEmpty())
        val outputB = solveB(input ?: inputB.orEmpty())
        return outputA to outputB
    }

    fun solve(): Pair<Int, Int> {
        val day = getDay()
        val input = readInput(day)
        val outputA = solveA(input)
        val outputB = solveB(input)
        return outputA to outputB
    }

    open fun solveA(input: String): Int = 0

    open fun solveB(input: String): Int = 0

    protected fun readInput(day: String): String {
        val inputStream: InputStream = File("src/main/kotlin/Day/Inputs/$day.txt").inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        return inputString
    }

    abstract fun getDay(): String

    open fun readInputTest(day: String): String? = null

    open fun readInputTestA(day: String): String? = null

    open fun readInputTestB(day: String): String? = null
}
