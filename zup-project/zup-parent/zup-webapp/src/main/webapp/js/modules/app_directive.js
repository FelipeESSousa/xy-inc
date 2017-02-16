//#import app.js
/**
 * Diretiva para tratar datahora quando exibida na tela.
 */
app.directive('date', function() {
	return {
		require: 'ngModel',
		link: function(scope, element, attrs, controller) {
			controller.$formatters.push(function(value) {
				return moment(new Date(value*1000)).format(attrs.date)=="Invalid date"?"":moment(new Date(value*1000)).format(attrs.date);
			});
			controller.$parsers.push(function(value) {
				return moment(new Date(value*1000)).format(attrs.date)=="Invalid date"?"":moment(new Date(value*1000)).format(attrs.date);
			});
		}
	};
});
/**
 * Diretiva para tratar datahora quando exibida na tela.
 */
app.directive('dateTimeDirective', function() {
    return {
        require : 'ngModel',
        link : function(scope, element, attrs, controller) {
            controller.$formatters.push(function(value) {
                return !value || moment(value)._d === "Invalid date" ? "" : formatDate(moment(value)._d, 'dd/MM/yyyy HH:mm');
            });
            controller.$parsers.push(function(value) {
                return getDateFromFormat(value, 'dd/MM/yyyy HH:mm') === 0 ? "" : getDateFromFormat(value, 'dd/MM/yyyy HH:mm');
            });
        }
    };
});

/**
 * Controller para o arquivo _foot.html
 */
app.directive('limtToDate', ['$filter', function ($filter) {
    var dateFormat = "DD/MM/YYYY"
    return {
        restrict: 'A',
        link: function (scope, ielem, attrs) {
            scope.$watch(attrs.ngModel, function (v) {
                $filter('limitTo')(ielem.val(), -5);
            });
        }
    }
}]);

/**
 * Diretiva para tratar datahora quando exibida na tela.
 */
app.directive('cep', function() {
    return {
        require : 'ngModel',
        link : function(scope, element, attrs, controller) {
            controller.$formatters.push(function(value) {
                if (value === undefined || value === null || value === "") {
                    return "";
                }
                var formatter =new StringMask('00000-000');
                return formatter.apply(value);
            });
            controller.$parsers.push(function(value) {
                if (value === undefined || value === null || value === "") {
                    return "";
                }
                var formatter =new StringMask('00000-000');
                return formatter.apply(value);
            });
        }
    };
});

/**
 * Diretiva para tratar data quando exibida na tela.
 */
app.directive('dateDirective', function() {
    return {
        require : 'ngModel',
        link : function(scope, element, attrs, controller) {
            controller.$formatters.push(function(value) {
                return moment(value).format('DD/MM/YYYY') === "Invalid date" ? "" : moment(value).format('DD/MM/YYYY');
            });
            controller.$parsers.push(function(value) {
                return moment(value).format('DD/MM/YYYY') === "Invalid date" ? "" : moment(value).format('DD/MM/YYYY');
            });
        }
    };
});

/**
 * Diretiva datepickerModal.
 */
app.directive('datepickerModal', function() {
    return {
        restrict : 'A',
        require : 'ngModel',
        link : function(scope, element, attrs, ngModelCtrl) {
            // Adaptacao para setar o model como $dirty ao selecionar uma data.
            scope.$watch(function() {
                if (ngModelCtrl.$touched) {
                    ngModelCtrl.$setDirty();
                }
            }), $(function() {
                element.datetimepicker({
                    dateformat : 'dd/mm/yyyy',
                    language : 'pt-BR',
                    autoclose : true,
                    pickTime : false,
                    clearBtn : true,
                    beforeShow : function() {
                        $(".ui-datepicker").css('font-size', 12);
                    }
                });
            });
        }
    };
});

/**
 * Diretiva datetimepickerModal
 */
app.directive('datetimepickerModal', function() {
    return {
        restrict : 'A',
        require : 'ngModel',
        link : function(scope, element, attrs, ngModelCtrl) {
            // Adaptacao para setar o model como $dirty ao selecionar uma data.
            scope.$watch(function() {
                if (ngModelCtrl.$touched) {
                    ngModelCtrl.$setDirty();
                }
            }), $(function() {
                element.datetimepicker({
                    dateFormat : 'dd/mm/yyyy',
                    timeFormat : 'HH:mm',
                    language : 'pt-BR',
                    autoclose : false,
                    clearBtn : true
                });
            });
        }
    };
});

/**
 * Diretiva timepickerModal
 */
app.directive('timepickerModal', function() {
    return {
        restrict : 'A',
        require : 'ngModel',
        link : function(scope, element, attrs, ngModelCtrl) {
            // Adaptacao para setar o model como $dirty ao selecionar uma data.
            scope.$watch(function() {
                if (ngModelCtrl.$touched) {
                    ngModelCtrl.$setDirty();
                }
            }), $(function() {
                element.datetimepicker({
                    timeFormat : 'HH:mm',
                    pickDate : false,
                    language : 'pt-BR',
                    autoclose : true,
                    clearBtn : true
                });
            });
        }
    };
});

/**
 * Diretiva dateMask
 */
