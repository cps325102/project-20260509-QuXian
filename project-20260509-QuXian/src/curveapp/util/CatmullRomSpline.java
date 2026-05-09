package curveapp.util;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Catmull‑Rom 样条曲线算法
 * 给定控制点列表，返回经过所有点的平滑曲线路径
 */
public final class CatmullRomSpline {

    private static final int STEPS = 30;   // 每段曲线的插值步数

    private CatmullRomSpline() {} // 工具类，禁止实例化

    /**
     * 构建经过所有控制点的 Catmull‑Rom 曲线
     * @param points 控制点列表（至少2个）
     * @return GeneralPath 路径，若点数不足则返回 null
     */
    public static GeneralPath buildCurve(List<Point2D.Double> points) {
        int n = points.size();
        if (n < 2) return null;

        GeneralPath path = new GeneralPath();
        for (int i = 0; i < n - 1; i++) {
            Point2D.Double p0, p1, p2, p3;

            p1 = points.get(i);
            p2 = points.get(i + 1);

            // 构造辅助点 p0 和 p3
            if (i == 0) {
                p0 = new Point2D.Double(2 * p1.x - p2.x, 2 * p1.y - p2.y);
            } else {
                p0 = points.get(i - 1);
            }

            if (i == n - 2) {
                p3 = new Point2D.Double(2 * p2.x - p1.x, 2 * p2.y - p1.y);
            } else {
                p3 = points.get(i + 2);
            }

            // 曲线起点
            if (i == 0) {
                path.moveTo(p1.x, p1.y);
            }

            // 插值生成曲线段
            for (int step = 1; step <= STEPS; step++) {
                double t = (double) step / STEPS;
                Point2D.Double pt = interpolate(p0, p1, p2, p3, t);
                path.lineTo(pt.x, pt.y);
            }
            // 确保终点精确落在 p2
            path.lineTo(p2.x, p2.y);
        }
        return path;
    }

    /**
     * Catmull‑Rom 单段插值（张力系数 0.5）
     */
    private static Point2D.Double interpolate(Point2D.Double p0, Point2D.Double p1,
                                              Point2D.Double p2, Point2D.Double p3, double t) {
        double t2 = t * t;
        double t3 = t2 * t;

        double x = 0.5 * ((2 * p1.x) +
                (-p0.x + p2.x) * t +
                (2 * p0.x - 5 * p1.x + 4 * p2.x - p3.x) * t2 +
                (-p0.x + 3 * p1.x - 3 * p2.x + p3.x) * t3);
        double y = 0.5 * ((2 * p1.y) +
                (-p0.y + p2.y) * t +
                (2 * p0.y - 5 * p1.y + 4 * p2.y - p3.y) * t2 +
                (-p0.y + 3 * p1.y - 3 * p2.y + p3.y) * t3);
        return new Point2D.Double(x, y);
    }
}