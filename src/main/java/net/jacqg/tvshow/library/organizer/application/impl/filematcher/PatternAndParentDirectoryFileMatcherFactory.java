package net.jacqg.tvshow.library.organizer.application.impl.filematcher;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class PatternAndParentDirectoryFileMatcherFactory implements FileMatcherFactory {

    private final Pattern pattern;

    public PatternAndParentDirectoryFileMatcherFactory(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public FileMatcher createFileMatcher(Path fileName) {
        return new PatternAndParentDirectoryFileMatcher(pattern, fileName);
    }
}
