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
package org.xwiki.gwt.wysiwyg.client.plugin.macro.exec;

import java.util.Collections;

import org.xwiki.gwt.user.client.Config;
import org.xwiki.gwt.user.client.Console;
import org.xwiki.gwt.user.client.StringUtils;
import org.xwiki.gwt.user.client.ui.LoadingPanel;
import org.xwiki.gwt.user.client.ui.rta.Reloader;
import org.xwiki.gwt.user.client.ui.rta.RichTextArea;
import org.xwiki.gwt.user.client.ui.rta.SelectionPreserver;
import org.xwiki.gwt.user.client.ui.rta.cmd.Command;
import org.xwiki.gwt.user.client.ui.rta.cmd.CommandManager;
import org.xwiki.gwt.user.client.ui.rta.cmd.internal.AbstractSelectionExecutable;
import org.xwiki.gwt.wysiwyg.client.converter.HTMLConverter;
import org.xwiki.gwt.wysiwyg.client.converter.HTMLConverterAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Refreshes all the macros present on the edited document.
 * 
 * @version $Id$
 */
public class RefreshExecutable extends AbstractSelectionExecutable implements AsyncCallback<String>
{
    /**
     * The command used to notify all the plug-ins that the content of the rich text area is about to be submitted.
     */
    private static final Command SUBMIT = new Command("submit");

    /**
     * The command used to notify all the rich text area listeners when its content has been reset.
     */
    private static final Command RESET = new Command("reset");

    /**
     * Used to prevent typing in the rich text area while waiting for the updated content from the server.
     */
    private final LoadingPanel waiting = new LoadingPanel();

    /**
     * The object used to reload the rich text area.
     */
    private final Reloader reloader;

    /**
     * The object used to restore the default selection after the rich text area content is reloaded.
     */
    private final SelectionPreserver selectionPreserver;

    /**
     * The object used to reload the rich text area when a {@link Reloader} cannot be used (e.g. when the content of the
     * rich text area is not retrieved from an URL).
     */
    private final HTMLConverterAsync converter;

    /**
     * The syntax used to save the edited content.
     */
    private final String syntax;

    /**
     * Creates a new executable that can be used to refresh the specified rich text area. If the configuration specifies
     * a template URL to get the content from then we use a {@link Reloader} to submit the content of the rich text area
     * to the template URL and then use the response to reset the content of the rich text area. Otherwise, use a a
     * converter to parse and render the content of the rich text area, using the syntax specified in the configuration.
     * 
     * @param rta the execution target
     * @param config the configuration object
     */
    public RefreshExecutable(RichTextArea rta, Config config)
    {
        super(rta);

        String templateURL = config.getParameter("inputURL");
        if (StringUtils.isEmpty(templateURL)) {
            converter = GWT.create(HTMLConverter.class);
            syntax = config.getParameter("syntax", "xhtml/1.0");
            reloader = null;
        } else {
            reloader = new Reloader(rta, templateURL);
            converter = null;
            syntax = null;
        }

        selectionPreserver = new SelectionPreserver(rta);
    }

    /**
     * {@inheritDoc}
     * 
     * @see AbstractSelectionExecutable#execute(String)
     */
    public boolean execute(String param)
    {
        // Check if there is a refresh in progress.
        if (waiting.isLoading()) {
            return false;
        }

        // Prevent typing while waiting for the updated content.
        waiting.startLoading(rta);
        waiting.setFocus(true);

        // Request the updated content.
        CommandManager cmdManager = rta.getCommandManager();
        refresh(cmdManager.execute(SUBMIT) ? cmdManager.getStringValue(SUBMIT) : rta.getHTML());

        return true;
    }

    /**
     * Sends a request to the server to parse and re-render the content of the given rich text area.
     * 
     * @param html the HTML content of the rich text area
     */
    private void refresh(String html)
    {
        if (reloader != null) {
            reloader.reload(Collections.singletonMap("html", html), this);
        } else {
            converter.parseAndRender(html, syntax, this);
        }
    }

    @Override
    public void onFailure(Throwable caught)
    {
        Console.getInstance().error(caught.getLocalizedMessage());
        // Try to focus the rich text area.
        rta.setFocus(true);
        waiting.stopLoading();
    }

    @Override
    public void onSuccess(String result)
    {
        // Restore the default selection.
        // Note: We haven't saved the selection before reloading the content because the current implementation of
        // SelectionPreserver can't save the selection across reloads: it stores references to DOM nodes which are
        // replaced after the content is reloaded. We use the selection preserver just to be able to restore the default
        // selection in a consistent manner (without duplicating code).
        selectionPreserver.restoreSelection();
        // Reset the content of the rich text area.
        if (reloader != null) {
            // The content of the rich text area is automatically updated when we use a reloader so we just need to
            // notify that the content has been reset.
            rta.getCommandManager().execute(RESET);
        } else {
            // Update the content of the rich text area and notify the change.
            rta.getCommandManager().execute(RESET, result);
        }
        // Store the initial value of the rich text area in case it is submitted without gaining focus.
        rta.getCommandManager().execute(SUBMIT, true);
        // Try to focus the rich text area.
        rta.setFocus(true);
        waiting.stopLoading();
    }
}
