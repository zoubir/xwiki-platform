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
package org.xwiki.rendering.macro;

import org.xwiki.component.phase.InitializationException;
import org.xwiki.component.phase.Initializable;
import org.xwiki.rendering.block.Block;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.block.EscapeBlock;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

/**
 * Prevents wiki syntax rendering.
 *
 * @version $Id: $
 * @since 1.6M1
 */
public class NoWikiMacro extends AbstractMacro implements Initializable
{
    private static final String DESCRIPTION = "Wiki syntax inside this macro is not rendered.";

    private Map<String, String> allowedParameters;

    /**
     * {@inheritDoc}
     * @see org.xwiki.component.phase.Initializable#initialize()
     */
    public void initialize() throws InitializationException
    {
        // TODO: Use an I8N service to translate the descriptions in several languages
        this.allowedParameters = new HashMap<String, String>();
    }

    /**
     * {@inheritDoc}
     * @see Macro#getDescription()
     */
    public String getDescription()
    {
        // TODO: Use an I8N service to translate the description in several languages
        return DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     * @see Macro#getAllowedParameters()
     */
    public Map<String, String> getAllowedParameters()
    {
        // We send a copy of the map and not our map since we don't want it to be modified.
        return new HashMap<String, String>(this.allowedParameters);
    }

    /**
     * {@inheritDoc}
     * @see Macro#execute(Map, String, org.xwiki.rendering.block.XDOM)
     */
    public List<Block> execute(Map<String, String> parameters, String content, XDOM dom)
        throws MacroExecutionException
    {
        return Arrays.asList((Block) new EscapeBlock(content));
    }
}
