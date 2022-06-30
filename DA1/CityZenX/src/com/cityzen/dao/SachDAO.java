/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.dao;

import com.cityzen.entity.Sach;
import com.cityzen.ultils.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class SachDAO extends CityZenDAO<Sach, String>{
    String INSERT_SQL = "INSERT INTO Sach (maSach,tenSach,tacGia,soTrang,diaChi,gia,viTri,tenLoai,nxb,hinh) VALUES (?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Sach SET tenSach = ?,tacGia = ?,soTrang = ?,diaChi = ?,gia = ?,viTri = ?,tenLoai = ?,nxb = ?,hinh = ? WHERE maSach=?";
    String DELETE_SQL = "DELETE FROM Sach WHERE maSach=?";
    String SELECT_ALL_SQL = "SELECT * FROM Sach";
    String SELECT_BY_ID_SQL = "SELECT * FROM Sach WHERE maSach=?";

    @Override
    public void insert(Sach entity) {
        XJdbc.Update(INSERT_SQL, entity.getMaSach(),entity.getTenSach(),entity.getTacGia(),entity.getSoTrang(),entity.getDiaChi(),entity.getGia()
        ,entity.getViTri(),entity.getTenLoai(),entity.getNhaXuatBan(),entity.getHinh());
    }

    @Override
    public void update(Sach entity) {
         XJdbc.Update(UPDATE_SQL, entity.getTenSach(),entity.getTacGia(),entity.getSoTrang(),entity.getDiaChi(),entity.getGia()
        ,entity.getViTri(),entity.getTenLoai(),entity.getNhaXuatBan(),entity.getHinh(),entity.getMaSach());
    }

    @Override
    public void delete(String id) {
        XJdbc.Update(DELETE_SQL, id);
    }

    @Override
    public List<Sach> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Sach selectById(String id) {
         List<Sach>list=this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Sach> selectBySql(String sql, Object... args) {
        List<Sach> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                Sach entity = new Sach();
                entity.setMaSach(rs.getString("maSach"));
                entity.setTenSach(rs.getString("tenSach"));
                entity.setTacGia(rs.getString("tacGia"));
                entity.setSoTrang(rs.getInt("soTrang"));
                entity.setDiaChi(rs.getString("diaChi"));
                entity.setGia(rs.getFloat("gia"));
                entity.setViTri(rs.getString("viTri"));
                entity.setTenLoai(rs.getString("tenLoai"));
                entity.setNhaXuatBan(rs.getString("nxb"));
                entity.setHinh(rs.getString("hinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Sach> selectByKeyword(String keyword){
        String sql = "SELECT * FROM Sach WHERE tenLoai Like ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }
}