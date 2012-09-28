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
package org.xwiki.wysiwyg.server.internal.wiki;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.gwt.wysiwyg.client.wiki.Attachment;
import org.xwiki.gwt.wysiwyg.client.wiki.AttachmentReference;
import org.xwiki.gwt.wysiwyg.client.wiki.EntityConfig;
import org.xwiki.gwt.wysiwyg.client.wiki.EntityReference;
import org.xwiki.gwt.wysiwyg.client.wiki.ResourceReference;
import org.xwiki.gwt.wysiwyg.client.wiki.WikiPage;
import org.xwiki.gwt.wysiwyg.client.wiki.WikiPageReference;
import org.xwiki.gwt.wysiwyg.client.wiki.WikiService;
import org.xwiki.wysiwyg.server.wiki.LinkService;

/**
 * Overwrites the class with the same name from {@code xwiki-platform-wysiwyg-server}.
 * 
 * @version $Id$
 */
@Component
@Singleton
public class DefaultWikiService implements WikiService
{
    /**
     * The link service.
     */
    @Inject
    private LinkService linkService;

    @Override
    public Boolean isMultiWiki()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getVirtualWikiNames()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getSpaceNames(String wikiName)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getPageNames(String wikiName, String spaceName)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<WikiPage> getRecentlyModifiedPages(String wikiName, int start, int count)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<WikiPage> getMatchingPages(String wikiName, String keyword, int start, int count)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityConfig getEntityConfig(EntityReference origin, ResourceReference destination)
    {
        return linkService.getEntityConfig(origin, destination);
    }

    @Override
    public Attachment getAttachment(AttachmentReference attachmentReference)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Attachment> getImageAttachments(WikiPageReference documentReference)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Attachment> getAttachments(WikiPageReference documentReference)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUploadURL(WikiPageReference documentReference)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResourceReference parseLinkReference(String linkReference, EntityReference baseReference)
    {
        return linkService.parseLinkReference(linkReference, baseReference);
    }
}
