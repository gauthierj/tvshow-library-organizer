package com.github.gauthierj.tvshow.library.organizer.application.impl;

import com.github.gauthierj.tvshow.library.organizer.model.TvShow;
import com.github.gauthierj.tvshow.library.organizer.model.TvShowEpisode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TvShowEpisodeToFileNameTransformerTest.Config.class)
@TestPropertySource(locations = "/application.properties")
public class TvShowEpisodeToFileNameTransformerTest {

    @Autowired
    private TvShowEpisodeFileOrganizerImpl.TvShowEpisodeToFileNameTransformer transformer;

    @Test
    public void testTransform() throws Exception {
        TvShow tvShow = new TvShow("The TV Show");
        TvShowEpisode tvShowEpisode = new TvShowEpisode(tvShow, 4, 3);
        Assert.assertEquals("The TV Show/Season 4/The TV Show - s04e03", transformer.transform(tvShowEpisode));
        TvShowEpisode tvShowEpisode2 = new TvShowEpisode(tvShow, 24, 23);
        Assert.assertEquals("The TV Show/Season 24/The TV Show - s24e23", transformer.transform(tvShowEpisode2));
    }

    @Configuration
    public static class Config {

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean
        public TvShowEpisodeFileOrganizerImpl.TvShowEpisodeToFileNameTransformer tvShowEpisodeToFileNameTransformer() {
            return new TvShowEpisodeFileOrganizerImpl.TvShowEpisodeToFileNameTransformer();
        }
    }
}