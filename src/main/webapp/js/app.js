var app =angular.module('myApp', ['ngRoute','ngCookies','ngStorage', 'ServicesModule']).
	config(['$routeProvider', function($routeProvider) {
	$routeProvider.when('/webseeker', {templateUrl: '/main.html', controller: 'SearchController'});
	//$routeProvider.when('/webseeker/results', {templateUrl: '/results.html', controller: 'ResultsController'});
	$routeProvider.when('/webseeker/results', {templateUrl: '/results.html', controller: 'ResultsController'});
    $routeProvider.otherwise({redirectTo: '/webseeker'});
  	}]);