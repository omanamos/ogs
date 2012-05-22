#!/usr/bin/python

from BeautifulSoup import BeautifulSoup
from xml.dom.minidom import Document

f = open("../data/raw.xml", "r")
soup = BeautifulSoup(f.read())
