package com.apps.zarmeen.boom;

import java.sql.Blob;

public class slides
{
    int id;
    int EpisodeId;
    int ComicId;
    String slide;

    slides(){}
    public slides(int id, int episodeId, int comicId, String slide) {
        this.id = id;
        EpisodeId = episodeId;
        ComicId = comicId;
        this.slide = slide;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEpisodeId() {
        return EpisodeId;
    }

    public void setEpisodeId(int episodeId) {
        EpisodeId = episodeId;
    }

    public int getComicId() {
        return ComicId;
    }

    public void setComicId(int comicId) {
        ComicId = comicId;
    }

    public String getSlide() {
        return slide;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }


}
