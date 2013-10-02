/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application.permission;

/**
 *
 * @author ongzy-001PC
 */
public class PathPermission {
    private String path = null;
    private String pathPrefix = null;
    private String pathPattern = null;
    private String permission = null;
    private String readPermission = null;
    private String writePermission = null;

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
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * @return the readPermission
     */
    public String getReadPermission() {
        return readPermission;
    }

    /**
     * @param readPermission the readPermission to set
     */
    public void setReadPermission(String readPermission) {
        this.readPermission = readPermission;
    }

    /**
     * @return the writePermission
     */
    public String getWritePermission() {
        return writePermission;
    }

    /**
     * @param writePermission the writePermission to set
     */
    public void setWritePermission(String writePermission) {
        this.writePermission = writePermission;
    }
}
