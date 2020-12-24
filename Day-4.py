import re

testPassports = [
"ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
"byr:1937 iyr:2017 cid:147 hgt:183cm",
"",
"iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
"hcl:#cfa07d byr:1929",
"",
"hcl:#ae17e1 iyr:2013",
"eyr:2024",
"ecl:brn pid:760753108 byr:1931",
"hgt:179cm",
"",
"hcl:#cfa07d eyr:2025 pid:166559648",
"iyr:2011 ecl:brn hgt:59in"
]

badPassports = [
"eyr:1972 cid:100",
"hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926",
"",
"iyr:2019",
"hcl:#602927 eyr:1967 hgt:170cm",
"ecl:grn pid:012533040 byr:1946",
"",
"hcl:dab227 iyr:2012",
"ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277",
"",
"hgt:59cm ecl:zzz",
"eyr:2038 hcl:74454a iyr:2023",
"pid:3556412378 byr:2007"
]

goodPassports = [
"pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980",
"hcl:#623a2f",
"",
"eyr:2029 ecl:blu cid:129 byr:1989",
"iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
"",
"hcl:#888785",
"hgt:164cm byr:2001 iyr:2015 cid:88",
"pid:545766238 ecl:hzl",
"eyr:2022",
"",
"iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"
]

with open('C:/dev/2020-Advent/passports.txt') as f:
    realPassports = f.read().splitlines()

def extractPassports(passportLines):
    passports = []
    running = {}
    for line in passportLines:
        if len(line) == 0 and len(running) != 0:
            passports.append(running)
            running = {}
        for match in re.finditer(r'(\w{3}):(\S+)', line):
            running.update({match.group(1) : match.group(2)})
    if len(running) != 0:
        passports.append(running)
    return passports

# Part 1
def isValidPassport1(passport):
    return set(passport.keys()).issuperset({"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"})

def countValidPassports1(passportLines):
    allPassports = extractPassports(passportLines)
    validPassports = [passport for passport in allPassports if isValidPassport1(passport)]
    return len(validPassports)

print("Valid passports in sample data: " + str(countValidPassports1(testPassports)))
print("Valid passports in real data: " + str(countValidPassports1(realPassports)))

# Part 2
def isYearValid(yearStr, min, max):
    if not yearStr:
        return False
    if not re.fullmatch(r'\d{4}', yearStr):
        return False
    year = int(yearStr)
    return min <= year and year <= max

def isHeightValid(heightStr):
    if not heightStr:
        return False
    match = re.fullmatch(r'(\d+)(cm|in)', heightStr)
    if not match:
        return False
    num = int(match.group(1))
    if match.group(2) == "cm":
        return 150 <= num and num <= 193
    else:
        return 59 <= num and num <= 76

def isHairColorValid(color):
    return re.fullmatch(r'#[0-9a-f]{6}', color)

def isEyeColorValid(color):
    return color in {'amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth'}

def isPassportIdValid(id):
    return re.fullmatch(r'\d{9}', id)

def isValidPassport2(passport):
    return set(passport.keys()).issuperset({"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"}) \
            and isYearValid(passport['byr'], 1920, 2002) \
            and isYearValid(passport['iyr'], 2010, 2020) \
            and isYearValid(passport['eyr'], 2020, 2030) \
            and isHeightValid(passport['hgt']) \
            and isHairColorValid(passport['hcl']) \
            and isEyeColorValid(passport['ecl']) \
            and isPassportIdValid(passport['pid'])

def countValidPassports2(passportLines):
    allPassports = extractPassports(passportLines)
    validPassports = [passport for passport in allPassports if isValidPassport2(passport)]
    return len(validPassports)

print("Valid passports in sample good data: " + str(countValidPassports2(goodPassports)))
print("Valid passports in sample bad data: " + str(countValidPassports2(badPassports)))
print("Valid passports in real data: " + str(countValidPassports2(realPassports)))
