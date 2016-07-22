# -*- coding: utf-8 -*-
#!/usr/bin/python

from optparse import OptionParser
import json
import operator
import sys
sys.path.append('../WebCrawler/')
from Utilities import replaceLatin
import numpy as np
import matplotlib.cm as colormap
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches

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

clmap = colormap.get_cmap('RdYlGn')

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
		colors.append(clmap(0.11))
	elif group[name] == 'grupo2':
		colors.append(clmap(0.36))
	elif group[name] == 'grupo3':
		colors.append(clmap(0.55))
	else:
		colors.append(clmap(0.86))
		


max = 4*len(x1T)
x1 = np.arange(0,max,4)

k = 0
for x in x1:
	print x, x1T[k]
	k += 1

fig = plt.figure(1,facecolor='white',figsize=(14, 7))
ax1 = fig.add_subplot(111)
		      
#colors = np.random.rand(max)
#for co in colors:
#	print co
	
plt.scatter(x1, y1, s=area, c=colors, alpha=0.6, cmap=clmap)
plt.xlim(0.0, max)
plt.ylim(0.0, 120)
plt.xticks(x1, x1T, rotation=90, fontsize=9)
plt.subplots_adjust(bottom=0.15,left=0.10, right=0.97, top=0.93)
plt.ylabel(u'Número de tramites')

g1_patch = mpatches.Patch(color=clmap(0.11), label='Grupo 1')
g2_patch = mpatches.Patch(color=clmap(0.36), label='Grupo 2')
g3_patch = mpatches.Patch(color=clmap(0.55), label='Grupo 3')
g4_patch = mpatches.Patch(color=clmap(0.86), label='Grupo 4')

plt.legend(handles=[g1_patch,g2_patch,g3_patch,g4_patch])

plt.savefig('tramites_poblacion.png')
plt.savefig('tramites_poblacion.pdf')

plt.show()

