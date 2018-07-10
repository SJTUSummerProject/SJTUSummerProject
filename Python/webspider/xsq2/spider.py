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

try:
    _create_unverified_https_context = ssl._create_unverified_context
except AttributeError:
    # Legacy Python that doesn't verify HTTPS certificates by default
    pass
else:
    # Handle target environment that doesn't support HTTPS verification
    ssl._create_default_https_context = _create_unverified_https_context

# sys.path.append('/home/han/PycharmProjects/TinyPythonProject/Beginner/shows')
from cities import cities

# 大麦网找活动
DAMAI_BASE_URL = "https://www.damai.cn/projectlist.do"
# ERR MSG
QUERY_DAYS_INVALID = 'Invalid days.'
CITY_NOT_FOUND = 'Sorry, your city is not supported.'
SHOW_NOT_FOUND = 'No result.'

# 活动类型
SHOW_TYPES = {
    '演唱会': {'mcid': 1, 'ccid': ''},
    '流行': {'mcid': 1, 'ccid': '9'},
    '摇滚': {'mcid': 1, 'ccid': '10'},
    '民族': {'mcid': 1, 'ccid': '11'},
    '音乐节': {'mcid': 1, 'ccid': '12'},

    '音乐会': {'mcid': 2, 'ccid': ''},

    '话剧歌剧': {'mcid': 3, 'ccid': ''},
    '话剧': {'mcid': 3, 'ccid': 19},
    '歌剧': {'mcid': 3, 'ccid': 20},
    '歌舞剧': {'mcid': 3, 'ccid': 21},
    '音乐剧': {'mcid': 3, 'ccid': 22},
    '儿童剧': {'mcid': 3, 'ccid': 23},

    '舞蹈': {'mcid': 4, 'ccid': '24'},
    '芭蕾': {'mcid': 4, 'ccid': '25'},
    '舞剧': {'mcid': 4, 'ccid': '26'},

    '相声': {'mcid': 5, 'ccid': 27},
    '魔术': {'mcid': 5, 'ccid': 28},
    '马戏': {'mcid': 5, 'ccid': 29},
    '杂技': {'mcid': 5, 'ccid': 30},
    '戏曲': {'mcid': 5, 'ccid': 31},

    '比赛': {'mcid': 6, 'ccid': ''},
}


# 爬title
def getTitle(html):
    titleList = re.findall(
        r'<p class="img"><a href=".*?".*?target="_blank"><img.*?src=".*?".*?alt=".*?".*?title="(.*?)" /></a></p>', html,
        re.S)
    newTitleList = []
    for index, item in enumerate(titleList):
        if item.find("js") == -1 and item.find("css") == -1 and item.find("dale") == -1 and item.find(
                "icon") == -1:
            newTitleList.append(item)
    return newTitleList


# 爬title
def getDetial(html):
    detialList = re.findall(
        r'<p class="img"><a href="(.*?)" target="_blank">', html, re.S)
    newDetialList = []
    for index, item in enumerate(detialList):
        newDetialList.append(item)
    return newDetialList


# 爬图片链接
def getImg(html):
    imgList = re.findall(
        r'<p class="img"><a href=".*?".*?target="_blank"><img.*?src="(.*?)".*?alt=".*?".*?title=".*?" /></a></p>', html,
        re.S)
    newImgList = []
    for index, item in enumerate(imgList):
        if item.find("js") == -1 and item.find("css") == -1 and item.find("dale") == -1 and item.find(
                "icon") == -1:
            newImgList.append(item)
    return newImgList


# 爬时间
def getTime(html):
    timeList = re.findall(r'<p class="mt5">.*?时间：(.*?)<span.*?class="ml20">', html, re.S)
    newTimeList = []
    for index, item in enumerate(timeList):
        newTimeList.append(item)
    return newTimeList


# 爬场馆
def getPlace(html):
    placeList = re.findall(r'场馆：<a href=".*?" target="_blank">(.*?)</a>.*?</span>', html, re.S)
    newPlaceList = []
    for index, item in enumerate(placeList):
        newPlaceList.append(item)
    return newPlaceList


# 爬票价
def getPrice(html):
    priceList = re.findall(r'<span class="price-sort">(.*?)</span>', html, re.S)
    newPriceList = []
    for index, item in enumerate(priceList):
        newPriceList.append(item)
    return newPriceList


# 爬状态
def getStatus(html):
    statusList = re.findall(r'<p>状态: (.*?)</p>', html, re.S)
    newStatusList = []
    for index, item in enumerate(statusList):
        newStatusList.append(item)
    return newStatusList


# 将url转化成html
def getHtml(url):
    try:
        page = urlopen(url)
        html = page.read()
    except Exception as e:
        print("failed to geturl:", e)
        return ""
    else:
        return html


# 将获取的信息进行保存
def saveInfo(infoList):
    with open('/home/han/PycharmProjects/TinyPythonProject/Beginner/shows/shows_scraper.csv', 'w+', newline='',
              encoding='utf-8') as fp:
        a = csv.writer(fp, delimiter=',')  # delimiter的意思是插入到csv文件中的一行记录以它分隔开
        a.writerow(['活 动', '活动链接', '图 片', '时 间', '场 馆', '价 格', '状 态'])
        a.writerows(infoList)
        print('保存完毕')


# 按格式输出
def pretty_print(infoList):
    pt = PrettyTable()
    pt._set_field_names(['活 动', '活动链接', '图 片', '时 间', '场 馆', '价 格', '状 态'])
    for info in infoList:
        pt.add_row(info)
    print(pt)


# 初始化
titles = []
details = []
imgs = []
times = []
places = []
prices = []
status = []

allInfo = []

arguments = docopt(__doc__)
city = cities.get(arguments['<city>'])
type = SHOW_TYPES.get(arguments['<type>'])
# url = ('https://www.damai.cn/projectlist.do?cityID={}&mcid={}&ccid={}').format(
#     city, type['mcid'], type['ccid']
# )

nodesImages = []
nodesInfos = []
PageHtml = []
ArrivedHtml = {}
multipage = 0;
for i in cities:
    print(cities[i])

    url = "http://www.xishiqu.com/"+cities[i]+"/cate/yanchanghui/"
    html = urlopen(url)
    bsObj = BeautifulSoup(html, 'html.parser')
    #有没有票？
    IsEmpty = bsObj.find("p",{"class":"empty"})
    if(IsEmpty!=None):
        yanchanghui = open("./演唱会信息/无/"+i+"演唱会信息.txt", "w")
        yanchanghui.write("None")
        yanchanghui.close();
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
    yanchanghui = open("./演唱会信息/有/"+i+"演唱会信息.txt", "w")
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

            nodesInfos.append(EachDict);
            Dicts[count] = EachDict
            count += 1
        j = json.dumps(Dicts, ensure_ascii=False)  # , encoding="UTF-8", ensure_ascii=False)
        yanchanghui.write(j);
        yanchanghui.write('\n')
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

            nodesInfos.append(EachDict);
            Dicts[count] = EachDict
            count += 1
        j = json.dumps(Dicts, ensure_ascii=False)  # , encoding="UTF-8", ensure_ascii=False)
        yanchanghui.write(j);
        yanchanghui.write('\n')

    yanchanghui.close();