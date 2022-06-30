package com.cityzen.form;

import com.cityzen.dao.NhapSachDAO;
import com.cityzen.entity.NhanVien;
import com.cityzen.entity.NhapSach;
import com.cityzen.ultils.Auth;
import com.cityzen.ultils.MsgBox;
import java.awt.Color;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class NhapSachForm extends javax.swing.JPanel {

    public NhapSachForm() {
        initComponents();
        fillTable();
        this.row = -1;
        this.updateStaus();
        tblSach.setDefaultEditor(Object.class, null); 
        txtMaNV.setEditable(false);
        btnThem.setEnabled(false);
    }

    NhapSachDAO dao = new NhapSachDAO();
    int row = -1;


    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);

        try {
            List<NhapSach> list = dao.selectAll();
            for (NhapSach kh : list) {
                Object[] row = {
                    kh.getMaLH(),
                    kh.getMaSach(),
                    kh.getSoLuong(),
                    kh.getGiaNhap(),
                    kh.getMaNPP(),
                    kh.getMaNV(),
                    kh.getNgayNhap(),
                    kh.getGhiChu()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
            e.printStackTrace();
        }
    }

    void setForm(NhapSach ns) {
        lblMaLH.setToolTipText(ns.getMaLH()+"");
        txtSoLuong.setText(ns.getSoLuong() + "");
        txtMaSach.setText(ns.getMaSach());
        txtGiaNhap.setText(ns.getGiaNhap() + "");
        txtGhiChu.setText(ns.getGhiChu());
        txtMaNPP.setText(ns.getMaNPP() + "");
        txtMaNV.setText(Auth.user.getMaNV());
        txtNgayNhap.setDate(ns.getNgayNhap());

    }

    NhapSach getForm() {
        NhapSach ns = new NhapSach();
        ns.setMaLH(Integer.parseInt(lblMaLH.getToolTipText()));
        ns.setMaSach(txtMaSach.getText());
        ns.setNgayNhap(txtNgayNhap.getDate());
        ns.setMaNPP(Integer.parseInt(txtMaNPP.getText()));
        ns.setMaNV(Auth.user.getMaNV());
        ns.setGiaNhap(Float.parseFloat(txtGiaNhap.getText()));
        ns.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        ns.setGhiChu(txtGhiChu.getText());
        return ns;
    }

    void clearForm() {
        NhapSach ns = new NhapSach();
        this.setForm(ns);
        NhanVien nv = new NhanVien();
        nv.setMaNV(Auth.user.getMaNV());
        txtNgayNhap.setDate(null);
        this.row = -1;
        this.updateStaus();
    }

    void insert() {
        NhapSach kh = this.getForm();
        try {
            dao.insert(kh);
            this.fillTable();
            this.clearForm();
            btnDanhSachActionPerformed(null);
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
            e.printStackTrace();
        }
    }

    void update() {
        NhapSach ns = this.getForm();
        try {
            dao.update(ns); //cập nhật
            this.fillTable();//đổ lại bảng
            btnDanhSachActionPerformed(null);
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại");
            e.printStackTrace();
        }
    }

    void delete() {
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa loại sách nhập này?")) {
            String malh = lblMaLH.getToolTipText();
            try {
                dao.delete(malh);
                this.fillTable();
                this.clearForm();
                btnDanhSachActionPerformed(null);
                MsgBox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại!");
                e.printStackTrace();
            }
        }
    }

    void edit() {
        try {
            Integer makh = (Integer) tblSach.getValueAt(this.row, 0);
            NhapSach kh = dao.selectById(makh);
            this.setForm(kh);
            this.updateStaus();
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vẫn dữ liệu!");
        }
    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblSach.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblSach.getRowCount() - 1;
        this.edit();
    }

    void updateStaus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblSach.getRowCount() - 1);
        // Trạng thái form
        btnThem.setEnabled(!edit);
        btnXoa.setEnabled(edit);
        btnSua.setEnabled(edit);

        // Trạng thái điều hướng
        btnFirt3.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);

    }
    
    public boolean validateForm(boolean chk) {
        if (txtMaSach.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống mã sách!");
            txtMaSach.requestFocus();
            return false;
        } else if (!txtMaSach.getText().matches("[A-Z]{2}[0-9]{5}")) {
            MsgBox.alert(this, "mã sách không đúng định dạng!");
            txtMaSach.requestFocus();
            return false;
        } else if (txtMaNPP.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống mã nhà phân phối!");
            txtMaNPP.requestFocus();
            return false;
        } else if (!txtMaNPP.getText().matches("^-?\\d{1,2}$")) {
            MsgBox.alert(this, "mã nhà phân phối không đúng định dạng!");
            txtMaNPP.requestFocus();
            return false;
        } else if (txtSoLuong.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống số lượng!");
            txtSoLuong.requestFocus();
            return false;
        } else if (!txtSoLuong.getText().matches("^-?\\d{1,5}$")) {
            MsgBox.alert(this, "số lượng sách không đúng định dạng!");
            txtSoLuong.requestFocus();
            return false;
        } else if (txtGiaNhap.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống Giá nhập sách!");
            txtGiaNhap.requestFocus();
            return false;
        } else if (!txtGiaNhap.getText().matches("^-?\\d{1,10}$")) {
            MsgBox.alert(this, "giá nhập sách không đúng định dạng!");
            txtGiaNhap.requestFocus();
            return false;
        } else if (txtGhiChu.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống ghi chú sách!");
            txtGhiChu.requestFocus();
            return false;
        } else if (txtNgayNhap.getDate() == null) {
            MsgBox.alert(this, "Không được phép để trống Ngày nhập!");
            txtNgayNhap.requestFocus();
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        btnCapNhat = new javax.swing.JButton();
        lblCapNhat = new javax.swing.JLabel();
        btnDanhSach = new javax.swing.JButton();
        lblDanhSach = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblMaLH = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        pnlCapNhat = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        txtMaNPP = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFirt3 = new javax.swing.JButton();
        txtNgayNhap = new com.toedter.calendar.JDateChooser();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        pnlDanhSach = new javax.swing.JPanel();
        tblNS = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();

        setBackground(new java.awt.Color(242, 246, 253));

        jPanel1.setBackground(new java.awt.Color(243, 247, 253));

        pnlMenu.setBackground(new java.awt.Color(243, 247, 253));

        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(140, 110, 207));
        btnCapNhat.setText("Cập Nhật");
        btnCapNhat.setContentAreaFilled(false);
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat.setFocusable(false);
        btnCapNhat.setMaximumSize(new java.awt.Dimension(127, 27));
        btnCapNhat.setMinimumSize(new java.awt.Dimension(127, 27));
        btnCapNhat.setPreferredSize(new java.awt.Dimension(127, 27));
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        lblCapNhat.setBackground(new java.awt.Color(140, 110, 207));
        lblCapNhat.setOpaque(true);

        btnDanhSach.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDanhSach.setForeground(new java.awt.Color(140, 110, 207));
        btnDanhSach.setText("Danh Sách");
        btnDanhSach.setContentAreaFilled(false);
        btnDanhSach.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDanhSach.setFocusable(false);
        btnDanhSach.setMaximumSize(new java.awt.Dimension(127, 27));
        btnDanhSach.setMinimumSize(new java.awt.Dimension(127, 27));
        btnDanhSach.setPreferredSize(new java.awt.Dimension(127, 27));
        btnDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhSachActionPerformed(evt);
            }
        });

        lblDanhSach.setBackground(new java.awt.Color(255, 255, 255));
        lblDanhSach.setOpaque(true);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(140, 110, 207));
        jLabel5.setText("QUẢN LÝ NHẬP SÁCH");

        lblMaLH.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(lblMaLH)
                        .addGap(0, 972, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(317, 317, 317))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lblMaLH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(7, 7, 7)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pnlForm.setPreferredSize(new java.awt.Dimension(1070, 500));
        pnlForm.setLayout(new java.awt.CardLayout());

        pnlCapNhat.setBackground(new java.awt.Color(243, 247, 253));
        pnlCapNhat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(140, 110, 207));
        jLabel2.setText("Số lượng:");
        pnlCapNhat.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 55, -1));

        txtSoLuong.setBackground(new java.awt.Color(243, 247, 253));
        txtSoLuong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSoLuong.setBorder(null);
        txtSoLuong.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 300, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(140, 110, 207));
        jLabel3.setText("Giá nhập:");
        pnlCapNhat.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, -1, -1));

        txtGiaNhap.setBackground(new java.awt.Color(243, 247, 253));
        txtGiaNhap.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGiaNhap.setBorder(null);
        txtGiaNhap.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtGiaNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 300, 20));

        txtMaNPP.setBackground(new java.awt.Color(243, 247, 253));
        txtMaNPP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaNPP.setBorder(null);
        txtMaNPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNPPActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtMaNPP, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 280, 20));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(140, 110, 207));
        jLabel6.setText("Mã nhà phân phối:");
        pnlCapNhat.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(140, 110, 207));
        jLabel7.setText("Mã sách:");
        pnlCapNhat.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, -1, -1));

        txtMaSach.setBackground(new java.awt.Color(243, 247, 253));
        txtMaSach.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaSach.setBorder(null);
        txtMaSach.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtMaSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 300, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(140, 110, 207));
        jLabel8.setText("Mã nhân viên:");
        pnlCapNhat.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, -1, -1));

        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new java.awt.Color(243, 247, 253));
        txtMaNV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaNV.setBorder(null);
        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 280, 20));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(140, 110, 207));
        jLabel9.setText("Ngày nhập hàng:");
        pnlCapNhat.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(140, 110, 207));
        jLabel11.setText("Ghi chú:");
        pnlCapNhat.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, -1));

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
        pnlCapNhat.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 110, 91, -1));

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
        pnlCapNhat.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 40, -1, -1));

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
        pnlCapNhat.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 180, 91, -1));

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
        pnlCapNhat.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 250, 91, -1));

        btnPrev.setBackground(new java.awt.Color(140, 110, 207));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-left (1).png"))); // NOI18N
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 370, 43, -1));

        btnNext.setBackground(new java.awt.Color(140, 110, 207));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-right (1).png"))); // NOI18N
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, 43, -1));

        btnLast.setBackground(new java.awt.Color(140, 110, 207));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-right (1).png"))); // NOI18N
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 370, 43, -1));

        btnFirt3.setBackground(new java.awt.Color(140, 110, 207));
        btnFirt3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-left (1).png"))); // NOI18N
        btnFirt3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnFirt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirt3ActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnFirt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 370, 43, -1));
        pnlCapNhat.add(txtNgayNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 280, -1));

        jSeparator2.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 280, 10));

        jSeparator3.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 300, 10));

        jSeparator5.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 300, 10));

        jSeparator7.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 300, 10));

        jSeparator8.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 280, 10));

        txtGhiChu.setBackground(new java.awt.Color(243, 247, 253));
        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));
        jScrollPane2.setViewportView(txtGhiChu);

        pnlCapNhat.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, 720, -1));

        pnlForm.add(pnlCapNhat, "card2");

        pnlDanhSach.setBackground(new java.awt.Color(243, 247, 253));

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã LH", "Mã Sách", "Số lượng", "Giá nhập", "Mã NPP", "Mã NV", "Ngày NH", "Ghi chú"
            }
        ));
        tblSach.setFocusable(false);
        tblSach.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblSach.setRowHeight(25);
        tblSach.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tblSach.setShowVerticalLines(false);
        tblSach.getTableHeader().setReorderingAllowed(false);
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        tblNS.setViewportView(tblSach);

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addGap(0, 153, Short.MAX_VALUE)
                .addComponent(tblNS, javax.swing.GroupLayout.PREFERRED_SIZE, 917, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(tblNS, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        pnlForm.add(pnlDanhSach, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        lblCapNhat.setBackground(new Color(140,110,207));
        lblDanhSach.setBackground(new Color(255, 255, 255));

        pnlForm.removeAll();
        pnlForm.repaint();
        pnlForm.revalidate();
        pnlForm.add(pnlCapNhat);
        pnlForm.repaint();
        pnlForm.revalidate();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachActionPerformed
        lblDanhSach.setBackground(new Color(140,110,207));
        lblCapNhat.setBackground(new Color(255, 255, 255));
        pnlForm.removeAll();
        pnlForm.repaint();
        pnlForm.revalidate();
        pnlForm.add(pnlDanhSach);
        pnlForm.repaint();
        pnlForm.revalidate();
    }//GEN-LAST:event_btnDanhSachActionPerformed

    private void txtMaNPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNPPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNPPActionPerformed

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateForm(true)) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validateForm(true)) {
            update();
            clearForm();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
        btnThem.setEnabled(true);
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirt3ActionPerformed
        first();
    }//GEN-LAST:event_btnFirt3ActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblSach.getSelectedRow();
            System.out.println(row);
            this.edit();
            btnCapNhatActionPerformed(null);
        }
    }//GEN-LAST:event_tblSachMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnFirt3;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel lblCapNhat;
    private javax.swing.JLabel lblDanhSach;
    private javax.swing.JLabel lblMaLH;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JScrollPane tblNS;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaNPP;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaSach;
    private com.toedter.calendar.JDateChooser txtNgayNhap;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
