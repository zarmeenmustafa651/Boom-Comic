package com.apps.zarmeen.boom;

public class bookmark
{
    int id;
    int UserId;
    int ComicId;
    int EpisodeId;

    public bookmark(){}

    public bookmark(int id, int userId, int comicId, int episodeId) {
        this.id = id;
        UserId = userId;
        ComicId = comicId;
        EpisodeId = episodeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getComicId() {
        return ComicId;
    }

    public void setComicId(int comicId) {
        ComicId = comicId;
    }

    public int getEpisodeId() {
        return EpisodeId;
    }

    public void setEpisodeId(int episodeId) {
        EpisodeId = episodeId;
    }
}
