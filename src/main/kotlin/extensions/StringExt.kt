package org.example.Extensions

fun String.parseLines(): List<String> = this.split("\n")

fun String.parseWhiteSpace(): List<String> = this.split("\\s+".toRegex())

fun String.parseRows(): List<String> {
    val rowsList = this.parseLines()
    val rows = rowsList.map { it.parseWhiteSpace().joinToString("") }
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

fun String.parseColumns(noCols: Int = 2): List<String> {
    val rowLists = this.parseLines()
    val rows = rowLists.map { it.parseWhiteSpace().joinToString("") }
    val columnsList = mutableListOf<String>()
    var i = 0
    while (i < noCols) {
        val col = mutableListOf<String>()
        rows.forEach { row ->
            col.add(row[i].toString())
        }
        columnsList.add(col.joinToString(""))
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

fun String.parseDiagonalDownRight(noRows: Int): List<Pair<String, List<Pair<Int, Int>>>> {
    val rows = this.parseRows()
    val diagonalsDownRightList = mutableListOf<Pair<String, List<Pair<Int, Int>>>>()
    val list = mutableListOf<String>()
    for (i in 0..((noRows*2)-2)) {
        val listXY = mutableListOf<Pair<Int, Int>>()
        if (i>=noRows) {
            val xStart = i-noRows+1
            for (y in xStart.. (noRows-1)) {
                val x = noRows+xStart-y-1
                list.add(rows[x][y].toString())
                listXY.add(y to x)
            }
        } else {
            for (y in 0..i) {
                val x = i-y
                list.add(rows[x][y].toString())
                listXY.add(y to x)
            }
        }
        diagonalsDownRightList.add(list.joinToString("") to listXY)
        list.clear()
    }
    return diagonalsDownRightList
}

fun String.parseDiagonalUpLeft(noRows: Int): List<Pair<String, List<Pair<Int, Int>>>> {
    val rows = this.parseRows()
    val diagonalsInt = mutableListOf<List<Int>>()
    val list = mutableListOf<Int>()
    for (i in 0..(noRows*2)-2) {
        for (x in i downTo 0) {
            list.add(x)
        }
        if (i>=noRows){
            val reverseList = list.reversed()
            val j = i-noRows+1
            val subList = reverseList.subList(j, reverseList.size-j)
            diagonalsInt.add(subList)
        } else {
            diagonalsInt.add(list.reversed())
        }
        list.clear()
    }

    val diagonalsList = mutableListOf<Pair<String, List<Pair<Int, Int>>>>()
    for (i in 0..diagonalsInt.size-1) {
        val listXY = mutableListOf<Pair<Int, Int>>()
        val yList = diagonalsInt[i]
        val xList = diagonalsInt[diagonalsInt.size-1-i].reversed()
        val diagonolString = mutableListOf<String>()
        for (j in 0..xList.size-1) {
            val y = yList[j]
            val x = xList[yList.size-1-j]
            diagonolString.add(rows[y][x].toString())
            listXY.add(x to y)
        }
        diagonalsList.add(diagonolString.joinToString("") to listXY)
        diagonolString.clear()
    }
    return diagonalsList
}
