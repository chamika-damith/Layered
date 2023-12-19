package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dao.SQLutil;
import com.example.layeredarchitecture.model.ItemDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<ItemDTO> allItems() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLutil.execute("SELECT * FROM Item");
        ArrayList<ItemDTO> itemDTOS=new ArrayList<>();
        while (rst.next()) {
            ItemDTO itemDTO=new ItemDTO(rst.getString("code"), rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }
    @Override
    public boolean saveItem(String code, String description, int qtyOnHand, BigDecimal unitPrice) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",code,description,qtyOnHand,unitPrice);
    }
    @Override
    public boolean updateItem(String code, String description, int qtyOnHand, BigDecimal unitPrice) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",description,unitPrice,qtyOnHand,code);
    }
    @Override
    public boolean deleteItems(String code) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM Item WHERE code=?",code);
    }
    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLutil.execute("SELECT code FROM Item WHERE code=?",code);
        return rst.next();
    }
    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLutil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }
    @Override
    public ItemDTO findItems(String newItemCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLutil.execute("SELECT * FROM Item WHERE code=?",newItemCode);
        rst.next();
        ItemDTO item = new ItemDTO(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        return item;
    }
}