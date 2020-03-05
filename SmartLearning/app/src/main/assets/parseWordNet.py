import re
inputfile = open('WordNet.gf')
outputfile = open('WordNet.csv', 'w')

# This file reads through WordNet.gf, extracts the function names and the explanation for each word
# and puts it in a csv file
for i in range(0,2):
    inputfile.next()

for line in inputfile:
    if("fun" not in line):
        continue
    line = line[4:]
    function = line.split(" ")[0]
    if("--" not in line):
        outputfile.writelines(function + ";" + "There is no explanation available for this word." + "\n")
        continue
    explanation = re.split(r'\t(.*?)(\n|;)', line)[1]

    outputfile.writelines(function + ";" +explanation + "\n")
