from functools import reduce

testSeatingMap =  ['L.LL.LL.LL',
                   'LLLLLLL.LL',
                   'L.L.L..L..',
                   'LLLL.LL.LL',
                   'L.LL.LL.LL',
                   'L.LLLLL.LL',
                   '..L.L.....',
                   'LLLLLLLLLL',
                   'L.LLLLLL.L',
                   'L.LLLLL.LL']

with open('C:/dev/2020-Advent/seating-map.txt') as f:
    realSeatingMap = f.read().splitlines()

def isFilled(row, col, seatingMap):
    if (row < 0) or (row >= len(seatingMap)) or (col < 0) or (col >= len(seatingMap[0])):
        return False
    else:
        return (seatingMap[row][col] == '#')

adjacentSeats = [(x, y) for x in [-1,0,1] for y in [-1,0,1] if y != x or y != 0]

def getAdjacentFilledCount(row, col, seatingMap):
    return len(list(filter(lambda offsets: isFilled(row + offsets[0], col + offsets[1], seatingMap), adjacentSeats)))

def part1Rules(seatingMap):
    newSeatingMap = []
    for rIndex, row in enumerate(seatingMap):
        newRow = ''
        for sIndex, seat in enumerate(row):
            filledSeats = getAdjacentFilledCount(rIndex, sIndex, seatingMap)
            if seat == 'L' and filledSeats == 0:
                newRow += '#'
            elif seat == '#' and filledSeats >= 4:
                newRow += 'L'
            else:
                newRow += seat
        newSeatingMap.append(newRow)
    return newSeatingMap

def isVisiblyFilled(row, col, dir, seatingMap):
    visRow = row + dir[0]
    visCol = col + dir[1]
    while (visRow >= 0) and (visRow < len(seatingMap)) and (visCol >= 0) and (visCol < len(seatingMap[0])) and seatingMap[visRow][visCol] == '.':
        visRow += dir[0]
        visCol += dir[1]
    return isFilled(visRow, visCol, seatingMap)

def getVisibleFilledCount(row, col, seatingMap):
    return len(list(filter(lambda offsets: isVisiblyFilled(row, col, offsets, seatingMap), adjacentSeats)))

def part2Rules(seatingMap):
    newSeatingMap = []
    for rIndex, row in enumerate(seatingMap):
        newRow = ''
        for sIndex, seat in enumerate(row):
            filledSeats = getVisibleFilledCount(rIndex, sIndex, seatingMap)
            if seat == 'L' and filledSeats == 0:
                newRow += '#'
            elif seat == '#' and filledSeats >= 5:
                newRow += 'L'
            else:
                newRow += seat
        newSeatingMap.append(newRow)
    return newSeatingMap

def findSteadyState(seatingMap, rules):
    oldSeatingMap = seatingMap
    newSeatingMap = [row.replace('L', '#') for row in oldSeatingMap]
    while oldSeatingMap != newSeatingMap:
        # print('------------------')
        # for row in newSeatingMap:
        #     print(row)
        oldSeatingMap = newSeatingMap
        newSeatingMap = rules(oldSeatingMap)
    return newSeatingMap

testPart1SteadyState = findSteadyState(testSeatingMap, part1Rules)
testPart1FilledSeats = reduce(lambda t, s: t + s.count('#'), testPart1SteadyState, 0)
print('Part 1 - filled test seats: ' + str(testPart1FilledSeats))

realPart1SteadyState = findSteadyState(realSeatingMap, part1Rules)
realPart1FilledSeats = reduce(lambda t, s: t + s.count('#'), realPart1SteadyState, 0)
print('Part 1 - filled real seats: ' + str(realPart1FilledSeats))

testPart2SteadyState = findSteadyState(testSeatingMap, part2Rules)
testPart2FilledSeats = reduce(lambda t, s: t + s.count('#'), testPart2SteadyState, 0)
print('Part 2 - filled test seats: ' + str(testPart2FilledSeats))

realPart2SteadyState = findSteadyState(realSeatingMap, part2Rules)
realPart2FilledSeats = reduce(lambda t, s: t + s.count('#'), realPart2SteadyState, 0)
print('Part 2 - filled real seats: ' + str(realPart2FilledSeats))
