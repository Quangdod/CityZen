package com.cityzen.form;

import com.cityzen.design.event.EventMenu;
import com.cityzen.ultils.Auth;
import com.cityzen.ultils.MsgBox;
import com.cityzen.ultils.XImage;
import java.awt.Color;
import java.awt.Component;
import org.bridj.ann.Convention;

public class MainForm extends javax.swing.JFrame {

    public static MainForm m ;
    
    public MainForm() {
        initComponents();
        m = this;
        setBackground(new Color(0, 0, 0, 0));
        showForm(new TrangChuForm());
    }

    public static void showForm(Component com) {
        body.removeAll();
        body.add(com);
        body.repaint();
        body.revalidate();
    }
    
    public static void an(){
        m.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.cityzen.design.swing.PanelBorder();
        menu = new com.cityzen.design.component.Menu();
        body = new javax.swing.JLayeredPane();
        profile1 = new com.cityzen.design.component.Profile();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(body))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        menu.setSelectedIndex(0);
        menu.addEvent(new EventMenu() {
            @Override
            public void menuIndexChange(int index) {
                if (index == 0) {
                    showForm(new TrangChuForm());
                } else if (index == 1) {
                    showForm(new SachForm());
                } else if (index == 2) {
                    showForm(new BanSachForm());
                } else if (index == 3) {
                    showForm(new NhapSachForm());
                } else if (index == 4) {
                    showForm(new KhachHangForm());
                } else if (index == 5) {
                    showForm(new NhaPhanPhoiForm());
                } else if (index == 6) {
                    if(!Auth.isManager()){
                        MsgBox.alert(menu, "Bạn không có quyền vào đây");
                    }else{
                        showForm(new NhanVienForm());
                    }
                } else if (index == 7) {
                    if(!Auth.isManager()){
                        MsgBox.alert(menu, "Bạn không có quyền vào đây");
                    }else{
                        showForm(new ThongKeForm());
                    }
                } else if (index == 8) {
                    showForm(new GioiThieuForm());
                }else if (index == 9) {
                    showForm(new ThoatForm());
                }
            }
        });
    }//GEN-LAST:event_formWindowOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLayeredPane body;
    private com.cityzen.design.component.Menu menu;
    private com.cityzen.design.swing.PanelBorder panelBorder1;
    private com.cityzen.design.component.Profile profile1;
    // End of variables declaration//GEN-END:variables
}
