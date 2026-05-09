package curveapp;

import curveapp.ui.CurvePanel;
import javax.swing.*;
import java.awt.*;

/**
 * 工业软件曲线绘制工具 - 主入口
 */
public class CurveDrawingApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("工业软件 - 曲线绘制工具");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);

            CurvePanel curvePanel = new CurvePanel();
            frame.add(curvePanel, BorderLayout.CENTER);

            // 底部控制面板
            JPanel controlPanel = new JPanel();
            JButton clearBtn = new JButton("清除所有点");
            clearBtn.addActionListener(e -> curvePanel.clearAllPoints());
            controlPanel.add(clearBtn);

            JLabel infoLabel = new JLabel("  左键添加点 | 拖拽移动点 | 右键删除点  ");
            infoLabel.setForeground(Color.DARK_GRAY);
            controlPanel.add(infoLabel);

            frame.add(controlPanel, BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }
}