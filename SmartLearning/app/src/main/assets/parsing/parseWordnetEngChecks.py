import codecs
#This script parses the engdish gf-wordnet and outputs a csv-file containing the function and its status
with codecs.open('WordNetEng.gf', 'r', encoding='ISO-8859-2') as inputfile:
    with codecs.open('WordNetChecked.csv', 'w', encoding='ISO-8859-2') as outputfile:
        #This file must be run first of the python parsing files
        outputfile.writelines("function;status;langcode\n")

        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("--" not in line):
                #In English, if there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"en-US\"\n")
                continue
            if("--" in line):
                #If there is a comment in English, I am not sure of what to do with the word, so lets consider it guessed...
                outputfile.writelines("\"" + function + "\";\"guessed\";\"en-US\"\n")
