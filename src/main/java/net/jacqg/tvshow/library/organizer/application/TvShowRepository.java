package net.jacqg.tvshow.library.organizer.application;

import net.jacqg.tvshow.library.organizer.model.TvShow;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;

public interface TvShowRepository {

    @Cacheable("TvShow")
    TvShow findOne(String name);

    Collection<TvShow> find(String name);
}
