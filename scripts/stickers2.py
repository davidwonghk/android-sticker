#!/bin/python

import sys
import os
import math
import logging
from PIL import Image, ImageDraw

SIZE = (256,256)
ICON_SIZE = 64,64
MARGIN = 15 
WIDTH = 4
TYPE = 'jpeg'


def create_if_empty(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)


def create_merge_image(target_dir, group_name, list, width):
    height = math.ceil(len(list) / float(width))
    height = int(height)
    ms = SIZE[0]

    merge_width = ms * width + (width+1)*MARGIN
    merge_height = ms * height + (height+1)*MARGIN
    merge_im = Image.new('RGB', (merge_width, merge_height), (255, 255, 255, 255))
    f = 0
    #draw = ImageDraw.Draw(merge_im)

    for fm in list:
        im = Image.open(fm)
        im = im.resize(SIZE, Image.ANTIALIAS)

        i = (f % width) * (ms+MARGIN) + MARGIN
        j = (f / width) * (ms+MARGIN) + MARGIN
        merge_im.paste(im, (i,j))
        f = f + 1

        merge_path = os.path.join(target_dir, "%s_merge.%s"%(group_name, TYPE))
        merge_im.save(merge_path, optimize=False,quality=100)




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
            images.append(old_path)

    create_merge_image(target_dir, dir_only, images, WIDTH)
