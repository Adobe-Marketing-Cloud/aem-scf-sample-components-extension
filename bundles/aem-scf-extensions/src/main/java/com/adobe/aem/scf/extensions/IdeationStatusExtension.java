package com.adobe.aem.scf.extensions;

import com.adobe.cq.social.forum.client.api.Post;
import com.adobe.cq.social.forum.client.endpoints.ForumOperationExtension;
import com.adobe.cq.social.forum.client.endpoints.ForumOperationExtension.ForumOperation;
import com.adobe.cq.social.scf.Operation;
import com.adobe.cq.social.scf.OperationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;


@Component(name = "Ideation Status Extension", immediate = true, metatype = true)
@Service
public class IdeationStatusExtension implements ForumOperationExtension {

    @Override
    public void beforeAction(Operation op, Session sessionUsed, Resource requestResource, Map<String, Object> props)
        throws OperationException {
        if (ResourceUtil.isA(requestResource, "acme/components/ideation/forum")) {
            List<String> tags = new ArrayList<String>();
            if (props.containsKey("tags")) {
                final Object v = props.get("tags");
                if (!(v instanceof String[])) {
                    if (v instanceof String) {
                        tags.add((String) v);
                    }
                } else {
                    for (String t : (String[]) v) {
                        tags.add(t);
                    }
                }
            }
            tags.add("acmeideas:new");
            props.put("tags", tags.toArray(new String[]{}));
        }
    }

    @Override
    public String getName() {
        return "ideation status";
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public List<ForumOperation> getOperationsToHookInto() {
        return Arrays.asList(ForumOperation.CREATE);
    }

    @Override
    public void afterAction(Operation oprtn, Session sn, Post t, Map<String, Object> map) throws OperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
