package com.adobe.aem.scf.extensions;

import com.adobe.cq.social.forum.client.endpoints.ForumOperations;
import com.adobe.cq.social.scf.OperationException;
import com.adobe.cq.social.scf.SocialComponent;
import com.adobe.cq.social.scf.SocialComponentFactory;
import com.adobe.cq.social.scf.SocialComponentFactoryManager;
import com.adobe.cq.social.scf.SocialOperationResult;
import com.adobe.cq.social.scf.core.operations.AbstractSocialOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.servlets.post.PostOperation;



@Component(immediate = true)
@Service
@Property(name = PostOperation.PROP_OPERATION_NAME, value = "social:setIdeaStatus")
public class SetStatusOperation extends AbstractSocialOperation implements PostOperation{
    
    @Reference
    private ForumOperations forumService;
    
    @Reference
    private SocialComponentFactoryManager srf;

    @Override
    protected SocialOperationResult performOperation(SlingHttpServletRequest req) throws OperationException {
        final String statusToSet = req.getParameter("status");
        final Resource idea = req.getResource();
        final ValueMap props = idea.adaptTo(ValueMap.class);
        final String[] tags = props.get("cq:tags", new String[]{});
        final List<String> tagList = new ArrayList<String>();
        for(String tag: tags) {
            if(!tag.startsWith("acmeideas:")) {
                tagList.add(tag);
            }
        }
        tagList.add(statusToSet);
        Map<String,Object> updates = new HashMap<String,Object>();
        updates.put("cq:tags", tagList.toArray(new String[]{}));
        Resource updatedResource = forumService.update(req.getResource(), updates, null, req.getResourceResolver().adaptTo(Session.class));
        return new SocialOperationResult(this.getSocialComponentForResource(updatedResource, req), 200, updatedResource.getPath());
    }

    private SocialComponent getSocialComponentForResource(Resource newProject, SlingHttpServletRequest request) {
        if (newProject == null) {
            return null;
        }
        final SocialComponentFactory factory = this.srf.getSocialComponentFactory(newProject);
        return factory.getSocialComponent(newProject, request);
    }

}
