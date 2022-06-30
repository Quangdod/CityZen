/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cityzen.dao;

import java.util.List;

/**
 *
 * @author ASUS
 */
public abstract class CityZenDAO<EntityType, KeyType> {

    abstract void insert(EntityType entity);

    abstract void update(EntityType entity);

    abstract void delete(KeyType id);

    abstract List<EntityType> selectAll();

    abstract EntityType selectById(KeyType id);

    abstract List<EntityType> selectBySql(String sql, Object... args);
}
