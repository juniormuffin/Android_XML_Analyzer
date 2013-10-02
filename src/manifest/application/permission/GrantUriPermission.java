/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application.permission;

/**
 *
 * @author ongzy-001PC
 */
public class GrantUriPermission {
    private String path = null;
    private String pathPattern = null;
    private String pathPrefix = null;

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the pathPattern
     */
    public String getPathPattern() {
        return pathPattern;
    }

    /**
     * @param pathPattern the pathPattern to set
     */
    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    /**
     * @return the pathPrefix
     */
    public String getPathPrefix() {
        return pathPrefix;
    }

    /**
     * @param pathPrefix the pathPrefix to set
     */
    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }
}
