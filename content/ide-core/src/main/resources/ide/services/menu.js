/*******************************************************************************
 * Copyright (c) 2017 SAP and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * SAP - initial API and implementation
 *******************************************************************************/

/* eslint-env node, dirigible */

var extensions = require('core/v3/extensions');
var response = require('http/v3/response');

var mainmenu = [];
var menuExtensions = extensions.getExtensions('ide-menu');
for (var i=0; i<menuExtensions.length; i++) {
    var module = menuExtensions[i];
    menuExtension = require(module);
    var menu = menuExtension.getMenu();
    mainmenu.push(menu);
}
response.println(JSON.stringify(mainmenu));