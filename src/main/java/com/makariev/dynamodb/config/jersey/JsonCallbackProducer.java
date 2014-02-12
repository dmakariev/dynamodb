/*
 * Copyright (C) 2013 Dimitar Makariev (http://makariev.com). All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.makariev.dynamodb.config.jersey;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;


/**
 *
 * @author dmakariev
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class JsonCallbackProducer extends JacksonJaxbJsonProvider {

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public long getSize(
            Object object,
            Class<?> clazz,
            Type type,
            Annotation[] annotations,
            MediaType mediaType) {
        long superSize = super.getSize(object, clazz, type, annotations, mediaType);
        final String callback = servletRequest.getParameter("callback");
        if (superSize >= 0 && callback != null) {
            superSize += callback.length() + 2;
        }
        return superSize;
    }

    @Override
    public void writeTo(
            Object object,
            Class<?> clazz,
            Type type,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, Object> multivaluedMap,
            OutputStream out) throws IOException, WebApplicationException {
        final String callback = servletRequest.getParameter("callback");

        if (callback != null) {
            out.write(callback.getBytes());
            out.write('(');
        }

        super.writeTo(object, clazz, type, annotations, mediaType, multivaluedMap, out);

        if (callback != null) {
            out.write(')');
        }
    }
}
