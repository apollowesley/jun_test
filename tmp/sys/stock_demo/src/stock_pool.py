#!/usr/bin/python
# -*- coding: utf-8 -*-

import pandas as pd
import calendar as cal


class StockPool:

    CACHE_DATAS = None

    def __init__(self):
        self.path = '../input_data/GBDT_CMF-12m_3-18-not_inc_50.txt'

    ########################
    # 加载票池数据(单例模式)
    ########################
    def load_data(self):
        if StockPool.CACHE_DATAS is None:
            v_data = pd.read_csv(self.path, delimiter='\t', dtype=str)
            v_dict = {}
            for v in v_data.values:
                v_dict[v[0]] = v[1:]
            StockPool.CACHE_DATAS = v_dict
        return StockPool.CACHE_DATAS

    # 加载票池数据
    @staticmethod
    def get_df_data():
        return StockPool().load_data()

    # 根据[年月]日期返回所有持有的股票列表
    @staticmethod
    def get_stock_list_by_yyyymm(str_date):
        return StockPool.get_df_data()[str_date]

    @staticmethod
    def month_range(begin_year,begin_month,end_year,end_month):
        str_begin_month = str(begin_month)
        if len(str_begin_month) <= 1:
            str_begin_month = '0' + str_begin_month
        str_begin_date = str(begin_year) + '-' + str_begin_month    #2016-01
        str_end_month = str(end_month)
        if len(str_end_month) <= 1:
            str_end_month = '0' + str_end_month
        str_end_date = str(end_year) + '-' + str_end_month  #2016-12
        return pd.period_range(str_begin_date, str_end_date, freq='M').strftime('%Y%m')

    @staticmethod
    def get_month_range(year, month):
        str_prefix = str(year) + str(month)
        str_end = str_prefix + str(cal.monthrange(year, month)[1])
        str_begin = str_prefix + '01'
        return [str_begin, str_end]


#################
# 测试用例
#################
if __name__ == '__main__':
    # for date in StockPool.month_range():
    #     print StockPool.get_stock_list_by_yyyymm(date)
    # print StockPool.get_stock_list('201601')
    # str_date = raw_input('请输入年月:\n')
    # stock_list = StockPool.get_stock_list_by_yyyymm(str_date)
    # print stock_list
    print StockPool.get_df_data()
