package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.dao.custom.CustomerDAO;
import com.example.layeredarchitecture.dao.custom.impl.CustomerDAOImpl;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO{
    private CustomerDAO customerDAO=new CustomerDAOImpl();

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String SearchCustomer(String newValue) throws SQLException, ClassNotFoundException {
        return customerDAO.SearchCustomer(newValue);
    }
}
