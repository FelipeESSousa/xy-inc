package br.com.zup.negocio.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe Utils responsavel por prover metodos de controle de data e hora
 * 
 *
 */
public class CalendarUtil {

    /**
     * Construtor privado para que a classe não seja instanciada 
     */
    private CalendarUtil(){
    }

    /**
     * Metodo Util responsavel por retornar a data e hora corrente
     * 
     * @return String data formatada em dd-MM-yyyy-HH-mm-ss
     */
    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime()); //08-06-2014-16-00-22
    }

    /**
     * Metodo Util responsavel por retornar a data formatada que esta gravada no banco
     * como String sem formatação.
     * 
     * @return String data formatada em dd-MM-yyyy-HH-mm-ss
     */
    public static String convertDate(String dataBanco) throws Exception{

        SimpleDateFormat dfEntrada = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat dfSaida = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date data = dfEntrada.parse(dataBanco);
            return dfSaida.format(data);

        } catch (ParseException e) {
            throw new Exception("Falha ao converter data: "+ dataBanco);
        }


    }
}
