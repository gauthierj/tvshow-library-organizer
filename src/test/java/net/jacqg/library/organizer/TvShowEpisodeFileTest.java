package net.jacqg.library.organizer;

import net.jacqg.tvshow.library.organizer.model.TvShowEpisodeFile;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class TvShowEpisodeFileTest {

    @Test
    public void testSubtitlePath() throws Exception {
        TvShowEpisodeFile tvShowEpisodeFile = new TvShowEpisodeFile(Paths.get("/test/azer/video.avi"), null);
        Assert.assertEquals("/test/azer/video.srt", tvShowEpisodeFile.getSubTitleFilePath().toString());
    }
}