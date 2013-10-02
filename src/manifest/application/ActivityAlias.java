/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application;

import java.util.ArrayList;
import manifest.application.intent.Intent;
import manifest.application.metaData.MetaData;

/**
 *
 * @author Administrator
 */
public class ActivityAlias extends ApplicationComponent{
    private String targetActivity = null;

    private ArrayList<Intent> intents = new ArrayList();
    private ArrayList<MetaData> metaDatas = new ArrayList();
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

    /**
     * @return the intents
     */
    public ArrayList<Intent> getIntents() {
        return intents;
    }

    /**
     * @param intents the intents to set
     */
    public void setIntents(ArrayList<Intent> intents) {
        this.intents = intents;
    }

    /**
     * @return the metaDatas
     */
    public ArrayList<MetaData> getMetaDatas() {
        return metaDatas;
    }

    /**
     * @param metaDatas the metaDatas to set
     */
    public void setMetaDatas(ArrayList<MetaData> metaDatas) {
        this.metaDatas = metaDatas;
    }
}
