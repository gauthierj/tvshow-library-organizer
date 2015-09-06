package net.jacqg.tvshow.library.organizer.model;

/**
 * Created by gjacques on 23/08/15.
 */
public class TvShow {

    private final String name;

    public TvShow(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TvShow{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvShow tvShow = (TvShow) o;

        return !(name != null ? !name.equals(tvShow.name) : tvShow.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
