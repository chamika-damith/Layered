package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.model.ItemDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO {
    ArrayList<ItemDTO> allItems() throws SQLException, ClassNotFoundException;
    boolean saveItem(String code, String description, int qtyOnHand, BigDecimal unitPrice) throws SQLException, ClassNotFoundException;
    boolean updateItem(String code, String description, int qtyOnHand, BigDecimal unitPrice) throws SQLException, ClassNotFoundException;
    boolean deleteItems(String code) throws SQLException, ClassNotFoundException;
    boolean existItem(String code) throws SQLException, ClassNotFoundException;
    String generateNewId() throws SQLException, ClassNotFoundException;
    ItemDTO findItems(String newItemCode) throws SQLException, ClassNotFoundException;

}
