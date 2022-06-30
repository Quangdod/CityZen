/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.design.swing;

import java.awt.Rectangle;
import javax.swing.Icon;

public class ModelNavigationBar {

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Rectangle getRec() {
        return rec;
    }

    public void setRec(Rectangle rec) {
        this.rec = rec;
    }

    public ModelNavigationBar(Icon icon, int index, Rectangle rec) {
        this.icon = icon;
        this.index = index;
        this.rec = rec;
    }

    public ModelNavigationBar() {
    }

    private Icon icon;
    private int index;
    private Rectangle rec;

}