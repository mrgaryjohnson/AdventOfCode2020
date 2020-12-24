import java.io.File

fun getTotalQuestionCountPart1(lines : List<String>) : Int {
    val grouped = lines.fold(Pair("", listOf(""))){p, l -> 
        	if (l.isEmpty()) 
        		Pair("", p.second.plus(p.first)) 
        	else 
        		Pair(p.first + l, p.second)
    	}
    val allGroups = grouped.second.plus(grouped.first)
    return allGroups.map{s -> s.toSet().size}.sum()
}

fun getTotalQuestionCountPart2(lines : List<String>) : Int {
    val grouped = lines.fold(Pair(CharRange('a', 'z').toSet(), listOf(0))){p, l -> 
        	if (l.isEmpty()) 
        		Pair(CharRange('a', 'z').toSet(), p.second.plus(p.first.size)) 
        	else 
        		Pair(p.first.intersect(l.toList()), p.second)
    	}
    val allGroups = grouped.second.plus(grouped.first.size)
    return allGroups.sum()
}

fun main() {
    val testLines = listOf(
"abc",
"",
"a",
"b",
"c",
"",
"ab",
"ac",
"",
"a",
"a",
"a",
"a",
"",
"b")
    
	val realLines = File("C:\\dev\\2020-Advent\\customs.txt")
			.readLines()
			
    val testCount1 = getTotalQuestionCountPart1(testLines)
    println("Part 1 - Total questions for test groups : $testCount1")
	
    val realCount1 = getTotalQuestionCountPart1(realLines)
    println("Part 1 - Total questions for groups : $realCount1")			
			
    val testCount2 = getTotalQuestionCountPart2(testLines)
    println("Part 2 - Total questions for test groups : $testCount2")
	
    val realCount2 = getTotalQuestionCountPart2(realLines)
    println("Part 2 - Total questions for groups : $realCount2")			
}