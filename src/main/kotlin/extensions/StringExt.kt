package org.example.Extensions

fun String.parseLines(): List<String> = this.split("\n")

fun String.parseWhiteSpace(): List<String> = this.split("\\s+".toRegex())

fun String.parseRows(): List<List<String>> {
    val rowsList = this.parseLines()
    val rows = rowsList.map { it.parseWhiteSpace() }
    return rows
}

fun String.parseRowsAsInts(): List<List<Int>> {
    val rowsList = this.parseLines()
    val rows = rowsList.map { row ->
        val stringRow = row.parseWhiteSpace()
        stringRow.mapNotNull { it.takeIf { it.isNotBlank() }?.toInt() }
    }
    return rows
}

fun String.parseColumns(noCols: Int = 2): List<List<String>> {
    val rowLists = this.parseLines()
    val rows = rowLists.map { it.parseWhiteSpace() }
    val columnsList = mutableListOf<List<String>>()
    var i = 0
    while (i < noCols) {
        val col = mutableListOf<String>()
        rows.forEach { row ->
            col.add(row[i])
        }
        columnsList.add(col)
        i++
    }
    return columnsList.toList()
}

fun String.parseColumnsAsInts(noCols: Int = 2): List<List<Int>> {
    val rowLists = this.parseLines()
    val rows = rowLists.map { it.parseWhiteSpace() }
    val columnsList = mutableListOf<List<Int>>()
    var i = 0
    while (i < noCols) {
        val col = mutableListOf<Int>()
        rows.forEach { row ->
            col.add(row[i].toInt())
        }
        columnsList.add(col)
        i++
    }
    return columnsList.toList()
}
