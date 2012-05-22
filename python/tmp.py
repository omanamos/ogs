#!/usr/bin/python

import re
import sqlite3

UPDATE_TMPL = 'UPDATE fresh SET base_name = ?, usable = ?, \
unusable_reason = ? WHERE asin = ?'

conn = sqlite3.connect('../../data/data')
c = conn.cursor()

c.execute(UPDATE_TMPL, ("test1", True, "reason1", "B00099XOCI"))
print c.rowcount

conn.commit()
c.close()