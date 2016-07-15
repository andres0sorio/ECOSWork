from selenium import webdriver
from urllib2 import urlopen

url = 'https://www.sivirtual.gov.co/memoficha-tramite/-/tramite/T30667'
file_name = 'tramite.txt'

conn = urlopen(url)
data = conn.read()
conn.close()

file = open(file_name,'wt')
file.write(data)
file.close()

browser = webdriver.Firefox()
browser.get(file_name)
html = browser.page_source
browser.quit()
browser.get('http://seleniumhq.org/')
