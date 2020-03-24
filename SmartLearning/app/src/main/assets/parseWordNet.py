import re
with open('WordNet.gf') as inputfile:
    with open('WordNet.csv', 'w') as outputfile:
        outputfile.writelines("function;synonym;explanation\n")

        for line in inputfile:
            if("fun" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("--" not in line):
                outputfile.writelines("\"" + function + ";\"\";" + "\"There is no explanation available for this word\"" + "\n")
                continue
            synonym = re.split(r'(-- )(.*?)((?:(?!\t).)*)', line)[3]
            explanation = re.split(r'\t(.*?)(\n|;)', line)[1]

            outputfile.writelines("\"" + function + "\";\"" + synonym + "\";\"" +explanation + "\"\n")

# This file reads through WordNet.gf, extracts the function names and the explanation for each word
# and puts it in a csv file
#for i in range(0,2):
#    inputfile.next() ##this code only for python 2.7


