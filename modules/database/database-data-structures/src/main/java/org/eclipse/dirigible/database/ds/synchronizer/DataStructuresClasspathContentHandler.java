/*
 * Copyright (c) 2017 SAP and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * SAP - initial API and implementation
 */

package org.eclipse.dirigible.database.ds.synchronizer;

import java.io.IOException;

import org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler;
import org.eclipse.dirigible.commons.api.module.StaticInjector;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Data Structures Classpath Content Handler.
 */
public class DataStructuresClasspathContentHandler extends AbstractClasspathContentHandler {

	private static final Logger logger = LoggerFactory.getLogger(DataStructuresClasspathContentHandler.class);

	private DataStructuresSynchronizer dataStructuresSynchronizer = StaticInjector.getInjector().getInstance(DataStructuresSynchronizer.class);

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#isValid(java.lang.String)
	 */
	@Override
	protected boolean isValid(String path) {

		try {
			if (path.endsWith(IDataStructureModel.FILE_EXTENSION_TABLE)) {
				dataStructuresSynchronizer.registerPredeliveredTable(path);
				return true;
			}

			if (path.endsWith(IDataStructureModel.FILE_EXTENSION_VIEW)) {
				dataStructuresSynchronizer.registerPredeliveredView(path);
				return true;
			}

			if (path.endsWith(IDataStructureModel.FILE_EXTENSION_REPLACE)) {
				dataStructuresSynchronizer.registerPredeliveredReplace(path);
				return true;
			}

			if (path.endsWith(IDataStructureModel.FILE_EXTENSION_APPEND)) {
				dataStructuresSynchronizer.registerPredeliveredAppend(path);
				return true;
			}

			if (path.endsWith(IDataStructureModel.FILE_EXTENSION_DELETE)) {
				dataStructuresSynchronizer.registerPredeliveredDelete(path);
				return true;
			}

			if (path.endsWith(IDataStructureModel.FILE_EXTENSION_UPDATE)) {
				dataStructuresSynchronizer.registerPredeliveredUpdate(path);
				return true;
			}
		} catch (IOException e) {
			logger.error("Predelivered Table or View is not valid", e);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#getLogger()
	 */
	@Override
	protected Logger getLogger() {
		return logger;
	}

}
