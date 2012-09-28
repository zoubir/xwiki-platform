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
package org.xwiki.wysiwyg.server;

import java.lang.reflect.Type;

import javax.servlet.ServletException;

import org.xwiki.component.embed.EmbeddableComponentManager;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Generic {@link RemoteServiceServlet} that dispatches the RPC calls to components registered through the component
 * manager.
 * 
 * @version $Id$
 * @see http://stuffthathappens.com/blog/2009/09/14/guice-with-gwt/
 */
public class XWikiRemoteServiceServlet extends RemoteServiceServlet
{
    /**
     * Field required by all {@link java.io.Serializable} classes.
     */
    private static final long serialVersionUID = 1911611911891893986L;

    /**
     * The component manager used to lookup services.
     */
    private EmbeddableComponentManager componentManager;

    @Override
    public void init() throws ServletException
    {
        super.init();

        // Initializes the component manager used to lookup other components.
        componentManager = new EmbeddableComponentManager();
        componentManager.initialize(this.getClass().getClassLoader());
    }

    @Override
    public String processCall(String payload) throws SerializationException
    {
        try {
            RPCRequest req = RPC.decodeRequest(payload, null, this);
            RemoteService service =
                (RemoteService) componentManager.getInstance((Type) req.getMethod().getDeclaringClass());
            return RPC.invokeAndEncodeResponse(service, req.getMethod(), req.getParameters(),
                req.getSerializationPolicy());
        } catch (Exception ex) {
            log("Failed to process the service call.", ex);
            return RPC.encodeResponseForFailure(null, ex);
        }
    }
}
