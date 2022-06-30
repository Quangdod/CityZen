package com.cityzen.form;

import com.cityzen.form.*;

public class TrangChuForm extends javax.swing.JPanel {

    public TrangChuForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblName = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        lblAnimation = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 246, 253));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblName.setFont(new java.awt.Font("sansserif", 1, 60)); // NOI18N
        lblName.setForeground(new java.awt.Color(140, 110, 207));
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblName.setText("CityZen");
        add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 371, 85));

        lblWelcome.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(140, 110, 207));
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("Chào mừng bạn đến với ");
        add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 469, 57));

        lblAnimation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/book.gif"))); // NOI18N
        lblAnimation.setMaximumSize(new java.awt.Dimension(400, 400));
        lblAnimation.setMinimumSize(new java.awt.Dimension(200, 200));
        lblAnimation.setPreferredSize(new java.awt.Dimension(400, 400));
        add(lblAnimation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1490, 710));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAnimation;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblWelcome;
    // End of variables declaration//GEN-END:variables
}
