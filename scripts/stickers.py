#!/bin/python

import sys
import os
import math
import logging
from PIL import Image, ImageDraw

SIZE = 128, 128
ICON_SIZE = 32, 32
MARGIN = 15
WIDTH = 4
TYPE = 'jpg'


def create_if_empty(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)


def create_thumbnail(old_path, new_path, size):
    try:
        print "trim %s to %s"%(old_path, new_path)
        im = Image.open(old_path)
        im.thumbnail(size, Image.ANTIALIAS)
        im.save(new_path, optimize=True,quality=80)
    except IOError:
        logging.error( "cannot create thumbnail for '%s'" % old_path)

def create_icon(old_path, new_path, size):
    try:
        im = Image.open(old_path)
        im.thumbnail(size, Image.ANTIALIAS)
        im.save(new_path, optimize=True,quality=40)
    except IOError:
        logging.error ("cannot create tab icon for '%s'" % old_path)

def create_merge_image(target_dir, group_name, list, width):
    height = math.ceil(len(list) / float(width))
    height = int(height)
    ms = SIZE[0]/2

    merge_width = ms * width + (width+1)*MARGIN
    merge_height = ms * height + (height+1)*MARGIN
    merge_im = Image.new('RGB', (merge_width, merge_height), (255, 255, 255, 255))
    f = 0
    #draw = ImageDraw.Draw(merge_im)

    for fm in list:
        im = Image.open(fm)
        im.thumbnail((ms, ms))

        i = (f % width) * (ms+MARGIN) + MARGIN
        j = (f / width) * (ms+MARGIN) + MARGIN
        merge_im.paste(im, (i,j))
        f = f + 1
        #if i == MARGIN:
        #    draw.line((0,j, merge_width, j), fill=0, width=1)
        #if j == MARGIN:
        #    draw.line((i,0, i, merge_height), fill=0, width=1)

        merge_path = os.path.join(target_dir, "%s_merge.%s"%(group_name, TYPE))
        merge_im.save(merge_path, optimize=True,quality=95)




###main###
src_dir = sys.argv[1]
target_dir = sys.argv[2]


create_if_empty(target_dir)

for d, _, _ in os.walk(src_dir):
    if d == src_dir:
        continue

    #if not d.endswith('blue'):
    #    continue

    dir_only = d[len(src_dir)+1 : ]
    i = 0
    images = []
    for f in os.listdir(d):
        old_path = os.path.join(d, f)
        if os.path.isfile(old_path):
            new_path = os.path.join(target_dir, "%s_%i.%s"%(dir_only, i, TYPE))
            create_thumbnail(old_path, new_path, SIZE)

            if i==0:
                #create the tab icon
                tab_path = os.path.join(target_dir, "%s_tab.%s"%(dir_only, TYPE))
                create_icon(old_path, tab_path, ICON_SIZE)

            i += 1

            images.append(new_path)

    create_merge_image(target_dir, dir_only, images, WIDTH)
