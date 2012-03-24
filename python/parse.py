#!/usr/bin/python

from BeautifulSoup import BeautifulSoup
from xml.dom.minidom import Document
import os

output = Document()
xml = output.createElement("xml")
output.appendChild(xml)

files=os.listdir('../data/raw')
for fileName in files:
	f = open('../data/raw/' + fileName)
	print "Processing " + fileName + "..."
	soup = BeautifulSoup(f.read())
	
	recipeName = fileName.split("-")[0].strip();
	ingredients = soup.find("div", {"class" : "ingredients"})
	ingredients = ingredients.findAll('li')
	
	recipe = output.createElement("recipe")
	recipe.setAttribute("name", recipeName)
	xml.appendChild(recipe)
	
	for ingredient in ingredients:
		ingr = output.createElement("ingredient")
		recipe.appendChild(ingr)
		ingrText = output.createTextNode(ingredient.text)
		ingr.appendChild(ingrText)
	
	f.close()

f = open("../data/raw.xml", "w")
f.write(output.toprettyxml())