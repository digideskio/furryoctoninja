(function () {
    angular.module("surveyapp")
        .service("api", api)
        .service("authStorage", authStorage)

    api.$inject = ["$http", "authStorage"];

    function api($http, authStorage) {
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
                })
                .catch(handleError);
        };
        self.survey.users = function () {
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
        };;

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
            if (data.status === 401) { window.location = "/"; }
            else { throw data.status; }
        }
    }

    authStorage.$inject = ["localStorageService"]

    function authStorage(localStorageService) {
        var current = null;
        var storageKey = "auth";
        var self = this;

        self.save = function(auth) {
            current = auth;
            localStorageService.set(storageKey, auth);
        };

        self.load = function() {
            current = localStorageService.get(storageKey);
        };

        self.token = function() {
            if (!current) { self.load(); }
            return current && current.Token;
        };

        self.isAdmin = function() {
            if (!current) { self.load(); }
            return current != null && current.Roles && current.Roles.indexOf("Admin") !== -1;
        };

        self.isAuthenticated = function() {
            if (!current) { self.load(); }
            return !!self.token();
        };
    }
})();