package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO {
    boolean existItem(String id) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    ItemDTO findItem(String code);
}
