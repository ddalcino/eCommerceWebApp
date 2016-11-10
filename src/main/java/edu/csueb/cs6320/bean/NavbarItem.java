package edu.csueb.cs6320.bean;

import java.io.Serializable;

/**
 *
 * @author dave
 */
public class NavbarItem implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String href;
    private String label;
    private boolean isActive;

    public NavbarItem() {
    }

    public NavbarItem(String href, String label, boolean isActive) {
        this.href = href;
        this.label = label;
        this.isActive = isActive;
    }
    
    /**
     * Copy constructor
     * @param other NavbarItem to copy
     */
    public NavbarItem(NavbarItem other) {
        this.href = other.href;
        this.label = other.label;
        this.isActive = other.isActive;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
