/*
 * Copyright 2013 Adobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function($CQ, _, Backbone, SCF) {
    "use strict";
     var Idea = SCF.Topic.extend({
         modelName: "IdeaModel",
         setStatus: function(status) {
             var error = _.bind(function(jqxhr, text, error) {
                this.log.error("error setting status " + error);
                this.trigger('idea:statuserror', {
                    'error': error
                });
            }, this);
            var success = _.bind(function(response) {
                this.set(response.response);
                this.trigger('idea:statusset', {
                    model: this
                });
                this.trigger(this.events.UPDATED, {
                    model: this
                });
            }, this);
            var postData = {
                'status': status,
                ':operation': 'social:setIdeaStatus'
            };
            $CQ.ajax(SCF.config.urlRoot + this.get('id') + SCF.constants.URL_EXT, {
                dataType: 'json',
                type: 'POST',
                xhrFields: {
                    withCredentials: true
                },
                data: this.addEncoding(postData),
                'success': success,
                'error': error
            });
         }
     });

	var IdeaView = SCF.TopicView.extend({
        viewName: "Idea",
        setStatus: function(e){
         	var status = this.getField("status");
         	this.model.setStatus(status);
            e.preventDefault();
     	}
	});

	SCF.Idea = Idea;       
    SCF.IdeaView = IdeaView;
    SCF.registerComponent('acme/components/ideation/post', SCF.Post, SCF.PostView);
    SCF.registerComponent('acme/components/ideation/topic', SCF.Idea, SCF.IdeaView);
    SCF.registerComponent('acme/components/ideation/forum', SCF.Forum, SCF.ForumView);
})($CQ, _, Backbone, SCF);