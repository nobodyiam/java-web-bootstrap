(function ($) {
    var app = angular.module('Greeting', [
        'ui.bootstrap',
        'toastr',
        'httpInterceptors' //custom http interceptor
    ]);

    app.controller('GreetingsController', function ($scope, $http, $modal, toastr) {
        this.pageNo = 1;
        this.pageSize = 10;
        this.greetings = [];
        var self = this;

        this.getGreetings = function (pageNo, pageSize) {
            $http.get('/greetings', {
                data: {
                    offset: (pageNo - 1) * pageSize,
                    limit: pageSize
                }
            }).success(function (data, status, headers, config) {
                self.greetings = data;

            }).error(function (data, status, headers, config) {
                toastr.error((data && data.msg) || 'Loading failed');
            })
        }

        this.add = function () {
            var modalInstance = $modal.open({
                templateUrl: 'templates/add.html',
                controller: 'GreetingController',
                controllerAs: 'greetingCtrl',
                size: 'lg',
                resolve: {
                    greeting: function () {
                        return {};
                    }
                }
            });

            modalInstance.result.then(function (addedItem) {
                if (self.pageNo == 1 && self.greetings.length < self.pageSize) {
                    self.greetings.push(addedItem);
                }
            });
        }

        this.edit = function (item) {

        }

        this.delete = function (item) {

        }

        this.init = function () {
            this.getGreetings(this.pageNo, this.pageSize);

        }

        this.init();
    });

    app.controller('GreetingController', function ($scope, $http, $modalInstance, toastr, greeting) {
        this.item = greeting;
        var self = this;

        this.save = function () {
            $http.post('/greetings', this.item).success(function (data, status, headers, config) {
                toastr.success('Saved successfully');
                self.close(data);
            }).error(function (data, status, headers, config) {
                toastr.error((data && data.msg) || 'Saved failed');
            })
        }

        this.close = function (data) {
            $modalInstance.close(data);
        }
    });

})(jQuery);