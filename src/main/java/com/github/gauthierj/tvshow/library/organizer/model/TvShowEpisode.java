package com.github.gauthierj.tvshow.library.organizer.model;

/**
 * Created by gjacques on 23/08/15.
 */
public class TvShowEpisode {

    private final TvShow tvShow;
    private final int season;
    private final int episode;

    public TvShowEpisode(TvShow tvShow, int season, int episode) {
        this.tvShow = tvShow;
        this.season = season;
        this.episode = episode;
    }

    public TvShow getTvShow() {
        return tvShow;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }
}
