import os
from data_visualization import visualization, get_results


def java(n):
    for i in range(n):
        print(i)
        # 使用os.system调用Java编译器来编译Java文件
        os.chdir(r"C:\Users\Administerator\Desktop\软件课设\BF")  # 进入java文件夹，根据实际情况修改
        os.system("javac -encoding GB2312 *.java")  # 编译
        # 使用os.system调用Java运行时环境来运行Java程序
        os.system("java game")


if os.path.exists(r"..\BF\Result\res.txt"):
    print("yes")
    os.remove(r"..\BF\Result\res.txt")

java(3)

current_file_path1 = r".\result.txt"  # 当前文件夹的结果路径
with open(r'..\BF\Result\res.txt', 'r') as source, open(current_file_path1, 'w') as target:
    target.write(source.read())  # 复制原结果文件到当前文件夹

result1, counts1 = get_results(current_file_path1)  # 获取结果数据
visualization(result1, counts1, )
