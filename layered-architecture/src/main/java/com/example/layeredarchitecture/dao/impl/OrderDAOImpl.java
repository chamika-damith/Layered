package com.example.layeredarchitecture.dao.impl;

import com.example.layeredarchitecture.dao.ItemDAO;
import com.example.layeredarchitecture.dao.OrderDAO;
import com.example.layeredarchitecture.dao.OrderDetailsDAO;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private ItemDAO itemDAO=new ItemDAOImpl();

    private OrderDetailsDAO orderDetailsDAO=new OrderDetailsDAOImpl();
    @Override
    public String generateNextNewOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }
    @Override
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = null;

        connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
        stm.setString(1, orderId);
        /*if order id already exist*/
        if (stm.executeQuery().next()) {

        }

        connection.setAutoCommit(false);
        stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
        stm.setString(1, orderId);
        stm.setDate(2, Date.valueOf(orderDate));
        stm.setString(3, customerId);

        if (stm.executeUpdate() != 1) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }


        for (OrderDetailDTO detail : orderDetails) {
            boolean isSaveOrderDetail = orderDetailsDAO.saveOrderDetails(orderDetails, orderId);

            if (!isSaveOrderDetail) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDTO item = itemDAO.findItems(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            boolean isUpdateItem = itemDAO.updateItem(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice());

            if (!isUpdateItem) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }
}
