
treeMap1 = [
"..##.......",
"#...#...#..",
".#....#..#.",
"..#.#...#.#",
".#...##..#.",
"..#.##.....",
".#.#.#....#",
".#........#",
"#.##...#...",
"#...##....#",
".#..#...#.#"]

with open('C:/dev/2020-Advent/trees.txt') as f:
    treeMap2 = f.read().splitlines()

def countTrees(treeMap, colMove, rowMove):
    row = 0
    col = 0
    trees = 0
    while row < len(treeMap):
        if treeMap[row][col] == '#':
            trees += 1
        col = (col + colMove) % len(treeMap[0])
        row = row + rowMove
    return trees

# Part 1
print("Part 1 sample trees: " + str(countTrees(treeMap1, 3, 1)))
print("Part 1 trees: " + str(countTrees(treeMap2, 3, 1)))

routes = [[1, 1], [3, 1], [5, 1], [7, 1], [1, 2]]

def countRoutes(treeMap):
    trees = 1
    for [colMove, rowMove] in routes:
        trees *= countTrees(treeMap, colMove, rowMove)
    return trees

# Part 2
print("Part 2 sample trees: " + str(countRoutes(treeMap1)))
print("Part 2 trees: " + str(countRoutes(treeMap2)))
