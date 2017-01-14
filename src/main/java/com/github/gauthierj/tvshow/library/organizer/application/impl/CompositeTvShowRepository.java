package com.github.gauthierj.tvshow.library.organizer.application.impl;

import com.github.gauthierj.tvshow.library.organizer.application.TvShowRepository;
import com.github.gauthierj.tvshow.library.organizer.application.exception.TvShowNotFoundException;
import com.github.gauthierj.tvshow.library.organizer.model.TvShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Component
@Primary
public class CompositeTvShowRepository implements TvShowRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompositeTvShowRepository.class);

    @Autowired
    private List<TvShowRepository> repositories;

    @PostConstruct
    private void init() {
        // Collections.sort(repositories, AnnotationAwareOrderComparator.INSTANCE);
        LOGGER.info("Repositories : {}", repositories);
    }

    @Override
    public TvShow findOne(String name) {
        for (TvShowRepository repository : repositories) {
            try {
                return repository.findOne(name);
            } catch (TvShowNotFoundException e) {
                LOGGER.debug("TV Show not found by repository {}", repository.getClass().getCanonicalName());
            }
        }
        throw new TvShowNotFoundException(name);
    }

    @Override
    public Collection<TvShow> find(String name) {
        for (TvShowRepository repository : repositories) {
            try {
                return repository.find(name);
            } catch (TvShowNotFoundException e) {
                LOGGER.debug("TV Show not found by repository {}", repository.getClass().getCanonicalName());
            }
        }
        throw new TvShowNotFoundException(name);
    }
}
