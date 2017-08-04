package com.adobe.aem.social.journal.extensions;

import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Property;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

import com.adobe.cq.social.commons.comments.listing.CommentSocialComponentListProviderManager;
import com.adobe.cq.social.scf.ClientUtilities;
import com.adobe.cq.social.scf.QueryRequestInfo;
import com.adobe.cq.social.scf.SocialComponent;
import com.adobe.cq.social.scf.SocialComponentFactory;
import com.adobe.cq.social.scf.core.AbstractSocialComponentFactory;
import org.osgi.framework.Constants;

@Component(name = "Custom Journal Social Component Factory")
@Property(name = Constants.SERVICE_RANKING, intValue = 100, propertyPrivate = true)
@Service
public class CustomJournalEntryComponentFactory extends AbstractSocialComponentFactory implements SocialComponentFactory {

    private static final String RESOURCE_TYPE_ENTRY = "/apps/custom-blog/components/hbs/entry_topic";

    @Reference
    private CommentSocialComponentListProviderManager commentListProviderManager;

    @Override
    public SocialComponent getSocialComponent(final Resource resource) {
        try {
            return new CustomJournalEntryComponent(resource, this.getClientUtilities(resource.getResourceResolver()),commentListProviderManager);
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public SocialComponent getSocialComponent(final Resource resource, final SlingHttpServletRequest request) {
        try {
            return new CustomJournalEntryComponent(resource, this.getClientUtilities(request),this.getQueryRequestInfo(request),commentListProviderManager);
        } catch (RepositoryException e) {
            return null;
        }
    }
    
    @Override
    public SocialComponent getSocialComponent(Resource resource, ClientUtilities clientUtils, QueryRequestInfo listInfo) {
        try {
            return new CustomJournalEntryComponent(resource, clientUtils, listInfo,commentListProviderManager);
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
        return 100;
    }

    @Override
    public String getSupportedResourceType() {
        return RESOURCE_TYPE_ENTRY;
    }

}
