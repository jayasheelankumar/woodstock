/*
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
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.renderkit.widget;

import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.util.LogUtil;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeJavascript;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeTemplates;

import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.faces.annotation.Renderer;
/**
 * This class renders the Hyperlink component
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Hyperlink", 
    componentFamily="com.sun.webui.jsf.Hyperlink"))
public class HyperlinkRenderer extends AnchorRenderer{

    /**
     * <p>Decode will determine if this component was the one that submitted the form.
     * It determines this by looking for the hidden field with the link's name 
     * appended with an "_submittedField"
     * If this hidden field contains the id of the component then this component submitted
     * the form.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     * @exception NullPointerException if <code>context</code> or
     * <code>component</code> is <code>null</code>
     */
    public void decode(FacesContext context, UIComponent component) {
        // Enforce NPE requirements in the Javadocs
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        
        String paramId = this.getSubmittedParameterId(context, component);
        String value = (String) 
            context.getExternalContext().getRequestParameterMap().get(paramId);

        if ((value == null) || !value.equals(component.getClientId(context))) {
            return;

        }
        //add the event to the queue so we know that a command happened.
        //this should automatically take care of actionlisteners and actions
        component.queueEvent(new ActionEvent(component));
    }   
      
      
    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        JSONArray json = new JSONArray();
        String url = (String)component.getAttributes().get("url");        
        if (url == null || url.length() == 0) {
            json.put(JavaScriptUtilities.getModuleName("widget.hyperlink"));   
        } else {
            json.put(JavaScriptUtilities.getModuleName("widget.anchor"));        
        }
        return json;
    }      
    
    /**
     * Set the attributes of the hyperlink element.
     * Override this method if custom properties need to be set for the 
     * component.
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json The JSON object
     */    
    protected void setAttributes(FacesContext context, UIComponent component,
            JSONObject json) throws IOException, JSONException {
        String url = (String)component.getAttributes().get("url");
        if (url == null || url.length() == 0) {
            UIComponent form = Util.getForm(context, component);
            String formClientId = form.getClientId(context);
            json.put("formId", formClientId);     
            JSONArray jarray = new JSONArray();
            JSONObject param;
            json.put("params", jarray);
            Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();

                if (kid instanceof UIParameter) {
                    String name = (String) kid.getAttributes().get("name"); //NOI18N
                    String value = (String) kid.getAttributes().get("value"); //NOI18N
                    if (name == null || value == null) {                           
                        continue;
                    }
                    jarray.put(name);
                    jarray.put(value);
                }
            }
        }
        super.setAttributes(context, component, json);     
    }
     
    
    /**
     * Returns the identifier for the parameter that corresponds to the hidden field
     * used to pass the value of the component that submitted the page.
     */
    protected String getSubmittedParameterId(FacesContext context, UIComponent component) {
        return component.getClientId(context) + "_submittedLink";
    }
          
    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        String url = (String)component.getAttributes().get("url");
        if (url == null || url.length() == 0) {
            return JavaScriptUtilities.getNamespace("hyperlink");            
        }
        return super.getWidgetType(context, component);
    }           
}
