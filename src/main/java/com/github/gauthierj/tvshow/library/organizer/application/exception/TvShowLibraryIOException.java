package com.github.gauthierj.tvshow.library.organizer.application.exception;

import java.io.IOException;

public class TvShowLibraryIOException extends TvShowLibraryOrganizerException {

    public TvShowLibraryIOException(String message, IOException cause) {
        super(message, cause);
    }
}
