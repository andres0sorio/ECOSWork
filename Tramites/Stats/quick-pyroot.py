#!/usr/bin/env python

"""

 5 column data format: town, population estimates

"""

import ROOT
from optparse import OptionParser
from ROOT import gStyle
from ROOT import TLine
from ROOT import TPaveText
from ROOT import TLatex
import locale

#------------------------------------------------------------------
parser = OptionParser()
parser.add_option("-i", type = "string", dest="input",
                  help="Input file with results", metavar="input" )

(options, args) = parser.parse_args()

if options.input is None:
        parser.error("please give an input")
#------------------------------------------------------------------

filename = options.input

h1 = ROOT.TH1F("Exp1","", 10, 0.0, 50.0)
h2 = ROOT.TH1F("Exp2","", 10, 0.0, 50.0)

group1 = []
group2 = []
group3 = []
group4 = []

sum = 0.0
sumg1 = 0.0
sumg2 = 0.0
sumg3 = 0.0
sumg4 = 0.0

add_2016 = False

with open(filename) as inputfile:
    for line in inputfile:
        data = line[:-1].split(',')
        pop = float( data[4] )
	h1.Fill( float(data[4]))
	h2.Fill( float(data[3]))
	sum += float(data[4])
	
        if pop <= 5.0:
                group1.append(data[0])
		sumg1 += pop
        elif pop > 5.0 and pop <= 10.0:
                group2.append(data[0])
		sumg2 += pop
        elif pop > 10.0 and pop <= 15.0:
                group3.append(data[0])
		sumg3 += pop
        elif pop > 15.0:
                group4.append(data[0])
		sumg4 += pop

c1 = ROOT.TCanvas("Plot1", "Canvas for plot 1", 385,103,607,452)
c1.SetFillColor(10)
c1.SetGridy()
c1.cd()
gStyle.SetOptStat(0)

h1.SetFillColor(5)
h1.GetXaxis().SetTitle("N#acute{u}mero habitantes [x1000]")
h1.GetXaxis().CenterTitle(True)
h1.GetXaxis().SetLabelFont(42)
h1.GetXaxis().SetLabelSize(0.035)
h1.GetXaxis().SetTitleSize(0.05)
h1.GetXaxis().SetTitleOffset(0.88)
h1.GetXaxis().SetTitleFont(42)
h1.GetYaxis().SetTitle("N#acute{u}mero de Municipios")
h1.GetYaxis().CenterTitle(True)
h1.GetYaxis().SetLabelFont(42)
h1.GetYaxis().SetLabelSize(0.035)
h1.GetYaxis().SetTitleSize(0.05)
h1.GetYaxis().SetTitleOffset(0.98)
h1.GetYaxis().SetTitleFont(42)

h1.Draw()
if add_2016:
	h2.Draw("SAME")

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

pt = TPaveText(0.15,0.9245775,0.85,0.995,"blNDC")
pt.SetName("titleT")
pt.SetBorderSize(0)
pt.SetFillColor(0)
pt.SetFillStyle(0)
pt.SetTextFont(42)
pt.SetTextSize(0.04225352)
AText = pt.AddText("Distribuci#acute{o}n Poblaci#acute{o}n municipios Cundinamarca < 50.000, 2017 (DANE)")
pt.Draw()
c1.Modified()
c1.cd()

tex1 = TLatex(1.1, 27.0,"g1")
tex1.SetLineWidth(2)
tex1.Draw()

tex2 = TLatex(6.1, 27.0,"g2")
tex2.SetLineWidth(2)
tex2.Draw()

tex3 = TLatex(11.1, 27.0,"g3")
tex3.SetLineWidth(2)
tex3.Draw()

tex4 = TLatex(16.1, 27.0,"g4")
tex4.SetLineWidth(2)
tex4.Draw()

locale.setlocale(locale.LC_ALL, 'en_US.utf8')
sumform = locale.format("%d", sum*1000, grouping=True)

print sum, h1.Integral("width")

tex5 = TLatex(30.0, 15.2,"Total: " + str(sumform))
tex5.SetTextFont(42)
tex5.SetLineWidth(2)
tex5.Draw()

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

print sumg1, sumg2, sumg3, sumg4
