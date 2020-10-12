#!/usr/bin/python
# -*- coding: utf-8 -*-

#######################
# 画图月度收益率图
#######################
import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
import pandas as pd
import stock_pool as sp   # 票池包
import close_stock as cs  # 收盘价数据包

if __name__ == '__main__':
    # 初始化参数设置
    np.seterr(divide='ignore', invalid='ignore')
    mpl.rcParams['axes.unicode_minus'] = False
    mpl.rcParams['font.sans-serif'] = 'SimHei'
    clrs = ['ro-', 'go-', 'yo-', 'bo-', 'co-', 'ko-', 'mo-']  # 颜色

    # 初始化业务参数
    str_begin_year = 2015    #开始年
    str_begin_month = 1      #开始月
    str_end_year = 2016      #终止年
    str_end_month = 12       #终止月
    str_stocks = ['000001.SZ','000002.SZ','600416.SH']    #关注的股票号

    # x轴的坐标标识
    group_labels = sp.StockPool.month_range(str_begin_year,str_begin_month,str_end_year,str_end_month)

    # 年的范围
    years = np.arange(str_begin_year, str_end_year+1, 1)

    # 月的范围
    months = np.arange(str_begin_month, str_end_month+1, 1)

    # 生成x轴的数据集合
    x = np.arange(years.shape[0] * months.shape[0])

    # 定义y轴的数据集合
    y = []

    fig = plt.figure(facecolor='w', figsize=(12, 7))
    x = np.arange(years.shape[0] * months.shape[0])
    for i, stock in enumerate(str_stocks):
        for year in years:
            for month in months:
                y0 = cs.CloseStock.get_data_by_stock(year, month, stock)
                y.append(y0*100)
        plt.plot(x, y, clrs[i], lw=2, label=stock)
        print pd.DataFrame(y,index=group_labels,columns=['收益率'])
        y = []
    plt.xticks(x, group_labels, rotation=45)
    plt.title(u'月收益率(%)', fontsize=18)
    plt.xlabel(u'年月(yyyyMM)', fontsize=14)
    plt.ylabel(u'收益率(%)', fontsize=14)
    plt.grid(b=True, ls=':')
    plt.legend(loc='upper right')
    plt.show()
