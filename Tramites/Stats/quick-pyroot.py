#!/usr/bin/env python

"""

 5 column data format: town, population estimates

"""

import ROOT
from optparse import OptionParser
from ROOT import gStyle
from ROOT import TLine

#------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")
#------------------------------------------------------------------

filename = options.input

h1 = ROOT.TH1F("Exp1","Poblacion municipios cundinamarca < 50 k, 2017 (DANE)", 10, 0.0, 50.0)

group1 = []
group2 = []
group3 = []
group4 = []

with open(filename) as inputfile:
    for line in inputfile:
        data = line[:-1].split(',')
        pop = float( data[4] )
	h1.Fill( float(data[4]))

        if pop <= 5.0:
                group1.append(data[0])
        elif pop > 5.0 and pop <= 10.0:
                group2.append(data[0])
        elif pop > 10.0 and pop <= 15.0:
                group3.append(data[0])
        elif pop > 15.0:
                group4.append(data[0])
        
c1 = ROOT.TCanvas("Plot1", "Canvas for plot 1", 385,103,607,452)
c1.SetFillColor(10)
c1.SetGridy()
c1.cd()
gStyle.SetOptStat(111)

#c2 = ROOT.TCanvas("Plot2", "Canvas for plot 1", 385,103,607,452)
#c2.SetFillColor(10)
#c2.cd()

h1.SetFillColor(5)
h1.GetXaxis().SetTitle("Numero habitantes [k]")
h1.GetXaxis().CenterTitle(True)
h1.GetXaxis().SetLabelFont(42)
h1.GetXaxis().SetLabelSize(0.035)
h1.GetXaxis().SetTitleSize(0.05)
h1.GetXaxis().SetTitleOffset(0.88)
h1.GetXaxis().SetTitleFont(42)
h1.GetYaxis().SetTitle("")
h1.GetYaxis().CenterTitle(True)
h1.GetYaxis().SetLabelFont(42)
h1.GetYaxis().SetLabelSize(0.035)
h1.GetYaxis().SetTitleSize(0.05)
h1.GetYaxis().SetTitleOffset(0.98)
h1.GetYaxis().SetTitleFont(42)

h1.Draw()

ymax = 34.0

x0 = 5.0
line1 = TLine(x0, 0.0, x0, ymax)
line1.SetLineColor(9)
line1.SetLineStyle(2)
line1.SetLineWidth(2)
line1.Draw()

x0 = 10.0
line2 = TLine(x0, 0.0, x0, ymax)
line2.SetLineColor(9)
line2.SetLineStyle(2)
line2.SetLineWidth(2)
line2.Draw()


x0 = 15.0
line3 = TLine(x0, 0.0, x0, ymax)
line3.SetLineColor(9)
line3.SetLineStyle(2)
line3.SetLineWidth(2)
line3.Draw()
c1.Modified()


c1.Print("population_dist.png")

inputfile.close()

group1_output = open('municipios_grupo1.dat','w')
group2_output = open('municipios_grupo2.dat','w')
group3_output = open('municipios_grupo3.dat','w')
group4_output = open('municipios_grupo4.dat','w')

for mn in group1:
        group1_output.write(mn + '\n')
group1_output.close()

for mn in group2:
        group2_output.write(mn + '\n')
group2_output.close()

for mn in group3:
        group3_output.write(mn + '\n')
group3_output.close()

for mn in group4:
        group4_output.write(mn + '\n')
group4_output.close()
