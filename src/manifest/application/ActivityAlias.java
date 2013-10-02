/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application;

/**
 *
 * @author Administrator
 */
public class ActivityAlias extends ApplicationComponent{
    private String targetActivity = null;

    /**
     * @return the targetActivity
     */
    public String getTargetActivity() {
        return targetActivity;
    }

    /**
     * @param targetActivity the targetActivity to set
     */
    public void setTargetActivity(String targetActivity) {
        this.targetActivity = targetActivity;
    }
}
