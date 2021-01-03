
def getNthPlay(plays, startingList):
    prevTimePlaid = {}
    for idx, play in enumerate(startingList):
        prevTimePlaid[play] = idx + 1
    lastPlay = startingList[-1]
    for turn in range(len(startingList) + 1, plays + 1):
        play = turn - 1 - prevTimePlaid.get(lastPlay, turn - 1)
        #print("Turn: " + str(turn) + ", LastPlay: " + str(lastPlay) + ", Play: " + str(play))
        prevTimePlaid[lastPlay] = turn - 1
        lastPlay = play
    return lastPlay

testLists = [[0, 3, 6], [1, 3, 2], [2, 1, 3], [1, 2, 3], [2, 3, 1], [3, 2, 1], [3, 1, 2]]
for testList in testLists:
    print("Part 1 - test list " + str(testList) + ", turn 2020: " + str(getNthPlay(2020, testList)))

realList = [1, 0, 18, 10, 19, 6]
print("Part 1 - real list " + str(realList) + ", turn 2020: " + str(getNthPlay(2020, realList)))

for testList in testLists:
    print("Part 2 - test list " + str(testList) + ", turn 30000000: " + str(getNthPlay(30000000, testList)))

print("Part 2 - real list " + str(realList) + ", turn 30000000: " + str(getNthPlay(30000000, realList)))
