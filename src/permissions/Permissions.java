/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package permissions;

/**
 *
 * @author Administrator
 */
public class Permissions {
    private String name;
    private String description;
    private int score;
    private String remarks;

    public Permissions(String name, String description, int score, String remarks){
        this.name = name;
        this.description = description;
        this.score = score;
        this.remarks = remarks;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
