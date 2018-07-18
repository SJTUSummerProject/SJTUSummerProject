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
from cities import cities

nodesImages = []
nodesInfos = []
PageHtml = []
ArrivedHtml = {}
multipage = 0;
for i in cities:
    print(cities[i])

    url = "http://www.xishiqu.com/"+cities[i]+"/cate/xiuxianyule/"
    html = urlopen(url)
    bsObj = BeautifulSoup(html, 'html.parser')
    #有没有票？
    IsEmpty = bsObj.find("p",{"class":"empty"})
    if(IsEmpty!=None):
        gejuhuaju = open("./休闲娱乐/无/"+i+"休闲娱乐信息.txt", "w")
        gejuhuaju.write("None")
        gejuhuaju.close();
        continue

    NodesInXSQ = bsObj.findAll("div", {"class": "nodes"})[0]
    NodesLiTag = NodesInXSQ.findAll('li')

    PageInXSQ = bsObj.find("div",{"class":"pager"})
    text = PageInXSQ.get_text();
    print(PageInXSQ.get_text())

    #有分页
    if(PageInXSQ.get_text()!='\n'):
        PageATags = PageInXSQ.findAll("a",{"href":re.compile("(/cate)")})
        PageFocusTag = PageInXSQ.find("a",{"class":"focus"}).get("href") #获得当前页面
        ArrivedHtml[PageFocusTag]=1
        PageHtml.append(PageFocusTag)
        multipage = 1
    else:
        multipage = 0
    gejuhuaju = open("./休闲娱乐/有/"+i+"休闲娱乐信息.txt", "w")
    count = 1

    while(len(PageHtml)>0):
        if(multipage == 1):
            for PageATag in PageATags:
                TmpHref = PageATag.get("href")
                if (TmpHref in ArrivedHtml):
                    continue
                PageHtml.append(TmpHref)
                ArrivedHtml[TmpHref] = 1
                print(PageATag)

        Dicts = {}
        for EachLiTag in NodesLiTag:
            EachImage = EachLiTag.find("img").get("src")
            EachTitle = EachLiTag.find("h3", {"class": "title"}).find("a").get_text()
            EachBriefIntro = EachLiTag.find("p").get_text()
            EachDate = EachLiTag.find("p", {"class": "date"}).get_text()
            EachVenue = EachLiTag.find("p", {"class": "venue"}).find("a").get_text()

            EachDict = {}
            EachDict["image"] = EachImage
            EachDict["title"] = EachTitle
            EachDict["briefintro"] = EachBriefIntro
            EachDict["date"] = EachDate
            EachDict["venue"] = EachVenue

            EachDetailUrl = "http://www.xishiqu.com"+EachLiTag.find("div",{"class":"thumb"}).find("a").get("href")
            print(EachDetailUrl)
            EachDetailHtml = urlopen(EachDetailUrl)
            EachDetailBsObj = BeautifulSoup(EachDetailHtml, 'html.parser')

            EachDetailDl = EachDetailBsObj.find("div",{"class":"intro-box"}).findAll("dl")
            EachDetailPrice = ""
            for EachDl in  EachDetailDl:
                if(EachDl.find("dt").get_text()=="票面价："):
                    EachDetailPrice = EachDl.find("dd").get_text()
                    EachDict["price"] = EachDl.find("dd").get_text()


            nodesInfos.append(EachDict);
            Dicts[count] = EachDict
            count += 1
        j = json.dumps(Dicts, ensure_ascii=False)  # , encoding="UTF-8", ensure_ascii=False)
        gejuhuaju.write(j);
        gejuhuaju.write('\n')
        if(multipage == 0):
            break
        #获得最前面的页面
        tmpurl = PageHtml.pop(0);
        url = "http://www.xishiqu.com"+tmpurl
        html = urlopen(url)
        bsObj = BeautifulSoup(html, 'html.parser')
        NodesInXSQ = bsObj.findAll("div", {"class": "nodes"})[0]
        NodesLiTag = NodesInXSQ.findAll('li')
        PageInXSQ = bsObj.find("div", {"class": "pager"})
        PageATags = PageInXSQ.findAll("a", {"href": re.compile("(/cate)")})
        PageFocusTag = PageInXSQ.find("a", {"class": "focus"}).get("href")  # 获得当前页面
        if(tmpurl == PageFocusTag):
            tmpurl = PageHtml.pop(0);
            url = "http://www.xishiqu.com" + tmpurl
            html = urlopen(url)
            bsObj = BeautifulSoup(html, 'html.parser')
            NodesInXSQ = bsObj.findAll("div", {"class": "nodes"})[0]
            NodesLiTag = NodesInXSQ.findAll('li')
            PageInXSQ = bsObj.find("div", {"class": "pager"})
            PageATags = PageInXSQ.findAll("a", {"href": re.compile("(/cate)")})
            PageFocusTag = PageInXSQ.find("a", {"class": "focus"}).get("href")  # 获得当前页面
    else:
        Dicts = {}
        for EachLiTag in NodesLiTag:
            EachImage = EachLiTag.find("img").get("src")
            EachTitle = EachLiTag.find("h3", {"class": "title"}).find("a").get_text()
            EachBriefIntro = EachLiTag.find("p").get_text()
            EachDate = EachLiTag.find("p", {"class": "date"}).get_text()
            EachVenue = EachLiTag.find("p", {"class": "venue"}).find("a").get_text()

            EachDict = {}
            EachDict["image"] = EachImage
            EachDict["title"] = EachTitle
            EachDict["briefintro"] = EachBriefIntro
            EachDict["date"] = EachDate
            EachDict["venue"] = EachVenue

            EachDetailUrl = "http://www.xishiqu.com" + EachLiTag.find("div", {"class": "thumb"}).find("a").get("href")
            EachDetailHtml = urlopen(EachDetailUrl)
            EachDetailBsObj = BeautifulSoup(EachDetailHtml, 'html.parser')

            EachDetailDl = EachDetailBsObj.find("div", {"class": "intro-box"}).findAll("dl")
            EachDetailPrice = ""
            for EachDl in EachDetailDl:
                if (EachDl.find("dt").get_text() == "票面价："):
                    EachDetailPrice = EachDl.find("dd").get_text()
                    EachDict["price"] = EachDl.find("dd").get_text()

            nodesInfos.append(EachDict);
            Dicts[count] = EachDict
            count += 1
        j = json.dumps(Dicts, ensure_ascii=False)  # , encoding="UTF-8", ensure_ascii=False)
        gejuhuaju.write(j);
        gejuhuaju.write('\n')

    gejuhuaju.close();