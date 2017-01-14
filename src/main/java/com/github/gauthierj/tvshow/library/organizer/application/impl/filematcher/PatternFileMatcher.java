package com.github.gauthierj.tvshow.library.organizer.application.impl.filematcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternFileMatcher implements FileMatcher {

    Logger LOGGER = LoggerFactory.getLogger(PatternFileMatcher.class);

    private final Matcher matcher;

    public PatternFileMatcher(Pattern pattern, Path fileName) {
        matcher = pattern.matcher(fileName.getFileName().toString());
    }

    @Override
    public boolean matches() {
        boolean matches = matcher.matches();
        if(matches) {
            LOGGER.debug("Matches. Pattern: {}, Path: {}", matcher.pattern().pattern(), matcher.group(0));
        }
        return matches;
    }

    protected Matcher matcher() {
        return matcher;
    }

    @Override
    public String getTvShowName() {
        return matcher.group("name");
    }

    @Override
    public int getSeason() {
        return Integer.parseInt(matcher.group("season"));
    }

    @Override
    public int getEpisode() {
        return Integer.parseInt(matcher.group("episode"));
    }
}
