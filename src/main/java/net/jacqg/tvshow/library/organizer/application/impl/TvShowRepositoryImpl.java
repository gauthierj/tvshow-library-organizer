package net.jacqg.tvshow.library.organizer.application.impl;

import net.jacqg.tvshow.library.organizer.application.TvShowRepository;
import net.jacqg.tvshow.library.organizer.application.exception.TvShowNotFoundException;
import net.jacqg.tvshow.library.organizer.model.TvShow;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@Component
public class TvShowRepositoryImpl implements TvShowRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TvShowRepositoryImpl.class);

    @Value("${tvshow.repository.ws.url:http://services.tvrage.com}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TvShow findOne(String name) {
        Collection<TvShow> tvShows = find(name);
        if(tvShows.isEmpty()) {
            throw new TvShowNotFoundException(name);
        }
        return tvShows.iterator().next();
    }

    @Override
    public Collection<TvShow> find(String name) {
        String searchUrl = UriComponentsBuilder.fromHttpUrl(url).path("/feeds/search.php").queryParam("show", name).build().toUriString();
        Results results = restTemplate.getForObject(searchUrl, Results.class);
        if(results != null && CollectionUtils.isNotEmpty(results.getShows())) {
            return CollectionUtils.collect(results.getShows(), input -> {
                return new TvShow(input.getName());
            });
        }
        return new ArrayList<>();
    }

    @XmlRootElement(name = "Results")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Results {

        @XmlElement(name = "show")
        private final List<Show> shows = new ArrayList<>();

        private Results() {
            super();
        }

        public Results(List<Show> shows) {
            this.shows.addAll(shows);
        }

        public List<Show> getShows() {
            return Collections.unmodifiableList(shows);
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Show {

        @XmlElement(name = "showid")
        private long id;
        @XmlElement
        private String name;

        private Show() {
            super();
        }

        public Show(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
