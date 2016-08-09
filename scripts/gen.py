#!/usr/bin/python

import sys
import os


target_dir = sys.argv[1]

d = {}
for f in os.listdir(target_dir):
    n = f.replace('.jpg', '')
    e = n.find('_')
    t = n[0:e]
    if e == -1:
        continue

    if t not in d:
        d[t] = []

    if (not n.endswith('tab') and not n.endswith('merge')):
        d[t].append("R.drawable.%s"%n)

for k in d:
    #ary = str(d[k]).replace("'", "")
    #ary = ary[1:-1]
    #print "add(\"%s\", R.drawable.%s_tab, R.drawable.%s_merge, new int[]{%s});"%(k, k, k, ary)
    print "add(\"%s\", R.drawable.%s_tab, R.drawable.%s_merge, %i);"%(k, k, k, len(d[k]))
