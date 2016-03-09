package com.adobe.aem.scf.extensions;

import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

import com.adobe.cq.social.commons.comments.api.Comment;
import com.adobe.cq.social.commons.comments.api.CommentSocialComponentFactory;
import com.adobe.cq.social.commons.comments.listing.CommentSocialComponentListProviderManager;
import com.adobe.cq.social.forum.client.api.TopicSocialComponentFactory;
import com.adobe.cq.social.scf.ClientUtilities;
import com.adobe.cq.social.scf.QueryRequestInfo;
import com.adobe.cq.social.scf.SocialComponent;
import com.adobe.cq.social.scf.SocialComponentFactory;
import com.adobe.cq.social.scf.core.AbstractSocialComponentFactory;

/**
 * CustomCommentFactory extends the default CommentSocialComponentFactory to leverage the default comment social
 * component implementation. This makes it possible to only make changes needed for customization without having to
 * implement all the APIs specified by {@link Comment}.
 */
@Component(name = "Idea Social Component Factory")
@Service
public class IdeaSocialComponentFactory extends AbstractSocialComponentFactory implements SocialComponentFactory {
    
    @Reference
    private CommentSocialComponentListProviderManager commentListProviderManager;

    @Override
    public SocialComponent getSocialComponent(final Resource resource) {
        try {
            return new IdeaSocialComponent(resource, this.getClientUtilities(resource.getResourceResolver()),commentListProviderManager);
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public SocialComponent getSocialComponent(final Resource resource, final SlingHttpServletRequest request) {
        try {
            return new IdeaSocialComponent(resource, this.getClientUtilities(request),this.getQueryRequestInfo(request),commentListProviderManager);
        } catch (RepositoryException e) {
            return null;
        }
    }
    
    @Override
    public SocialComponent getSocialComponent(Resource resource, ClientUtilities clientUtils, QueryRequestInfo listInfo) {
        try {
            return new IdeaSocialComponent(resource, clientUtils, listInfo,commentListProviderManager);
        } catch (RepositoryException e) {
            return null;
        }
    }


    /*
     * (non-Javadoc)
     * @see com.adobe.cq.social.commons.client.api.AbstractSocialComponentFactory#getPriority() Set the priority to a
     * number greater than 0 to override the default SocialComponentFactory for comments.
     */
    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public String getSupportedResourceType() {
        return "acme/components/ideation/topic";
    }

}
