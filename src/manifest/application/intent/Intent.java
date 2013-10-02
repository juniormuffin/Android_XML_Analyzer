/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application.intent;

import java.util.ArrayList;

/**
 *
 * @author ongzy-001PC
 */
public class Intent {
    private String icon = null;
    private String label = null;
    private int priority = 0;
    
    private ArrayList<Action> actions = new ArrayList();
    private ArrayList<Data> data = new ArrayList();
    private ArrayList<Category> categories = new ArrayList();

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

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the actions
     */
    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    /**
     * @return the data
     */
    public ArrayList<Data> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    /**
     * @return the categories
     */
    public ArrayList<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
