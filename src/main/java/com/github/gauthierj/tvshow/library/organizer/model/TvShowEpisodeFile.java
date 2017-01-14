package com.github.gauthierj.tvshow.library.organizer.model;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TvShowEpisodeFile {

    private final TvShowEpisode tvShowEpisode;
    private final Path videoFilePath;
    private final Path subTitleFilePath;

    public TvShowEpisodeFile(Path videoFilePath, TvShowEpisode tvShowEpisode) {
        this.tvShowEpisode = tvShowEpisode;
        this.videoFilePath = videoFilePath;
        this.subTitleFilePath = Paths.get(videoFilePath.getParent().toString(), FilenameUtils.getBaseName(videoFilePath.toString()) + ".srt");
    }

    public TvShowEpisode getTvShowEpisode() {
        return tvShowEpisode;
    }

    public Path getVideoFilePath() {
        return videoFilePath;
    }

    public boolean hasSubtitle() {
        return Files.exists(subTitleFilePath);
    }

    public Path getSubTitleFilePath() {
        return subTitleFilePath;
    }
}
