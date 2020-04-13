import codecs
import re

#This script parses the wordnet-grammars of the specified words and writes them all to a .csv-file so that they can be loaded into the Room databse.

with codecs.open('WordNetChecked.csv', 'w', encoding='ISO-8859-2') as outputfile:
    #Parse English
    with codecs.open('WordNetEng.gf', 'r', encoding='ISO-8859-2') as inputfile:
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
    
    #Parse Swedish
    with codecs.open('WordNetSwe.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"sv-SE\"\n")
                continue
            if("--" not in line):
                #In Swedish, if there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"sv-SE\"\n")
                continue
            if("--" in line):
                #If there is a comment in Swedish, then the word is guessed
                outputfile.writelines("\"" + function + "\";\"guessed\";\"sv-SE\"\n")

    #Parse Bulgarian
    with codecs.open('WordNetBul.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"bg-BG\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"bg-BG\"\n")
                continue
            if("--" in line):
                #If there is a comment in, then the word is considered guessed
                outputfile.writelines("\"" + function + "\";\"guessed\";\"bg-BG\"\n")

    #Parse Spanish
    with codecs.open('WordNetSpa.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"es-ES\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"es-ES\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"es-ES\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"es-ES\"\n")

    #Parse Catalan
    with codecs.open('WordNetCat.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"ca-ES\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"ca-ES\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"ca-ES\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"ca-ES\"\n")
    
    #Parse Chinese
    with codecs.open('WordNetChi.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"cmn-Hans-CN\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"cmn-Hans-CN\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"cmn-Hans-CN\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"cmn-Hans-CN\"\n")

    #Parse Dutch
    with codecs.open('WordNetDut.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"nl-NL\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"nl-NL\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"nl-NL\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"nl-NL\"\n")

    #Parse Estonian
    with codecs.open('WordNetEst.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"et-EE\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"et-EE\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"et-EE\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"et-EE\"\n")     

    #Parse Finnish
    with codecs.open('WordNetFin.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"fi-FI\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"fi-FI\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"fi-FI\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"fi-FI\"\n")

    #Parse Italian
    with codecs.open('WordNetIta.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"it-IT\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"it-IT\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"it-IT\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"it-IT\"\n")

    #Parse Portuguese
    with codecs.open('WordNetPor.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"pt-PT\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"pt-PT\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"pt-PT\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"pt-PT\"\n")  

    #Parse Thai
    with codecs.open('WordNetTha.gf', 'r', encoding='ISO-8859-2') as inputfile:
        for line in inputfile:
            if("lin" not in line):
                continue
            line = line[4:]
            function = line.split(" ")[0]
            if("variants {} ;" in line):
                #There is no definition for the linearization
                outputfile.writelines("\"" + function + "\";\"not_defined\";\"th-TH\"\n")
                continue
            if("--" not in line):
                #If there is no comment then the word is considered checked
                outputfile.writelines("\"" + function + "\";\"checked\";\"th-TH\"\n")
                continue
            if("--guessed" in line):
                outputfile.writelines("\"" + function + "\";\"guessed\";\"th-TH\"\n")
            if("--unchecked" in line):
                outputfile.writelines("\"" + function + "\";\"unchecked\";\"th-TH\"\n")  