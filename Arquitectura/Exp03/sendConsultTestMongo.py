from JsonEpisodeHelper import JsonEpisodeHelper

import json
import urllib2
import time
import numpy
import pylab
import random
import unittest
from time import sleep
import ast

cedula = 703927262
url = 'http://localhost:4567/api/episode/get'
pointsP1 = []
output = open('consult_experiment.dat', 'w')

def getJson(url,data):

    req = urllib2.Request(url)
    req.add_header('Content-Type', 'application/json')
    start = time.time()
    response = urllib2.urlopen(req, data)
    end = time.time()
    ##jdata = json.loads( response.read().decode("utf-8") )
    response_json = json.load( response )
    jdata = ast.literal_eval(response_json)

    print len(jdata)
   

def runLatencyExperiment(ntimes):

    for i in range(0,ntimes):
        data = '{ cedula : ' + str(cedula) + '}'
        value = getJson(url,data)
        pointsP1.append(value)
        print i
        sleep(0.050)
        points = str(value)
        output.write(points + '\n')

runLatencyExperiment(1)

output.close()
