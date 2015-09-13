package net.jacqg.tvshow.library.organizer.application.impl;

import net.jacqg.tvshow.library.organizer.application.TvShowEpisodeFileOrganizer;
import net.jacqg.tvshow.library.organizer.application.exception.TvShowLibraryIOException;
import net.jacqg.tvshow.library.organizer.application.impl.filesystem.FileSystem;
import net.jacqg.tvshow.library.organizer.model.TvShowEpisode;
import net.jacqg.tvshow.library.organizer.model.TvShowEpisodeFile;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TvShowEpisodeFileOrganizerImpl implements TvShowEpisodeFileOrganizer {

    @Autowired
    private TvShowEpisodeToFileNameTransformer transformer;

    @Autowired
    private FileSystem fileSystem;

    private final List<TvShowEpisodeFileOrganizerListener> listeners = new ArrayList<>();

    @Autowired(required = false)
    public void registerListeners(List<TvShowEpisodeFileOrganizerListener> listeners) {
        if(CollectionUtils.isNotEmpty(listeners)) {
            this.listeners.addAll(listeners);
        }
    }

    @Override
    public void organize(Set<TvShowEpisodeFile> files, Path library) throws TvShowLibraryIOException {
        for (TvShowEpisodeFile file : files) {
            String fileName = transformer.transform(file.getTvShowEpisode());
            moveToCanonicalLocation(library, fileName, file.getVideoFilePath());
            if(file.hasSubtitle()) {
                moveToCanonicalLocation(library, fileName, file.getSubTitleFilePath());
            }
            notifyListeners(file);
        }
    }

    private void moveToCanonicalLocation(Path library, String fileName, Path videoFilePath) throws TvShowLibraryIOException {
        Path destination = Paths.get(library.toString(), fileName + "." + FilenameUtils.getExtension(videoFilePath.toString()));
        fileSystem.move(videoFilePath, destination);
    }

    private void notifyListeners(TvShowEpisodeFile file) {
        for (TvShowEpisodeFileOrganizerListener listener : listeners) {
            listener.tvShowEpisodeFileMoved(file);
        }
    }

    @Component
    public static class TvShowEpisodeToFileNameTransformer implements Transformer<TvShowEpisode, String> {

        @Value("${tvshow.file.layout}")
        private String layout;

        @Override
        public String transform(TvShowEpisode tvShowEpisode) {
            return layout.replaceAll("\\{tvShow\\}", tvShowEpisode.getTvShow().getName().replace("&", "and").replace(": ", " - ").replace("'", ""))
                    .replaceAll("\\{season\\}", String.valueOf(tvShowEpisode.getSeason()))
                    .replaceAll("\\{season:2\\}", String.format("%02d", tvShowEpisode.getSeason()))
                    .replaceAll("\\{episode:2\\}", String.format("%02d", tvShowEpisode.getEpisode()));
        }
    }

    public static interface  TvShowEpisodeFileOrganizerListener {

        void tvShowEpisodeFileMoved(TvShowEpisodeFile file);
    }
}
