#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import matplotlib.pyplot as plt
import numpy as np


# line chart
def plotLineChart1():
    x = np.array([10, 100, 500, 1000, 2000])
    y1 = np.array([3000, 9000, 23000, 45000, 69000])
    y2 = np.array([0.003, 0.006, 0.004, 0.003, 0.003])
    plt.title("in语句优化前后不同参数量耗时对比(微秒)")
    plt.xlabel("参数量")
    plt.ylabel("耗时(us)")
    # color, linestyle, linewidth, marker, markersize, label
    # ls='-' 实线 ls='--' 虚线 ls='-.' 点划线 ls=':' 点虚线
    plt.plot(x, y1, color='#0065AC', ls='-.', lw=1.6, marker='*', ms=8, label="优化前")
    plt.plot(x, y2, color='#FF7F0E', ls='-.', lw=1.6, marker='*', ms=8, label="优化后")
    plt.grid()
    # 线的标签
    plt.legend(loc='upper left')
    # 默认的像素：[6.0,4.0]，分辨率为100，图片尺寸为 600*400
    # 指定dpi=200，图片尺寸为 1200*800
    # 指定dpi=300，图片尺寸为 1800*1200
    plt.savefig('plot_in.png', dpi=200)
    plt.show()


if __name__ == '__main__':
    plotLineChart1()
