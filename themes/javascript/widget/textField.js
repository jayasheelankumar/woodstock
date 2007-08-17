//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

dojo.provide("webui.@THEME@.widget.textField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.fieldBase");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.textField = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.textField.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_textField_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_textField_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_textField_event_state_begin",
        endTopic: "webui_@THEME@_widget_textField_event_state_end"
    },

    /**
     * This closure is used to process submit events.
     */
    submit: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_textField_event_submit_begin",
        endTopic: "webui_@THEME@_widget_textField_event_submit_end"
    },

    /**
     * This closure is used to process validation events.
     */
    validation: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_textField_event_validation_begin",
        endTopic: "webui_@THEME@_widget_textField_event_validation_end",
    
        /**
         * Process validation event.
         *
         * This function interprets an event (one of onXXX events, such as onBlur,
         * etc) and extracts data needed for subsequent Ajax request generation. 
         * Specifically, the widget id that has generated the event. If widget
         * id is found, publishBeginEvent is called with extracted data.
         *
         * @param event Event generated by the widget.
         */
        processEvent: function(event) {
            if (event == null) {
                return false;
            }

            var widget = dojo.widget.byId(event.currentTarget.parentNode.id);
            if (widget) {
                // Include default AJAX implementation.
                widget.ajaxify();
            }

            // Publish an event for custom AJAX implementations to listen for.
            dojo.event.topic.publish(
                webui.@THEME@.widget.textField.event.validation.beginTopic, {
                    id: event.currentTarget.parentNode.id
                });
        }
    }
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.textField.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.textField.superclass.fillInTemplate.call(this, props, frag);

    // Set public functions.
    this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); }
    
    // Set events.
    if (this.autoValidate == true) {
        // Generate the following event ONLY when 'autoValidate' == true.
        dojo.event.connect(this.fieldNode, "onblur", 
            webui.@THEME@.widget.textField.event.validation.processEvent);
    }
    return true;
}

/**
 * Helper function to obtain HTML input element class names.
 */
webui.@THEME@.widget.textField.getInputClassName = function() {          
    
    //readOnly style
    if (this.fieldNode.readOnly)
        return this.widget.getClassName("TEXT_FIELD_READONLY", "");

    //invalid style
    var validStyle =  (this.valid == false) 
        ? " " + this.widget.getClassName("TEXT_FIELD_INVALID", "")
        : " " + this.widget.getClassName("TEXT_FIELD_VALID", "");
    
    // Set default style.    
    return (this.disabled == true)
        ? this.widget.getClassName("TEXT_FIELD_DISABLED", "") 
        : this.widget.getClassName("TEXT_FIELD", "") + validStyle;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.textField.getProps = function() {
    var props = webui.@THEME@.widget.textField.superclass.getProps.call(this);

    // Set properties.
    if (this.autoValidate != null) { props.autoValidate = this.autoValidate; }
    
    return props;
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.textField, webui.@THEME@.widget.fieldBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.textField, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.textField.fillInTemplate,
    getInputClassName: webui.@THEME@.widget.textField.getInputClassName,
    getProps: webui.@THEME@.widget.textField.getProps,
    submit: webui.@THEME@.widget.widgetBase.event.submit.processEvent,

    // Set defaults.
    event: webui.@THEME@.widget.textField.event,
    widgetType: "textField"
});
