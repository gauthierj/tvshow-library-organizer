package net.jacqg.tvshow.library.organizer.application.exception;

/**
 * Created by gjacques on 23/08/15.
 */
public class TvShowNotFoundException extends TvShowLibraryOrganizerException {

    private final String name;

    public TvShowNotFoundException(String name) {
        super("Tv show ");
        this.name = name;
    }

    public TvShowNotFoundException(String name, Throwable cause) {
        super("", cause);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
