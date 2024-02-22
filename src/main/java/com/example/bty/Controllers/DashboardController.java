//package com.example.bty.Controllers;
//
//import com.example.bty.Entities.User;
//import com.example.bty.Services.IServiceUser;
//import com.example.bty.Services.ServiceUser;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class DashboardController implements Initializable {
//
//
//    // **************** MEMBERS ****************
//    public AnchorPane members_form;
//    public TextField members_customerId;
//    public TextField members_name;
//    public TextArea members_caddress;
//    public TextField members_phoneNum;
//    public ComboBox members_gender;
//    public ComboBox members_schedule;
//    public DatePicker members_startDate;
//    public DatePicker members_endDate;
//    public ComboBox members_status;
//    public Button members_addBtn;
//    public Button members_clearBtn;
//    public Button members_updateBtn;
//    public Button members_deleteBtn;
//    public TableView members_tableView;
//    public TableColumn members_col_customerID;
//    public TableColumn members_col_name;
//    public TableColumn members_col_address;
//    public TableColumn members_col_phoneNum;
//    public TableColumn members_col_gender;
//    public TableColumn members_col_schedule;
//    public TableColumn members_col_startDate;
//    public TableColumn members_col_endDate;
//    public TableColumn members_col_status;
//    public AnchorPane main_form;
//    public Label username;
//    public Button dashboard_btn;
//    public Button coaches_btn;
//    public Button members_btn;
//    public Button logout;
//    public Button payment_btn;
//
//    // **************** COACHES ****************
//    public AnchorPane coaches_form;
//    public TextField coaches_coachID;
//    public TextField coaches_name;
//    public TextArea coaches_address;
//    public ComboBox coaches_gender;
//    public TextField coaches_phoneNum;
//    public Button coaches_createBtn;
//
//    public Button coaches_updateBtn;
//    public Button coaches_resetBtn;
//    public Button coaches_deleteBtn;
//    public ComboBox coaches_status;
//    public TableView coaches_tableView;
//    public TableColumn coaches_col_coachID;
//    public TableColumn coaches_col_name;
//    public TableColumn coaches_col_address;
//    public TableColumn coaches_col_gender;
//    public TableColumn coaches_col_phoneNum;
//    public TableColumn coaches_col_status;
//
//    // ****************  //  fin MEMBERS ****************
//
//    IServiceUser serviceUser=new ServiceUser();
////    public void membersAddBtn(ActionEvent actionEvent) {
////
////    }
////    public void membersClear(ActionEvent actionEvent) {
////
////    }
////    public void membersUpdate(ActionEvent actionEvent) {
////    }
////public void membersSelect(MouseEvent mouseEvent) {
////}
//    public void membersDelete(ActionEvent actionEvent) {
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//    }
//
//    public void switchForm(ActionEvent actionEvent) {
//    }
//
//    public void logout(ActionEvent actionEvent) {
//    }
//
//    //*********** COACHES  METHODS ***********
//
//
//        public void coachesCreateBtn(ActionEvent actionEvent) {
//            // Gather data from form fields
//            String name = coaches_name.getText();
//            String address = coaches_address.getText();
//            String phoneNum = coaches_phoneNum.getText();
//            String gender = coaches_gender.getValue().toString();
//            String status = coaches_status.getValue().toString();
//
//            // Create a new User object
//            User newCoach = new User();
//            newCoach.setName(name);
//            newCoach.setAddress(address);
//            newCoach.setPhoneNum(phoneNum);
//            newCoach.setGender(gender);
//            newCoach.setStatus(status);
//
//            // Use the serviceUser object to register the new coach
//            serviceUser.register(newCoach);
//        }
//
//    public void coachesUpdateBtn(ActionEvent actionEvent) {
//    }
//
//    public void coachesClearBtn(ActionEvent actionEvent) {
//    }
//
//    public void coachesDeleteBtn(ActionEvent actionEvent) {
//    }
//
//    public void coachesSelect(MouseEvent mouseEvent) {
//    }
//
//}
