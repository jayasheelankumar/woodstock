/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.component;

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The Link component is iused to insert information into the &lt;head&gt; 
 * element, such as links to external stylesheets.
 */
@Component(type = "com.sun.webui.jsf.Link", family = "com.sun.webui.jsf.Link",
displayName = "Link", tagName = "link",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_link",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_link_props")
public class Link extends UIComponentBase {

    /**
     * <p>Construct a new <code>Link</code>.</p>
     */
    public Link() {
        super();
        setRendererType("com.sun.webui.jsf.Link");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Link";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name = "id")
    @Override
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name = "rendered")
    @Override
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }
    /**
     * <p>Defines the character (charset) encoding of the target URL. Default 
     * value is "ISO-8859-1".</p>
     */
    @Property(name = "charset", displayName = "Charset", category = "Advanced",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.CharacterSetsEditor")
    private String charset = null;

    /**
     * <p>Defines the character (charset) encoding of the target URL. Default 
     * value is "ISO-8859-1".</p>
     */
    public String getCharset() {
        if (this.charset != null) {
            return this.charset;
        }
        ValueExpression _vb = getValueExpression("charset");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the character (charset) encoding of the target URL. Default 
     * value is "ISO-8859-1".</p>
     * @see #getCharset()
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
    /**
     * <p>Specifies the type of display device for which the referenced document 
     * is designed.  The media attribute is useful for specifying different 
     * stylesheets for print and viewing on a screen.  The default value is 
     * "screen".</p>
     */
    @Property(name = "media", displayName = "Media Type", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String media = null;

    /**
     * <p>Specifies the type of display device for which the referenced document 
     * is designed.  The media attribute is useful for specifying different 
     * stylesheets for print and viewing on a screen.  The default value is 
     * "screen".</p>
     */
    public String getMedia() {
        if (this.media != null) {
            return this.media;
        }
        ValueExpression _vb = getValueExpression("media");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the type of display device for which the referenced document 
     * is designed.  The media attribute is useful for specifying different 
     * stylesheets for print and viewing on a screen.  The default value is 
     * "screen".</p>
     * @see #getMedia()
     */
    public void setMedia(String media) {
        this.media = media;
    }
    /**
     * <p>Defines the relationship between the current document and the 
     * targeted document. Default is "stylesheet". Other possible values 
     * are described at w3.org.</p>
     */
    @Property(name = "rel", displayName = "Rel", category = "Appearance",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.HtmlLinkTypesEditor")
    private String rel = null;

    /**
     * <p>Defines the relationship between the current document and the 
     * targeted document. Default is "stylesheet". Other possible values 
     * are described at w3.org.</p>
     */
    public String getRel() {
        if (this.rel != null) {
            return this.rel;
        }
        ValueExpression _vb = getValueExpression("rel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "stylesheet";
    }

    /**
     * <p>Defines the relationship between the current document and the 
     * targeted document. Default is "stylesheet". Other possible values 
     * are described at w3.org.</p>
     * @see #getRel()
     */
    public void setRel(String rel) {
        this.rel = rel;
    }
    /**
     * <p>Specifies the MIME type of the target URL.  Default is: "text/css"</p>
     */
    @Property(name = "type", displayName = "Mime type", category = "Appearance")
    private String type = null;

    /**
     * <p>Specifies the MIME type of the target URL.  Default is: "text/css"</p>
     */
    public String getType() {
        if (this.type != null) {
            return this.type;
        }
        ValueExpression _vb = getValueExpression("type");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "text/css";
    }

    /**
     * <p>Specifies the MIME type of the target URL.  Default is: "text/css"</p>
     * @see #getType()
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * <p>The absolute or relative target URL of the resource.</p>
     */
    @Property(name = "url", displayName = "URL", category = "Appearance", isDefault = true,
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.SunWebUrlPropertyEditor")
    private String url = null;

    /**
     * <p>The absolute or relative target URL of the resource.</p>
     */
    public String getUrl() {
        if (this.url != null) {
            return this.url;
        }
        ValueExpression _vb = getValueExpression("url");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The absolute or relative target URL of the resource.</p>
     * @see #getUrl()
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * <p>Defines the ISO language code of the human language used in the target 
     * URL file. For example, valid values might be en, fr, es.</p>
     */
    @Property(name = "urlLang", displayName = "URL Language", category = "Advanced",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.LanguagesEditor")
    private String urlLang = null;

    /**
     * <p>Defines the ISO language code of the human language used in the target 
     * URL file. For example, valid values might be en, fr, es.</p>
     */
    public String getUrlLang() {
        if (this.urlLang != null) {
            return this.urlLang;
        }
        ValueExpression _vb = getValueExpression("urlLang");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the ISO language code of the human language used in the target 
     * URL file. For example, valid values might be en, fr, es.</p>
     * @see #getUrlLang()
     */
    public void setUrlLang(String urlLang) {
        this.urlLang = urlLang;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.charset = (String) _values[1];
        this.media = (String) _values[2];
        this.rel = (String) _values[3];
        this.type = (String) _values[4];
        this.url = (String) _values[5];
        this.urlLang = (String) _values[6];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[7];
        _values[0] = super.saveState(_context);
        _values[1] = this.charset;
        _values[2] = this.media;
        _values[3] = this.rel;
        _values[4] = this.type;
        _values[5] = this.url;
        _values[6] = this.urlLang;
        return _values;
    }
}
