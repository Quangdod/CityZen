package com.cityzen.dao;

import com.cityzen.entity.HoaDon;
import com.cityzen.entity.Sach;
import com.cityzen.ultils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO extends CityZenDAO<HoaDon, Integer> {

    String INSERT_SQL = "INSERT INTO HoaDon (ngayMua,maNV,ghiChu,maKH) VALUES (?,?,?,?)";
    String UPDATE_SQL = "UPDATE HoaDon SET ngayMua = ?,maNV = ?,ghiChu = ?,maKH = ? WHERE maHD=?";
    String DELETE_SQL = "DELETE FROM HoaDon WHERE maHD=?";
    String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE maHD=?";
    String SELECT = "SELECT top 1 * FROM HoaDon  ORDER BY maHD Desc";
    String SELECT_BY_MANV="SELECT * FROM HoaDon Where maNV=?";
    String KhachHang = "select COUNT(*) as KH from HoaDon";
    
    @Override
    public void insert(HoaDon entity) {
        XJdbc.Update(INSERT_SQL, entity.getNgayMua(), entity.getMaNV(), entity.getGhiChu(), entity.getMaKH());
    }

    @Override
    public void update(HoaDon entity) {
        XJdbc.Update(UPDATE_SQL, entity.getNgayMua(), entity.getMaNV(), entity.getGhiChu(), entity.getMaKH(), entity.getMaHD());
    }

    @Override
    public void delete(Integer id) {
        XJdbc.Update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(Integer id) {
        List<HoaDon> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public int KhachHang() {
       return selectBySqlKhachhang(KhachHang);
    }
    
    public int selectBySqlKhachhang(String sql) {
        try {
            ResultSet rs = XJdbc.Query(sql);
            while (rs.next()) {                
                int n = rs.getInt("KH");
                return n;
            }
            rs.getStatement().getConnection().close();
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getInt("maHD"));
                entity.setNgayMua(rs.getDate("ngayMua"));
                entity.setMaNV(rs.getString("maNV"));
                entity.setGhiChu(rs.getString("ghiChu"));
                entity.setMaKH(rs.getInt("maKH"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HoaDon selectTop1() {
        List<HoaDon> list= selectBySql(SELECT);
        if (list!= null) {
            return list.get(0);
        }
        return null;
    }
     public List<HoaDon> selectByNhanVien(String manv) {
        String SQL = "select * from HoaDon where MaNV = ?";
        return this.selectBySql(SQL, manv);
    }
         public List<HoaDon> selectNotInCourse( String keyword) {
        String sql="SELECT * FROM HoaDon "
                + " WHERE maNV LIKE ?";
//                + " MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";
        return this.selectBySql(sql, "%"+keyword+"%");
    }

    
}
