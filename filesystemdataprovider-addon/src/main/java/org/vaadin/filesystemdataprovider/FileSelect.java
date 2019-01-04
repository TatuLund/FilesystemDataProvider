package org.vaadin.filesystemdataprovider;

import java.io.File;
import java.util.Date;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Tree;

/**
 * FileSelect is a simple single file/directory selector component
 * 
 * @author Tatu Lund
 * */
public class FileSelect extends CustomField<File> {
    private Tree<File> tree = new Tree<>();
	private File rootFile;
	private File selectedFile = null;
	
	/**
	 * Constructor
	 * 
	 * @param rootFile The root directory where to browse
	 */
	public FileSelect(File rootFile) {
		this.rootFile = rootFile;
	}

	@Override
	protected Component initContent() {
    	FilesystemData root = new FilesystemData(rootFile, false);
    	FilesystemDataProvider fileSystem = new FilesystemDataProvider(root);     	
        tree.setDataProvider(fileSystem);

        tree.setItemIconGenerator(file -> {
        	return FileTypeResolver.getIcon(file);
        });
        
        tree.setItemDescriptionGenerator(file -> {
        	String desc = "";
        	if (!file.isDirectory()) {
        		Date date = new Date(file.lastModified());
        		long size = file.length();
        		String unit = "";
        		if (size > 1024*1024*1024) {
        			size = size / (1024*1024*1024);
        			unit = "GB";
        		}
        		else if (size > 1024*1024) {
        			size = size / (1024*1024);
        			unit = "MB";
        		}
        		else if (size > 1024) {
        			size = size / (1024);
        			unit = "KB";
        		} else {
        			unit = "B";        			
        		}        			        		
        		desc = file.getName()+", "+date+", "+size+ " "+unit;        		
        	} else {
        		desc = root.getChildrenFromFilesystem(file).size()+" files";
        	}
        	return desc;
        });
        
        tree.addItemClickListener(event -> {
        	File file = event.getItem();     
        	selectedFile = file; 
        	this.fireEvent(new ValueChangeEvent<File>(this,file,true));
        });

        tree.setSelectionMode(SelectionMode.SINGLE);

		return tree;
	}

	@Override
	protected void doSetValue(File value) {
		tree.select(value);		
	}

	/**
	 * Get the selected File
	 * 
	 * @return The selected File, can be a file or directory
	 */
	@Override
	public File getValue() {
		return selectedFile;
	}

}
