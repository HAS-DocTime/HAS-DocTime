"use strict";
exports.__esModule = true;
var fs_1 = require("fs");
var environment_1 = require("./environments/environment");
var angularJSON = require("../angular.json");
angularJSON.projects['has-docTime'].architect.serve.options.host = environment_1.environment.host;
(0, fs_1.writeFileSync)('./angular.json', JSON.stringify(angularJSON, null, 2));
