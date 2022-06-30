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
public class Sach {
    private String maSach;
    private String tenSach;
    private String tacGia;
    private int soTrang;
    private String diaChi;
    private float gia;
    private String viTri;
    private String tenLoai;
    private String nhaXuatBan;
    private String hinh;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, String tacGia, int soTrang, String diaChi, float gia, String viTri, String tenLoai, String nhaXuatBan, String hinh) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.soTrang = soTrang;
        this.diaChi = diaChi;
        this.gia = gia;
        this.viTri = viTri;
        this.tenLoai = tenLoai;
        this.nhaXuatBan = nhaXuatBan;
        this.hinh = hinh;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

}