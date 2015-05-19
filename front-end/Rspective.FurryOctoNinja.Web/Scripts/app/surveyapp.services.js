(function () {
    angular.module("surveyapp")
        .service("api", api)
        .service("authStorage", authStorage)
        .service("dataRefresher", dataRefresher)

    api.$inject = ["$http", "dataRefresher", "authStorage"];

    function api($http, dataRefresher, authStorage) {
        var clientId = "El246n9cf1minYI0YGcBVQ8971fK8Gfp";
        var self = this;

        self.auth = {};
        self.auth.login = function (login, password) {
            return $http.post('/api/auth/login', { login: login, password: password, clientId: clientId })
                .then(function (data) {
                    authStorage.save(data.data);
                    return data.status;
                })
                .catch(handleError);
        };
        self.auth.refresh = function () {
            return $http(prepareRequest("POST", "/api/auth/refresh", {}))
                .then(function (data) {
                    authStorage.save(data.data);
                    return data.status;
                })
                .catch(handleError);
        };

        self.survey = {};
        self.survey.get = function () {
            return $http(prepareRequest("GET", "/api/survey", {}))
                .then(function (data) {
                    return data.data;
                })
                .catch(handleError);
        };
        self.survey.post = function (survey) {
            return $http(prepareRequest("POST", "/api/survey", survey))
                .then(function (data) {
                    return data.data;
                })
                .catch(handleError);
        };
        self.survey.progress = function () {
            return $http(prepareRequest("GET", "/api/survey/users", {}))
                .then(function (data) {
                    return data.data;
                })
                .catch(handleError);
        };;
        self.survey.results = function () {
            return $http(prepareRequest("GET", "/api/survey/results", {}))
                .then(function (data) {
                    return data.data;
                })
                .catch(handleError);
        };
        self.survey.notify = function () {
            return $http(prepareRequest("POST", "/api/survey/notify"))
                .then(function (data) {
                    return data.data;
                })
                .catch(handleError);
        };

        self.user = {};
        self.user.current = function () {
            return $http(prepareRequest("GET", "/api/user/current", {}))
                .then(function (data) {
                    return data.data;
                })
                .catch(handleError);
        };
        self.user.all = function () {
            return $http(prepareRequest("GET", "/api/user/all", {}))
                .then(function (data) {
                    return data.data;
                })
                .catch(handleError);
        };

        dataRefresher.addPermanent(function () {
            self.auth.refresh();
        }, 300);

        function prepareRequest(method, url, payload) {
            return {
                method: method,
                url: url,
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Token " + clientId + ":" + authStorage.token()
                },
                data: payload
            }
        }

        function handleError(data) {
            if (data.status === 401) {
                authStorage.save(null);
                window.location = "/";
            }
            
            else { throw data.status; }
        }
    }

    authStorage.$inject = ["localStorageService"]

    function authStorage(localStorageService) {
        var current = null;
        var storageKey = "auth";
        var self = this;

        self.save = function (auth) {
            current = auth;
            localStorageService.set(storageKey, auth);
        };

        self.load = function () {
            current = localStorageService.get(storageKey);
        };

        self.token = function () {
            if (!current) { self.load(); }
            return current && current.Token;
        };

        self.isAdmin = function () {
            if (!current) { self.load(); }
            return current != null && current.Roles && current.Roles.indexOf("Admin") !== -1;
        };

        self.isAuthenticated = function () {
            if (!current) { self.load(); }
            return !!self.token();
        };
    }

    dataRefresher.$inject = ["$rootScope", "$timeout"];

    function dataRefresher ($rootScope, $timeout) {
        var self = this;
        self.permanent = [];
        self.temporary = [];

        function guid () {
            function s4() {
                return Math.floor((1 + Math.random()) * 0x10000)
                  .toString(16)
                  .substring(1);
            }
            return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
        }

        function addJob(collectionName) {
            var collection = self[collectionName];
            var result = {
                name    : collectionName, 
                index   : collection.length,
                uuid    : guid()
            };
            collection.push(result.uuid);
            return result;
        }

        function doJob (identifier, callback, delayInSeconds) {
            $timeout(function () {
                var collection = self[identifier.name];
                if (collection.length > identifier.index && collection[identifier.index] === identifier.uuid) {
                    callback();
                    doJob(identifier, callback, delayInSeconds);
                }
            }, delayInSeconds * 1000)
        }

        self.addTemporary = function (callback, delayInSeconds) {
            doJob(addJob("temporary"), callback, delayInSeconds);
        };

        self.addPermanent = function (callback, delayInSeconds) {
            doJob(addJob("permanent"), callback, delayInSeconds);
        };

        $rootScope.$on("$routeChangeStart", function () {
            self.temporary = [];
        });
    }

})();