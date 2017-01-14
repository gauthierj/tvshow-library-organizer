package com.github.gauthierj.tvshow.library.organizer.application.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.tvshow.library.organizer.application.TvShowRepository;
import com.github.gauthierj.tvshow.library.organizer.application.exception.TvShowNotFoundException;
import com.github.gauthierj.tvshow.library.organizer.model.TvShow;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Component
@Order(0)
public class TvMazeTvShowRepositoryImpl implements TvShowRepository, Ordered {

    @Value(("${tvmaze.url:http://api.tvmaze.com}"))
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TvShow findOne(String name) {
        String query = UriComponentsBuilder.fromHttpUrl(url)
                .replacePath("singlesearch/shows")
                .queryParam("q", name)
                .buildAndExpand()
                .toUriString();
        try {
            TvMazeTvShow tvShow = restTemplate.getForObject(query, TvMazeTvShow.class);
            return new TvShow(tvShow.getName());
        } catch (HttpClientErrorException e) {
            throw new TvShowNotFoundException(name, e);
        }
    }

    @Override
    public Collection<TvShow> find(String name) {
        String query = UriComponentsBuilder.fromHttpUrl(url)
                .replacePath("search/shows")
                .queryParam("q", name)
                .buildAndExpand()
                .toUriString();
        TvMazeListItem[] forObject = restTemplate.getForObject(query, TvMazeListItem[].class);
        if(forObject != null) {
            return CollectionUtils.collect(Arrays.asList(forObject), input -> {
                return new TvShow(input.getShow().getName());
            });
        }
        return new ArrayList<>();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static class TvMazeTvShow {

        @JsonProperty
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class TvMazeListItem {

        @JsonProperty
        private TvMazeTvShow show;

        public TvMazeTvShow getShow() {
            return show;
        }
    }
}
