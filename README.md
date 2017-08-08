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


Customized journal/blog
=======================

This extension adds a subtitle property to the blog, which gets displayed under the title in blog.
Things to take care of while using this project :
1. Add a property "subtitle" in your author and publish instance's /system/console/configMgr/com.adobe.cq.social.journal.client.endpoints.impl.JournalOperationsService
2. Add the new journal from design dialog in your site and you are good to go. Please make sure, you have added relevant configurations such allow Rich Text Editor, allow following etc by editing the site.
