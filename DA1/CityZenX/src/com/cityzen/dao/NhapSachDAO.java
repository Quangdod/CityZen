/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.dao;

import com.cityzen.entity.NhapSach;
import com.cityzen.form.BanSachForm;
import com.cityzen.form.ThongKeForm;
import com.cityzen.ultils.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trant
 */
public class NhapSachDAO extends CityZenDAO<NhapSach, String>{
    String insert_sql = "INSERT INTO NhapSach (ngayNhap, maNPP, maNV, maSach, giaNhap, soLuong, ghiChu) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String update_sql = "UPDATE NhapSach SET ngayNhap=?, maNPP=?, maNV=?, maSach=?, giaNhap=?, soLuong=?, ghiChu=? WHERE maLH=?";
    String delete_sql = "DELETE FROM NhapSach WHERE maLH=?";
    String selectAll_sql = "SELECT * FROM NhapSach";
    String selectById_sql = "SELECT * FROM NhapSach WHERE maLH=?";
    String selectBySach = "SELECT * FROM NhapSach WHERE maSach=?";
    String sosachnhap = "select sum(soLuong) as NS from NhapSach";
    String sql_nam = "SELECT distinct Year(ngayNhap) as nam FROM NhapSach UNION SELECT distinct Year(ngaymua) FROM HoaDon";
    
    String sql_truSoLuong = "UPDATE NhapSach SET soLuong = soLuong - " + BanSachForm.soLuongSach + " WHERE maSach = ?";
    
    public void updateSoLuong(NhapSach entity) {
        XJdbc.Update(sql_truSoLuong, entity.getMaSach());
    }
       
    
    int nam = ThongKeForm.nam2;
    
    public NhapSachDAO() {
        nam = ThongKeForm.nam2;
    }
    
    String ssn1 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 1 and year(ngayNhap) like " + nam + ")";
    String ssn2 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 2 and year(ngayNhap) like " + nam + ")";
    String ssn3 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 3 and year(ngayNhap) like " + nam + ")";
    String ssn4 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 4 and year(ngayNhap) like " + nam + ")";
    String ssn5 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 5 and year(ngayNhap) like " + nam + ")";
    String ssn6 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 6 and year(ngayNhap) like " + nam + ")";
    String ssn7 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 7 and year(ngayNhap) like " + nam + ")";
    String ssn8 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 8 and year(ngayNhap) like " + nam + ")";
    String ssn9 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 9 and year(ngayNhap) like " + nam + ")";
    String ssn10 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 10 and year(ngayNhap) like " + nam + ")";
    String ssn11 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 11 and year(ngayNhap) like " + nam + ")";
    String ssn12 = "select sum(soLuong) as sl from NhapSach group by MONTH(ngayNhap), year(ngayNhap) having (MONTH(ngayNhap) = 12 and year(ngayNhap) like " + nam + ")";

    
    
    public List<Integer> selectNam() {
        List<Integer> list = this.selectNam2(sql_nam);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    protected List<Integer> selectNam2(String sql, Object... args) {
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {                
                int n = rs.getInt("nam");
                list.add(n);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public int SoSachNhapThang1() {
       return selectBySqlNhapSachThangTheoNam(ssn1);
    }
    
    public int SoSachNhapThang2() {
       return selectBySqlNhapSachThangTheoNam(ssn2);
    }
    
    public int SoSachNhapThang3() {
       return selectBySqlNhapSachThangTheoNam(ssn3);
    }
    
    public int SoSachNhapThang4() {
       return selectBySqlNhapSachThangTheoNam(ssn4);
    }
    
    public int SoSachNhapThang5() {
       return selectBySqlNhapSachThangTheoNam(ssn5);
    }
    
    public int SoSachNhapThang6() {
       return selectBySqlNhapSachThangTheoNam(ssn6);
    }
    
    public int SoSachNhapThang7() {
       return selectBySqlNhapSachThangTheoNam(ssn7);
    }
    
    public int SoSachNhapThang8() {
       return selectBySqlNhapSachThangTheoNam(ssn8);
    }
    
    public int SoSachNhapThang9() {
       return selectBySqlNhapSachThangTheoNam(ssn9);
    }
    
    public int SoSachNhapThang10() {
       return selectBySqlNhapSachThangTheoNam(ssn10);
    }
    
    public int SoSachNhapThang11() {
       return selectBySqlNhapSachThangTheoNam(ssn11);
    }
    
    public int SoSachNhapThang12() {
       return selectBySqlNhapSachThangTheoNam(ssn12);
    }
    
    public int selectBySqlNhapSachThangTheoNam(String sql) {
        try {
            ResultSet rs = XJdbc.Query(sql);
            while (rs.next()) {                
                int n = rs.getInt("sl");
                return n;
            }
            rs.getStatement().getConnection().close();
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void insert(NhapSach entity) {
        XJdbc.Update(insert_sql, 
                entity.getNgayNhap(), 
                entity.getMaNPP(),
                entity.getMaNV(), 
                entity.getMaSach(),
                entity.getGiaNhap(), 
                entity.getSoLuong(), 
                entity.getGhiChu());
    }

    @Override
    public void update(NhapSach entity) {
        XJdbc.Update(update_sql, 
                entity.getNgayNhap(),
                entity.getMaNPP(),
                entity.getMaNV(),
                entity.getMaSach(),
                entity.getGiaNhap(),
                entity.getSoLuong(),
                entity.getGhiChu(),
                entity.getMaLH());
    }

    public void delete(String malh) {
        XJdbc.Update(delete_sql, malh);
    }

    @Override
    public List<NhapSach> selectAll() {
        return this.selectBySql(selectAll_sql);
    }

    public NhapSach selectById(Integer malh) {
        List<NhapSach> list = this.selectBySql(selectById_sql, malh);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public int SoSachNhap() {
       return selectBySqlNhapSach(sosachnhap);
    }
    
    public int selectBySqlNhapSach(String sql) {
        try {
            ResultSet rs = XJdbc.Query(sql);
            while (rs.next()) {                
                int n = rs.getInt("NS");
                return n;
            }
            rs.getStatement().getConnection().close();
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<NhapSach> selectBySql(String sql, Object... args) {
        List<NhapSach> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {                
                NhapSach entity = new NhapSach();
                entity.setMaLH(rs.getInt("maLH"));
                entity.setNgayNhap(rs.getDate("ngayNhap"));
                entity.setMaNPP(rs.getInt("maNPP"));
                entity.setMaNV(rs.getString("maNV"));
                entity.setMaSach(rs.getString("maSach"));
                entity.setGiaNhap(rs.getFloat("giaNhap"));
                entity.setSoLuong(rs.getInt("soLuong"));
                entity.setGhiChu(rs.getString("ghiChu"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<NhapSach> selectBySach(String maSach) {
        return this.selectBySql(selectBySach, maSach);
    }

    @Override
    NhapSach selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
