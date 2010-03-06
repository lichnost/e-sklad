package org.stablylab.webui.client.widget;

import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;

public class AppInfo {
	
	public static void display(String title, String text){
		InfoConfig ic = new InfoConfig(title,text);
		ic.height = 100;
		ic.width = 350;
		ic.display = 5000;
		Info.display(ic);
	}
	
}
