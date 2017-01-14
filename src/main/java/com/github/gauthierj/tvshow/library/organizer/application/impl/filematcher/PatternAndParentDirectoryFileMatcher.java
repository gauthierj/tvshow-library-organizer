package com.github.gauthierj.tvshow.library.organizer.application.impl.filematcher;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternAndParentDirectoryFileMatcher extends PatternFileMatcher {

    private static final Pattern SEASON_DIRECTORY_PATTERN = Pattern.compile("\\D+(?<season>\\d{1,2})");
    private final Path path;
    private final Matcher seasonDirectoryMatcher;

    public PatternAndParentDirectoryFileMatcher(Pattern pattern, Path path) {
        super(pattern, path);
        this.path = path;
        if(path.getParent() != null) {
            seasonDirectoryMatcher = SEASON_DIRECTORY_PATTERN.matcher(path.getParent().getFileName().toString());
        } else {
            seasonDirectoryMatcher = null;
        }

    }

    @Override
    public boolean matches() {
        return path.getParent() != null
                && seasonDirectoryMatcher != null
                && seasonDirectoryMatcher.matches()
                && path.getParent().getParent() != null
                && super.matches();
    }

    @Override
    public String getTvShowName() {
        return path.getParent().getParent().getFileName().toString();
    }

    @Override
    public int getSeason() {
        return Integer.parseInt(seasonDirectoryMatcher.group("season"));
    }

    @Override
    public int getEpisode() {
        return Integer.parseInt(matcher().group("episode"));
    }
}
