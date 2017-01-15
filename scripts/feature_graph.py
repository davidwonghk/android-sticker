import sys, os
import os.path
import random
from PIL import Image, ImageFilter

WIDTH = 1024
HEIGHT = 500
srcDir = sys.argv[1]



def transplant(im) :
	img = im.convert("RGBA")
	pixdata = img.load()
	bg = pixdata[0, 0]

	for y in xrange(img.size[1]):
	    for x in xrange(img.size[0]):
	        if pixdata[x, y] == bg:
	            pixdata[x, y] = (255, 255, 255, 0)

	return img


#dropRate: in pencentage
def generateFeatureGraphic():

	featureGraphic = Image.new("RGBA", (WIDTH, HEIGHT), "white")

	for imgFile in os.listdir(srcDir):
		if (False == imgFile.endswith('.gif')):
			continue

		imgPath = os.path.join(srcDir, imgFile)
		im = Image.open(imgPath)

		size = WIDTH/4, HEIGHT/4
		deg = random.randint(0, 360)

		im.thumbnail(size)
		im = transplant(im)
		im = im.rotate(deg)

		x = random.randint(0,WIDTH-1) - size[0]/2
		y = random.randint(0,HEIGHT-1) -size[1]/2
		featureGraphic.paste(im, (x, y), im)

	return featureGraphic


for i in range(0, 100):
	filename = './fg/featureGraphic%i.jpg'%(i)
	generateFeatureGraphic().save(filename, optimize=True,quality=95)
	print "generated %s"%filename
