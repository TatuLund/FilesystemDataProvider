package org.vaadin.filesystemdataprovider.demo;

import java.io.File;

import javax.servlet.annotation.WebServlet;

import org.vaadin.filesystemdataprovider.FileTypeResolver;
import org.vaadin.filesystemdataprovider.FilesystemData;
import org.vaadin.filesystemdataprovider.FilesystemDataProvider;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Panel;
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

    	File rootFile = new File("C:/Users/Tatu/Documents");
    	FilesystemData root = new FilesystemData(rootFile);
    	FilesystemDataProvider fileSystem = new FilesystemDataProvider(root); 
        final Tree<File> tree = new Tree<>();
        tree.setDataProvider(fileSystem);

        tree.setItemIconGenerator(file -> {
        	return FileTypeResolver.getIcon(file);
        });
        
        final Panel layout = new Panel();
        layout.setSizeFull();
        layout.setContent(tree);
        setContent(layout);
    }
}
