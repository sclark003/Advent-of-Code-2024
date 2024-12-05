package org.example

import org.example.Day.*

fun main() {
    val day = Three()
    println("~~~~~TEST~~~~~")
    val (a, b) = day.solveTest()
    println("A: ${a},    B: ${b}")

    println("\n\n~~~~~~~~~~~~~~")
    val (c, d) = day.solve()
    println("A: ${c},    B: ${d}")
}
