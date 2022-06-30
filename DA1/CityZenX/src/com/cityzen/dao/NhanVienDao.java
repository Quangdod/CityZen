/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.dao;

import com.cityzen.ultils.XJdbc;
import com.cityzen.entity.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QUANG
 */
public class NhanVienDao {

    private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        NhanVien model = new NhanVien();
        model.setMaNV(rs.getString("maNV"));
        model.setTenNV(rs.getString("tenNV"));
        model.setGioiTinh(rs.getString("gioiTinh"));
        model.setSoDT(rs.getString("sdt"));
        model.setNgaySinh(rs.getDate("ngaySinh"));
        model.setEmail(rs.getString("email"));
        model.setVaiTro(rs.getBoolean("vaiTro"));
        model.setMatKhau(rs.getString("matKhau"));
        model.setDiaChi(rs.getString("diaChi"));
        model.setGhiChu(rs.getString("ghiChu"));
        model.setHinh(rs.getString("hinh"));
        return model;
    }

    private List<NhanVien> select(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {      
                ResultSet rs = XJdbc.Query(sql, args);
                while (rs.next()) {
                    NhanVien model = readFromResultSet(rs);
                    list.add(model);                    
                }
                rs.getStatement().getConnection().close();
                return list;
        } catch (SQLException ex) {
            throw new RuntimeException();
        }   
    }

    public void insert(NhanVien model) {
        String sql = "INSERT INTO NhanVien (maNV, tenNV, gioiTinh, sdt, ngaySinh, email, vaiTro, matKhau, diaChi, ghiChu, hinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        XJdbc.Update(sql,
                model.getMaNV(),
                model.getTenNV(),
                model.getGioiTinh(),
                model.getSoDT(),
                model.getNgaySinh(),
                model.getEmail(),
                model.isVaiTro(),
                model.getMatKhau(),
                model.getDiaChi(),
                model.getGhiChu(),
                model.getHinh());
    }

    public void update(NhanVien model) {
        String sql = "UPDATE NhanVien SET tenNV=?, gioiTinh=?, sdt=?, ngaySinh=?, email=?, vaiTro=?, matKhau=?, diaChi=?, ghiChu=?, hinh=? WHERE maNV=?";
        XJdbc.Update(sql,
                model.getTenNV(),
                model.getGioiTinh(),
                model.getSoDT(),
                model.getNgaySinh(),
                model.getEmail(),
                model.isVaiTro(),
                model.getMatKhau(),
                model.getDiaChi(),
                model.getGhiChu(),
                model.getHinh(),
                model.getMaNV());
    }

    public void delete(String MaNV) {
        String sql = "DELETE FROM NhanVien WHERE maNV=?";
        XJdbc.Update(sql, MaNV);
    }

    public List<NhanVien> select() {
        String sql = "SELECT * FROM NhanVien";
        return select(sql);
    }

    public void DoiMatKhau(NhanVien model) {
        String sql = "UPDATE NhanVien SET matKhau=? WHERE maNV=?";
        XJdbc.Update(sql,
                model.getMatKhau(),
                model.getMaNV());
    }

    public NhanVien findById(String manv) {
        String sql = "SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    public List<NhanVien> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NhanVien WHERE tenNV LIKE ?";
        return select(sql, "%" + keyword + "%");
    }
}
