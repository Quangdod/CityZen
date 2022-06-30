package com.cityzen.form;

import com.cityzen.dao.HoaDonChiTietDAO;
import com.cityzen.dao.HoaDonDAO;
import com.cityzen.dao.KhachHangDAO;
import com.cityzen.dao.NhanVienDao;
import com.cityzen.dao.NhapSachDAO;
import com.cityzen.dao.SachDAO;
import com.cityzen.entity.NhanVien;
import com.cityzen.design.chart.ModelChart;
import com.cityzen.design.model.Model_Card;
import com.cityzen.entity.HoaDon;
import com.cityzen.entity.HoaDonChiTiet;
import com.cityzen.entity.KhachHang;
import com.cityzen.entity.Sach;
import com.cityzen.ultils.Auth;
import com.cityzen.ultils.MsgBox;
import com.cityzen.ultils.XDate;
import java.awt.Color;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import com.cityzen.entity.NhapSach;

public class BanSachForm extends javax.swing.JPanel {

    int row = -1;
    HoaDonDAO hdDao = new HoaDonDAO();
    HoaDonChiTietDAO hdctDao = new HoaDonChiTietDAO();
    SachDAO sDao = new SachDAO();
    Double subTotal1 = 0.0;
    Double cash = 0.0;
    Double balance = 0.0;

