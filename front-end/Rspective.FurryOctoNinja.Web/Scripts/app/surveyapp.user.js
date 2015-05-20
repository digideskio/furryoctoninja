﻿(function () {
    angular.module("surveyapp")
        .controller("UserController", user);

    user.$inject = ["dataRefresher", "api", "users"];

    function user(dataRefresher, api, users) {
        var self = this;

        self.edited = { Id: undefined, Name:undefined, Login: undefined, Password: undefined };
        self.created = { Id: undefined, Name:undefined, Login: undefined, Password: undefined };
        self.mode = -1;

        self.errors = {};
        self.users = users;

        self.edit = function (user) {
            self.mode = user.Id;
            angular.extend(self.edited, user);
            self.edited.Password = "";
        };

        self.createDisabled = false;
        self.create = function () {
            self.createDisabled = true;
            api.user.create(self.created)
                .then(function (user) {
                })
                .catch(function (error) {
                    if (error.status == 400) {
                        self.errors.create = error.data.OverallError;
                    }

                    self.createDisabled = false;
                });

            return false;
        };

        self.updateDisabled = false;
        self.update = function () {
            self.updateDisabled = true;
            api.user.update(self.edited)
                .then(function (user) {
                })
                .catch(function (error) {
                    if (error.status == 400) {
                        self.errors.update = error.data.OverallError;
                    }

                    self.updateDisabled = false;
                });

            return false;
        };

        dataRefresher.addTemporary(function () {
            api.user.all()
                .then(function (users) {
                    self.users = users;
                });
        }, 45);
    }
})();