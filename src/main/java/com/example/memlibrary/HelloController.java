package com.example.memlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
                "«Штани за 40 гривень» — український інтернет-мем, що виник у 2014 році та символізує роздратування від пошкодження чи втрати дешевої, але цінної та надійної речі. Фраза часто використовується для вираження комічної фрустрації щодо дрібних невдач у повсякденному житті, підкреслюючи економічную реальність багатьох українців, де недорогий одяг набуває особливої ваги.",
                "/audio/40grn.mp3",
                null
        ));

        items.add(new MediaItem(
                "9 чи 10",
                "/images/9chi10.png",
                "Мем «9 чи 10» став популярним через смішну телефонну розмову.",
                "/audio/9chi10.mp3",
                null
        ));

        items.add(new MediaItem(
                "А я думав сова",
                "/images/sova.png",
                "«А я думав сова» — культовий інтернет-мем із дивного старого відео.",
                "/audio/sova.mp3",
                null
        ));

        items.add(new MediaItem(
                "Вы продоёте рыбов?",
                "/images/ribov.png",
                "Мем із котом та фразою «Вы продоёте рыбов?».",
                "/audio/ribov.mp3",
                null
        ));

        items.add(new MediaItem(
                "Наташ, вставай",
                "/images/natasha.png",
                "Мем із котами, які будять Наташу.",
                "/audio/natasha.mp3",
                null
        ));

        items.add(new MediaItem(
                "Все \"добре\" ",
                "/images/agro.png",
                "Життєва ситуація, коли рівень гніву під час роботи або гри досягає свого максимуму.",
                null,
                "/video/agro.mp4"
        ));

        items.add(new MediaItem(
                "Все зроблено)",
                "/images/gotovo.png",
                "Вигляд програміста, який героїчно подолав складний баг і тепер заслужено йде відпочивати",
                null,
                "/video/gotovo.mp4"
        ));

        items.add(new MediaItem(
                "Вітдай сало!!",
                "/images/otdaySalo.png",
                "Культова та емоційна вимога повернути найцінніше🤣",
                null,
                "/video/otdaySalo.mp4"
        ));

        items.add(new MediaItem(
                "У мене, у мене, у мене... у мене....",
                "/images/uMene.png",
                "Типовий дзвінок бабусі, яка намагається щось сказати боту, і думає, що цу оператор служби підтримки(жива людина)",
                null,
                "/video/uMene.mp4"
        ));

        items.add(new MediaItem(
                "Жиза",
                "/images/40grn.png",
                "Життєва ситуація коли довго не писав код.",
                null,
                "/video/zhiza.mp4"
        ));
    }

    private void createIcons() {
        for (int i = 0; i < items.size(); i++) {
            MediaItem item = items.get(i);
            Image image;
            try {
                image = new Image(getClass().getResourceAsStream(item.getImagePath()));
            } catch (Exception e) {
                continue;
            }

            ImageView icon = new ImageView(image);
            icon.setFitWidth(130);
            icon.setFitHeight(80);
            icon.setPreserveRatio(false);

            Rectangle clip = new Rectangle(130, 80);
            clip.setArcWidth(16);
            clip.setArcHeight(16);
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
            if (node instanceof Button) {
                Button btn = (Button) node;
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
                String videoPath = getClass().getResource(item.getVideoPath()).toExternalForm();
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

            Image image = new Image(getClass().getResourceAsStream(item.getImagePath()));
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
}