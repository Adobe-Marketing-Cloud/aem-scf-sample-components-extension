aem-scf-sample-components-extension
===================================

Sample package for AEM Social Communities - Social Component Framework (SCF).  Examples of extending Communities components to build a new social component.

building the sample
===================

* change directory to the root of the repository aem-scf-sample-components-extension/
* run *mvn clean install*
* a successful build should create a bundle artifact in *bundles/aem-scf-extensions* and a package artifact in *content/target*

installing the sample
=====================

* use the package manager at http://[server]/crx/packmgr/index.jsp and upload the zip file found at *content/target*
* install the package
* check out the ideation component at http://[server]/content/acme/en/ideas.html

 


