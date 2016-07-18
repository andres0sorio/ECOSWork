import dryscrape
from bs4 import BeautifulSoup
from Utilities import replaceLatin

encoding = 'utf-8'

class Scraper(object):
    """ """
    def __init__(self, session, url):
        self.url = url
        session.visit(url)
        self.response = session.body()
        self.soup = BeautifulSoup(self.response)

    def getName(self):

        try:
            tramite = replaceLatin( self.soup.title.contents[0].encode(encoding) )
            return tramite
        
        except AttributeError:
            return 'No Name'
            
    def searchSteps(self):
        
        result = []

        try:
            titles = self.soup.find_all('div', class_='titcontent')
            for tts in titles:
                step = replaceLatin( tts.find('h3').getText().encode(encoding).strip() )
                result.append(step)
            if len(result) == 0:
                result.append("No data")
            return result
    
        except AttributeError:
            result.append("No data")

        return result
            
    def searchReqs(self):

        result = []
        
        try:
            docs = self.soup.find('ul', id="DOCUMENTO1").find_all('li')
            for doc in docs:
                itemc1 = replaceLatin( doc.contents[0].encode(encoding).strip() )
                itemc2 = replaceLatin( doc.contents[1].contents[0].encode(encoding).strip() )
                require = itemc1 + itemc2
                requerimientos = ",".join(require.split(":"))
                result.append(requerimientos)
            if len(result) == 0:
                result.append("No data")
            return result

        except AttributeError:
            result.append("No data")

        return result

    def searchVer(self):

        result = []
        
        try:
            docs = self.soup.find('ul', id="VERIFICACION_INST1").find_all('li')
            for doc in docs:
                itemc1 = replaceLatin( doc.contents[0].encode(encoding).strip() )
                require = itemc1
                requerimientos = ",".join(require.split("*"))
                result.append(requerimientos)
            if len(result) == 0:
                result.append("No data")
            return result

        except AttributeError:
            result.append("No data")

        return result

    def searchRes(self):

        result = []
        
        try:
            seguimiento = self.soup.find('div', id='segTramite').find_all('h3')

            if len(seguimiento) > 0:
                resultado =  replaceLatin( seguimiento[0].getText().split(':')[1].encode(encoding).strip() )
                result.append(resultado)
            if len(result) == 0:
                result.append("No data")
            return result

        except AttributeError:
            result.append("No data")

        return result
