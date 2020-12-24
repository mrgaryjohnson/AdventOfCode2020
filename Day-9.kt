import java.io.File

fun getDiffCounts(adapters : List<Int>) : Map<Int, Int> {
	val builtInAdapter = (adapters.max() ?: 0) + 3
	val adaptersPlusBuiltIn = adapters.plus(builtInAdapter)
	return adaptersPlusBuiltIn
			.sorted()
			.runningFold(Pair(-1, 0)){p, a -> Pair(p.second, a)}
			.filter{p -> p.first >= 0}
			.groupingBy{it.second - it.first}
			.eachCount()
}

fun main() {
    val smallTestAdapters = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)
    val smallDiffCounts = getDiffCounts(smallTestAdapters)
    println("Part 1 - Small test counts: $smallDiffCounts")
    
    val largeTestAdapters = listOf(28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19,
									38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3)
    val largeDiffCounts = getDiffCounts(largeTestAdapters)
    println("Part 1 - Large test counts: $largeDiffCounts")
    
    val realAdapters = File("C:\\dev\\2020-Advent\\jolt-adapters.txt")
    		.readLines()
            .map{it.toInt()}
    val realDiffCounts = getDiffCounts(realAdapters)
    println("Part 1 - real counts: $realDiffCounts")
	
    val product = realDiffCounts.getOrDefault(1, 0) * realDiffCounts.getOrDefault(3, 0)
    println("Part 1 - difference product $product")
}