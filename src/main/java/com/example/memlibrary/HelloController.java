package com.example.memlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private Button playButton;

    @FXML
    private ImageView mainImageView;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private VBox iconContainer;

    private MediaPlayer mediaPlayer;

    private final List<MediaItem> items = new ArrayList<>();

    private MediaItem currentItem;

    @FXML
    public void initialize() {

        createItems();
        createIcons();

        if (!items.isEmpty()) {
            showItem(items.get(0));
        }
    }

    private void createItems() {

        items.add(new MediaItem(
                "Штани за 40 гривень",
                "/images/40grn.png",
                "«Штани за 40 гривень» — популярний український мем про дешеві, але дуже цінні речі.",
                "/audio/40grn.mp3"
        ));

        items.add(new MediaItem(
                "9 чи 10",
                "/images/9chi10.png",
                "Мем «9 чи 10» став популярним через смішну телефонну розмову.",
                "/audio/9chi10.mp3"
        ));

        items.add(new MediaItem(
                "А я думал сова",
                "/images/sova.png",
                "«А я думав сова» — культовий інтернет-мем із дивного старого відео.",
                "/audio/sova.mp3"
        ));

        items.add(new MediaItem(
                "Вы продоёте рыбов?",
                "/images/ribov.png",
                "Мем із котом та фразою «Вы продоёте рыбов?».",
                "/audio/ribov.mp3"
        ));

        items.add(new MediaItem(
                "Наташ, вставай",
                "/images/natasha.png",
                "Мем із котами, які будять Наташу.",
                "/audio/natasha.mp3"
        ));
    }

    private void createIcons() {

        for (MediaItem item : items) {

            Image image;

            try {

                image = new Image(
                        getClass().getResourceAsStream(item.getImagePath())
                );

            } catch (Exception e) {

                continue;
            }

            ImageView icon = new ImageView(image);

            icon.setFitWidth(90);
            icon.setFitHeight(60);
            icon.setPreserveRatio(true);

            Button button = new Button(item.getTitle());

            button.setGraphic(icon);

            button.setContentDisplay(ContentDisplay.TOP);

            button.setPrefWidth(140);
            button.setPrefHeight(110);

            button.setStyle("""
                -fx-background-color:
                    linear-gradient(to bottom,
                    #ffffff 0%,
                    #dce9fb 45%,
                    #aec6ea 100%);
                -fx-background-radius: 14;
                -fx-border-radius: 14;
                -fx-border-color: #6d8eb5;
                -fx-border-width: 1.2;
                -fx-font-size: 11px;
                -fx-font-weight: bold;
                -fx-text-fill: #17365d;
                -fx-padding: 6;
                -fx-cursor: hand;
            """);

            button.setOnMouseEntered(e -> {

                button.setStyle("""
                    -fx-background-color:
                        linear-gradient(to bottom,
                        #ffffff 0%,
                        #edf5ff 35%,
                        #7fb2f0 100%);
                    -fx-background-radius: 14;
                    -fx-border-radius: 14;
                    -fx-border-color: #5f84b7;
                    -fx-border-width: 1.2;
                    -fx-font-size: 11px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #0f2f57;
                    -fx-padding: 6;
                    -fx-cursor: hand;
                """);
            });

            button.setOnMouseExited(e -> {

                button.setStyle("""
                    -fx-background-color:
                        linear-gradient(to bottom,
                        #ffffff 0%,
                        #dce9fb 45%,
                        #aec6ea 100%);
                    -fx-background-radius: 14;
                    -fx-border-radius: 14;
                    -fx-border-color: #6d8eb5;
                    -fx-border-width: 1.2;
                    -fx-font-size: 11px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #17365d;
                    -fx-padding: 6;
                    -fx-cursor: hand;
                """);
            });

            button.setOnAction(event -> showItem(item));

            iconContainer.getChildren().add(button);
        }
    }

    private void showItem(MediaItem item) {

        currentItem = item;

        Image image = new Image(
                getClass().getResourceAsStream(item.getImagePath())
        );

        mainImageView.setImage(image);

        descriptionArea.setText(item.getDescription());
    }

    public void playAudio(ActionEvent event) {

        try {

            if (currentItem == null) {
                return;
            }

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            String audioPath = getClass()
                    .getResource(currentItem.getAudioPath())
                    .toExternalForm();

            Media media = new Media(audioPath);

            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}