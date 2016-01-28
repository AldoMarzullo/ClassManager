define('ace/mode/dlv', function(require, exports, module) {

var oop = require("ace/lib/oop");
var TextMode = require("ace/mode/text").Mode;
var ExampleHighlightRules = require("ace/mode/dlv_highlight_rules").ExampleHighlightRules;

var Mode = function() {
    this.HighlightRules = ExampleHighlightRules;
};
oop.inherits(Mode, TextMode);

(function() {
    // Extra logic goes here. (see below)
}).call(Mode.prototype);

exports.Mode = Mode;
});

define('ace/mode/dlv_highlight_rules', function(require, exports, module) {

var oop = require("ace/lib/oop");
var TextHighlightRules = require("ace/mode/text_highlight_rules").TextHighlightRules;

var ExampleHighlightRules = function() {

    this.$rules = {
    	start: [{
		  	token : "",
		  	regex : "",
		  	next: "rule"
		}],
		rule: [
		{
			token: "variable",
			regex: "[a-z]+",
			next: "parOp"
		},
		{
			token: "keyword",
			regex: ":-",
			next: "start"
		},
		{
			token: "keyword",
			regex: "\\.",
			next: "start"
		}
		],
		parOp: [{
			token: "text.bold",
			regex: "\\(",
			next:  "parCont"
		}
		],
		parCont:[
		{
			token: "italic",
			regex: "( )*[a-z][a-zA-Z0-9]*",
			next: "parCont"
		},
		{
			token: "keyword",
			regex: "\\,",
			next: "parCont"
		},
		{
			token: "text",
			regex: "",
			next: "parCl"
		}
		],
		parCl: [{
			token: "text.bold",
			regex: "\\)",
			next:  "start"
		}]
	};
    


}

oop.inherits(ExampleHighlightRules, TextHighlightRules);

exports.ExampleHighlightRules = ExampleHighlightRules;
});