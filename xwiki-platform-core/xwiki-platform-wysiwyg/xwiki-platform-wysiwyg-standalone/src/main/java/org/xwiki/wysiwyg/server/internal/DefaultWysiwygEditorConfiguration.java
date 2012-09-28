/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.wysiwyg.server.internal;

import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.wysiwyg.server.WysiwygEditorConfiguration;

/**
 * Overwrites the class with the same name from {@code xwiki-platform-wysiwyg-server}.
 * 
 * @version $Id$
 */
@Component
@Singleton
public class DefaultWysiwygEditorConfiguration implements WysiwygEditorConfiguration
{
    @Override
    public Boolean isSourceEditorEnabled()
    {
        return false;
    }

    @Override
    public String getPlugins()
    {
        return "";
    }

    @Override
    public String getMenuBar()
    {
        return "";
    }

    @Override
    public String getToolBar()
    {
        return "";
    }

    @Override
    public Boolean isAttachmentSelectionLimited()
    {
        return false;
    }

    @Override
    public Boolean areExternalImagesAllowed()
    {
        return true;
    }

    @Override
    public Boolean isImageSelectionLimited()
    {
        return false;
    }

    @Override
    public String getColorPalette()
    {
        return "";
    }

    @Override
    public Integer getColorsPerRow()
    {
        return 8;
    }

    @Override
    public String getFontNames()
    {
        return "";
    }

    @Override
    public String getFontSizes()
    {
        return "";
    }

    @Override
    public String getStyleNames()
    {
        return "";
    }

    @Override
    public Integer getHistorySize()
    {
        return 10;
    }
}
