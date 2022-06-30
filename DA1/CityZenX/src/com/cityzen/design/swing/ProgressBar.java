/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.design.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author QUANG
 */
public class ProgressBar extends javax.swing.JPanel {
    int progres = 0;

    public void updateProgress(int progress_value) {
        progres = progress_value;
    }
       @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gra = new GradientPaint(0, 0, new Color(255,0,0), getWidth(), 0, new Color(255,0,0));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // gradient chho background
//         GradientPaint gra = new GradientPaint(0, 0, new Color(56,163,165), getWidth(), 0, new Color(34,87,122));
//        g2.setPaint(gra);
//        g2.fillRect(0, 0, getWidth(), getHeight());
        /*
                KEY_ANTIALIASING khử răng cưa
         */
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // làm cho graphic mịn hơn
        g2.translate(this.getWidth() / 2, this.getHeight() / 2); // xác định tọa độ theo X Y chỉ định
        
        g2.rotate(Math.toRadians(270)); // chuyển đổi góc đo độ sang radian (270=1,5 radian)
        Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);

        Ellipse2D circle = new Ellipse2D.Float(0, 0, 110, 110);
        arc.setFrameFromCenter(new Point(0, 0), new Point(50, 50)); //  độ dài của progressbar

        circle.setFrameFromCenter(new Point(0, 0), new Point(40, 40)); // tạo cái circle thứ hai nhở hơn cái đầu
        
        arc.setAngleStart(1);  // vị trí bắt đầu
        arc.setAngleExtent(-progres * 3.6); // Local end 360/100=3.6
        g2.setColor(new Color(0,117,255));
        g2.draw(arc);
        g2.fill(arc);
        g2.setColor(new Color(14,17,31));////////////////////inside color////////////////////

        g2.draw(circle);
        g2.fill(circle);
        
       
       
        // Phần %
        g2.setColor(new Color(0,193,255)); ////////////////////////
        g2.rotate(Math.toRadians(90)); // chuyển đổi góc đo độ sang radian (90=0,5 radian)
        g.setFont(new Font("Verdana",Font.PLAIN,20));
        FontMetrics fm= g2.getFontMetrics(); // lấy số liệu font từ dạng graphic chỉ định
        Rectangle2D r= fm.getStringBounds(progres + "%", g); // rectangle2d lấy định dạng từ một hình chữ nhật
        // lấy chiều dài rộng chiều y của r qua
        int x=(0-(int)r.getWidth()/2);
        int y=(0-(int)r.getHeight()/2+fm.getAscent());
        g2.drawString(progres + "%", x, y);
    }
}
