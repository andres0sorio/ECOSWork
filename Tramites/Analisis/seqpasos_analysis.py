# -*- coding: utf-8 -*-
#!/usr/bin/python

import numpy
from optparse import OptionParser
import json
from pylab import *
import operator

#-------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")
#-------------------------------------------------------------------

infile = options.input

counter = 0
codes = {}
stepsTwo = {}
stepsThree = {}

with open(infile) as inputfile:
	for line in inputfile:
		jsonstr = line[:-1]
		data  = json.loads( jsonstr )

		code = data['codigo']

		if code in codes:
			continue
		else:
			codes[code] = 1

		if len( data['pasos'] ) == 2:

			action1 = data['pasos'][0].split()[0]
			action2 = data['pasos'][1].split()[0]
			seqact  = action1 + '_' + action2

			if seqact in stepsTwo:
				stepsTwo[seqact] += 1
			else:
				stepsTwo[seqact]  = 1

		if len( data['pasos'] ) == 3:

			action1 = data['pasos'][0].split()[0]
			action2 = data['pasos'][1].split()[0]
			action3 = data['pasos'][2].split()[0]
			
			seqact  = action1 + '_' + action2 + '_' + action3

			if seqact in stepsThree:
				stepsThree[seqact] += 1
			else:
				stepsThree[seqact]  = 1


		counter += 1

x1 = []
y1 = []

x2 = []
y2 = []

sorted_x1 = sorted( stepsTwo.items(), key=operator.itemgetter(1))
sorted_x2 = sorted( stepsThree.items(), key=operator.itemgetter(1))

max = len(sorted_x1)

perc_view = 0.60
perc_view_str = ' (' + str(1.0-perc_view) + ')'

ymin = max*perc_view
yi = 0

for xx in sorted_x1:
	if yi < ymin:
		yi += 1
		continue
	x1.append(xx[1])
	y1.append(xx[0])
	yi += 1

pos = arange(len(y1))+.5

print len(pos), len(x1)

plt.figure(1,facecolor='white')
plt.subplots_adjust(left=0.20, right=0.95)
plt.barh(pos,x1, align='center')
plt.yticks(pos, y1)
plt.xlabel(u'Número de veces que aparece la sequencia')
plt.title(u'Trámites - Sequencia de dos pasos' + perc_view_str)
plt.grid(True)

plt.savefig('tramites_seq2pasos.png')

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

pos = arange(len(y2))+.5

print len(pos), len(x2)

plt.figure(2,facecolor='white')
plt.subplots_adjust(left=0.31, right=0.95)
plt.barh(pos,x2, align='center')
plt.yticks(pos, y2)
plt.xlabel(u'Número de veces que aparece la secuencia')
plt.title(u'Trámites - Sequencia de tres pasos' + perc_view_str)
plt.grid(True)

plt.savefig('tramites_seq3pasos.png')

plt.show()

