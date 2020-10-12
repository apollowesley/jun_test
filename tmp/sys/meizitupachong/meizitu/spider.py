import datetime

from bs4 import BeautifulSoup
import os
from meizitu.download import request
from pymongo import MongoClient

'''
修改抓取地址在最下方get_all
'''


class meizi():
    def __init__(self):
        client = MongoClient()
        db = client['meizitu']
        self.meizitu_collection = db['xinggan']
        self.title = ''
        self.url = ''
        self.img_urls = []

    def get_all(self, url):
        html = request.get(url, 3,3)
        all_page = BeautifulSoup(html.text, 'lxml').find('div', class_="nav-links").find_all('a')[-2].get_text()
        print('开始抓取第 1 页', url)
        self.all_url(url)
        for each in range(2, int(all_page) + 1):
            each_url = url + 'page/' + str(each)
            print('开始抓取第 ' + str(each) + ' 页', each_url)
            self.all_url(each_url)

    def all_url(self, url):
        html = request.get(url, 3)
        all_span = BeautifulSoup(html.text, 'lxml').find('ul', id="pins").find_all('span')
        for span in all_span:
            if span.find('a') == None:
                pass
            else:
                a = span.find('a')
                title = a.get_text()
                self.title = title
                print(u'开始保存：', title)
                path = str(title).replace("?", '_')
                self.mkdir(path)
                href = a['href']
                self.url = href
                if self.meizitu_collection.find_one({'主题页面': href}):
                    print('这个页面已经爬取过了')
                else:
                    self.html(href)

    def html(self, url):
        html = request.get(url, 3)
        max_span = BeautifulSoup(html.text, 'lxml').find('div', class_='pagenavi').find_all('span')[-2].get_text()
        page_num = 0
        for page in range(1, int(max_span) + 1):
            page_num = page_num + 1
            page_url = url + '/' + str(page)
            self.img(page_url, max_span, page_num)

    def img(self, url, max_span, page_num):
        img_html = request.get(url, 3)
        img_url = BeautifulSoup(img_html.text, 'lxml').find('div', class_='main-image').find('img')['src']
        self.img_urls.append(img_url)
        if int(max_span) == page_num:
            self.save(img_url)
            post = {
                '标题': self.title,
                '主题页面': self.url,
                '图片地址': self.img_urls,
                '获取时间': datetime.datetime.now()
            }
            self.meizitu_collection.save(post)
            print('插入数据库成功')
        else:
            self.save(img_url)

    def save(self, url):
        name = url[-9:-4]
        print('开始保存', url)
        img = request.get(url, 3)
        f = open(name + '.jpg', 'ab')
        f.write(img.content)
        f.close()

    def mkdir(self, path):
        path = path.strip()
        img_path = os.path.join('F:\meizi', path)
        isExists = os.path.exists(img_path)
        if not isExists:
            print(u'创建了 ', path, u' 文件夹！')
            os.makedirs(img_path)
            os.chdir(img_path)
            return True
        else:
            print(u'文件夹 ', path, u' 已存在')
            return False


meizi = meizi()
meizi.get_all('http://www.mzitu.com/xinggan/')
