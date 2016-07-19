#!/usr/bin/python

from optparse import OptionParser
import json
import operator
import sys
sys.path.append('../WebCrawler/')
from Utilities import replaceLatin
import numpy as np
import matplotlib.pyplot as plt

#-------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")
#-------------------------------------------------------------------

infile = options.input

poblacionDB = '../Stats/Municipios.xls_Export.csv'

counter = {}
info    = {}
codes   = {}
group   = {}

with open(infile) as inputfile:
	for line in inputfile:
		jsonstr = line[:-1]
		data  = json.loads( jsonstr )

		code = data['codigo']
		municipio = data['municipio']
		
		if code in codes:
			print "this code is repeated ", code
			continue
		else:
			codes[code] = 1
			
		if municipio in counter:
			counter[municipio] += 1 
		else:
			counter[municipio]  = 1
			group[municipio] = data['grupo']

		

with open(poblacionDB) as inputfile:
	for line in inputfile:
		
		data       = line[:-1].split(',')
		name       = replaceLatin( data[0] )
		population = float(data[3])
		info[name] = population

x1    = []
y1    = []
x1T   = []
area  = []
colors = []

for name in sorted(counter.keys()):
	print name, info[name], counter[name], group[name]
	nm = ''
	if len(name) >= 8:
		nm = name[:8] + '.'
	else:
		nm = name
	x1T.append( nm )
	y1.append( float(counter[name]) )
	area.append( np.pi * ( float(info[name]) )**2 )
	if   group[name] == 'grupo1':
		colors.append(0.144911803701)
	elif group[name] == 'grupo2':
		colors.append(0.366158837925)
	elif group[name] == 'grupo3':
		colors.append(0.559969698516)
	else:
		colors.append(0.862106972926)

max = len(x1T)
x1 = np.arange(max)

k = 0
for x in x1:
	print x, x1T[k]
	k += 1

plt.figure(1,facecolor='white',figsize=(14, 7))

#colors = np.random.rand(max)
#for co in colors:
#	print co
	
plt.scatter(x1, y1, s=area, c=colors, alpha=0.5)
plt.xlim(0.0, max)
plt.ylim(0.0, 110)
plt.xticks(x1, x1T, rotation=45, fontsize=8)
plt.subplots_adjust(bottom=0.15,left=0.10, right=0.97, top=0.93)
plt.ylabel('Numero de tramites')
plt.show()


