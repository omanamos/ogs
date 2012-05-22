#!/usr/bin/python

import re
import sqlite3

UPDATE_TMPL = 'UPDATE fresh SET base_name = ? , usable = ?, unusable_reason = ? WHERE asin = ?'

conn = sqlite3.connect('../../data/data')
c = conn.cursor()

c.execute('select asin, name from fresh')
match1 = 0
match2 = 0
no_match = 0
updates = []

for row in c:
    name = row[1]
    asin = row[0]
    base_name = ''
    usable = True
    unusable_reason = ''
    if re.match('^.*,.*$', name):
        match1 += 1
        base_name = name.split(',')[0].strip()
        usable = True
    elif re.match('^.*,.*,.*$', name):
        match2 += 1
        base_name = name.split(',')[0].strip()
        usable = True
    else:
        no_match += 1
        base_name = name
        usable = False
        unusable_reason = 'no comma delimited quantity/description'
        #try:
        #    print name
        #except UnicodeEncodeError as e:
        #    print e
    update = [base_name, usable, unusable_reason, asin]
    updates.append(update)

print "UPDATING..."
c.executemany(UPDATE_TMPL, updates);
conn.commit()
    

total = match1 + match2 + no_match
print "%i matches regex 1(%i%%)" % (match1, 100 * match1 / total)
print "%i matches regex 2(%i%%)" % (match2, 100 * match2 / total)
print "%i no-matches (%i%%)" % (no_match, 100 * no_match / total)
