import dryscrape
from bs4 import BeautifulSoup

sess = dryscrape.Session()
url = 'https://www.sivirtual.gov.co/resultadosbusqueda?q=palestina caldas&start=10'
sess.visit(url)
response = sess.body()
soup = BeautifulSoup(response)

results = soup.find('div', id='divContenedorResultados')

links = results.findAll('a')

for link in links:
    if link.has_attr('rank'):
        if ('href' in dict(link.attrs)):
            if link['href'].find('tramite') > 0:
                print link['href']
                

    
