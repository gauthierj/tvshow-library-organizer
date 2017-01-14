package com.github.gauthierj.tvshow.library.organizer.application.impl.filesystem;

import com.github.gauthierj.dsm.webapi.client.filestation.copymove.CopyMoveService;
import com.github.gauthierj.tvshow.library.organizer.application.exception.TvShowLibraryIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

// @Component
public class DsmFileSystem implements FileSystem {

    @Autowired
    private CopyMoveService copyMoveService;

    @Override
    public void move(Path source, Path destination) throws TvShowLibraryIOException {

    }
}
