package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.custom.CustomerDAO;
import com.example.layeredarchitecture.dao.SQLutil;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
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

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst =SQLutil.execute("SELECT * FROM Customer");

        ArrayList<CustomerDTO> customerDTOS=new ArrayList<>();

        while (rst.next()){
            CustomerDTO customerDTO=new CustomerDTO(rst.getString("id"), rst.getString("name"), rst.getString("address"));
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public boolean save(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO Customer (id,name, address) VALUES (?,?,?)",dto.getId(),dto.getName(),dto.getAddress());
    }

    @Override
    public boolean update(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE Customer SET name=?, address=? WHERE id=?",dto.getName(),dto.getAddress(),dto.getId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLutil.execute("SELECT id FROM Customer WHERE id=?",id);
        return rst.next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM Customer WHERE id=?",id);
    }


}
