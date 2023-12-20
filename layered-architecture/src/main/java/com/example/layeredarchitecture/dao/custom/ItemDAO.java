package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.model.ItemDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<ItemDTO>{
    String generateNewId() throws SQLException, ClassNotFoundException;
    ItemDTO findItems(String newItemCode) throws SQLException, ClassNotFoundException;

}
