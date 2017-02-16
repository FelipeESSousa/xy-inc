//METODO DE EXIBICAO DE MENSAGENS
//--------Exemplos------
//ShowMessage({tipo: 'success', mensagem : 'Esta é uma mensagem de sucesso!' });
//ShowMessage({tipo: 'info', mensagem : 'Esta é uma mensagem de informação!',  titulo: 'Este e um titulo'});
//ShowMessage({tipo: 'warning', mensagem : 'Esta mensagem sera exibida dentro da div informada em apendElement!',  titulo: 'Este e um titulo', apendElement: 'divTeste'});
//ShowMessage({tipo: 'popUp', mensagem : 'esta e uma mensagem em Modal!'});
//ShowMessage({tipo: 'popUp', mensagem : 'tem certeza que deseja excluir!', delegate: Excluir, lblDelegate: 'Excluir', titulo: 'Confirme exclusao'});
//function Excluir(){ //todo: faça a chamada ajax aqui.}
//ShowMessage({tipo: 'info', mensagem : 'tem certeza que deseja excluir!', delegate:  function(){alert('todo: faça a chamada ajax aqui');}, lblDelegate: 'Excluir'});

function ShowMessage(options) {
	

  var settings = $.extend({
      mensagem: '',
      titulo: '',
      tipo: 'info',
      delegate: null,
      lblDelegate: 'Ok',
      btnDelegateClass: 'btn-danger',
      apendElement: 'MsgPadrao',
      labelBotaoFechar: 'Fechar',
      closeAutomatico: true,
      escondeBotaoFechar: false,
      fecharClickOut: false
      
  }, options);

  var htmlMensagem = "";
    
  if (settings.tipo === "popUp") {
      if (settings.titulo === '')
          settings.titulo = 'Atenção';

      htmlMensagem += '<div class="modal_message modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" >';
      htmlMensagem += '<div class="modal-dialog">';
      htmlMensagem += '<div class="modal-content">';

      htmlMensagem += '<div class="modal-header">';

      if (!settings.escondeBotaoFechar)
          htmlMensagem += "<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>";
      else
          $('body').append('<div class="modal-backdrop in modal-no-close"></div>');

      htmlMensagem += "<h4 class='modal-title' id='myModalLabel'>" + settings.titulo + "</h4>";
      htmlMensagem += "</div>";
      htmlMensagem += "<div class='modal-body'>";
      htmlMensagem += "<p>" + settings.mensagem + "</p>";
      htmlMensagem += "</div>";

      htmlMensagem += "<div class='modal-footer'>";

      if (settings.delegate !== null)
          htmlMensagem += "<button id='" + settings.apendElement + "btnEventoMensagem' type='button' class='btn " + settings.btnDelegateClass + "'>" + settings.lblDelegate+ "</button>";

      if (!settings.escondeBotaoFechar)
          htmlMensagem += "<button type='button' class='btn btn-warning' data-dismiss='modal'>" + settings.labelBotaoFechar + "</button>";
      htmlMensagem += "</div>";
      htmlMensagem += "</div>";
      htmlMensagem += "</div>";
      htmlMensagem += "</div>";
      
      $("#" + settings.apendElement + "").html(htmlMensagem);
      $('#' + settings.apendElement + ' .modal_message').modal({ show: true, backdrop: !settings.fecharClickOut ? 'static' : false});
      $('#' + settings.apendElement + ' .modal_message').on('hidden.bs.modal', function () {
          $('.modal-backdrop.in').remove();
      });
      
      if (settings.delegate !== null) {
          $("#" + settings.apendElement + "btnEventoMensagem").click(settings.delegate);
          $("#" + settings.apendElement + "btnEventoMensagem").click(function () {
              $('#' + settings.apendElement + ' .modal_message').modal('hide');
              $('.modal-no-close').remove();
          });
      }
  }
  else {
    var alertId =  settings.apendElement + '_alert_' + Math.random()*9999|1000;
      htmlMensagem += '<div id="'+ alertId + '" class="alert alert-block alert-' + settings.tipo + ' fade in">';

      if (!settings.escondeBotaoFechar)
          htmlMensagem += "<button type='button' class='close' data-dismiss='alert' aria-hidden='true' id='btnfefe'>×</button>";

      if (settings.titulo !== '')
          htmlMensagem += "<h4>" + settings.titulo + "</h4>";

      htmlMensagem += "<p>" + settings.mensagem + "</p>";

      if (settings.delegate !== null)
          htmlMensagem += "<p><button id='" + settings.apendElement + "btnEventoMensagem' type='button' class='btn btn-primary' >" + settings.lblDelegate + "</button></p>";

      htmlMensagem += "</div>";
      
      $("#" + settings.apendElement + "").html(htmlMensagem);
      
    // Volta para o topo da página
      window.scrollTo(0, 0);
      
      if (settings.closeAutomatico) {
          setTimeout(function () {
              $('#' + alertId).alert('close');
          }, 10000);
      }
      
      if (settings.delegate !== null) {
          $("#" + settings.apendElement + "btnEventoMensagem").click(settings.delegate);
         
          $("#" + settings.apendElement + "btnEventoMensagem").click(function () {
              $(".alert").alert('close');
          });            
      }
  }
  
  // Habilitar botoes
  $('button[ng-disabled*=bloqueioRequisicao]').attr('disabled', false);
}

//METODO PARA EXIBIR MENSAGENS PADRAO ATRAVES DO PATTERN 
function ShowMessagePattern(options,msg){
	options.mensagem = msg;
	ShowMessage(options);
}