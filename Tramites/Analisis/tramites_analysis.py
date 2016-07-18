#!/usr/bin/python

import numpy
from optparse import OptionParser
import json
from pylab import *
import operator

#-----------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

#if options.input is None:
#        parser.error("please give an input")

#-----------------------------------------------------

#infile = options.input

infile = 'jsondata_grupo3.json'

counter = 0

names = {}
codes = {}

outfile = open('listado_tramites_grupo3.csv','w')

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
	
	outfile.write( ','.join(tramites) + '\n')
	
	
outfile.close()
	
