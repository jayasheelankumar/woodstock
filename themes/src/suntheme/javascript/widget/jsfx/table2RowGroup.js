/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

webui.@THEME_JS@.dojo.provide("webui.@THEME_JS@.widget.jsfx.table2RowGroup");

webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.json");
webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.jsfx.common");
webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.jsfx.dynaFaces");
webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.table2RowGroup");

/**
 * @class This class contains functions to obtain data asynchronously using JSF
 * Extensions as the underlying transfer protocol.
 * @static
 */
webui.@THEME_JS@.widget.jsfx.table2RowGroup = {
    /**
     * This function is used to process scroll events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {int} row The first row to be rendered.
     * @return {boolean} true if successful; otherwise, false.
     */
    processScrollEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Dynamic Faces requires a DOM node as the source property.
        var domNode = document.getElementById(props.id);

        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: "none",
            render: props.id,
            replaceElement: webui.@THEME_JS@.widget.jsfx.table2RowGroup.scrollCallback,
            xjson: {
                id: props.id,
                event: "scroll",
                first: props.first
            }
        });
        return true;
    },
    
    /**
     * This function is used to update widgets.
     *
     * @param {String} elementId The HTML element Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param {Object} xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     * @return {boolean} true if successful; otherwise, false.
     */
    scrollCallback: function(id, content, closure, xjson) {
        
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = webui.@THEME_JS@.json.parse(content);

        // Reject duplicate AJAX requests.
        var widget = webui.@THEME_JS@.dijit.byId(id);
        if (widget.first != xjson.first) {
            return false;
        }        
        // Add rows.
        widget.addRows(props.rows);

        // Publish an event for custom AJAX implementations to listen for.
        webui.@THEME_JS@.dojo.publish(
            webui.@THEME_JS@.widget.table2RowGroup.event.scroll.endTopic, [props]);
        return true;
    }
};
    
// Listen for Dojo Widget events.
webui.@THEME_JS@.dojo.subscribe(webui.@THEME_JS@.widget.table2RowGroup.event.refresh.beginTopic,
    webui.@THEME_JS@.widget.jsfx.common, "processRefreshEvent");
webui.@THEME_JS@.dojo.subscribe(webui.@THEME_JS@.widget.table2RowGroup.event.scroll.beginTopic,
    webui.@THEME_JS@.widget.jsfx.table2RowGroup, "processScrollEvent");
webui.@THEME_JS@.dojo.subscribe(webui.@THEME_JS@.widget.table2RowGroup.event.pagination.next.beginTopic,
    webui.@THEME_JS@.widget.jsfx.table2RowGroup, "processScrollEvent");
