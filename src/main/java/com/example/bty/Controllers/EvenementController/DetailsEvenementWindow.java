package com.example.bty.Controllers.EvenementController;

import com.example.bty.Entities.Evenement;
import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Utils.ConnexionDB;
import com.example.bty.Utils.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;




public class DetailsEvenementWindow extends Stage {

    private static Connection connection;
    private PreparedStatement statementInsert;


    private static Map<Evenement, VBox> detailsMap = new HashMap<>();

public DetailsEvenementWindow(){    connection = ConnexionDB.getInstance().getConnexion();
}
    public static void display(Evenement evenement) {
    Stage detailsStage = new Stage();
    detailsStage.setTitle("Détails de l'événement");
    detailsStage.initModality(Modality.APPLICATION_MODAL);
    detailsStage.setResizable(false);
    VBox detailsCard = createDetailsCard(evenement);

    Scene scene = new Scene(detailsCard, 400, 300);
    scene.getStylesheets().add(DetailsEvenementWindow.class.getResource("/com/example/bty/CSSmoduleEvenement/VitrineClient.css").toExternalForm());

    detailsStage.setScene(scene);
    detailsStage.show();
}
public static VBox createDetailsCard(Evenement evenement) {
    DetailsEvenementWindow instance = new DetailsEvenementWindow();

    VBox detailsCard = detailsMap.get(evenement);

    Stage detailsStage = new Stage();
    if (detailsCard == null) {

        detailsCard = new VBox();
        detailsCard.getStyleClass().add("product-card");
        detailsCard.setSpacing(10);
        detailsCard.setPadding(new Insets(20));


        Label dateLabel = new Label("Date : " + evenement.getDate());
        Label timeLabel = new Label("Time : " + evenement.getTime());
        Label categorieLabel = new Label("Catégorie : " + evenement.getCategorie());
        Label objectifLabel = new Label("Objectif : " + evenement.getObjectif());
        Label placeLabel = new Label("Nombre de places : " + evenement.getNbre_place());
        Button ReserverButton = new Button("Reserver");
        ReserverButton.getStyleClass().add("add-to-cart-button");
        // Add an action to the ReserverButton
        ReserverButton.setOnAction(event -> {
         instance.participerAuCours(evenement, ReserverButton);
        });
        detailsCard.getChildren().addAll( dateLabel, timeLabel, categorieLabel, objectifLabel, placeLabel, ReserverButton);
        detailsMap.put(evenement, detailsCard);
    }
    return detailsCard;
}
//    private void participerAuCours(Evenement evenement, Button ReserverButton) {
//        // Récupérer l'utilisateur connecté à partir de la session
//        User loggedInUser = Session.getInstance().getLoggedInUser();
//
//        // Vérifier si l'utilisateur est connecté
//        if (loggedInUser != null) {
//            // Vérifier si l'utilisateur est un membre
//            if (loggedInUser.getRole() == Role.MEMBRE) {
//                if (!hasUserReservedEvent(loggedInUser.getId(), evenement.getId())) {
//                // Afficher une boîte de dialogue de confirmation
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Confirmation de Reservation");
//                alert.setHeaderText(null);
//                alert.setContentText("Êtes-vous sûr de vouloir participer à cet evnement ?");
//
//                // Personnaliser les boutons de la boîte de dialogue
//                ButtonType buttonTypeOui = new ButtonType("Oui");
//                ButtonType buttonTypeNon = new ButtonType("Non");
//                alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);
//
//                // Attendre la réponse de l'utilisateur
//                alert.showAndWait().ifPresent(response -> {
//                    if (response == buttonTypeOui) {
//                        // L'utilisateur a cliqué sur "Oui", procéder à la participation
//                        participerAuCoursEffectif(evenement, ReserverButton,  loggedInUser);
//                    }
//                });
//            }else {
//                afficherMessage("Information", "Vous avez déjà réservé cet événement.");
//            } else  {
//                System.out.println("Seuls les membres peuvent reserver cet evenement.");
//            }}
//        } else {
//            // Gérer le cas où l'utilisateur n'est pas connecté
//            System.out.println("Vous devez être connecté pour reserver cet evenement.");
//        }
//    }
private void participerAuCours(Evenement evenement, Button ReserverButton) {
    // Récupérer l'utilisateur connecté à partir de la session
    User loggedInUser = Session.getInstance().getLoggedInUser();

    // Vérifier si l'utilisateur est connecté
    if (loggedInUser != null) {
        // Vérifier si l'utilisateur est un membre
        if (loggedInUser.getRole() == Role.MEMBRE) {
            // Vérifier si l'utilisateur a déjà réservé cet événement
            if (!hasUserReservedEvent(loggedInUser.getId(), evenement.getNom())) {
                // Afficher une boîte de dialogue de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de Reservation");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez confirmer votre inscription à cet événement.");

                // Personnaliser les boutons de la boîte de dialogue
                ButtonType buttonTypeOui = new ButtonType("Oui");
                ButtonType buttonTypeNon = new ButtonType("Non");
                alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                // Attendre la réponse de l'utilisateur
                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeOui) {
                        // L'utilisateur a cliqué sur "Oui", procéder à la participation
                        participerAuCoursEffectif(evenement, ReserverButton,  loggedInUser);
                    }
                });
            } else {
                afficherMessage("Information", "Vous avez déjà réservé cet événement.");
            }
        } else {
            System.out.println("Seuls les membres peuvent réserver cet événement.");
        }
    } else {
        // Gérer le cas où l'utilisateur n'est pas connecté
        System.out.println("Vous devez être connecté pour réserver cet événement.");
    }
}

    private void participerAuCoursEffectif(Evenement evenement, Button ReserverButton, User loggedInUser) {
        int userID = loggedInUser.getId();
        String userName = loggedInUser.getName();

        try {
            if (evenement.getNbre_place() > 0) {
                // Insérer l'ID du membre et l'ID du cours dans la table de participation
                String queryInsert = "INSERT INTO reservation (id_user, nomEvenement, nomParticipant,date_reservation) VALUES (?,?,?,?)";
                PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
                statementInsert.setInt(1, userID);
                statementInsert.setString(2, evenement.getNom());
                statementInsert.setString(3, userName); // Envoyer le nom du participant à la base de données
                statementInsert.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                int rowsInserted = statementInsert.executeUpdate();

                if (rowsInserted > 0) {
                    // Participation réussie
                    afficherMessage("Succès","Vous avez participé à l'evenement avec succès !");

                    // Diminuer la capacité du cours dans la base de données
                    evenement.setNbre_place(evenement.getNbre_place() - 1);
                    String queryUpdate = "UPDATE evenement SET nbrPlace = ? WHERE id_evenement  = ?";
                    PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate);
                    statementUpdate.setInt(1, evenement.getNbre_place());
                    statementUpdate.setInt(2, evenement.getId());
                    statementUpdate.executeUpdate();

                    // Vérifier si la capacité est épuisée
                    if (evenement.getNbre_place() == 0) {
                        ReserverButton.setDisable(true);
                        ReserverButton.setText("Complet");
                        ReserverButton.setStyle("-fx-opacity: 0.5");
                    }
                } else {
                    afficherMessage("Échec","Erreur lors de la participation à l'evenement.");
                }
            } else {
                afficherMessage ("INFORMATION","La capacité de cet evenement est épuisée. Vous ne pouvez plus reserver.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void afficherMessage(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    // Méthode pour vérifier si l'utilisateur a déjà réservé cet événement
    private boolean hasUserReservedEvent(int userId, String eventName) {
        try {
            String queryCheckReservation = "SELECT COUNT(*) FROM reservation WHERE id_user = ? AND nomEvenement = ?";
            PreparedStatement statementCheckReservation = connection.prepareStatement(queryCheckReservation);
            statementCheckReservation.setInt(1, userId);
            statementCheckReservation.setString(2, eventName);
            ResultSet resultSet = statementCheckReservation.executeQuery();

            if (resultSet.next()) {
                int reservationCount = resultSet.getInt(1);
                return reservationCount > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
