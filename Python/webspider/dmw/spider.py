
#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Usage:
    shows <city> <type>
"""
import os
import re
import csv
import sys

from prettytable import PrettyTable
from urllib.request import urlopen
from bs4 import BeautifulSoup
from docopt import docopt

sys.path.append('/Library/Frameworks/Python.framework/Versions/3.6/lib/python3.6/lib／python3.6/site-packages')

import 


sys.path.append('/Users/sky/Desktop/软件工程导论/SJTUSummerProject/SJTUSummerProject/Python/webspider/dmw/spider')
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
url = ('https://www.damai.cn/projectlist.do?cityID={}&mcid={}&ccid={}').format(
    city, type['mcid'], type['ccid']
)
html = urlopen(url)
bsObj = BeautifulSoup(html, 'html.parser')
page_num_text = bsObj.findAll("span", {"class": "ml10"})[0].get_text()
page_num = int(page_num_text[page_num_text.index('共') + 1:page_num_text.index('页')])
print("共%d页" % page_num)  # 得到活动一共多少页
for page in range(1, page_num + 1):
    url = ('https://www.damai.cn/projectlist.do?cityID={}&mcid={}&ccid={}&pageIndex={}').format(
        city, type['mcid'], type['ccid'], page
    )
    print("page:%d,url:%s" % (page, url))
    html = getHtml(url).decode("UTF-8")
    if (html == ''):
        titles.extend('none')
        details.extend('none')
        imgs.extend('none')
        times.extend('none')
        places.extend('none')
        prices.extend('none')
        status.extend('none')
    else:
        titles.extend(getTitle(html))
        details.extend(getDetial(html))
        imgs.extend(getImg(html))
        times.extend(getTime(html))
        places.extend(getPlace(html))
        prices.extend(getPrice(html))
        status.extend(getStatus(html))

print(len(titles))
print(len(details))
print(len(imgs))
print(len(times))
print(len(places))
print(len(prices))
print(len(status))

for i in range(0, len(titles)):
    tmp = []
    tmp.append(titles[i])
    tmp.append('https:' + details[i])
    tmp.append('https:' + imgs[i])
    tmp.append(times[i])
    tmp.append(places[i])
    tmp.append(prices[i])
    tmp.append(status[i])
    allInfo.append(tmp)

saveInfo(allInfo)  # 保存为csv格式文件
pretty_print(allInfo)  # prettytable格式整理打印