app.directive("dateMask", [ "$filter", function() {
    return {
        require : "ngModel",
        link : function(scope, element, attrs, ctrl) {
            var formato = 'DD/MM/YYYY';
            var tamanho = 10;
            var stringToDate = function(string) {
                var dateArray = string.split("/");
                var stringDate = dateArray[2] + '-' + dateArray[1] + '-' + dateArray[0];
                return new Date(stringDate);
            };

            var validaDate = function(value) {
                if (isNaN(value)) {
                    return "";
                }
                return moment(value).format(formato) === "Invalid date" ? "" : moment(value).format(formato);
            };

            var _formatDate = function(date) {
                date = date.replace(/[^0-9]+/g, "");
                if (date.length > 2) {
                    date = date.substring(0, 2) + "/" + date.substring(2);
                }
                if (date.length > 5) {
                    date = date.substring(0, 5) + "/" + date.substring(5, 9);
                }
                if (date.length === tamanho) {
                    var retorno = stringToDate(date);
                    return validaDate(retorno);
                }
                return date;
            };

            element.bind("keyup", function() {
                ctrl.$setViewValue(_formatDate(ctrl.$viewValue));
                ctrl.$render();
            });

            ctrl.$parsers.push(function(value) {
                if (value.length === tamanho) {
                    var retorno = stringToDate(value);
                    return validaDate(retorno);
                }
            });

            ctrl.$formatters.push(function(value) {
                return validaDate(value);
            });


        }
    };
} ]);

/**
 * Diretiva timeMask
 */
app.directive("timeMask", [ "$filter", function() {
    return {
        require : "ngModel",
        link : function(scope, element, attrs, ctrl) {
            var mostraSegundos = false;
            var tamanho = 5;
            var formato = "hh:mm";
            if (mostraSegundos) {
                formato = "hh:mm:ss";
                tamanho = 8;
            }

            var stringToDate = function(string) {
                var today = new Date();
                var todayString = moment(today).format('YYYY-MM-DD');
                var timeArray = string.split(":");
                var segundo = '00';
                if (mostraSegundos) {
                    segundo = timearray[2];
                }
                var stringDate = todayString + 'T' + timeArray[0] + ':' + timeArray[1] + ':' + segundo;

                return new Date(stringDate);
            };

            var validaDate = function(value) {
                if (isNaN(value)) {
                    return "";
                }
                return moment(value).format(formato) === "Invalid date" ? "" : moment(value).format(formato);
            };

            var _formatDate = function(date) {
                date = date.replace(/[^0-9]+/g, "");
                if (date.length > 2) {
                    date = date.substring(0, 2) + ":" + date.substring(2);
                }
                if (mostraSegundos && date.length > 5) {
                    date = date.substring(0, 5) + ":" + date.substring(5, 7);
                }
                if (date.length === tamanho) {
                    var retorno = stringToDate(date);
                    return validaDate(retorno);
                }
                return date;
            };

            element.bind("keyup", function() {
                ctrl.$setViewValue(_formatDate(ctrl.$viewValue));
                ctrl.$render();
            });

            ctrl.$parsers.push(function(value) {
                if (value.length === tamanho) {
                    var retorno = stringToDate(value);
                    return validaDate(retorno);
                }
            });

            ctrl.$formatters.push(function(value) {
                return validaDate(value);
            });
        }
    };
} ]);

/**
 * Diretiva dateTimeMask
 */
app.directive("dateTimeMask", [ "$filter", function() {
    return {
        require : "ngModel",
        link : function(scope, element, attrs, ctrl) {
            var mostraSegundos = false;
            var formato = "DD/MM/YYYY HH:mm";
            var tamanho = 16;
            if (mostraSegundos) {
                formato = "DD/MM/YYYY HH:mm:SS";
                tamanho = 19;
            }

            var stringToDate = function(string) {
                var dateArray = string.split("/");
                var ano = dateArray[2].split(" ");
                var timeArray = ano[1].split(":");
                var segundo = '00';
                if (mostraSegundos) {
                    segundo = timearray[2];
                }
                var stringDate = ano[0] + "-" + dateArray[1] + '-' + dateArray[0] + 'T' + timeArray[0] + ':' + timeArray[1] + ':' + segundo;

                return new Date(stringDate);
            };

            var validaDate = function(value) {
                if (isNaN(value)) {
                    return "";
                }
                return moment(value).format(formato) === "Invalid date" ? "" : moment(value).format(formato);
            };

            var _formatDate = function(date) {
                date = date.replace(/[^0-9]+/g, "");
                if (date.length > 2) {
                    date = date.substring(0, 2) + "/" + date.substring(2);
                }
                if (date.length > 5) {
                    date = date.substring(0, 5) + "/" + date.substring(5, tamanho);
                }
                if (date.length > 10) {
                    date = date.substring(0, 10) + " " + date.substring(10, tamanho);
                }
                if (date.length > 13) {
                    date = date.substring(0, 13) + ":" + date.substring(13, tamanho);
                }
                if (mostraSegundos && date.length > 16) {
                    date = date.substring(0, 16) + ":" + date.substring(16, tamanho);
                }
                if (date.length === tamanho) {
                    var retorno = stringToDate(date);
                    return validaDate(retorno);
                }
                return date;
            };

            element.bind("keyup", function() {
                ctrl.$setViewValue(_formatDate(ctrl.$viewValue));
                ctrl.$render();
            });

            ctrl.$parsers.push(function(value) {
                if (value.length === tamanho) {
                    var retorno = stringToDate(value);
                    return validaDate(retorno);
                }
            });

            ctrl.$formatters.push(function(value) {
                return validaDate(value);
            });
        }
    };
} ]);

// Diretiva que faz um input aceitar apenas numeros
app.directive('onlyDigits', function() {
    return {
        require : 'ngModel',
        restrict : 'A',
        link : function(scope, element, attr, ctrl) {
            function inputValue(val) {
                if (val) {
                    var digits = val.replace(/[^0-9]/g, '');
                    if (digits !== val) {
                        ctrl.$setViewValue(digits);
                        ctrl.$render();
                    }
                    return digits.toString();
                }
                return undefined;
            }
            ctrl.$parsers.push(inputValue);
        }
    };
});