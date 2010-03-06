package org.stablylab.core.accessory;

import org.mozilla.javascript.ClassShutter;

public class PriceClassShutter implements ClassShutter {

	@Override
	public boolean visibleToScripts(String cls) {
		boolean result = false;
		if(cls.startsWith("org.stablylab.core.model"))
			result = true;
		else if(cls.equals("org.stablylab.core.accessory.PriceHelper"))
			result = true;
		else if(cls.equals("java.util.HashMap"))
			result = true;
		else result = false;
		return result;
	}

}
