#!/usr/bin/python

import os

seg_folders = os.listdir(os.environ["CRAWL_DATA"] + "/segments/")
dump_count = 0
tmpl = "%(nh)s/bin/nutch readseg -dump %(cd)s/segments/%(fldr)s \
%(nh)s/dump/%(dc)i -nofetch -nogenerate -noparse -noparsedata -noparsetext"

for folder in seg_folders:
    status = os.system(tmpl % {'cd': os.environ['CRAWL_DATA'],
                               'fldr': folder,
                               'nh': os.environ['NUTCH_HOME'],
                               'dc': dump_count})
    print "XXXXXX: " + str(status)
    if status != 0 and status != 256:
        break;
    dump_count += 1
