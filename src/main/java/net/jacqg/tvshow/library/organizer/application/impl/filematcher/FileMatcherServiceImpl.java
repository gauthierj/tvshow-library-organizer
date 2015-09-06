package net.jacqg.tvshow.library.organizer.application.impl.filematcher;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.util.List;

@Component
public class FileMatcherServiceImpl implements FileMatcherService {

    @Resource
    private List<FileMatcherFactory> fileMatcherFactories;

    @Override
    public FileMatcher findMatchingFileMatcher(Path fileName) {
        for (FileMatcherFactory fileMatcherFactory : fileMatcherFactories) {
            FileMatcher fileMatcher = fileMatcherFactory.createFileMatcher(fileName);
            if(fileMatcher.matches()) {
                return fileMatcher;
            }
        }
        return null;
    }
}
