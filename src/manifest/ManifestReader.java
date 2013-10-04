/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

import manifest.application.ActivityAlias;
import manifest.application.Application;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import manifest.application.Activity;
import manifest.application.Library;
import manifest.application.Provider;
import manifest.application.Receiver;
import manifest.application.Service;
import manifest.application.intent.Action;
import manifest.application.intent.Category;
import manifest.application.intent.Data;
import manifest.application.intent.Intent;
import manifest.application.metaData.MetaData;
import manifest.application.permission.GrantUriPermission;
import manifest.application.permission.PathPermission;
import permissions.Permissions;

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

            NodeList nList = doc.getElementsByTagName("uses-permission");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (!getManifest().getPermissions().contains((String) eElement.getAttribute("android:name"))) {
                        getManifest().getPermissions().add(eElement.getAttribute("android:name"));
                    }
                }
            }
            /*
             <uses-permission />    
             <permission />     
             * <permission-tree />    
             * <permission-group />    
             * <instrumentation />    
             * <uses-configuration />       
             * <uses-feature />     
             * <supports-screens />      
             * <compatible-screens />      
             * <supports-gl-texture />   
             */
            readManifest(doc, getManifest());
            readSDK(doc, getManifest());
            readApplication(doc, getManifest());

            readActivity(doc, getManifest());

            readActivityAlias(doc, getManifest());
            readService(doc, getManifest());
            readReceiver(doc, getManifest());
            readProvider(doc, getManifest());
            readLibrary(doc, getManifest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readLibrary(Document doc, Manifest manifest) {
        NodeList nList = doc.getElementsByTagName("uses-library");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                manifest.getApplication().getLibraries().add(processLibraryAttributes((Element) nNode));
            }
        }
    }

    private Library processLibraryAttributes(Element eElement) {
        Library library = new Library();

        if (eElement.getAttribute("android:name") != null && eElement.getAttribute("android:name").trim().length() > 0) {
            library.setName(eElement.getAttribute("android:name").trim());
        }

        if (eElement.getAttribute("android:required") != null && eElement.getAttribute("android:required").trim().length() > 0) {
            if (eElement.getAttribute("android:required").trim().equalsIgnoreCase("true")) {
                library.setRequired(true);
            } else if (eElement.getAttribute("android:required").trim().equalsIgnoreCase("false")) {
                library.setRequired(false);
            }
        }
        return library;
    }

    private void readProvider(Document doc, Manifest manifest) {
        NodeList nList = doc.getElementsByTagName("provider");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Provider provider = processProviderAttributes((Element) nNode);

                NodeList childList = nNode.getChildNodes();
                for (int k = 0; k < childList.getLength(); k++) {
                    Node nNodeChild = childList.item(k);

                    if (nNodeChild.getNodeType() == Node.ELEMENT_NODE) {
                        if (nNodeChild.getNodeName().trim().equalsIgnoreCase("meta-data")) {
                            MetaData metaData = new MetaData();
                            Element eElement = (Element) nNodeChild;

                            if (eElement.getAttribute("android:name") != null
                                    && (eElement.getAttribute("android:name")).length() > 0) {
                                metaData.setName(eElement.getAttribute("android:name"));
                            }
                            if (eElement.getAttribute("android:resource") != null
                                    && (eElement.getAttribute("android:resource")).length() > 0) {
                                metaData.setResource(eElement.getAttribute("android:resource"));
                            }
                            if (eElement.getAttribute("android:value") != null
                                    && (eElement.getAttribute("android:value")).length() > 0) {
                                metaData.setValue(eElement.getAttribute("android:value"));
                            }
                            provider.getMetaDatas().add(metaData);
                        }

                        if (nNodeChild.getNodeName().trim().equalsIgnoreCase("grant-uri-permission")) {
                            GrantUriPermission grantUriPermission = new GrantUriPermission();
                            Element eElement = (Element) nNodeChild;

                            if (eElement.getAttribute("android:path") != null
                                    && (eElement.getAttribute("android:path")).length() > 0) {
                                grantUriPermission.setPath(eElement.getAttribute("android:path"));
                            }
                            if (eElement.getAttribute("android:pathPattern") != null
                                    && (eElement.getAttribute("android:pathPattern")).length() > 0) {
                                grantUriPermission.setPathPattern(eElement.getAttribute("android:pathPattern"));
                            }
                            if (eElement.getAttribute("android:pathPrefix") != null
                                    && (eElement.getAttribute("android:pathPrefix")).length() > 0) {
                                grantUriPermission.setPathPrefix(eElement.getAttribute("android:pathPrefix"));
                            }
                            provider.getGrantUriPermissions().add(grantUriPermission);
                        }

                        if (nNodeChild.getNodeName().trim().equalsIgnoreCase("path-permission")) {
                            PathPermission pathPermission = new PathPermission();
                            Element eElement = (Element) nNodeChild;

                            if (eElement.getAttribute("android:path") != null
                                    && (eElement.getAttribute("android:path")).length() > 0) {
                                pathPermission.setPath(eElement.getAttribute("android:path"));
                            }
                            if (eElement.getAttribute("android:pathPrefix") != null
                                    && (eElement.getAttribute("android:pathPrefix")).length() > 0) {
                                pathPermission.setPathPrefix(eElement.getAttribute("android:pathPrefix"));
                            }
                            if (eElement.getAttribute("android:pathPattern") != null
                                    && (eElement.getAttribute("android:pathPattern")).length() > 0) {
                                pathPermission.setPathPattern(eElement.getAttribute("android:pathPattern"));
                            }

                            if (eElement.getAttribute("android:permission") != null
                                    && (eElement.getAttribute("android:permission")).length() > 0) {
                                pathPermission.setPermission(eElement.getAttribute("android:permission"));
                            }
                            if (eElement.getAttribute("android:readPermission") != null
                                    && (eElement.getAttribute("android:readPermission")).length() > 0) {
                                pathPermission.setReadPermission(eElement.getAttribute("android:readPermission"));
                            }
                            if (eElement.getAttribute("android:writePermission") != null
                                    && (eElement.getAttribute("android:writePermission")).length() > 0) {
                                pathPermission.setWritePermission(eElement.getAttribute("android:writePermission"));
                            }
                            provider.getPathPermissions().add(pathPermission);
                        }
                    }
                }
                manifest.getApplication().getApplicationComponents().add(provider);
            }
        }
    }
    /*
     private void readProvider(Document doc, Manifest manifest) {
     NodeList nList = doc.getElementsByTagName("provider");

     for (int temp = 0; temp < nList.getLength(); temp++) {
     Node nNode = nList.item(temp);
     if (nNode.getNodeType() == Node.ELEMENT_NODE) {
     manifest.getApplication().getApplicationComponents().add(processReceiverAttributes((Element) nNode));
     }
     }
     }*/

    private Provider processProviderAttributes(Element eElement) {
        Provider provider = new Provider();
        if (eElement.getAttribute("android:authorities") != null && eElement.getAttribute("android:authorities").trim().length() > 0) {
            provider.setAuthorities(eElement.getAttribute("android:authorities").trim());
        }

        if (eElement.getAttribute("android:enabled") != null && eElement.getAttribute("android:enabled").trim().length() > 0) {
            if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("true")) {
                provider.setEnabled(true);
            } else if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("false")) {
                provider.setEnabled(false);
            }
        }
        if (eElement.getAttribute("android:exported") != null && eElement.getAttribute("android:exported").trim().length() > 0) {
            if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("true")) {
                provider.setExported(true);
            } else if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("false")) {
                provider.setExported(false);
            }
        }
        if (eElement.getAttribute("android:grantUriPermissions") != null && eElement.getAttribute("android:grantUriPermissions").trim().length() > 0) {
            if (eElement.getAttribute("android:grantUriPermissions").trim().equalsIgnoreCase("true")) {
                provider.setGrantUriPermission(true);
            } else if (eElement.getAttribute("android:grantUriPermissions").trim().equalsIgnoreCase("false")) {
                provider.setGrantUriPermission(false);
            }
        }
        if (eElement.getAttribute("android:icon") != null && eElement.getAttribute("android:icon").trim().length() > 0) {
            provider.setIcon(eElement.getAttribute("android:icon").trim());
        }

        if (eElement.getAttribute("android:initOrder") != null && eElement.getAttribute("android:initOrder").trim().length() > 0) {
            provider.setInitOrder(Integer.parseInt(eElement.getAttribute("android:initOrder").trim()));
        }
        if (eElement.getAttribute("android:label") != null && eElement.getAttribute("android:label").trim().length() > 0) {
            provider.setLabel(eElement.getAttribute("android:label").trim());
        }

        if (eElement.getAttribute("android:multiprocess") != null && eElement.getAttribute("android:multiprocess").trim().length() > 0) {
            if (eElement.getAttribute("android:multiprocess").trim().equalsIgnoreCase("true")) {
                provider.setMultiprocess(true);
            } else if (eElement.getAttribute("android:multiprocess").trim().equalsIgnoreCase("false")) {
                provider.setMultiprocess(false);
            }
        }

        if (eElement.getAttribute("android:name") != null && eElement.getAttribute("android:name").trim().length() > 0) {
            provider.setName(eElement.getAttribute("android:name").trim());
        }
        if (eElement.getAttribute("android:permission") != null && eElement.getAttribute("android:permission").trim().length() > 0) {
            provider.setPermission(eElement.getAttribute("android:permission").trim());
        }
        if (eElement.getAttribute("android:process") != null && eElement.getAttribute("android:process").trim().length() > 0) {
            provider.setProcess(eElement.getAttribute("android:process").trim());
        }
        if (eElement.getAttribute("android:readPermission") != null && eElement.getAttribute("android:readPermission").trim().length() > 0) {
            provider.setReadPermission(eElement.getAttribute("android:readPermission").trim());
        }
        if (eElement.getAttribute("android:syncable") != null && eElement.getAttribute("android:syncable").trim().length() > 0) {
            if (eElement.getAttribute("android:syncable").trim().equalsIgnoreCase("true")) {
                provider.setSyncable(true);
            } else if (eElement.getAttribute("android:syncable").trim().equalsIgnoreCase("false")) {
                provider.setSyncable(false);
            }
        }
        if (eElement.getAttribute("android:writePermission") != null && eElement.getAttribute("android:writePermission").trim().length() > 0) {
            provider.setWritePermission(eElement.getAttribute("android:writePermission").trim());
        }
        return provider;
    }

    private void readReceiver(Document doc, Manifest manifest) {
        NodeList nList = doc.getElementsByTagName("receiver");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Receiver receiver = processReceiverAttributes((Element) nNode);

                NodeList childList = nNode.getChildNodes();
                for (int k = 0; k < childList.getLength(); k++) {
                    Node nNodeChild = childList.item(k);

                    if (nNodeChild.getNodeType() == Node.ELEMENT_NODE) {
                        if (nNodeChild.getNodeName().trim().equalsIgnoreCase("intent-filter")) {

                            Intent intent = new Intent();
                            /**
                             * **inside intent filter Start********
                             */
                            NodeList childList2 = nNodeChild.getChildNodes();
                            for (int j = 0; j < childList2.getLength(); j++) {
                                Node nNodeChild2 = childList2.item(j);
                                if (nNodeChild2.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNodeChild2;
                                    if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("action")) {
                                        Action action = new Action();
                                        if (eElement.getAttribute("android:name") != null
                                                && (eElement.getAttribute("android:name")).length() > 0) {
                                            action.setName(eElement.getAttribute("android:name"));
                                        }
                                        intent.getActions().add(action);
                                    } else if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("category")) {
                                        Category category = new Category();
                                        if (eElement.getAttribute("android:name") != null
                                                && (eElement.getAttribute("android:name")).length() > 0) {
                                            category.setName(eElement.getAttribute("android:name"));
                                        }
                                        intent.getCategories().add(category);
                                    } else if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("data")) {
                                        Data data = new Data();

                                        if (eElement.getAttribute("android:host") != null
                                                && (eElement.getAttribute("android:host")).length() > 0) {
                                            data.setHost(eElement.getAttribute("android:host"));
                                        }
                                        if (eElement.getAttribute("android:mimeType") != null
                                                && (eElement.getAttribute("android:mimeType")).length() > 0) {
                                            data.setMimeType(eElement.getAttribute("android:mimeType"));
                                        }
                                        if (eElement.getAttribute("android:path") != null
                                                && (eElement.getAttribute("android:path")).length() > 0) {
                                            data.setPath(eElement.getAttribute("android:path"));
                                        }
                                        if (eElement.getAttribute("android:pathPattern") != null
                                                && (eElement.getAttribute("android:pathPattern")).length() > 0) {
                                            data.setPathPattern(eElement.getAttribute("android:pathPattern"));
                                        }
                                        if (eElement.getAttribute("android:pathPrefix") != null
                                                && (eElement.getAttribute("android:pathPrefix")).length() > 0) {
                                            data.setPathPrefix(eElement.getAttribute("android:pathPrefix"));
                                        }
                                        if (eElement.getAttribute("android:port") != null
                                                && (eElement.getAttribute("android:port")).length() > 0) {
                                            data.setPort(eElement.getAttribute("android:port"));
                                        }
                                        if (eElement.getAttribute("android:scheme") != null
                                                && (eElement.getAttribute("android:scheme")).length() > 0) {
                                            data.setScheme(eElement.getAttribute("android:scheme"));
                                        }
                                        intent.getData().add(data);
                                    }
                                }
                            }
                            /**
                             * **inside intent filter End********
                             */
                            Element eElement = (Element) nNodeChild;
                            if (eElement.getAttribute("android:priority") != null
                                    && (eElement.getAttribute("android:priority")).length() > 0) {
                                intent.setPriority(Integer.parseInt(eElement.getAttribute("android:priority")));
                            }
                            if (eElement.getAttribute("android:icon") != null
                                    && (eElement.getAttribute("android:icon")).length() > 0) {
                                intent.setIcon(eElement.getAttribute("android:icon"));
                            }
                            if (eElement.getAttribute("android:label") != null
                                    && (eElement.getAttribute("android:label")).length() > 0) {
                                intent.setLabel(eElement.getAttribute("android:label"));
                            }
                            receiver.getIntents().add(intent);
                        } else if (nNodeChild.getNodeName().trim().equalsIgnoreCase("meta-data")) {
                            MetaData metaData = new MetaData();
                            Element eElement = (Element) nNodeChild;

                            if (eElement.getAttribute("android:name") != null
                                    && (eElement.getAttribute("android:name")).length() > 0) {
                                metaData.setName(eElement.getAttribute("android:name"));
                            }
                            if (eElement.getAttribute("android:resource") != null
                                    && (eElement.getAttribute("android:resource")).length() > 0) {
                                metaData.setResource(eElement.getAttribute("android:resource"));
                            }
                            if (eElement.getAttribute("android:value") != null
                                    && (eElement.getAttribute("android:value")).length() > 0) {
                                metaData.setValue(eElement.getAttribute("android:value"));
                            }
                            receiver.getMetaDatas().add(metaData);
                        }
                    }
                }
                manifest.getApplication().getApplicationComponents().add(receiver);
            }
        }
    }

    private Receiver processReceiverAttributes(Element eElement) {
        Receiver receiver = new Receiver();
        if (eElement.getAttribute("android:enabled") != null && eElement.getAttribute("android:enabled").trim().length() > 0) {
            if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("true")) {
                receiver.setEnabled(true);
            } else if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("false")) {
                receiver.setEnabled(false);
            }
        }
        if (eElement.getAttribute("android:exported") != null && eElement.getAttribute("android:exported").trim().length() > 0) {
            if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("true")) {
                receiver.setExported(true);
            } else if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("false")) {
                receiver.setExported(false);
            }
        }
        if (eElement.getAttribute("android:icon") != null && eElement.getAttribute("android:icon").trim().length() > 0) {
            receiver.setIcon(eElement.getAttribute("android:icon").trim());
        }

        if (eElement.getAttribute("android:label") != null && eElement.getAttribute("android:label").trim().length() > 0) {
            receiver.setLabel(eElement.getAttribute("android:label").trim());
        }
        if (eElement.getAttribute("android:name") != null && eElement.getAttribute("android:name").trim().length() > 0) {
            receiver.setName(eElement.getAttribute("android:name").trim());
        }
        if (eElement.getAttribute("android:permission") != null && eElement.getAttribute("android:permission").trim().length() > 0) {
            receiver.setPermission(eElement.getAttribute("android:permission").trim());
        }
        if (eElement.getAttribute("android:process") != null && eElement.getAttribute("android:process").trim().length() > 0) {
            receiver.setProcess(eElement.getAttribute("android:process").trim());
        }
        return receiver;
    }

    private void readService(Document doc, Manifest manifest) {
        NodeList nList = doc.getElementsByTagName("service");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Service service = processServiceAttributes((Element) nNode);

                NodeList childList = nNode.getChildNodes();
                for (int k = 0; k < childList.getLength(); k++) {
                    Node nNodeChild = childList.item(k);

                    if (nNodeChild.getNodeType() == Node.ELEMENT_NODE) {
                        if (nNodeChild.getNodeName().trim().equalsIgnoreCase("intent-filter")) {

                            Intent intent = new Intent();
                            /**
                             * **inside intent filter Start********
                             */
                            NodeList childList2 = nNodeChild.getChildNodes();
                            for (int j = 0; j < childList2.getLength(); j++) {
                                Node nNodeChild2 = childList2.item(j);
                                if (nNodeChild2.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNodeChild2;
                                    if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("action")) {
                                        Action action = new Action();
                                        if (eElement.getAttribute("android:name") != null
                                                && (eElement.getAttribute("android:name")).length() > 0) {
                                            action.setName(eElement.getAttribute("android:name"));
                                        }
                                        intent.getActions().add(action);
                                    } else if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("category")) {
                                        Category category = new Category();
                                        if (eElement.getAttribute("android:name") != null
                                                && (eElement.getAttribute("android:name")).length() > 0) {
                                            category.setName(eElement.getAttribute("android:name"));
                                        }
                                        intent.getCategories().add(category);
                                    } else if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("data")) {
                                        Data data = new Data();

                                        if (eElement.getAttribute("android:host") != null
                                                && (eElement.getAttribute("android:host")).length() > 0) {
                                            data.setHost(eElement.getAttribute("android:host"));
                                        }
                                        if (eElement.getAttribute("android:mimeType") != null
                                                && (eElement.getAttribute("android:mimeType")).length() > 0) {
                                            data.setMimeType(eElement.getAttribute("android:mimeType"));
                                        }
                                        if (eElement.getAttribute("android:path") != null
                                                && (eElement.getAttribute("android:path")).length() > 0) {
                                            data.setPath(eElement.getAttribute("android:path"));
                                        }
                                        if (eElement.getAttribute("android:pathPattern") != null
                                                && (eElement.getAttribute("android:pathPattern")).length() > 0) {
                                            data.setPathPattern(eElement.getAttribute("android:pathPattern"));
                                        }
                                        if (eElement.getAttribute("android:pathPrefix") != null
                                                && (eElement.getAttribute("android:pathPrefix")).length() > 0) {
                                            data.setPathPrefix(eElement.getAttribute("android:pathPrefix"));
                                        }
                                        if (eElement.getAttribute("android:port") != null
                                                && (eElement.getAttribute("android:port")).length() > 0) {
                                            data.setPort(eElement.getAttribute("android:port"));
                                        }
                                        if (eElement.getAttribute("android:scheme") != null
                                                && (eElement.getAttribute("android:scheme")).length() > 0) {
                                            data.setScheme(eElement.getAttribute("android:scheme"));
                                        }
                                        intent.getData().add(data);
                                    }
                                }
                            }
                            /**
                             * **inside intent filter End********
                             */
                            Element eElement = (Element) nNodeChild;
                            if (eElement.getAttribute("android:priority") != null
                                    && (eElement.getAttribute("android:priority")).length() > 0) {
                                intent.setPriority(Integer.parseInt(eElement.getAttribute("android:priority")));
                            }
                            if (eElement.getAttribute("android:icon") != null
                                    && (eElement.getAttribute("android:icon")).length() > 0) {
                                intent.setIcon(eElement.getAttribute("android:icon"));
                            }
                            if (eElement.getAttribute("android:label") != null
                                    && (eElement.getAttribute("android:label")).length() > 0) {
                                intent.setLabel(eElement.getAttribute("android:label"));
                            }
                            service.getIntents().add(intent);
                        } else if (nNodeChild.getNodeName().trim().equalsIgnoreCase("meta-data")) {
                            MetaData metaData = new MetaData();
                            Element eElement = (Element) nNodeChild;

                            if (eElement.getAttribute("android:name") != null
                                    && (eElement.getAttribute("android:name")).length() > 0) {
                                metaData.setName(eElement.getAttribute("android:name"));
                            }
                            if (eElement.getAttribute("android:resource") != null
                                    && (eElement.getAttribute("android:resource")).length() > 0) {
                                metaData.setResource(eElement.getAttribute("android:resource"));
                            }
                            if (eElement.getAttribute("android:value") != null
                                    && (eElement.getAttribute("android:value")).length() > 0) {
                                metaData.setValue(eElement.getAttribute("android:value"));
                            }
                            service.getMetaDatas().add(metaData);
                        }
                    }
                }
                manifest.getApplication().getApplicationComponents().add(service);
            }
        }
    }

    /**
     *
     * @param doc
     * @return an arraylist of Activity Object read from the manifest file
     *
     * private void readService(Document doc, Manifest manifest) { NodeList
     * nList = doc.getElementsByTagName("service");
     *
     * for (int temp = 0; temp < nList.getLength(); temp++) { Node nNode =
     * nList.item(temp); if (nNode.getNodeType() == Node.ELEMENT_NODE) {
     * manifest.getApplication().getApplicationComponents().add(processServiceAttributes((Element)
     * nNode)); } } }
     */
    private Service processServiceAttributes(Element eElement) {
        Service service = new Service();
        if (eElement.getAttribute("android:enabled") != null && eElement.getAttribute("android:enabled").trim().length() > 0) {
            if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("true")) {
                service.setEnabled(true);
            } else if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("false")) {
                service.setEnabled(false);
            }
        }
        if (eElement.getAttribute("android:exported") != null && eElement.getAttribute("android:exported").trim().length() > 0) {
            if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("true")) {
                service.setExported(true);
            } else if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("false")) {
                service.setExported(false);
            }
        }
        if (eElement.getAttribute("android:icon") != null && eElement.getAttribute("android:icon").trim().length() > 0) {
            service.setIcon(eElement.getAttribute("android:icon").trim());
        }

        if (eElement.getAttribute("android:isolatedProcess") != null && eElement.getAttribute("android:isolatedProcess").trim().length() > 0) {
            if (eElement.getAttribute("android:isolatedProcess").trim().equalsIgnoreCase("true")) {
                service.setIsolatedProcess(true);
            } else if (eElement.getAttribute("android:isolatedProcess").trim().equalsIgnoreCase("false")) {
                service.setIsolatedProcess(false);
            }
        }
        if (eElement.getAttribute("android:label") != null && eElement.getAttribute("android:label").trim().length() > 0) {
            service.setLabel(eElement.getAttribute("android:label").trim());
        }
        if (eElement.getAttribute("android:name") != null && eElement.getAttribute("android:name").trim().length() > 0) {
            service.setName(eElement.getAttribute("android:name").trim());
        }
        if (eElement.getAttribute("android:permission") != null && eElement.getAttribute("android:permission").trim().length() > 0) {
            service.setPermission(eElement.getAttribute("android:permission").trim());
        }
        if (eElement.getAttribute("android:process") != null && eElement.getAttribute("android:process").trim().length() > 0) {
            service.setProcess(eElement.getAttribute("android:process").trim());
        }
        return service;
    }

    /**
     *
     * @param doc
     * @return an arraylist of Activity Object read from the manifest file
     */
    private void readApplication(Document doc, Manifest manifest) {
        NodeList nList = doc.getElementsByTagName("application");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                manifest.setApplication(processApplicationAttributes((Element) nNode));
            }
        }
    }

    private Application processApplicationAttributes(Element eElement) {
        Application app = new Application();

        if (eElement.getAttribute("android:allowTaskReparenting") != null && eElement.getAttribute("android:allowTaskReparenting").trim().length() > 0) {
            if (eElement.getAttribute("android:allowTaskReparenting").trim().equalsIgnoreCase("true")) {
                app.setAllowTaskReparenting(true);
            } else if (eElement.getAttribute("android:allowTaskReparenting").trim().equalsIgnoreCase("false")) {
                app.setAllowTaskReparenting(false);
            }
        }
        if (eElement.getAttribute("android:allowBackup") != null && eElement.getAttribute("android:allowBackup").trim().length() > 0) {
            if (eElement.getAttribute("android:allowBackup").trim().equalsIgnoreCase("true")) {
                app.setAllowBackup(true);
            } else if (eElement.getAttribute("android:allowBackup").trim().equalsIgnoreCase("false")) {
                app.setAllowBackup(false);
            }
        }
        if (eElement.getAttribute("android:backupAgent") != null && eElement.getAttribute("android:backupAgent").trim().length() > 0) {
            app.setBackupAgent(eElement.getAttribute("android:backupAgent").trim());
        }

        if (eElement.getAttribute("android:debuggable") != null && eElement.getAttribute("android:debuggable").trim().length() > 0) {
            if (eElement.getAttribute("android:debuggable").trim().equalsIgnoreCase("true")) {
                app.setDebuggable(true);
            } else if (eElement.getAttribute("android:debuggable").trim().equalsIgnoreCase("false")) {
                app.setDebuggable(false);
            }
        }
        if (eElement.getAttribute("android:description") != null && eElement.getAttribute("android:description").trim().length() > 0) {
            app.setDescription(eElement.getAttribute("android:description").trim());
        }
        if (eElement.getAttribute("android:enabled") != null && eElement.getAttribute("android:enabled").trim().length() > 0) {
            if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("true")) {
                app.setEnabled(true);
            } else if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("false")) {
                app.setEnabled(false);
            }
        }
        if (eElement.getAttribute("android:hasCode") != null && eElement.getAttribute("android:hasCode").trim().length() > 0) {
            if (eElement.getAttribute("android:hasCode").trim().equalsIgnoreCase("true")) {
                app.setHasCode(true);
            } else if (eElement.getAttribute("android:hasCode").trim().equalsIgnoreCase("false")) {
                app.setHasCode(false);
            }
        }
        if (eElement.getAttribute("android:hardwareAccelerated") != null && eElement.getAttribute("android:hardwareAccelerated").trim().length() > 0) {
            if (eElement.getAttribute("android:hardwareAccelerated").trim().equalsIgnoreCase("true")) {
                app.setHardwareAccelerated(true);
            } else if (eElement.getAttribute("android:hardwareAccelerated").trim().equalsIgnoreCase("false")) {
                app.setHardwareAccelerated(false);
            }
        }
        if (eElement.getAttribute("android:icon") != null && eElement.getAttribute("android:icon").trim().length() > 0) {
            app.setIcon(eElement.getAttribute("android:icon").trim());
        }
        if (eElement.getAttribute("android:killAfterRestore") != null && eElement.getAttribute("android:killAfterRestore").trim().length() > 0) {
            if (eElement.getAttribute("android:killAfterRestore").trim().equalsIgnoreCase("true")) {
                app.setKillAfterRestore(true);
            } else if (eElement.getAttribute("android:killAfterRestore").trim().equalsIgnoreCase("false")) {
                app.setKillAfterRestore(false);
            }
        }
        if (eElement.getAttribute("android:largeHeap") != null && eElement.getAttribute("android:largeHeap").trim().length() > 0) {
            if (eElement.getAttribute("android:largeHeap").trim().equalsIgnoreCase("true")) {
                app.setLargeHeap(true);
            } else if (eElement.getAttribute("android:largeHeap").trim().equalsIgnoreCase("false")) {
                app.setLargeHeap(false);
            }
        }
        if (eElement.getAttribute("android:label") != null && eElement.getAttribute("android:label").trim().length() > 0) {
            app.setLabel(eElement.getAttribute("android:label").trim());
        }
        if (eElement.getAttribute("android:logo") != null && eElement.getAttribute("android:logo").trim().length() > 0) {
            app.setLogo(eElement.getAttribute("android:logo").trim());
        }
        if (eElement.getAttribute("android:manageSpaceActivity") != null && eElement.getAttribute("android:manageSpaceActivity").trim().length() > 0) {
            app.setManageSpaceActivity(eElement.getAttribute("android:manageSpaceActivity").trim());
        }
        if (eElement.getAttribute("android:name") != null && eElement.getAttribute("android:name").trim().length() > 0) {
            app.setName(eElement.getAttribute("android:name").trim());
        }
        if (eElement.getAttribute("android:permission") != null && eElement.getAttribute("android:permission").trim().length() > 0) {
            app.setPermission(eElement.getAttribute("android:permission").trim());
        }
        if (eElement.getAttribute("android:persistent") != null && eElement.getAttribute("android:persistent").trim().length() > 0) {
            if (eElement.getAttribute("android:persistent").trim().equalsIgnoreCase("true")) {
                app.setPersistent(true);
            } else if (eElement.getAttribute("android:persistent").trim().equalsIgnoreCase("false")) {
                app.setPersistent(false);
            }
        }
        if (eElement.getAttribute("android:process") != null && eElement.getAttribute("android:process").trim().length() > 0) {
            app.setProcess(eElement.getAttribute("android:process").trim());
        }
        if (eElement.getAttribute("android:restoreAnyVersion") != null && eElement.getAttribute("android:restoreAnyVersion").trim().length() > 0) {
            if (eElement.getAttribute("android:restoreAnyVersion").trim().equalsIgnoreCase("true")) {
                app.setRestoreAnyVersion(true);
            } else if (eElement.getAttribute("android:restoreAnyVersion").trim().equalsIgnoreCase("false")) {
                app.setRestoreAnyVersion(false);
            }
        }
        if (eElement.getAttribute("android:requiredAccountType") != null && eElement.getAttribute("android:requiredAccountType").trim().length() > 0) {
            app.setRequiredAccountType(eElement.getAttribute("android:requiredAccountType").trim());
        }
        if (eElement.getAttribute("android:restrictedAccountType") != null && eElement.getAttribute("android:restrictedAccountType").trim().length() > 0) {
            app.setRestrictedAccountType(eElement.getAttribute("android:restrictedAccountType").trim());
        }
        if (eElement.getAttribute("android:supportsRtl") != null && eElement.getAttribute("android:supportsRtl").trim().length() > 0) {
            if (eElement.getAttribute("android:supportsRtl").trim().equalsIgnoreCase("true")) {
                app.setSupportsRtl(true);
            } else if (eElement.getAttribute("android:supportsRtl").trim().equalsIgnoreCase("false")) {
                app.setSupportsRtl(false);
            }
        }

        if (eElement.getAttribute("android:taskAffinity") != null && eElement.getAttribute("android:taskAffinity").trim().length() > 0) {
            app.setTaskAffinity(eElement.getAttribute("android:taskAffinity").trim());
        }
        if (eElement.getAttribute("android:testOnly") != null && eElement.getAttribute("android:testOnly").trim().length() > 0) {
            if (eElement.getAttribute("android:testOnly").trim().equalsIgnoreCase("true")) {
                app.setTestOnly(true);
            } else if (eElement.getAttribute("android:testOnly").trim().equalsIgnoreCase("false")) {
                app.setTestOnly(false);
            }
        }
        if (eElement.getAttribute("android:theme") != null && eElement.getAttribute("android:theme").trim().length() > 0) {
            app.setTheme(eElement.getAttribute("android:theme").trim());
        }

        if (eElement.getAttribute("android:uiOptions") != null && eElement.getAttribute("android:uiOptions").trim().length() > 0) {
            app.setUiOptions(eElement.getAttribute("android:uiOptions").trim());
        }
        if (eElement.getAttribute("android:vmSafeMode") != null && eElement.getAttribute("android:vmSafeMode").trim().length() > 0) {
            if (eElement.getAttribute("android:vmSafeMode").trim().equalsIgnoreCase("true")) {
                app.setVmSafeMode(true);
            } else if (eElement.getAttribute("android:vmSafeMode").trim().equalsIgnoreCase("false")) {
                app.setVmSafeMode(false);
            }
        }
        return app;
    }

    private void readManifest(Document doc, Manifest manifest) {
        try {
            if (doc.getDocumentElement().getAttribute("package") != null && doc.getDocumentElement().getAttribute("package").trim().length() > 0) {
                manifest.setPackageName(doc.getDocumentElement().getAttribute("package"));
            }
            if (doc.getDocumentElement().getAttribute("android:sharedUserId") != null && doc.getDocumentElement().getAttribute("android:sharedUserId").trim().length() > 0) {
                manifest.setSharedUserId(doc.getDocumentElement().getAttribute("android:sharedUserId"));
            }
            if (doc.getDocumentElement().getAttribute("android:sharedUserLabel") != null && doc.getDocumentElement().getAttribute("android:sharedUserLabel").trim().length() > 0) {
                manifest.setSharedUserLabel(doc.getDocumentElement().getAttribute("android:sharedUserLabel"));
            }
            if (doc.getDocumentElement().getAttribute("android:versionCode") != null && doc.getDocumentElement().getAttribute("android:versionCode").trim().length() > 0) {
                manifest.setVersionCode(Integer.parseInt(doc.getDocumentElement().getAttribute("android:versionCode")));
            }
            if (doc.getDocumentElement().getAttribute("android:versionName") != null && doc.getDocumentElement().getAttribute("android:versionName").trim().length() > 0) {
                manifest.setVersionName(doc.getDocumentElement().getAttribute("android:versionName"));
            }
            if (doc.getDocumentElement().getAttribute("android:installLocation") != null && doc.getDocumentElement().getAttribute("android:installLocation").trim().length() > 0) {
                manifest.setInstallLocation(doc.getDocumentElement().getAttribute("android:installLocation"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readSDK(Document doc, Manifest manifest) {
        try {
            NodeList nList = doc.getElementsByTagName("uses-sdk");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    manifest.setSdkMinVersion(Integer.parseInt(0 + eElement.getAttribute("android:minSdkVersion")));
                    manifest.setSdkTargetVersion(Integer.parseInt(0 + eElement.getAttribute("android:targetSdkVersion")));
                    manifest.setSdkMaxVersion(Integer.parseInt(0 + eElement.getAttribute("android:maxSdkVersion")));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readActivityAlias(Document doc, Manifest manifest) {
        NodeList nList = doc.getElementsByTagName("activity-alias");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                ActivityAlias activityalias = processActivityAliasAttributes((Element) nNode);

                NodeList childList = nNode.getChildNodes();
                for (int k = 0; k < childList.getLength(); k++) {
                    Node nNodeChild = childList.item(k);

                    if (nNodeChild.getNodeType() == Node.ELEMENT_NODE) {
                        if (nNodeChild.getNodeName().trim().equalsIgnoreCase("intent-filter")) {

                            Intent intent = new Intent();
                            /**
                             * **inside intent filter Start********
                             */
                            NodeList childList2 = nNodeChild.getChildNodes();
                            for (int j = 0; j < childList2.getLength(); j++) {
                                Node nNodeChild2 = childList2.item(j);
                                if (nNodeChild2.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNodeChild2;
                                    if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("action")) {
                                        Action action = new Action();
                                        if (eElement.getAttribute("android:name") != null
                                                && (eElement.getAttribute("android:name")).length() > 0) {
                                            action.setName(eElement.getAttribute("android:name"));
                                        }
                                        intent.getActions().add(action);
                                    } else if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("category")) {
                                        Category category = new Category();
                                        if (eElement.getAttribute("android:name") != null
                                                && (eElement.getAttribute("android:name")).length() > 0) {
                                            category.setName(eElement.getAttribute("android:name"));
                                        }
                                        intent.getCategories().add(category);
                                    } else if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("data")) {
                                        Data data = new Data();

                                        if (eElement.getAttribute("android:host") != null
                                                && (eElement.getAttribute("android:host")).length() > 0) {
                                            data.setHost(eElement.getAttribute("android:host"));
                                        }
                                        if (eElement.getAttribute("android:mimeType") != null
                                                && (eElement.getAttribute("android:mimeType")).length() > 0) {
                                            data.setMimeType(eElement.getAttribute("android:mimeType"));
                                        }
                                        if (eElement.getAttribute("android:path") != null
                                                && (eElement.getAttribute("android:path")).length() > 0) {
                                            data.setPath(eElement.getAttribute("android:path"));
                                        }
                                        if (eElement.getAttribute("android:pathPattern") != null
                                                && (eElement.getAttribute("android:pathPattern")).length() > 0) {
                                            data.setPathPattern(eElement.getAttribute("android:pathPattern"));
                                        }
                                        if (eElement.getAttribute("android:pathPrefix") != null
                                                && (eElement.getAttribute("android:pathPrefix")).length() > 0) {
                                            data.setPathPrefix(eElement.getAttribute("android:pathPrefix"));
                                        }
                                        if (eElement.getAttribute("android:port") != null
                                                && (eElement.getAttribute("android:port")).length() > 0) {
                                            data.setPort(eElement.getAttribute("android:port"));
                                        }
                                        if (eElement.getAttribute("android:scheme") != null
                                                && (eElement.getAttribute("android:scheme")).length() > 0) {
                                            data.setScheme(eElement.getAttribute("android:scheme"));
                                        }
                                        intent.getData().add(data);
                                    }
                                }
                            }
                            /**
                             * **inside intent filter End********
                             */
                            Element eElement = (Element) nNodeChild;
                            if (eElement.getAttribute("android:priority") != null
                                    && (eElement.getAttribute("android:priority")).length() > 0) {
                                intent.setPriority(Integer.parseInt(eElement.getAttribute("android:priority")));
                            }
                            if (eElement.getAttribute("android:icon") != null
                                    && (eElement.getAttribute("android:icon")).length() > 0) {
                                intent.setIcon(eElement.getAttribute("android:icon"));
                            }
                            if (eElement.getAttribute("android:label") != null
                                    && (eElement.getAttribute("android:label")).length() > 0) {
                                intent.setLabel(eElement.getAttribute("android:label"));
                            }
                            activityalias.getIntents().add(intent);
                        } else if (nNodeChild.getNodeName().trim().equalsIgnoreCase("meta-data")) {
                            MetaData metaData = new MetaData();
                            Element eElement = (Element) nNodeChild;

                            if (eElement.getAttribute("android:name") != null
                                    && (eElement.getAttribute("android:name")).length() > 0) {
                                metaData.setName(eElement.getAttribute("android:name"));
                            }
                            if (eElement.getAttribute("android:resource") != null
                                    && (eElement.getAttribute("android:resource")).length() > 0) {
                                metaData.setResource(eElement.getAttribute("android:resource"));
                            }
                            if (eElement.getAttribute("android:value") != null
                                    && (eElement.getAttribute("android:value")).length() > 0) {
                                metaData.setValue(eElement.getAttribute("android:value"));
                            }
                            activityalias.getMetaDatas().add(metaData);
                        }
                    }
                }
                manifest.getApplication().getApplicationComponents().add(activityalias);
            }
        }
    }

    /**
     *
     * @param doc
     * @return an arraylist of Activity Object read from the manifest file
     *
     * private void readActivityAlias(Document doc, Manifest manifest) {
     * NodeList nList = doc.getElementsByTagName("activity-alias");
     *
     * for (int temp = 0; temp < nList.getLength(); temp++) { Node nNode =
     * nList.item(temp); if (nNode.getNodeType() == Node.ELEMENT_NODE) {
     * manifest.getApplication().getApplicationComponents().add(processActivityAliasAttributes((Element)
     * nNode)); } } }
     */
    private ActivityAlias processActivityAliasAttributes(Element eElement) {
        ActivityAlias activityAlias = new ActivityAlias();
        if (eElement.getAttribute("android:enabled") != null && eElement.getAttribute("android:enabled").trim().length() > 0) {
            if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("true")) {
                activityAlias.setEnabled(true);
            } else if (eElement.getAttribute("android:enabled").trim().equalsIgnoreCase("false")) {
                activityAlias.setEnabled(false);
            }
        }
        if (eElement.getAttribute("android:exported") != null && eElement.getAttribute("android:exported").trim().length() > 0) {
            if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("true")) {
                activityAlias.setExported(true);
            } else if (eElement.getAttribute("android:exported").trim().equalsIgnoreCase("false")) {
                activityAlias.setExported(false);
            }
        }
        if (eElement.getAttribute("android:icon") != null && eElement.getAttribute("android:icon").trim().length() > 0) {
            activityAlias.setIcon(eElement.getAttribute("android:icon").trim());
        }

        if (eElement.getAttribute("android:label") != null && eElement.getAttribute("android:label").trim().length() > 0) {
            activityAlias.setLabel(eElement.getAttribute("android:label").trim());
        }
        if (eElement.getAttribute("android:name") != null && eElement.getAttribute("android:name").trim().length() > 0) {
            activityAlias.setName(eElement.getAttribute("android:name").trim());
        }
        if (eElement.getAttribute("android:permission") != null && eElement.getAttribute("android:permission").trim().length() > 0) {
            activityAlias.setPermission(eElement.getAttribute("android:permission").trim());
        }
        if (eElement.getAttribute("android:targetActivity") != null && eElement.getAttribute("android:targetActivity").trim().length() > 0) {
            activityAlias.setTargetActivity(eElement.getAttribute("android:targetActivity").trim());
        }
        return activityAlias;
    }

    private void readActivity(Document doc, Manifest manifest) {
        NodeList nList = doc.getElementsByTagName("activity");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Activity activity = processActivityAttributes((Element) nNode);

                NodeList childList = nNode.getChildNodes();
                for (int k = 0; k < childList.getLength(); k++) {
                    Node nNodeChild = childList.item(k);

                    if (nNodeChild.getNodeType() == Node.ELEMENT_NODE) {
                        if (nNodeChild.getNodeName().trim().equalsIgnoreCase("intent-filter")) {

                            Intent intent = new Intent();
                            /**
                             * **inside intent filter Start********
                             */
                            NodeList childList2 = nNodeChild.getChildNodes();
                            for (int j = 0; j < childList2.getLength(); j++) {
                                Node nNodeChild2 = childList2.item(j);
                                if (nNodeChild2.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNodeChild2;
                                    if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("action")) {
                                        Action action = new Action();
                                        if (eElement.getAttribute("android:name") != null
                                                && (eElement.getAttribute("android:name")).length() > 0) {
                                            action.setName(eElement.getAttribute("android:name"));
                                        }
                                        intent.getActions().add(action);
                                    } else if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("category")) {
                                        Category category = new Category();
                                        if (eElement.getAttribute("android:name") != null
                                                && (eElement.getAttribute("android:name")).length() > 0) {
                                            category.setName(eElement.getAttribute("android:name"));
                                        }
                                        intent.getCategories().add(category);
                                    } else if (nNodeChild2.getNodeName().trim().equalsIgnoreCase("data")) {
                                        Data data = new Data();

                                        if (eElement.getAttribute("android:host") != null
                                                && (eElement.getAttribute("android:host")).length() > 0) {
                                            data.setHost(eElement.getAttribute("android:host"));
                                        }
                                        if (eElement.getAttribute("android:mimeType") != null
                                                && (eElement.getAttribute("android:mimeType")).length() > 0) {
                                            data.setMimeType(eElement.getAttribute("android:mimeType"));
                                        }
                                        if (eElement.getAttribute("android:path") != null
                                                && (eElement.getAttribute("android:path")).length() > 0) {
                                            data.setPath(eElement.getAttribute("android:path"));
                                        }
                                        if (eElement.getAttribute("android:pathPattern") != null
                                                && (eElement.getAttribute("android:pathPattern")).length() > 0) {
                                            data.setPathPattern(eElement.getAttribute("android:pathPattern"));
                                        }
                                        if (eElement.getAttribute("android:pathPrefix") != null
                                                && (eElement.getAttribute("android:pathPrefix")).length() > 0) {
                                            data.setPathPrefix(eElement.getAttribute("android:pathPrefix"));
                                        }
                                        if (eElement.getAttribute("android:port") != null
                                                && (eElement.getAttribute("android:port")).length() > 0) {
                                            data.setPort(eElement.getAttribute("android:port"));
                                        }
                                        if (eElement.getAttribute("android:scheme") != null
                                                && (eElement.getAttribute("android:scheme")).length() > 0) {
                                            data.setScheme(eElement.getAttribute("android:scheme"));
                                        }
                                        intent.getData().add(data);
                                    }
                                }
                            }
                            /**
                             * **inside intent filter End********
                             */
                            Element eElement = (Element) nNodeChild;
                            if (eElement.getAttribute("android:priority") != null
                                    && (eElement.getAttribute("android:priority")).length() > 0) {
                                intent.setPriority(Integer.parseInt(eElement.getAttribute("android:priority")));
                            }
                            if (eElement.getAttribute("android:icon") != null
                                    && (eElement.getAttribute("android:icon")).length() > 0) {
                                intent.setIcon(eElement.getAttribute("android:icon"));
                            }
                            if (eElement.getAttribute("android:label") != null
                                    && (eElement.getAttribute("android:label")).length() > 0) {
                                intent.setLabel(eElement.getAttribute("android:label"));
                            }
                            activity.getIntents().add(intent);
                        } else if (nNodeChild.getNodeName().trim().equalsIgnoreCase("meta-data")) {
                            MetaData metaData = new MetaData();
                            Element eElement = (Element) nNodeChild;

                            if (eElement.getAttribute("android:name") != null
                                    && (eElement.getAttribute("android:name")).length() > 0) {
                                metaData.setName(eElement.getAttribute("android:name"));
                            }
                            if (eElement.getAttribute("android:resource") != null
                                    && (eElement.getAttribute("android:resource")).length() > 0) {
                                metaData.setResource(eElement.getAttribute("android:resource"));
                            }
                            if (eElement.getAttribute("android:value") != null
                                    && (eElement.getAttribute("android:value")).length() > 0) {
                                metaData.setValue(eElement.getAttribute("android:value"));
                            }
                            activity.getMetaDatas().add(metaData);
                        }
                    }
                }
                manifest.getApplication().getApplicationComponents().add(activity);
            }
        }
    }

    /**
     *
     * @param doc
     * @return an arraylist of Activity Object read from the manifest file
     *
     */
    private Activity processActivityAttributes(Element eElement) {
        Activity act = new Activity();
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
        return act;
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
