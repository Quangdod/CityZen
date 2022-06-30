package com.cityzen.form;

import com.cityzen.dao.NhanVienDao;
import com.cityzen.entity.NhanVien;
import com.cityzen.ultils.MsgBox;
import com.cityzen.ultils.Auth;
import com.cityzen.ultils.XImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class NhanVienForm extends javax.swing.JPanel {

    public NhanVienForm() {
        initComponents();
        this.fillTable();
        this.updateStatus(true);
        tblSach.setDefaultEditor(Object.class, null);
        btnThem.setEnabled(false);
    }

    NhanVienDao dao = new NhanVienDao();
    int index = -1;
    JFileChooser fileChooser = new JFileChooser();

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<NhanVien> list = dao.select();
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getGioiTinh(),
                    nv.getSoDT(),
                    nv.getNgaySinh(),
                    nv.getEmail(),
                    nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                    nv.getMatKhau(),
                    nv.getDiaChi(),
                    nv.getGhiChu(),
                    nv.getHinh()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "filltable Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    void insert() {
        NhanVien nv = getModel();
        try {
            dao.insert(nv);
            this.fillTable();
            this.clear();
            btnDanhSachActionPerformed(null);
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Mã nhân viên đã tồn tại!");
            System.out.println(e);
        }
    }

    void edit() {
        try {
            String manv = (String) tblSach.getValueAt(this.index, 0);
            NhanVien model = dao.findById(manv);
            if (model != null) {
                this.setModel(model);
                this.updateStatus(true);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    void update() {
        NhanVien nh = getModel();
        if (nh.getMaNV().equals(Auth.user.getMaNV())) {
            try {
                dao.update(nh);
                this.fillTable();
                btnDanhSachActionPerformed(null);
                MsgBox.alert(this, "Bạn phải đăng nhập lại vì vấn đề bảo mật!");
                MainForm.an();
                DangNhapForm dn = new DangNhapForm();
                dn.setVisible(true);
            } catch (Exception e) {
                MsgBox.alert(this, "Cập nhật thất bại!");
                e.printStackTrace();
            }
        } else {
            try {
                dao.update(nh);
                this.fillTable();
                btnDanhSachActionPerformed(null);
                MsgBox.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Cập nhật thất bại!");
                e.printStackTrace();
            }
        }
    }

    void delete() {
        NhanVien nh = getModel();
        String macd = txtMaNV.getText();
        if (MsgBox.confirm(this, "Bạn có muốn xóa nhân viên này này?")) {
            if (nh.getMaNV().equals(Auth.user.getMaNV())) {
               MsgBox.alert(this, "Bạn không được xóa chính mình!"); 
               return;
            } else {
                try {
                    dao.delete(macd);
                    this.fillTable();
                    this.clear();
                    btnDanhSachActionPerformed(null);
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    void clear() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtMatKhau.setText("");
        txtGhiChu.setText("");
        txtNgaySinh.setDate(null);
        rdoKhac.setSelected(true);
        rdoNhanVien.setSelected(true);
        index = -1;
        this.updateStatus(true);
        lblAnh.setToolTipText("anhnguoi.png");
        lblAnh.setIcon(XImage.read("anhnguoi.png", lblAnh));
    }

//    void clear() {
//        this.setModel(new NhanVien());
//        this.updateStatus(true);
//    }
    void updateStatus(boolean insertable) {
        boolean edit = (this.index >= 0);
        boolean first = (this.index == 0);
        boolean last = (this.index == tblSach.getRowCount() - 1);
        txtMaNV.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    NhanVien getModel() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtTenNV.getText());
        if (rdoNam.isSelected()) {
            nv.setGioiTinh("Nam");
        } else if (rdoNu.isSelected()) {
            nv.setGioiTinh("Nữ");
        }
        if (rdoKhac.isSelected()) {
            nv.setGioiTinh("Khác");
        }
        nv.setSoDT(txtSDT.getText());
        nv.setNgaySinh(txtNgaySinh.getDate());
        nv.setEmail(txtEmail.getText());
        nv.setVaiTro(rdoQuanLy.isSelected());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        nv.setDiaChi(txtDiaChi.getText());
        nv.setGhiChu(txtGhiChu.getText());
        nv.setHinh(lblAnh.getToolTipText());
        return nv;
    }

    NhanVien setModel(NhanVien model) {
        txtMaNV.setText(model.getMaNV());
        txtTenNV.setText(model.getTenNV());
        String gt = model.getGioiTinh();
        if (gt.equalsIgnoreCase("Nam")) {
            rdoNam.setSelected(true);
        } else if (gt.equalsIgnoreCase("Nữ")) {
            rdoNu.setSelected(true);
        } else if (gt.equalsIgnoreCase("Khác")) {
            rdoKhac.setSelected(true);
        }
        txtSDT.setText(model.getSoDT());
        txtNgaySinh.setDate(model.getNgaySinh());
        txtEmail.setText(model.getEmail());
        rdoQuanLy.setSelected(model.isVaiTro());
        rdoNhanVien.setSelected(!model.isVaiTro());
        txtMatKhau.setText(model.getMatKhau());
        txtDiaChi.setText(model.getDiaChi());
        txtGhiChu.setText(model.getGhiChu());
        if (model.getHinh() != null) {
            lblAnh.setToolTipText(model.getHinh());
            lblAnh.setIcon(XImage.read(model.getHinh(), lblAnh));
        } else {
            lblAnh.setToolTipText("anhnguoi.png");
            lblAnh.setIcon(XImage.read("anhnguoi.png", lblAnh));
        }
        return model;
    }

    void selectImage() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName(), lblAnh);
            lblAnh.setIcon(icon);
            lblAnh.setToolTipText(file.getName());
        }
    }

    void timKiem() {
        this.fillTable();
        this.clear();
        this.index = -1;
        updateStatus(true);
    }

    void createQR() {
        try {
            String qr = String.valueOf(tblSach.getModel().getValueAt(tblSach.getSelectedRow(), 0));
            try {
                String qrCodeData = qr;
                String filePath = "D:\\QR.png";
                String charset = "UTF-8"; // or "ISO-8859-1"
                Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                BitMatrix matrix = new MultiFormatWriter().encode(
                        new String(qrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 200, 200, hintMap);
                MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                        .lastIndexOf('.') + 1), new File(filePath));
                MsgBox.alert(this, "Mã QR đã được lưu tại ổ đĩa D");
            } catch (Exception e) {
                System.err.println(e);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Bạn chưa chọn nhân viên cụ thể");
        }
    }

    public boolean validateForm(boolean chk) {
        if (txtMaNV.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống Mã nhân viên!");
            txtMaNV.requestFocus();
            return false;
        } else if (txtMaNV.getText().matches("CTZ[0-9]{3}")) {
            MsgBox.alert(this, "Mã nhân viên không đúng định dạng!");
            txtMaNV.requestFocus();
            return false;
        } else if (txtTenNV.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống tên nhân viên!");
            txtTenNV.requestFocus();
            return false;
        } else if (!txtTenNV.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ"
                + "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ"
                + "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$")) {
            MsgBox.alert(this, "Tên nhân viên không đúng định dạng!");
            txtTenNV.requestFocus();
            return false;
        } else if (txtMatKhau.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống Mật khẩu!");
            txtMatKhau.requestFocus();
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
        } else if (txtEmail.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống Email!");
            txtEmail.requestFocus();
            return false;
        } else if (!txtEmail.getText().matches("\\w+@\\w+(\\.\\w+){1,2}")) {
            MsgBox.alert(this, "Email không đúng định dạng!");
            txtEmail.requestFocus();
            return false;
        } else if (txtDiaChi.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống địa chỉ!");
            txtDiaChi.requestFocus();
            return false;
        } else if (txtGhiChu.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống ghi chú!");
            txtGhiChu.requestFocus();
            return false;
        } else if (lblAnh.getToolTipText().equalsIgnoreCase("anhnguoi.png")) {
            MsgBox.alert(this, "Không được để trống Ảnh!");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        btnCapNhat = new javax.swing.JButton();
        lblCapNhat = new javax.swing.JLabel();
        btnDanhSach = new javax.swing.JButton();
        lblDanhSach = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        pnlCapNhat = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        rdoKhac = new javax.swing.JRadioButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        txtMatKhau = new javax.swing.JPasswordField();
        rdoNhanVien = new javax.swing.JRadioButton();
        rdoQuanLy = new javax.swing.JRadioButton();
        btnHide = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        pnlDanhSach = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tblView = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        btnQR = new javax.swing.JButton();

        setBackground(new java.awt.Color(242, 246, 253));

        jPanel1.setBackground(new java.awt.Color(243, 247, 253));
        jPanel1.setPreferredSize(new java.awt.Dimension(1070, 650));

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
        jLabel5.setText("QUẢN LÝ NHÂN VIÊN");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(lblCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(314, 314, 314))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(0, 62, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(14, 14, 14)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pnlForm.setPreferredSize(new java.awt.Dimension(1070, 500));
        pnlForm.setLayout(new java.awt.CardLayout());

        pnlCapNhat.setBackground(new java.awt.Color(243, 247, 253));
        pnlCapNhat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Mã nhân viên:");
        pnlCapNhat.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, -1, -1));

        txtMaNV.setBackground(new java.awt.Color(243, 247, 253));
        txtMaNV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaNV.setBorder(null);
        txtMaNV.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 223, 20));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(140, 110, 207));
        jLabel2.setText("Giới tính:");
        pnlCapNhat.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 55, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(140, 110, 207));
        jLabel3.setText("SĐT:");
        pnlCapNhat.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, -1, -1));

        txtSDT.setBackground(new java.awt.Color(243, 247, 253));
        txtSDT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSDT.setBorder(null);
        txtSDT.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 190, 240, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(140, 110, 207));
        jLabel4.setText("Email:");
        pnlCapNhat.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, -1));

        txtEmail.setBackground(new java.awt.Color(243, 247, 253));
        txtEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtEmail.setBorder(null);
        txtEmail.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 230, 19));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(140, 110, 207));
        jLabel6.setText("Địa chỉ:");
        pnlCapNhat.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(140, 110, 207));
        jLabel7.setText("Tên nhân viên:");
        pnlCapNhat.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, -1, -1));

        txtTenNV.setBackground(new java.awt.Color(243, 247, 253));
        txtTenNV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTenNV.setBorder(null);
        txtTenNV.setPreferredSize(new java.awt.Dimension(6, 28));
        txtTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNVActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 223, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(140, 110, 207));
        jLabel8.setText("Ngày sinh:");
        pnlCapNhat.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(140, 110, 207));
        jLabel9.setText("Mật khẩu:");
        pnlCapNhat.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(140, 110, 207));
        jLabel10.setText("Vai trò:");
        pnlCapNhat.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(140, 110, 207));
        jLabel11.setText("Ghi chú:");
        pnlCapNhat.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, -1, -1));

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
        pnlCapNhat.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, 91, -1));

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
        pnlCapNhat.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 160, 91, -1));

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
        pnlCapNhat.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 220, 91, -1));

        btnPrev.setBackground(new java.awt.Color(140, 110, 207));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-left (1).png"))); // NOI18N
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 410, 43, -1));

        btnNext.setBackground(new java.awt.Color(140, 110, 207));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-right (1).png"))); // NOI18N
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 410, 43, -1));

        btnLast.setBackground(new java.awt.Color(140, 110, 207));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-right (1).png"))); // NOI18N
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 410, 43, -1));

        btnFirst.setBackground(new java.awt.Color(140, 110, 207));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-left (1).png"))); // NOI18N
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 410, 43, -1));

        rdoNam.setBackground(new java.awt.Color(243, 247, 253));
        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoNam.setForeground(new java.awt.Color(140, 110, 207));
        rdoNam.setText("Nam");
        pnlCapNhat.add(rdoNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, -1, -1));

        rdoNu.setBackground(new java.awt.Color(243, 247, 253));
        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoNu.setForeground(new java.awt.Color(140, 110, 207));
        rdoNu.setText("Nữ");
        pnlCapNhat.add(rdoNu, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, -1, -1));

        rdoKhac.setBackground(new java.awt.Color(243, 247, 253));
        buttonGroup1.add(rdoKhac);
        rdoKhac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoKhac.setForeground(new java.awt.Color(140, 110, 207));
        rdoKhac.setSelected(true);
        rdoKhac.setText("Khác");
        pnlCapNhat.add(rdoKhac, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 270, -1, -1));
        pnlCapNhat.add(txtNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, 240, -1));

        txtMatKhau.setBackground(new java.awt.Color(243, 247, 253));
        txtMatKhau.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMatKhau.setBorder(null);
        pnlCapNhat.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 215, 20));

        rdoNhanVien.setBackground(new java.awt.Color(243, 247, 253));
        buttonGroup2.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoNhanVien.setForeground(new java.awt.Color(140, 110, 207));
        rdoNhanVien.setSelected(true);
        rdoNhanVien.setText("Nhân viên");
        rdoNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNhanVienActionPerformed(evt);
            }
        });
        pnlCapNhat.add(rdoNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, -1, -1));

        rdoQuanLy.setBackground(new java.awt.Color(243, 247, 253));
        buttonGroup2.add(rdoQuanLy);
        rdoQuanLy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoQuanLy.setForeground(new java.awt.Color(140, 110, 207));
        rdoQuanLy.setText("Quản lý");
        pnlCapNhat.add(rdoQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, -1, -1));

        btnHide.setBackground(new java.awt.Color(243, 247, 253));
        btnHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/eye-crossed.png"))); // NOI18N
        btnHide.setContentAreaFilled(false);
        btnHide.setOpaque(true);
        btnHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHideMouseClicked(evt);
            }
        });
        pnlCapNhat.add(btnHide, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 70, 31, 22));

        jSeparator1.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 223, 10));

        jSeparator2.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 223, 10));

        jSeparator3.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 230, 10));

        jSeparator4.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, 240, 10));

        jSeparator5.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, 215, 10));

        txtGhiChu.setBackground(new java.awt.Color(243, 247, 253));
        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));
        jScrollPane2.setViewportView(txtGhiChu);

        pnlCapNhat.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 230, -1));

        jScrollPane1.setBackground(new java.awt.Color(243, 247, 253));

        txtDiaChi.setBackground(new java.awt.Color(243, 247, 253));
        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtDiaChi.setRows(5);
        txtDiaChi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));
        jScrollPane1.setViewportView(txtDiaChi);

        pnlCapNhat.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 250, -1));

        jPanel2.setBackground(new java.awt.Color(243, 247, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ảnh nhân viên", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(140, 110, 207))); // NOI18N

        lblAnh.setBackground(new java.awt.Color(243, 247, 253));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAnhMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
        );

        pnlCapNhat.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 180, 170));

        pnlForm.add(pnlCapNhat, "card2");

        pnlDanhSach.setBackground(new java.awt.Color(243, 247, 253));

        txtTimKiem.setBackground(new java.awt.Color(243, 247, 253));
        txtTimKiem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTimKiem.setBorder(null);
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/search.png"))); // NOI18N

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên NV", "Giới tính", "SĐT", "Ngày sinh", "Email", "Vai trò", "Mật khẩu", "Địa chỉ", "Ghi chú", "Hình"
            }
        ));
        tblSach.setFocusable(false);
        tblSach.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblSach.setMinimumSize(new java.awt.Dimension(800, 550));
        tblSach.setPreferredSize(new java.awt.Dimension(800, 550));
        tblSach.setRowHeight(25);
        tblSach.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tblSach.setShowVerticalLines(false);
        tblSach.getTableHeader().setReorderingAllowed(false);
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSachMousePressed(evt);
            }
        });
        tblView.setViewportView(tblSach);

        jSeparator6.setForeground(new java.awt.Color(140, 110, 207));

        btnQR.setBackground(new java.awt.Color(140, 110, 207));
        btnQR.setForeground(new java.awt.Color(255, 255, 255));
        btnQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/qr.png"))); // NOI18N
        btnQR.setText("Tạo mã QR");
        btnQR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQRMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addGap(352, 352, 352)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDanhSachLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap(146, Short.MAX_VALUE)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tblView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQR, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(tblView, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQR)
                .addGap(124, 124, 124))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
        );
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

    private void txtTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNVActionPerformed

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
            clear();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clear();
        btnThem.setEnabled(true);
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.index--;
        this.edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.index++;
        this.edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.index = tblSach.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.index = 0;
        this.edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void rdoNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNhanVienActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        if (evt.getClickCount() == 2) {
            this.index = tblSach.getSelectedRow();
            this.edit();
            btnCapNhatActionPerformed(null);
        }
    }//GEN-LAST:event_tblSachMouseClicked

    private void lblAnhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMousePressed
        this.selectImage();
    }//GEN-LAST:event_lblAnhMousePressed

    private void tblSachMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMousePressed

    }//GEN-LAST:event_tblSachMousePressed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        timKiem();
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void btnQRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQRMouseClicked
        createQR();
    }//GEN-LAST:event_btnQRMouseClicked

    private void btnHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHideMouseClicked
        if (evt.getClickCount() == 1) {
            txtMatKhau.setEchoChar((char) 0);
        } else {
            txtMatKhau.setEchoChar('•');
        }
    }//GEN-LAST:event_btnHideMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnHide;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblCapNhat;
    private javax.swing.JLabel lblDanhSach;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JRadioButton rdoKhac;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTable tblSach;
    private javax.swing.JScrollPane tblView;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
