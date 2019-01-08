package vn.edu.tdmu.imuseum.model;

import java.util.Arrays;

/**
 * Created by nvulinh on 11/20/17.
 *
 */

public class ArtifactSave {
    private int id;
    private String title;
    private String name;
    private String description;
    private String image;

    public ArtifactSave() {
    }

    public ArtifactSave(int id, String title, String name, String description, String image) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + "\nTirle: " + getTitle() + "\nName: " + getName() + "\nDescription: " + getDescription() + "\nImage: " + getImage();
    }
}
