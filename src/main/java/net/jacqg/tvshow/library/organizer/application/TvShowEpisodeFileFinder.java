package net.jacqg.tvshow.library.organizer.application;

import net.jacqg.tvshow.library.organizer.model.TvShowEpisodeFile;
import net.jacqg.tvshow.library.organizer.application.exception.TvShowLibraryIOException;

import java.nio.file.Path;
import java.util.Set;

public interface TvShowEpisodeFileFinder {
    Set<TvShowEpisodeFile> findTvShowEpisodeFiles(Path source, Set<String> ignores) throws TvShowLibraryIOException;
}
