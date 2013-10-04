/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

/**
 *
 * @author ongzy-001PC
 */
public class Configuration {
    private boolean reqFiveWayNav = false;
    private boolean reqHardKeyboard = false;

    /**
     * @return the reqFiveWayNav
     */
    public boolean isReqFiveWayNav() {
        return reqFiveWayNav;
    }

    /**
     * @param reqFiveWayNav the reqFiveWayNav to set
     */
    public void setReqFiveWayNav(boolean reqFiveWayNav) {
        this.reqFiveWayNav = reqFiveWayNav;
    }

    /**
     * @return the reqHardKeyboard
     */
    public boolean isReqHardKeyboard() {
        return reqHardKeyboard;
    }

    /**
     * @param reqHardKeyboard the reqHardKeyboard to set
     */
    public void setReqHardKeyboard(boolean reqHardKeyboard) {
        this.reqHardKeyboard = reqHardKeyboard;
    }

    /**
     * @return the reqKeyboardType
     */
    public Configuration.ReqKeyboardType getReqKeyboardType() {
        return reqKeyboardType;
    }

    /**
     * @param reqKeyboardType the reqKeyboardType to set
     */
    public void setReqKeyboardType(Configuration.ReqKeyboardType reqKeyboardType) {
        this.reqKeyboardType = reqKeyboardType;
    }

    /**
     * @return the reqNavigation
     */
    public Configuration.ReqNavigation getReqNavigation() {
        return reqNavigation;
    }

    /**
     * @param reqNavigation the reqNavigation to set
     */
    public void setReqNavigation(Configuration.ReqNavigation reqNavigation) {
        this.reqNavigation = reqNavigation;
    }

    /**
     * @return the reqTouchScreen
     */
    public Configuration.ReqTouchScreen getReqTouchScreen() {
        return reqTouchScreen;
    }

    /**
     * @param reqTouchScreen the reqTouchScreen to set
     */
    public void setReqTouchScreen(Configuration.ReqTouchScreen reqTouchScreen) {
        this.reqTouchScreen = reqTouchScreen;
    }
    
    public enum ReqKeyboardType {
        undefined,nokeys,qwerty,twelvekey;
    }
    private Configuration.ReqKeyboardType reqKeyboardType;
    
    public enum ReqNavigation {
        undefined,nonav,dpad,trackball,wheel;
    }
    private Configuration.ReqNavigation reqNavigation;
    
    public enum ReqTouchScreen {
        undefined,nonav,dpad,trackball,wheel;
    }
    private Configuration.ReqTouchScreen reqTouchScreen;
}
