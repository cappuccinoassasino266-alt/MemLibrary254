package com.example.memlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private Button playButton;

    @FXML
    private ImageView mainImageView;

    @FXML
    private MediaView mainMediaView;

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
            highlightActiveButton(0);
        }
    }

    private void createItems() {

        items.add(new MediaItem(
                "Штани за 40 гривень",
                "/images/40grn.png",
                "«Штани за 40 гривень» — культовий український мем про дешеві, але дуже цінні речі.",
                "/audio/40grn.mp3",
                null
        ));

        items.add(new MediaItem(
                "9 чи 10",
                "/images/9chi10.png",
                "Смішна телефонна розмова, яка стала легендою українського інтернету.",
                "/audio/9chi10.mp3",
                null
        ));

        items.add(new MediaItem(
                "А я думав сова",
                "/images/sova.png",
                "Відомий мем із дивного старого відео, який став культовим.",
                "/audio/sova.mp3",
                null
        ));

        items.add(new MediaItem(
                "Вы продоёте рыбов?",
                "/images/ribov.png",
                "Мем із котом та легендарною фразою про рибов.",
                "/audio/ribov.mp3",
                null
        ));

        items.add(new MediaItem(
                "Наташ, вставай",
                "/images/natasha.png",
                "Меми з котами давно стали класикою інтернету.",
                "/audio/natasha.mp3",
                null
        ));

        items.add(new MediaItem(
                "Все \"добре\"",
                "/images/agro.png",
                "Життєва ситуація, коли рівень гніву досягає максимуму.",
                null,
                "/video/agro.mp4"
        ));

        items.add(new MediaItem(
                "Все зроблено)",
                "/images/gotovo.png",
                "Вигляд програміста після перемоги над багами.",
                null,
                "/video/gotovo.mp4"
        ));

        items.add(new MediaItem(
                "Віддай сало!!",
                "/images/otdaySalo.png",
                "Культова емоційна вимога повернути найцінніше.",
                null,
                "/video/otdaySalo.mp4"
        ));

        items.add(new MediaItem(
                "У мене... у мене...",
                "/images/uMene.png",
                "Типова розмова зі службою підтримки.",
                null,
                "/video/uMene.mp4"
        ));

        items.add(new MediaItem(
                "Жиза",
                "/images/40grn.png",
                "Стан після кількох годин програмування.",
                null,
                "/video/zhiza.mp4"
        ));
    }

    private void createIcons() {

        for (int i = 0; i < items.size(); i++) {

            MediaItem item = items.get(i);

            Image image = new Image(
                    getClass().getResourceAsStream(item.getImagePath())
            );

            ImageView icon = new ImageView(image);

            icon.setFitWidth(130);
            icon.setFitHeight(80);
            icon.setPreserveRatio(false);

            Rectangle clip = new Rectangle(130, 80);
            clip.setArcWidth(18);
            clip.setArcHeight(18);

            icon.setClip(clip);

            Button button = new Button(item.getTitle());

            button.setGraphic(icon);
            button.setContentDisplay(ContentDisplay.TOP);

            button.setPrefWidth(180);
            button.setPrefHeight(140);

            button.getStyleClass().add("meme-button");

            final int index = i;

            button.setOnAction(event -> {

                showItem(item);
                highlightActiveButton(index);
            });

            iconContainer.getChildren().add(button);
        }
    }

    private void highlightActiveButton(int activeIndex) {

        for (int i = 0; i < iconContainer.getChildren().size(); i++) {

            Node node = iconContainer.getChildren().get(i);

            if (node instanceof Button btn) {

                btn.getStyleClass().remove("active-meme");

                if (i == activeIndex) {
                    btn.getStyleClass().add("active-meme");
                }
            }
        }
    }

    private void showItem(MediaItem item) {

        currentItem = item;

        descriptionArea.setText(item.getDescription());

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }

        if (item.isVideo()) {

            mainImageView.setVisible(false);
            mainMediaView.setVisible(true);

            playButton.setText("▶ Програти відео");

            try {

                String videoPath = getClass()
                        .getResource(item.getVideoPath())
                        .toExternalForm();

                Media media = new Media(videoPath);

                mediaPlayer = new MediaPlayer(media);

                mainMediaView.setMediaPlayer(mediaPlayer);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            mainMediaView.setVisible(false);
            mainImageView.setVisible(true);

            playButton.setText("▶ Програти аудіо");

            Image image = new Image(
                    getClass().getResourceAsStream(item.getImagePath())
            );

            mainImageView.setImage(image);
        }
    }

    public void playAudio(ActionEvent event) {

        try {

            if (currentItem == null) {
                return;
            }

            if (currentItem.isVideo()) {

                if (mediaPlayer != null) {

                    mediaPlayer.stop();
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    mediaPlayer.play();
                }

            } else {

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }

                String audioPath = getClass()
                        .getResource(currentItem.getAudioPath())
                        .toExternalForm();

                Media media = new Media(audioPath);

                mediaPlayer = new MediaPlayer(media);

                mediaPlayer.play();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setDarkTheme(ActionEvent event) {

        Scene scene = ((Node) event.getSource()).getScene();

        scene.getRoot().setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#141820,#1b2230 50%,#10141b);"
        );
    }

    @FXML
    private void setWhiteTheme(ActionEvent event) {

        Scene scene = ((Node) event.getSource()).getScene();

        scene.getRoot().setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#ffffff,#dde8f7,#c7d8ef);"
        );
    }

    @FXML
    private void setGradientTheme(ActionEvent event) {

        Scene scene = ((Node) event.getSource()).getScene();

        scene.getRoot().setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#4facfe,#00f2fe,#6a11cb);"
        );
    }

    @FXML
    private void setGlassTheme(ActionEvent event) {

        Scene scene = ((Node) event.getSource()).getScene();

        scene.getRoot().setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#2b5876,#4e4376,#1f1c2c);"
        );
    }
}