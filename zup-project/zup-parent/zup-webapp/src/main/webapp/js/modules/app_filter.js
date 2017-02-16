/**
 * Filters out all duplicate items from an array by checking the specified key
 *
 * @param [key]
 *            {string} the name of the attribute of each object to compare for
 *            uniqueness if the key is empty, the entire object will be compared
 *            if the key === false then no filtering will be performed
 * @return {array}
 */
angular.module('ui.filters', []);
angular.module('ui.filters').filter('unique', function() {

    return function(items, filterOn) {

        if (filterOn === false) {
            return items;
        }

        if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
            var newItems = [];

            var extractValueToCompare = function(item) {
                if (angular.isObject(item) && angular.isString(filterOn)) {
                    return item[filterOn];
                } else {
                    return item;
                }
            };

            angular.forEach(items, function(item) {
                var isDuplicate = false;

                for (var i = 0; i < newItems.length; i++) {
                    if (angular.equals(extractValueToCompare(newItems[i]), extractValueToCompare(item))) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    newItems.push(item);
                }

            });
            items = newItems;
        }
        return items;
    };
});

/**
 * Retorna apenas os usuarios que contem pessoa e unidade associada
 */
angular.module('ui.filters').filter('usuariosComPessoa', function() {

    return function(items, filterOn) {

        if (filterOn === false) {
            return items;
        }

        if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
            var newItems = [];
            angular.forEach(items, function(item) {
                if (item.pessoa && item.pessoa.unidade) {
                    newItems.push(item);
                }
            });
            items = newItems;
        }
        return items;
    };
});

/**
 * Corta string no caracter passado e mostra apenas o indice passado
 */
angular.module('ui.filters').filter('split', function() {
    return function(input, splitChar, splitIndex) {
        // do some bounds checking here to ensure it has that index
        return input.split(splitChar)[splitIndex];
    };
});

/**
 * Filter para placa
 */
app.filter('placa', function() {
    return function(placa) {
        if (placa === undefined || placa === null || placa === "") {
            return "";
        }
        return placa.replace(/[^a-zA-Z]+/g, '') + '-' + placa.replace(/\D/g, '');
    };
});

/**
 * Filter numberScore
 */
app.filter('numberScore', function($filter) {
    return function(input, fractionSize) {
        if (input === undefined || input === null || input === "") {
            return "";
        }
        var number = $filter('number')(input, fractionSize);
        number = number.replace(/,/g, '.');
        return number;
    };
});

/**
 * Filter telefoneSemDDD
 */
app.filter('telefoneSemDDD', function() {
    return function(input) {
        if (input === undefined || input === null || input === "") {
            return "";
        }
        if (input.length <= 4) {
            return input;
        }
        var formatter = input.length === 9 ? new StringMask('00000-0000') : new StringMask('0000-0000');
        return formatter.apply(input);
    };
});

/**
 * Filter cep
 */
app.filter('cep', function() {
    return function(input) {
        if (input === undefined || input === null || input === "") {
            return "";
        }
        var formatter =new StringMask('00000-000');
        return formatter.apply(input);
    };
});
