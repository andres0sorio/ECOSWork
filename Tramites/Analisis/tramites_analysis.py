#!/usr/bin/python

import numpy
from optparse import OptionParser
import json
from pylab import *
import operator

#--------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

parser.add_option("-o", type = "string", dest="output",
                  help="Output file with results", metavar="output" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")
	
if options.output is None:
        parser.error("please give an output")
#--------------------------------------------------------------------

infile = options.input
outfile = options.output

counter = 0

names = {}
codes = {}

outputfile = open(outfile,'w')

with open(infile) as inputfile:
	for line in inputfile:
		jsonstr = line[:-1]
		data  = json.loads( jsonstr )
		name = data['nombre']
		code = data['codigo']

		if code in codes:
			print "this code is repeated ", code
			continue
		else:
			codes[code] = 1
			
		if name in names:
			if not (data['municipio'] in names[name]):
				names[name].append( data['municipio'] ) 
		else:
			names[name]  = [ data['municipio'] ]
				
		counter += 1


for name in sorted(names.keys()):
	#print name, len(names[name]), '+'.join( names[name] )
	tramites = []
	tramites.append(name.replace(',','').encode('utf-8'))
	tramites.append(str(len(names[name])))
	tramites.append(str('+'.join( names[name] )))
	
	outputfile.write( ','.join(tramites) + '\n')


inputfile.close()	
outputfile.close()
	
