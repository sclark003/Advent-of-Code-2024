package org.example.Day

class Three: Solve() {

    override fun solveA(input: String): Int {
        val parsedInput = parseCorruptedInput(input)
        val output = runProgram(parsedInput)
        return output.sum()
    }

    override fun solveB(input: String): Int {
        val parsedInput = parseCorruptedInputWithStatements(input)
        val output = runProgram(parsedInput)
        return output.sum()
    }

    private fun parseCorruptedInput(input: String): String {
        val pattern = Regex("mul\\([0-9]+,[0-9]+\\)")//"[^mul(\\d,\\d)]")
        val parsedInput = pattern.findAll(input)
        return parsedInput.map { it.value }.joinToString("")
    }

    private fun parseCorruptedInputWithStatements(input: String): String {
        val filteredInput = filterInput(input)
        val pattern = Regex("mul\\([0-9]+,[0-9]+\\)")
        val parsedInput = pattern.findAll(filteredInput).map { it.value }.joinToString("")
        return parsedInput
    }

    private fun filterInput(input: String): String {
        var newInputList = mutableListOf<String>()
        var lastPattern = 0
        for (i in 0..(input.length-8)) {
            if (input.subSequence(i, i+7) == "don't()" || input.subSequence(i, i+4) == "do()") {
                val x = input.subSequence(lastPattern, i)
                newInputList.add(x.toString())
                lastPattern = i
            }
        }
        newInputList.add(input.subSequence(lastPattern, input.length-1).toString())
        return newInputList.filter { !it.startsWith("don't") }.joinToString()
    }

    private fun runProgram(input: String): List<Int> {
        val multiplicationsList = input.split("mul").mapNotNull { it.ifBlank { null } }.map { mul ->
            mul.replace(Regex("[\\(\\)]"), "").let { it.split(",").let { it.first().toInt() * it.last().toInt() } }
        }
        return multiplicationsList
    }

    override fun getDay(): String = "Three"

    override fun readInputTestA(day: String): String =
        """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"""

    override fun readInputTestB(day: String): String? = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""

}
