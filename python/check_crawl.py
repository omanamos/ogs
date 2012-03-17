#!/usr/bin/python

import pprint

f = open('../data/crawl/check3')

hash = {}
ones = 0
twos = 0
others = 0

lastK = None

for line in f:
	if line.startswith('http'):
		url = line.split('\t')[0]
		hash[url] = 0
		lastK = url
	elif line.startswith('Status: '):
		if '1' in line:
			ones += 1
			hash[lastK] = 1
		elif '2' in line:
			twos += 1
			hash[lastK] = 2
		else:
			others += 1

out1 = {}
out2 = {}
productCount = 0
oneCount = 0
for k, v in hash.iteritems():
	if v > 1:
		out1[k] = v
	elif 'product' in k and k not in out1 and v == 2:
		out2[k] = v
		productCount += 1

print "##################################### OUT 1 ################"
# pprint.pprint(out1)

print "##################################### OUT 2 ################"
#pprint.pprint(out2)
print "###################### product count = " + str(productCount)
print "###################### one count = " + str(ones)
print "###################### two count = " + str(twos)
print "###################### other count = " + str(others)