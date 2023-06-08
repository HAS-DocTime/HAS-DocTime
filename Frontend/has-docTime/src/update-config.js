"use strict";
exports.__esModule = true;
var fs_1 = require("fs");
var angularJSON = require("../angular.json");
var environment_1 = require("./environments/environment")
angularJSON.projects['has-docTime'].architect.serve.options.host = environment_1.environment.host;
(0, fs_1.writeFileSync)('./angular.json', JSON.stringify(angularJSON, null, 2));
