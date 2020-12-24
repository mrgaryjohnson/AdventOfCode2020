import java.io.File

fun parseLine(line : String) : Pair<String, Map<String, Int>> {
    val topParts = line.split("bags contain");
    val outerBag = topParts[0].trim()
    val innerPart = topParts[1]
    val innerBags = "(\\d+)(.+?)bag"
    		.toRegex()
        	.findAll(innerPart)
            .map{match -> match.destructured.let{(count, color) -> Pair(color.trim(), count.toInt())}}
            .toMap()
    return Pair(outerBag, innerBags)
}

fun getParentBags(bag : String, parentMap : Map<String, List<String>>) : Set<String> {
	var children = setOf<String>(bag)
    var parents = setOf<String>()
    while (!children.isEmpty()) {
        val child = children.first()
        val bagsParents = parentMap.getOrDefault(child, listOf())
        		.filter{!(it in parents)}
        children = children.minus(child).plus(bagsParents)
        parents = parents.plus(child)
    }
    return parents.minus(bag)
}

fun getContainingBagCount(bag : String, ruleLines : List<String>) : Int {
    val rules = ruleLines.map{parseLine(it)}
	val bagParentMap = rules
	        .flatMap{p -> p.second.keys.map{s -> Pair(s, p.first)}}
    	    .groupBy({it.first}, {it.second})
   
    val parentBags = getParentBags(bag, bagParentMap)
	return parentBags.size
}

fun getContainedBagCount(bag : String, ruleLines : List<String>) : Int {
    val rules = ruleLines.map{parseLine(it)}.toMap()
    var queue = listOf(Pair(bag, 1))
    var totalBags = 0
    while (!queue.isEmpty()) {
        val parent = queue.first()
        val parentColor = parent.first
        val parentMultiplier = parent.second
        totalBags += parentMultiplier

        val childEntries = rules.getOrDefault(parentColor, mapOf())
                .map{(color, multiplier) -> Pair(color, multiplier * parentMultiplier)}

        queue = queue.minus(parent).plus(childEntries)
    }
    return totalBags - 1 // Subtract out original bag
}

fun main() {
    val part1TestRules = listOf(
"light red bags contain 1 bright white bag, 2 muted yellow bags.",
"dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
"bright white bags contain 1 shiny gold bag.",
"muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
"shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
"dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
"vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
"faded blue bags contain no other bags.",
"dotted black bags contain no other bags.")
    
    val part2TestRules = listOf(
"shiny gold bags contain 2 dark red bags.",
"dark red bags contain 2 dark orange bags.",
"dark orange bags contain 2 dark yellow bags.",
"dark yellow bags contain 2 dark green bags.",
"dark green bags contain 2 dark blue bags.",
"dark blue bags contain 2 dark violet bags.",
"dark violet bags contain no other bags."
    )

	val realRules = File("C:\\dev\\2020-Advent\\bag-rules.txt")
			.readLines()

    val myBag = "shiny gold"
    
    val testContainingBagCount = getContainingBagCount(myBag, part1TestRules)
    println("$testContainingBagCount bags can contain a $myBag bag, with test rules")
    
    val containingBagCount = getContainingBagCount(myBag, realRules)
    println("$containingBagCount bags can contain a $myBag bag")

    val testContainedBagCount = getContainedBagCount(myBag, part2TestRules)
    println("A $myBag bag must contian $testContainedBagCount bags, with test rules")
    
    val containedBagCount = getContainedBagCount(myBag, realRules)
    println("A $myBag bag must contian $containedBagCount bags")
}