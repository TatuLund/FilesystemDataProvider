package org.vaadin.filesystemdataprovider;

import java.io.File;
import java.io.FilenameFilter;
import com.vaadin.data.provider.TreeDataProvider;

// This is the server-side UI component that provides public API 
// for MyComponent
public class FilesystemDataProvider extends TreeDataProvider<File> {

    private FilenameFilter filter = null;

    private boolean recursive = true;
	
    public FilesystemDataProvider(FilesystemData treeData) {
    	super(treeData);
    }
	
	@Override
	public boolean isInMemory() {
		return true;
	}


}
