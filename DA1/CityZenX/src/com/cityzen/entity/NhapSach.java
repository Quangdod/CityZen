/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.entity;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class NhapSach {
    private int maLH;
    private Date ngayNhap;
    private int maNPP;
    private String maNV;   
    private String maSach;
    private float giaNhap;
    private int soLuong;
    private String ghiChu;

    /**
     *
     */
    public NhapSach() {
    }

    public NhapSach(int maLH, Date ngayNhap, int maNPP, String maNV, String maSach, float giaNhap, int soLuong, String ghiChu) {
        this.maLH = maLH;
        this.ngayNhap = ngayNhap;
        this.maNPP = maNPP;
        this.maNV = maNV;
        this.maSach = maSach;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
    }
    
    public int getMaLH() {
        return maLH;
    }

    public void setMaLH(int maLH) {
        this.maLH = maLH;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getMaNPP() {
        return maNPP;
    }

    public void setMaNPP(int maNPP) {
        this.maNPP = maNPP;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public float getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}

