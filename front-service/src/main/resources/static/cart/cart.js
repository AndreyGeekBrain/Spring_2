angular.module('market-front').controller('cartController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/getaway/api/v1';

    $scope.loadCart = function () {
        $http.post('http://localhost:5555/getaway/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.clearCart = function () {
        $http.post('http://localhost:5555/getaway/api/v1/carts/clear', $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.decreaseCount=function(productId){
        console.log(productId);
        console.log($localStorage.cartName);
        $scope.idProduct =productId;

        $http.post('http://localhost:5555/getaway/api/v1/carts/decrease/' + productId, $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.checkOut = function () {
        $http({
            url: contextPath + '/orders/' + $localStorage.cartName,
            method: 'POST',
            data: {orderDetailsDto: $scope.orderDetails}
        }).then(function (response) {
                $scope.loadCart();
                $scope.orderDetails = null
            });
    };

    $scope.delProduct=function(productId){
        $http.post('http://localhost:5555/getaway/api/v1/carts/deleteproduct/' + productId, $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.loadCart();

});