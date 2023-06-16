/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.support.ctx.core.servlets;

import com.adobe.aem.support.ctx.core.ctxconfig.PersonCtxConfig;
import com.google.gson.JsonObject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "cq:Page" , methods = HttpConstants.METHOD_GET, extensions = "json", selectors = "ctx")
@ServiceDescription("Simple Demo Servlet")
public class CqPageCtxResourceServlet extends SlingSafeMethodsServlet {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            final Resource resource = req.getResource();
            logger.info("Detected following resource path {}", resource.getPath());

            PersonCtxConfig config = resource.adaptTo(ConfigurationBuilder.class).as(PersonCtxConfig.class);
            JsonObject personCtxConfigJson = new JsonObject();

            personCtxConfigJson.addProperty("first_name", config.first_name());
            personCtxConfigJson.addProperty("last_name", config.first_name());

            resp.setStatus(SlingHttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getWriter().write(personCtxConfigJson.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resp.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
