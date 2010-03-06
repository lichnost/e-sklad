package org.stablylab.core.report;

import org.mozilla.javascript.ClassShutter;

public class ReportClassShutter implements ClassShutter{

	@Override
	public boolean visibleToScripts(String cls) {
//		return true;
		boolean result = false;
		if(cls.startsWith("org.stablylab.core.model"))
			result = true;
		else if(cls.equals("org.stablylab.core.report.ReportHelper"))
			result = true;
		else if(cls.equals("java.util.HashMap"))
			result = true;
		else result = false;
		return result;
	}

}
