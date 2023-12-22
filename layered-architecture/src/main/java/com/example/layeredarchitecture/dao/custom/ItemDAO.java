package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.dao.CrudDAO;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<ItemDTO> {
    String generateNewId() throws SQLException, ClassNotFoundException;
    ItemDTO findItems(String newItemCode) throws SQLException, ClassNotFoundException;

}
