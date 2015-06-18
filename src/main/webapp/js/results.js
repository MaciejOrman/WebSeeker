function ResultsController($scope, $rootScope, $http, $location, linkService, $cookieStore, $routeParams, $sessionStorage) {
	 $scope.nick =$sessionStorage.nick;
	 $scope.query=$sessionStorage.query;
	console.log($scope.nick+'--------'+$scope.query);
	//$rootScope.results = {};
	//$rootScope.query="java";
	//$scope.nick="Maciek";
	
/*	for(var i=1;i<11;i++){
		$scope.grades[i]=0;
	}*/ 
//is it necessary to initialize all grades with 0? 
//it will be missleading in further implementation


	
		$scope.search = function(){	
	/*		 $http.post("/search", {query:  $scope.query}).
			    		  success(function(data, status, headers, config) {
			    			  
			    			  $rootScope.results = data;
			    			  $rootScope.links = data.results;
			    			  
			    			//  for(var i = 0; i<$rootScope.links.length;i++){
			    			//	  $rootScope.linksAndGrades =   
			    			//  }
			    			  
			    			$location.path("/webseeker/results");
			    			//console.log(data.process_duckduckgo.Results);
			    		  }).
			    		  error(function(data, status, headers, config) {
			    			// called asynchronously if an error occurs
			    			// or server returns response with an error status.
			    		  });	*/
	/*		if($scope.query==undefined ||$scope.query==null || $scope.query==""){
				$scope.query = $cookieStore.get('query');
			}
			if($scope.nick==undefined ||$scope.nick==null || $scope.nick==""){
				$scope.nick = $cookieStore.get('nick');
			}*/

			var promise = linkService.getLinks($scope.query, $scope.nick);
	
			promise.then(function(data){
				$scope.links = data;
				//$location.path('/webseeker/results');
				
			});
		};
		
		$scope.meanSearch = function(){
			var promise = linkService.getMeanSortedLinks($scope.query, $scope.nick);
			
			promise.then(function(data){
				$scope.links = data;
				//$location.path('/webseeker/results');
				
			});
		}
		
		$scope.collSearch = function(){
			var promise = linkService.getCollSortedLinks($scope.query, $scope.nick);
			
			promise.then(function(data){
				$scope.links = data;
				//$location.path('/webseeker/results');
				
			});
		}
		
		$scope.printGrades = function(){
			console.log($scope.links);
		};
		
		$scope.sendGrades = function(){
			$scope.links.query = $scope.query;
			var promise = linkService.sendGrades($scope.links);
			
			promise.then(function(data){
				alert("Your grades have been sent.")			
			});
		}
		
		$scope.back = function(){
			$location.path('/webseeker/');
		}
};

 
 
