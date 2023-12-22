package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.bo.impl.CustomerBOImpl;
import com.example.layeredarchitecture.bo.impl.ItemBOImpl;
import com.example.layeredarchitecture.bo.impl.PlaceOrderBoImpl;


public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,PLACEORDER;
    }

    public SuperBO getBO(BOTypes boFactory) {
        switch (boFactory) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PLACEORDER:
                return new PlaceOrderBoImpl();
            default:
                return null;
        }
    }

}
