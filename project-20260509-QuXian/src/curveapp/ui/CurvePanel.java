package curveapp.ui;

import curveapp.model.ControlPointManager;
import curveapp.util.CatmullRomSpline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * 曲线绘制面板
 * 负责鼠标交互（添加/拖拽/删除点）以及图形绘制
 */
public class CurvePanel extends JPanel {

    private final ControlPointManager pointManager;
    private int draggedPointIndex = -1;   // 正在拖拽的点索引
    private double dragOffsetX, dragOffsetY;

    // 外观参数
    private static final int POINT_RADIUS = 6;
    private static final int HIT_THRESHOLD = 10;

    public CurvePanel() {
        pointManager = new ControlPointManager();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));

        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    // ---------- 公开方法供外部调用 ----------
    public void clearAllPoints() {
        pointManager.clear();
        draggedPointIndex = -1;
        repaint();
    }

    // ---------- 内部交互处理 ----------
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            // 右键删除
            if (SwingUtilities.isRightMouseButton(e)) {
                int hit = pointManager.findHitIndex(e.getX(), e.getY(), HIT_THRESHOLD);
                if (hit != -1) {
                    pointManager.removePoint(hit);
                    if (draggedPointIndex == hit) draggedPointIndex = -1;
                    repaint();
                }
                return;
            }

            // 左键：先尝试拖拽，否则添加新点
            if (SwingUtilities.isLeftMouseButton(e)) {
                int hit = pointManager.findHitIndex(e.getX(), e.getY(), HIT_THRESHOLD);
                if (hit != -1) {
                    draggedPointIndex = hit;
                    Point2D.Double p = pointManager.getPoints().get(hit);
                    dragOffsetX = p.x - e.getX();
                    dragOffsetY = p.y - e.getY();
                } else {
                    boolean added = pointManager.addPoint(e.getX(), e.getY(), HIT_THRESHOLD);
                    if (added) repaint();
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (draggedPointIndex != -1) {
                double newX = e.getX() + dragOffsetX;
                double newY = e.getY() + dragOffsetY;
                // 边界约束（保留点半径边距）
                newX = Math.max(POINT_RADIUS, Math.min(getWidth() - POINT_RADIUS, newX));
                newY = Math.max(POINT_RADIUS, Math.min(getHeight() - POINT_RADIUS, newY));
                pointManager.movePoint(draggedPointIndex, newX, newY);
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            draggedPointIndex = -1;
            repaint();
        }
    }

    // ---------- 绘制 ----------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawGrid(g2d);                     // 背景网格
        drawCurve(g2d);                    // 曲线
        drawControlPoints(g2d);            // 控制点
        drawInstruction(g2d);              // 操作提示
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(new Color(230, 230, 230));
        int step = 30;
        int w = getWidth(), h = getHeight();
        for (int x = 0; x < w; x += step) g2d.drawLine(x, 0, x, h);
        for (int y = 0; y < h; y += step) g2d.drawLine(0, y, w, y);
    }

    private void drawCurve(Graphics2D g2d) {
        if (pointManager.size() >= 2) {
            GeneralPath curve = CatmullRomSpline.buildCurve(pointManager.getPoints());
            if (curve != null) {
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.setColor(new Color(220, 80, 60)); // 橙红色曲线
                g2d.draw(curve);
            }
        }
    }

    private void drawControlPoints(Graphics2D g2d) {
        for (Point2D.Double p : pointManager.getPoints()) {
            int x = (int) p.x, y = (int) p.y;
            g2d.setColor(new Color(70, 130, 200)); // 钢蓝色
            g2d.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
            g2d.setColor(Color.WHITE);
            g2d.drawOval(x - POINT_RADIUS, y - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
        }
    }

    private void drawInstruction(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        g2d.drawString("左键添加点 | 拖拽移动点 | 右键删除点 | 下方按钮清除所有点", 15, 25);
    }
}