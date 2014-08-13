<%--
  Copyright 2013 Adobe

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@taglib prefix="personalization" uri="http://www.day.com/taglibs/cq/personalization/1.0" %><%
%><%@page session="false"
      import="javax.jcr.Session,
          org.apache.sling.api.resource.ResourceResolver,
        com.day.cq.wcm.api.WCMMode,
        com.day.cq.personalization.UserPropertiesUtil"%><%
  final Page rootGuidePage = currentPage.getAbsoluteParent(2);
  Session session = resourceResolver.adaptTo(Session.class);
  boolean isImpersonated = (session != null && session.getAttribute(ResourceResolver.USER_IMPERSONATOR) != null);
  boolean isAuthor = (WCMMode.fromRequest(request) != WCMMode.DISABLED);
%>
<div class="header-bar" style="background-color:#fff;">
    <div class="login-ui" style="background-image: none;">
        <span class="head-logo">ACME</span>
            <button class="btn pull-right logout hidden" type="submit">Logout</button>
      <div class="btn-group pull-right">
                <a class="btn dropdown-toggle login" data-toggle="dropdown" href="#">Login</a>
                <ul class="dropdown-menu">
          <form>
                        <label for="j_username">Username:</label>
                        <input name="j_username"></input>
                        <label for="j_password">Password:</label>
                      <input name="j_password" type="password"></input>
                    <input name="j_validate" value="true" class="hidden"/>
                    <button type="submit" class="btn">Submit</button>
                    </form>
                </ul>
          </div>
  </div>
</div>
