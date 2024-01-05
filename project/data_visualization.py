import numpy as np
import pandas as pd
from pyecharts import options as opts
from pyecharts.charts import Bar, Page, Pie, Polar
# from pyecharts.faker import Faker
import os
from pyecharts.globals import ThemeType


def get_results(current_file_path1):
    def process(path: str) -> np.ndarray:
        file = open(path, 'r', encoding='utf-8')
        a = []
        for line in file:
            str_list = line[:-1].split(',')
            int_list = [int(x) for x in str_list]
            a.append(int_list)
        file.close()
        return np.array(a)

    data1 = process(current_file_path1)  # 获取结果数组
    # runs为运行次数
    runs = data1.shape[0]

    # 计算第一列的平均值
    average_score_1 = round(float(np.mean(data1[:, 0])), 1)
    # 计算第二列的平均值
    average_honey_1 = round(float(np.mean(data1[:, 1])), 1)
    # 计算第三列的平均值
    average_live_bee_1 = round(float(np.mean(data1[:, 2])), 2)
    # 计算第四列的平均值
    average_live_time_1 = round(float(np.mean(data1[:, 3])), 1)

    # 找到第一列的最大值
    score_max_1 = int(np.max(data1[:, 0]))
    # 找到第二列的最大值
    honey_max_1 = int(data1[:, 1].max())
    bar_data1 = [average_score_1, average_honey_1, average_live_bee_1, average_live_time_1, score_max_1, honey_max_1]
    # print(average_score_1, average_honey_1, average_live_bee_1, average_live_time_1, score_max_1, honey_max_1)

    # # 获取第一列的值，统计所有不重复的数字
    # first_column = data1[:, 0]
    # first_column_filtered = first_column[first_column >= 0]
    # unique_values_score1 = np.unique(first_column_filtered)
    # counts_score = np.bincount(first_column_filtered)
    # score_data1 = counts_score.tolist()
    # unique_values_score1 = unique_values_score1.tolist()

    # 获取第三列的值
    third_column = data1[:, 2]
    # 获取第三列中所有不重复的数字
    unique_values_bee = np.unique(third_column)
    # 统计每个数字出现的次数
    counts_bee = np.bincount(third_column)
    pie_data1 = counts_bee.tolist()
    # 输出结果
    # for value in unique_values:
    #     count = pie_data1[value]
    #     print(f"数字 {value} 在第三列中出现了 {count} 次")
    return bar_data1, pie_data1,  # unique_values_score1, score_data1


def visualization(result1, counts1, result2, counts2):  # unique_values_score1, score_data1):
    # 生成数据
    x_data = ['平均分数', '平均采蜜量', '平均存活只数', '游戏平均剩余时间', '最高得分', '最高采蜜量']
    y_data_a = result1
    y_data_b = result2

    def bar_data() -> Bar:
        c = (

            Bar(init_opts=opts.InitOpts(theme=ThemeType.MACARONS))
            .add_xaxis(x_data)
            .add_yaxis("用户1", y_data_a)
            .add_yaxis("用户2", y_data_b)
            .set_global_opts(
                xaxis_opts=opts.AxisOpts(axislabel_opts=opts.LabelOpts(font_family="黑体", font_size=10)),
                title_opts=opts.TitleOpts(title="最终结果", subtitle=" "),
            )
        )
        return c

    # 2.饼图
    def pie_type() -> Pie:
        c = (
            Pie(init_opts=opts.InitOpts(theme=ThemeType.MACARONS))

            .add(
                "",
                [list(z) for z in zip(("0", "1", "2", "3"), counts1)],
                radius=["30%", "70%"],
                center=["30%", "50%"],
                label_opts=opts.LabelOpts(is_show=True),
            )

            .add(
                "",
                [list(z) for z in zip(("0", "1", "2", "3"), counts2)],
                radius=["30%", "75%"],
                center=["75%", "50%"],
                label_opts=opts.LabelOpts(is_show=True)
            )
            .set_global_opts(
                title_opts=opts.TitleOpts(title="蜜蜂存活只数"),
                legend_opts=opts.LegendOpts(orient="vertical")
            )
            .set_series_opts(label_opts=opts.LabelOpts(formatter="{b}: {c}"))
        )
        return c

    # def polar_data() -> Polar:
    #     # for i in score_data1:
    #
    #     data = unique_values_score1
    #     data = [(unique_values_score1, score_data1) for score_data1 in range(600)]
    #     c = (
    #         Polar(init_opts=opts.InitOpts(theme=ThemeType.MACARONS))
    #         .add("", data, type_="scatter", label_opts=opts.LabelOpts(is_show=False))
    #         .set_global_opts(title_opts=opts.TitleOpts(title="Polar-Scatter0"))
    #     )
    #     return c

    def page_simple_layout():
        #    page = Page()   默认布局
        page = Page(layout=Page.DraggablePageLayout)  # 可改动位置
        # page = Page(layout=Page.SimplePageLayout)  # 简单布局
        # 将上面定义好的图添加到 page
        page.add(bar_data(), pie_type(), )
        page.render("page_simple_layout.html")

    page_simple_layout()
    cmd_str = "page_simple_layout.html"
    f = os.popen(cmd_str)
    f.close()
