/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

/**
 *
 * @author ongzy-001PC
 */
public class CompatibleScreen {

    /**
     * @return the screenSize
     */
    public CompatibleScreen.ScreenSize getScreenSize() {
        return screenSize;
    }

    /**
     * @param screenSize the screenSize to set
     */
    public void setScreenSize(CompatibleScreen.ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    /**
     * @return the screenDensity
     */
    public CompatibleScreen.ScreenDensity getScreenDensity() {
        return screenDensity;
    }

    /**
     * @param screenDensity the screenDensity to set
     */
    public void setScreenDensity(CompatibleScreen.ScreenDensity screenDensity) {
        this.screenDensity = screenDensity;
    }
    
    public enum ScreenSize {
        small,normal,large,xlarge;
    }
    private CompatibleScreen.ScreenSize screenSize;
    
    public enum ScreenDensity {
        ldpi,mdpi,hdpi,xhdpi;
    }
    private CompatibleScreen.ScreenDensity screenDensity;
}
