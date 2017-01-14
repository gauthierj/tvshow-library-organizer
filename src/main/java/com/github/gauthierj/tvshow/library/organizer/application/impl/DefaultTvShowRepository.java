package com.github.gauthierj.tvshow.library.organizer.application.impl;

import com.github.gauthierj.tvshow.library.organizer.application.TvShowRepository;
import com.github.gauthierj.tvshow.library.organizer.model.TvShow;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultTvShowRepository implements TvShowRepository, Ordered {

    @Override
    public TvShow findOne(String name) {
        return new TvShow(name);
    }

    @Override
    public Collection<TvShow> find(String name) {
        return Collections.singleton(findOne(name));
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
