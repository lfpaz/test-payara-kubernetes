package br.com.lfpaz.test;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author lfpaz
 */
@Named
@SessionScoped
public class IndexControl implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    private String value;
    private HashMap<String, String> map = new HashMap<>();

    public void add(String k, String v) {
        key = k;
        value = v;
        this.add();
    }

    public void add() {
        map.put(key, value);
        Logger.getGlobal().log(Level.FINE, "CDI/JSF Key ->{0} Value -> {1}", new Object[]{key, value});
        key = "";
        value = "";
    }

    public void clear() {
        map.clear();
        Logger.getGlobal().fine("CDI/JSF Clear Data");
    }

    public void clearSession() {
        Logger.getGlobal().log(Level.FINE, "CDI/JSF Session Invalidade");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String valor) {
        this.value = valor;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public String getGitVersion() {
        ResourceBundle rb = ResourceBundle.getBundle("git");
        try {
            String branch = rb.getString("git.branch");
            String id = rb.getString("git.commit.id.abbrev");
            String server = InetAddress.getLocalHost().getHostName();
            return "Server: " + server + " - Branch: " + branch + " Commit: " + id;
        } catch (UnknownHostException ex) {
            return "No version found";
        }
    }
}
