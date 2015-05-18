(function () {
    angular.module("surveyapp")
        .service("api", api)
        .service("authStorage", authStorage)

    api.$inject = ["$http", "authStorage"];

    function api($http, authStorage) {
        var clientId = "El246n9cf1minYI0YGcBVQ8971fK8Gfp";
        var that = this;
        that.auth = {
            login: apiAuthLogin,
            refresh: apiAuthRefresh
        };
        that.survey = {
            get: apiGetSurvey,
            post: apiPostSurvey,
            users: null,
            results: null
        };

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

        function apiAuthLogin(login, password) {
            return $http.post('/api/auth/login', { login: login, password: password, clientId: clientId })
                .then(function (data) {
                    authStorage.save(data.data);
                    return data.status;
                })
                .catch(handleError);
        }

        function apiAuthRefresh() {
            return $http(prepareRequest("POST", "/api/auth/refresh", {}))
                .then(function (data) {
                    authStorage.save(data.data);
                    return data.status;
                })
                .catch(handleError);
        }

        function apiGetSurvey() {
            return $http(prepareRequest("GET", "/api/survey", {}))
                .then(function (data) {
                    return data.data;
                })
                .catch(handleError);
        }

        function apiPostSurvey(survey) {
            return $http(prepareRequest("POST", "/api/survey/post", survey))
                .then(function (data) {
                })
                .catch(handleError);
        }
    }

    authStorage.$inject = ["localStorageService"]

    function authStorage(localStorageService) {
        var current = null;
        var storageKey = "auth";
        var that = this;
        that.save = authSave;
        that.load = authLoad;
        that.token = authGetToken;

        function authSave(auth) {
            current = auth;
            localStorageService.set(storageKey, auth);
        }

        function authLoad() {
            current = localStorageService.get(storageKey);
        }

        function authGetToken() {
            if (!current) { that.load(); }
            return current && current.Token;
        }
    }
})();