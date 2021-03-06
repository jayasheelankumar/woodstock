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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import com.sun.webui.jsf.component.AlarmStatus;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Render an instance of the AlarmStatus component.</p>
 *
 * @author Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.AlarmStatus"))
public class AlarmStatusRenderer extends HyperlinkRenderer {

    /** Creates a new instance of AlarmStatusRenderer */
    public AlarmStatusRenderer() {
    }

    protected void renderAlarmLabel(FacesContext context,
            AlarmStatus alarmStatus, ResponseWriter writer, Theme theme)
            throws IOException {
        writer.startElement("span", alarmStatus);
        addCoreAttributes(context, alarmStatus, writer,
                theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT));

        Object textObj = alarmStatus.getText();
        String text = textObj == null ? theme.getMessage("masthead.currentAlarms") : ConversionUtilities.convertValueToString(alarmStatus, textObj);

        writer.write(text);
        writer.write("&nbsp;");
        writer.endElement("span");
    }

    /**
     * <p>Render the start of the JobInfo component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        AlarmStatus alarmStatus = (AlarmStatus) component;
        Theme theme = ThemeUtilities.getTheme(context);

        UIComponent facet = alarmStatus.getFacet("alarmLabel");

        if (facet != null) {
            RenderingUtilities.renderComponent(facet, context);
        } else {
            renderAlarmLabel(context, alarmStatus, writer, theme);
        }

        int numAlarms = 0;
        String icon = null;
        String themeIcon = null;
        String alt = null;
        facet = alarmStatus.getFacet("downAlarms");

        if (facet != null) {
            // render the developer specified facet for down alarms
            RenderingUtilities.renderComponent(facet, context);
        } else if (alarmStatus.isDownAlarms()) {
            // render the down alarm image + count
            numAlarms = alarmStatus.getNumDownAlarms();
            themeIcon = numAlarms != 0 ? ThemeImages.ALARM_MASTHEAD_DOWN_MEDIUM : ThemeImages.ALARM_MASTHEAD_DOWN_DIMMED;
            icon = alarmStatus.getDownIcon() != null ? alarmStatus.getDownIcon() : themeIcon;
            alt = theme.getMessage("Alarm.downImageAltText");

            renderAlarmCount(context, writer, alarmStatus, icon,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_ALARM_LINK),
                    numAlarms, alt);
        }

        facet = alarmStatus.getFacet("criticalAlarms");

        if (facet != null) {
            // render the developer specified facet for critical alarms
            RenderingUtilities.renderComponent(facet, context);
        } else if (alarmStatus.isCriticalAlarms()) {
            // render the critical alarm + count
            numAlarms = alarmStatus.getNumCriticalAlarms();
            themeIcon = numAlarms != 0 ? ThemeImages.ALARM_MASTHEAD_CRITICAL_MEDIUM : ThemeImages.ALARM_MASTHEAD_CRITICAL_DIMMED;
            icon = alarmStatus.getCriticalIcon() != null ? alarmStatus.getCriticalIcon() : themeIcon;
            alt = theme.getMessage("Alarm.criticalImageAltText");

            renderAlarmCount(context, writer, alarmStatus, icon,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_ALARM_LINK),
                    numAlarms, alt);
        }

        facet = alarmStatus.getFacet("majorAlarms");

        if (facet != null) {
            // render the developer specified facet for major alarms
            RenderingUtilities.renderComponent(facet, context);
        } else if (alarmStatus.isMajorAlarms()) {
            // render the major alarm + count
            numAlarms = alarmStatus.getNumMajorAlarms();
            themeIcon = numAlarms != 0 ? ThemeImages.ALARM_MASTHEAD_MAJOR_MEDIUM : ThemeImages.ALARM_MASTHEAD_MAJOR_DIMMED;
            icon = alarmStatus.getMajorIcon() != null ? alarmStatus.getMajorIcon() : themeIcon;
            alt = theme.getMessage("Alarm.majorImageAltText");

            renderAlarmCount(context, writer, alarmStatus, icon,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_ALARM_LINK),
                    numAlarms, alt);
        }

        facet = alarmStatus.getFacet("minorAlarms");

        if (facet != null) {
            // render the developer specified facet for minor alarms
            RenderingUtilities.renderComponent(facet, context);
        } else if (alarmStatus.isMinorAlarms()) {
            // render the minor alarm + count
            numAlarms = alarmStatus.getNumMinorAlarms();
            themeIcon = numAlarms != 0 ? ThemeImages.ALARM_MASTHEAD_MINOR_MEDIUM : ThemeImages.ALARM_MASTHEAD_MINOR_DIMMED;
            icon = alarmStatus.getMinorIcon() != null ? alarmStatus.getMinorIcon() : themeIcon;
            alt = theme.getMessage("Alarm.minorImageAltText");

            renderAlarmCount(context, writer, alarmStatus, icon,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_ALARM_LINK),
                    numAlarms, alt);
        }
    }

    protected void renderAlarmCount(FacesContext context,
            ResponseWriter writer, AlarmStatus alarmStatus, String icon,
            String styleClass, int numAlarms, String alt) throws IOException {

        // We don't want conversion here. This is just to cache
        // the original value so we can restore it after calling
        // renderLink.
        //
        Object realText = alarmStatus.getText();
        String realIcon = alarmStatus.getIcon();
        String realStyleClass = alarmStatus.getStyleClass();
        boolean reallyDisabled = alarmStatus.isDisabled();
        String realAlt = alarmStatus.getAlt();

        // This is really bad practice.
        // A renderer MUST not modify a component.
        // It looks like we need to either make
        // renderLink more granular or reimplement it here.
        //
        alarmStatus.setIcon(icon);
        alarmStatus.setText("&nbsp;" + numAlarms);
        alarmStatus.setStyleClass(styleClass);
        alarmStatus.setAlt(alt);

        if (numAlarms == 0) {
            // alarm count of zero is NOT linked
            alarmStatus.setDisabled(true);
        }

        renderLink(context, alarmStatus, writer);
        writer.write("&nbsp;&nbsp;&nbsp;");

        alarmStatus.setText(realText);
        alarmStatus.setIcon(realIcon);
        alarmStatus.setStyleClass(realStyleClass);
        alarmStatus.setDisabled(reallyDisabled);
        alarmStatus.setAlt(alt);
    }

    /**
     * <p>Render the end of the JobInfo component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // override super so tha we do nothing here
    }

    @Override
    protected void finishRenderAttributes(FacesContext context,
            UIComponent component,
            ResponseWriter writer)
            throws IOException {
        // this method will (usually) be called 4 times to render each alarm
        // image and count as a separate instance of the same anchor tag
        AlarmStatus alarmStatus = (AlarmStatus) component;
        UIComponent image = alarmStatus.getImageFacet();

        String label = ConversionUtilities.convertValueToString(alarmStatus,
                alarmStatus.getText());

        if (image != null) {
            RenderingUtilities.renderComponent(image, context);
        }

        if (label != null && label.length() != 0) {
            writer.write(label);
        }
    }

    /**
     * @deprecated
     */
    protected void renderAlarmImage(FacesContext context, AlarmStatus alarmStatus,
            ResponseWriter writer, Theme theme, String alarmSrc)
            throws IOException {
        ImageComponent image = new ImageComponent();

        image.setIcon(alarmSrc);

        RenderingUtilities.renderComponent(image, context);
    }
}
