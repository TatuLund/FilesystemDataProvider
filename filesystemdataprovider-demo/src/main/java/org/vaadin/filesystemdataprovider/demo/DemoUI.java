package org.vaadin.filesystemdataprovider.demo;

import java.io.File;
import java.util.Date;

import javax.servlet.annotation.WebServlet;

import org.vaadin.filesystemdataprovider.FileTypeResolver;
import org.vaadin.filesystemdataprovider.FilesystemData;
import org.vaadin.filesystemdataprovider.FilesystemDataProvider;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;

@Theme("demo")
@Title("FilesystemDataProvider Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

    	TabSheet tabSheet = new TabSheet();

    	// Demo 1: Use FilesystemData non-recursively
    	
    	// When using non-recursive mode, it is possible to browse entire file system
    	// DataProvider pre-fetches root directory and rest is loaded progressively
    	// lazily
    	File rootFile = new File("C:/");
    	FilesystemData root = new FilesystemData(rootFile, false);
    	FilesystemDataProvider fileSystem = new FilesystemDataProvider(root);     	
        final Tree<File> tree = new Tree<>();
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
        	if (!file.isDirectory()) {
        		Date date = new Date(file.lastModified());
        		Notification.show(file.getPath()+", "+date+", "+file.length());        		
        	}
        });
                
        final Panel layout1 = new Panel();
        layout1.setSizeFull();        
        layout1.setContent(tree);
    	tabSheet.addTab(layout1,"Non-recursive demo");

    	// Demo 2: Use FilesystemData recursively
    	
    	// Use path that points somewhere that makes sense
    	// Large directories are slow to construct and consume memory
    	File rootFile2 = new File("C:/Users/TatuL/Documents");
    	FilesystemData root2 = new FilesystemData(rootFile2);
    	FilesystemDataProvider fileSystem2 = new FilesystemDataProvider(root2);     	
        final Tree<File> tree2 = new Tree<>();
        tree2.setDataProvider(fileSystem2);

        tree2.setItemIconGenerator(file -> {
        	return FileTypeResolver.getIcon(file);
        });
        
        tree2.addItemClickListener(event -> {
        	File file = event.getItem();
        	if (!file.isDirectory()) {
        		Date date = new Date(file.lastModified());
        		Notification.show(file.getPath()+", "+date+", "+file.length());        		
        	}
        });
        
        final Panel layout2 = new Panel();
        layout2.setSizeFull();        
        layout2.setContent(tree2);
    	tabSheet.addTab(layout2,"Recursive demo");
    	    	
        setContent(tabSheet);
    }
}
