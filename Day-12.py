import re

testInsts = ['F10', 'N3', 'F7', 'R90', 'F11']

with open('C:/dev/2020-Advent/navigation.txt') as f:
    realInsts = f.read().splitlines()

vecByDir = {'N': (1, 0), 'E': (0, 1), 'S': (-1, 0), 'W': (0, -1)}
vecByAngle = {0: (1, 0), 90: (0, 1), 180: (-1, 0), 270: (0, -1)}
signByDir = {'L': -1, 'R': 1}

def applyPart1Rules(op, dist, state):
    if op in ['L', 'R']:
        state['angle'] = (state['angle'] + (signByDir[op] * dist)) % 360
    else:
        if op == 'F':
            unitVec = vecByAngle[state['angle']]
        else:
            unitVec = vecByDir[op]
        state['ns'] += unitVec[0] * dist
        state['ew'] += unitVec[1] * dist



def applyPart2Rules(op, dist, state):
    if op == 'L':
        for _ in range(dist // 90):
            state['wp-ns'], state['wp-ew'] = state['wp-ew'], - state['wp-ns']
    elif op == 'R':
        for _ in range(dist // 90):
            state['wp-ns'], state['wp-ew'] = - state['wp-ew'], state['wp-ns']
    elif op == 'F':
        state['ns'] += state['wp-ns'] * dist
        state['ew'] += state['wp-ew'] * dist
    else:
        unitVec = vecByDir[op]
        state['wp-ns'] += unitVec[0] * dist
        state['wp-ew'] += unitVec[1] * dist

def getManhattanDistance(insts, rules):
    state = {'angle': 90, 'ns': 0, 'ew': 0, 'wp-ns': 1, 'wp-ew': 10}
    for inst in insts:
        match = re.match(r'(\w)(\d+)', inst)
        op = match.group(1)
        dist = int(match.group(2))
        rules(op, dist, state)
        #print(str(state))
    return abs(state['ns']) + abs(state['ew'])

testPart1Dist = getManhattanDistance(testInsts, applyPart1Rules)
print('Part 1 - test Manhattan distance: ' + str(testPart1Dist))

realPart1Dist = getManhattanDistance(realInsts, applyPart1Rules)
print('Part 1 - real Manhattan distance: ' + str(realPart1Dist))

testPart2Dist = getManhattanDistance(testInsts, applyPart2Rules)
print('Part 2 - test Manhattan distance: ' + str(testPart2Dist))

realPart2Dist = getManhattanDistance(realInsts, applyPart2Rules)
print('Part 2 - real Manhattan distance: ' + str(realPart2Dist))
