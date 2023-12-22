package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.dao.SQLutil;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dao.custom.impl.ItemDAOImpl;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO{
    private ItemDAO itemDAO=new ItemDAOImpl();

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return itemDAO.getAll();
    }

    @Override
    public ItemDTO findItem(String code) {
        try {
            return itemDAO.findItems(code);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
