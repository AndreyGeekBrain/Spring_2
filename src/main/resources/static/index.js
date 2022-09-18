angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {

    // $scope - можно воспринимать как контекст который видит html, аналог model для mvc
    // $localStorage - это память в браузере пользователя, область видимости данных которой только js.

    // Вызовы функции ниже проходят в html

    const contextPath = 'http://localhost:8189/app/api/v1';

    // Если у нас нет cartName, то мы будем его присваивать рандомно.
    if(!$localStorage.cartName){
        $localStorage.cartName = "cart_" + (Math.random() * 100);
    }

    // Если пользователь авторизован.
    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        $localStorage.cartName = "cart_" + $localStorage.springWebUser.username;
    }


    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/auth',$scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        delete $localStorage.cartName;
        $http.defaults.headers.common.Authorization = '';
    };

    // $scope.addToCart - это означает, что мы создаем метод.
    $scope.addToCart = function (productId) {
        console.log(productId);
        // отправили запрос на бекэнд
        $http.post('http://localhost:8189/app/api/v1/carts/add/' + productId, $localStorage.cartName)
            // получили ответ от бекэнда 200 ОК
            .then(function (response) {
                // вызвали метод loadCart(), который находился в контексте. Он перегрузит продукт.
                $scope.loadCart();
            });
    }

    $scope.decreaseCount=function(productId){
        console.log(productId);
        console.log($localStorage.cartName);
        $scope.idProduct =productId;

        $http.post('http://localhost:8189/app/api/v1/carts/decrease/' + productId, $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.delProduct=function(productId){
        $http.post('http://localhost:8189/app/api/v1/carts/deleteproduct/' + productId, $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }


    $scope.loadCart = function () {
        $http.post('http://localhost:8189/app/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                // Создаем переменную Cart в контексте и в нее кладем результат ответа.
                $scope.Cart = response.data;
            });
    }

    $scope.clearCart = function () {
        $http.post('http://localhost:8189/app/api/v1/carts/clear', $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.showCurrentUserInfo = function () {
        $http.get('http://localhost:8189/app/api/v1/profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    }

    $scope.loadProducts();
});