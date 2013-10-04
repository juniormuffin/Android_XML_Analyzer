/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

/**
 *
 * @author ongzy-001PC
 */
public class Feature {
    private String name = null;
    private boolean required = true;
    private int glEsVersion = 0;

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
     * @return the required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return the glEsVersion
     */
    public int getGlEsVersion() {
        return glEsVersion;
    }

    /**
     * @param glEsVersion the glEsVersion to set
     */
    public void setGlEsVersion(int glEsVersion) {
        this.glEsVersion = glEsVersion;
    }
}
