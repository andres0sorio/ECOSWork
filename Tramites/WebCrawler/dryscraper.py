import dryscrape
from bs4 import BeautifulSoup

sess = dryscrape.Session()
url = 'https://www.sivirtual.gov.co/memoficha-tramite/-/tramite/T30667'
url = 'https://www.sivirtual.gov.co/memoficha-tramite/-/tramite/T34188'
sess.visit(url)
response = sess.body()
soup = BeautifulSoup(response)

tramite = soup.title.contents[0].encode('utf-8')
municipio = soup.find('h2',id='nombreEntidad').contents[0].encode('utf-8')

docs = soup.find('ul', id="DOCUMENTO1").find_all('li')

print tramite, municipio

for doc in docs:
    itemc1 = doc.contents[0].encode('utf-8').strip()
    itemc2 = doc.contents[1].contents[0].encode('utf-8').strip()
    require = itemc1 + itemc2
    requerimientos = ",".join(require.split(":"))
    print requerimientos

    
