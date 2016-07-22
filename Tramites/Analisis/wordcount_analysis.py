# -*- coding: utf-8 -*-
#!/usr/bin/python

import numpy
from optparse import OptionParser
import json
from pylab import *
import operator

#----------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")
#----------------------------------------------------------------------

infile = options.input

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

perc_view = 0.60
perc_view_str = ' (' + str(1.0-perc_view) + ')'

max = len(sorted_x1)
ymin = max*perc_view
yi = 0

for xx in sorted_x1:
	if yi < ymin:
		yi += 1
		continue
	x1.append(xx[1])
	y1.append(xx[0])
	yi += 1
	
	
pos = arange( len(y1) )+.5

print len(pos),len(y1)

plt.figure(1,facecolor='white')
plt.subplots_adjust(left=0.18, right=0.85)

plt.barh(pos,x1, align='center')
plt.yticks(pos, y1)
plt.xlabel(u'Número de veces que aparece en la muestra')
plt.title(u'Trámites: Pasos (actividad primaria)' + perc_view_str)
plt.grid(True)

plt.savefig('tramites_pasos.png')

perc_view = 0.70
perc_view_str = ' (' + str(1.0-perc_view) + ')'

max = len(sorted_x2)
ymin = max*perc_view
yi = 0

for xx in sorted_x2:
	if yi < ymin:
		yi += 1
		continue
	x2.append(xx[1])
	y2.append(xx[0])
	yi += 1

pos2 = arange( len(y2) )+.5

print len(pos2), len(y2)

plt.figure(2, facecolor='white')
plt.subplots_adjust(left=0.18, right=0.85)

plt.barh(pos2,x2, align='center')
plt.yticks(pos2, y2)
plt.xlabel(u'Número de veces que aparece en la muestra')
plt.title(u'Trámites: documentos requeridos' + perc_view_str)
plt.grid(True)

cti = 0
for xx in sorted_x1:
	cti += xx[1]

print "control: ", cti, counter

plt.savefig('tramites_docsreq.png')

plt.show()


