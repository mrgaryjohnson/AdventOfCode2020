import java.io.File

// Part - 1
fun isValidSledLine(line : String) : Boolean {
    val pattern = "(\\d+)-(\\d+)\\s(.):\\s(.*)".toRegex()
    val match = pattern.matchEntire(line)
	return match
			?.destructured
			?.let{(min, max, char, pass) ->
				val charCount = pass.sumBy{c -> if (c == char[0]) 1 else 0}
				return (charCount >= min.toInt()) && (charCount <= max.toInt())
			}
			?: throw IllegalArgumentException("Not at match")
}

// Part - 2
fun isValidTobogganLine(line : String) : Boolean {
    val pattern = "(\\d+)-(\\d+)\\s(.):\\s(.*)".toRegex()
    val match = pattern.matchEntire(line)
	return match
			?.destructured
			?.let{(min, max, char, pass) ->
				val firstChar = pass[min.toInt() - 1]
				val secondChar = pass[max.toInt() - 1]
				return (firstChar == char[0]).xor(secondChar == char[0])
			} 
			?: throw IllegalArgumentException("Not at match")
}

fun main() {
    val validLines = File("C:\\dev\\2020-Advent\\passwords.txt")
			.readLines()
			.sumBy{line -> if (isValidTobogganLine(line)) 1 else 0}
    println("$validLines value lines")
}