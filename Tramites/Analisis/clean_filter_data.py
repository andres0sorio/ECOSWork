#!/usr/bin/python

import unittest
from optparse import OptionParser
import json

#--------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")
#--------------------------------------------------------------------

infile = options.input
outfile_name = infile.split('.')[0] + "_clean" + ".json"
outfile = open( outfile_name, 'w')

counter = 0
codes = {}

with open(infile) as inputfile:
	for line in inputfile:
		jsonstr = line[:-1]
		data  = json.loads( jsonstr )

		code = data['codigo']
		
		if code in codes:
			continue
		else:
			codes[code] = 1

		outdata = json.dumps(data)
		outfile.write(outdata + '\n')
		
		counter += 1

print counter

inputfile.close()
outfile.close()

