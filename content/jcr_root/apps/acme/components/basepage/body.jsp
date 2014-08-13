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
%><%@page session="false"%><%
%><%@ include file="/libs/foundation/global.jsp" %><%
%><body class="scf-guide"><sling:include path="./header"/>
<div class="cg container-fluid" style="margin-top: 50px">
	<div class="row-fluid">
        <div class="span12">
            <sling:include path="./navigation" resourceType="/apps/community-components/components/navigation"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <sling:include path="./content"/>
        </div>
	</div>
</div>
<cq:include path="clientcontext" resourceType="cq/personalization/components/clientcontext"/>
</body>