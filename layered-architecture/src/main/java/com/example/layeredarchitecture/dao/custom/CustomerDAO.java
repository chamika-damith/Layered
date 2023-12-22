package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.dao.CrudDAO;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<CustomerDTO> {
    String generateCusNewId() throws SQLException, ClassNotFoundException;
    String SearchCustomer(String newValue) throws SQLException, ClassNotFoundException;

}
