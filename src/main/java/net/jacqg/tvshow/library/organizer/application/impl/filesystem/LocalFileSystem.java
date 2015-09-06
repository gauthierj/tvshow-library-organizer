package net.jacqg.tvshow.library.organizer.application.impl.filesystem;

import net.jacqg.tvshow.library.organizer.application.exception.TvShowLibraryIOException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class LocalFileSystem implements FileSystem {

    @Override
    public void move(Path source, Path destination) throws TvShowLibraryIOException {
        try {
            FileUtils.moveFile(source.toFile(), destination.toFile());
        } catch (IOException e) {
            throw new TvShowLibraryIOException(String.format("Could not move %s to %s", source, destination), e);
        }
    }
}
