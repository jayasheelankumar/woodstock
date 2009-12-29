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
package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

/**
 * The Breadcrumbs component is used to display a list of hyperlinks, as an aid to
 * navigation.
 *
 * <p>Breadcrumbs show the user's location within an application, and the physical 
 * or logical path to the current page. The user can click hyperlinks in the
 * breadcrumbs to navigate to other, previously visited pages in the application.
 *
 * <p>A breadcrumb's hyperlinks may be specified in one of two ways:
 * <ul>
 * <li>Directly, as children of the breadcrumbs component.
 * <li>Indirectly via the <code>pages</code> attribute. The value must a value binding
 * expression that identifies an array of <code>com.sun.webui.jsf.Hyperlink</code> components. Hyperlinks 
 * specified in this manner are referred to as "bound" hyperlinks. Bound hyperlinks must
 * be uniquely identifiable by the value of their <code>id</code> attribute, and this attribute
 * must not be null. Bound hyperlinks should not be part of a view tree, and the value of
 * their <code>parent</code> property should be null.
 * </ul>
 * You should provide either child hyperlinks or bound hyperlinks, but not both.
 * If both are provided, bound hyperlinks are rendered, and any child hyperlinks are 
 * ignored. Non-hyperlink children are also ignored.
 *
 * <p>Bound hyperlinks are treated as child hyperlinks for all phases of request processing,
 * except that they are not asked to save or restore their state.
 *
 * <p>The breadcrumbs component has an <code>immediate</code> property, the default
 * value of which is <code>true</code>. If the breadcrumbs is immediate, all action events
 * generated by child or bound hyperlinks will be treated as though they too were immediate,
 * whether or not the source hyperlink is immediate. If the breadcrumbs is not immediate,
 * action events will be processed according to whether their source hyperlink is
 * immediate or not.
 */
@Component(type = "com.sun.webui.jsf.Breadcrumbs", family = "com.sun.webui.jsf.Breadcrumbs",
displayName = "Breadcrumbs", tagName = "breadcrumbs",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_breadcrumbs",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_breadcrumbs_props")
public class Breadcrumbs extends UIComponentBase implements NamingContainer {

    public Breadcrumbs() {
        super();
        setRendererType("com.sun.webui.jsf.Breadcrumbs");
    }

    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Breadcrumbs";
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
     * An array of zero or more Hyperlink components that constitute the current
     * path. If this property is not null, any child Hyperlinks will be ignored.
     * The hyperlinks must have non-null, unique <code>id</code>s.
     */
    @Property(name = "pages", displayName = "Pages", category = "Behavior",
    editorClassName = "com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    private com.sun.webui.jsf.component.Hyperlink[] pages = null;

    /**
     * Get the array of zero or more Hyperlink components that constitute the current
     * path.
     */
    public com.sun.webui.jsf.component.Hyperlink[] getPages() {
        if (this.pages != null) {
            return this.pages;
        }
        ValueExpression _vb = getValueExpression("pages");
        if (_vb != null) {
            return (com.sun.webui.jsf.component.Hyperlink[]) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the array of zero or more Hyperlink components that constitute the current
     * path.
     */
    public void setPages(com.sun.webui.jsf.component.Hyperlink[] pages) {
        this.pages = pages;
    }
    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name = "style", displayName = "CSS Style(s)", category = "Appearance",
    editorClassName = "com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * Returns the CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     * @see #setStyle
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     * @see #getStyle
     */
    public void setStyle(String style) {
        this.style = style;
    }
    /**
     * CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name = "styleClass", displayName = "CSS Style Class(es)", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * Get the CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     * @see #setStyleClass
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    /**
     * Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.
     */
    @Property(name = "tabIndex", displayName = "Tab Index", category = "Accessibility")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * Get the position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.
     * @see #setTabIndex
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Set the position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }
    /**
     * Indicates whether actions should be handled immediately when generated by 
     * hyperlinks that are part of this component. The default value is true.
     */
    @Property(displayName = "Immediate", category = "Advanced")
    private boolean immediate = true;
    private boolean immediate_set = false;

    public boolean isImmediate() {
        if (this.immediate_set) {
            return this.immediate;
        }
        ValueExpression _vb = getValueExpression("immediate");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
        this.immediate_set = true;
    }
    /**
     * Indicates whether the component and its child components should be viewable 
     * by the user in the rendered HTML page. 
     */
    @Property(name = "visible", displayName = "Visible", category = "Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * Returns true if this component and its child components should be viewable
     * by the user in the rendered HTML page.
     * @see #setVisible
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * Returns true if this component and its child components should be viewable
     * by the user in the rendered HTML page.
     * @see #isVisible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    @Override
    public void processDecodes(FacesContext context) {
        if (this.isRendered()) {
            if (this.getPages() != null) {
                for (Hyperlink hyperlink : this.getPages()) {
                    UIComponent parent = hyperlink.getParent();
                    hyperlink.setParent(this);
                    hyperlink.processDecodes(context);
                    hyperlink.setParent(parent);
                }
            } else {
                super.processDecodes(context);
            }
        }
    }

    @Override
    public void processValidators(FacesContext context) {
        if (this.isRendered()) {
            if (this.getPages() != null) {
                for (Hyperlink hyperlink : this.getPages()) {
                    UIComponent parent = hyperlink.getParent();
                    hyperlink.setParent(this);
                    hyperlink.processValidators(context);
                    hyperlink.setParent(parent);
                }
            } else {
                super.processValidators(context);
            }
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if (this.isRendered()) {
            if (this.getPages() != null) {
                for (Hyperlink hyperlink : this.getPages()) {
                    UIComponent parent = hyperlink.getParent();
                    hyperlink.setParent(this);
                    hyperlink.processUpdates(context);
                    hyperlink.setParent(parent);
                }
            } else {
                super.processUpdates(context);
            }
        }
    }

    @Override
    public void queueEvent(FacesEvent event) {
        if (this.isImmediate()) {
            // If this breadcrumbs component is immediate, then treat all action
            // events generated by all children hyperlinks, or all hyperlinks bound
            // to the pages property, as though they were immediate.
            if (event instanceof ActionEvent) {
                event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            }
        }
        super.queueEvent(event);
    }

    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object[] _values = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        if (_values[1] != null) {
            Object[] _pageValues = (Object[]) _values[1];
            this.pages = new Hyperlink[_pageValues.length];
            for (int i = 0; i < _pageValues.length; i++) {
                this.pages[i] = new Hyperlink();
                this.pages[i].restoreState(_context, _pageValues[i]);
            }
        }
        this.style = (String) _values[2];
        this.styleClass = (String) _values[3];
        this.tabIndex = ((Integer) _values[4]).intValue();
        this.tabIndex_set = ((Boolean) _values[5]).booleanValue();
        this.immediate = ((Boolean) _values[6]).booleanValue();
        this.immediate_set = ((Boolean) _values[7]).booleanValue();
        this.visible = ((Boolean) _values[8]).booleanValue();
        this.visible_set = ((Boolean) _values[9]).booleanValue();
    }

    @Override
    public Object saveState(FacesContext _context) {
        Object[] _values = new Object[10];
        _values[0] = super.saveState(_context);
        if (this.pages != null) {
            Object[] _pageValues = new Object[this.pages.length];
            for (int i = 0; i < this.pages.length; i++) {
                _pageValues[i] = this.pages[i].saveState(_context);
            }
            _values[1] = _pageValues;
        }
        _values[2] = this.style;
        _values[3] = this.styleClass;
        _values[4] = new Integer(this.tabIndex);
        _values[5] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.immediate ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.immediate_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
