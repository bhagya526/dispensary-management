package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,DOCTOR,MEDICINE,ORDER,ORDER_DETAIL,PRESCRIPTION,USER
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case DOCTOR:
                return new DoctorDAOImpl();
            case MEDICINE:
                return new MedicineDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();

            case PRESCRIPTION:
                return new PrescriptionDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }



}
