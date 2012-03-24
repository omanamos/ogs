#!/usr/bin/python

import sys

input = open(sys.argv[1], 'r')
output = open(sys.argv[2], 'w')

for line in input:
	if line.startswith('http') or line.startswith('Status: '):
		output.write(line)
