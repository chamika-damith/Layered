package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.dao.custom.impl.CustomerDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.ItemDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.OrderDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.OrderDetailsDAOImpl;

import static com.example.layeredarchitecture.dao.DAOFactory.DADTypes.*;

public class DAOFactory  {
    public static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getFactory(){
        return (daoFactory==null) ? daoFactory=new DAOFactory(): daoFactory;
    }

    public enum DADTypes{
        CUSTOMER,ITEM,ORDER,ORDERDETAIL;
    }
    public SuperDAO getDao(DADTypes daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailsDAOImpl();
            default:
                return null;
        }
    }
}
