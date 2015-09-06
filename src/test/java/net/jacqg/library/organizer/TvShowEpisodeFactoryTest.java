package net.jacqg.library.organizer;

import net.jacqg.tvshow.library.organizer.application.impl.filematcher.*;
import net.jacqg.tvshow.library.organizer.model.TvShow;
import net.jacqg.tvshow.library.organizer.application.impl.TvShowEpisodeFactory;
import net.jacqg.tvshow.library.organizer.model.TvShowEpisode;
import net.jacqg.tvshow.library.organizer.application.TvShowRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TvShowEpisodeFactoryTest.Config.class)
@TestPropertySource(locations = "/application.properties")
public class TvShowEpisodeFactoryTest {

    @Autowired
    private TvShowEpisodeFactory tvShowEpisodeFactory;

    @Test
    public void testIsTvShow() throws Exception {
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("[www.Cpasbien.pe] Under.The.Dome.S02E07.FRENCH.HDTV.XviD-RNT.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Castle.2009.S07E18.HDTV.x264-LOL.mp4")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Castle.2009.S07E23.HDTV.x264-2HD.mp4")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Criminal.Minds.S10E20.HDTV.x264-ASAP.mp4")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("CSI.S15E13.The.Greater.Good.HDTV.x264-LOL.mp4")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("New.Girl.S04E21.HDTV.x264-ASAP.mp4")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Law.and.Order.SVU.S16E20.720p.WEB-DL.DD5.1.H.264-NTb.mkv")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("CSI.S15E13.The.Greater.Good.HDTV.x264-LOL.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Esprits Criminels - Saison 7 - 11 Sous le signe du Zodiaque.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Esprits criminels - Saison 2 - Episode 22 - Le nettoyeur.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("tout.le.monde.aime.raymond.s1e20.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Lie to Me Season 3 Episode 06 - Beyond Belief.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("That 70's Show - 3-19 - Eric Se Lâche.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("One Tree Hill 8X08.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Black Books - s1e3 - The Grapes Of Wrath.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Two And A Half Men SE05 E12 A Little Clammy And None Too Fresh.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Greys.Anatomy - S2E04.Faux.semblants.hdtv.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Dexter 6x6.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/Monk/Monk Saison 8/01-Monk et sa série préférée.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/Greys Anatomy/S4/S04E01_Le vent du changement.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/Greys Anatomy/S6/6-01 - L'un.Part….avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/The Big Bang Theory/S3/03x01 - The Electric Can Opener Fluctuation.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/Alf/alf saison 1/1x05.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/Little Britain/S3/302.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/New York - Unité spéciale/NYUS saison 1/S1 - E06 - Crime Sur Le Campus.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/New York - Unité spéciale/new york unite special  s3/LAOS03EP07azer.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/New York - Unité spéciale/new york unite special  s3/LAOS03EP07.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/New York - Unité spéciale/NYUS Saison 2/s2e08.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/New York - Unité spéciale/Law and Order - Special Victims Unit - Saison 4/L&O SVU S4 - Ep. 15 - Traffic d'innocence.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/New York - Unité spéciale/S7/7x10 - Tragédies en séries - ADVRIP.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/New York - Unité spéciale/Law and Order - Special Victims Unit - Saison 11/L&O SVU S11 - Ep. 4 - Meurtre en état d'ivresse.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/esprits criminels/Esprit criminels saison 5/Episode 18 - Intime conviction.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/esprits criminels/stban_espcrim_s6/6x06.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("/Volumes/Séries TV/Gilmore Girls/gilmore girls saison 7/Gilmore.Girls.S7.e10.Joyeuses.Beignes.FRENCH.HDTV.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Dexter 6x6.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Dexter 6x6.avi")));
        assertTrue(tvShowEpisodeFactory.isTvShowEpisode(Paths.get("Dexter 6x6.avi")));
    }

    @Test
    public void testFromFileName() throws Exception {
        testFromFileName("Under The Dome", 2, 7, "[www.Cpasbien.pe] Under.The.Dome.S02E07.FRENCH.HDTV.XviD-RNT.avi");
        testFromFileName("Castle 2009", 7, 18, "Castle.2009.S07E18.HDTV.x264-LOL.mp4");
        testFromFileName("Castle 2009", 7, 23, "Castle.2009.S07E23.HDTV.x264-2HD.mp4");
        testFromFileName("Criminal Minds", 10, 20, "Criminal.Minds.S10E20.HDTV.x264-ASAP.mp4");
        testFromFileName("Csi", 15, 13, "CSI.S15E13.The.Greater.Good.HDTV.x264-LOL.mp4");
        testFromFileName("New Girl", 4, 21, "New.Girl.S04E21.HDTV.x264-ASAP.mp4");
        testFromFileName("Law And Order Svu", 16, 20, "Law.and.Order.SVU.S16E20.720p.WEB-DL.DD5.1.H.264-NTb.mkv");
        testFromFileName("Csi", 15, 13, "CSI.S15E13.The.Greater.Good.HDTV.x264-LOL.avi");
        testFromFileName("Esprits Criminels", 7, 11, "Esprits Criminels - Saison 7 - 11 Sous le signe du Zodiaque.avi");
        testFromFileName("Esprits Criminels", 2, 22, "Esprits criminels - Saison 2 - Episode 22 - Le nettoyeur.avi");
        testFromFileName("Tout Le Monde Aime Raymond", 1, 20, "tout.le.monde.aime.raymond.s1e20.avi");
        testFromFileName("Lie To Me", 3, 6, "Lie to Me Season 3 Episode 06 - Beyond Belief.avi");
        testFromFileName("That 70's Show", 3, 19, "That 70's Show - 3-19 - Eric Se Lâche.avi");
        testFromFileName("One Tree Hill", 8, 8, "One Tree Hill 8X08.avi");
        testFromFileName("Black Books", 1, 3, "Black Books - s1e3 - The Grapes Of Wrath.avi");
        testFromFileName("Two And A Half Men", 5, 12, "Two And A Half Men SE05 E12 A Little Clammy And None Too Fresh.avi");
        testFromFileName("Greys Anatomy", 2, 4, "Greys.Anatomy - S2E04.Faux.semblants.hdtv.avi");
        testFromFileName("Dexter", 6, 6, "Dexter 6x6.avi");
        testFromFileName("Monk", 8, 1, "/Volumes/Séries TV/Monk/Monk Saison 8/01-Monk et sa série préférée.avi");
        testFromFileName("Greys Anatomy", 4, 1, "/Volumes/Séries TV/Greys Anatomy/S4/S04E01_Le vent du changement.avi");
        testFromFileName("Greys Anatomy", 6, 1, "/Volumes/Séries TV/Greys Anatomy/S6/6-01 - L'un.Part….avi");
        testFromFileName("The Big Bang Theory", 3, 1, "/Volumes/Séries TV/The Big Bang Theory/S3/03x01 - The Electric Can Opener Fluctuation.avi");
        testFromFileName("Alf", 1, 5, "/Volumes/Séries TV/Alf/alf saison 1/1x05.avi");
        testFromFileName("Little Britain", 3, 2, "/Volumes/Séries TV/Little Britain/S3/302.avi");
        testFromFileName("New York - Unité Spéciale", 1, 6, "/Volumes/Séries TV/New York - Unité spéciale/NYUS saison 1/S1 - E06 - Crime Sur Le Campus.avi");
        testFromFileName("New York - Unité Spéciale", 3, 7, "/Volumes/Séries TV/New York - Unité spéciale/new york unite special  s3/LAOS03EP07azer.avi");
        testFromFileName("New York - Unité Spéciale", 3, 7, "/Volumes/Séries TV/New York - Unité spéciale/new york unite special  s3/LAOS03EP07.avi");
        testFromFileName("New York - Unité Spéciale", 2, 8, "/Volumes/Séries TV/New York - Unité spéciale/NYUS Saison 2/s2e08.avi");
        testFromFileName("New York - Unité Spéciale", 4, 15, "/Volumes/Séries TV/New York - Unité spéciale/Law and Order - Special Victims Unit - Saison 4/L&O SVU S4 - Ep. 15 - Traffic d'innocence.avi");
        testFromFileName("New York - Unité Spéciale", 7, 10, "/Volumes/Séries TV/New York - Unité spéciale/S7/7x10 - Tragédies en séries - ADVRIP.avi");
        testFromFileName("New York - Unité Spéciale", 11, 14, "/Volumes/Séries TV/New York - Unité spéciale/Law and Order - Special Victims Unit - Saison 11/L&O SVU S11 - Ep. 14 - Meurtre en état d'ivresse.avi");
        testFromFileName("Esprits Criminels", 5, 18, "/Volumes/Séries TV/esprits criminels/Esprit criminels saison 5/Episode 18 - Intime conviction.avi");
        testFromFileName("Esprits Criminels", 6, 6, "/Volumes/Séries TV/esprits criminels/stban_espcrim_s6/6x06.avi");
        testFromFileName("Gilmore Girls", 7, 10, "/Volumes/Séries TV/Gilmore Girls/gilmore girls saison 7/Gilmore.Girls.S7.e10.Joyeuses.Beignes.FRENCH.HDTV.avi");
        testFromFileName("Dexter", 6, 6, "Dexter 6x6.avi");
        testFromFileName("Dexter", 6, 6, "Dexter 6x6.avi");
        testFromFileName("Dexter", 6, 6, "Dexter 6x6.avi");
    }

    private void testFromFileName(String expectedName, int expectedSeason, int expectedEpisode, String fileName) {
        TvShowEpisode tvShowEpisode = tvShowEpisodeFactory.fromFileName(Paths.get(fileName));
        assertEquals(expectedName, tvShowEpisode.getTvShow().getName());
        assertEquals(expectedSeason, tvShowEpisode.getSeason());
        assertEquals(expectedEpisode, tvShowEpisode.getEpisode());
    }

    @Configuration
    public static class Config {

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean
        public TvShowRepository tvShowRepository() {
            TvShowRepository mock = mock(TvShowRepository.class);
            when(mock.findOne(anyString())).thenAnswer(invocationOnMock -> new TvShow((String) invocationOnMock.getArguments()[0]));
            return mock;
        }

        @Bean
        public TvShowEpisodeFactory tvShowFileFactory() {
            return new TvShowEpisodeFactory();
        }

        @Bean
        public FileMatcherService fileMatcherService() {
            return new FileMatcherServiceImpl();
        }

        @Bean
        public List<FileMatcherFactory> fileMatcherFactories(@Value("#{'${thshow.patternFileMatcher.patterns}'.split(';')}") List<String> patterns,
                                                             @Value("#{'${thshow.patternAndDirectoryFileMatcher.patterns}'.split(';')}") List<String> patterns2) {
            ArrayList<FileMatcherFactory> fileMatcherFactories = new ArrayList<>(CollectionUtils.collect(patterns, PatternFileMatcherFactory::new));
            fileMatcherFactories.addAll(CollectionUtils.collect(patterns2, PatternAndParentDirectoryFileMatcherFactory::new));
            return fileMatcherFactories;
        }
    }
}