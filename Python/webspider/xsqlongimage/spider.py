#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Usage:
    shows <city> <type>
"""

import csv
import re
import ssl

from bs4 import BeautifulSoup
from docopt import docopt
from prettytable import PrettyTable
from urllib.request import urlopen
import json

url = "http://www.xishiqu.com/"
html = urlopen(url)
bsObj = BeautifulSoup(html, 'html.parser')
# 幻灯片的长图
#re.compile("\Aitem")
longItems = bsObj.find("div",{"class":"main-carousel carousel slide"})
longItems = longItems.findAll("div", {"class": "item"})
longImages = [];
count = 1
changtu = open("./长图/1200*450.txt", "w")

for eachItem in longItems:
    eachImageMap = {}
    eachImage = eachItem.find("img").get("data-original")
    eachImageMap["index"] = count;
    eachImageMap["image"] = eachImage
    j = json.dumps(eachImageMap,ensure_ascii=False)  # ,encoding="UTF-8")
    changtu.write(j)
    changtu.write("\n")
    count+=1

changtu.close()