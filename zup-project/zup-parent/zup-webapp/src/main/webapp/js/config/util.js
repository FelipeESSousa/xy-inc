/*
File used to store the global functions used by the Siaep project.
*/

/**
 * Volta para o topo da página
 */
var voltarParaTopo = function() {
	window.scrollTo(0, 0);
	$( 'html, body' ).animate( { scrollTop: 0 }, 'slow' );
};


/**
 * Trata campos numéricos e não permite que seja colado nenhum tipo de string
 * 
 * Exemplo de utilização:
 * Todos que tem a classe numberOnly terão este comportamento
 *
 *	Adicione no seu controler
 * $(".numberOnly").ForceNumericOnly();
 * 
 * E adicione no seu input a classe numberOnly
 */
jQuery.fn.ForceNumericOnly = function(){
    return this.each(function()
    {
        $(this).keydown(function(e)
        {
            var key = e.charCode || e.keyCode || 0;
            // allow backspace, tab, delete, enter, arrows, numbers and keypad numbers ONLY
            // home, end, period, and numpad decimal
            return (
                key == 8 || 
                key == 9 ||
                key == 13 ||
                key == 46 ||
                key == 110 ||
                key == 190 ||
                (key >= 35 && key <= 40) ||
                (key >= 48 && key <= 57) ||
                (key >= 96 && key <= 105));
        });
        
        $(this).focusout(function(e){
        	if(isNaN(Number(this.value))){
        		this.value = "";
        	}
        });
    });
};


/**
* Valida um campo para aceitar apenas números
*/
function numbersonly(myfield, e, dec) {
	var key;
	var keychar;

	if (window.event)
		key = window.event.keyCode;
	else if (e)
		key = e.which;
	else
		return true;
	keychar = String.fromCharCode(key);

	// control keys
	if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13)
			|| (key == 27))
		return true;

	// numbers
	else if ((("0123456789").indexOf(keychar) > -1))
		return true;

	// decimal point jump
	else if (dec && (keychar == ".")) {
		myfield.form.elements[dec].focus();
		return false;
	} else
		return false;
}

/**
 * Remove acentos de caracteres
 * @param  {String} stringComAcento [string que contem os acentos]
 * @return {String}                 [string sem acentos]
 */
function removerAcentos( newStringComAcento ) {
  var string = newStringComAcento;
	var mapaAcentosHex 	= {
		a : /[\xE0-\xE6]/g,
		e : /[\xE8-\xEB]/g,
		i : /[\xEC-\xEF]/g,
		o : /[\xF2-\xF6]/g,
		u : /[\xF9-\xFC]/g,
		c : /\xE7/g,
		n : /\xF1/g
	};
 
	for ( var letra in mapaAcentosHex ) {
		var expressaoRegular = mapaAcentosHex[letra];
		string = string.replace( expressaoRegular, letra );
	}
 
	return string;
}

/**
 * @author: Aplica estilo cinza em textAreas.
 */
function aplicarTextAreaBackgroundCinza(){
	if($("textarea").is(":disabled")){
		$("textarea").css("background-color","#C0C0C0");
	}
};



/**
 * Verifica se o value do elemento html é um datetime do tipo DD/MM/YYYY hh:mm:ss.
 * @param  {String} date
 * @return {Boolean}
 */
var isDateTime = function(date) {
	var parts = date.value.replace(/\//g,'-');
	var dt  = parts.split(/\-|\s/)
	var dateTime = new Date(dt.slice(0,3).reverse().join('/')+' '+dt[3]);

	var isVal = (new Date(dateTime) !== "Invalid Date" && !isNaN(new Date(dateTime)) ) ? true : false;
    if(!isVal)
    	document.getElementById(date.id).value = "";
}

