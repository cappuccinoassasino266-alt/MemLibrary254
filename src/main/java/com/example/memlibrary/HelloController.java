package com.example.memlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    Button iconButton1, playButton;
    @FXML
    ImageView mainImageView;
    @FXML
    TextArea descriptionArea;
    @FXML
    VBox iconContainer;

    MediaPlayer mediaPlayer;

    private final List<MediaItem> items = new ArrayList<>();

    private void createItems() {
        items.add(new MediaItem("Штани за 40 гривень",
                "/images/40grn.png",
                "Штани за 40 гривень — український інтернет-мем, що виник у 2014 році та символізує роздратування від пошкодження чи втрати дешевої, але цінної та надійної речі. Фраза часто використовується для вираження комічної фрустрації щодо дрібних невдач у повсякденному житті, підкреслюючи економічну реальність багатьох українців, де недорогі покупки з секонд-хендів чи ринків набувають особливої ваги",
                "/audio/40grn.mp3"));
        items.add(new MediaItem("9 чи 10",
                "/images/9chi10.png",
                "Мем «9 чи 10, не чує баба» — це популярний український інтернет-мем, що базується на гумористичному аудіозаписі телефонної розмови між диспетчером поліції та літньою жінкою, яка недочуває.",
                "/audio/9chi10.mp3"));
    }



    public void iconButton1Clicked(ActionEvent event) {

    }

    public void playAudio(ActionEvent event) {

        try {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            String audio = getClass().getResource("/audio/40grn.mp3").toExternalForm();
            Media media = new Media(audio);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
