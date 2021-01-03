from functools import reduce
import re

part1TestInsts = ["mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
                  "mem[8] = 11",
                  "mem[7] = 101",
                  "mem[8] = 0"]

part2TestInsts = ["mask = 000000000000000000000000000000X1001X",
                  "mem[42] = 100",
                  "mask = 00000000000000000000000000000000X0XX",
                  "mem[26] = 1"]

with open('C:/dev/2020-Advent/bitmask-code.txt') as f:
    realInsts = f.read().splitlines()

def applyMaskToValue(value, mask):
    def handleDigit(state, digitMask):
        initialDigit = state[0] % 2
        if digitMask == "0":
            maskedDigit = 0
        elif digitMask == "1":
            maskedDigit = 1
        else:
            maskedDigit = initialDigit
        remainingValue = state[0] // 2
        maskedValue = state[1] + (pow(2, state[2]) *maskedDigit)
        nextPower = state[2] + 1
        return (remainingValue, maskedValue, nextPower)

    # Reduce over the mask, accumulating into tuple of
    #   Value to be handled
    #   Resulting value
    #   Power of digit being handled
    finalState = reduce(handleDigit, mask[::-1], (value, 0, 0))
    return finalState[1]

def getCmd(line):
    cmdMatch = re.match(r'mem\[(\d+)\]\s=\s(\d+)', line)
    if not cmdMatch:
        print("No cmd match for: '" + line + "'")
    address = int(cmdMatch.group(1))
    value = int(cmdMatch.group(2))
    return (address, value)

def applyPart1Cmd(line, mask, mem):
    address, unmaskedValue = getCmd(line)
    mem[address] = applyMaskToValue(unmaskedValue, mask)

def getMask(line):
    maskMatch = re.match(r'mask\s=\s([X10]{36})', line)
    if not maskMatch:
        print("No mask match for: '" + line + "'")
    return maskMatch.group(1)

def handleLines(lines, cmdHandler):
    mem = {}
    mask = "X" * 36
    for line in lines:
        if line[:4] == "mask":
            mask = getMask(line)
        else:
            cmdHandler(line, mask, mem)
    return mem

def sumMem(lines, cmdHandler):
    updatedMem = handleLines(lines, cmdHandler)
    return sum(updatedMem.values())

print("Part 1 - test lines, mem value sum: " + str(sumMem(part1TestInsts, applyPart1Cmd)))
print("Part 1 - real lines, mem value sum: " + str(sumMem(realInsts, applyPart1Cmd)))

def getAddresses(address, mask):
    digitMask = mask[-1]
    if digitMask == "0":
        addressDigits = [address % 2]
    elif digitMask == "1":
        addressDigits = [1]
    else:
        addressDigits = [0, 1]

    if len(mask) == 1:
        addresses = addressDigits
    else:
        subAddresses = getAddresses(address // 2, mask[:-1])
        addresses = [sa * 2 + ad for sa in subAddresses for ad in addressDigits]

    #print("Address: " + str(address) + ", Mask: " + mask + ", Addresses: " + str(addresses))
    return addresses

def applyPart2Cmd(line, mask, mem):
    unmaskedAddress, value = getCmd(line)
    maskedAddresses = getAddresses(unmaskedAddress, mask)
    for maskedAddress in maskedAddresses:
        mem[maskedAddress] = value

print("Part 2 - test lines, mem value sum: " + str(sumMem(part2TestInsts, applyPart2Cmd)))
print("Part 2 - real lines, mem value sum: " + str(sumMem(realInsts, applyPart2Cmd)))
