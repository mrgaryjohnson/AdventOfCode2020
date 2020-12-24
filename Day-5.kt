import java.io.File

fun partition(ops : CharSequence, max : Int, low : Char, high : Char) : Int {
    return ops.fold(listOf(0, max - 1)){range, op -> 
        if (op == low) 
        	listOf(range[0], ((range[0] + range[1] + 1) / 2) - 1)
        else 
        	listOf((range[0] + range[1] + 1) / 2, range[1])
    }[0]
}

fun getSeatNumber(id : String, rows : Int, cols : Int) : Int {
    val rowId = id.subSequence(0, 7)
    var row = partition(rowId, rows, 'F', 'B')
    val colId = id.subSequence(7, 10)
    var col = partition(colId, cols, 'L', 'R')
    return row * cols + col
}

fun main() {
    println("Test 1 - " + getSeatNumber("FBFBBFFRLR", 128, 8))
    println("Test 2 - " + getSeatNumber("BFFFBBFRRR", 128, 8))
    println("Test 3 - " + getSeatNumber("FFFBBBFRRR", 128, 8))
    println("Test 4 - " + getSeatNumber("BBFFBBFRLL", 128, 8))

    val maxSeat = File("C:\\dev\\2020-Advent\\boarding-passes.txt")
			.readLines()
			.map{line -> getSeatNumber(line, 128, 8)}
			.maxOrNull()
    println("Max seat: $maxSeat")
	
	val assignedSeats = File("C:\\dev\\2020-Advent\\boarding-passes.txt")
			.readLines()
			.map{line -> getSeatNumber(line, 128, 8)}
			.sorted()
			.toList()
	val seat = assignedSeats
        .runningFold(Pair(-7, -3)){p, s -> Pair(p.second, s)}
        .filter{p -> p.second - p.first == 2}
        .first()
        .first + 1
	println("Seat: $seat")
}