(function(root, $, CQ) {
    "use strict";
    root.ComponentGuide = {};



    function showLogin() {
        $(".login").removeClass("hidden");
        $(".logout").addClass("hidden");
    }

    function showLogout() {
        $(".login").addClass("hidden");
        $(".logout").removeClass("hidden");
    }

    function handleUserImpersonate(event) {
        event.preventDefault();
        var dataPayload = "originalURI=" + encodeURIComponent(window.location.pathname);
        dataPayload += "&action=impersonate";
        dataPayload += "&userId=";

        if ($(event.target).hasClass("login")) {
            if (event.target.form) {
                dataPayload += encodeURIComponent($(event.target.form).find("input").val());
                $.post("/libs/granite/ui/content/userproperties.impersonate.html", dataPayload).done(function() {
                    showLogout();
                });
            }
        } else {
            dataPayload += encodeURIComponent("-");
            $.post("/libs/granite/ui/content/userproperties.impersonate.html", dataPayload).done(function() {
                showLogin();
            });
        }
    }

    // Attach to the impersonation UI.
    $(function(){
        $("button.login").click(handleUserImpersonate);
        $("button.logout").click(handleUserImpersonate);
        $(".login .search-query").keyup(function(event) {
            var searchPath = "/libs/granite/ui/content/userproperties/self/form/items/impersonate/items/userpicker.userlist.html?impersonatesOnly=1&search="
            searchPath += encodeURIComponent($(event.target).val());
            var $targetForm = $(event.target.form);
            $.get(searchPath).done(function(data) {
                var $ul = $(data);
                var $menu = $targetForm.find(".dropdown-menu");
                $menu.empty();
                $ul.find("li").each(function(idx, object){
                    var $anchor = $('<a href="#"/>');
                    var $object = $(object);
                    var dataValue = $object.attr("data-value");
                    $anchor.append($object.children());
                    var $newli = $("<li/>");
                    $newli.attr("data-value", dataValue);
                    $newli.append($anchor);
                    $menu.append($newli);
                });
                $menu.find("a").click(function(event){
                    $targetForm.find("input").first().val($(event.target).closest("li").attr("data-value"));
                    $menu.hide();
                });
                $menu.show();
                $targetForm.find(".dropdown-toggle").trigger("click");
            });
        });
    });

    $(function() {
        $(".login-ui .dropdown-menu input, .login-ui .dropdown-menu label").click(function(event) {
            event.stopPropagation();
        });
        $(".login-ui .dropdown-menu form").submit(function(event) {
            event.preventDefault();
            var $form = $(event.target);
            $.post("/crx/de/j_security_check", $form.serialize()).done(function() {
                window.location.reload(true);
            });
        });
        $(".login-ui .logout").click(function(event) {
            $.post("/crx/de/logout.jsp").done(function() {
                window.location.reload(true);
            });
        });

    })
    $(function() {
        if (typeof CQ.WCM === "undefined") {
            CQ.shared.User.init();
            if (CQ.shared.User.data.userID != "anonymous") {
                $(".navbar .login").addClass("hidden");
                $(".navbar .logout").removeClass("hidden");
            } else {
                $(".navbar .login").removeClass("hidden");
                $(".navbar .logout").addClass("hidden");
            }
        }
    });
})(window, $CQ, CQ);
