package vn.edu.tdmu.imuseum.model;

import java.io.Serializable;

/**
 * Created by nvulinh on 8/25/17.
 * @author nvulinh
 */

public class CategoryArtifact implements Serializable {
    private int idCategory;
    private String nameCategory;

    public CategoryArtifact() {
    }

    public CategoryArtifact(int idCategory, String nameCategory) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
