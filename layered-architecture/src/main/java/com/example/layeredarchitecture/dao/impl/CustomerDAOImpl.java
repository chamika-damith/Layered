package com.example.layeredarchitecture.dao.impl;

import com.example.layeredarchitecture.dao.CustomerDAO;
import com.example.layeredarchitecture.dao.SQLutil;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ResultSet rst =SQLutil.execute("SELECT * FROM Customer");

        ArrayList<CustomerDTO> customerDTOS=new ArrayList<>();

        while (rst.next()){
            CustomerDTO customerDTO=new CustomerDTO(rst.getString("id"), rst.getString("name"), rst.getString("address"));
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public boolean saveCustomer(String id,String name,String address) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO Customer (id,name, address) VALUES (?,?,?)",id,name,address);
    }

    @Override
    public boolean updateCustomer(String id,String name,String address) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE Customer SET name=?, address=? WHERE id=?",id,name,address);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLutil.execute("SELECT id FROM Customer WHERE id=?",id);
        return rst.next();
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM Customer WHERE id=?",id);
    }

    @Override
    public String generateCusNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLutil.execute("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public String SearchCustomer(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLutil.execute("SELECT * FROM Customer WHERE id=?",newValue);
        rst.next();
        CustomerDTO customerDTO = new CustomerDTO(newValue + "", rst.getString("name"), rst.getString("address"));
        return customerDTO.getName();
    }

}
