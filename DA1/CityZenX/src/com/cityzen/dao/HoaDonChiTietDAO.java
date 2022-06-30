/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.dao;

import com.cityzen.entity.HoaDon;
import com.cityzen.entity.HoaDonChiTiet;
import com.cityzen.form.ThongKeForm;
import com.cityzen.ultils.XJdbc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.DriverManager;

/**
 *
 * @author Hoang Nhi
 */
public class HoaDonChiTietDAO extends CityZenDAO<HoaDonChiTiet, Integer> {
    String INSERT_SQL = "INSERT INTO HoaDonChiTiet (maSach,maHD,soLuong,gia) VALUES (?,?,?,?)";
    String UPDATE_SQL = "UPDATE HoaDonChiTiet SET maSach = ?,maHD = ?,soLuong = ?,gia = ? WHERE maHDCT=?";
    String DELETE_SQL = "DELETE FROM HoaDonChiTiet WHERE maHDCT=?";
    String SELECT_ALL_SQL = "SELECT * FROM HoaDonChiTiet";
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDonChiTiet WHERE maHDCT=?";
    String SoSachBan = "select sum(soLuong) as SB from HoaDonChiTiet";
    String doanhThu = "select sum(gia) as DT from HoaDonChiTiet";
    
    int nam = ThongKeForm.nam2;
    
    public HoaDonChiTietDAO() {
        nam = ThongKeForm.nam2;
    }
    
    String bansachThang1 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 1 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang2 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 2 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang3 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 3 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang4 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 4 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang5 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 5 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang6 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 6 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang7 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 7 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang8 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 8 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang9 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 9 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang10 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 10 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang11 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 11 and year(hd.ngayMua) like "+ nam +")";
    String bansachThang12 = "select sum(soLuong) as nnh from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 12 and year(hd.ngayMua) like "+ nam +")";
    
    String dtt1 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 1 and year(hd.ngayMua) like " + nam +")";
    String dtt2 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 2 and year(hd.ngayMua) like " + nam +")";
    String dtt3 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 3 and year(hd.ngayMua) like " + nam +")";
    String dtt4 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 4 and year(hd.ngayMua) like " + nam +")";
    String dtt5 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 5 and year(hd.ngayMua) like " + nam +")";
    String dtt6 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 6 and year(hd.ngayMua) like " + nam +")";
    String dtt7 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 7 and year(hd.ngayMua) like " + nam +")";
    String dtt8 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 8 and year(hd.ngayMua) like " + nam +")";
    String dtt9 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 9 and year(hd.ngayMua) like " + nam +")";
    String dtt10 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 10 and year(hd.ngayMua) like " + nam +")";
    String dtt11 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 11 and year(hd.ngayMua) like " + nam +")";
    String dtt12 = "select sum(gia)/1000000 as dttg from HoaDonChiTiet ct inner join HoaDon hd on ct.maHD = hd.maHD group by MONTH(hd.ngayMua), year(hd.ngayMua) having (MONTH(hd.ngayMua) = 12 and year(hd.ngayMua) like " + nam +")";
    
    public int SachBanThang1() {
       return selectBySqlBanSachThang(bansachThang1);
    }
    
    public int SachBanThang2() {
       return selectBySqlBanSachThang(bansachThang2);
    }
    
    public int SachBanThang3() {
       return selectBySqlBanSachThang(bansachThang3);
    }
    
    public int SachBanThang4() {
       return selectBySqlBanSachThang(bansachThang4);
    }
    
    public int SachBanThang5() {
       return selectBySqlBanSachThang(bansachThang5);
    }
    
    public int SachBanThang6() {
       return selectBySqlBanSachThang(bansachThang6);
    }
    
    public int SachBanThang7() {
       return selectBySqlBanSachThang(bansachThang7);
    }
    
    public int SachBanThang8() {
       return selectBySqlBanSachThang(bansachThang8);
    }
    
    public int SachBanThang9() {
       return selectBySqlBanSachThang(bansachThang9);
    }
    
    public int SachBanThang10() {
       return selectBySqlBanSachThang(bansachThang10);
    }
    
    public int SachBanThang11() {
       return selectBySqlBanSachThang(bansachThang11);
    }
    
    public int SachBanThang12() {
       return selectBySqlBanSachThang(bansachThang12);
    }
    
    
    public int selectBySqlBanSachThang(String sql) {
        try {
            ResultSet rs = XJdbc.Query(sql);
            while (rs.next()) {                
                int n = rs.getInt("nnh");
                return n;
            }
            rs.getStatement().getConnection().close();
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public int dtt1() {
       return selectBySqlDoanhThuThang(dtt1);
    }
    
    public int dtt2() {
       return selectBySqlDoanhThuThang(dtt2);
    }
    
    public int dtt3() {
       return selectBySqlDoanhThuThang(dtt3);
    }
    
    public int dtt4() {
       return selectBySqlDoanhThuThang(dtt4);
    }
    
    public int dtt5() {
       return selectBySqlDoanhThuThang(dtt5);
    }
    
    public int dtt6() {
       return selectBySqlDoanhThuThang(dtt6);
    }
    
    public int dtt7() {
       return selectBySqlDoanhThuThang(dtt7);
    }
    
    public int dtt8() {
       return selectBySqlDoanhThuThang(dtt8);
    }
    
    public int dtt9() {
       return selectBySqlDoanhThuThang(dtt9);
    }
    
    public int dtt10() {
       return selectBySqlDoanhThuThang(dtt10);
    }
    
    public int dtt11() {
       return selectBySqlDoanhThuThang(dtt11);
    }
    
    public int dtt12() {
       return selectBySqlDoanhThuThang(dtt12);
    }
    
    public int selectBySqlDoanhThuThang(String sql) {
        try {
            ResultSet rs = XJdbc.Query(sql);
            while (rs.next()) {                
                int n = rs.getInt("dttg");
                return n;
            }
            rs.getStatement().getConnection().close();
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void insert(HoaDonChiTiet entity) {
        XJdbc.Update(INSERT_SQL, entity.getMaSach(), entity.getMaHD(), entity.getSoLuong(), entity.getGia());
        
    }

    @Override
    public void update(HoaDonChiTiet entity) {
         XJdbc.Update(UPDATE_SQL, entity.getMaSach(), entity.getMaHD(), entity.getSoLuong(), entity.getGia(),entity.getMaHDCT());
    }

    @Override
    public void delete(Integer id) {
        XJdbc.Update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDonChiTiet selectById(Integer id) {
         List<HoaDonChiTiet> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public int SoSachBan() {
       return selectBySqlSoSach(SoSachBan);
    }
    
    public int DoanhThu() {
       return selectBySqlDoanhThu(doanhThu);
    }
    
    public int selectBySqlDoanhThu(String sql) {
        try {
            ResultSet rs = XJdbc.Query(sql);
            while (rs.next()) {                
                int n = rs.getInt("DT");
                return n;
            }
            rs.getStatement().getConnection().close();
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public int selectBySqlSoSach(String sql) {
        try {
            ResultSet rs = XJdbc.Query(sql);
            while (rs.next()) {                
                int n = rs.getInt("SB");
                return n;
            }
            rs.getStatement().getConnection().close();
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                HoaDonChiTiet entity = new HoaDonChiTiet();
                entity.setMaHDCT(rs.getInt("maHDCT"));
                entity.setMaSach(rs.getString("maSach"));
                entity.setMaHD(rs.getInt("maHD"));
                entity.setSoLuong(rs.getInt("soLuong"));
                entity.setGia(rs.getFloat("gia"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
}
