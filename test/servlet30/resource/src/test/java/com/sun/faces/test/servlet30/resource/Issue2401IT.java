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

package com.sun.faces.test.servlet30.resource;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.junit.*;
import static org.junit.Assert.*;

public class Issue2401IT {

    /**
     * Stores the web URL.
     */
    private String webUrl;
    /**
     * Stores the web client.
     */
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }

    // ------------------------------------------------------------ Test Methods

    @Test
    public void testResourceWithAndWithoutLibrary() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/start.xhtml");
        assertTrue(page.asText().contains(
                "Resource created with library: /test-servlet30-resource/faces/javax.faces.resource/images/background.png?ln=css"));
        assertTrue(page.asText().contains(
                "Resource created without library: /test-servlet30-resource/faces/javax.faces.resource/css/images/background.png"));
    }

    @Test
    public void testResourceVersions() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/issue2565.xhtml");

        HtmlElement element = page.getHtmlElementById("trailingUnderscore");
        assertEquals("SUCCESS", element.getTextContent());

        element = page.getHtmlElementById("noUnderscore");
        assertEquals("SUCCESS", element.getTextContent());

        element = page.getHtmlElementById("leadingUnderscore");
        assertEquals("SUCCESS", element.getTextContent());

        element = page.getHtmlElementById("validVersion");
        assertEquals("SUCCESS", element.getTextContent());
    }

}
