package msku.ceng.madlab.week6;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private  String name;
    private  String director;
    private  int year;

    private List<String> stars = new ArrayList<>();
    private String description;

    public Movie(String name, String director, int year, List<String> stars, String description) {
        this.name = name;
        this.director = director;
        this.year = year;
        this.stars = stars;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getStars() {
        return stars;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
