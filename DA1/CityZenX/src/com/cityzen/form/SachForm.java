package com.cityzen.form;

import com.cityzen.dao.SachDAO;
import com.cityzen.entity.Sach;
import com.cityzen.ultils.Auth;
import com.cityzen.ultils.MsgBox;
import com.cityzen.ultils.XImage;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SachForm extends javax.swing.JPanel {

    public SachForm() {
        initComponents();
        updateStatus();
        fillToTable();
        tblSach.setDefaultEditor(Object.class, null);
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
    }

    SachDAO sachDAO = new SachDAO();
    int row = 0;
    JFileChooser fileChooser = new JFileChooser();

    void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTim.getText();
            List<Sach> list = sachDAO.selectByKeyword(keyword);
            for (Sach s : list) {
                Object[] rows = {
                    s.getMaSach(),
                    s.getTenSach(),
                    s.getTacGia(),
                    s.getTenLoai(),
                    s.getSoTrang(),
                    s.getGia(),
                    s.getNhaXuatBan(),
                    s.getViTri(),
                    s.getDiaChi(),
                    s.getHinh()
                };
                model.addRow(rows);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu !");
            e.printStackTrace();
        }
    }

    void setForm(Sach sa) {
        txtMaSach.setText(sa.getMaSach());
        txtTenSach.setText(sa.getTenSach());
        txtTenLoai.setText(sa.getTenLoai());
        txtSoTrang.setText(sa.getSoTrang() + "");
        txtViTri.setText(sa.getDiaChi());
        txtGia.setText(sa.getGia() + "");
        txtTG.setText(sa.getTacGia());
        txtNhaXuatBan.setText(sa.getNhaXuatBan());
        txtDiaChi.setText(sa.getViTri());
        if (sa.getHinh() != null) {
            lblAnh.setToolTipText(sa.getHinh());
            lblAnh.setIcon(XImage.read(sa.getHinh(), lblAnh));
        } else {
            lblAnh.setToolTipText("anhsach.png");
            lblAnh.setIcon(XImage.read("anhsach.png", lblAnh));
        }
    }

    Sach getForm() {
        Sach sach = new Sach();
        sach.setMaSach(txtMaSach.getText());
        sach.setTenSach(txtTenSach.getText());
        sach.setTenLoai(txtTenLoai.getText());
        sach.setSoTrang(Integer.parseInt(txtSoTrang.getText()));
        sach.setDiaChi(txtViTri.getText());
        sach.setGia(Float.parseFloat(txtGia.getText()));
        sach.setTacGia(txtTG.getText());
        sach.setNhaXuatBan(txtNhaXuatBan.getText());
        sach.setViTri(txtDiaChi.getText());
        sach.setHinh(lblAnh.getToolTipText());
        return sach;
    }

    void updateStatus() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblSach.getRowCount() - 1;
        // Trạng thái form
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        // Trạng thái điều hướng
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void clearForm() {
        Sach cd = new Sach();
        this.setForm(cd);
        this.row = -1;
        this.updateStatus();
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

    void insert() {
        Sach s = getForm();
        try {
            sachDAO.insert(s);
            this.fillToTable();
            this.clearForm();
            btnDanhSachActionPerformed(null);
            MsgBox.alert(this, "Thêm mới thành công !");
            return;
        } catch (Exception e) {
            MsgBox.alert(this, "Mã sách đã tồn tại!");
            System.out.println(e);
            return;
        }

    }

    void update() {
        Sach s = getForm();

        try {
            sachDAO.update(s);
            this.fillToTable();
            btnDanhSachActionPerformed(null);
            MsgBox.alert(this, "Cập nhật thành công !");
            return;
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại !");
            e.printStackTrace();
        }

    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa sách!");
        } else {
            String maSach = txtMaSach.getText();
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa sách này ?")) {
                try {
                    sachDAO.delete(maSach);
                    this.fillToTable();
                    this.clearForm();
                    btnDanhSachActionPerformed(null);
                    MsgBox.alert(this, "Xóa thành công !");
                    return;
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại !");
                    e.printStackTrace();
                }
            }
        }
    }

    void edit() {
        String maSach = (String) tblSach.getValueAt(row, 0);
        Sach sach = sachDAO.selectById(maSach);
        this.setForm(sach);

        this.updateStatus();
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

    private void timkiem() {
        this.fillToTable();
        this.clearForm();
        this.row = -1;
        this.updateStatus();
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
        } else if (txtTenSach.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống tên sách!");
            txtTenSach.requestFocus();
            return false;
        } else if (txtTG.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống tên tác giả!");
            txtTG.requestFocus();
            return false;
        } else if (!txtTG.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ"
                + "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ"
                + "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$")) {
            MsgBox.alert(this, "Tên tác giả không đúng định dạng!");
            txtTG.requestFocus();
            return false;
        } else if (txtNhaXuatBan.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống tên nhà xuất bản!");
            txtTenSach.requestFocus();
            return false;
        } else if (txtTenLoai.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống tên loại sách!");
            txtTenLoai.requestFocus();
            return false;
        } else if (txtDiaChi.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống địa chỉ!");
            txtDiaChi.requestFocus();
            return false;
        } else if (txtViTri.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống vị trí!");
            txtTenLoai.requestFocus();
            return false;
        } else if (txtSoTrang.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống số trang!");
            txtTenLoai.requestFocus();
            return false;
        } else if (!txtSoTrang.getText().matches("^-?\\d{1,5}$")) {
            MsgBox.alert(this, "số trang không đúng định dạng!");
            txtMaSach.requestFocus();
            return false;
        } else if (txtGia.getText().length() == 0) {
            MsgBox.alert(this, "Không được để trống Giá sách!");
            txtTenLoai.requestFocus();
            return false;
        } else if (!txtGia.getText().matches("^-?\\d{1,10}$")) {
            MsgBox.alert(this, "giá sách không đúng định dạng!");
            txtGia.requestFocus();
            return false;
        } else if (lblAnh.getToolTipText().equalsIgnoreCase("anhsach.png")) {
            MsgBox.alert(this, "Không được để trống ảnh!");
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
        pnlForm = new javax.swing.JPanel();
        pnlCapNhat = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenLoai = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSoTrang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtViTri = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNhaXuatBan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        txtTG = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();

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
        jLabel5.setText("QUẢN LÝ SÁCH");

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
                .addGap(105, 105, 105))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(496, 496, 496))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(0, 48, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(28, 28, 28)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                        .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pnlForm.setPreferredSize(new java.awt.Dimension(1070, 500));
        pnlForm.setLayout(new java.awt.CardLayout());

        pnlCapNhat.setBackground(new java.awt.Color(243, 247, 253));
        pnlCapNhat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Mã Sách:");
        pnlCapNhat.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        txtMaSach.setBackground(new java.awt.Color(243, 247, 253));
        txtMaSach.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaSach.setBorder(null);
        txtMaSach.setMaximumSize(new java.awt.Dimension(197, 20));
        txtMaSach.setMinimumSize(new java.awt.Dimension(197, 20));
        txtMaSach.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtMaSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 270, 20));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(140, 110, 207));
        jLabel2.setText("Tên Loại:");
        pnlCapNhat.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 55, -1));

        txtTenLoai.setBackground(new java.awt.Color(243, 247, 253));
        txtTenLoai.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTenLoai.setBorder(null);
        txtTenLoai.setMaximumSize(new java.awt.Dimension(197, 20));
        txtTenLoai.setMinimumSize(new java.awt.Dimension(197, 20));
        txtTenLoai.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtTenLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 270, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(140, 110, 207));
        jLabel3.setText("Số trang:");
        pnlCapNhat.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, -1, -1));

        txtSoTrang.setBackground(new java.awt.Color(243, 247, 253));
        txtSoTrang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSoTrang.setBorder(null);
        txtSoTrang.setMaximumSize(new java.awt.Dimension(197, 20));
        txtSoTrang.setMinimumSize(new java.awt.Dimension(197, 20));
        txtSoTrang.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtSoTrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 120, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(140, 110, 207));
        jLabel4.setText("Địa chỉ:");
        pnlCapNhat.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, -1, -1));

        txtViTri.setBackground(new java.awt.Color(243, 247, 253));
        txtViTri.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtViTri.setBorder(null);
        txtViTri.setMaximumSize(new java.awt.Dimension(197, 20));
        txtViTri.setMinimumSize(new java.awt.Dimension(197, 20));
        txtViTri.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtViTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 120, 20));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(140, 110, 207));
        jLabel7.setText("Tên Sách:");
        pnlCapNhat.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, -1, -1));

        txtTenSach.setBackground(new java.awt.Color(243, 247, 253));
        txtTenSach.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTenSach.setBorder(null);
        txtTenSach.setMaximumSize(new java.awt.Dimension(197, 20));
        txtTenSach.setMinimumSize(new java.awt.Dimension(197, 20));
        txtTenSach.setPreferredSize(new java.awt.Dimension(6, 28));
        pnlCapNhat.add(txtTenSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 270, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(140, 110, 207));
        jLabel8.setText("Giá:");
        pnlCapNhat.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 230, -1, -1));

        txtGia.setBackground(new java.awt.Color(243, 247, 253));
        txtGia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGia.setBorder(null);
        txtGia.setMaximumSize(new java.awt.Dimension(197, 20));
        txtGia.setMinimumSize(new java.awt.Dimension(197, 20));
        txtGia.setPreferredSize(new java.awt.Dimension(197, 20));
        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 270, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(140, 110, 207));
        jLabel9.setText("Tác giả:");
        pnlCapNhat.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(140, 110, 207));
        jLabel10.setText("Nhà xuất bản:");
        pnlCapNhat.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, -1, -1));

        txtNhaXuatBan.setBackground(new java.awt.Color(243, 247, 253));
        txtNhaXuatBan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtNhaXuatBan.setBorder(null);
        txtNhaXuatBan.setMaximumSize(new java.awt.Dimension(197, 20));
        txtNhaXuatBan.setMinimumSize(new java.awt.Dimension(197, 20));
        txtNhaXuatBan.setPreferredSize(new java.awt.Dimension(197, 20));
        txtNhaXuatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhaXuatBanActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtNhaXuatBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 100, 280, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(140, 110, 207));
        jLabel11.setText("Vị trí:");
        pnlCapNhat.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, -1));

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
        pnlCapNhat.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 100, 91, -1));

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
        pnlCapNhat.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, -1, -1));

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
        pnlCapNhat.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 170, 91, -1));

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
        pnlCapNhat.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 240, 91, -1));

        btnPrev.setBackground(new java.awt.Color(140, 110, 207));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-left (1).png"))); // NOI18N
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, 43, -1));

        btnNext.setBackground(new java.awt.Color(140, 110, 207));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-small-right (1).png"))); // NOI18N
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 370, 43, -1));

        btnLast.setBackground(new java.awt.Color(140, 110, 207));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-right (1).png"))); // NOI18N
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 370, 43, -1));

        btnFirst.setBackground(new java.awt.Color(140, 110, 207));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/angle-double-small-left (1).png"))); // NOI18N
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlCapNhat.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 43, -1));

        txtTG.setBackground(new java.awt.Color(243, 247, 253));
        txtTG.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTG.setBorder(null);
        txtTG.setMaximumSize(new java.awt.Dimension(197, 20));
        txtTG.setMinimumSize(new java.awt.Dimension(197, 20));
        txtTG.setPreferredSize(new java.awt.Dimension(197, 20));
        txtTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTGActionPerformed(evt);
            }
        });
        pnlCapNhat.add(txtTG, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 40, 280, -1));

        jSeparator1.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 270, 10));

        jSeparator2.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 270, 10));

        jSeparator3.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 60, 280, 10));

        jSeparator4.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 120, 280, 10));

        jSeparator5.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 270, 10));

        jSeparator6.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 270, 10));

        jSeparator8.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 120, 10));

        jSeparator9.setForeground(new java.awt.Color(140, 110, 207));
        pnlCapNhat.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 130, 10));

        jScrollPane3.setBackground(new java.awt.Color(243, 247, 253));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(140, 110, 207)));

        txtDiaChi.setBackground(new java.awt.Color(243, 247, 253));
        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtDiaChi.setRows(5);
        jScrollPane3.setViewportView(txtDiaChi);

        pnlCapNhat.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 200, 280, 80));

        jPanel2.setBackground(new java.awt.Color(243, 247, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ảnh sách", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(140, 110, 207))); // NOI18N

        lblAnh.setBackground(new java.awt.Color(243, 247, 253));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAnhMousePressed(evt);
            }
        });
        lblAnh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lblAnhKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );

        pnlCapNhat.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 140, 150));

        pnlForm.add(pnlCapNhat, "card2");

        pnlDanhSach.setBackground(new java.awt.Color(243, 247, 253));

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sách", "Tên Sách", "Tác Giả", "Loại sách", "Số trang", "Giá", "NXB", "Vị trí", "Địa chỉ", "Hình"
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
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/search.png"))); // NOI18N

        txtTim.setBackground(new java.awt.Color(243, 247, 253));
        txtTim.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTim.setBorder(null);
        txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKeyPressed(evt);
            }
        });

        jSeparator10.setForeground(new java.awt.Color(140, 110, 207));

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addGap(330, 330, 330)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator10)
                    .addComponent(txtTim, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
                .addContainerGap(365, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTim))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        pnlForm.add(pnlDanhSach, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, 1228, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed

    private void txtNhaXuatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhaXuatBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhaXuatBanActionPerformed

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

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void txtTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGActionPerformed

    private void lblAnhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblAnhKeyPressed

    }//GEN-LAST:event_lblAnhKeyPressed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblSach.rowAtPoint(evt.getPoint());
            edit();
            btnCapNhatActionPerformed(null);
        }
    }//GEN-LAST:event_tblSachMouseClicked

    private void txtTimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyPressed
        timkiem();
    }//GEN-LAST:event_txtTimKeyPressed

    private void lblAnhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMousePressed
        try {
            this.selectImage();
        } catch (Exception e) {
            MsgBox.alert(this, "Định dạng hình ảnh không hợp lệ");
        }
    }//GEN-LAST:event_lblAnhMousePressed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblCapNhat;
    private javax.swing.JLabel lblDanhSach;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNhaXuatBan;
    private javax.swing.JTextField txtSoTrang;
    private javax.swing.JTextField txtTG;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTim;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables
}
