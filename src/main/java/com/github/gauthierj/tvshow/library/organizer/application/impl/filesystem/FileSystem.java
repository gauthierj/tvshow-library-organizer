package com.github.gauthierj.tvshow.library.organizer.application.impl.filesystem;

import com.github.gauthierj.tvshow.library.organizer.application.exception.TvShowLibraryIOException;

import java.nio.file.Path;

public interface FileSystem {

    void move(Path source, Path destination) throws TvShowLibraryIOException;
}
