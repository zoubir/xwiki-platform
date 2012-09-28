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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.gwt.wysiwyg.client.wiki.EntityConfig;
import org.xwiki.gwt.wysiwyg.client.wiki.EntityReference;
import org.xwiki.gwt.wysiwyg.client.wiki.URIReference;
import org.xwiki.rendering.listener.reference.ResourceReference;
import org.xwiki.rendering.listener.reference.ResourceType;
import org.xwiki.rendering.parser.ResourceReferenceParser;
import org.xwiki.rendering.renderer.reference.ResourceReferenceSerializer;
import org.xwiki.wysiwyg.server.wiki.LinkService;

/**
 * Overwrites the class with the same name from {@code xwiki-platform-wysiwyg-server}.
 * 
 * @version $Id$
 */
@Component
@Singleton
public class DefaultLinkService implements LinkService
{
    /**
     * The component used to serialize link references.
     * <p>
     * Note: The link reference syntax is independent of the syntax of the edited document. The current hint should be
     * replaced with a generic one to avoid confusion.
     */
    @Inject
    @Named("xhtmlmarker")
    private ResourceReferenceSerializer linkReferenceSerializer;

    /**
     * The component used to parser link references.
     * <p>
     * Note: The link reference syntax is independent of the syntax of the edited document. The current hint should be
     * replaced with a generic one to avoid confusion.
     */
    @Inject
    @Named("xhtmlmarker")
    private ResourceReferenceParser linkReferenceParser;

    @Override
    public EntityConfig getEntityConfig(EntityReference origin,
        org.xwiki.gwt.wysiwyg.client.wiki.ResourceReference destination)
    {
        if (org.xwiki.gwt.wysiwyg.client.wiki.EntityReference.EntityType.EXTERNAL != destination.getEntityReference()
            .getType()) {
            throw new UnsupportedOperationException();
        }

        EntityConfig entityConfig = new EntityConfig();

        String url = new URIReference(destination.getEntityReference()).getURI();
        entityConfig.setUrl(url);

        ResourceType resourceType = new ResourceType(destination.getType().getScheme());
        ResourceReference linkReference = new ResourceReference(url, resourceType);
        linkReference.setTyped(destination.isTyped());
        entityConfig.setReference(linkReferenceSerializer.serialize(linkReference));

        return entityConfig;
    }

    @Override
    public org.xwiki.gwt.wysiwyg.client.wiki.ResourceReference parseLinkReference(String linkReferenceAsString,
        EntityReference baseReference)
    {
        ResourceReference linkReference = linkReferenceParser.parse(linkReferenceAsString);
        org.xwiki.gwt.wysiwyg.client.wiki.ResourceReference clientLinkReference =
            new org.xwiki.gwt.wysiwyg.client.wiki.ResourceReference();
        clientLinkReference.setType(org.xwiki.gwt.wysiwyg.client.wiki.ResourceReference.ResourceType
            .forScheme(linkReference.getType().getScheme()));
        clientLinkReference.setTyped(linkReference.isTyped());
        clientLinkReference.getParameters().putAll(linkReference.getParameters());
        clientLinkReference.setEntityReference(new URIReference(linkReference.getReference()).getEntityReference());
        return clientLinkReference;
    }
}
