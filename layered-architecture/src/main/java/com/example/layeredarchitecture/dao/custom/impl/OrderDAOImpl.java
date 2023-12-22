package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.custom.OrderDAO;
import com.example.layeredarchitecture.dao.SQLutil;

import java.sql.*;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public String generateNextNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLutil.execute("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }
}
