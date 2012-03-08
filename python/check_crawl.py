#!/usr/bin/python

import pprint

f = open('../data/crawl/check2')

hash = {}

for line in f:
	if line.startswith('http'):
		url = line.split('\t')[0]
		if url in hash:
			hash[url] += 1
		else:
			hash[url] = 1

out1 = {}
out2 = {}
productCount = 0
for k, v in hash.iteritems():
	if v > 1:
		out1[k] = v
	elif 'product' in k:
		out2[k] = v
		productCount += 1

print "##################################### OUT 1 ################"
pprint.pprint(out1)

print "##################################### OUT 2 ################"
#pprint.pprint(out2)
print "###################### product count = " + str(productCount)