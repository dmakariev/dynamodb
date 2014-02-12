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
package com.makariev.dynamodb.config.base;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 *
 * @author dmakariev
 */
public class EnableSLF4JBridgeHandler {

    public EnableSLF4JBridgeHandler() {
        init();
    }

//http://blog.cn-consult.dk/2009/03/bridging-javautillogging-to-slf4j.html
    private static void init() {
        final Logger rootLogger = LogManager.getLogManager().getLogger("");
        final Handler[] handlers = rootLogger.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            rootLogger.removeHandler(handlers[i]);
        }
        SLF4JBridgeHandler.install();
    }
}
