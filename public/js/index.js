define([
    'jquery',
    'backbone',
], function ($, Backbone, uploadFiles) {
    'use strict';

    //Hiding the laoding img div once this script is loaded to avoid user clicking the overlays.
    $(document).ready(function(){
        var achCbView = $('#adminView');
        console.log("SESHU ---")
        achCbView.find(".loadingCase").remove();
    });

    var View = Backbone.View.extend({

        el: $('#adminView'),
/*
    Two types of overlay
        - Widget (white ones which slides to left)
        - Popup (dark ones which slides up)
*/
        events: {
            'click #submit': 'createContent'
/*
    Events related to upload - ENDS HERE
*/
        },
        createContent: function(){
            console.log("Inside createContent function");
        }

    });
    return View;
});
