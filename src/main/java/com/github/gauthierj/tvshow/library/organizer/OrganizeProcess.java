package com.github.gauthierj.tvshow.library.organizer;

import com.github.gauthierj.tvshow.library.organizer.application.TvShowEpisodeFileFinder;
import com.github.gauthierj.tvshow.library.organizer.application.TvShowEpisodeFileOrganizer;
import com.github.gauthierj.tvshow.library.organizer.application.impl.TvShowEpisodeFileFinderImpl;
import com.github.gauthierj.tvshow.library.organizer.application.impl.TvShowEpisodeFileOrganizerImpl;
import com.github.gauthierj.tvshow.library.organizer.model.TvShow;
import com.github.gauthierj.tvshow.library.organizer.model.TvShowEpisode;
import com.github.gauthierj.tvshow.library.organizer.model.TvShowEpisodeFile;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.util.*;

@Component
@PropertySource("/organize-process.properties")
public class OrganizeProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizeProcess.class);

    @Autowired
    private TvShowEpisodeFileFinder tvShowEpisodeFileFinder;

    @Autowired
    private TvShowEpisodeFileOrganizer tvShowEpisodeFileOrganizer;

    @Value("${tvshow.process.sourceDirectory}")
    private String sourceDirectory;

    @Value("${tvshow.process.library}")
    private String library;

    @Value("#{'${tvshow.process.ignores}'.split(';')}")
    private Set<String> ignores;

    @Value("${tvshow.process.manualFix}")
    private String manualFix;

    private final Map<String, String> manualFixes = new HashMap<>();

    @PostConstruct
    public void init() {
        if(StringUtils.isNotBlank(manualFix)) {
            for (String fix : manualFix.split(";")){
                String[] split = fix.split("=");
                manualFixes.put(split[0], split[1]);
            }
        }
    }

    public void organize() {
        LOGGER.info("------------------------------------------------------------------------");
        LOGGER.info("Configuration :");
        LOGGER.info("Source directory: {}", sourceDirectory);
        LOGGER.info("Library: {}", library);
        LOGGER.info("Ignores: {}", ignores);
        LOGGER.info("Manual fixes: {}", manualFixes);
        LOGGER.info("------------------------------------------------------------------------");
        LOGGER.info("Searching for TV Show episode files");
        Set<TvShowEpisodeFile> tvShowEpisodeFiles = tvShowEpisodeFileFinder.findTvShowEpisodeFiles(Paths.get(sourceDirectory), ignores);
        LOGGER.info("Found {} TV Show episode files", tvShowEpisodeFiles.size());
        LOGGER.info("------------------------------------------------------------------------");
        LOGGER.info("Fixing wrong TV Show names");

        Collection<TvShowEpisodeFile> finalTvShowEpisodeFiles = CollectionUtils.collect(tvShowEpisodeFiles, input -> {
            if (manualFixes.containsKey(input.getTvShowEpisode().getTvShow().getName())) {
                TvShow tvShow = new TvShow(manualFixes.get(input.getTvShowEpisode().getTvShow().getName()));
                TvShowEpisode tvShowEpisode = new TvShowEpisode(tvShow, input.getTvShowEpisode().getSeason(), input.getTvShowEpisode().getEpisode());
                return new TvShowEpisodeFile(input.getVideoFilePath(), tvShowEpisode);
            }
            return input;
        });
        List<String> tvShowNames = new ArrayList<>(new HashSet<>(CollectionUtils.collect(finalTvShowEpisodeFiles, input -> input.getTvShowEpisode().getTvShow().getName())));
        Collections.sort(tvShowNames);;
        System.out.println(tvShowNames);
        LOGGER.info("Fixed wrong TV Show names");
        LOGGER.info("------------------------------------------------------------------------");
        LOGGER.info("Moving files");
        tvShowEpisodeFileOrganizer.organize(new LinkedHashSet<>(finalTvShowEpisodeFiles), Paths.get(library));
        LOGGER.info("Moved {} TV Show episode files", finalTvShowEpisodeFiles.size());
    }

    @Configuration
    public static class Config {

        @Bean
        public TvShowEpisodeFileFinderImpl.TvShowEpisodeFileFinderListener finderLoggerListener() {
            return new TvShowEpisodeFileFinderImpl.TvShowEpisodeFileFinderListener() {

                private int count = 0;

                @Override
                public void TvShowEpisodeFileFound(TvShowEpisodeFile file) {
                    count++;
                    if(count % 100 == 0) {
                        LOGGER.info("Found {} TV Show episode files", count);
                    }
                }
            };
        }

        public TvShowEpisodeFileOrganizerImpl.TvShowEpisodeFileOrganizerListener organizerLoggerListener() {
            return new TvShowEpisodeFileOrganizerImpl.TvShowEpisodeFileOrganizerListener() {

                private int count = 0;

                @Override
                public void tvShowEpisodeFileMoved(TvShowEpisodeFile file) {
                    count++;
                    if(count % 100 == 0) {
                        LOGGER.info("Organized {} TV Show episode files", count);
                    }
                }
            };
        }
    }
}
