package com.github.gauthierj.tvshow.library.organizer.application.impl.filematcher;

import org.springframework.cache.annotation.Cacheable;

import java.nio.file.Path;

public interface FileMatcherService {

    @Cacheable("FileMatcher")
    FileMatcher findMatchingFileMatcher(Path fileName);
}
