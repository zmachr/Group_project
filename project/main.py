import os
from data_visualization import visualization, get_results
import shutil
import time

# 计时器
T1 = time.time()


# def java(n):
#     for i in range(n):
#         print(i)
#         # 使用os.system调用Java编译器来编译Java文件
#         os.chdir(r"D:\1study\大学\大三上\软件课设\软件课设v2\BF")  # 进入java文件夹，根据实际情况修改
#         os.system("javac -encoding GB2312 *.java")  # 编译
#         # 使用os.system调用Java运行时环境来运行Java程序
#         os.system("java game")


# def CrossMatch(group1, group2, n):
def CrossMatch(n, BF, HoneyBee, Hornet, round):
    # """
    # 交叉对战,使用os和shutil模块对java文件进行移动、复制及删除
    # :param group1: 用户选择的第一组的编号，例如"001"(string)
    # :param group2: 用户选择的第二组的编号
    # :param n: 对战次数n千次
    # :return:
    # """
    # GroupOldSource = os.path.dirname(__file__) + "/../group_old"#BF原始group路径
    # G1HBSource = GroupSource + "/【{}】/HoneyBee.java".format(group1)  # group1蜜蜂路径
    # G1HSource = GroupSource + "/【{}】/Hornet.java".format(group1)  # group1黄蜂路径
    # G2HBSource = GroupSource + "/【{}】/HoneyBee.java".format(group2)  # group2蜜蜂路径
    # G2HSource = GroupSource + "/【{}】/Hornet.java".format(group2)  # group2黄蜂路径

    # java中res.txt
    if os.path.exists(BF + r"\Result\res{}.txt".format(round)):
        print("yes")
        os.remove(BF + r"\Result\res{}.txt".format(round))


    # 第一轮：group1蜜蜂 vs group2黄蜂
    # 检查BF1中是否有蜂.java，有则删除
    if os.path.exists(BF + "\HoneyBee.java"):
        print("yes")
        os.remove(BF + "\HoneyBee.java")
    if os.path.exists(BF + "\Hornet.java"):
        print("yes")
        os.remove(BF + "\Hornet.java")
    # 将group1,group2的蜂.java复制到BF1
    shutil.copy(HoneyBee, BF)  # 1出蜜蜂
    shutil.copy(Hornet, BF)  # 2出黄蜂

    # BF1现在完整，调用函数java(),开始对战
    # java(1)
    # 运行java程序，n千次
    for i in range(n):
        print(i)
        # 使用os.system调用Java编译器来编译Java文件
        os.chdir(BF)  # 进入java文件夹，根据实际情况修改
        os.system("javac HoneyBee.java")  # 编译
        os.system("javac Hornet.java")  # 编译
        # 使用os.system调用Java运行时环境来运行Java程序
        os.system("java game")


    # python中result.txt,数据可视化
    current_file_path = r".\result{}.txt".format(round)  # 当前文件夹的结果路径
    with open(r'..\BF{0}\Result\res{1}.txt'.format(round, round), 'r') as source, open(current_file_path,
                                                                                       'w') as target:
        target.write(source.read())  # 复制原结果文件到当前文件夹

    result, counts = get_results(current_file_path)
    return result, counts

    # # 第二轮：group2蜜蜂 vs group1黄蜂
    # # 检查BF2中是否有蜂.java，有则删除
    # if os.path.exists(r"..\BF2\HoneyBee.java"):
    #     print("yes")
    #     os.remove(r"..\BF2\HoneyBee.java")
    # if os.path.exists(r"..\BF2\Hornet.java"):
    #     print("yes")
    #     os.remove(r"..\BF2\Hornet.java")
    # # 将group1,group2的蜂.java复制到BF2
    # shutil.copy(G2HBSource, BF2Source)  # 2出蜜蜂
    # shutil.copy(G1HSource, BF2Source)  # 1出黄蜂
    #
    # # BF2现在完整，调用函数java(),开始对战
    # # java(1)
    # # 运行java程序，n千次
    # for i in range(n):
    #     print(i)
    #     # 使用os.system调用Java编译器来编译Java文件
    #     os.chdir(BF2Source)  # 进入java文件夹，根据实际情况修改
    #     os.system("javac HoneyBee.java")  # 编译
    #     os.system("javac Hornet.java")  # 编译
    #     # 使用os.system调用Java运行时环境来运行Java程序
    #     os.system("java game")


group1 = '001'
group2 = '001'
BF1Source = os.path.dirname(__file__) + "/../BF1"  # BF1路径
BF2Source = os.path.dirname(__file__) + "/../BF2"  # BF2路径
GroupSource = os.path.dirname(__file__) + "/../group"  # group路径

G1HBSource = GroupSource + "/【{}】/HoneyBee.java".format(group1)  # group1蜜蜂路径
G1HSource = GroupSource + "/【{}】/Hornet.java".format(group1)  # group1黄蜂路径
G2HBSource = GroupSource + "/【{}】/HoneyBee.java".format(group2)  # group2蜜蜂路径
G2HSource = GroupSource + "/【{}】/Hornet.java".format(group2)  # group2黄蜂路径

# java中res.txt
# if os.path.exists(r"..\BF1\Result\res1.txt"):
#     print("yes")
#     os.remove(r"..\BF1\Result\res1.txt")
# if os.path.exists(r"..\BF2\Result\res2.txt"):
#     print("yes")
#     os.remove(r"..\BF2\Result\res2.txt")

# # 运行java:n千次
# java(1)
# 交叉对战
# CrossMatch("214", "217", 1)
result1, counts1 = CrossMatch(1, BF1Source, G1HBSource, G2HSource, 1)
result2, counts2 = CrossMatch(1, BF2Source, G2HBSource, G1HSource, 2)

# python中result.txt,数据可视化
# current_file_path1 = r".\result1.txt"  # 当前文件夹的结果路径
# with open(r'..\BF1\Result\res1.txt', 'r') as source, open(current_file_path1, 'w') as target:
#     target.write(source.read())  # 复制原结果文件到当前文件夹
#
# current_file_path2 = r".\result2.txt"  # 当前文件夹的结果路径
# with open(r'..\BF2\Result\res2.txt', 'r') as source, open(current_file_path2, 'w') as target:
#     target.write(source.read())  # 复制原结果文件到当前文件夹


# result1, counts1, result2, counts2 = get_results(current_file_path1, current_file_path2)  # 获取结果数据
# result1, counts1 = get_results(current_file_path1)  # 获取结果数据
# result2, counts2 = get_results(current_file_path2)  # 获取结果数据
visualization(result1, counts1, result2, counts2)

T2 = time.time()
print('程序运行时间:%s秒' % (T2 - T1))