    public BanSachForm() {
        initComponents();
        this.init();
        btnThem.setEnabled(false);
        txtMaNV.setEditable(false);
        txtNgayMua.setEditable(false);
        txtMaHD.setEditable(false);
        txtTienMat.setEditable(false);
        btnTinhTien.setEnabled(false);
        btnPrint.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        btnHoaDonChiTiet = new javax.swing.JButton();
        lblHoaDonChiTiet = new javax.swing.JLabel();
        btnHD = new javax.swing.JButton();
        lblHD = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        pnlHoaDon = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        lblMaKH = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        txtNgayMua = new javax.swing.JTextField();
        lblMaHD1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        pnlHDCT = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        btnMoi1 = new javax.swing.JButton();
        btnPrev2 = new javax.swing.JButton();
        btnNext2 = new javax.swing.JButton();
        btnLast2 = new javax.swing.JButton();
        btnFirst2 = new javax.swing.JButton();
        txtGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        lblMaHDCT = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtBill = new javax.swing.JTextArea();
        btnThem1 = new javax.swing.JButton();
        btnTinhTien = new javax.swing.JButton();
        txtTienMat = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 246, 253));
        setForeground(new java.awt.Color(0, 51, 204));

        jPanel1.setBackground(new java.awt.Color(243, 247, 253));

        pnlMenu.setBackground(new java.awt.Color(243, 247, 253));

        btnHoaDonChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHoaDonChiTiet.setForeground(new java.awt.Color(140, 110, 207));
        btnHoaDonChiTiet.setText("Hóa Đơn Chi Tiết");
        btnHoaDonChiTiet.setContentAreaFilled(false);
        btnHoaDonChiTiet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHoaDonChiTiet.setFocusable(false);
        btnHoaDonChiTiet.setMaximumSize(new java.awt.Dimension(127, 27));
        btnHoaDonChiTiet.setMinimumSize(new java.awt.Dimension(127, 27));
        btnHoaDonChiTiet.setPreferredSize(new java.awt.Dimension(127, 27));
        btnHoaDonChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonChiTietActionPerformed(evt);
            }
        });

        lblHoaDonChiTiet.setBackground(new java.awt.Color(255, 255, 255));
        lblHoaDonChiTiet.setOpaque(true);

        btnHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHD.setForeground(new java.awt.Color(140, 110, 207));
        btnHD.setText("Hóa Đơn");
        btnHD.setContentAreaFilled(false);
        btnHD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHD.setFocusable(false);
        btnHD.setMaximumSize(new java.awt.Dimension(127, 27));
        btnHD.setMinimumSize(new java.awt.Dimension(127, 27));
        btnHD.setPreferredSize(new java.awt.Dimension(127, 27));
        btnHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHDActionPerformed(evt);
            }
        });

        lblHD.setBackground(new java.awt.Color(140, 110, 207));
        lblHD.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(140, 110, 207));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(140, 110, 207));
        jLabel5.setText("QUẢN LÝ BÁN SÁCH");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(lblHD, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHoaDonChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(btnHD, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(259, 259, 259)
                        .addComponent(btnHoaDonChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnHoaDonChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblHD, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHoaDonChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        pnlForm.setPreferredSize(new java.awt.Dimension(1070, 500));
        pnlForm.setLayout(new java.awt.CardLayout());

        pnlHoaDon.setBackground(new java.awt.Color(243, 247, 253));
        pnlHoaDon.setPreferredSize(new java.awt.Dimension(1070, 599));
        pnlHoaDon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(140, 110, 207));
        jLabel2.setText("Mã nhân viên:");
        pnlHoaDon.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 80, -1));

        txtMaNV.setBackground(new java.awt.Color(243, 247, 253));
        txtMaNV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaNV.setBorder(null);
        txtMaNV.setMaximumSize(new java.awt.Dimension(197, 20));
        txtMaNV.setMinimumSize(new java.awt.Dimension(197, 20));
        txtMaNV.setPreferredSize(new java.awt.Dimension(197, 20));
        pnlHoaDon.add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 241, 20));

        txtGhiChu.setBackground(new java.awt.Color(243, 247, 253));
        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));
        jScrollPane1.setViewportView(txtGhiChu);

        pnlHoaDon.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 241, -1));

        lblMaKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaKH.setForeground(new java.awt.Color(140, 110, 207));
        lblMaKH.setText("Mã khách hàng:");
        pnlHoaDon.add(lblMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, -1, -1));

        txtMaKH.setBackground(new java.awt.Color(243, 247, 253));
        txtMaKH.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaKH.setBorder(null);
        txtMaKH.setMaximumSize(new java.awt.Dimension(197, 20));
        txtMaKH.setMinimumSize(new java.awt.Dimension(197, 20));
        txtMaKH.setPreferredSize(new java.awt.Dimension(197, 20));
        pnlHoaDon.add(txtMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 241, 20));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(140, 110, 207));
        jLabel9.setText("Ngày Mua:");
        pnlHoaDon.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(140, 110, 207));
        jLabel10.setText("Ghi chú:");
        pnlHoaDon.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, -1, -1));

        btnXoa.setBackground(new java.awt.Color(140, 110, 207));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/delete-24.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlHoaDon.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 91, -1));

        btnThem.setBackground(new java.awt.Color(140, 110, 207));
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/add-file-24 (1).png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlHoaDon.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 70, -1, -1));

        btnSua.setBackground(new java.awt.Color(140, 110, 207));
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/available-updates-24.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        pnlHoaDon.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 70, 91, -1));

        btnMoi.setBackground(new java.awt.Color(140, 110, 207));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/add-list-24.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        pnlHoaDon.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 130, 91, -1));

        btnPrev.setBackground(new java.awt.Color(140, 110, 207));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-left (1).png"))); // NOI18N
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlHoaDon.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 250, 43, -1));

        btnNext.setBackground(new java.awt.Color(140, 110, 207));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-right (1).png"))); // NOI18N
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlHoaDon.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 250, 43, -1));

        btnLast.setBackground(new java.awt.Color(140, 110, 207));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-right (1).png"))); // NOI18N
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlHoaDon.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 43, -1));

        btnFirst.setBackground(new java.awt.Color(140, 110, 207));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-left (1).png"))); // NOI18N
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlHoaDon.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, 43, -1));

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Mã KH", "Mã NV", "Ngày Mua", "Ghi Chú"
            }
        ));
        tblHD.setFocusable(false);
        tblHD.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblHD.setRowHeight(25);
        tblHD.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tblHD.setShowVerticalLines(false);
        tblHD.getTableHeader().setReorderingAllowed(false);
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHDMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblHD);

        pnlHoaDon.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 880, 234));

        txtNgayMua.setBackground(new java.awt.Color(243, 247, 253));
        txtNgayMua.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtNgayMua.setBorder(null);
        pnlHoaDon.add(txtNgayMua, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 241, 20));
        pnlHoaDon.add(lblMaHD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 196, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/search.png"))); // NOI18N
        pnlHoaDon.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 290, -1, -1));

        txtTimKiem.setBackground(new java.awt.Color(243, 247, 253));
        txtTimKiem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTimKiem.setBorder(null);
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });
        pnlHoaDon.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, 314, 20));

        jSeparator1.setForeground(new java.awt.Color(140, 110, 207));
        pnlHoaDon.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 310, 10));

        jSeparator2.setForeground(new java.awt.Color(140, 110, 207));
        pnlHoaDon.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 240, 10));

        jSeparator3.setForeground(new java.awt.Color(140, 110, 207));
        pnlHoaDon.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 240, 10));

        jSeparator4.setForeground(new java.awt.Color(140, 110, 207));
        pnlHoaDon.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 240, 10));

        pnlForm.add(pnlHoaDon, "card2");

        pnlHDCT.setBackground(new java.awt.Color(243, 247, 253));
        pnlHDCT.setPreferredSize(new java.awt.Dimension(1500, 599));
        pnlHDCT.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(140, 110, 207));
        jLabel4.setText("Mã Sách:");
        pnlHDCT.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 80, -1));

        txtMaSach.setBackground(new java.awt.Color(243, 247, 253));
        txtMaSach.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaSach.setBorder(null);
        txtMaSach.setMaximumSize(new java.awt.Dimension(197, 20));
        txtMaSach.setMinimumSize(new java.awt.Dimension(197, 20));
        txtMaSach.setPreferredSize(new java.awt.Dimension(197, 20));
        txtMaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSachActionPerformed(evt);
            }
        });
        pnlHDCT.add(txtMaSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 170, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(140, 110, 207));
        jLabel8.setText("Mã hóa đơn:");
        pnlHDCT.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(140, 110, 207));
        jLabel11.setText("Tiền mặt:");
        pnlHDCT.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 280, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(140, 110, 207));
        jLabel12.setText("Giá:");
        pnlHDCT.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        btnPrint.setBackground(new java.awt.Color(140, 110, 207));
        btnPrint.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/printer-2-24 (1).png"))); // NOI18N
        btnPrint.setText("In HĐ");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        pnlHDCT.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 520, 120, 40));

        btnMoi1.setBackground(new java.awt.Color(140, 110, 207));
        btnMoi1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnMoi1.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/add-list-24.png"))); // NOI18N
        btnMoi1.setText("Mới");
        btnMoi1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoi1ActionPerformed(evt);
            }
        });
        pnlHDCT.add(btnMoi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 80, 91, -1));

        btnPrev2.setBackground(new java.awt.Color(140, 110, 207));
        btnPrev2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-left (1).png"))); // NOI18N
        btnPrev2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPrev2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrev2ActionPerformed(evt);
            }
        });
        pnlHDCT.add(btnPrev2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 43, -1));

        btnNext2.setBackground(new java.awt.Color(140, 110, 207));
        btnNext2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-right (1).png"))); // NOI18N
        btnNext2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext2ActionPerformed(evt);
            }
        });
        pnlHDCT.add(btnNext2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 43, -1));

        btnLast2.setBackground(new java.awt.Color(140, 110, 207));
        btnLast2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-right (1).png"))); // NOI18N
        btnLast2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLast2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLast2ActionPerformed(evt);
            }
        });
        pnlHDCT.add(btnLast2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, 43, -1));

        btnFirst2.setBackground(new java.awt.Color(140, 110, 207));
        btnFirst2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-left (1).png"))); // NOI18N
        btnFirst2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnFirst2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirst2ActionPerformed(evt);
            }
        });
        pnlHDCT.add(btnFirst2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 43, -1));

        txtGia.setBackground(new java.awt.Color(243, 247, 253));
        txtGia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGia.setBorder(null);
        pnlHDCT.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 170, 20));

        txtSoLuong.setBackground(new java.awt.Color(243, 247, 253));
        txtSoLuong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSoLuong.setBorder(null);
        pnlHDCT.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 170, 20));

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã HDCT", "Mã HD", "Mã Sách", "Số Lượng", "Giá"
            }
        ));
        tblHDCT.setFocusable(false);
        tblHDCT.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblHDCT.setRowHeight(25);
        tblHDCT.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tblHDCT.setShowVerticalLines(false);
        tblHDCT.getTableHeader().setReorderingAllowed(false);
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHDCTMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblHDCT);

        pnlHDCT.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 750, 234));
        pnlHDCT.add(lblMaHDCT, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 214, -1, -1));

        txtMaHD.setBackground(new java.awt.Color(243, 247, 253));
        txtMaHD.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaHD.setBorder(null);
        pnlHDCT.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 170, 20));

        jSeparator5.setForeground(new java.awt.Color(140, 110, 207));
        pnlHDCT.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 170, 10));

        jSeparator6.setForeground(new java.awt.Color(140, 110, 207));
        pnlHDCT.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 170, 10));

        jSeparator7.setForeground(new java.awt.Color(140, 110, 207));
        pnlHDCT.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 170, 10));

        jSeparator8.setForeground(new java.awt.Color(140, 110, 207));
        pnlHDCT.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 170, 10));

        txtBill.setColumns(20);
        txtBill.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBill.setRows(5);
        jScrollPane3.setViewportView(txtBill);

        pnlHDCT.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, 320, 480));

        btnThem1.setBackground(new java.awt.Color(140, 110, 207));
        btnThem1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnThem1.setForeground(new java.awt.Color(255, 255, 255));
        btnThem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/add-file-24 (1).png"))); // NOI18N
        btnThem1.setText("Thêm");
        btnThem1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });
        pnlHDCT.add(btnThem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, -1, -1));

        btnTinhTien.setBackground(new java.awt.Color(140, 110, 207));
        btnTinhTien.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnTinhTien.setForeground(new java.awt.Color(255, 255, 255));
        btnTinhTien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/calculator-8-24.png"))); // NOI18N
        btnTinhTien.setText("Thành Tiền");
        btnTinhTien.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnTinhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhTienActionPerformed(evt);
            }
        });
        pnlHDCT.add(btnTinhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 190, 130, 40));

        txtTienMat.setBackground(new java.awt.Color(243, 247, 253));
        txtTienMat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTienMat.setBorder(null);
        txtTienMat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTienMatMouseClicked(evt);
            }
        });
        pnlHDCT.add(txtTienMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 280, 190, 20));

        jSeparator9.setForeground(new java.awt.Color(140, 110, 207));
        pnlHDCT.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 300, 190, 10));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(140, 110, 207));
        jLabel13.setText("Số lượng:");
        pnlHDCT.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, -1));

        pnlForm.add(pnlHDCT, "card4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 148, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnHoaDonChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonChiTietActionPerformed
        lblHoaDonChiTiet.setBackground(new Color(140, 110, 207));
        lblHD.setBackground(new Color(255, 255, 255));
        pnlForm.removeAll();
        pnlForm.repaint();
        pnlForm.revalidate();
        pnlForm.add(pnlHDCT);
        pnlForm.repaint();
        pnlForm.revalidate();
    }//GEN-LAST:event_btnHoaDonChiTietActionPerformed

    private void btnHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHDActionPerformed
        lblHD.setBackground(new Color(140, 110, 207));
        lblHoaDonChiTiet.setBackground(new Color(255, 255, 255));
        pnlForm.removeAll();
        pnlForm.repaint();
        pnlForm.revalidate();
        pnlForm.add(pnlHoaDon);
        pnlForm.repaint();
        pnlForm.revalidate();
    }//GEN-LAST:event_btnHDActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteHD();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (this.checkHD()) {
            insertHD();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (this.checkHD()) {
            updateHD();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearFormHD();
        btnThem.setEnabled(true);
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prevHD();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextHD();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastHD();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        try {
            cash = Double.valueOf(txtTienMat.getText());
            balance = cash - subTotal1;
            if (balance < 0) {
                MsgBox.alert(this, "Bạn không đủ tiền");
                return;
            }
            if (txtTienMat.getText().length() == 0) {
                MsgBox.alert(this, "Tiền mặt không được nhỏ hơn 0");
            }
            if (!txtTienMat.getText().matches("^-?\\d{1,10}$")) {
                MsgBox.alert(this, "Tiền mặt không đúng định dạng");
            }
            try {
                txtBill.setText(txtBill.getText()
                        + "\t" + "Tiền mặt : " + cash + "\n"
                        + "\t" + "Tiền thối lại: " + balance + "\n"
                        + "-----------------------------------------------------" + "\n"
                        + "\t" + "Develop by: 5 City"
                );

                txtBill.print();
            } catch (Exception e) {
            }
            subTotal1 = 0.0;
            cash = 0.0;
            balance = 0.0;
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            txtBill.setText("-----------------------------------------------------" + "\n"
                    + "\t" + "Nhà sách CityZen" + "\n"
                    + "" + "\tSDT:0985653810" + "\n"
                    + "" + "       26 Phường Thị Minh Khai, Quận 5,HCM" + "\n"
                    + "\t" + "Ngày mua: " + date + "\n"
                    + "----------------------------------------------------" + "\n"
            );
            btnHDActionPerformed(null);
        } catch (Exception e) {
            MsgBox.alert(this, "Bạn chưa nhập tiền mặt");
        }

    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoi1ActionPerformed
        clearFormHDCT();
        btnThem1.setEnabled(true);
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        txtBill.setText("-----------------------------------------------------" + "\n"
                + "\t" + "Nhà sách CityZen" + "\n"
                + "" + "\tSDT:0985653810" + "\n"
                + "" + "       26 Phường Thị Minh Khai, Quận 5,HCM" + "\n"
                + "\t" + "Ngày mua: " + date + "\n"
                + "----------------------------------------------------" + "\n"
        );
    }//GEN-LAST:event_btnMoi1ActionPerformed

    private void btnPrev2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrev2ActionPerformed
        prevHDCT();
    }//GEN-LAST:event_btnPrev2ActionPerformed

    private void btnNext2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext2ActionPerformed
        nextHDCT();
    }//GEN-LAST:event_btnNext2ActionPerformed

    private void btnLast2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLast2ActionPerformed
        lastHDCT();
    }//GEN-LAST:event_btnLast2ActionPerformed

    private void btnFirst2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirst2ActionPerformed
        firstHDCT();
    }//GEN-LAST:event_btnFirst2ActionPerformed

    private void tblHDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMousePressed
        if (evt.getClickCount() == 2) {
            this.row = tblHD.getSelectedRow();
            this.editHD();
        }
    }//GEN-LAST:event_tblHDMousePressed

    private void tblHDCTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMousePressed
        if (evt.getClickCount() == 2) {
            this.row = tblHDCT.getSelectedRow();
            this.editHDCT();
            btnThem1.setEnabled(false);
        }
    }//GEN-LAST:event_tblHDCTMousePressed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstHD();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        fillTableHDSer();
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        if (this.checkHDCT()) {
            truSoLuong();
            Sach s = new Sach();
            s = sDao.selectById(txtMaSach.getText());
            txtBill.setText(txtBill.getText()
                    + s.getTenSach() + "\n" + "\t" + txtSoLuong.getText() + "\t" + txtGia.getText() + "\n"
            );
            subTotal1 = subTotal1 + (Double.valueOf(txtSoLuong.getText()) * Double.valueOf(txtGia.getText()));
            insertHDCT();
            btnTinhTien.setEnabled(true);
        }

    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnTinhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhTienActionPerformed
        txtBill.setText(txtBill.getText() + "-----------------------------------------------------" + "\n"
                + "\t" + "Thanh toán: " + subTotal1 + "\n"
        );
        txtTienMat.setEditable(true);
    }//GEN-LAST:event_btnTinhTienActionPerformed

    private void txtMaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSachActionPerformed

    private void txtTienMatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTienMatMouseClicked
        btnPrint.setEnabled(true);
    }//GEN-LAST:event_txtTienMatMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirst2;
    private javax.swing.JButton btnHD;
    private javax.swing.JButton btnHoaDonChiTiet;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLast2;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnMoi1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNext2;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrev2;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnTinhTien;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblHD;
    private javax.swing.JLabel lblHoaDonChiTiet;
    private javax.swing.JLabel lblMaHD1;
    private javax.swing.JLabel lblMaHDCT;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlHDCT;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTextArea txtBill;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNgayMua;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTienMat;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
    private void init() {
        this.fillToTableHDCT();
        this.fillToTableHD();
        this.updateStatusButtonHD();
        this.updateStatusButtonHDCT();
        tblHD.setDefaultEditor(Object.class, null);
        tblHDCT.setDefaultEditor(Object.class, null);
        billHeader();

    }

    private void fillTableHDSer() {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
        model.setRowCount(0);
        String keyword = txtTimKiem.getText();
        List<HoaDon> list = hdDao.selectNotInCourse(keyword);
        for (HoaDon hd : list) {
            Object[] row
                    = {hd.getMaHD(), hd.getMaKH(), hd.getMaNV(),
                        hd.getNgayMua(), hd.getGhiChu()};
            model.addRow(row);
        }

    }

    private void fillToTableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = hdDao.selectAll();

            for (HoaDon hd : list) {
                Object[] row
                        = {hd.getMaHD(),
                            hd.getMaKH(),
                            hd.getMaNV(),
                            hd.getNgayMua(),
                            hd.getGhiChu(),};
                model.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu Hóa Đơn!");
        }
    }

    private void fillToTableHDCT() {
        DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
        model.setRowCount(0);
        try {
            List<HoaDonChiTiet> list = hdctDao.selectAll();
            for (HoaDonChiTiet hdct : list) {
                Object[] row
                        = {hdct.getMaHDCT(),
                            hdct.getMaHD(),
                            hdct.getMaSach(),
                            hdct.getSoLuong(),
                            hdct.getGia()};
                model.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu hóa đơn chi tiết!");
        }
    }

    private void setFormHD(HoaDon hd) {
        lblMaHD1.setToolTipText(hd.getMaHD() + "");
        txtMaKH.setText(hd.getMaKH() + "");
        txtMaNV.setText(hd.getMaNV());
        txtNgayMua.setText(XDate.toString(hd.getNgayMua()));
        txtGhiChu.setText(hd.getGhiChu());

    }

    private void setFormHDCT(HoaDonChiTiet hdct) {
        lblMaHDCT.setToolTipText(hdct.getMaHDCT() + "");
        txtMaHD.setText(hdct.getMaHD() + "");
        txtMaSach.setText(hdct.getMaSach());
        txtSoLuong.setText(hdct.getSoLuong() + "");
        txtGia.setText(hdct.getGia() + "");
    }

    private HoaDon getFormHD() {
        HoaDon hd = new HoaDon();
        hd.setMaHD(new Integer(lblMaHD1.getToolTipText()));
        hd.setMaKH(new Integer(txtMaKH.getText()));
        hd.setMaNV(txtMaNV.getText());
        hd.setNgayMua(XDate.toDate(txtNgayMua.getText()));
        hd.setGhiChu(txtGhiChu.getText());
        return hd;
    }

    private HoaDonChiTiet getFormHDCT() {
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setMaHDCT(Integer.parseInt(lblMaHDCT.getToolTipText()));
        hdct.setMaHD(new Integer(txtMaHD.getText())); // chú ý
        hdct.setMaSach(txtMaSach.getText());
        hdct.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        hdct.setGia(Float.parseFloat(txtGia.getText()));
        return hdct;
    }

    private void editHD() {
        int maHD = (int) tblHD.getValueAt(row, 0);
        HoaDon hd = hdDao.selectById(maHD);
        setFormHD(hd);
        this.updateStatusButtonHD();
    }

    private void editHDCT() {
        int maHDCT = (int) tblHDCT.getValueAt(row, 0);
        HoaDonChiTiet hdct = hdctDao.selectById(maHDCT);
        setFormHDCT(hdct);
        this.updateStatusButtonHDCT();
    }

    private void clearFormHD() {
        HoaDon hd = new HoaDon();
        this.setFormHD(hd);
        this.row = -1;
        this.getTT();
        this.updateStatusButtonHD();
    }

    private void clearFormHDCT() {
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        this.setFormHDCT(hdct);
        HoaDon hd1 = new HoaDon();
        hd1 = hdDao.selectTop1();
        btnHoaDonChiTietActionPerformed(null);
        txtMaHD.setText(hd1.getMaHD() + "");
        this.row = -1;
        this.updateStatusButtonHDCT();
    }

    private void insertHD() {
        HoaDon hd = getFormHD();
        try {
            hdDao.insert(hd);
            this.fillToTableHD();
            this.clearFormHD();
            HoaDon hd1 = new HoaDon();
            this.clearFormHDCT();
            hd1 = hdDao.selectTop1();  // lấy mã hóa đơn mới vừa thêm vào           
            btnHoaDonChiTietActionPerformed(null);
            txtMaHD.setText(hd1.getMaHD() + "");         // ok
            MsgBox.alert(this, "Thêm hóa đơn thành công!!");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm hóa đơn thất bại!!");
        }
    }

    private void insertHDCT() {
        HoaDonChiTiet hdct = getFormHDCT();
        try {
            hdctDao.insert(hdct);
            this.fillToTableHDCT();
            this.clearFormHDCT();
            MsgBox.alert(this, "Thêm sách vào hóa đơn chi tiết thành công!!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm sách vào hóa đơn chi tiết thất bại!!");
        }
    }

    private void updateHD() {
        HoaDon hd = getFormHD();
        try {
            hdDao.update(hd);
            this.fillToTableHD();
            this.clearFormHD();
            MsgBox.alert(this, "Cập nhật thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    private void updateHDCT() {
        HoaDonChiTiet hdct = getFormHDCT();
        try {
            hdctDao.update(hdct);
            this.fillToTableHDCT();
            this.clearFormHDCT();
            MsgBox.alert(this, "Cập nhật thành công!");

        } catch (Exception e) {

            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    private void deleteHD() {
        int maHD = (Integer) tblHD.getValueAt(row, 0);
        if (MsgBox.confirm(this, "Bạn có chắc chắc xóa Hóa Đơn này!")) {
            try {
                hdDao.delete(maHD);
                this.fillToTableHD();
                this.clearFormHD();
                MsgBox.alert(this, "Xóa hóa đơn thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa hóa đơn thất bại!");
            }
        }
    }

    private void deleteHDCT() {
        int maHDCT = (Integer) tblHDCT.getValueAt(row, 0);
        if (MsgBox.confirm(this, "Bạn có chắc chắc sách trong hóa đơn chi tiếtnày!")) {
            try {
                hdctDao.delete(maHDCT);
                this.fillToTableHDCT();
                this.clearFormHDCT();
                MsgBox.alert(this, "Xóa sách trong hóa đơn thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa sách trong hóa đơn thất bại!");
            }
        }
    }

    private void firstHD() {
        row = 0;
        editHD();
    }

    private void prevHD() {
        if (row > 0) {
            row--;
            editHD();
        }
    }

    private void nextHD() {
        if (row < tblHD.getRowCount() - 1) {
            row++;
            editHD();
        }
    }

    private void lastHD() {
        row = tblHD.getRowCount() - 1;
        editHD();
    }

    private void firstHDCT() {
        row = 0;
        editHDCT();
    }

    private void prevHDCT() {
        if (row > 0) {
            row--;
            editHDCT();
        }
    }

    private void nextHDCT() {
        if (row < tblHDCT.getRowCount() - 1) {
            row++;
            editHDCT();
        }
    }

    private void lastHDCT() {
        row = tblHDCT.getRowCount() - 1;
        editHDCT();
    }

    private void updateStatusButtonHD() {
        boolean edit = (this.row >= 0);
        boolean firt = (this.row == 0);
        boolean last = (this.row == tblHD.getRowCount() - 1);

        // Trạng thái form
//        lblMaHDD.setEnabled(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        // Trạng thái điểu hướng
        btnFirst.setEnabled(edit && !firt);
        btnLast.setEnabled(edit && !last);
        btnNext.setEnabled(edit && !last);
        btnPrev.setEnabled(edit && !firt);
    }

    private void updateStatusButtonHDCT() {
        boolean edit = (this.row >= 0);
        boolean firt = (this.row == 0);
        boolean last = (this.row == tblHDCT.getRowCount() - 1);

        // Trạng thái form
//        txtMaNH.setEditable(!edit);
        btnPrint.setEnabled(!edit);

        // Trạng thái điểu hướng
        btnFirst2.setEnabled(edit && !firt);
        btnLast2.setEnabled(edit && !last);
        btnNext2.setEnabled(edit && !last);
        btnPrev2.setEnabled(edit && !firt);
    }

    private boolean checkHD() {
        String error = "";
        KhachHangDAO khd = new KhachHangDAO();
        if (txtMaKH.getText().length() == 0) {
            error += "Mã Khách hàng không được bỏ trống!\n";
            txtMaKH.setBackground(Color.red);
        } else if (!txtMaKH.getText().matches("[0-9]")) {
            error += "Mã khách hàng không hợp lệ!\n";
            txtMaKH.setBackground(Color.red);
        }
        try {
            KhachHang kh = khd.selectById(Integer.parseInt(txtMaKH.getText()));
            if (kh == null) {
                error += "Mã khách hàng không tồn tại\n";
            }
        } catch (Exception e) {
        }
        if (!error.equals("")) {
            MsgBox.alert(this, error);
        }
        return error.equals("");
    }

    private boolean checkHDCT() {
        String error = "";
        if (txtMaSach.getText().length() == 0) {
            error += "Mã Sách không được bỏ trống!\n";
        } else if (!txtMaSach.getText().matches("[A-Z]{2}[0-9]{5}")) {
            error += "Mã Sách không đúng định dạng !\n";
        }
        try {
            Sach s = sDao.selectById(txtMaSach.getText());
            if (s == null) {
                error += "Mã sách không tồn tại\n";
            }
        } catch (Exception e) {
        }
        try {
            double gia = Double.parseDouble(txtGia.getText());
            if (gia <= 0) {
                error += "Giá tiền phải lớn hơn 0!\n";
            } else if (txtGia.getText().length() == 0) {
                error += "Giá không được bỏ trống!\n";
            } else if (!txtGia.getText().matches("^-?\\d{1,10}$")) {
                error += "Giá tiền không đúng định dạng !\n";
            }
        } catch (NumberFormatException ex) {
            error += "Giá không hợp lệ!\n";
        }
        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) {
                error += "Số lượng học phải lớn hơn 0!\n";
            } else if (txtSoLuong.getText().length() == 0) {
                error += "số lượng không được bỏ trống!\n";
            } else if (!txtSoLuong.getText().matches("^-?\\d{1,5}$")) {
                error += "Số lượng không đúng định dạng !\n";
            }
        } catch (NumberFormatException ex) {
            error += "Số lượng học không hợp lệ\n";
        }
        if (!error.equals("")) {
            MsgBox.alert(this, error);
        }
        return error.equals("");
    }

    private void getTT() {
        String s = Auth.user.getMaNV();
        txtMaNV.setText(s);
    }

    private void billHeader() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        txtBill.setText("-----------------------------------------------------" + "\n"
                + "\t" + "Nhà sách CityZen" + "\n"
                + "" + "\tSDT:0985653810" + "\n"
                + "" + "       26 Phường Thị Minh Khai, Quận 5,HCM" + "\n"
                + "\t" + "Ngày mua: " + date + "\n"
                + "----------------------------------------------------" + "\n"
        );
    }
    // còn mã nhân viên khi nhân viên đăng nhập sẽ lấy mã đó

    public static int soLuongSach;

    public void truSoLuong() {
        int soluong = Integer.parseInt(txtSoLuong.getText());
        soLuongSach = soluong;
        System.out.println(soLuongSach);
        System.out.println(soluong);
        NhapSachDAO nsd = new NhapSachDAO();
        NhapSach ns = doi();
        try {
            nsd.updateSoLuong(ns);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private NhapSach doi() {
        NhapSach ns = new NhapSach();
        ns.setMaSach(txtMaSach.getText());
        return ns;
    }
}
