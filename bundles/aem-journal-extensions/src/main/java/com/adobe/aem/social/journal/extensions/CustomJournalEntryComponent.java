package com.adobe.aem.social.journal.extensions;

import java.text.SimpleDateFormat;
import java.util.Map;
import javax.jcr.RepositoryException;

import com.adobe.cq.social.commons.comments.listing.CommentSocialComponentList;
import com.adobe.cq.social.forum.client.api.AbstractPost;
import com.adobe.cq.social.journal.client.api.JournalEntryComment;
import com.adobe.cq.social.ugc.api.PathConstraintType;
import com.adobe.cq.social.commons.comments.listing.CommentSocialComponentListProviderManager;
import com.adobe.cq.social.scf.ClientUtilities;
import com.adobe.cq.social.scf.QueryRequestInfo;
import com.adobe.cq.social.journal.client.api.Journal;
import com.adobe.cq.social.scf.core.DefaultResourceID;
import com.adobe.cq.social.scf.core.ResourceID;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.commons.lang3.StringUtils;



public class CustomJournalEntryComponent extends AbstractPost implements JournalEntryComment {
    private static final String ENTRY_HTML_SUFFIX = ".entry.html";
    private static final String RESOURCE_TYPE_ENTRY = "/apps/custom-blog/components/hbs/entry_topic";
    private String subtitle = null;
    /**
     *
     */
    public CustomJournalEntryComponent(final Resource resource, final ClientUtilities clientUtils,
                                       final CommentSocialComponentListProviderManager listProviderManager) throws RepositoryException {
        super(resource, clientUtils, QueryRequestInfo.DEFAULT_QUERY_INFO_FACTORY.create(), listProviderManager);
        final CommentSocialComponentList list = (CommentSocialComponentList) getItems();
        list.setPathConstraint(PathConstraintType.IsChildNode);
        setSubtitle();
    }

    /**
     *
     */
    public CustomJournalEntryComponent(final Resource resource, final ClientUtilities clientUtils,
                                       final QueryRequestInfo queryInfo, final CommentSocialComponentListProviderManager listProviderManager)
            throws RepositoryException {
        super(resource, clientUtils, queryInfo, listProviderManager);
        final CommentSocialComponentList list = (CommentSocialComponentList) getItems();
        list.setPathConstraint(PathConstraintType.IsChildNode);
        setSubtitle();
    }

    private void setSubtitle() {
        Map props = this.getProperties();
        if (props.containsKey("subtitle")) {
            this.subtitle = props.get("subtitle").toString();
        }
    }

    @Override
    public String getFriendlyUrl() {
        final Resource journal = getJournal(this.getResource());
        if (journal != null) {
            final String pagePath = clientUtils.getSocialUtils().getContainingPage(journal).getPath();
            ResourceID urlId = this.id;
            if (!isTopLevel()) {
                final Resource topicResource = getJournalEntry(this.getResource());
                urlId = new DefaultResourceID(topicResource);
            }
            // Adding a date to the Blog URL to make it SEO compatible
            final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd");
            final String creationDateString = dateFormat.format(this.getCreated().getTime());
            return clientUtils.externalLink(pagePath, false) + ENTRY_HTML_SUFFIX + "/" + creationDateString + "/"
                    + StringUtils.substringAfterLast(urlId.getResourceIdentifier(), "/") + ".html";
        } else {
            return null;
        }
    }

    /**
     * Find the Journal resource for the specified resource.
     * @param resource the specified resource.
     * @return Resource
     */
    private static Resource getJournal(final Resource resource) {
        if (ResourceUtil.isA(resource, Journal.RESOURCE_TYPE_COMMENT)) {
            return getParent(getJournalEntry(resource));
        } else if (ResourceUtil.isA(resource, Journal.RESOURCE_TYPE_ENTRY )|| ResourceUtil.isA(resource, RESOURCE_TYPE_ENTRY)) {
            return getParent(resource);
        } else if (ResourceUtil.isA(resource, Journal.RESOURCE_TYPE)) {
            return resource;
        } else {
            return null;
        }
    }

    /**
     * Find the Journal Entry resource for the specified resource.
     * @param resource the specified resource.
     * @return Resource
     */
    private static Resource getJournalEntry(final Resource resource) {
        if (ResourceUtil.isA(resource, Journal.RESOURCE_TYPE_COMMENT)) {
            Resource parent = resource;
            do {
                parent = getParent(parent);
                if (ResourceUtil.isA(parent, Journal.RESOURCE_TYPE_ENTRY)) {
                    return parent;
                }
            } while (parent != null && !parent.isResourceType("cq:page"));
            return null;
        } else if (ResourceUtil.isA(resource, Journal.RESOURCE_TYPE_ENTRY)) {
            return resource;
        } else {
            return null;
        }
    }

    /**
     * Find the parent resource for the specified resource.
     * @param resource the specified resource.
     * @return Resource
     */
    private static Resource getParent(final Resource resource) {
        if (resource == null) {
            return null;
        }
        return resource.getParent();
    }

    public String getSubtitle() {
        return this.subtitle;
    }
}
