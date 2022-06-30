package com.cityzen.form;

import com.cityzen.entity.NhaPhanPhoi;
import com.cityzen.ultils.MsgBox;
import java.awt.Color;
import java.util.List;
import com.cityzen.dao.NhaPhanPhoiDao;
import com.cityzen.ultils.Auth;
import com.cityzen.ultils.XImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class NhaPhanPhoiForm extends javax.swing.JPanel {

    public NhaPhanPhoiForm() {
        initComponents();
        this.fillTable();
        this.updateStatus(true);
        //txtMaNPP.setEditable(false);
        tblSach.setDefaultEditor(Object.class, null);  
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
    }

    NhaPhanPhoiDao dao = new NhaPhanPhoiDao();
    int index = 0;
    JFileChooser fileChooser = new JFileChooser();

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        try {
            List<NhaPhanPhoi> list = dao.selectAll();
            for (NhaPhanPhoi npp : list) {
                Object[] row = {
                    npp.getMaNPP(),
                    npp.getTenNPP(),
                    npp.getDiaChi(),
                    npp.getSoDT(),
                    npp.getEmail(),
                    npp.getGhiChu(),
                    npp.getLogo()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "fill table Lỗi truy vấn dữ liệu!");
        }
    }

    void updateStatus(boolean insertable) {
        boolean edit = (this.index >= 0);
        boolean first = (this.index == 0);
        boolean last = (this.index == tblSach.getRowCount() - 1);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    NhaPhanPhoi getModel() {
        NhaPhanPhoi npp = new NhaPhanPhoi();
        npp.setMaNPP(Integer.parseInt(lblMa.getToolTipText()));
        npp.setTenNPP(txtTenNPP.getText());
        npp.setDiaChi(txtDiaChi.getText());
        npp.setSoDT(txtSDT.getText());
        npp.setEmail(txtEmail.getText());
        npp.setGhiChu(txtGhiChu.getText());
        npp.setLogo(lbllogo.getToolTipText());;
        return npp;
    }

    NhaPhanPhoi setModel(NhaPhanPhoi npp) {
        lblMa.setToolTipText(npp.getMaNPP()+"");
        txtTenNPP.setText(npp.getTenNPP());
        txtDiaChi.setText(npp.getDiaChi());
        txtSDT.setText(npp.getSoDT());
        txtEmail.setText(npp.getEmail());
        txtGhiChu.setText(npp.getGhiChu());
        if (npp.getLogo() != null) {
            lbllogo.setToolTipText(npp.getLogo());
            lbllogo.setIcon(XImage.read(npp.getLogo(), lbllogo));
        } else {
            lbllogo.setToolTipText("npphinh.png");
            lbllogo.setIcon(XImage.read("npphinh.png", lbllogo));
        }
        return npp;
    }
    
    void insert() {
        NhaPhanPhoi nv = getModel();
        try {
            dao.insert(nv);
            this.fillTable();
            this.clear();
            btnDanhSachActionPerformed(null);
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
            e.printStackTrace();
        }
    }

    void edit() {
        try {
            Integer manpp = (Integer) tblSach.getValueAt(this.index, 0);
            NhaPhanPhoi model = dao.selectById(manpp);
            if (model != null) {
                this.setModel(model);
                this.updateStatus(true);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "edit Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    void update() {
        NhaPhanPhoi nh = getModel();
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

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa!");
        } else {
            String macd = lblMa.getToolTipText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa nhà phân phối này?")) {
                try {
                    dao.delete(macd);
                    this.clear();
                    this.fillTable(); 
                    btnDanhSachActionPerformed(null);
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Không thể xóa!");
                    MsgBox.alert(this, "Nhà phân phối này đang có trong sách đã nhập!");
                    System.out.println(e);
                }
            }
        }
    }
    
    void clear() {
        NhaPhanPhoi npp = new NhaPhanPhoi();
        this.setModel(npp);
        this.index = -1;
        this.updateStatus(true);
        tblSach.clearSelection();
    }
    
    void timKiem() {
        this.fillTable();
        this.clear();
        this.index = -1;
        updateStatus(true);
    }
    
    void selectImage() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName(), lbllogo);
            lbllogo.setIcon(icon);
            lbllogo.setToolTipText(file.getName());
        }
    }
    
    public boolean validateForm(boolean chk) {
        if (txtTenNPP.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống Tên nhà phân phối!");
            txtTenNPP.requestFocus();
            return false;
        } else if (!txtTenNPP.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ" +
            "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
            "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$")) {
            MsgBox.alert(this, "Tên nhà phân phối không đúng định dạng!");
            txtTenNPP.requestFocus();
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
        } else if (txtGhiChu.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống Ghi chú!");
            txtGhiChu.requestFocus();
            return false;
        } else if (txtDiaChi.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống Địa chỉ!");
            txtDiaChi.requestFocus();
            return false;
        } else if (lbllogo.getToolTipText().equalsIgnoreCase("npphinh.png")) {
            MsgBox.alert(this, "Không được để trống Logo!");
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
        lblMa = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        pnlCapNhat = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        txtSDT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTenNPP = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        lbllogo = new javax.swing.JLabel();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();

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
        jLabel5.setText("QUẢN LÝ NHÀ PHÂN PHỐI");

        lblMa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

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
                    .addComponent(btnDanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(lblMa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(309, 309, 309))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
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
        jLabel2.setText("Địa chỉ:");
        pnlCapNhat.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 55, -1));

        txtGhiChu.setBackground(new java.awt.Color(243, 247, 253));
        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));
        jScrollPane1.setViewportView(txtGhiChu);

        pnlCapNhat.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 242, 299, -1));

        txtSDT.setBackground(new java.awt.Color(243, 247, 253));
        txtSDT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSDT.setBorder(null);
        txtSDT.setPreferredSize(new java.awt.Dimension(197, 20));
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 281, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(140, 110, 207));
        jLabel6.setText("SĐT:");
        pnlCapNhat.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(140, 110, 207));
        jLabel7.setText("Tên NPP:");
        pnlCapNhat.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 52, -1, -1));

        txtTenNPP.setBackground(new java.awt.Color(243, 247, 253));
        txtTenNPP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTenNPP.setBorder(null);
        txtTenNPP.setPreferredSize(new java.awt.Dimension(6, 28));
        txtTenNPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNPPActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtTenNPP, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 74, 299, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(140, 110, 207));
        jLabel8.setText("Email:");
        pnlCapNhat.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 138, -1, -1));

        txtEmail.setBackground(new java.awt.Color(243, 247, 253));
        txtEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtEmail.setBorder(null);
        txtEmail.setPreferredSize(new java.awt.Dimension(197, 20));
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 160, 299, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(140, 110, 207));
        jLabel11.setText("Ghi chú:");
        pnlCapNhat.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 213, -1, -1));

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
        pnlCapNhat.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(936, 130, 91, -1));

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
        pnlCapNhat.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(936, 50, -1, -1));

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
        pnlCapNhat.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(936, 205, 91, -1));

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
        pnlCapNhat.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(936, 283, 91, -1));

        btnPrev.setBackground(new java.awt.Color(140, 110, 207));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-left (1).png"))); // NOI18N
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, 43, -1));

        btnNext.setBackground(new java.awt.Color(140, 110, 207));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-right (1).png"))); // NOI18N
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 43, -1));

        btnLast.setBackground(new java.awt.Color(140, 110, 207));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-right (1).png"))); // NOI18N
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 370, 43, -1));

        btnFirst.setBackground(new java.awt.Color(140, 110, 207));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-left (1).png"))); // NOI18N
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, 43, -1));

        jSeparator1.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 100, 299, 10));

        jSeparator2.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 186, 299, 10));

        jSeparator4.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 189, 281, 10));

        txtDiaChi.setBackground(new java.awt.Color(243, 247, 253));
        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtDiaChi.setRows(5);
        txtDiaChi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));
        jScrollPane4.setViewportView(txtDiaChi);

        pnlCapNhat.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 242, 281, -1));

        jPanel2.setBackground(new java.awt.Color(243, 247, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Logo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(140, 110, 207))); // NOI18N

        lbllogo.setBackground(new java.awt.Color(243, 247, 253));
        lbllogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbllogoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbllogo, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbllogo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlCapNhat.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 32, 290, 100));

        pnlForm.add(pnlCapNhat, "card2");

        pnlDanhSach.setBackground(new java.awt.Color(243, 247, 253));

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NPP", "Tên NPP", "Địa chỉ", "SĐT", "Email", "Ghi chú", "Logo"
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSachMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblSach);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/search.png"))); // NOI18N

        txtTimKiem.setBackground(new java.awt.Color(243, 247, 253));
        txtTimKiem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTimKiem.setBorder(null);
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap(153, Short.MAX_VALUE)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                        .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(233, 233, 233))))
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(172, Short.MAX_VALUE))
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

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void txtTenNPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNPPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNPPActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

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
        btnCapNhat.setEnabled(true);
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

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        timKiem();
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void tblSachMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMousePressed
        
    }//GEN-LAST:event_tblSachMousePressed

    private void lbllogoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbllogoMousePressed
        this.selectImage();
    }//GEN-LAST:event_lbllogoMousePressed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        if (evt.getClickCount() == 2) {
            this.index = tblSach.getSelectedRow();
            System.out.println(index);
            this.edit();
            btnCapNhatActionPerformed(null);
        }
    }//GEN-LAST:event_tblSachMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblCapNhat;
    private javax.swing.JLabel lblDanhSach;
    private javax.swing.JLabel lblMa;
    private javax.swing.JLabel lbllogo;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNPP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
