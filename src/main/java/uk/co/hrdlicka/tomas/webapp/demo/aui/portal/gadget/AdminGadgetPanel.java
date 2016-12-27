/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

/**
 * Admin Gadget Panel
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class AdminGadgetPanel extends GenericGadgetPanel {

    public AdminGadgetPanel() {
        super();

        setLayoutClass(null);
        setGroupClass("aui-group admin content-body");
        setItemClass("aui-item");
        setShowContentOnly(true);
    }
}