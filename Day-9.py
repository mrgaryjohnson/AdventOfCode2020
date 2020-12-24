
testData = [35, 20, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576]

with open('C:/dev/2020-Advent/xmas-code.txt') as f:
    realData = list(map(int, f.read().splitlines()))

def canBeSummed(candidates, target):
    sortedCandidates = sorted(candidates)
    left = 0
    right = len(sortedCandidates) - 1
    while (left < right) and (sortedCandidates[left] + sortedCandidates[right] != target):
        if sortedCandidates[left] + sortedCandidates[right] < target:
            left += 1
        else:
            right -= 1
    return (left != right)

def findFirstInvalid(numbers, preambleLen):
    preambleLeft = 0
    preambleRight = preambleLen
    targetIdx = preambleLen
    while (targetIdx < len(numbers)) and canBeSummed(numbers[preambleLeft:preambleRight], numbers[targetIdx]):
        preambleLeft += 1
        preambleRight += 1
        targetIdx += 1
    if targetIdx == len(numbers):
        return -1
    else:
        print("Invalid number " + str(numbers[targetIdx]) + " found for preamble " + str(numbers[preambleLeft:preambleRight]))
        return numbers[targetIdx]

invalidTest = findFirstInvalid(testData, 5)
print("Part 1 - First invalid in test data: " + str(invalidTest))

invalidReal = findFirstInvalid(realData, 25)
print("Part 1 - First invalid in real data: " + str(invalidReal))

def findContiguous(numbers, target):
    matchingRange = []
    left = 0
    while (len(matchingRange) == 0) and (left < len(numbers)):
        right = left
        while (right <= len(numbers)) and (sum(numbers[left:right]) < target):
            right += 1
        if sum(numbers[left:right]) == target:
            matchingRange = numbers[left:right]
        left += 1
    return matchingRange

rangeTest = findContiguous(testData, invalidTest)
minTest = min(rangeTest)
maxTest = max(rangeTest)
sumTest = minTest + maxTest
print("Part 2 - Sum of high and low in test data: " + str(sumTest))

rangeReal = findContiguous(realData, invalidReal)
minReal = min(rangeReal)
maxReal = max(rangeReal)
sumReal = minReal + maxReal
print("Part 2 - Sum of high and low in real data: " + str(sumReal))
