/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

/**
 *
 * @author ongzy-001PC
 */
public class Instrumentation {
    private boolean functionalTest = false;
    private boolean handleProfiling = false;
    private String icon = null;
    private String label = null;
    private String name = null;
    private String targetPackage = null;

    /**
     * @return the functionalTest
     */
    public boolean isFunctionalTest() {
        return functionalTest;
    }

    /**
     * @param functionalTest the functionalTest to set
     */
    public void setFunctionalTest(boolean functionalTest) {
        this.functionalTest = functionalTest;
    }

    /**
     * @return the handlProfiling
     */
    public boolean isHandleProfiling() {
        return handleProfiling;
    }

    /**
     * @param handlProfiling the handlProfiling to set
     */
    public void setHandleProfiling(boolean handleProfiling) {
        this.handleProfiling = handleProfiling;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

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
     * @return the targetPackage
     */
    public String getTargetPackage() {
        return targetPackage;
    }

    /**
     * @param targetPackage the targetPackage to set
     */
    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
