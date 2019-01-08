package vn.edu.tdmu.imuseum.model;

/**
 * Created by nvulinh on 11/24/17.
 *
 */

public class Survey {
    private int artifactSurvey;
    private String comment;
    private int rank;

    public Survey() {
    }

    public Survey(int idSurvey, String comment, int rank) {
        this.artifactSurvey = idSurvey;
        this.comment = comment;
        this.rank = rank;
    }

    public int getIdSurvey() {
        return artifactSurvey;
    }

    public void setIdSurvey(int idSurvey) {
        this.artifactSurvey = idSurvey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
