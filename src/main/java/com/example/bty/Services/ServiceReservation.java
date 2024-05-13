//package com.example.bty.Services;
//
//import com.example.bty.Entities.Evenement;
//import com.example.bty.Entities.Reservation;
//import com.example.bty.Utils.ConnexionDB;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ServiceReservation {
//    private Connection connexion;
//    public ServiceReservation(){connexion= ConnexionDB.getInstance().getConnexion();}
//    public List<Reservation> getActiveReservations(int userId) {
//        List<Reservation> activeReservations = new ArrayList<>();
//
//        try {
//            String query = "SELECT * FROM reservation WHERE id_user = ? AND temps_restant > 0";
//            PreparedStatement statement = connexion.prepareStatement(query);
//            statement.setInt(1, userId);
//
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                int reservationId = resultSet.getInt("id");
//
//                // Vous devrez adapter ces lignes en fonction de la structure de votre table Reservation
//                String nomEvenement = resultSet.getString("nom_evenement");
//                Timestamp dateReservation = resultSet.getTimestamp("date_reservation");
//                BigDecimal tempsRestantBigDecimal = resultSet.getBigDecimal("temps_restant");
//                BigInteger tempsRestant = tempsRestantBigDecimal != null ? tempsRestantBigDecimal.toBigInteger() : BigInteger.ZERO;
//
//                Reservation reservation = new Reservation(reservationId,dateReservation,nomEvenement, userId,  tempsRestant);
//                activeReservations.add(reservation);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return activeReservations;
//    }
//}
