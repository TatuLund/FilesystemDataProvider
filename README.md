[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/filesystem-dataprovider-add-on)
[![Stars on Vaadin Directory](https://img.shields.io/vaadin-directory/star/filesystem-dataprovider-add-on.svg)](https://vaadin.com/directory/component/filesystem-dataprovider-add-on)

# MyComponent Add-on for Vaadin 8

FilesystemDataProvider is a data model add-on for Vaadin 8 providing hierarchical
data of the filesystem, and it can be used to supply it to Tree and TreeGrid components.
This is fully server side add-on, hence using it does not require widgetset recompilation.
The inspiration of this add-on is to provide similar functionality than Vaadin 7's 
built in FilesystemContainer had. Hence it is useful for Vaadin 7 -> 8 migration projects.

## Online demo

Try the add-on demo at TBD

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to http://vaadin.com/addon/filesystemdataprovider

## Building and running demo

git clone https://github.com/TatuLund/FilesystemDataProvider.git
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for filesystemdataprovider-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your filesystemdataprovider-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the filesystemdataprovider-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/filesystemdataprovider-demo/ to see the application.


## Release notes

### Version 0.3.0
- Made lazy loading automatic in non-recursive mode

### Version 0.2.0
- Added JavaDocs
- Minor fixes and improvements
- Updated README.md

### Version 0.1.0
- First release

## Roadmap

This component is developed as a hobby with no public roadmap or any guarantees of upcoming releases. Feel free to add improvement ideas to issue tracker.

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

FilesystemDataProvider is written by Tatu Lund

