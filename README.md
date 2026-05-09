# 工业软件曲线绘制工具

[![Java Version](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/technologies/javase-downloads.html) [![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

一个基于 Java Swing 实现的交互式曲线绘制工具，专为工业软件场景设计。支持鼠标添加控制点、实时生成平滑曲线、拖拽调整点位置以及删除点，并采用 **Catmull‑Rom 样条** 算法保证曲线精确经过所有控制点。

![界面预览](screenshot.png) <!-- 请替换为实际截图路径 -->

## 功能特性

- ✨ **鼠标添加点**：左键单击空白区域即可生成新的控制点（自动避免重叠）
- 🖱️ **拖拽移动点**：按住左键拖拽控制点，曲线实时更新
- 🗑️ **右键删除点**：右键单击控制点即可删除
- 📐 **Catmull‑Rom 样条曲线**：平滑且经过每一个控制点
- 🎨 **抗锯齿绘制**：曲线和控制点边缘平滑
- 🔲 **辅助网格**：方便定位和对齐
- 🧹 **一键清除**：底部按钮可清除所有控制点

## 技术栈

- **语言**：Java 8+
- **GUI框架**：Swing / AWT
- **曲线算法**：Catmull‑Rom 样条插值（张力系数 0.5）
- **构建工具**：无依赖，原生 JDK 编译运行

## 项目结构
project-20260509-QuXian/
├── src/
│ └── curveapp/
│ ├── CurveDrawingApp.java # 程序入口
│ ├── model/
│ │ └── ControlPointManager.java # 控制点数据模型
│ ├── util/
│ │ └── CatmullRomSpline.java # Catmull‑Rom 曲线算法
│ └── ui/
│ └── CurvePanel.java # 主界面及交互面板
├── README.md
└── LICENSE


## 快速开始

### 环境要求

- JDK 8 或更高版本
- Git（可选）

### 克隆与运行

```bash
# 克隆仓库
git clone https://github.com/cps325102/project-20260509-QuXian.git

# 进入项目目录
cd project-20260509-QuXian

# 编译所有 Java 文件（Windows/Mac/Linux）
javac -d . src/curveapp/**/*.java src/curveapp/*.java

# 运行主类
java curveapp.CurveDrawingApp


使用说明
添加点：在白色画布左键点击任意位置，即可出现一个蓝色控制点。

移动点：按住左键拖拽任意控制点，曲线会随点位置动态变化。

删除点：右键单击某个控制点，该点会被移除，曲线自动更新。

清除所有点：点击窗口底部的 “清除所有点” 按钮。

退出程序：关闭主窗口即可。

提示：曲线需要至少 2 个点 才会绘制。当点数量不足 2 时，只显示控制点而不显示曲线。

算法说明
本项目使用 Catmull‑Rom 样条曲线 生成经过所有控制点的平滑路径。该算法是计算机图形学中常用的插值曲线，具有以下特点：

曲线精确经过除首尾虚拟点外的每一个控制点。

曲线在控制点处连续且可导（C¹ 连续），视觉上平滑。

实现简单，无需解线性方程组。

 
每对相邻控制点之间通过 30 个插值步生成线段，构成最终曲线。

详细实现见 CatmullRomSpline.java

代码结构说明
类	职责
CurveDrawingApp	程序入口，创建主窗口并组装 UI 组件
ControlPointManager	存储控制点列表，提供增删改查及命中检测接口
CatmullRomSpline	静态工具类，根据控制点列表构建 GeneralPath 曲线
CurvePanel	继承 JPanel，负责鼠标交互（添加/拖拽/删除）和所有图形绘制