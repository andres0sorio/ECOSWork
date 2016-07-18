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

infile = 'jsondata_all.json'

counter = 0
codes = {}
steps = {}
requirements = {}

with open(infile) as inputfile:
	for line in inputfile:
		jsonstr = line[:-1]
		data  = json.loads( jsonstr )

		code = data['codigo']

		if code in codes:
			continue
		else:
			codes[code] = 1
			
		for ip in data['pasos']:
			action = ip.split()[0]
			if action in steps:
				steps[action] += 1
			else:
				steps[action]  = 1

		for rq in data['requerimientos']:
			if rq == "No data":
				continue
			preq = rq.split()[0]

			if preq in requirements:
				requirements[preq] += 1
			else:
				requirements[preq]  = 1
				
		counter += 1

x1 = []
y1 = []
x2 = []
y2 = []

sorted_x1 = sorted( steps.items(), key=operator.itemgetter(1))
sorted_x2 = sorted( requirements.items(), key=operator.itemgetter(1))

max = len(sorted_x1)

for xx in sorted_x1:
	x1.append(xx[1]/float(counter)*100.0)
	y1.append(xx[0])

pos = arange(max)+.5

figure(1,facecolor='white')
barh(pos,x1, align='center')
yticks(pos, y1)
xlabel('Ocurrencia [%]')
title('Tramites - accion primaria')
grid(True)

max = len(sorted_x2)
npos = 0
for xx in sorted_x2:
	x2.append(xx[1]/float(counter)*100.0)
	y2.append(xx[0])
	npos += 1

pos2 = arange(max)+.5

figure(2, facecolor='white')
barh(pos2,x2, align='center')
yticks(pos2, y2)
xlabel('Ocurrencia [%]')
title('Tramites - documentos requeridos')
grid(True)

show()

