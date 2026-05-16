package com.example.memlibrary;

public class MediaItem {
    private String title;
    private String imagePath;
    private String description;
    private String audioPath;

    public MediaItem(String title, String imagePath, String description, String audioPath){
        this.title = title;
        this.imagePath = imagePath;
        this.description = description;
        this.audioPath = audioPath;
    }

    public String getTitle(){
        return title;
    }

    public String getImagePath(){
        return imagePath;
    }

    public String getDescription(){
        return description;
    }

    public String getAudioPath(){
        return audioPath;
    }
}
