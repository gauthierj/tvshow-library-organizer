package net.jacqg.tvshow.library.organizer.application.impl.filematcher;

public interface FileMatcher {

    public boolean matches();

    public String getTvShowName();

    public int getSeason();

    public int getEpisode();
}
