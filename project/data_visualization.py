import numpy as np
import pandas as pd
from pyecharts import options as opts
from pyecharts.charts import Bar, Page, Pie, Polar
# from pyecharts.faker import Faker
import os
from pyecharts.globals import ThemeType
# 导入输出图片工具
from pyecharts.render import make_snapshot
# 使用snapshot-selenium 渲染图片
from snapshot_selenium import snapshot


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


def visualization(result1, counts1, result2, counts2, group1, group2):  # unique_values_score1, score_data1):
    # 生成数据
    # x_data = ['平均分数', '平均采蜜量', '平均存活只数', '游戏平均剩余时间', '最高得分', '最高采蜜量']
    x_data = ['平均分数', '平均采蜜量', '最高得分', '最高采蜜量']
    new_result1 = result1[0:2] + result1[4:]
    new_result2 = result2[0:2] + result2[4:]
    y_data_a = new_result1
    y_data_b = new_result2

    x_data_small = ['平均存活只数', '游戏平均剩余时间']
    y_data_a_small = result1[2:4]
    y_data_b_small = result2[2:4]

    def bar_data() -> Bar:
        c = (

            Bar(init_opts=opts.InitOpts(theme=ThemeType.MACARONS, width="800px", height="500px", ))
            .add_xaxis(x_data)
            # .add_yaxis("用户1", result1)
            # .add_yaxis("用户2", result2)
            .add_yaxis(group1, y_data_a)
            .add_yaxis(group2, y_data_b)
            .set_global_opts(
                xaxis_opts=opts.AxisOpts(axislabel_opts=opts.LabelOpts(font_family="黑体", font_size=12)),
                title_opts=opts.TitleOpts(title="最终结果", subtitle=" "),
                brush_opts=opts.BrushOpts(),
                # datazoom_opts=opts.DataZoomOpts(orient="vertical"),  # DataZoomOpts：区域缩放配置项， 布局方式是横还是竖
            )
        )
        return c

    def bar_data_small() -> Bar:
        c = (

            Bar(init_opts=opts.InitOpts(theme=ThemeType.MACARONS, width="800px", height="500px", ))
            .add_xaxis(x_data_small)
            .add_yaxis(group1, y_data_a_small)
            .add_yaxis(group2, y_data_b_small)
            .set_global_opts(
                xaxis_opts=opts.AxisOpts(axislabel_opts=opts.LabelOpts(font_family="黑体", font_size=12)),
                title_opts=opts.TitleOpts(title="最终结果", subtitle=" "),
                brush_opts=opts.BrushOpts(),
                # datazoom_opts=opts.DataZoomOpts(orient="vertical"),
            )
        )
        return c

    def pie_type() -> Pie:
        c = (
            Pie(init_opts=opts.InitOpts(theme=ThemeType.MACARONS, width="1000px", height="500px", ))

            .add(
                group1,
                [list(z) for z in zip(("0", "1", "2", "3"), counts1)],
                radius=["30%", "70%"],
                center=["27%", "50%"],
                label_opts=opts.LabelOpts(is_show=True),
            )

            .add(
                group2,
                [list(z) for z in zip(("0", "1", "2", "3"), counts2)],
                radius=["30%", "70%"],
                center=["72%", "50%"],
                label_opts=opts.LabelOpts(is_show=True)
            )
            .set_global_opts(
                title_opts=opts.TitleOpts(title="蜜蜂存活只数", subtitle=" " * 70 + group1 + " " * 122 + group2),
                legend_opts=opts.LegendOpts(orient="vertical"),
                brush_opts=opts.BrushOpts(),
            )
            .set_series_opts(label_opts=opts.LabelOpts(formatter="{b}: {c}"))
        )
        return c

    def page_simple_layout():
        page = Page(layout=Page.SimplePageLayout)  # 简单布局
        page.add(bar_data(), bar_data_small(), pie_type(), )
        page.render("page_simple_layout.html")

         # 打开HTML文件
        with open('page_simple_layout.html', 'r') as file:
            # 读取文件内容
            content = file.readlines()

        # 在第二行插入新的<body>标签
        content.insert(1, '<body style="background-color: rgb(190,246,128);">\n')

        # 将修改后的HTML内容写回文件
        with open('page_simple_layout.html', 'w') as file:
            file.writelines(content)

    # make_snapshot(snapshot, bar_data().render(), "bar.png", )
    # make_snapshot(snapshot, bar_data_small().render(), "bar_small.png", )
    # make_snapshot(snapshot, pie_type().render(), "pie.png", )
    page_simple_layout()

    # cmd_str = "page_simple_layout.html"
    # f = os.popen(cmd_str)
    # f.close()
