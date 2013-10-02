/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application;

/**
 *
 * @author Administrator
 */
public class Service extends ApplicationComponent{
    private boolean isolatedProcess = false;
    private String process = null;

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
}
