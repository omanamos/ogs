#!/usr/bin/python

import os
import sqlite3

conn = sqlite3.connect(os.environ["CRAWL_SQLITE"])
c = conn.cursor()

for level in range(3):
    c.execute("select category, count(category) as cnt from fresh_categories \
where level = ? group by category order by cnt desc", [level])
    cnts = [0] * 10
    asin_cnt = 0
    cat_cnt = 0
    total_asins = 0
    
    output = open("../output/category_count_level_" + str(level), "w");
    
    for row in c:
        # write "category | count" to disk
        output.write("%s | %i\n" % (row[0], row[1]))
        if row[1] <= 10:
            cnts[row[1] - 1] += 1
            asin_cnt += row[1]
            cat_cnt += 1

    print "level = " + str(level)
    print "asin_cnt = " + str(asin_cnt)
    print "cat_cnt = " + str(cat_cnt)
    print cnts
    print

c.execute("select distinct base_name, c0.category, c1.category, c2.category \
from fresh f join fresh_categories c0 on f.asin = c0.asin join \
fresh_categories c1 on f.asin = c1.asin join fresh_categories c2 on f.asin = \
c2.asin where c0.level = 0 and c1.level = 1 and c2.level = 2")

cnts = 0
for row in c:
    if row[3] == None or len(row[3]) == 0 or row[3] == "null":
        cnts += 1

print cnts
