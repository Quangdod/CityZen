package com.cityzen.design.component;

import com.cityzen.design.event.EventMenu;
import com.cityzen.design.event.EventMenuCallBack;
import com.cityzen.design.event.EventMenuSelected;
import com.cityzen.design.model.Model_Menu;
import com.cityzen.design.shadow.ShadowBorder;
import com.cityzen.design.swing.PanelShadow;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Menu extends PanelShadow {

    private int selectedIndex = -1;
    private double menuTarget;
    private double menuLastTarget;
    private double currentLocation;
    private BufferedImage selectedImage;
    private Animator animator;
    private EventMenuCallBack callBack;
    private EventMenu event;

    public Menu() {
        initComponents();
        init();
    }

    private void init() {
        setRadius(20);
        initData();
        listMenu.addEventSelectedMenu(new EventMenuSelected() {
            @Override
            public void menuSelected(int index, EventMenuCallBack callBack) {
                if (!animator.isRunning()) {
                    if (index != selectedIndex) {
                        Menu.this.callBack = callBack;
                        selectedIndex = index;
                        menuTarget = selectedIndex * 50 + listMenu.getY();
                        animator.start();
                    }
                }
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                currentLocation = (menuTarget - menuLastTarget) * fraction;
                currentLocation += menuLastTarget;
                repaint();
            }

            @Override
            public void end() {
                menuLastTarget = menuTarget;
                callBack.call(selectedIndex);
                if (event != null) {
                    event.menuIndexChange(selectedIndex);
                }
            }
        };
        animator = new Animator(300, target);
        animator.setResolution(1);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        menuTarget = selectedIndex * 50 + listMenu.getY();
        menuLastTarget = menuTarget;
        currentLocation = menuLastTarget;
        listMenu.selectedIndex(index);
        repaint();
    }

    private void initData() {
        listMenu.addItem(new Model_Menu("1", "Trang chủ", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("2", "Sách", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("3", "Bán sách", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("4", "Nhập sách", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("5", "Khách hàng", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("6", "Nhà phân phối", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("7", "Nhân viên", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("8", "Thống kê", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("9", "Giới thiệu", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("10", "Hướng ra", Model_Menu.MenuType.MENU));
        //listMenu.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
    }

    private void createImage() {
        int width = getWidth() - 30;
        selectedImage = ShadowBorder.getInstance().createShadowOut(width, 50, 8, 8, new Color(242, 246, 253));
    }

    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        createImage();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        if (selectedIndex >= 0) {
            grphcs.drawImage(selectedImage, 15, (int) currentLocation, null);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listMenu = new com.cityzen.design.swing.ListMenu<>();

        listMenu.setOpaque(false);
        add(listMenu);
    }// </editor-fold>//GEN-END:initComponents

    public void addEvent(EventMenu event) {
        this.event = event;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cityzen.design.swing.ListMenu<String> listMenu;
    // End of variables declaration//GEN-END:variables
}
