package com.devstack.pos.dao;

import com.devstack.pos.dao.custom.impl.CustomerDaoImpl;
import com.devstack.pos.dao.custom.impl.ProductDaoImpl;
import com.devstack.pos.dao.custom.impl.UserDaoImpl;
import com.devstack.pos.enums.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DaoFactory() : daoFactory;
    }

    public <T>T getDao(DaoType daoType) {
        switch (daoType) {
            case Customer:
                return (T) new CustomerDaoImpl();
            case Product:
                return (T) new ProductDaoImpl();
            case User:
                return (T) new UserDaoImpl();
            default:
                return null;
        }
    }
}
