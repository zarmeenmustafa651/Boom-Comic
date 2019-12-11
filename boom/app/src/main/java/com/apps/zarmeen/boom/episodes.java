package com.apps.zarmeen.boom;

public class episodes
{
    int id;
    int comicId;
    String EpisodeTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getEpisodeTitle() {
        return EpisodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        EpisodeTitle = episodeTitle;
    }

    episodes(){}
    public episodes(int id, int comicId, String episodeTitle) {
        this.id = id;
        this.comicId = comicId;
        EpisodeTitle = episodeTitle;
    }
}
