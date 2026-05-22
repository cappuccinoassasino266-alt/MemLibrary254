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
                "«Штани за 40 гривень» — популярний український мем про дешеві, але дуже цінні речі, які шкода втратити.",
                "/audio/40grn.mp3"
        ));

        items.add(new MediaItem(
                "9 чи 10",
                "/images/9chi10.png",
                "Мем «9 чи 10» став популярним через смішну телефонну розмову та фразу «не чує баба».",
                "/audio/9chi10.mp3"
        ));


        items.add(new MediaItem(
                "А я думал сова",
                "/images/sova.png",
                "«А я думав сова» — культовий інтернет-мем, що виник із уривка старого відео.",
                "/audio/sova.mp3"
        ));

        items.add(new MediaItem(
                "Вы продоёте рыбов?",
                "/images/ribov.png",
                "Мем із котом та фразою «Вы продоёте рыбов?» став дуже популярним у соцмережах.",
                "/audio/ribov.mp3"
        ));


        items.add(new MediaItem(
                "Наташ, вставай",
                "/images/natasha.png",
                "Мем із котами, які будять Наташу та повідомляють дивні новини.",
                "/audio/natasha.mp3"
        ));


    }

    private void createIcons() {

        for (MediaItem item : items) {

            ImageView icon = new ImageView(
                    new Image(getClass().getResourceAsStream(item.getImagePath()))
            );

            icon.setFitWidth(90);
            icon.setFitHeight(60);
            icon.setPreserveRatio(true);

            Button button = new Button(item.getTitle());

            button.setGraphic(icon);

            button.setContentDisplay(ContentDisplay.TOP);

            button.setPrefWidth(130);
            button.setPrefHeight(100);

            button.setStyle("""
                    -fx-font-size: 11px;
                    -fx-background-radius: 10;
                    -fx-padding: 5;
                    """);

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