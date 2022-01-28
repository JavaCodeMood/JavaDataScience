package com.lhf.javadatascience.chapter9;


import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

/**
 * @ClassName SineGraph
 * @Desc 绘制2D正弦曲线
 * @Author diandian
 * @Date 2022/1/26 21:18
 **/
public class SineGraph extends JFrame {
    private static final long serialVersionUID = 1L;

    //绘制正弦曲线
    public SineGraph() throws FileNotFoundException, IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);  //定义关闭窗体时的默认行为
        setSize(1400, 1000);  //设置窗体大小

        //创建数据 使用for循环与sin()函数生成一系列的x与y值，然后把它们放入一个数据表中
        DataTable data = new DataTable(Double.class, Double.class);
        for (double x = -5.0; x <= 5.0; x+=0.25) {
            double y = 5.0*Math.sin(x);
            data.add(x, y);
        }

        //使用XYPlot来绘制正弦 创建对象，并传入数据
        XYPlot plot = new XYPlot(data);
        //把图形设置到交互面板
        getContentPane().add(new InteractivePanel(plot));
        //渲染图形，创建DefaultLineRenderer2D
        LineRenderer lines = new DefaultLineRenderer2D();
        //添加到XYPlot对象
        plot.setLineRenderers(data, lines);
        //使用Color类绘制彩色图形
        //Color color = new Color(0.0f, 0.0f, 0.0f);
        Color color = new Color(200, 10, 10);
        Color color1 = new Color(2);
        plot.getPointRenderers(data);  //绘制点
        plot.getLineRenderers(data);  //绘制线条
        plot.setBorderColor(color);
        plot.setBackground(color1);
    }

    public static void main(String[] args) {
        SineGraph frame = null;
        try {
            frame = new SineGraph();
        } catch (IOException e) {
        }
        frame.setVisible(true);
    }
}