#!/usr/bin/python

import re
import sqlite3

from BeautifulSoup import BeautifulSoup

SELECT_TMPL = '''SELECT asin, html FROM fresh;'''
INSERT_TMPL = '''INSERT INTO fresh_categories VALUES (?,?,?)'''

conn = sqlite3.connect('../../data/data')
c = conn.cursor()
c.execute('''DELETE FROM fresh_categories where 1''')
conn.commit()

def loadDatabase(asin, categories):
    zipped = zip([asin] * len(categories), categories, \
    reversed(range(len(categories))))
    c.executemany(INSERT_TMPL, zipped)
    conn.commit()

def parseCategories(asin, soup):
    category_parent_node = soup.find('div', {'class': 'productSims'})
    if category_parent_node != None:
        categories = category_parent_node.findAll('span')
        categories = map(lambda x: x.text, categories)[:(len(categories)-1)]
        loadDatabase(asin, categories)

cs = conn.cursor()
cs.execute(SELECT_TMPL)
for row in cs:
    asin = row[0]
    input = row[1]
    soup = BeautifulSoup(input.encode('ascii', 'replace'))
    parseCategories(asin, soup)
