/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.dao;

import com.cityzen.entity.NhaPhanPhoi;
import com.cityzen.ultils.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LOVE
 */
public class NhaPhanPhoiDao extends CityZenDAO<NhaPhanPhoi, String> {

    String INSERT_SQL = "INSERT INTO NhaPhanPhoi (tenNPP, diaChi, sdt, email, ghiChu, loGo) VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE NhaPhanPhoi SET tenNPP=?, diaChi=?, sdt=?, email=?, ghiChu=?, loGo=? where maNPP = ?";
    String DELETE_SQL = "DELETE FROM NhaPhanPhoi where maNPP=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhaPhanPhoi";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhaPhanPhoi WHERE maNPP=?";

    @Override
    public void insert(NhaPhanPhoi entity) {
        XJdbc.Update(INSERT_SQL,
                entity.getTenNPP(),
                entity.getDiaChi(),
                entity.getSoDT(),
                entity.getEmail(),
                entity.getGhiChu(),
                entity.getLogo());
    }

    @Override
    public void update(NhaPhanPhoi entity) {
        XJdbc.Update(UPDATE_SQL,
                entity.getTenNPP(),
                entity.getDiaChi(),
                entity.getSoDT(),
                entity.getEmail(),
                entity.getGhiChu(),
                entity.getLogo(),
                entity.getMaNPP());
    }

    @Override
    public void delete(String id) {
        XJdbc.Update(DELETE_SQL, id);
    }

    @Override
    public List<NhaPhanPhoi> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public NhaPhanPhoi selectById(int id) {
        List<NhaPhanPhoi> list = selectBySql(SELECT_BY_ID_SQL, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<NhaPhanPhoi> selectBySql(String sql, Object... args) {
        List<NhaPhanPhoi> list = new ArrayList<NhaPhanPhoi>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                NhaPhanPhoi model = new NhaPhanPhoi();
                model.setMaNPP(rs.getInt("maNPP"));
                model.setTenNPP(rs.getString("tenNPP"));
                model.setDiaChi(rs.getString("diaChi"));
                model.setSoDT(rs.getString("sdt"));
                model.setEmail(rs.getString("email"));
                model.setGhiChu(rs.getString("ghiChu"));
                model.setLogo(rs.getString("loGo"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NhaPhanPhoi selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
