/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

import application.Activity;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import permissions.Permissions;

/**
 *
 * @author Administrator
 */
public class ManifestReader {

    private Manifest manifest;

    public void calculateScore(ArrayList<Permissions> permissionsList) {
        for (String temp : getManifest().getPermissions()) {
            for (Permissions permissionTemp : permissionsList) {
                if (temp.toUpperCase().trim().endsWith(permissionTemp.getName().trim().toUpperCase())) {
                    getManifest().setScore(getManifest().getScore() + permissionTemp.getScore());
                }
            }
        }
    }

    public void readManifest(String filename) {
        try {
            setManifest(new Manifest());
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            if (doc.getDocumentElement().getAttribute("package") != null) {
                manifest.setPackageName(doc.getDocumentElement().getAttribute("package"));
            }

            NodeList nList = doc.getElementsByTagName("uses-sdk");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(0);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    getManifest().setSdkVersion(eElement.getAttribute("android:targetSdkVersion"));
                }
            }

            nList = doc.getElementsByTagName("application");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(0);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    getManifest().setAppLabel(eElement.getAttribute("android:label"));
                }
            }

            nList = doc.getElementsByTagName("uses-permission");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (!getManifest().getPermissions().contains((String) eElement.getAttribute("android:name"))) {
                        getManifest().getPermissions().add(eElement.getAttribute("android:name"));
                    }
                }
            }
            String[] actvitiesLabel = {"activity", "activity-alias", "service", "receiver", "provider"};
            for (int i = 0; i < actvitiesLabel.length; i++) {
                nList = doc.getElementsByTagName(actvitiesLabel[i]);

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        Activity act = new Activity();
                        processActivityAttributes(act, eElement);
                        act.setType(i + 1);
                        act.setStrType(actvitiesLabel[i]);
                        getManifest().getActivities().add(act);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processActivityAttributes(Activity act, Element eElement) {
        if (eElement.getAttribute("android:allowTaskReparenting") != null && eElement.getAttribute("android:allowTaskReparenting").trim().length() > 0) {
            if (eElement.getAttribute("android:allowTaskReparenting").trim().equalsIgnoreCase("true")) {
                act.setAllowTaskReparenting(true);
            } else if (eElement.getAttribute("android:allowTaskReparenting").trim().equalsIgnoreCase("false")) {
                act.setAllowTaskReparenting(false);
            }
        }
        if (eElement.getAttribute("android:alwaysRetainTaskState") != null && eElement.getAttribute("android:alwaysRetainTaskState").trim().length() > 0) {
            if (eElement.getAttribute("android:alwaysRetainTaskState").trim().equalsIgnoreCase("true")) {
                act.setAlwaysRetainTaskState(true);
            } else if (eElement.getAttribute("android:alwaysRetainTaskState").trim().equalsIgnoreCase("false")) {
                act.setAlwaysRetainTaskState(false);
            }
        }
        if (eElement.getAttribute("android:clearTaskOnLaunch") != null && eElement.getAttribute("android:clearTaskOnLaunch").trim().length() > 0) {
            if (eElement.getAttribute("android:clearTaskOnLaunch").trim().equalsIgnoreCase("true")) {
                act.setClearTaskOnLaunch(true);
            } else if (eElement.getAttribute("android:clearTaskOnLaunch").trim().equalsIgnoreCase("false")) {
                act.setClearTaskOnLaunch(false);
            }
        }

        if (eElement.getAttribute("android:configChanges") != null && eElement.getAttribute("android:configChanges").trim().length() > 0) {
            act.setConfigChanges(eElement.getAttribute("android:configChanges").trim());
        }

        if (eElement.getAttribute("android:enabled") != null && eElement.getAttribute("android:enabled").trim().length() > 0) {
            if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("true")) {
                act.setEnabled(true);
            } else if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("false")) {
                act.setEnabled(false);
            }
        }
        if (eElement.getAttribute("android:excludeFromRecents") != null && eElement.getAttribute("android:excludeFromRecents").trim().length() > 0) {
            if (eElement.getAttribute("android:excludeFromRecents").trim().equalsIgnoreCase("true")) {
                act.setExcludeFromRecents(true);
            } else if (eElement.getAttribute("android:excludeFromRecents").trim().equalsIgnoreCase("false")) {
                act.setExcludeFromRecents(false);
            }
        }
        if (eElement.getAttribute("android:exported") != null && eElement.getAttribute("android:exported").trim().length() > 0) {
            if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("true")) {
                act.setExported(true);
            } else if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("false")) {
                act.setExported(false);
            }
        }
        if (eElement.getAttribute("android:finishOnTaskLaunch") != null && eElement.getAttribute("android:finishOnTaskLaunch").trim().length() > 0) {
            if (eElement.getAttribute("android:finishOnTaskLaunch").trim().equalsIgnoreCase("true")) {
                act.setFinishOnTaskLaunch(true);
            } else if (eElement.getAttribute("android:finishOnTaskLaunch").trim().equalsIgnoreCase("false")) {
                act.setFinishOnTaskLaunch(false);
            }
        }
        if (eElement.getAttribute("android:hardwareAccelerated") != null && eElement.getAttribute("android:hardwareAccelerated").trim().length() > 0) {
            if (eElement.getAttribute("android:hardwareAccelerated").trim().equalsIgnoreCase("true")) {
                act.setHardwareAccerlerated(true);
            } else if (eElement.getAttribute("android:hardwareAccelerated").trim().equalsIgnoreCase("false")) {
                act.setHardwareAccerlerated(false);
            }
        }

        if (eElement.getAttribute("android:icon") != null && eElement.getAttribute("android:icon").trim().length() > 0) {
            act.setIcon(eElement.getAttribute("android:icon").trim());
        }

        if (eElement.getAttribute("android:label") != null && eElement.getAttribute("android:label").trim().length() > 0) {
            act.setLabel(eElement.getAttribute("android:label").trim());
        }

        if (eElement.getAttribute("android:launchMode") != null && eElement.getAttribute("android:launchMode").trim().length() > 0) {
            act.setLaunchmode(eElement.getAttribute("android:launchMode").trim());
        }
        
        if (eElement.getAttribute("android:multiprocess") != null && eElement.getAttribute("android:multiprocess").trim().length() > 0) {
            if (eElement.getAttribute("android:multiprocess").trim().equalsIgnoreCase("true")) {
                act.setMultiprocess(true);
            } else if (eElement.getAttribute("android:multiprocess").trim().equalsIgnoreCase("false")) {
                act.setMultiprocess(false);
            }
        }

        if (eElement.getAttribute("android:name") != null && eElement.getAttribute("android:name").trim().length() > 0) {
            act.setName(eElement.getAttribute("android:name").trim());
        }
        
        if (eElement.getAttribute("android:noHistory") != null && eElement.getAttribute("android:noHistory").trim().length() > 0) {
            if (eElement.getAttribute("android:noHistory").trim().equalsIgnoreCase("true")) {
                act.setNoHistory(true);
            } else if (eElement.getAttribute("android:noHistory").trim().equalsIgnoreCase("false")) {
                act.setNoHistory(false);
            }
        }

        if (eElement.getAttribute("android:parentActivityName") != null && eElement.getAttribute("android:parentActivityName").trim().length() > 0) {
            act.setParentActivityName(eElement.getAttribute("android:parentActivityName").trim());
        }

        if (eElement.getAttribute("android:permission") != null && eElement.getAttribute("android:permission").trim().length() > 0) {
            act.setPermission(eElement.getAttribute("android:permission").trim());
        }

        if (eElement.getAttribute("android:process") != null && eElement.getAttribute("android:process").trim().length() > 0) {
            act.setProcess(eElement.getAttribute("android:process").trim());
        }

        if (eElement.getAttribute("android:screenOrientation") != null && eElement.getAttribute("android:screenOrientation").trim().length() > 0) {
            act.setScreenOrientation(eElement.getAttribute("android:screenOrientation").trim());
        }

        if (eElement.getAttribute("android:stateNotNeeded") != null && eElement.getAttribute("android:stateNotNeeded").trim().length() > 0) {
            if (eElement.getAttribute("android:stateNotNeeded").trim().equalsIgnoreCase("true")) {
                act.setStateNotNeeded(true);
            } else if (eElement.getAttribute("android:stateNotNeeded").trim().equalsIgnoreCase("false")) {
                act.setStateNotNeeded(false);
            }
        }

        if (eElement.getAttribute("android:taskAffinity") != null && eElement.getAttribute("android:taskAffinity").trim().length() > 0) {
            act.setTaskAffinity(eElement.getAttribute("android:taskAffinity").trim());
        }

        if (eElement.getAttribute("android:theme") != null && eElement.getAttribute("android:theme").trim().length() > 0) {
            System.out.println(eElement.getAttribute("android:theme"));
            act.setTheme(eElement.getAttribute("android:theme").trim());
        }

        if (eElement.getAttribute("android:uiOptions") != null && eElement.getAttribute("android:uiOptions").trim().length() > 0) {
            act.setUiOptions(eElement.getAttribute("android:uiOptions").trim());
        }

        if (eElement.getAttribute("android:windowSoftInputMode") != null && eElement.getAttribute("android:windowSoftInputMode").trim().length() > 0) {
            act.setWindowSoftInputMode(eElement.getAttribute("android:windowSoftInputMode").trim());
        }
    }

    /**
     * @return the manifest
     */
    public Manifest getManifest() {
        return manifest;
    }

    /**
     * @param manifest the manifest to set
     */
    public void setManifest(Manifest manifest) {
        this.manifest = manifest;
    }
}
