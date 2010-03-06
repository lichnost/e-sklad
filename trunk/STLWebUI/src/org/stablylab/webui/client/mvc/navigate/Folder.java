package org.stablylab.webui.client.mvc.navigate;

import com.extjs.gxt.ui.client.widget.tree.TreeItem;

@Deprecated
public class Folder extends TreeItem {

	public Folder() {

	}

	public Folder(String name) {
		setData("name", name);
		setText(name);
	}

	public Folder(String name, FolderItem[] children) {
		this(name);
		for (int i = 0; i < children.length; i++) {
			add(children[i]);
		}
	}

	public String getName() {
		return (String) getData("name");
	}

	public String toString() {
		return getName();
	}

}
