package com.cityzen.form;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadingForm extends javax.swing.JFrame {

    public LoadingForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        runProgress();
    }
    

    private void runProgress() {
        
        new Thread(new Runnable() {
            @Override
            public void run() {
               for (int i = 1; i <= 100; i++) {
            try {
                pgB.updateProgress(i);
                pgB.repaint(); // yêu cầu thực hiện lại
                Thread.sleep(20);
                if(i == 100){
                   new DangNhapForm().setVisible(true);
                   LoadingForm.this.dispose();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(LoadingForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            }
        }).start();
        
        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Nen = new javax.swing.JPanel();
        pgB = new com.cityzen.design.swing.ProgressBar();
        lblAnimation = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Nen.setBackground(new java.awt.Color(14, 17, 31));
        Nen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pgB.setBackground(new java.awt.Color(14, 17, 31));
        pgB.setForeground(new java.awt.Color(0, 117, 255));

        javax.swing.GroupLayout pgBLayout = new javax.swing.GroupLayout(pgB);
        pgB.setLayout(pgBLayout);
        pgBLayout.setHorizontalGroup(
            pgBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        pgBLayout.setVerticalGroup(
            pgBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        Nen.add(pgB, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 410, -1, -1));

        lblAnimation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/loading.gif"))); // NOI18N
        Nen.add(lblAnimation, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, -1, -1));

        getContentPane().add(Nen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Nen;
    private javax.swing.JLabel lblAnimation;
    private com.cityzen.design.swing.ProgressBar pgB;
    // End of variables declaration//GEN-END:variables
}
