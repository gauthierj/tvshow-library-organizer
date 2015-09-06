package net.jacqg.tvshow.library.organizer.application;

import net.jacqg.tvshow.library.organizer.application.exception.TvShowLibraryIOException;
import net.jacqg.tvshow.library.organizer.model.TvShowEpisodeFile;

import java.nio.file.Path;
import java.util.Set;

public interface TvShowEpisodeFileOrganizer {
    void organize(Set<TvShowEpisodeFile> files, Path library) throws TvShowLibraryIOException;
}
