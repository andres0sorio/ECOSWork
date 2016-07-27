# -*- coding: utf-8 -*-
#!/usr/bin/python

import unittest
import ROOT
from ROOT import TTimeStamp
from ROOT import TDatime
from ROOT import gStyle
from ROOT import TLatex
from optparse import OptionParser
import json
import numpy as np
import math
#-------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")
#-------------------------------------------------------------------

infile = options.input
outfile = open('basic_results.csv','w')

counter = 0
codes   = {}
names   = {}
info_tramites = {}

histos = []
histos.append( ROOT.TH1F("Basic1","N pasos", 10, 0.0, 10.00) )
histos.append( ROOT.TH1F("Basic2","N verificaciones", 10, 0.0, 10.00) )
histos.append( ROOT.TH1F("Basic3","N requerimientos", 10, 0.0, 10.00) )
histos.append( ROOT.TH1F("Basic4","N resultados", 10, 0.0, 10.00) )

histos2d = []
histos2d.append( ROOT.TH2F("Basic5","Verificaciones vs Requerimientos", 14, 0.0, 14.00, 7, 0.0, 7.00) )

with open(infile) as inputfile:
	for line in inputfile:
		jsonstr = line[:-1]
		data  = json.loads( jsonstr )

		npasos = len( data['pasos'] )
		code = data['codigo']
		name = data['nombre']
		town = data['municipio']
		url  = data['url']
		
		nverif = 0
                nresul = 0
                nreqs  = 0
		
		if code in codes:
			continue
		else:
			codes[code] = 1

		if data['verificacion'][0] != "No data":
                        nverif = len( data['verificacion'] )
                        
                if data['resultados'][0] != "No data":
                        nresul = len( data['resultados'] )

                if data['requerimientos'][0] != "No data":
                        nreqs  = len( data['requerimientos'] )

		if name in names:
			if not ( town in names[name]):
				names[name].append( town )
			else:
				print "Este tramite ya esta asignado a", town, code, url
				continue
				
		else:
			names[name]  = [ town ]
			
		#...
		if name in info_tramites:
			info_tramites[name].append( [npasos, nreqs, nverif] )
		else:
			info_tramites[name] = [ [npasos, nreqs, nverif] ] 
		#...

print len(info_tramites)

for name in sorted(info_tramites.keys()):

	npasos = []
	nverif = []
	nrequs = []

	for info in info_tramites[name]:
		npasos.append( info[0] )
		nrequs.append( info[1] )
		nverif.append( info[2] )

	v1 = np.array(npasos) #pasos
	v2 = np.array(nverif) #verificaciones
	v3 = np.array(nrequs) #requisitos

	v1_max = v1.max()
	v2_max = v2.max()
	v3_max = v3.max()

	v1_min = v1.min()
	v2_min = v2.min()
	v3_min = v3.min()

	v1_mean = v1.mean()
	v2_mean = v2.mean()
	v3_mean = v3.mean()

	v1_std = v1.std()
	v2_std = v2.std()
	v3_std = v3.std()

	matx = np.array( [ v1, v2, v3 ] )

	matxcorr = np.corrcoef(matx)

	histos[0].Fill( v1_mean )
	histos[1].Fill( v2_mean )
	histos[2].Fill( v3_mean )
	histos2d[0].Fill( v3_mean , v2_mean )

	nmunicipios = len (info_tramites[name])

	v1v2_corr = matxcorr[0,1]
	v1v3_corr = matxcorr[0,2]
	v2v3_corr = matxcorr[1,2]

	if math.isnan(v1v2_corr):
		v1v2_corr = 0.0
	if math.isnan(v1v3_corr):
		v1v3_corr = 0.0
	if math.isnan(v2v3_corr):
		v2v3_corr = 0.0

	outfile.write( name.encode('utf-8').replace(',','') + ',' + str(nmunicipios) + ',' +
		       str(v1_min) + ',' + str(v1_max) + ',' + str(v1_mean) + ',' + str(v1_std) + ',' +
		       str(v2_min) + ',' + str(v2_max) + ',' + str(v2_mean) + ',' + str(v2_std) + ',' +
		       str(v3_min) + ',' + str(v3_max) + ',' + str(v3_mean) + ',' + str(v3_std) + ',' +
		       str(v1v2_corr) + ',' + str(v1v3_corr) + ',' + str(v2v3_corr) +
		       '\n')
	counter += 1
			
print counter

outfile.close()

max = len(histos) + len(histos2d)

xlabels = []
xlabels.append(u'Numero de Pasos')
xlabels.append(u'Numero de verificaciones')
xlabels.append(u'Numero de requerimientos')
xlabels.append(u'Numero de resultados')

ylabels = []
ylabels.append(u'Frecuencia')

canvas = []
for i in range(0,max):
	cname = "Plot." + str(i)
	c1 = ROOT.TCanvas(cname, "Canvas for plot", 359,130,598,455 )
	c1.SetFillColor(10)
	c1.SetGridy()
	c1.cd()
	gStyle.SetOptStat(111)
	gStyle.SetOptTitle(0)
	canvas.append(c1)

idx = 0

for h1 in histos:
	canvas[idx].cd()
        h1.SetLineWidth(2)
        h1.SetFillColor(38)
        h1.SetFillStyle(3001)

        h1.GetXaxis().SetTitleFont(42)
        h1.GetXaxis().SetLabelFont(42)
        h1.GetYaxis().SetTitleFont(42)
        h1.GetYaxis().SetLabelFont(42)
        
        h1.GetXaxis().CenterTitle(True)
        h1.GetYaxis().CenterTitle(True)
        h1.GetXaxis().SetTitle(xlabels[idx].encode('utf-8'))
        h1.GetYaxis().SetTitle(ylabels[0].encode('utf-8'))
        h1.Draw()
	canvas[idx].SaveAs(canvas[idx].GetName()+".png")
	idx += 1

for h1 in histos2d:
	canvas[idx].cd()
	h1.Draw("COLTEXT")
	canvas[idx].SaveAs(canvas[idx].GetName()+".png")
	idx += 1
	
