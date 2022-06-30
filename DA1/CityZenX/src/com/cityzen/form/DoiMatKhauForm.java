/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.form;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import com.cityzen.dao.NhanVienDao;
import com.cityzen.entity.NhanVien;
import com.cityzen.ultils.Auth;
import com.cityzen.ultils.MsgBox;
import com.cityzen.ultils.XImage;

/**
 *
 * @author QUANG
 */
public class DoiMatKhauForm extends javax.swing.JFrame {

    /**
     * Creates new form DangKy
     */
    public DoiMatKhauForm() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(XImage.APP_ICON);
        this.setTitle("Đổi mật khẩu");
        btnXacNhan.setEnabled(false);
        btnDoiMatKhau.setEnabled(false);
    }

    int maopt = (int) (Math.random() * 900000) + 10000;

    void sendEmail() {
        String ten = txtEmail.getText();
        System.out.println(maopt);
        String textsend = "Mã OTP của bạn là: " + maopt;
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);
            String email = "quangdodps15144@fpt.edu.vn";
            Session s = Session.getInstance(p,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, "Quang123");
                }
            });
            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(email));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(txtEmail.getText()));
            msg.setSubject("OTP for you");
            msg.setText(textsend);
            Transport.send(msg);
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra email để lấy mã OTP", "Message",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            btnXacNhan.setEnabled(true);
            btnGui.setEnabled(false);
        } catch (MessagingException ex) {
            System.out.println(ex);
            MsgBox.alert(this, "Email không đúng định dạng");
//            Logger.getLogger(DoiMatKhauForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void XacNhan() {
        try {
            if (Integer.parseInt(txtOtp.getText()) == maopt) {
                System.out.println(txtOtp.getText());
                JOptionPane.showMessageDialog(this, "Thành công");
                btnDoiMatKhau.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Mã OTP không đúng");
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Mã OTP không đúng định dạng!");
        }
    }

    NhanVienDao dao = new NhanVienDao();

    NhanVien getModel() {
        NhanVien model = new NhanVien();
        model.setMaNV(txtMaNhanVien.getText());
        model.setMatKhau(new String(txtMatKhauMoi.getText()));
        return model;
    }

    void DoiMatKhau() {
        String manv = txtMaNhanVien.getText();
        NhanVien nhanVien = dao.findById(manv);
        String matKhau = new String(txtMatKhauMoi.getText());
        String matKhau2 = nhanVien.getMatKhau();
        String confirm = new String(txtXacNhan.getText());
        NhanVien model = getModel();
        try {
            //
            if (nhanVien != null) {
                Auth.user = nhanVien;
                if (txtMatKhauMoi.getText().equals("")) {
                    MsgBox.alert(this, "Bạn chưa nhập mật khẩu mới");
                } else if (!confirm.equals(model.getMatKhau())) {
                    MsgBox.alert(this, "Mật khẩu xác nhận không khớp");
                } else {
                    try {
                        dao.DoiMatKhau(model);
                        MsgBox.alert(this, "Đổi mật khẩu thành công");
                        new DangNhapForm().setVisible(true);
                        this.setVisible(false);
                    } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại!");
                    }
                }
            } else {
                MsgBox.alert(this, "Tên đăng nhập không tồn tại!");
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblOtp = new javax.swing.JLabel();
        txtOtp = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtXacNhan = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        lblXacNhan = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblMaNhanVien = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        btnGui = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        btnDoiMatKhau = new javax.swing.JButton();
        lblMatKhauMoi = new javax.swing.JLabel();
        txtMatKhauMoi = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        txtTrove = new javax.swing.JLabel();
        lblAnimation = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(180, 200, 200));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblOtp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOtp.setForeground(new java.awt.Color(73, 74, 102));
        lblOtp.setText("OTP:");
        getContentPane().add(lblOtp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        txtOtp.setBackground(new java.awt.Color(180, 200, 200));
        txtOtp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtOtp.setForeground(new java.awt.Color(72, 85, 106));
        txtOtp.setBorder(null);
        getContentPane().add(txtOtp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 310, 20));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 410, 10));

        txtXacNhan.setBackground(new java.awt.Color(72, 85, 106));
        txtXacNhan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtXacNhan.setForeground(new java.awt.Color(180, 200, 200));
        txtXacNhan.setBorder(null);
        getContentPane().add(txtXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 410, 20));

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(73, 74, 102));
        lblEmail.setText("Email:");
        getContentPane().add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtEmail.setBackground(new java.awt.Color(180, 200, 200));
        txtEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(72, 85, 106));
        txtEmail.setBorder(null);
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 310, 20));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 310, 10));

        lblXacNhan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblXacNhan.setForeground(new java.awt.Color(180, 200, 200));
        lblXacNhan.setText("Xác nhận mật khẩu mới:");
        getContentPane().add(lblXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 400, 10));

        lblMaNhanVien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMaNhanVien.setForeground(new java.awt.Color(180, 200, 200));
        lblMaNhanVien.setText("Mã nhân viên:");
        getContentPane().add(lblMaNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        txtMaNhanVien.setBackground(new java.awt.Color(72, 85, 106));
        txtMaNhanVien.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaNhanVien.setForeground(new java.awt.Color(180, 200, 200));
        txtMaNhanVien.setBorder(null);
        getContentPane().add(txtMaNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 410, 20));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 410, 10));

        btnGui.setBackground(new java.awt.Color(72, 85, 106));
        btnGui.setForeground(new java.awt.Color(180, 200, 200));
        btnGui.setText("Gửi");
        btnGui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiActionPerformed(evt);
            }
        });
        getContentPane().add(btnGui, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 80, -1));

        btnXacNhan.setBackground(new java.awt.Color(72, 85, 106));
        btnXacNhan.setForeground(new java.awt.Color(180, 200, 200));
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        getContentPane().add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, -1, -1));
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 310, 10));

        btnDoiMatKhau.setBackground(new java.awt.Color(180, 200, 200));
        btnDoiMatKhau.setForeground(new java.awt.Color(72, 85, 106));
        btnDoiMatKhau.setText("Đổi mật khẩu");
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });
        getContentPane().add(btnDoiMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, -1, -1));

        lblMatKhauMoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMatKhauMoi.setForeground(new java.awt.Color(180, 200, 200));
        lblMatKhauMoi.setText("Mật khẩu mới:");
        getContentPane().add(lblMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        txtMatKhauMoi.setBackground(new java.awt.Color(72, 85, 106));
        txtMatKhauMoi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMatKhauMoi.setForeground(new java.awt.Color(180, 200, 200));
        txtMatKhauMoi.setBorder(null);
        getContentPane().add(txtMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 410, 20));
        getContentPane().add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 410, 10));

        txtTrove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/trove.png"))); // NOI18N
        txtTrove.setToolTipText("Trở về form đăng nhập");
        txtTrove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTroveMouseClicked(evt);
            }
        });
        getContentPane().add(txtTrove, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, -1, 20));

        lblAnimation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cityzen/icon/color.gif"))); // NOI18N
        getContentPane().add(lblAnimation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        if (txtEmail.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa nhập mã OTP");
        } else {
            XacNhan();
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnGuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiActionPerformed
        if (txtEmail.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa nhập email");
        } else {
            sendEmail();
        }
    }//GEN-LAST:event_btnGuiActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        try {
            DoiMatKhau();
        } catch (Exception e) {
            MsgBox.alert(this, "Mã nhân viên không tồn tại");
        }
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void txtTroveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTroveMouseClicked
        DangNhapForm dn = new DangNhapForm();
        dn.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_txtTroveMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnGui;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblAnimation;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblMatKhauMoi;
    private javax.swing.JLabel lblOtp;
    private javax.swing.JLabel lblXacNhan;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMatKhauMoi;
    private javax.swing.JTextField txtOtp;
    private javax.swing.JLabel txtTrove;
    private javax.swing.JTextField txtXacNhan;
    // End of variables declaration//GEN-END:variables
}
