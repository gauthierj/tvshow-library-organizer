package net.jacqg.tvshow.library.organizer.application.impl;

import net.jacqg.tvshow.library.organizer.application.TvShowEpisodeFileFinder;
import net.jacqg.tvshow.library.organizer.model.TvShowEpisode;
import net.jacqg.tvshow.library.organizer.model.TvShowEpisodeFile;
import net.jacqg.tvshow.library.organizer.application.exception.TvShowLibraryIOException;
import net.jacqg.tvshow.library.organizer.application.exception.TvShowNotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@Component
public class TvShowEpisodeFileFinderImpl implements TvShowEpisodeFileFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(TvShowEpisodeFileFinderImpl.class);

    @Autowired
    private TvShowEpisodeFactory tvShowEpisodeFactory;

    @Value("#{'${tvshow.visitor.videoFileExtensions}'.split(';')}")
    private final Set<String> videoFileExtensions = new HashSet<>();

    private final List<TvShowEpisodeFileFinderListener> listeners = new ArrayList<>();

    @Autowired(required = false)
    public void registerListeners(List<TvShowEpisodeFileFinderListener> listeners) {
        if(CollectionUtils.isNotEmpty(listeners)) {
            this.listeners.addAll(listeners);
        }
    }

    @Override
    public Set<TvShowEpisodeFile> findTvShowEpisodeFiles(Path source, Set<String> ignores) throws TvShowLibraryIOException {
        try {
            TvShowFileVisitor visitor = new TvShowFileVisitor(videoFileExtensions, ignores, tvShowEpisodeFactory);
            Files.walkFileTree(Paths.get("/Volumes/Séries TV/"), visitor);
            return visitor.getTvShowEpisodeFiles();
        } catch (IOException e) {
            LOGGER.error("An error occurred while walking file tree", e);
            throw new TvShowLibraryIOException("Could not walk file tree", e);
        }
    }

    private void notifyListeners(TvShowEpisodeFile tvShowEpisodeFile) {
        for (TvShowEpisodeFileFinderListener listener : listeners) {
            listener.TvShowEpisodeFileFound(tvShowEpisodeFile);
        }
    }

    private class TvShowFileVisitor extends SimpleFileVisitor<Path> {

        private final Set<String> ignores = new HashSet<>();
        private final Set<String> videoFileExtensions = new HashSet<>();
        private final TvShowEpisodeFactory tvShowEpisodeFactory;
        private final Set<TvShowEpisodeFile> tvShowEpisodeFiles = new LinkedHashSet<>();

        public TvShowFileVisitor(Set<String> videoFileExtensions, Set<String> ignores, TvShowEpisodeFactory tvShowEpisodeFactory) {
            if(CollectionUtils.isNotEmpty(videoFileExtensions)) {
                this.videoFileExtensions.addAll(videoFileExtensions);
            }
            if(CollectionUtils.isNotEmpty(ignores)) {
                this.ignores.addAll(ignores);
            }
            this.tvShowEpisodeFactory = tvShowEpisodeFactory;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if(ignores.contains(file.toString())) {
                LOGGER.info("Skipping subtree of: {}", file);
                return FileVisitResult.SKIP_SUBTREE;
            }
            if(isVideoFile(file)) {
                try {
                    visitVideoFile(file);
                } catch (TvShowNotFoundException e) {
                    LOGGER.debug("Video file not identified as TV Show: {}", file);
                }
            }
            return FileVisitResult.CONTINUE;
        }

        public Set<TvShowEpisodeFile> getTvShowEpisodeFiles() {
            return Collections.unmodifiableSet(tvShowEpisodeFiles);
        }

        private void visitVideoFile(Path file) {
            if(tvShowEpisodeFactory.isTvShowEpisode(file)) {
                TvShowEpisode tvShowEpisode = tvShowEpisodeFactory.fromFileName(file);
                TvShowEpisodeFile tvShowEpisodeFile = new TvShowEpisodeFile(file, tvShowEpisode);
                tvShowEpisodeFiles.add(tvShowEpisodeFile);
                notifyListeners(tvShowEpisodeFile);
            } else {
                LOGGER.debug("Video file not identified as TV Show: {}", file);
            }
        }

        private boolean isVideoFile(Path file) {
            String extension = FilenameUtils.getExtension(file.toString());
            return StringUtils.isNoneBlank(extension) && videoFileExtensions.contains(extension.toLowerCase());
        }
    }

    public static interface TvShowEpisodeFileFinderListener {

        void TvShowEpisodeFileFound(TvShowEpisodeFile file);
    }
}
