#!/usr/bin/python

import os
import re
import sqlite3

from BeautifulSoup import BeautifulSoup

SQL_TMPL = '''INSERT INTO fresh VALUES (?,?,?,?,?)'''

conn = sqlite3.connect(os.environ["CRAWL_SQLITE"])
c = conn.cursor()

def load_db(data):
    print "INSERTING... HTML LEN=" + str(len(data['html']))
    c.execute(SQL_TMPL, (data['asin'], data['name'], len(data['html']), \
                             data['html'], data['dump_locator']))
    conn.commit()

def parse_recno(line):
    return line.split(':: ')[1].strip()

def parse_asin_from_url(url):
    return url.split('asin=')[1][0:10]


dir_list = os.listdir(os.environ["DUMP"])
prev_line = ""
sql_data = dict()
docText = ""
search_for_start_flag = False
search_for_end_flag = False
seen_asins = set()
finished = set()
try:
    fff = open('finished.out', 'r')
    finished = set(fff.readlines())
except IOError as e:
    print "No Folders Finished, Starting From Scratch"

fff = open('finished.out', 'a')

for folder in dir_list:
    if (folder + '\n') not in finished:
        print "FOLDER: " + folder
        f = open(os.environ["DUMP"] + "/" + folder + "/dump")
        for line in f:
            if search_for_end_flag:
                if line.startswith("Recno:: "):
                    search_for_end_flag = False
                    soup = BeautifulSoup(docText)
                    sql_data['html'] = soup.prettify().decode('utf-8')
                    sql_data['name'] = soup.title.text.split('|')[0].strip()
                    load_db(sql_data)
                    docText = ""
                    sql_data = dict()
                else:
                    docText += line
            elif search_for_start_flag and re.match('^Content:$', line):
                search_for_start_flag = False
                search_for_end_flag = True
            elif line.startswith("URL:: http://fresh.amazon.com/product"):
                recno = parse_recno(prev_line)
                asin = parse_asin_from_url(line)
                print "RECNO: " + recno
                if asin not in seen_asins:
                    print "UNSEEN ASIN: " + asin
                    sql_data['dump_locator'] = folder + ' ' + recno
                    sql_data['asin'] = asin
                    search_for_start_flag = True
                    seen_asins.add(asin)
            prev_line = line
        fff.write(folder + '\n')
    else:
        print "FINISHED FOLDER: " + folder

c.close()
