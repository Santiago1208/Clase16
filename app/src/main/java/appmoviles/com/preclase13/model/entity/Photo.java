package appmoviles.com.preclase13.model.entity;

import java.io.Serializable;

public class Photo implements Serializable {

    private String id;
    private String name;
    private String description;
    private int views;
    private String albumID;

    public Photo() {
    }

    public Photo(String id, String name, String description, int views, String albumID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.views = views;
        this.albumID = albumID;
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

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }
}
