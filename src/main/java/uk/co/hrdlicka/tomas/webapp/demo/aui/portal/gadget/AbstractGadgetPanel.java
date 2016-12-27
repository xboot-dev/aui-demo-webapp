/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Abstract Gadget Panel
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractGadgetPanel implements GadgetPanel {
    protected final Logger log = Logger.getLogger(getClass().getName());

    public static final String DEFAULT_LAYOUT_CLASS = "gadget-layout";
    public static final String DEFAULT_GROUP_CLASS = "gadget-group";
    public static final String DEFAULT_ITEM_CLASS = "gadget-item";

    protected String layoutClass;
    protected String groupClass;
    protected String itemClass;
    protected boolean showContentOnly;

    public AbstractGadgetPanel() {
        setLayoutClass(DEFAULT_LAYOUT_CLASS);
        setGroupClass(DEFAULT_GROUP_CLASS);
        setItemClass(DEFAULT_ITEM_CLASS);
    }

    public String getLayoutClass() {
        return layoutClass;
    }

    public void setLayoutClass(String layoutClass) {
        this.layoutClass = layoutClass;
    }

    public String getGroupClass() {
        return groupClass;
    }

    public void setGroupClass(String groupClass) {
        this.groupClass = groupClass;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public boolean isShowContentOnly() {
        return showContentOnly;
    }

    public void setShowContentOnly(boolean showContentOnly) {
        this.showContentOnly = showContentOnly;
    }

    public abstract List<Gadget> getLeftColumnGadgets();

    public abstract List<Gadget> getRightColumnGadgets();

    public String render(final Map<String, Object> context) {
        if (!shouldDisplay(context)) {
            return null;
        }

        List<Gadget> leftColumn = getLeftColumnGadgets();
        List<Gadget> rightColumn = getRightColumnGadgets();

        if (leftColumn == null || rightColumn == null) {
            log.warn("Left and/or Right column can not be null for Gadget panel!");
            return null;
        }

        List<Gadget> mainColumn = leftColumn;

        if (mainColumn.size() < rightColumn.size()) {
            mainColumn = rightColumn;
        }

        StringBuilder templateContent = new StringBuilder();

        if (getLayoutClass() != null && !getLayoutClass().isEmpty()) {
            templateContent.append(String.format("<div class=\"%s\">", getLayoutClass()));
        }

        for (int i = 0; i < mainColumn.size(); i++) {
            templateContent.append(String.format("	<div class=\"%s\">", getGroupClass()));

            Gadget left = null;
            Gadget right = null;

            if (i < leftColumn.size()) {
                left = leftColumn.get(i);
            }

            if (i < rightColumn.size()) {
                right = rightColumn.get(i);
            }

            renderGadget(left, templateContent, context);
            renderGadget(right, templateContent, context);

            templateContent.append("	</div>");
        }

        if (getLayoutClass() != null && !getLayoutClass().isEmpty()) {
            templateContent.append("</div>");
        }

        return templateContent.toString();
    }

    public boolean shouldDisplay(final Map<String, Object> context) {
        return true;
    }

    protected void renderGadget(final Gadget gadget, final StringBuilder content, final Map<String, Object> context) {
        String classStyle = (gadget != null && gadget.shouldDisplay(context)) ? getItemClass() : null;
        String key = (gadget != null) ? gadget.getKey() : null;

        content.append(String.format("		<div id=\"%s\" class=\"%s\">", key, classStyle));

        if (classStyle != null) {
            if (!isShowContentOnly()) {
                content.append(String.format("			<h3>%s</h3>", gadget.getDisplayName()));
            }

            content.append(gadget.render(context));
        }

        content.append("		</div>");
    }
}