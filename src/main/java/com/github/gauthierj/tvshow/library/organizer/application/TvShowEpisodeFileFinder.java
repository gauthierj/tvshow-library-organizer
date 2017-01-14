package com.github.gauthierj.tvshow.library.organizer.application;

import com.github.gauthierj.tvshow.library.organizer.model.TvShowEpisodeFile;
import com.github.gauthierj.tvshow.library.organizer.application.exception.TvShowLibraryIOException;

import java.nio.file.Path;
import java.util.Set;

public interface TvShowEpisodeFileFinder {
    Set<TvShowEpisodeFile> findTvShowEpisodeFiles(Path source, Set<String> ignores) throws TvShowLibraryIOException;
}
