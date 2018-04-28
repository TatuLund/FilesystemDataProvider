package org.vaadin.filesystemdataprovider;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.stream.Stream;

import com.vaadin.data.provider.BackEndHierarchicalDataProvider;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.HierarchicalQuery;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.Registration;

// This is the server-side UI component that provides public API 
// for MyComponent
public class FilesystemDataProvider extends TreeDataProvider<File> {

    private boolean recursive;
    FilesystemData treeData;
    
    /**
     * Construct new FilesystemDataProvider with given FilesystemData 
     * 
     * @param treeData The data model
     */
    public FilesystemDataProvider(FilesystemData treeData) {
    	super(treeData);
    	recursive = treeData.isRecursive();
    	this.treeData = treeData;
    }

    @Override
    public int getChildCount(
            HierarchicalQuery<File, SerializablePredicate<File>> query) {    	
    	final File parent = query.getParentOptional().orElse(treeData.getRootItems().get(0));
    	if (parent.isFile()) return 0;
    	else return (int) fetchChildren(query).count();
    }    
    
    @Override
    public boolean hasChildren(File item) {
    	if (!isInMemory()) {
    		return !treeData.getChildrenFromFilesystem(item).isEmpty();
    	} else {
    		return super.hasChildren(item);
    	}
    }

    @Override
    public Stream<File> fetchChildren(
            HierarchicalQuery<File, SerializablePredicate<File>> query) {
    	if (!isInMemory()) {    		
        	File parent = query.getParentOptional().orElse(treeData.getRootItems().get(0));
			if (treeData.getChildren(parent).isEmpty()) {
	        	List<File> files = treeData.getChildrenFromFilesystem(parent);
				treeData.addItems(parent, files);
				return files.stream();
			} else {
				return super.fetchChildren(query);
			}
    	} else {
    		return super.fetchChildren(query);
    	}
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
