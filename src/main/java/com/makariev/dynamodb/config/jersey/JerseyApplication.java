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

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import com.makariev.dynamodb.jpa.PersonResource;
//import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 *
 * @author dmakariev
 */
public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {

        //Resources
        //packages(PersonResource.class.getPackage().toString());
        registerClasses(PersonResource.class);

        //Features
        register(JsonCallbackProducer.class);
        //register(JacksonFeature.class);

        property(ServerProperties.MEDIA_TYPE_MAPPINGS, mediaTypeMappings());

    }

    private static Map<String, MediaType> mediaTypeMappings() {
        final Map<String, MediaType> mediaTypeMap = new HashMap<String, MediaType>();
        mediaTypeMap.put("json", MediaType.APPLICATION_JSON_TYPE);
        mediaTypeMap.put("xml", MediaType.APPLICATION_XML_TYPE);
        mediaTypeMap.put("txt", MediaType.TEXT_PLAIN_TYPE);
        mediaTypeMap.put("html", MediaType.TEXT_HTML_TYPE);
        mediaTypeMap.put("xhtml", MediaType.APPLICATION_XHTML_XML_TYPE);
        final MediaType jpeg = new MediaType("image", "jpeg");
        mediaTypeMap.put("jpg", jpeg);
        mediaTypeMap.put("jpeg", jpeg);
        final MediaType zip = new MediaType("application", "x-zip-compressed");
        mediaTypeMap.put("zip", zip);
        return mediaTypeMap;
    }

}
