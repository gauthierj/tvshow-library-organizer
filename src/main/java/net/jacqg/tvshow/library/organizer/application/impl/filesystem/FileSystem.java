package net.jacqg.tvshow.library.organizer.application.impl.filesystem;

import net.jacqg.tvshow.library.organizer.application.exception.TvShowLibraryIOException;

import java.nio.file.Path;

public interface FileSystem {

    void move(Path source, Path destination) throws TvShowLibraryIOException;
}
