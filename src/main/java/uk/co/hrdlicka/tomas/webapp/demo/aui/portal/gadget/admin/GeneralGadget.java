/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.admin;

import java.util.HashMap;
import java.util.Map;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.AbstractGadget;

/**
 * General Gadget
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class GeneralGadget extends AbstractGadget {

    public static final String TEMPLATE = "/gadget/general.vm";

    public GeneralGadget() {
        setTemplate(TEMPLATE);
    }

    @Override
    protected Map<String, Object> createVelocityParams(Map<String, Object> context) {
        Map<String, Object> velocityParams = new HashMap<String, Object>();
        return velocityParams;
    }
}