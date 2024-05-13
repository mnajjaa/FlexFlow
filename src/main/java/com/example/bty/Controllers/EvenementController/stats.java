package com.example.bty.Controllers.EvenementController;

import com.example.bty.Entities.Evenement;
import com.example.bty.Utils.ConnexionDB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static javafx.application.Application.launch;

public class stats extends Application {
    private Connection connection;
    private DetailsEvenementWindow detailsEvenementWindow;


    public stats() {
        connection = ConnexionDB.getInstance().getConnexion();
    }

    @Override
    public void start(Stage stage) throws Exception {
        detailsEvenementWindow = new DetailsEvenementWindow();

        BarChart<String, Number> eventDateBarChart = createEventDateBarChartForClient();

        Scene scene = new Scene(eventDateBarChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Statistiques des Événements");
        stage.show();

    }



    public BarChart<String, Number> createEventDateBarChartForClient() {
        ObservableList<XYChart.Series<String, Number>> seriesList = FXCollections.observableArrayList();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> eventDateBarChart = new BarChart<>(xAxis, yAxis);

        try {
            String query = "SELECT nomEvenement, Date FROM evenement";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            Map<String, XYChart.Series<String, Number>> eventSeriesMap = new HashMap<>();

            while (resultSet.next()) {
                String eventName = resultSet.getString("nomEvenement");
                java.sql.Date eventDate = resultSet.getDate("Date");
                LocalDate eventLocalDate = eventDate.toLocalDate();
                LocalDate currentDate = LocalDate.now();

                // Filtrer les événements passés
                if (eventLocalDate.isAfter(currentDate)) {
                    long daysUntilEvent = calculateDaysUntilEvent(eventDate);

                    XYChart.Series<String, Number> series = eventSeriesMap.get(eventName);
                    if (series == null) {
                        series = new XYChart.Series<>();
                        series.setName(eventName);
                        eventSeriesMap.put(eventName, series);
                        seriesList.add(series);
                    }

                    series.getData().add(new XYChart.Data<>(eventName, daysUntilEvent));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        eventDateBarChart.setData(seriesList);
        eventDateBarChart.setTitle("Durée jusqu'à la Date des Événements");
        xAxis.setLabel("Événements");
        yAxis.setLabel("Nombre du jour restant");

        return eventDateBarChart;
    }



    private long calculateDaysUntilEvent(Date eventDate) {
        // Convertir java.sql.Date en java.time.LocalDate
        LocalDate eventLocalDate = eventDate.toLocalDate();

        // Obtenir la date actuelle
        LocalDate currentDate = LocalDate.now();

        // Calculer la différence de jours entre la date actuelle et la date de l'événement
        return Math.abs(currentDate.until(eventLocalDate).getDays());
    }

    public static void main(String[] args) {
        launch(args);
    }

}

