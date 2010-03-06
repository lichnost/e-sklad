package org.stablylab.core.settings;

import com.thoughtworks.xstream.XStream;

public class XStreamFactory {

	private static XStream _sharedInstance;
	
	public static synchronized XStream getXStream(){
		
		if(_sharedInstance == null)
			_sharedInstance = new XStream();
		_sharedInstance.setMode(XStream.ID_REFERENCES);
		
		return _sharedInstance;
		
	}
}
