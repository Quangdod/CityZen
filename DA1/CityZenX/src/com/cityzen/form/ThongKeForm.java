package com.cityzen.form;

import com.cityzen.dao.HoaDonChiTietDAO;
import com.cityzen.dao.HoaDonDAO;
import com.cityzen.dao.NhapSachDAO;
import com.cityzen.design.chart.ModelChart;
import com.cityzen.design.model.Model_Card;
import com.cityzen.entity.HoaDon;
import com.cityzen.entity.NhapSach;
import static com.cityzen.form.MainForm.showForm;
import com.cityzen.ultils.MsgBox;
import java.awt.Color;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class ThongKeForm extends javax.swing.JPanel {

    public ThongKeForm() {
        initComponents();
        testData();
        fillNam(); 
        btnLoad.setEnabled(false);
    }
    
    HoaDonChiTietDAO hdctDao2 = new HoaDonChiTietDAO();
    NhapSachDAO nsd2 = new NhapSachDAO();
    HoaDonDAO hd2 = new HoaDonDAO();

    private void testData() {
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/cityzen/icon/income.png")), "Số sách bán được", hdctDao2.SoSachBan()+""));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/cityzen/icon/other_income.png")), "Số sách nhập vào", nsd2.SoSachNhap()+""));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/cityzen/icon/expense.png")), "Doanh thu", hdctDao2.DoanhThu()+" vnđ"));
        card4.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/cityzen/icon/expense.png")), "Khách hàng", hd2.KhachHang()+""));
    }
    
    void fillNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxNam.getModel();
        model.removeAllElements();
        List<Integer> list = nsd2.selectNam();
        for (Integer kh : list) {
            model.addElement(kh);
        }
        cbxNam.setSelectedIndex(0);
    }
    
    public static int nam2;
    
    void hienThongKe(){
        NhapSachDAO nsd = new NhapSachDAO();
        HoaDonChiTietDAO hdctDao = new HoaDonChiTietDAO();
        chart.addLegend("Nhập sách", new Color(245, 189, 135));
        chart.addLegend("Bán sách", new Color(135, 189, 245));
        chart.addLegend("Doanh thu 1/1000000", new Color(189, 135, 245));
        chart.addData(new ModelChart("Tháng 1", new double[]{nsd.SoSachNhapThang1(), hdctDao.SachBanThang1(), hdctDao.dtt1()}));
        chart.addData(new ModelChart("Tháng 2", new double[]{nsd.SoSachNhapThang2(), hdctDao.SachBanThang2(), hdctDao.dtt2()}));
        chart.addData(new ModelChart("Tháng 3", new double[]{nsd.SoSachNhapThang3(), hdctDao.SachBanThang3(), hdctDao.dtt3()}));
        chart.addData(new ModelChart("Tháng 4", new double[]{nsd.SoSachNhapThang4(), hdctDao.SachBanThang4(), hdctDao.dtt4()}));
        chart.addData(new ModelChart("Tháng 5", new double[]{nsd.SoSachNhapThang5(), hdctDao.SachBanThang5(), hdctDao.dtt5()}));
        chart.addData(new ModelChart("Tháng 6", new double[]{nsd.SoSachNhapThang6(), hdctDao.SachBanThang6(), hdctDao.dtt6()}));
        chart.addData(new ModelChart("Tháng 7", new double[]{nsd.SoSachNhapThang7(), hdctDao.SachBanThang7(), hdctDao.dtt7()}));
        chart.addData(new ModelChart("Tháng 8", new double[]{nsd.SoSachNhapThang8(), hdctDao.SachBanThang8(), hdctDao.dtt8()}));
        chart.addData(new ModelChart("Tháng 9", new double[]{nsd.SoSachNhapThang9(), hdctDao.SachBanThang9(), hdctDao.dtt9()}));
        chart.addData(new ModelChart("Tháng 10", new double[]{nsd.SoSachNhapThang10(), hdctDao.SachBanThang10(), hdctDao.dtt10()}));
        chart.addData(new ModelChart("Tháng 11", new double[]{nsd.SoSachNhapThang11(), hdctDao.SachBanThang11(), hdctDao.dtt11()}));
        chart.addData(new ModelChart("Tháng 12", new double[]{nsd.SoSachNhapThang12(), hdctDao.SachBanThang12(), hdctDao.dtt12()}));
    }
    
    void clear(){
        showForm(new ThongKeForm());
    }
        

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDoanhThu = new javax.swing.JLabel();
        card1 = new com.cityzen.design.component.Card();
        card2 = new com.cityzen.design.component.Card();
        card3 = new com.cityzen.design.component.Card();
        card4 = new com.cityzen.design.component.Card();
        panelShadow1 = new com.cityzen.design.swing.PanelShadow();
        lblThongKeThang = new javax.swing.JLabel();
        chart = new com.cityzen.design.chart.Chart();
        lblThongKeThang1 = new javax.swing.JLabel();
        cbxNam = new com.cityzen.design.swing.Combobox();
        btnXacNhan = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();

        setBackground(new java.awt.Color(242, 246, 253));

        lblDoanhThu.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblDoanhThu.setForeground(new java.awt.Color(140, 110, 207));
        lblDoanhThu.setText("Thống kê tổng");
        lblDoanhThu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        panelShadow1.setForeground(new java.awt.Color(255, 255, 255));

        lblThongKeThang.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblThongKeThang.setForeground(new java.awt.Color(140, 110, 207));
        lblThongKeThang.setText("Thống kê theo tháng");
        lblThongKeThang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        chart.setOpaque(false);

        javax.swing.GroupLayout panelShadow1Layout = new javax.swing.GroupLayout(panelShadow1);
        panelShadow1.setLayout(panelShadow1Layout);
        panelShadow1Layout.setHorizontalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE)
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addComponent(lblThongKeThang)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelShadow1Layout.setVerticalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addComponent(lblThongKeThang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
        );

        lblThongKeThang1.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        lblThongKeThang1.setForeground(new java.awt.Color(140, 110, 207));
        lblThongKeThang1.setText("Chọn năm:");
        lblThongKeThang1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        cbxNam.setBackground(new java.awt.Color(140, 110, 207));
        cbxNam.setForeground(new java.awt.Color(255, 255, 255));
        cbxNam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbxNamMousePressed(evt);
            }
        });
        cbxNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNamActionPerformed(evt);
            }
        });
        cbxNam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbxNamKeyPressed(evt);
            }
        });

        btnXacNhan.setBackground(new java.awt.Color(140, 110, 207));
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        btnLoad.setBackground(new java.awt.Color(140, 110, 207));
        btnLoad.setForeground(new java.awt.Color(255, 255, 255));
        btnLoad.setText("Xóa");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDoanhThu)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblThongKeThang1)
                        .addGap(18, 18, 18)
                        .addComponent(cbxNam, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXacNhan)
                        .addGap(18, 18, 18)
                        .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(panelShadow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDoanhThu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(card2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(card1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThongKeThang1)
                    .addComponent(cbxNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacNhan)
                    .addComponent(btnLoad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelShadow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxNamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxNamKeyPressed
        
    }//GEN-LAST:event_cbxNamKeyPressed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        hienThongKe(); 
        btnLoad.setEnabled(true);
        btnXacNhan.setEnabled(false);
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        clear();
        btnLoad.setEnabled(false);
        btnXacNhan.setEnabled(true);
    }//GEN-LAST:event_btnLoadActionPerformed

    private void cbxNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNamActionPerformed
        nam2 = Integer.parseInt(cbxNam.getSelectedItem().toString());
    }//GEN-LAST:event_cbxNamActionPerformed

    private void cbxNamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxNamMousePressed
        
    }//GEN-LAST:event_cbxNamMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnXacNhan;
    private com.cityzen.design.component.Card card1;
    private com.cityzen.design.component.Card card2;
    private com.cityzen.design.component.Card card3;
    private com.cityzen.design.component.Card card4;
    private com.cityzen.design.swing.Combobox cbxNam;
    private com.cityzen.design.chart.Chart chart;
    private javax.swing.JLabel lblDoanhThu;
    private javax.swing.JLabel lblThongKeThang;
    private javax.swing.JLabel lblThongKeThang1;
    private com.cityzen.design.swing.PanelShadow panelShadow1;
    // End of variables declaration//GEN-END:variables
}
