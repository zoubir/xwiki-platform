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
package org.xwiki.wysiwyg.server.internal.plugin.importer;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.gwt.wysiwyg.client.cleaner.HTMLCleaner;
import org.xwiki.gwt.wysiwyg.client.plugin.importer.ImportService;
import org.xwiki.gwt.wysiwyg.client.wiki.Attachment;

/**
 * Overwrites the class with the same name from {@code xwiki-platform-wysiwyg-server}.
 * 
 * @version $Id$
 */
@Component
@Singleton
public class XWikiImportService implements ImportService
{
    /**
     * The component used to clean the pasted HTML.
     */
    @Inject
    private HTMLCleaner cleaner;

    @Override
    public String cleanOfficeHTML(String htmlPaste, String cleanerHint, Map<String, String> cleaningParams)
    {
        return cleaner.clean(htmlPaste);
    }

    @Override
    public String officeToXHTML(Attachment attachment, Map<String, String> cleaningParams)
    {
        throw new UnsupportedOperationException("This operation requires an office server.");
    }
}
