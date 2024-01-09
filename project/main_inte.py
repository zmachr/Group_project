import sys
import os
import subprocess
from vs import *
from tkinter import Tk
from tkinter.filedialog import askopenfilename
from PyQt5.QtGui import QCursor
from PyQt5.QtWidgets import *
from PyQt5.QtCore import Qt, QTimer 
from PyQt5.QtWebEngineWidgets import QWebEngineView
from Ui_integration import * 
from data_visualization import visualization, get_results

class MainWindow(QMainWindow): 
    def __init__(self):
        super().__init__()
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)
        self.setWindowFlag(QtCore.Qt.FramelessWindowHint)
        self.setAttribute(QtCore.Qt.WA_TranslucentBackground)
        self.shadow =  QtWidgets.QGraphicsDropShadowEffect(self)
        self.shadow.setOffset(10,10)
        self.shadow.setBlurRadius(10)
        self.shadow.setColor(QtCore.Qt.black)
        self.ui.frame.setGraphicsEffect(self.shadow)
        self.ui.pushButton_close.clicked.connect(self.close)
        self.ui.pushButton_close2.clicked.connect(self.close)
        self.ui.pushButton_close3.clicked.connect(self.close)
        self.ui.pushButton_close4.clicked.connect(self.close)
        self.m_flag = None
        # self.restoreMouseEvents()

        #Page1，初始页面
        self.ui.stackedWidget.setCurrentIndex(0)  
        self.ui.pushButton_begin.clicked.connect(lambda:self.ui.stackedWidget.setCurrentIndex(1))
    
        #Page2，选择页面
        self.ui.stackedWidget_tip.setCurrentIndex(0)
        self.l1_path = None 
        self.l2_path = None
        self.r1_path = None
        self.r2_path = None
        self.g1_name = None
        self.g2_name = None
       
        self.ui.pushButton_l1.clicked.connect(self.selectFile)
        self.ui.pushButton_l2.clicked.connect(self.selectFile)
        self.ui.pushButton_r1.clicked.connect(self.selectFile)
        self.ui.pushButton_r2.clicked.connect(self.selectFile)
        # self.ui.pushButton_vs.clicked.connect(lambda:self.ui.stackedWidget.setCurrentIndex(2))
        self.ui.pushButton_vs.clicked.connect(self.run_vs)

        #Page3，过渡页面
        
        #Page4，结果页面
        self.ui.stackedWidget_win.setCurrentIndex(0)
        self.ui.pushButton_detail.clicked.connect(self.load_chart)

        self.show()
    
    def selectFile(self):  
        Tk().withdraw()  # 隐藏根窗口

        filepath = askopenfilename()  # 打开文件选择对话框
        print("Selected file:", filepath)
        # 根据按钮的对象名称存储文件路径

        button_name = self.sender().objectName()
        if button_name == "pushButton_l1":
            self.l1_path = filepath
        elif button_name == "pushButton_l2":
            self.l2_path = filepath
        elif button_name == "pushButton_r1":
            self.r1_path = filepath
        elif button_name == "pushButton_r2":
            self.r2_path = filepath
        
    def run_vs(self):
        self.g1_name = self.ui.group1_namein.text() 
        self.g2_name = self.ui.group2_namein.text() 
        print(self.g1_name)
        print(self.g2_name)
        self.ui.group1_nameout.setText(self.g1_name)
        self.ui.group2_nameout.setText(self.g2_name)

        n = self.ui.comboBox.currentText()
        # 检查四个文件路径是否都有空
        if not self.l1_path or not self.l2_path or not self.r1_path or not self.r2_path:
            self.ui.stackedWidget_tip.setCurrentIndex(1)    
        elif self.l1_path and self.l2_path and self.r1_path and self.r2_path :
            if n != '未选择':
                # 调用"vs.py"运行四个文件
                args = [sys.executable, 'vs.py'] + [self.l1_path, self.r1_path, self.l2_path, self.r2_path, n, self.g1_name, self.g2_name]  
                vs = subprocess.Popen(args)
                self.ui.stackedWidget.setCurrentIndex(2)
                self.progress_begin(vs)
                self.ui.stackedWidget.setCurrentIndex(3)
                # self.load_chart() 
            else: 
                self.ui.stackedWidget_tip.setCurrentIndex(2)
        
        #放置奖杯
        current_file_path1 = r".\result1.txt"  # 当前文件夹的结果路径
       
        current_file_path2 = r".\result2.txt"  # 当前文件夹的结果路径
       
        result1, r1 = get_results(current_file_path1)  # 获取结果数据
        result2, r2 = get_results(current_file_path2)  # 获取结果数据
        if result1[0] > result2[0]:
            self.ui.stackedWidget_win.setCurrentIndex(1)
        else:
            self.ui.stackedWidget_win.setCurrentIndex(2)

        #呈现数据
        self.ui.label_4.setText(str(result1[0]))
        self.ui.label_5.setText(str(result2[0]))
        self.ui.label_6.setText(str(result1[1]))
        self.ui.label_7.setText(str(result2[1]))

    def progress_begin(self, process):  
        for i in range(101):  
            time.sleep(0.05)  
            self.ui.progressBar.setValue(i)      
            if i == 99:  # 如果进度条到99%，等待一段时间看进程是否完成  
                time.sleep(1)  
                if process.poll() is None:  # 如果进程仍在运行，卡在99%的进度条  
                    process.wait()
                if process.poll() is not None:
                    continue
            if i == 100 or process.poll() is not None:
                time.sleep(1)
                self.ui.progressBar.setValue(100)  
                break

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton and self.isMaximized() == False:
            self.m_flag = True
            self.m_Position = event.globalPos() - self.pos()
            event.accept()
            self.setCursor(QCursor(Qt.OpenHandCursor))

    def mouseMoveEvent(self, mouse_event):
        if Qt.LeftButton and self.m_flag:
            self.move(mouse_event.globalPos() - self.m_Position)
            mouse_event.accept()

    def mouseReleaseEvent(self, mouse_event):
        self.m_flag = False
        self.setCursor(QCursor(Qt.ArrowCursor))

    def load_chart(self):

        self.win = WebWindow()
        self.win.show()
        # chart_path = "file:///D:/code/software_project/Group_project-main/project/page_simple_layout.html"
        # self.chart_view = QWebEngineView()
        # self.chart_view.load(QtCore.QUrl(chart_path))

        # #弹新网页 
        # self.setCentralWidget(self.chart_view)

        # #有网页，翻不了页
        # self.ui.page_chart.setLayout(QVBoxLayout())
        # self.ui.page_chart.layout().addWidget(self.chart_view)

        # #有网页，翻不了页
        # self.Layout = QHBoxLayout(self.ui.page_chart)
        # self.Layout.update()
        # self.Layout.addWidget(self.chart_view)

class WebWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        
        chart_path = "file:///D:/code/software_project/Group_project-main/project/page_simple_layout.html"
        self.setGeometry(5,30,1355,730)
        self.chart_view = QWebEngineView()
        self.chart_view.load(QtCore.QUrl(chart_path))

        #弹新网页 
        self.setCentralWidget(self.chart_view)
 
                
if __name__ == "__main__":
    QtCore.QCoreApplication.setAttribute(QtCore.Qt.AA_EnableHighDpiScaling)
    app = QApplication(sys.argv)
    win = MainWindow()
    sys.exit(app.exec_())    