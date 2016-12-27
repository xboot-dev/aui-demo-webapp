/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

import java.util.List;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.RenderFragment;

/**
 * Gadget Panel
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public interface GadgetPanel extends RenderFragment {

	List<Gadget> getLeftColumnGadgets();
	List<Gadget> getRightColumnGadgets();

}