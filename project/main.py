import os
from data_visualization import visualization, get_results
import shutil
import time
from multiprocessing import Pool

test_multi = False  # True串行，并行都运行，可比较结果；False仅并行


def CrossMatch(n, BF, HoneyBee, Hornet, round):
    """
    交叉对战，n千次
    :param n: 一轮对战次数
    :param BF: BF路径
    :param HoneyBee: 蜜蜂路径
    :param Hornet: 黄蜂路径
    :param round: 轮次
    :return:
    """
    print('Run task %s (%s)...' % ("round{}".format(round), os.getpid()))
    start = time.time()
    # java中res.txt
    if os.path.exists(BF + r"\Result\res{}.txt".format(round)):
        print("yes")
        os.remove(BF + r"\Result\res{}.txt".format(round))

    # 检查BF中是否有蜂.java，有则删除
    if os.path.exists(BF + "\HoneyBee.java"):
        print("yes")
        os.remove(BF + "\HoneyBee.java")
    if os.path.exists(BF + "\Hornet.java"):
        print("yes")
        os.remove(BF + "\Hornet.java")
    # 将group1,group2的蜂.java复制到BF
    shutil.copy(HoneyBee, BF)  # 1(2)出蜜蜂
    shutil.copy(Hornet, BF)  # 2(1)出黄蜂

    # 运行java程序，n千次
    for i in range(n):
        print(i)
        # 使用os.system调用Java编译器来编译Java文件
        os.chdir(BF)  # 进入java文件夹，根据实际情况修改
        os.system("javac *.java")  # 编译
        # os.system("javac Hornet.java")  # 编译
        # 使用os.system调用Java运行时环境来运行Java程序
        os.system("java game")

    end = time.time()

    print('Task %s runs %0.5f seconds.' % ("round{}".format(round), (end - start)))


if __name__ == '__main__':
    start_total = time.time()
    # 小组1,2
    group1 = '214'
    group2 = '001'
    n = 1
    # BF,Group目录路径
    BFSource = [os.path.dirname(__file__) + "/../BF1", os.path.dirname(__file__) + "/../BF2"]
    GroupSource = os.path.dirname(__file__) + "/../group"  # group路径
    # 黄蜂，蜜蜂路径
    GHoneyBee = [GroupSource + "/【{}】/HoneyBee.java".format(group1), GroupSource + "/【{}】/HoneyBee.java".format(group2)]
    GHornet = [GroupSource + "/【{}】/Hornet.java".format(group2), GroupSource + "/【{}】/Hornet.java".format(group1)]
    # current_file_path = []

    T_multi_begin = time.time()
    # pool并发运行交叉对战
    print('Parent process %s.' % os.getpid())
    p = Pool(2)
    for round in range(2):
        p.apply_async(CrossMatch, args=(n, BFSource[round], GHoneyBee[round], GHornet[round], round + 1))

    print('Waiting for all subprocesses done...')
    p.close()
    p.join()
    print('All subprocesses done.')
    T_multi_end = time.time()
    # 串行测试，对照组,如果测串行，最终结果是串行结果
    if test_multi:
        T_single_begin = time.time()
        CrossMatch(n, BFSource[0], GHoneyBee[0], GHornet[0], 1)
        CrossMatch(n, BFSource[1], GHoneyBee[1], GHornet[1], 2)
        print('串行时间(不含数据处理):%.5fs' % (time.time() - T_single_begin))
    print('并行时间(不含数据处理):%.5fs' % (T_multi_end - T_multi_begin))

    # python中result.txt,数据可视化
    current_file_path1 = r".\result1.txt"  # 当前文件夹的结果路径
    with open(r'..\BF1\Result\res1.txt', 'r') as source, open(current_file_path1, 'w') as target:
        target.write(source.read())  # 复制原结果文件到当前文件夹

    current_file_path2 = r".\result2.txt"  # 当前文件夹的结果路径
    with open(r'..\BF2\Result\res2.txt', 'r') as source, open(current_file_path2, 'w') as target:
        target.write(source.read())  # 复制原结果文件到当前文件夹

    result1, counts1 = get_results(current_file_path1)  # 获取结果数据
    result2, counts2 = get_results(current_file_path2)  # 获取结果数据
    visualization(result1, counts1, result2, counts2)
    print('Total Time:%.5fs' % (time.time() - start_total))
