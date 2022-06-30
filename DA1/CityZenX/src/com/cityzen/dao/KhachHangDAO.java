/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.dao;

import com.cityzen.entity.KhachHang;
import com.cityzen.ultils.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trant
 */
public class KhachHangDAO extends CityZenDAO<KhachHang, String>{
    String INSERT_SQL = "INSERT INTO KhachHang (tenKH,gioiTinh, sdt,ngaySinh,diaChi,ghiChu,capKH,hinh) VALUES (?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE KhachHang SET tenKH = ?,gioiTinh = ?, sdt = ?,ngaySinh = ?,diaChi = ?,ghiChu = ?,capKH = ?,hinh = ? WHERE maKH=?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE maKH=?";
    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE maKH=?";

    @Override
    public void insert(KhachHang entity) {
        XJdbc.Update(INSERT_SQL, entity.getTenKH(),entity.getGioiTinh(),entity.getSoDT(),entity.getNgaySinh(),entity.getDiaChi(),entity.getGhiChu()
        ,entity.getCapKH(),entity.getHinh());
    }

    @Override
    public void update(KhachHang entity) {
        XJdbc.Update(UPDATE_SQL, entity.getTenKH(),entity.getGioiTinh(),entity.getSoDT(),entity.getNgaySinh(),entity.getDiaChi(),entity.getGhiChu()
        ,entity.getCapKH(),entity.getHinh(),entity.getMaKH());
    }
    
    public void DoiMatKhau(KhachHang model) {
        String sql = "UPDATE KhachHang SET capKH = ? + 1 WHERE maKH = ?";
        XJdbc.Update(sql,
                model.getCapKH(),
                model.getMaKH());
    }

    @Override
    public void delete(String id) {
        XJdbc.Update(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public KhachHang selectById(Integer id) {
        List<KhachHang>list=this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getInt("maKH"));
                entity.setTenKH(rs.getString("tenKH"));
                entity.setGioiTinh(rs.getString("gioiTinh"));
                entity.setSoDT(rs.getString("sdt"));
                entity.setNgaySinh(rs.getDate("ngaySinh"));
                entity.setDiaChi(rs.getString("diaChi"));
                entity.setGhiChu(rs.getString("ghiChu"));
                entity.setCapKH(rs.getInt("capKH"));
                entity.setHinh(rs.getString("hinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<KhachHang> selectByKeyword(String keyword){
        String sql = "SELECT * FROM KhachHang WHERE tenKH LIKE ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }

    @Override
    public KhachHang selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

