#!/usr/bin/python

import dryscrape
from bs4 import BeautifulSoup
import sys
from Utilities import replaceLatin

#------------------------------------------------------------------

municipios = []
municipios.append(sys.argv[1])
start = "start="
group = sys.argv[2]

mydriver = dryscrape.driver.webkit.Driver()
session = dryscrape.Session(mydriver)
session.set_attribute('auto_load_images', False)

prefix = './data/' + group + '/'

depto = '+Cundinamarca'

for mn in municipios:

    name = mn.split()
    full_name = '_'.join(name)
    filename = prefix + full_name + '.csv'
    output = open(filename,'w')
    counter = 0
    tramites = {}
    mnflat = replaceLatin( mn )

    for idx in range(0,100,10):
        url = 'https://www.sivirtual.gov.co/resultadosbusqueda?q=' + mnflat + depto + '&' + start + str(idx)
        print url
        repeated = 0
        session.visit(url)
        response = session.body()
        soup = BeautifulSoup(response)

        results = soup.find('div', id='divContenedorResultados')

        links = results.findAll('a')

        for link in links:
            if link.has_attr('rank'):
                if ('href' in dict(link.attrs)):
                    if link['href'].find('tramite') > 0:
                        href = link['href'] 
                        trm = href.split('/')[-1]

                        if trm in tramites:
                            repeated += 1
                            break
                        else:
                            info = []
                            info.append(mn)
                            info.append(trm)
                            info.append(href)
                            outinfo = ','.join(info) + '\n'
                            output.write( outinfo )
                            tramites[trm] = href
                            
        if repeated > 0 :
            break

    output.close()

