/*
 * Copyright (c) 2017 SAP and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * SAP - initial API and implementation
 */

var messageHub = new FramesMessageHub();

messageHub.fireFileOpen = function(fileDescriptor){
	messageHub.post({data: fileDescriptor}, 'fileselected');
}

angular.module('database', []).controller('DatabaseController', function ($scope, $http) {
					
	var databasesSvcUrl = "/services/v3/ide/databases";
	$scope.selectedDatabase;
	$scope.jstree;
	
	$http.get(databasesSvcUrl)
		.success(function(data) {
			$scope.databases = data;
			if(data[0]) {
				$scope.selectedDatabase = data[0];
				messageHub.post($scope.selectedDatabase, 'database.database.selection.changed');
				$http.get(databasesSvcUrl + "/" + $scope.selectedDatabase).success(function(data) {
					$scope.datasources = data;
					if(data[0]) {
						$scope.selectedDatasource = data[0];
						messageHub.post($scope.selectedDatasource, 'database.datasource.selection.changed');
						$scope.refreshDatabase();
					}
				});
			}
	});
	
	$scope.refreshDatabase = function() {
		if($scope.selectedDatabase && $scope.selectedDatasource){
				$http.get(databasesSvcUrl + '/' + $scope.selectedDatabase + '/' + $scope.selectedDatasource)
					.success(function(data) {
						$scope.datasource = data;
						this.baseUrl = databasesSvcUrl + '/' + $scope.selectedDatabase + '/' + $scope.selectedDatasource;
						var schemas = $scope.datasource.schemas.map(function(schemas){
							return build(schemas);
						})
						if ($scope.jstree) {
							$('.database').jstree(true).settings.core.data = schemas;
							$('.database').jstree(true).refresh();
						} else {
						  $scope.jstree = $('.database').jstree({
							"core" : {
							  "data" : schemas,
							  "themes": {
						            "name": "default",
						            "responsive": false,
						            "dots": false,
									"icons": true,
									'variant' : 'small',
									'stripes' : true
						      		},
							  'check_callback' : function(o, n, p, i, m) {
									if(m && m.dnd && m.pos !== 'i') { return false; }
									if(o === "move_node" || o === "copy_node") {
										if(this.get_node(n).parent === this.get_node(p).id) { return false; }
									}
									return true;
								  }
							  },
							  'types': {
								  'default': {
									  'icon': "fa fa-file"
								  },
								  'folder': {
									  'icon': "fa fa-folder"
								  },
								  'file': {
									  'icon': "fa fa-file"
								  },
								  'project': {
									  'icon': "fa fa-folder"
								  }
							  },
					          'contextmenu' : {
									'items' : function(node) {
										var ctxmenu = {};
										if (node.original.type === 'table'
											|| node.original.type === 'view') {
											ctxmenu.contents = {
												"separator_before": false,
												"label": "Show Contents",
												"action": function(data){
													var tree = $.jstree.reference(data.reference);
													var node = tree.get_node(data.reference);
													var sqlCommand = "SELECT * FROM " + node.original.text;
													messageHub.post({data: sqlCommand}, 'database.sql.execute');
												}.bind(this)
											};
										}
										return ctxmenu;
									}
								},
								"plugins": ['state','dnd','sort','types','contextmenu','unique']
						  })
						 .on('select_node.jstree', function (e, data) {
							//
						  })
						 .on('dblclick.jstree', function (evt, node) {
							 var data= $('.database').jstree().get_selected(true);
							 var kind = $('.database').jstree().get_node(evt.target).original.kind;
							 // if(['table'].indexOf(type)<0)
// 								messageHub.fireFileOpen(data[0].original._file);
						  })
						  .on('open_node.jstree', function(evt, data) {
							//data.instance.set_icon(data.node, 'fa fa-folder-open-o');
						  })
						  .on('close_node.jstree', function(evt, data) {
							//data.instance.set_icon(data.node, 'fa fa-folder-o');
						  });
						}
				}.bind(this));
			} else {
				$('.database').jstree(true).settings.core.data = [];
				$('.database').jstree(true).refresh();
			}
	};
	
	var build = function(f){
		var children = [];
		var icon = 'fa fa-th-large';
		var name = f.name;
		if(f.kind=='schema') {
			children = f.tables.map(function(_table){
				return build(_table)
			});
			icon = 'fa fa-database';
		} else if(f.kind=='table') {
			children = f.columns.map(function(_column){
				return build(_column)
			});
			icon = 'fa fa-table';
		} else if(f.kind=='column') {
			icon = 'fa fa-th-large';
			name += ' [<i>' + f.type + '</i>';
			name += ' <i>(' + f.size + ')</i>';
			name += ']';
		}
		f.label = f.name;
		return {
			"text": name,
			"children": children,
			"type": f.kind,
			"_file": f,
			"icon": icon
		}
	}
	
	$scope.databaseChanged = function(evt){
		$http.get(databasesSvcUrl + '/' + $scope.selectedDatabase)
				.success(function(data) {
					$scope.datasources = data;
					if (data[0]) {
						$scope.selectedDatasource = data[0];
						messageHub.post($scope.selectedDatabase, 'database.database.selection.changed');
					} else {
						$scope.selectedDatasource = undefined;
					}
					$scope.refreshDatabase();
		});
	};
	
	$scope.datasourceChanged = function(evt){
		messageHub.post($scope.selectedDatasource, 'database.datasource.selection.changed');
		$scope.refreshDatabase();
	};

});
	
