/*
 * Copyright (c) 2017 SAP and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * SAP - initial API and implementation
 */

package org.eclipse.dirigible.core.workspace.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dirigible.core.workspace.api.IProject;
import org.eclipse.dirigible.core.workspace.api.IWorkspace;
import org.eclipse.dirigible.repository.api.ICollection;

/**
 * The Workspace's Workspace.
 */
public class Workspace extends Folder implements IWorkspace {

	/**
	 * Instantiates a new workspace.
	 *
	 * @param workspaceCollection
	 *            the workspace collection
	 */
	public Workspace(ICollection workspaceCollection) {
		super(workspaceCollection);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#createProject(java.lang.String)
	 */
	@Override
	public IProject createProject(String name) {
		ICollection collection = this.createCollection(name);
		return new Project(collection);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#getProject(java.lang.String)
	 */
	@Override
	public IProject getProject(String name) {
		ICollection collection = this.getCollection(name);
		return new Project(collection);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#getProjects()
	 */
	@Override
	public List<IProject> getProjects() {
		List<IProject> projects = new ArrayList<IProject>();
		List<ICollection> collections = this.getCollections();
		for (ICollection collection : collections) {
			projects.add(new Project(collection));
		}
		return projects;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#deleteProject(java.lang.String)
	 */
	@Override
	public void deleteProject(String name) {
		this.removeCollection(name);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#copyProject(java.lang.String, java.lang.String)
	 */
	@Override
	public void copyProject(String sourceProject, String targetProject) {
		ICollection collection = this.createCollection(sourceProject);
		collection.copyTo(this.getCollection(targetProject).getPath());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#copyFolder(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void copyFolder(String sourceProject, String sourceFolderPath, String targetProject, String targetFolderPath) {
		ICollection collection = this.createCollection(sourceProject);
		collection.getCollection(sourceFolderPath).copyTo(this.getCollection(targetProject).getCollection(targetFolderPath).getPath());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#copyFile(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void copyFile(String sourceProject, String sourceFilePath, String targetProject, String targetFilePath) {
		ICollection collection = this.createCollection(sourceProject);
		collection.getResource(sourceFilePath).copyTo(this.getCollection(targetProject).getResource(targetFilePath).getPath());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#moveProject(java.lang.String, java.lang.String)
	 */
	@Override
	public void moveProject(String sourceProject, String targetProject) {
		ICollection collection = this.createCollection(sourceProject);
		collection.moveTo(this.getCollection(targetProject).getPath());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#moveFolder(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void moveFolder(String sourceProject, String sourceFolderPath, String targetProject, String targetFolderPath) {
		ICollection collection = this.createCollection(sourceProject);
		collection.getCollection(sourceFolderPath).moveTo(this.getCollection(targetProject).getCollection(targetFolderPath).getPath());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.workspace.api.IWorkspace#moveFile(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void moveFile(String sourceProject, String sourceFilePath, String targetProject, String targetFilePath) {
		ICollection collection = this.createCollection(sourceProject);
		collection.getResource(sourceFilePath).moveTo(this.getCollection(targetProject).getResource(targetFilePath).getPath());
	}

}
