package util;

/**
 * HeaderComboItem.java  Date Created: Apr 30, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class HeaderComboItem {
    public HeaderComboItem(Object item, boolean isHeader) {
        this.item = item;
        this.isHeader = isHeader;
    }

    public HeaderComboItem(Object item) {
        this(item, false);
    }

    public Object getItem() {
        return item;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    public String toString() {
        return item.toString();
    }

    Object item;
    boolean isHeader;
}