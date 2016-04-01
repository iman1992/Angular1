var carApp = angular.module('CarApp',['ngRoute']);
    
    carApp.controller('ViewController', [function(){
        var self = this;
        self.title = "Cars Demo App";
        
    }]).config(['$routeProvider', function($routeProvider) {
                   $routeProvider
                   .when('/', {
                       templateUrl: 'allCars.html'
                   })
                   .when('/edit/:reguestId', {
                       templateUrl: 'editCar.html',
                       controller: 'CarController'
                   })
                   .when('/add', {
                       templateUrl: 'addCar.html',
                       controller: 'CarController'
                   })
                   .otherwise({redirectTo: '/'});
       }]);

    carApp.controller('CarController', ['$routeParams','CarFactory', function($routeParams,CarFactory){
        var self = this;
        var id = $routeParams.requestId;
        
        CarFactory.getCars().then(function(data){
            self.cars = data;
        });
        
        self.editcar = CarFactory.getCar(id);
        
        self.edit = function(){
            CarFactory.editCar(self.editCar);
        };
        
        self.delete = function(){
            CarFactory.deleteCar(id);
        };
         
        self.add = function(newcar){
           CarFactory.addCar(newcar);  
        };
    }]);

    carApp.factory('CarFactory', function($http){
      var cars = [];
      
      var getCars = function(){
        return $http.get('/AngularCarExcercise/api/car/complete').then(function(response){
                return response.data; 
            });
      };
      
      var getCar = function(id){
          return $http.get('/AngularCarExcercise/api/car/'+id).then(function(response){
                return response.data; 
            });
      };
      
      var deleteCar = function(id){
         $http.delete('/AngularCarExcercise/api/car/'+id).then(function(response){
             return response.data;
         });
      };
     
      var addCar = function(car){
         $http.post('/AngularCarExcercise/api/car', car);
      };
    
      var editCar = function(car){
         $http.put('/AngularCarExcercise/api/car', car);
     };
      
      return {
          getCars: getCars,
          getCar: getCar,
          deleteCar: deleteCar,
          addCar: addCar,
          editCar: editCar
      };
    });


