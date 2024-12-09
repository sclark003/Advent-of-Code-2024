package org.example.Day

import org.example.Extensions.parseLines

class Five : Solve() {

    override fun solveA(input: String): Int {
        val (rules, updates) = parseRules2Updates(input)
        val safeUpdates = updates.mapNotNull { if (findSafeUpdates(rules, it)) it else null }
        return safeUpdates.sumOf { findMiddleNo(it) }
    }

    override fun solveB(input: String): Int {
        val (rules, updates) = parseRules2Updates(input)
        var unsafeUpdates = updates.mapNotNull { if (!findSafeUpdates(rules, it)) it else null }
        val safeUpdates = mutableListOf<List<Int>>()
        while (unsafeUpdates.isNotEmpty()) {
            val newUpdates = unsafeUpdates.map { makeUpdateSafe(rules, it) }
            val newUnsafeUpdates = newUpdates.mapNotNull {
                if (!findSafeUpdates(rules, it)) it else {
                    safeUpdates.add(it)
                    null
                }
            }
            unsafeUpdates = newUnsafeUpdates
        }
        return safeUpdates.sumOf { findMiddleNo(it) }
    }

    private fun parseRules2Updates(input: String): Pair<Map<Int, List<Int>>, List<List<Int>>> {
        val lines = input.parseLines()
        val empty = lines.indexOfFirst { !it.contains("|") }
        val rules = parseRules(lines.subList(0, empty))
        val updates =
            lines.subList(empty + 1, lines.size).map { it.replace(Regex("[^0-9,]"), "").split(",").map { it.toInt() } }
        return rules to updates
    }

    private fun parseRules(input: List<String>): Map<Int, List<Int>> {
        val rules = mutableMapOf<Int, List<Int>>()
        val rulePairs = input.map { line ->
            line.split("|").let {
                it.first() to it.last()
            }
        }
        rulePairs.forEach { pair ->
            val keyInt = pair.first.toInt()
            if (rules[keyInt] == null) {
                val values =
                    rulePairs.filter { it.first == pair.first }.map { it.second.replace(Regex("[^0-9]"), "").toInt() }
                rules[keyInt] = values
            }
        }
        return rules
    }

    private fun findSafeUpdates(rules: Map<Int, List<Int>>, update: List<Int>): Boolean {
        update.forEachIndexed { i, x ->
            if (i < (update.size) - 1) {
                val nextUpdates = update.subList(i + 1, update.size)
                val otherRules = nextUpdates.mapNotNull { rules[it] }
                if (otherRules.isNotEmpty() && x in otherRules.flatten()) {
                    return false
                }
            }
        }
        return true
    }

    private fun makeUpdateSafe(rules: Map<Int, List<Int>>, update: List<Int>): List<Int> {
        update.forEachIndexed { i, x ->
            if (i < (update.size) - 1) {
                val prevUpdates = if (i > 0) update.subList(0, i) else null
                val nextUpdates = update.subList(i + 1, update.size)
                val otherRules = nextUpdates.mapNotNull { rules[it]?.let { rule -> it to rule } }

                if (otherRules.isNotEmpty() && x in otherRules.map { it.second }.flatten()) {
                    val otherRuleVal = otherRules.first { x in it.second }.first
                    val indexOfotherRuleVal = nextUpdates.indexOf(otherRuleVal)
                    val newNextUpdates = nextUpdates.subList(0, indexOfotherRuleVal).plus(x)
                        .plus(nextUpdates.subList(indexOfotherRuleVal + 1, nextUpdates.size))
                    return (prevUpdates ?: emptyList()).plus(otherRuleVal).plus(newNextUpdates)
                }
            }
        }
        return update
    }

    private fun findMiddleNo(update: List<Int>) = update.elementAt(update.size / 2)

    override fun readInputTest(day: String) = """47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47"""

    override fun getDay() = "Five"
}
