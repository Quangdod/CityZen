package com.cityzen.form;

import com.cityzen.dao.KhachHangDAO;
import com.cityzen.entity.KhachHang;
import com.cityzen.ultils.Auth;
import com.cityzen.ultils.MsgBox;
import com.cityzen.ultils.XImage;
import java.awt.Color;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class KhachHangForm extends javax.swing.JPanel {

    public KhachHangForm() {
        initComponents();
        init();
        tblSach.setDefaultEditor(Object.class, null);
        btnThem.setEnabled(false);
    }

    KhachHangDAO dao = new KhachHangDAO();
    int row = -1;

    void init() {
        fillTable();
        this.row = -1;
        this.updateStatus(true);
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);

        try {
            String keyword = txtTimKiem.getText();
            List<KhachHang> list = dao.selectByKeyword(keyword);
            for (KhachHang kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getGioiTinh(),
                    kh.getSoDT(),
                    kh.getNgaySinh(),
                    kh.getDiaChi(),
                    kh.getCapKH(),
                    kh.getGhiChu(),
                    kh.getHinh()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "fill table Lỗi truy vấn dữ liệu!");
        }
    }

    void setForm(KhachHang kh) {
        lblMaKhachHang.setToolTipText(kh.getMaKH() + "");
        txtTenKH.setText(kh.getTenKH());
        String gt = kh.getGioiTinh();
        if (gt.equalsIgnoreCase("Nam")) {
            rdoNam.setSelected(true);
        } else if (gt.equalsIgnoreCase("Nữ")) {
            rdoNu.setSelected(true);
        } else if (gt.equalsIgnoreCase("Khác")) {
            rdoKhac.setSelected(true);
        }
        txtSDT.setText(kh.getSoDT());
        txtNgaySinh.setDate(kh.getNgaySinh());
        txtDiaChi.setText(kh.getDiaChi());
        txtGhiChu.setText(kh.getGhiChu());
        txtCapKH.setText(kh.getCapKH() + "");
        if (kh.getHinh() != null) {
            lblAnh.setToolTipText(kh.getHinh());
            lblAnh.setIcon(XImage.read(kh.getHinh(), lblAnh));
        } else {
            lblAnh.setToolTipText("anhnguoi.png");
            lblAnh.setIcon(XImage.read("anhnguoi.png", lblAnh));
        }
    }

    KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setMaKH(Integer.parseInt(lblMaKhachHang.getToolTipText()));
        kh.setTenKH(txtTenKH.getText());
        kh.setDiaChi(txtDiaChi.getText());
        kh.setNgaySinh(txtNgaySinh.getDate());
        kh.setGhiChu(txtGhiChu.getText());
        kh.setSoDT(txtSDT.getText());
        kh.setCapKH(Integer.valueOf(txtCapKH.getText()));
        if (rdoNam.isSelected()) {
            kh.setGioiTinh("Nam");
        } else if (rdoNu.isSelected()) {
            kh.setGioiTinh("Nữ");
        } else {
            kh.setGioiTinh("Khác");
        }
        kh.setHinh(lblAnh.getToolTipText());
        return kh;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    void clearForm() {
        KhachHang kh = new KhachHang();
        kh.setGioiTinh("Khác");
        this.setForm(kh);
        this.row = -1;
        this.updateStatus(true);
        tblSach.clearSelection();
    }

    void insert() {
        KhachHang kh = this.getForm();
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
        KhachHang kh = this.getForm();
        try {
            dao.update(kh);
            this.fillTable();
            btnDanhSachActionPerformed(null);
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
            e.printStackTrace();
        }
    }

    KhachHang getModelCKH() {
        KhachHang model = new KhachHang();
        model.setMaKH((int) tblSach.getModel().getValueAt(tblSach.getSelectedRow(), 0));
        model.setCapKH((int) tblSach.getModel().getValueAt(tblSach.getSelectedRow(), 6));
        return model;
    }

    void capKH() {
        KhachHang model = getModelCKH();
        int ts = (int) tblSach.getModel().getValueAt(tblSach.getSelectedRow(), 6);
        try {
            if (ts > 4) {
                MsgBox.alert(this, "Đã đạt cấp tối đa");
            } else {
                try {
                    dao.DoiMatKhau(model);
                    this.fillTable();
                    MsgBox.alert(this, "Nâng cấp khách hàng thành công");
                } catch (Exception e) {
                    System.out.println(e);
                    MsgBox.alert(this, "Nâng cấp thất bại!");
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Bạn chưa chọn khách hàng để nâng cấp");
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa!");
        } else {
            String macd = lblMaKhachHang.getToolTipText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa khách hàng này?")) {
                try {
                    dao.delete(macd);
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
    }

    void edit() {
        try {
            Integer makh = (Integer) tblSach.getValueAt(this.row, 0);
            KhachHang kh = dao.selectById(makh);
            this.setForm(kh);
            this.updateStatus(true);
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

    void updateStatus(boolean insertable) {
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

    void timKiem() {
        this.fillTable();
        this.clearForm();
        this.row = -1;
        updateStatus(true);
    }

    void chonAnh() {
        JFileChooser fchChooser = new JFileChooser();
        if (fchChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fchChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName(), lblAnh);
            lblAnh.setIcon(icon);
            lblAnh.setToolTipText(file.getName());
        }
    }

    public boolean validateForm(boolean chk) {
        if (txtTenKH.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống tên khách hàng!");
            txtTenKH.requestFocus();
            return false;
        } else if (!txtTenKH.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ" +
            "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
            "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$")) {
            MsgBox.alert(this, "Tên khách hàng không đúng định dạng!");
            txtTenKH.requestFocus();
            return false;
        } else if (txtNgaySinh.getDate() == null) {
            MsgBox.alert(this, "Không được phép để trống Ngày sinh!");
            txtNgaySinh.requestFocus();
            return false;
        } else if (txtSDT.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống Số điện thoại!");
            txtSDT.requestFocus();
            return false;
        } else if (!txtSDT.getText().matches("((84)|(0))\\d{9}")) {
            MsgBox.alert(this, "Không đúng định dạng Số điện thoại!");
            txtSDT.requestFocus();
            return false;
        } else if (txtCapKH.getText().length() == 0) {
            MsgBox.alert(this, "Bạn chưa nhập cấp khách hàng!");
            txtCapKH.requestFocus();
            return false;
        } else if (!txtCapKH.getText().matches("^\\d{1,2}$")) {
            MsgBox.alert(this, "Cấp khách hàng không đúng định dạng!");
            txtCapKH.requestFocus();
            return false;
        } else if (txtGhiChu.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống Ghi chú!");
            txtGhiChu.requestFocus();
            return false;
        } else if (txtDiaChi.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống Địa chỉ!");
            txtDiaChi.requestFocus();
            return false;
        } else if (lblAnh.getToolTipText().equalsIgnoreCase("anhnguoi.png")) {
            MsgBox.alert(this, "Không được để trống ảnh!");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        btnCapNhat = new javax.swing.JButton();
        lblCapNhat = new javax.swing.JLabel();
        btnDanhSach = new javax.swing.JButton();
        lblDanhSach = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblMaKhachHang = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        pnlCapNhat = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        txtCapKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
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
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        rdoKhac = new javax.swing.JRadioButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        btnNangCap = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(242, 246, 253));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(243, 247, 253));

        pnlMenu.setBackground(new java.awt.Color(243, 247, 253));
        pnlMenu.setForeground(new java.awt.Color(140, 110, 207));

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
        jLabel5.setText("QUẢN LÝ KHÁCH HÀNG");

        lblMaKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(lblMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(328, 328, 328))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlForm.setPreferredSize(new java.awt.Dimension(1070, 500));
        pnlForm.setLayout(new java.awt.CardLayout());

        pnlCapNhat.setBackground(new java.awt.Color(243, 247, 253));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(140, 110, 207));
        jLabel2.setText("Giới tính:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(140, 110, 207));
        jLabel4.setText("SĐT:");

        txtSDT.setBackground(new java.awt.Color(243, 247, 253));
        txtSDT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSDT.setBorder(null);
        txtSDT.setPreferredSize(new java.awt.Dimension(6, 28));

        txtCapKH.setBackground(new java.awt.Color(243, 247, 253));
        txtCapKH.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCapKH.setBorder(null);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(140, 110, 207));
        jLabel6.setText("Cấp khách hàng:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(140, 110, 207));
        jLabel7.setText("Tên khách hàng:");

        txtTenKH.setBackground(new java.awt.Color(243, 247, 253));
        txtTenKH.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTenKH.setBorder(null);
        txtTenKH.setPreferredSize(new java.awt.Dimension(6, 28));
        txtTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKHActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(140, 110, 207));
        jLabel8.setText("Địa chỉ:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(140, 110, 207));
        jLabel9.setText("Ngày sinh:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(140, 110, 207));
        jLabel11.setText("Ghi chú:");

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

        btnPrev.setBackground(new java.awt.Color(140, 110, 207));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-left (1).png"))); // NOI18N
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(140, 110, 207));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-right (1).png"))); // NOI18N
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(140, 110, 207));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-right (1).png"))); // NOI18N
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnFirt3.setBackground(new java.awt.Color(140, 110, 207));
        btnFirt3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-left (1).png"))); // NOI18N
        btnFirt3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnFirt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirt3ActionPerformed(evt);
            }
        });

        rdoNam.setBackground(new java.awt.Color(243, 247, 253));
        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoNam.setForeground(new java.awt.Color(140, 110, 207));
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(243, 247, 253));
        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoNu.setForeground(new java.awt.Color(140, 110, 207));
        rdoNu.setText("Nữ");

        rdoKhac.setBackground(new java.awt.Color(243, 247, 253));
        buttonGroup1.add(rdoKhac);
        rdoKhac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoKhac.setForeground(new java.awt.Color(140, 110, 207));
        rdoKhac.setSelected(true);
        rdoKhac.setText("Khác");

        txtNgaySinh.setDateFormatString("dd-MM-YYYY");

        jSeparator2.setForeground(new java.awt.Color(140, 110, 207));

        jSeparator3.setForeground(new java.awt.Color(140, 110, 207));

        jSeparator4.setForeground(new java.awt.Color(140, 110, 207));

        txtGhiChu.setBackground(new java.awt.Color(243, 247, 253));
        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));
        jScrollPane4.setViewportView(txtGhiChu);

        txtDiaChi.setBackground(new java.awt.Color(243, 247, 253));
        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtDiaChi.setRows(5);
        txtDiaChi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));
        jScrollPane1.setViewportView(txtDiaChi);

        jPanel2.setBackground(new java.awt.Color(243, 247, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ảnh khách hàng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(140, 110, 207))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(140, 110, 207));
        jPanel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAnhMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlCapNhatLayout = new javax.swing.GroupLayout(pnlCapNhat);
        pnlCapNhat.setLayout(pnlCapNhatLayout);
        pnlCapNhatLayout.setHorizontalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFirt3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(335, 335, 335))
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                                    .addComponent(rdoNam)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(rdoNu)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(rdoKhac))
                                                .addComponent(jLabel7)
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3)
                            .addComponent(txtCapKH)
                            .addComponent(jSeparator4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        pnlCapNhatLayout.setVerticalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(jLabel7)
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnXoa))
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoNam)
                                    .addComponent(rdoNu)
                                    .addComponent(rdoKhac)))
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSua)
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(txtCapKH, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(btnThem))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(btnMoi)))
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrev, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFirt3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLast, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(35, 35, 35))
        );

        pnlForm.add(pnlCapNhat, "card2");

        pnlDanhSach.setBackground(new java.awt.Color(243, 247, 253));

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã KH", "Tên KH", "Giới Tính", "SDT", "Ngày Sinh", "Địa Chỉ", "Cấp KH", "Ghi chú", "Hình"
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
        jScrollPane2.setViewportView(tblSach);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(140, 110, 207));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/search.png"))); // NOI18N

        btnNangCap.setBackground(new java.awt.Color(140, 110, 207));
        btnNangCap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNangCap.setForeground(new java.awt.Color(255, 255, 255));
        btnNangCap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/upload.png"))); // NOI18N
        btnNangCap.setText("Nâng cấp KH");
        btnNangCap.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNangCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNangCapActionPerformed(evt);
            }
        });

        txtTimKiem.setBackground(new java.awt.Color(242, 246, 253));
        txtTimKiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTimKiem.setBorder(null);
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(140, 110, 207));

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 927, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE))
                        .addGap(184, 184, 184))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                        .addComponent(btnNangCap)
                        .addContainerGap())))
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNangCap)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pnlForm.add(pnlDanhSach, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 690));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        lblCapNhat.setBackground(new Color(140, 110, 207));
        lblDanhSach.setBackground(new Color(255, 255, 255));

        pnlForm.removeAll();
        pnlForm.repaint();
        pnlForm.revalidate();
        pnlForm.add(pnlCapNhat);
        pnlForm.repaint();
        pnlForm.revalidate();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachActionPerformed
        lblDanhSach.setBackground(new Color(140, 110, 207));
        lblCapNhat.setBackground(new Color(255, 255, 255));
        pnlForm.removeAll();
        pnlForm.repaint();
        pnlForm.revalidate();
        pnlForm.add(pnlDanhSach);
        pnlForm.repaint();
        pnlForm.revalidate();
    }//GEN-LAST:event_btnDanhSachActionPerformed

    private void txtTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHActionPerformed

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

    private void btnNangCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNangCapActionPerformed
        capKH();
    }//GEN-LAST:event_btnNangCapActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblSach.getSelectedRow();
            this.edit();
            btnCapNhatActionPerformed(null);
        }
    }//GEN-LAST:event_tblSachMouseClicked

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        this.timKiem();
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void lblAnhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMousePressed
        chonAnh();
    }//GEN-LAST:event_lblAnhMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnFirt3;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNangCap;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblCapNhat;
    private javax.swing.JLabel lblDanhSach;
    private javax.swing.JLabel lblMaKhachHang;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JRadioButton rdoKhac;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtCapKH;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextArea txtGhiChu;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
