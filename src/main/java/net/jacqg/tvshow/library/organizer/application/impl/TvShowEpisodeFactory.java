package net.jacqg.tvshow.library.organizer.application.impl;

import net.jacqg.tvshow.library.organizer.application.TvShowRepository;
import net.jacqg.tvshow.library.organizer.application.impl.filematcher.FileMatcher;
import net.jacqg.tvshow.library.organizer.application.impl.filematcher.FileMatcherService;
import net.jacqg.tvshow.library.organizer.model.TvShow;
import net.jacqg.tvshow.library.organizer.model.TvShowEpisode;
import net.jacqg.tvshow.library.organizer.application.exception.TvShowNotFoundException;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class TvShowEpisodeFactory {

    @Autowired
    private FileMatcherService fileMatcherService;

    private static final ToCanonicalNameTransformer nameTransformer = new ToCanonicalNameTransformer();

    @Autowired
    private TvShowRepository tvShowRepository;

    public boolean isTvShowEpisode(Path fileName) {
        return fileMatcherService.findMatchingFileMatcher(fileName) != null;
    }

    public TvShowEpisode fromFileName(Path fileName) {
        FileMatcher fileMatcher = fileMatcherService.findMatchingFileMatcher(fileName);
        if(fileMatcher != null) {
            return new TvShowEpisode(getTvShow(fileMatcher.getTvShowName()), fileMatcher.getSeason(), fileMatcher.getEpisode());
        }
        throw new TvShowNotFoundException(fileName.getFileName().toString());
    }

    private TvShow getTvShow(String nameFromFileName) {
        String canonicalName = nameTransformer.transform(nameFromFileName);
        return tvShowRepository.findOne(canonicalName);
    }

    public static class ToCanonicalNameTransformer implements Transformer<String, String> {

        @Override
        public String transform(String original) {
            return WordUtils.capitalizeFully(original.replaceAll("\\.", " ").trim());
        }
    }
}
