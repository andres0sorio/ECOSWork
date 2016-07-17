#!/usr/bin/python

from time import sleep
import os
import subprocess
from optparse import OptionParser

#------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

parser.add_option("-s", type = "int", dest="step",
                  help="Step", metavar="step" )

(options, args) = parser.parse_args()

if options.input is None:
    parser.error("please give an input")

if options.step is None:
    parser.error("please select the current step")

#------------------------------------------------------------------

step = options.step
town_list = options.input
group = town_list.split('.')[0].split('_')[1]

municipios = []

with open(town_list) as inputfile:
    for line in inputfile:
        municipio = line[:-1]
        municipios.append(municipio)
    inputfile.close()
    
if step == 1: 

    for mn in municipios:
        p = subprocess.Popen(["python", "webcrawler.py", mn, group],
                             stdout=subprocess.PIPE)
        result = p.stdout.read()
        print result

elif step == 2:

    output_failed = open('webcrawler2_failures.dat', 'a')

    for fname in municipios:
        print fname
        with open(fname) as inputfile:
            for line in inputfile:

                info = line[:-1].split(',')
                mn  = info[0]
                cod = info[1]
                url = info[2]
                
                p = subprocess.Popen(["python", "webcrawler2.py", mn, cod, url, group],
                                     stdout=subprocess.PIPE)
                result = p.stdout.read()[:-1]
                print result
                if result == "Success":
                    print result
                else:
                    output_failed.write(line)

        inputfile.close()
        #break
        
    output_failed.close()

else:

    print "Current step not implemented (choose 1 or 2)"

