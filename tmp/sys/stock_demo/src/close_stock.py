#!/usr/bin/python
# -*- coding: utf-8 -*-

import pandas as pd
import calendar as cal


class CloseStock:
    CACHE_DATAS = None

    def __init__(self):
        self.path = '../input_data/close.txt'

    def load_data(self):
        if CloseStock.CACHE_DATAS is None:
            CloseStock.CACHE_DATAS = pd.read_csv(self.path, delimiter='\t', dtype=str, skiprows=1)
        return CloseStock.CACHE_DATAS

    @staticmethod
    def get_df_data():
        return CloseStock().load_data()

    @staticmethod
    def fmt_yyyy_mm(year, month):
        str_month = str(month)
        if len(str_month) <= 1:
            str_month = '0' + str_month
        return str(year) + str_month

    # 获取当月的第一天日期和最后一天日期
    @staticmethod
    def get_month_range(year, month):
        str_prefix = CloseStock.fmt_yyyy_mm(year,month)
        str_end = str_prefix + str(cal.monthrange(year, month)[1])
        str_begin = str_prefix + '01'
        return [str_begin, str_end]

    # 获取月收益率
    @staticmethod
    def get_rate_of_return(year, month):
        v_df = CloseStock.get_df_data()
        v_dr = CloseStock.get_month_range(year, month)
        v_df = v_df[(v_df.close >= v_dr[0]) & (v_df.close <= v_dr[1])]
        v_x2 = v_df.tail(1).astype('float64').values[0][1:]
        v_x1 = v_df.head(1).astype('float64').values[0][1:]
        return (v_x2-v_x1) / v_x1

    @staticmethod
    def get_data_by_stock(year, month, stock):
        v_df = CloseStock.get_df_data()
        v_dr = CloseStock.get_month_range(year, month)
        v_df = v_df[(v_df.close >= v_dr[0]) & (v_df.close <= v_dr[1])]
        x0 = v_df.head(1).astype('float64')[stock].values
        x1 = v_df.tail(1).astype('float64')[stock].values
        return ((x1 - x0) / x0)[0]

if __name__ == '__main__':
    print 'hello'

