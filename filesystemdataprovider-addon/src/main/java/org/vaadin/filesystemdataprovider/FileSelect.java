package org.vaadin.filesystemdataprovider;

import java.io.File;
import java.util.Date;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;

/**
 * FileSelect is a simple single file/directory selector component
 * 
 * @author Tatu Lund
 * */
public class FileSelect extends CustomField<File> {
	private String filesText = "files";
	
    private Tree<File> tree = new Tree<>();
    private Panel content;
    
    private File rootFile;
	private File selectedFile = null;
	private String filter = null;
	private FilesystemData root = null;

	private static int GIGA = 1024*1024*1024;
	private static int MEGA = 1024*1024;
	private static int KILO = 1024;
	
	/**
	 * Constructor
	 * 
	 * @param rootFile The root directory where to browse
	 */
	public FileSelect(File rootFile) {
		this.rootFile = rootFile;
		content = createContent();
	}

	/**
	 * Alternative constructor with filter
	 * 
	 * @since 1.1.0
	 * 
	 * @param rootFile The root directory where to browse
	 * @param filter Set filter used for filename extension
	 */
	public FileSelect(File rootFile, String filter) {
		this.rootFile = rootFile;
		this.filter = filter;
		content = createContent();
	}

	@Override
	protected Component initContent() {
    	return content;
	}

	private Panel createContent() {
		if (filter != null) {
			root = new FilesystemData(rootFile, filter, false);
		} else {
			root = new FilesystemData(rootFile, false);
		}
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
        		if (size > GIGA) {
        			size = size / GIGA;
        			unit = "GB";
        		}
        		else if (size > MEGA) {
        			size = size / MEGA;
        			unit = "MB";
        		}
        		else if (size > KILO) {
        			size = size / KILO;
        			unit = "KB";
        		} else {
        			unit = "B";        			
        		}        			        		
        		desc = file.getName()+", "+date+", "+size+ " "+unit;        		
        	} else {
        		desc = root.getChildrenFromFilesystem(file).size()+" "+filesText;
        	}
        	return desc;
        });

        tree.setSelectionMode(SelectionMode.SINGLE);
        
        tree.addSelectionListener(event -> {
        	selectedFile = null;
        	event.getFirstSelectedItem().ifPresent(file -> {
        		selectedFile = file;
        		fireEvent(new ValueChangeEvent<File>(this,selectedFile,true));	
        	});
        });

        Panel panel = new Panel();
        panel.setSizeFull();
        panel.setContent(tree);
        
       	return panel;
	}

	@Override
	protected void doSetValue(File value) {
		tree.select(value);		
	}

	/**
	 * Set String used for "files" text, for localization.
	 * 
	 * @since 1.1.0
	 * 
	 * @param filesText String for "files" text
	 */
	public void setFilesText(String filesText) {
		this.filesText = filesText;
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
