/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generic Gadget Panel
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class GenericGadgetPanel extends AbstractGadgetPanel {
    protected final List<Gadget> leftColumn = new ArrayList<Gadget>();
    protected final List<Gadget> rightColumn = new ArrayList<Gadget>();

    public void addLeftColumnGadget(final Gadget gadget) {
        if (leftColumn.contains(gadget)) {
            return;
        }

        leftColumn.add(gadget);
    }

    public void addRightColumnGadget(final Gadget gadget) {
        if (rightColumn.contains(gadget)) {
            return;
        }

        rightColumn.add(gadget);
    }

    @Override
    public List<Gadget> getLeftColumnGadgets() {
        Collections.sort(leftColumn, new GadgetComparator());
        return leftColumn;
    }

    @Override
    public List<Gadget> getRightColumnGadgets() {
        Collections.sort(rightColumn, new GadgetComparator());
        return rightColumn;
    }
}