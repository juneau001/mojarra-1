/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.context;

import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.sun.faces.util.Util;

/**
 * @see javax.faces.context.ExternalContext#getRequestHeaderValuesMap()
 */
public class RequestHeaderValuesMap extends StringArrayValuesMap {

    private final HttpServletRequest request;


    // ------------------------------------------------------------ Constructors


    public RequestHeaderValuesMap(HttpServletRequest request) {
        this.request = request;
    }


    // -------------------------------------------------------- Methods from Map


    @Override
    public boolean containsKey(Object key) {
        return (request.getHeaders(key.toString()) != null);
    }


    @Override
    public String[] get(Object key) {
        Util.notNull("key", key);

        List<String> valuesList = new ArrayList<>();
        Enumeration valuesEnum = this.request.getHeaders(key.toString());
        while (valuesEnum.hasMoreElements()) {
            valuesList.add((String) valuesEnum.nextElement());
        }

        return valuesList.toArray(new String[valuesList.size()]);
    }


    @Override
    public Set<Map.Entry<String,String[]>> entrySet() {
        return Collections.unmodifiableSet(super.entrySet());
    }


    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(super.keySet());
    }


    @Override
    public Collection<String[]> values() {
        return Collections.unmodifiableCollection(super.values());
    }


    @Override
    public int hashCode() {
        return hashCode(request);
    }


    @Override
    public boolean equals(Object obj) {
       return !(obj == null || !(obj.getClass()
                   == ExternalContextImpl
                       .theUnmodifiableMapClass))
               && super.equals(obj);
    }


    // --------------------------------------------- Methods from BaseContextMap

    @Override
    protected Iterator<Map.Entry<String,String[]>> getEntryIterator() {
        return new EntryIterator(request.getHeaderNames());
    }


    @Override
    protected Iterator<String> getKeyIterator() {
        return new KeyIterator(request.getHeaderNames());
    }


    @Override
    protected Iterator<String[]> getValueIterator() {
        return new ValueIterator(request.getHeaderNames());
    }

} // END RequestHeaderValuesMap
