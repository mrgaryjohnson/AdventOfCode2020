import java.io.File

fun getDiffCounts(adapters :List<Int>) :Map<Int, Int> {
    val builtInAdapter = (adapters.maxOrNull() ?: 0) + 3
    val adaptersPlusBuiltIn = adapters.plus(builtInAdapter)
   	return adaptersPlusBuiltIn
  	      .sorted()
 	       .runningFold(Pair(-1, 0)){p, a -> Pair(p.second, a)}
 	       .filter{p -> p.first >= 0}
 	       .groupingBy{it.second - it.first}
  	      .eachCount()
}

// Shifts list down, adding new element as begining
fun shift(l : List<Long>, n : Long) : List<Long> {
    var ml = l.toMutableList()
    ml.add(0, n)
    ml.removeAt(3)
    return ml.toList()
}

fun accumulateOptions(p : Pair<Int, List<Long>>, i : Int) : Pair<Int, List<Long>> {
    var c = p
    while (c.first < i - 1) {
        c = Pair(c.first + 1, shift(c.second, 0))
    }
    var sum = c.second.sum()
    return Pair(i, shift(c.second, sum))
}

// To calculator the number of possible options, I am using a Fibonacci-like series.
//   f(n) is either:
//     0 if n is mssing from the list, 
//     or f(n-1) + f(n-2) + f(n-3) if it is in the list
// The Big-O is based on the maximum value in the list, not the size of the list
fun getArrangementOptions(l : List<Int>) : Long {
    val optionsPair = l.sorted()
            .fold(Pair(0, listOf(1L, 0L, 0L))){p, i -> accumulateOptions(p, i)}
    return optionsPair.second[0]
}

fun main() {
    val smallTestAdapters = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)
    val largeTestAdapters = listOf(28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19,
									38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3)
    val realAdapters = File("C:\\dev\\2020-Advent\\jolt-adapters.txt")
    		.readLines()
            .map{it.toInt()}

    val smallDiffCounts = getDiffCounts(smallTestAdapters)
    println("Part 1 - Small test counts: $smallDiffCounts")
    
    val largeDiffCounts = getDiffCounts(largeTestAdapters)
    println("Part 1 - Large test counts: $largeDiffCounts")
    
    val realDiffCounts = getDiffCounts(realAdapters).withDefault{0}
    println("Part 1 - Real counts: $realDiffCounts")
    val product = realDiffCounts.getOrDefault(1, 0) * realDiffCounts.getOrDefault(3, 0)
    println("Part 1 - Difference product $product")

    val smallOptions = getArrangementOptions(smallTestAdapters)
    println("Part 2 - Small test options: $smallOptions")

    val largeOptions = getArrangementOptions(largeTestAdapters)
    println("Part 2 - Small test options: $largeOptions")

    val realOptions = getArrangementOptions(realAdapters)
    println("Part 2 - Real options: $realOptions")
}
