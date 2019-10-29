package appmoviles.com.preclase13.model.entity;

import java.io.Serializable;

public class Photo implements Serializable {

    private String id;
    private String name;
    private String description;
    private int views;

    public Photo() {
    }

    public Photo(String id, String name, String description, int views) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.views = views;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return name;
    }
}
