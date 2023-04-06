package com.example.fazekas_tamas_stopper;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class StopperController {

    @FXML
    private Button buttonStartStop;
    @FXML
    private Button buttonReszido;
    @FXML
    private VBox reszidoBox;
    private Timer timer;
    private String timeLabel;
    @FXML
    private Label labelStopWatch;
    private LocalDateTime endTime = null;
    private static Duration elapsedTime = Duration.ofNanos(0);
    private static Duration savedDuration = Duration.ofNanos(0);
    private Boolean firstRun = true;
    @FXML
    public void startStop(ActionEvent actionEvent) {
        LocalDateTime startTime = LocalDateTime.now();
        if (buttonStartStop.getText().equals("Start")){
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        endTime = LocalDateTime.now();
                        if (firstRun){
                            elapsedTime = Duration.between(startTime, endTime);
                            firstRun = false;
                        } else {
                            elapsedTime = Duration.between(startTime, endTime).plus(savedDuration);
                        }
                        long perc = elapsedTime.toMinutes();
                        long masodperc = elapsedTime.getSeconds();
                        long ezredmasodperc = elapsedTime.toMillis() % 1000;
                        timeLabel = String.format("%02d:%02d.%03d", perc, masodperc, ezredmasodperc);
                        labelStopWatch.setText(String.valueOf(timeLabel));
                    });
                }
            };
            buttonStartStop.setText("Stop");
            buttonReszido.setText("Részidő");
            timer.schedule(task, 0, 1);
            savedDuration = Duration.ofNanos(elapsedTime.toNanos());
        } else {
            timer.cancel();
            buttonStartStop.setText("Start");
            buttonReszido.setText("Reset");
        }
    }

    @FXML
    public void reszido(ActionEvent actionEvent) {
        if (buttonReszido.getText().equals("Reset")){
            reszidoBox.getChildren().clear();
            buttonStartStop.setText("Start");
            buttonReszido.setText("Részidő");
            labelStopWatch.setText("00:00.000");
            elapsedTime = Duration.ofNanos(0);
        } else {
            Label label = new Label(String.valueOf(timeLabel));
            reszidoBox.getChildren().add(label);
        }

    }
}