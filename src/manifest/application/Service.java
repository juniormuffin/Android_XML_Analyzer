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
public class Service extends ApplicationComponent{
    private boolean isolatedProcess = false;
    private String process = null;

    private ArrayList<Intent> intents = new ArrayList();
    private ArrayList<MetaData> metaDatas = new ArrayList();
    /**
     * @return the isolatedProcess
     */
    public boolean isIsolatedProcess() {
        return isolatedProcess;
    }

    /**
     * @param isolatedProcess the isolatedProcess to set
     */
    public void setIsolatedProcess(boolean isolatedProcess) {
        this.isolatedProcess = isolatedProcess;
    }

    /**
     * @return the process
     */
    public String getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(String process) {
        this.process = process;
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
