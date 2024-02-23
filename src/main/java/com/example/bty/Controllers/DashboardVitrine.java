//package com.example.bty.Controllers;
//
//import com.example.bty.Controllers.ProduitController.VitrineClient;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.scene.shape.Line;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//
//public class DashboardVitrine extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        StackPane stackPane = new StackPane();
//        stackPane.setPrefSize(1100, 600);
//
//        AnchorPane anchorPane = new AnchorPane();
//        stackPane.getChildren().add(anchorPane);
//
//        BorderPane borderPane = new BorderPane();
//        borderPane.setPrefSize(1100, 600);
//        borderPane.getStyleClass().add("border-pane");
//
//        AnchorPane.setBottomAnchor(borderPane, 0.0);
//        AnchorPane.setTopAnchor(borderPane, 0.0);
//        AnchorPane.setRightAnchor(borderPane, 0.0);
//        AnchorPane.setLeftAnchor(borderPane, 0.0);
//
//        AnchorPane leftAnchorPane = new AnchorPane();
//        leftAnchorPane.setPrefSize(220, 555);
//        AnchorPane.setTopAnchor(leftAnchorPane, 0.0);
//        AnchorPane.setLeftAnchor(leftAnchorPane, 0.0);
//
//        AnchorPane innerAnchorPane = new AnchorPane();
//        innerAnchorPane.getStyleClass().addAll("nav", "nav-border", "shadow");
//        innerAnchorPane.setPrefSize(220, 600);
//
//        FontAwesomeIconView userIcon = new FontAwesomeIconView();
//        userIcon.setFill(javafx.scene.paint.Color.WHITE);
//        userIcon.setGlyphName("USER");
//        userIcon.setLayoutX(82);
//        userIcon.setLayoutY(91);
//        userIcon.setSize("6em");
//
//        Label welcomeLabel = new Label("Welcome,");
//        welcomeLabel.setLayoutX(78);
//        welcomeLabel.setLayoutY(101);
//        welcomeLabel.setTextFill(javafx.scene.paint.Color.WHITE);
//        welcomeLabel.setFont(new Font("Tahoma", 15));
//
//        Label usernameLabel = new Label("MarcoMan");
//        usernameLabel.setId("username");
//        usernameLabel.setAlignment(javafx.geometry.Pos.CENTER);
//        usernameLabel.setLayoutX(11);
//        usernameLabel.setLayoutY(120);
//        usernameLabel.setPrefSize(201, 23);
//        usernameLabel.setTextFill(javafx.scene.paint.Color.WHITE);
//        usernameLabel.setFont(new Font("Arial Bold", 20));
//
//        Line line = new Line();
//        line.setEndX(100);
//        line.setLayoutX(111);
//        line.setLayoutY(152);
//        line.setStartX(-100);
//        line.setStroke(javafx.scene.paint.Color.WHITE);
//
//
//
//
//        Button dashboardBtn = new Button("Dashboard");
//        dashboardBtn.setId("dashboard_btn");
//        dashboardBtn.setLayoutX(21);
//        dashboardBtn.setLayoutY(170);
//        dashboardBtn.setMnemonicParsing(false);
//        dashboardBtn.setPrefSize(180, 35);
//        dashboardBtn.getStyleClass().addAll("nav-btn", "dashboard-btn");
//        dashboardBtn.setText("Dashboard");
//
//        Button userBtn = new Button("Utilisateurs");
//        userBtn.setId("user_btn");
//        userBtn.setLayoutX(21);
//        userBtn.setLayoutY(220);
//        userBtn.setMnemonicParsing(false);
//        userBtn.setPrefSize(180, 35);
//        userBtn.getStyleClass().addAll("nav-btn");
//
//        Button coursBtn = new Button("Cours");
//        coursBtn.setId("cours_btn");
//        coursBtn.setLayoutX(21);
//        coursBtn.setLayoutY(270);
//        coursBtn.setMnemonicParsing(false);
//        coursBtn.setPrefSize(180, 35);
//        coursBtn.getStyleClass().addAll("nav-btn");
//
//        Button evenementBtn = new Button("Evenement");
//        evenementBtn.setId("evenement_btn");
//        evenementBtn.setLayoutX(21);
//        evenementBtn.setLayoutY(320);
//        evenementBtn.setMnemonicParsing(false);
//        evenementBtn.setPrefSize(180, 35);
//        evenementBtn.getStyleClass().addAll("nav-btn");
//
//        Button produitBtn = new Button("Produits");
//        produitBtn.setId("produit_btn");
//        produitBtn.setLayoutX(21);
//        produitBtn.setLayoutY(370);
//        produitBtn.setMnemonicParsing(false);
//        produitBtn.setPrefSize(180, 35);
//        produitBtn.getStyleClass().addAll("nav-btn");
//
//        Button coachBtn = new Button("Coaching privé");
//        coachBtn.setId("coach_btn");
//        coachBtn.setLayoutX(21);
//        coachBtn.setLayoutY(420);
//        coachBtn.setMnemonicParsing(false);
//        coachBtn.setPrefSize(180, 35);
//        coachBtn.getStyleClass().addAll("nav-btn");
//
//        Button reclamationBtn = new Button("Réclamation");
//        reclamationBtn.setId("reclamation_btn");
//        reclamationBtn.setLayoutX(21);
//        reclamationBtn.setLayoutY(470);
//        reclamationBtn.setMnemonicParsing(false);
//        reclamationBtn.setPrefSize(180, 35);
//        reclamationBtn.getStyleClass().addAll("nav-btn");
//
//        Button logoutBtn = new Button();
//        logoutBtn.setId("logout");
//        logoutBtn.setLayoutX(14);
//        logoutBtn.setLayoutY(545);
//        logoutBtn.setMnemonicParsing(false);
//        logoutBtn.getStyleClass().addAll("logout", "shadow");
//
//
//        FontAwesomeIconView logoutIcon = new FontAwesomeIconView();
//        logoutIcon.setFill(javafx.scene.paint.Color.WHITE);
//        logoutIcon.setGlyphName("SIGN_OUT");
//        logoutIcon.setSize("2em");
//
//        logoutBtn.setGraphic(logoutIcon);
//
//        Label logoutLabel = new Label("Logout");
//        logoutLabel.setLayoutX(58);
//        logoutLabel.setLayoutY(551);
//        logoutLabel.setTextFill(javafx.scene.paint.Color.WHITE);
//        logoutLabel.setFont(new Font(15));
//
//        innerAnchorPane.getChildren().addAll(userIcon, welcomeLabel, usernameLabel, line, dashboardBtn, userBtn, coursBtn, evenementBtn, produitBtn, coachBtn, reclamationBtn, logoutBtn, logoutLabel);
//
//        leftAnchorPane.getChildren().add(innerAnchorPane);
//
//        borderPane.setLeft(leftAnchorPane);
//        anchorPane.getChildren().add(borderPane);
//
//
//
//
//
//        Scene scene = new Scene(stackPane, 800, 600);
//        scene.getStylesheets().add(getClass().getResource("/dashboardDesign.css").toExternalForm());
//
//
//
//
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Affichage des produits");
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
