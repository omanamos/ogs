#!/usr/bin/python

import pprint
import sys

f = open(sys.argv[1])

hash = {}
fetchedProducts = []
ones = 0
twos = 0
productCount = 0
productFetchedCount = 0
others = 0

lastK = None
lastIsProduct = False

for line in f:
	if line.startswith('http'):
		url = line.split('\t')[0]
		hash[url] = 0
		lastK = url
                lastIsProduct = False
                if 'product' in url:
                        lastIsProduct = True
                        fetchedProducts.append(url)
                        productCount += 1
	elif line.startswith('Status: '):
		if '1' in line:
			ones += 1
			hash[lastK] = 1
		elif '2' in line:
			twos += 1
			hash[lastK] = 2
                        if lastIsProduct:
                                productFetchedCount += 1
		else:
			others += 1

print "total urls = " + str(len(hash.keys()))
print "total product urls = " + str(productCount)
print "product urls fetched = " + str(productFetchedCount)
print "non-product urls fetched = " + str(twos - productFetchedCount)
print ""
print "total unfetched urls = " + str(ones)
print "total fetched urls = " + str(twos)
print "missing urls = " + str(others)
