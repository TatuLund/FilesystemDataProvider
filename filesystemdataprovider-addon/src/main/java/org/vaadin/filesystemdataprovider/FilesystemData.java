package org.vaadin.filesystemdataprovider;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.data.TreeData;

public class FilesystemData extends TreeData<File> {

    private FilenameFilter filter = null;
	private boolean recursive;
	
	public FilesystemData(File root) {
		super();
        this.addRootItems(root);
        constructFileSystem(root);
	}

	public FilesystemData(File root, boolean recursive) {
		super();
        this.addRootItems(root);
        setRecursive(recursive);
        if (recursive) {
        	constructFileSystem(root);
        } else {
        	if (root.isDirectory()) {
        		this.addItems(root, getChildrenFromFilesystem(root));
        	}
        }        
	}
	
	public FilesystemData(File root, String filter, boolean recursive) {
		super();
        this.addRootItems(root);
        setRecursive(recursive);
        this.filter = new FileExtensionFilter(filter);
        if (recursive) {
        	constructFileSystem(root);
        } else {
        	if (root.isDirectory() && !Files.isSymbolicLink(root.toPath())) {
        		this.addItems(root, getChildrenFromFilesystem(root));
        	}
        }        
	}
	
	private void constructFileSystem(File root) {
        if (root.isDirectory()) {
        	List<File> files = getChildrenFromFilesystem(root);
        	addItems(root, files);
        	for (File file : files) {
        		if (file.isDirectory()) constructFileSystem(file);
        	}
        }        		
	}
	
    public List<File> getChildrenFromFilesystem(File item) {
        if (!item.isDirectory()) {
        	return new LinkedList<File>();
        }
        File[] f;
        if (filter != null) {
            f = item.listFiles(filter);
        } else {
            f = item.listFiles();
        }

        if (f == null) {
            return new LinkedList<File>();
        }

        final List<File> l = Arrays.asList(f);
        Collections.sort(l);

        return l;
    }

    public class FileExtensionFilter implements FilenameFilter, Serializable {

        private final String filter;

        /**
         * Constructs a new FileExtensionFilter using given extension.
         *
         * @param fileExtension
         *            the File extension without the separator (dot).
         */
        public FileExtensionFilter(String fileExtension) {
            filter = "." + fileExtension;
        }

        /**
         * Allows only files with the extension and directories.
         *
         * @see java.io.FilenameFilter#accept(File, String)
         */
        @Override
        public boolean accept(File dir, String name) {
            if (name.endsWith(filter)) {
                return true;
            }
            return new File(dir, name).isDirectory();
        }

    }
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public boolean isRecursive() {
        return recursive;
    }
  
    public void setFilter(FilenameFilter filter) {
        this.filter = filter;
    }

    public void setFilter(String extension) {
        filter = new FileExtensionFilter(extension);
    }
	
}
