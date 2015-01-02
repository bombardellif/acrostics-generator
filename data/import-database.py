import sys
import re
import redis

def programParameters():
    default = [
       'openthesaurus.txt',  # thesaurus text file
    ]
    i=0
    firstLoop = True
    for arg in sys.argv:
        if not firstLoop:
            default[i] = arg
            i += 1
        else:
            firstLoop = False

    return default

rexRemoveParentheses = re.compile('(\(.*?\) ?|\\n)')
def clearText(outList):
    global rexRemoveParentheses

    for i in range(0, len(outList)):
        outList[i] = rexRemoveParentheses.sub('', outList[i]).strip().lower()

if __name__ == '__main__':
    fileName = programParameters()[0]

    print('Importing file named', fileName)

    # Our data structure to save
    dictionary = {}

    # construct our dictionary while reading the file
    file = open(fileName, 'r')
    for line in file:
        splittedLine = line.split(';')

        # remove comments that appear inside parentheses in the thesaurus
        clearText(splittedLine)

        # for each element in the line, creates an entry at the dictionary adding
        # the synonyms to the set pointed by this element
        for i in range(0, len(splittedLine)):
            therm = splittedLine[i]

            # collect every element in the list except This therm
            synonyms = splittedLine[:i] + splittedLine[i+1:]

            if therm in dictionary:
                dictionary[therm].update(synonyms)
            else:
                dictionary[therm] = set(synonyms)

    print('Read Complete!')

    print('Importing database')
    # Add the dictionary to the Redis database
    r = redis.StrictRedis(host='localhost', port=6379, db=0)

    for key, value in dictionary.items():
        for element in value:
            r.sadd(key, element)

    print('Import completed')
