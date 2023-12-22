package com.example.layeredarchitecture.bo.impl;

import com.example.layeredarchitecture.bo.CustomerBO;
import com.example.layeredarchitecture.bo.ItemBO;
import com.example.layeredarchitecture.bo.PlaceOrderBO;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dao.custom.OrderDAO;
import com.example.layeredarchitecture.dao.custom.OrderDetailsDAO;
import com.example.layeredarchitecture.dao.custom.impl.ItemDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.OrderDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.OrderDetailsDAOImpl;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBoImpl implements PlaceOrderBO {

    private OrderDetailsDAO orderDetailsDAO=new OrderDetailsDAOImpl();
    private CustomerBO customerBO = new CustomerBOImpl();
    private ItemBO itemBO=new ItemBOImpl();
    private OrderDAO orderDAO=new OrderDAOImpl();
    private ItemDAO itemDAO=new ItemDAOImpl();

    @Override
    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
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

            boolean isUpdateItem = itemDAO.update(new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(),item.getQtyOnHand()));

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

    @Override
    public ItemDTO findItem(String code) {
        return itemBO.findItem(code);
    }

    @Override
    public String SearchCustomer(String newValue) throws SQLException, ClassNotFoundException {
        return customerBO.SearchCustomer(newValue);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.existCustomer(id);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerBO.getAllCustomers();
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemBO.existItem(id);
    }

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return itemBO.getAllItems();
    }

    @Override
    public String generateNextNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNextNewOrderId();
    }
}
