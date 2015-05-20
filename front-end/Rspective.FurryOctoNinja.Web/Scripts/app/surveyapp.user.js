(function () {
    angular.module("surveyapp")
        .controller("UserController", user);

    user.$inject = ["dataRefresher", "api", "users"];

    function user(dataRefresher, api, users) {
        var self = this;

        self.updated = { Id: undefined, Name: undefined, Login: undefined, Password: undefined };
        self.created = { Id: undefined, Name:undefined, Login: undefined, Password: undefined };
        self.mode = -1;
        self.updatedIndex = -1;

        self.errors = {};
        self.users = users;

        self.edit = function (user, index) {
            self.mode = user.Id;
            angular.extend(self.updated, user);
            self.updated.Password = "";
            self.updatedIndex = index;
        };

        self.createDisabled = false;
        self.create = function () {
            self.createDisabled = true;
            self.errors.create = null;
            api.user.create(self.created)
                .then(function (user) {
                    self.users.push(user);
                    self.mode = -1;
                    self.createDisabled = false;
                    self.create.Id = null;
                    self.create.Name = null; 
                    self.create.Login = null;
                    self.create.Password = null;
                })
                .catch(function (error) {
                    if (error.status == 400) {
                        self.errors.create = error.data.OverallErrors;
                    }

                    self.createDisabled = false;
                });

            return false;
        };

        self.updateDisabled = false;
        self.update = function () {
            self.updateDisabled = true;
            self.errors.update = null;
            api.user.update(self.updated)
                .then(function (user) {
                    angular.extend(self.users[self.updatedIndex], user);
                    self.mode = -1;
                    self.updateDisabled = false;
                })
                .catch(function (error) {
                    if (error.status == 400) {
                        self.errors.update = error.data.OverallErrors;
                    }

                    self.updateDisabled = false;
                });

            return false;
        };

        self.deleteDisabled = false;
        self.delete = function (user, index) {
            if (!confirm("Are you sure yoiu want to delete user '" + user.Name + "' ?")) {
                return;
            }

            self.deleteDisabled = true;

            api.user.delete(user.Id)
                .then(function (user) {
                    self.users.splice(index, 1);
                    self.updateDisabled = false;
                })
                .catch(function (error) {
                    self.updateDisabled = false;
                });
        }

        dataRefresher.addTemporary(function () {
            api.user.all()
                .then(function (users) {
                    self.users = users;
                });
        }, 45);
    }
})();