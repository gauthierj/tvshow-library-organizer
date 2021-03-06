package com.github.gauthierj.tvshow.library.organizer.application.impl.filematcher;

import java.nio.file.Path;

public interface FileMatcherFactory {

    FileMatcher createFileMatcher(Path fileName);
}
