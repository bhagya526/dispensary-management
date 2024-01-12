package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Dto.MedicineDto;
import lk.ijse.Dto.PlaceOrderDto;
import lk.ijse.Dto.tm.CartTm;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.MedicineBO;
import lk.ijse.bo.Custom.OrderBO;
import lk.ijse.bo.Custom.PrescriptionBO;
import lk.ijse.entity.Medicine;
import lk.ijse.entity.Prescription;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderFormController {
    public JFXComboBox<String> cmbOrderId;
    public JFXComboBox<String> cmbPresId;
    public JFXComboBox<String> cmbmedId;
    public Label lblmedname;
    public Label lblmedtype;
    public Label lblqtyonhand;
    public Label lblmedprice;
    public TableView tblcart;
    public TableColumn colid;
    public TableColumn colname;
    public TableColumn colqty;
    public TableColumn colprice;
    public TableColumn coltot;
    public JFXTextField txtqty;

    public JFXTextField txtorderId;
    public Label lblnetTot;

    public Label lblDate;
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();
    OrderBO orderBO = (OrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ORDER);
    PrescriptionBO prescriptionBO = (PrescriptionBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PRESCRIPTION);
    MedicineBO medicineBO = (MedicineBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEDICINE);


    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        loadAllPrescription();
        loadAllMedicine();
        setDate();
    }

    private void generateNextOrderId() {
        try {
            String orderId = orderBO.generateNextOrderId();
            txtorderId.setText(orderId);
            txtorderId.setEditable(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllPrescription() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<Prescription> idList = prescriptionBO.LoadAllPrescription();

            for (Prescription dto : idList) {
                obList.add(dto.getPres_id());
            }

            cmbPresId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllMedicine() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<MedicineDto> idList = medicineBO.getAllMedicine();

            for (MedicineDto dto : idList) {
                obList.add(dto.getMedicine_id());
            }

            cmbmedId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("medId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("medName"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("medQty"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("medPrice"));
        coltot.setCellValueFactory(new PropertyValueFactory<>("total"));
    }


    public void orderidOnAction(ActionEvent actionEvent) {
    }

    public void medIdOnAction(ActionEvent actionEvent) {
        String id = cmbmedId.getValue();

        try {
            if (id != null) {
                Medicine dto = medicineBO.searchMedicine(id);

                lblmedname.setText(dto.getName());
                lblmedprice.setText(dto.getPrice());
                lblmedtype.setText(dto.getType());
                lblqtyonhand.setText(dto.getQty());
            }

            txtqty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException {
        String orderId = txtorderId.getText();
        String date = lblDate.getText();
        String presId = cmbPresId.getValue();
        double total = Double.parseDouble(lblnetTot.getText());
        String type = lblmedtype.getText();
        String medId = cmbmedId.getValue();

        List<CartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblcart.getItems().size(); i++) {
            cartTmList.add((CartTm) tblcart.getItems().get(i));
        }

        System.out.println("ok1");
        PlaceOrderDto placeOrderDto = new PlaceOrderDto(orderId,date,presId,total,type,medId,cartTmList);
        try{
            Boolean isSuccess = orderBO.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Successfully Placed!").show();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addtoCartOnAction(ActionEvent actionEvent) {

        String medid = cmbmedId.getValue();
        String medname = lblmedname.getText();
        int qty = Integer.parseInt(txtqty.getText());
        double unitPrice = Double.parseDouble(lblmedprice.getText());
        double tot = unitPrice * qty;

        if (!txtqty.getText().isEmpty() || !(qty <= 0)) {
            if (qty <= Integer.parseInt(lblqtyonhand.getText())) {

                if (!obList.isEmpty()) {
                    for (int i = 0; i < tblcart.getItems().size(); i++) {
                        if (colid.getCellData(i).equals(medid)) {
                            int col_qty = Integer.valueOf(String.valueOf(colqty.getCellData(i)));
                            qty += col_qty;
                            tot = unitPrice * qty;

                            obList.get(i).setMedQty(String.valueOf(qty));
                            obList.get(i).setTotal(String.valueOf(tot));

                            calculateTotal();
                            tblcart.refresh();
                            return;
                        }
                    }
                }


                var cartTm = new CartTm(medid, medname, String.valueOf(qty), String.valueOf(unitPrice), String.valueOf(tot));

                obList.add(cartTm);

                tblcart.setItems(obList);
                calculateTotal();
                txtqty.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Out of stock").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity").show();
        }
    }

    private void calculateTotal() {

        double total = 0;
        for (int i = 0; i < tblcart.getItems().size(); i++) {
            total += Double.parseDouble((String) coltot.getCellData(i));
        }

        lblnetTot.setText(String.valueOf(total));
    }
}
