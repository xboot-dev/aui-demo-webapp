/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.RenderFragment;

/**
 * Gadget
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public interface Gadget extends RenderFragment {

    String getKey();
    void setKey(String key);
    String getDisplayName();
    void setDisplayName(String displayName);
    String getDescription();
    void setDescription(String description);
    int getOrder();
    void setOrder(int order);

}