package com.cityzen.form;

import com.cityzen.design.chart.ModelChart;
import com.cityzen.design.model.Model_Card;
import java.awt.Color;
import javax.swing.ImageIcon;

public class ThoatForm extends javax.swing.JPanel {

    public ThoatForm() {
        initComponents();
        testData();
    }

    private void testData() {
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/cityzen/icon/doitaikhoan.png")), "Đổi Tài Khoản", ""));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/cityzen/icon/doimatkhau.png")), "Đổi mật khẩu", ""));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/cityzen/icon/dangxuat.png")), "Thoát chương trình", ""));
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        card1 = new com.cityzen.design.component.Card();
        card2 = new com.cityzen.design.component.Card();
        card3 = new com.cityzen.design.component.Card();

        setBackground(new java.awt.Color(242, 246, 253));
        setPreferredSize(new java.awt.Dimension(1082, 653));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Hướng ra");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        card1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                card1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                card1MousePressed(evt);
            }
        });

        card2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                card2MousePressed(evt);
            }
        });

        card3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                card3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(card2, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                            .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(425, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void card3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card3MousePressed
        System.exit(0);
    }//GEN-LAST:event_card3MousePressed

    private void card3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card3MouseEntered
        card3.setBackground(new Color(140,110,207));
    }//GEN-LAST:event_card3MouseEntered

    private void card3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card3MouseExited
        card3.setBackground(new Color(242, 246, 253));
    }//GEN-LAST:event_card3MouseExited

    private void card2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card2MouseEntered
        card2.setBackground(new Color(140,110,207));
    }//GEN-LAST:event_card2MouseEntered

    private void card2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card2MouseExited
       card2.setBackground(new Color(242, 246, 253));
    }//GEN-LAST:event_card2MouseExited

    private void card1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card1MouseEntered
        card1.setBackground(new Color(140,110,207));
    }//GEN-LAST:event_card1MouseEntered

    private void card1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card1MouseExited
       card1.setBackground(new Color(242, 246, 253));
    }//GEN-LAST:event_card1MouseExited

    private void card1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_card1MouseClicked

    private void card1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card1MousePressed
        DangNhapForm dn = new DangNhapForm();
        dn.setVisible(true);
        MainForm.an();
    }//GEN-LAST:event_card1MousePressed

    private void card2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card2MousePressed
        MainForm.an();
        DoiMatKhauForm dn = new DoiMatKhauForm();
        dn.setVisible(true);
    }//GEN-LAST:event_card2MousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cityzen.design.component.Card card1;
    private com.cityzen.design.component.Card card2;
    private com.cityzen.design.component.Card card3;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
