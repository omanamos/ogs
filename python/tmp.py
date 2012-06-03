#!/usr/bin/python

import sqlite3

conn = sqlite3.connect('../../data/data')
c = conn.cursor()

c.execute("select html from fresh where asin='B0010AYJXI' limit 1")

out = open("out.tmp", 'w')
output = str(c.fetchone()[0].encode('ascii', 'replace'))
print output
out.write(output)
