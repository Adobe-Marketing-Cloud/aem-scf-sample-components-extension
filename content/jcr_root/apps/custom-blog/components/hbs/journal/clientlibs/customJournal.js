(function($CQ, _, Backbone, SCF) {
	"use strict";

    var CustomBlog = SCF.Components["social/journal/components/hbs/journal"].Model;	
    var CustomBlogView = SCF.JournalView.extend({
		extractCommentData: function(e) {
            var form = this.getForm("new-comment");
            if (form === null || form.validate()) {
                var msg = this.getField("message");
                var subtitle = this.getField("subtitle");
                var tags = this.getField("tags");
                var data = _.extend(this.getOtherProperties(), {
                    "message": msg,
                    "subtitle": subtitle,
                    "tags": tags
                });
                if (!SCF.Session.get("loggedIn")) {
                    data.userIdentifier = this.getField("anon-name");
                    data.email = this.getField("anon-email");
                    data.url = this.getField("anon-web");
                }
                if (typeof this.files !== "undefined") {
                    data.files = this.files;
                }
                return data;
            } else {
                return false;
            }
        }
    });

    var CustomBlogTopic = SCF.BlogTopic.extend({
        getCustomProperties: function() {
            var customData = {
                subject: this.get("subject")
            };
            customData.subtitle = this.get("subtitle");
            if (this.has("isDraft")) {
                customData.isDraft = this.get("isDraft");
                var publishDate = this.get("publishDate");
                if (!_.isEmpty(publishDate)) {
                    customData.publishDate = publishDate;
                    customData.isScheduled = true;
                }
            }
            if (this.getConfigValue("usingPrivilegedUsers")) {
                var composedFor = this.get("composedFor");
                if (!_.isEmpty(composedFor)) {
                    customData.composedFor = composedFor;
                }
            }

            return customData;
        }
    });

    var CustomBlogTopicView = SCF.BlogTopicView.extend({
        edit: function(e) {
            this.$el.find(".scf-js-journal-comment-section").toggleClass("scf-is-hidden");
            SCF.TopicView.prototype.edit.call(this, e);
            this.$el.find(".scf-js-topic-details").hide();
            this.$el.find(".scf-js-topic-details-tags-editable").show();
            this.$el.find(".scf-comment-toolbar .scf-comment-edit").hide();

            var subject = this.model.get('subject');
            this.setField("editSubject", subject);
            this.focus("editSubject");

            var subtitle = this.model.get('subtitle');
            this.setField("editSubtitle", subtitle);

            if (!this.eventBinded) {
                this.bindDatePicker(e);
                this.eventBinded = true;
            }
        },
        getOtherProperties: function(isReply) {
            var subject = this.getField("editSubject").trim();
            var subtitle = this.getField("editSubtitle").trim();
            var tags = this.getField("editTags");
            var props = {
                'tags': tags
            };
            if (!isReply) {
                props["subject"] = subject;
                props["subtitle"] = subtitle;
            }
            var publishMode = $(this.$el.find(".scf-js-pubish-type > button > span")).text();
            var publishDate = null;
            if (!_.isEmpty(publishMode) && publishMode == $(this.$el.find(
                    ".scf-js-pubish-type > ul > li > a")[1])
                .text()) {
                props.isDraft = true;
            } else if (!_.isEmpty(publishMode) && publishMode == $(this.$el.find(
                    ".scf-js-pubish-type > ul > li > a")[2]).text()) {
                props.isDraft = true;
                props.isScheduled = true;
                props.publishDate = getDateTime(this.$el.find(
                        ".scf-js-event-basics-start-input").val(),
                    this.$el.find(".scf-js-event-basics-start-hour").val(), this.$el.find(
                        ".scf-js-event-basics-start-min").val(), this.$el.find(".scf-js-event-basics-start-time-ampm").val());
            } else {
                props.isDraft = false;
            }
            if (this.model.getConfigValue("usingPrivilegedUsers")) {
                var composedFor = this.getField("composedFor");
                if (!_.isEmpty(composedFor)) {
                    props.composedFor = composedFor;
                }
            }
            this.eventBinded = false;
            return props;
        }
    });

    SCF.registerComponent('/apps/custom-blog/components/hbs/entry_topic', CustomBlogTopic, CustomBlogTopicView);
	SCF.registerComponent('/apps/custom-blog/components/hbs/journal', CustomBlog, CustomBlogView);


})($CQ, _, Backbone, SCF);