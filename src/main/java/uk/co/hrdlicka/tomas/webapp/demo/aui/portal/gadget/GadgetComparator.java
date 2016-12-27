/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

import java.util.Comparator;

/**
 * Gadget Comparator
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class GadgetComparator implements Comparator<Gadget> {

    @Override
    public int compare(final Gadget gadget1, final Gadget gadget2) {
        Integer order1 = gadget1.getOrder();
        Integer order2 = gadget2.getOrder();
        return order1.compareTo(order2);
    }
}