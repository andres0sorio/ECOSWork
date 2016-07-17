#!/usr/bin/python

import dryscrape
from bs4 import BeautifulSoup
from Utilities import replaceLatin

mydriver = dryscrape.driver.webkit.Driver()
mydriver.set_timeout(10000)
session = dryscrape.Session(mydriver)

url = 'https://www.sivirtual.gov.co/memoficha-tramite/-/tramite/T34188'
url = 'https://www.sivirtual.gov.co/memoficha-tramite/-/tramite/T31463'
url = 'https://www.sivirtual.gov.co/memoficha-tramite/-/tramite/T29684'
url = 'https://www.sivirtual.gov.co/memoficha-tramite/-/tramite/T33703'

session.visit(url)
response = session.body()
soup = BeautifulSoup(response)
encoding = 'utf-8'

tramite = soup.title.contents[0].encode(encoding)
municipio = soup.find('h2',id='nombreEntidad').getText().encode(encoding)
print tramite, municipio

try:
    docs = soup.find('ul', id="DOCUMENTO1").find_all('li')

    for doc in docs:
        itemc1 = doc.contents[0].encode(encoding).strip()
        itemc2 = doc.contents[1].contents[0].encode(encoding).strip()
        require = itemc1 + itemc2
        requerimientos = ",".join(require.split(":"))
        print requerimientos
except AttributeError:
    print "Error> No data"

try:

    titles = soup.find_all('div', class_='titcontent')
    
    for tts in titles:
        print tts.find('h3').contents[0]
except AttributeError:
    print "Error> No data"

    
try:

    seguimiento = soup.find('div', id='segTramite').find_all('h3')

    if len(seguimiento) > 0:
        resultado =  seguimiento[0].getText().split(':')[1].strip()
        print resultado
    
except AttributeError:
    print "Error> No data"
    

result = []

try:
            docs = soup.find('ul', id="VERIFICACION_INST1").find_all('li')
            for doc in docs:
                itemc1 = replaceLatin( doc.contents[0].encode(encoding).strip() )
                require = itemc1
                requerimientos = ",".join(require.split("*"))
                result.append(requerimientos)
            if len(result) == 0:
                result.append('No data')
            print result

except AttributeError:
    result.append('No data')
    print result
