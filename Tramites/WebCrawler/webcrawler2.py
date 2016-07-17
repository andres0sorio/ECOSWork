#!/usr/bin/python

import dryscrape
from bs4 import BeautifulSoup
from time import sleep
from Tramites import Tramite 
from Scraper import Scraper
import os,sys
from Utilities import replaceLatin
import logging
import json
#------------------------------------------------------------------
logging.basicConfig(filename='logs/webcrawler2.log',level=logging.DEBUG,format='%(asctime)s %(message)s')
logging.info('webcrawler2')

mydriver = dryscrape.driver.webkit.Driver()
mydriver.set_timeout(50000)
session = dryscrape.Session(mydriver)
session.set_attribute('auto_load_images', False)

trmObj = Tramite()
trmObj.setMunicipio(replaceLatin(sys.argv[1]))
trmObj.setCodigo   (sys.argv[2])
trmObj.setUrl      (sys.argv[3])
trmObj.setGrupo    (sys.argv[4])

success = False

prefix = './jsondata/' + sys.argv[4] + '/'

json_output = open( prefix + 'jsondata_' + sys.argv[4] + '.json', 'a')

for attempt in range(1,30):

    scp = Scraper( session, trmObj.getUrl() )

    pasos = scp.searchSteps()
    trmObj.setPasos(pasos)

    if len(pasos) == 1 and pasos[0] == 'No data':
        logging.info('Failed attempt ' + str(attempt) )
        sleep(0.200)
        continue
    
    nombre = scp.getName()
    trmObj.setNombre(nombre)
        
    result = scp.searchReqs()
    trmObj.setRequerimientos(result)

    result = scp.searchVer()
    trmObj.setVerificacion(result)
    
    result = scp.searchRes()
    trmObj.setResultados(result)

    output = json.dumps(trmObj.__dict__)
    json_output.write( output + '\n')
    #json.dump( trmObj.__dict__, json_output )
    
    if len(pasos) != 1 and pasos[0] != 'No data':
        logging.info('Success at ' + str(attempt) )
        success = True
        break

if success:
    print "Success"
else:
    print "Failed"

json_output.close()
