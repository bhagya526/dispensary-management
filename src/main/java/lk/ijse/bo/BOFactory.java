package lk.ijse.bo;

import lk.ijse.bo.Custom.impl.*;

public class BOFactory {


    private static BOFactory boFactory;
    private BOFactory(){ }


    public static BOFactory getBOFactory(){

        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes{
        CREATE_ACCOUNT,CUSTOMER,DOCTOR,MEDICINE,ORDER,PRESCRIPTION,LOGIN

    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CREATE_ACCOUNT:
                return new CreateAccountBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case DOCTOR:
                return new DoctorBOImpl();
            case MEDICINE:
                return new MedicineBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PRESCRIPTION:
                return new PrescriptionBOImpl();
            case LOGIN:
                return new LoginPageBOImpl();
            default:
                return null;
        }
    }
}
