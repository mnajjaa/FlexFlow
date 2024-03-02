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
package com.example.bty.Controllers;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    // **************** MEMBERS ****************
    @FXML
    public AnchorPane members_form;
    @FXML
    public TextField members_customerId;
    @FXML
    public TextField members_name;
    @FXML
    public TextField members_phoneNum;
    @FXML
    public ComboBox members_status;
    @FXML
    public Button members_addBtn;
    @FXML
    public Button members_updateBtn;
    @FXML
    public Button members_deleteBtn;
    @FXML
    public TableView members_tableView;
    @FXML
    public TableColumn members_col_customerID;
    @FXML
    public TableColumn members_col_name;
    @FXML
    public TableColumn members_col_phoneNum;
    @FXML
    public TableColumn members_col_status;
    @FXML
    public TableColumn members_col_email;
    @FXML
    public AnchorPane main_form;
    @FXML
    public Label username;
    @FXML
    public Button dashboard_btn;
    @FXML
    public Button coaches_btn;
    @FXML
    public Button members_btn;
    @FXML
    public Button logout;
    @FXML
    public Button payment_btn;

    // **************** COACHES *****************
    public static int id;
    @FXML
    public AnchorPane coaches_form;
    @FXML
    public TextField coaches_coachID;
    @FXML
    public TextField coaches_name;
    @FXML
    public TextField coaches_phoneNum;
    @FXML
    public Button coaches_createBtn;
    @FXML
    public Button coaches_updateBtn;
    @FXML
    public Button coaches_deleteBtn;
    @FXML
    public TableView<User> coaches_tableView;
    @FXML
    public TableColumn<User, Integer> coaches_col_coachID;
    @FXML
    public TableColumn<User, String> coaches_col_nom;
    @FXML
    public TableColumn<User, String> coaches_col_email;
    @FXML
    public TableColumn<User, String> coaches_col_telephone;
    @FXML
    public TableColumn<User, Boolean> coaches_col_etat;
    @FXML
    public TextField coaches_password;
    @FXML
    public ComboBox coaches_etat;
    @FXML
    public TextField coaches_telephone;
    @FXML
    public TextField coaches_email;
    public Button user_btn;
    public Button cours_btn;
    public Button evenement_btn;
    public Button produit_btn;
    public Button coach_btn;
    public Button reclamation_btn;
    public AnchorPane coaches_list;
    public AnchorPane dashboard_coach;
    public AnchorPane dashboard_membre;
    public AnchorPane dashboard_Admin;
    public Label usernameAdmin;
    public Label usernameMembre;
    public Label usernameCoach;
    public Button Events_btn;
    public Button Produtcts_btn;
    public Button Reclamations_btn;
    public Button Cours_btn;
    public Button logoutMembre;
    public Button CoursCoach_btn;
    public Button membreCoach_btn;
    public Button logoutCoach;
    public Button Produits_btn;
    public Button eventsAdmin_btn;
    public Button coursAdmin_btn;
    public Button storeAdmin_btn;
    public Button profileAdmin_btn;
    public Button logout00_btn;
    public Button report_btn;
    public Button commande_btn;
    public Button offre_btn;
    public TableColumn coaches_col_action;
    @FXML
    public AnchorPane user_profil;
    public ImageView image;
    public AnchorPane box_1;
    public Button coaches_updateBtn1;
    public AnchorPane box_11;
    public TextField confirm_password;
    public ImageView profile_img;
    public Button update_img_btn;
    @FXML
    public TextField profil_name;
    @FXML
    public TextField profil_email;
    @FXML
    public TextField profil_telephone;
    public TextField new_password;
    public AnchorPane membres_list;
    public TableView membres_tableView;
    public TableColumn membres_col_ID;
    public TableColumn membres_col_nom;
    public TableColumn membres_col_email;
    public TableColumn membres_col_telephone;
    public TableColumn membres_col_action;
    public TableColumn membres_col_etat;
    Session session = Session.getInstance();
    User u=session.getLoggedInUser();
    User user ;
    public static String pathImage ;


    // ****************  //  fin MEMBERS ****************

    IServiceUser serviceUser00=new ServiceUser();

    public void consulterMembers() {
        // Fetch all members
        List<User> members = serviceUser00.getAllMembers();

        // Clear the table
        members_tableView.getItems().clear();

        // Define how to populate the columns
        membres_col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        membres_col_nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        membres_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        membres_col_telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        membres_col_etat.setCellFactory(tc -> new TableCell<User, Boolean>() {
            private CheckBox checkBox = new CheckBox();
            private Label label = new Label();

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item);
                    checkBox.setOnAction(e -> {
                        User member = getTableView().getItems().get(getIndex());
                        member.setEtat(checkBox.isSelected());
                        serviceUser00.update(member); // Assuming update() method updates the member in the database
                        label.setText(checkBox.isSelected() ? "Actif" : "Inactif");
                        label.getStyleClass().clear();
                        label.getStyleClass().add(checkBox.isSelected() ? "label-active" : "label-inactive");
                    });
                    label.setText(item ? "Actif" : "Inactif");
                    label.getStyleClass().add(item ? "label-active" : "label-inactive");
                    HBox hBox = new HBox(checkBox, label);
                    hBox.setSpacing(10);
                    setGraphic(hBox);
                }
            }
        });

        membres_col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        membres_col_action.setCellFactory(param -> new TableCell<User, Void>() {
            //**********
                final FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                final HBox pane = new HBox(deleteIcon);

                {
                    deleteIcon.getStyleClass().add("delete-icon");

                    deleteIcon.setOnMouseClicked(event -> {
                        User selectedMember = (User) members_tableView.getSelectionModel().getSelectedItem();
                        // Create a confirmation dialog
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Delete Coach");
                        alert.setContentText("Are you sure you want to delete this coach?");

                        // Show the dialog and wait for user's response
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                // If user confirmed, delete the coach
                                serviceUser00.delete(selectedMember.getId());

                                // Refresh the table
                                consulterMembers();
                            }
                        });
                    });
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(pane);
                    }
                }

            //**********

        });


        // Add fetched members to the table
        members_tableView.getItems().addAll(members);
    }
    public void membersSelect(MouseEvent mouseEvent) {
    }


    public void membersAddBtn(ActionEvent actionEvent) {

    }

    public void membersUpdate(ActionEvent actionEvent) {
    }
    public void membersDelete(ActionEvent actionEvent) {
    }

    public void consulterCoaches() {
        // Fetch all coaches
        List<User> coaches = serviceUser00.getAllCoaches();

        // Clear the table
        coaches_tableView.getItems().clear();

        // Define how to populate the columns
        coaches_col_coachID.setCellValueFactory(new PropertyValueFactory<>("id"));
        coaches_col_nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        coaches_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        coaches_col_telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        coaches_col_etat.setCellFactory(tc -> new TableCell<User, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setGraphic(null);
                } else {
                    Label label = new Label();
                    if (item) {
                        label.setText("Actif");
                        label.setStyle("-fx-text-fill: green;");
                    } else {
                        label.setText("Inactif");
                        label.setStyle("-fx-text-fill: red;");
                    }
                    setGraphic(label);
                }
            }
        });
        coaches_col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

 //*************************************************************************************
        coaches_col_action.setCellFactory(param -> new TableCell<User, Void>() {
            final FontAwesomeIconView updateIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
            final FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            final HBox pane = new HBox(updateIcon, deleteIcon);

            {
                updateIcon.getStyleClass().add("update-icon");
                deleteIcon.getStyleClass().add("delete-icon");

                updateIcon.setOnMouseClicked(event -> {
//  Get the selected coach
                    User selectedCoach = coaches_tableView.getSelectionModel().getSelectedItem();

                    // Check if a row has been selected
                    if (selectedCoach != null) {
                        id = selectedCoach.getId();
                        coaches_list.setVisible(false);
                        coaches_list.setManaged(false);

                        coaches_form.setVisible(true);
                        coaches_form.setManaged(true);

                        // Populate the form fields with the selected coach's details

                        coaches_name.setText(selectedCoach.getName());
                        coaches_email.setText(selectedCoach.getEmail());
                        coaches_telephone.setText(selectedCoach.getTelephone());
                        coaches_password.setText(selectedCoach.getPassword());

                        coaches_name.setEditable(true);
                        coaches_email.setEditable(true);
                        coaches_telephone.setEditable(true);
                        coaches_password.setEditable(true);


                        System.out.println(coaches_name.getText() + " " + coaches_email.getText() + " " + coaches_telephone.getText());
                    }
                });
                deleteIcon.setOnMouseClicked(event -> {
                    User selectedCoach = coaches_tableView.getSelectionModel().getSelectedItem();

                    // Create a confirmation dialog
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Delete Coach");
                    alert.setContentText("Are you sure you want to delete this coach?");

                    // Show the dialog and wait for user's response
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // If user confirmed, delete the coach
                            serviceUser00.delete(selectedCoach.getId());

                            // Refresh the table
                            consulterCoaches();
                        }
                    });
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });

        // Add fetched coaches to the table
        coaches_tableView.getItems().addAll(coaches);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.user = session.getLoggedInUser();
        System.out.println("Image"+user.getImage());

        profil_name.setText(session.getLoggedInUser().getName());
        profil_email.setText(session.getLoggedInUser().getEmail());
        profil_telephone.setText(session.getLoggedInUser().getTelephone());
        if(user.getImage()!=null){
            Image img = new Image("file:"+user.getImage());
            profile_img.setImage(img);
        }


        if(user.getRole().equals(Role.ADMIN)){
            dashboard_Admin.setVisible(true);
            dashboard_Admin.setManaged(true);

            //dashboard_coach.setVisible(false);
            dashboard_membre.setVisible(false);
            dashboard_membre.setManaged(false);
            usernameAdmin.setText(u.getName());
          //  consulterCoaches();
        }
        else if(user.getRole().equals(Role.COACH)){
            dashboard_coach.setVisible(true);
            dashboard_Admin.setVisible(false);
            dashboard_membre.setVisible(false);
            usernameCoach.setText(u.getName());
        }
        else if(user.getRole().equals(Role.MEMBRE)){
            System.out.println("membre found");
            dashboard_membre.setVisible(true);
            dashboard_Admin.setVisible(false);
            dashboard_coach.setVisible(false);
            usernameMembre.setText(u.getName());
        }
        else{
            System.out.println("user not found");
        }

     consulterMembers();
    }

    public void switchForm(ActionEvent actionEvent) {
 if (actionEvent.getSource().equals(dashboard_btn)) {
            main_form.setVisible(true);
            members_form.setVisible(false);
            coaches_form.setVisible(false);
        } else if (actionEvent.getSource().equals(members_btn)) {
            main_form.setVisible(false);
            members_form.setVisible(true);
            coaches_form.setVisible(false);
        } else if (actionEvent.getSource().equals(coaches_btn)) {
            main_form.setVisible(false);
            members_form.setVisible(false);
            coaches_form.setVisible(true);
        }
 //ne9ssa ***********
    }

    public void logout(ActionEvent actionEvent) {
    }

    //*********** COACHES  METHODS ***********


    public void coachesCreateBtn(ActionEvent actionEvent) {
        // Gather data from form fields
        String name = coaches_name.getText();
        String email = coaches_email.getText();
        String password = coaches_password.getText();
        String telephone = coaches_telephone.getText();
        Role defaultRole = Role.COACH; // Définissez le rôle par défaut ici

        // Check if the email already exists
        if (serviceUser00.emailExists(email)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Email Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("Email already exists. Please use a different email.");
            alert.showAndWait();
        } else {
            User newCoach = new User(name, email, password, telephone, defaultRole,true,null);
            // Appeler la méthode d'inscription avec les valeurs récupérées
            serviceUser00.register(newCoach);
            // serviceUser00.ActiverOrDesactiver(newCoach.getId());
            System.out.println("Coach created successfully! and activated ");
        }
    }

    public void coachesUpdateBtn(ActionEvent actionEvent) {

    User coach=new User(id,coaches_name.getText(),coaches_email.getText(),coaches_telephone.getText(),Role.COACH,true,null);
    serviceUser00.update(coach);
        coaches_list.setVisible(true);
        coaches_list.setManaged(true);

        coaches_form.setVisible(false);
        coaches_form.setManaged(false);
        consulterCoaches();
    }

    public void coachesDeleteBtn(ActionEvent actionEvent) {
    }

    public void coachesSelect(MouseEvent mouseEvent) {

    }


    public void goToDashbordAdmin(ActionEvent actionEvent) {
    }

    public void goToCoach(ActionEvent actionEvent) {
        consulterCoaches();
    }

    public void goToMembre(ActionEvent actionEvent) {
        consulterMembers();
    }

    public void goToEvents(ActionEvent actionEvent) {
    }

    public void goToCoachs(ActionEvent actionEvent) {
    }

    public void goToCours(ActionEvent actionEvent) {
    }

    public void goToReclamations(ActionEvent actionEvent) {
    }

    public void goToCoursCoach(ActionEvent actionEvent) {
    }

    public void goToMembreCoach(ActionEvent actionEvent) {
    }

    public void goToStoreAdmin(ActionEvent event) {
    }

    public void goToCommandes(ActionEvent event) {

    }

    public void goToOffres(ActionEvent event) {


    }

    public void coachesUpdateImage(ActionEvent event) {





    }

    @FXML
    public void goToProfile(ActionEvent event) {
        System.out.println("Hello");
        user_profil.setManaged(true);
        user_profil.setVisible(true);
        coaches_list.setManaged(false);
        coaches_list.setVisible(false);
        coaches_form.setManaged(false);
        coaches_form.setVisible(false);


    }

    public void updateImgBtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        pathImage= fileChooser.showOpenDialog(null).getAbsolutePath();
        Image img = new Image("file:"+pathImage);
        profile_img.setImage(img);
        serviceUser00.updateImage(pathImage,Session.getInstance().getLoggedInUser().getId());
    }

    public void updateCoachBtn(ActionEvent event) {
    }


    public void dali(ActionEvent event) {
        System.out.println("Hello");
        user_profil.setManaged(true);
        user_profil.setVisible(true);
        coaches_list.setManaged(false);
        coaches_list.setVisible(false);
        coaches_form.setManaged(false);
        coaches_form.setVisible(false);

    }

    public void updatePassword(ActionEvent event) {
       if(!Objects.equals(new_password.getText(), confirm_password.getText())){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Password");
           alert.setHeaderText(null);
           alert.setContentText("Password not match");
           alert.showAndWait();
       }
       else{
           serviceUser00.updatePassword(new_password.getText(),Session.getInstance().getLoggedInUser().getId());
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Password");
                alert.setHeaderText(null);
                alert.setContentText("Password updated");
                alert.showAndWait();
                new_password.clear();
                confirm_password.clear();
       }
    }

}
package com.example.bty.Controllers;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Services.IServiceUser;
import com.example.bty.Services.ServiceUser;
import com.example.bty.Utils.Session;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    // **************** MEMBERS ****************
    @FXML
    public AnchorPane members_form;
    @FXML
    public TextField members_customerId;
    @FXML
    public TextField members_name;
    @FXML
    public TextField members_phoneNum;
    @FXML
    public ComboBox members_status;
    @FXML
    public Button members_addBtn;
    @FXML
    public Button members_updateBtn;
    @FXML
    public Button members_deleteBtn;
    @FXML
    public TableView members_tableView;
    @FXML
    public TableColumn members_col_customerID;
    @FXML
    public TableColumn members_col_name;
    @FXML
    public TableColumn members_col_phoneNum;
    @FXML
    public TableColumn members_col_status;
    @FXML
    public TableColumn members_col_email;
    @FXML
    public AnchorPane main_form;
    @FXML
    public Label username;
    @FXML
    public Button dashboard_btn;
    @FXML
    public Button coaches_btn;
    @FXML
    public Button members_btn;
    @FXML
    public Button logout;
    @FXML
    public Button payment_btn;

    // **************** COACHES *****************
    @FXML
    public AnchorPane coaches_form;
    @FXML
    public TextField coaches_coachID;
    @FXML
    public TextField coaches_name;
    @FXML
    public TextField coaches_phoneNum;
    @FXML
    public Button coaches_createBtn;
    @FXML
    public Button coaches_updateBtn;
    @FXML
    public Button coaches_deleteBtn;
    @FXML
    public TableView<User> coaches_tableView;
    @FXML
    public TableColumn<User, Integer> coaches_col_coachID;
    @FXML
    public TableColumn<User, String> coaches_col_nom;
    @FXML
    public TableColumn<User, String> coaches_col_email;
    @FXML
    public TableColumn<User, String> coaches_col_telephone;
    @FXML
    public TableColumn<User, Boolean> coaches_col_etat;
    @FXML
    public TextField coaches_password;
    @FXML
    public ComboBox coaches_etat;
    @FXML
    public TextField coaches_telephone;
    @FXML
    public TextField coaches_email;
    public Button user_btn;
    public Button cours_btn;
    public Button evenement_btn;
    public Button produit_btn;
    public Button coach_btn;
    public Button reclamation_btn;
    public AnchorPane coaches_list;
    public TableColumn coaches_col_action;
    public VBox dashboard_coach;
    public VBox dashboard_membre;
    public VBox dashboard_Admin;
    public Label usernameAdmin;
    public Label usernameMembre;
    public Label usernameCoach;
    public Button Events_btn;
    public Button Produtcts_btn;
    public Button Reclamations_btn;
    public Button Cours_btn;
    public Button logoutMembre;
    public Button CoursCoach_btn;
    Session session = Session.getInstance();
    User u=session.getLoggedInUser();
    User user ;


    // ****************  //  fin MEMBERS ****************

    IServiceUser serviceUser00=new ServiceUser();

    public void consulterMembers() {
        // Fetch all members
        List<User> members = serviceUser00.getAllMembers();

        // Clear the table
        members_tableView.getItems().clear();

        // Define how to populate the columns
        members_col_customerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        members_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        members_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        members_col_status.setCellValueFactory(new PropertyValueFactory<>("etat"));
        members_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Add fetched members to the table
        members_tableView.getItems().addAll(members);
    }
    public void membersSelect(MouseEvent mouseEvent) {
    }


    public void membersAddBtn(ActionEvent actionEvent) {

    }

    public void membersUpdate(ActionEvent actionEvent) {
    }
    public void membersDelete(ActionEvent actionEvent) {
    }

    public void consulterCoaches() {
        // Fetch all coaches
        List<User> coaches = serviceUser00.getAllCoaches();

        // Clear the table
        coaches_tableView.getItems().clear();

        // Define how to populate the columns
        coaches_col_coachID.setCellValueFactory(new PropertyValueFactory<>("id"));
        coaches_col_nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        coaches_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        coaches_col_telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        coaches_col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        // Add fetched coaches to the table
        coaches_tableView.getItems().addAll(coaches);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.user = session.getLoggedInUser();
        if(user.getRole().equals(Role.ADMIN)){
            dashboard_Admin.setVisible(true);
            //dashboard_coach.setVisible(false);
            dashboard_membre.setVisible(false);
            usernameAdmin.setText(u.getName());
            consulterCoaches();
        }
        else if(user.getRole().equals(Role.COACH)){
            dashboard_coach.setVisible(true);
            dashboard_Admin.setVisible(false);
            dashboard_membre.setVisible(false);
            usernameCoach.setText(u.getName());
        }
        else if(user.getRole().equals(Role.MEMBRE)){
            System.out.println("membre found");
            dashboard_membre.setVisible(true);
            dashboard_Admin.setVisible(false);
            dashboard_coach.setVisible(false);
            usernameMembre.setText(u.getName());
        }
        else{
            System.out.println("user not found");
        }

    // consulterMembers();
    }

    public void switchForm(ActionEvent actionEvent) {
 if (actionEvent.getSource().equals(dashboard_btn)) {
            main_form.setVisible(true);
            members_form.setVisible(false);
            coaches_form.setVisible(false);
        } else if (actionEvent.getSource().equals(members_btn)) {
            main_form.setVisible(false);
            members_form.setVisible(true);
            coaches_form.setVisible(false);
        } else if (actionEvent.getSource().equals(coaches_btn)) {
            main_form.setVisible(false);
            members_form.setVisible(false);
            coaches_form.setVisible(true);
        }
 //ne9ssa ***********
    }

    public void logout(ActionEvent actionEvent) {
    }

    //*********** COACHES  METHODS ***********


    public void coachesCreateBtn(ActionEvent actionEvent) {
        // Gather data from form fields
        String name = coaches_name.getText();
        String email = coaches_email.getText();
        String password = coaches_password.getText();
        String telephone = coaches_telephone.getText();
        Role defaultRole = Role.COACH; // Définissez le rôle par défaut ici

        // Check if the email already exists
        if (serviceUser00.emailExists(email)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Email Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("Email already exists. Please use a different email.");
            alert.showAndWait();
        } else {
            User newCoach = new User(name, email, password, telephone, defaultRole,null);
            // Appeler la méthode d'inscription avec les valeurs récupérées
            serviceUser00.register(newCoach);
            // serviceUser00.ActiverOrDesactiver(newCoach.getId());
            System.out.println("Coach created successfully! and activated ");
        }
    }

    public void coachesUpdateBtn(ActionEvent actionEvent) {
    }

    public void coachesDeleteBtn(ActionEvent actionEvent) {
    }

    public void coachesSelect(MouseEvent mouseEvent) {
    }


    public void goToDashbordAdmin(ActionEvent actionEvent) {
    }

    public void goToCoach(ActionEvent actionEvent) {
    }

    public void goToMembre(ActionEvent actionEvent) {
    }

    public void goToEvents(ActionEvent actionEvent) {
    }

    public void goToCoachs(ActionEvent actionEvent) {
    }

    public void goToCours(ActionEvent actionEvent) {
    }

    public void goToReclamations(ActionEvent actionEvent) {
    }

    public void goToCoursCoach(ActionEvent actionEvent) {
    }

    public void goToMembreCoach(ActionEvent actionEvent) {
    }
}
