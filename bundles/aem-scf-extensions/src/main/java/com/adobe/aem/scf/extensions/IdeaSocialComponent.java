package com.adobe.aem.scf.extensions;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;

import com.adobe.cq.social.commons.client.api.ClientUtilities;
import com.adobe.cq.social.commons.client.api.QueryRequestInfo;
import com.adobe.cq.social.commons.comments.listing.CommentSocialComponentListProviderManager;
import com.adobe.cq.social.forum.client.api.AbstractPost;
import com.adobe.cq.social.forum.client.api.ForumConfiguration;
import com.adobe.cq.social.forum.client.api.Post;

public class IdeaSocialComponent extends AbstractPost<ForumConfiguration> implements Post<ForumConfiguration> {
    
    private Tag statusTag;
    private List<Tag> tags;
    
    /**
     * Construct a comment for the specified resource and client utilities.
     * @param resource the specified resource
     * @param clientUtils the client utilities instance
     * @param commentListProviderManager list manager to use for listing content
     * @throws RepositoryException if an error occurs
     */
    public IdeaSocialComponent(final Resource resource, final ClientUtilities clientUtils,
        final CommentSocialComponentListProviderManager commentListProviderManager) throws RepositoryException{
        super(resource, clientUtils, commentListProviderManager);
        filterTags();
    }
    
    /**
     * Constructor of a comment.
     * @param resource the specified {@link com.adobe.cq.social.commons.Comment}
     * @param clientUtils the client utilities instance
     * @param queryInfo the query info.
     * @param commentListProviderManager list manager to use for listing content
     * @throws RepositoryException if an error occurs
     */
    public IdeaSocialComponent(final Resource resource, final ClientUtilities clientUtils,
        final QueryRequestInfo queryInfo, final CommentSocialComponentListProviderManager commentListProviderManager)
        throws RepositoryException {
        super(resource, clientUtils, queryInfo, commentListProviderManager);
        filterTags();
    }

    public IdeaSocialComponent(final Resource resource, final ClientUtilities clientUtils, final QueryRequestInfo queryInfo,
        final Resource latestPost, final int numReplies,
        final CommentSocialComponentListProviderManager listProviderManager) throws RepositoryException {
        super(resource, clientUtils, queryInfo, latestPost, numReplies, listProviderManager);
        filterTags();
    }
    
    @Override
    public List<com.adobe.cq.social.commons.comments.api.Comment.Tag> getTags() {
       return this.tags;
    }
    
    private void filterTags() {
        this.tags = new ArrayList<Tag>();
        for(Tag tag:super.getTags()) {
            if(tag.getTagId().startsWith("acmeideas:")) {
                statusTag = tag;
            } else {
                tags.add(tag);
            }
        }
    }
    
    public String getStatus() {
        return null == this.statusTag ? null : this.statusTag.getTitle();
    }
    
    public boolean isNewIdea() {
        if(this.statusTag != null && "acmeideas:new".equals(statusTag.getTagId())){
            return true;
        }
        return false;
    }
    
    public boolean isAccepted() {
        if(this.statusTag != null && "acmeideas:accepted".equals(statusTag.getTagId())){
            return true;
        }
        return false;
    }
    
    public boolean isReviewed() {
        if(this.statusTag != null && "acmeideas:reviewed".equals(statusTag.getTagId())){
            return true;
        }
        return false;
    }
    
    public boolean isUnderReview() {
        if(this.statusTag != null && "acmeideas:under-review".equals(statusTag.getTagId())){
            return true;
        }
        return false;
    }
    
    public boolean isLaunched() {
        if(this.statusTag != null && "acmeideas:launched".equals(statusTag.getTagId())){
            return true;
        }
        return false;
    }

}
