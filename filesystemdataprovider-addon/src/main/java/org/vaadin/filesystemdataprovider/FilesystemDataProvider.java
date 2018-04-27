package org.vaadin.filesystemdataprovider;

import java.io.File;
import java.io.FilenameFilter;
import com.vaadin.data.provider.TreeDataProvider;

// This is the server-side UI component that provides public API 
// for MyComponent
public class FilesystemDataProvider extends TreeDataProvider<File> {

    private boolean recursive;
	    
    /**
     * Construct new FilesystemDataProvider with given FilesystemData 
     * 
     * @param treeData The data model
     */
    public FilesystemDataProvider(FilesystemData treeData) {
    	super(treeData);
    	recursive = treeData.isRecursive();
    }
	
    /**
     * FilesystemDataProvider is fully in-memory if it is constructed
     * recursively, otherwise it is progressively lazy
     * 
     * @return boolean value
     */
	@Override
	public boolean isInMemory() {
		return recursive;
	}


}
