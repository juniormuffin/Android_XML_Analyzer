/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application.intent;

/**
 *
 * @author ongzy-001PC
 */
public class Data {
    private String host = null;
    private String mimeType = null;
    private String path = null;
    private String pathPattern = null;
    private String pathPrefix = null;
    private String port = null;
    private String scheme = null;

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

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

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * @param scheme the scheme to set
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
