package com.github.gauthierj.tvshow.library.organizer;

import com.github.gauthierj.tvshow.library.organizer.application.impl.filematcher.FileMatcherFactory;
import com.github.gauthierj.tvshow.library.organizer.application.impl.filematcher.PatternFileMatcherFactory;
import com.github.gauthierj.tvshow.library.organizer.application.impl.filematcher.PatternAndParentDirectoryFileMatcherFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MainConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public List<FileMatcherFactory> fileMatcherFactories(@Value("#{'${tvshow.patternFileMatcher.patterns}'.split(';')}") List<String> patterns,
                                                         @Value("#{'${tvshow.patternAndDirectoryFileMatcher.patterns}'.split(';')}") List<String> patterns2) {
        ArrayList<FileMatcherFactory> fileMatcherFactories = new ArrayList<>(CollectionUtils.collect(patterns, PatternFileMatcherFactory::new));
        fileMatcherFactories.addAll(CollectionUtils.collect(patterns2, PatternAndParentDirectoryFileMatcherFactory::new));
        return fileMatcherFactories;
    }

    @Bean
    public CacheManager cacheManager() {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(new net.sf.ehcache.CacheManager());
        ehCacheCacheManager.getCacheManager().addCache(new Cache(new CacheConfiguration("TvShow", 5000)));
        ehCacheCacheManager.getCacheManager().addCache(new Cache(new CacheConfiguration("FileMatcher", 10000)));
        return ehCacheCacheManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setFileEncoding("UTF-8");
        return propertySourcesPlaceholderConfigurer;
    }
}
