package curveapp.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理所有控制点（数据模型）
 * 提供添加、删除、移动、清除、命中测试等功能
 */
public class ControlPointManager {

    private final List<Point2D.Double> points = new ArrayList<>();

    /** 添加新点（如与现有点距离过近则忽略） */
    public boolean addPoint(double x, double y, double minDistance) {
        for (Point2D.Double p : points) {
            if (p.distance(x, y) < minDistance) {
                return false;
            }
        }
        points.add(new Point2D.Double(x, y));
        return true;
    }

    /** 删除指定索引的点 */
    public void removePoint(int index) {
        if (index >= 0 && index < points.size()) {
            points.remove(index);
        }
    }

    /** 移动指定索引的点到新坐标（可附加边界限制由上层处理） */
    public void movePoint(int index, double newX, double newY) {
        if (index >= 0 && index < points.size()) {
            Point2D.Double p = points.get(index);
            p.x = newX;
            p.y = newY;
        }
    }

    /** 清除所有点 */
    public void clear() {
        points.clear();
    }

    /** 获取所有点的只读副本 */
    public List<Point2D.Double> getPoints() {
        return new ArrayList<>(points);
    }

    /** 获取点的数量 */
    public int size() {
        return points.size();
    }

    /** 查找与给定坐标距离 <= threshold 的点，返回其索引；找不到返回 -1 */
    public int findHitIndex(double mx, double my, double threshold) {
        for (int i = 0; i < points.size(); i++) {
            Point2D.Double p = points.get(i);
            if (p.distance(mx, my) <= threshold) {
                return i;
            }
        }
        return -1;
    }
}
