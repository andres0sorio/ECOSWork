#!/usr/bin/python

import unittest
import ROOT
from ROOT import TTimeStamp
from ROOT import TDatime
from ROOT import gStyle
from optparse import OptionParser
import json

#-----------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")

#-----------------------------------------------------

infile = options.input

counter = 0
codes   = {}
nombres = {}

histos = []
histos.append( ROOT.TH1F("Basic1","N pasos", 10, 0.0, 10.00) )
histos.append( ROOT.TH1F("Basic2","N verificaciones", 10, 0.0, 10.00) )
histos.append( ROOT.TH1F("Basic3","N resultados", 10, 0.0, 10.00) )
histos.append( ROOT.TH1F("Basic4","N requerimientos", 10, 0.0, 10.00) )

with open(infile) as inputfile:
	for line in inputfile:
		jsonstr = line[:-1]
		data  = json.loads( jsonstr )

		npasos = len( data['pasos'] )
		code = data['codigo']
		name = data['nombre']
		
		if code in codes:
			continue
		else:
			codes[code] = 1

		if name in nombres:
			continue
		else:
			nombres[name] = 1
					
                nverif = 0
                nresul = 0
                nreqs  = 0
                
                if data['verificacion'][0] != "No data":
                        nverif = len( data['verificacion'] )
                        
                if data['resultados'][0] != "No data":
                        nresul = len( data['resultados'] )

                if  data['requerimientos'][0] != "No data":
                        nreqs  = len( data['requerimientos'] )

		histos[0].Fill( float(npasos) )
                histos[1].Fill( float(nverif) )
		histos[2].Fill( float(nresul) )
		histos[3].Fill( float(nreqs)  )
				
		counter += 1
			
print counter

c1 = ROOT.TCanvas("Plot1", "Canvas for plot 1", 90,109,901,644)
c1.SetFillColor(10)
c1.SetGridy()
c1.Divide(2,2)
c1.cd()
gStyle.SetOptStat(111)

for i in range(0,4):
	c1.cd(i+1)
        histos[i].SetLineWidth(2)
        histos[i].SetFillColor(38)
        histos[i].SetFillStyle(3001)
	histos[i].Draw()
