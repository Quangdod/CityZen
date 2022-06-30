/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.ultils;

import com.cityzen.design.swing.ImageAvatar;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ASUS
 */
public class XImage {   
    
    public static Image APP_ICON;
    
    static {
        // Tải biểu tượng ứng dụng
        String file = "/com/cityzen/icon/icon.png";
        APP_ICON = new ImageIcon(XImage.class.getResource(file)).getImage();
    }
    
//    public static Image getAppIcon() {
//        URL url = XImage.class.getResource("/com/cityzen/icon/citizen.ico");
//        return new ImageIcon(url).getImage();
//    }

    public static void save(File src) {
        File dst = new File("Images", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ImageIcon read(String fileName, JLabel lblhinh) {
        File path = new File("Images", fileName);
        ImageIcon imageIcon = new ImageIcon(path.getAbsolutePath());
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(lblhinh.getWidth(), lblhinh.getHeight(), image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(newImage);
        return img;
    }
    
    public static ImageIcon readLogo(String fileName) {
        File path = new File("Images", fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
    }
}
