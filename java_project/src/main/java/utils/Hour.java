package utils;
import java.util.Calendar;
import java.util.Objects;

public class Hour implements Comparable<Hour>{


    /**
     * As horas do tempo.
     */
    private int hours;

    /**
     * Os minutos do tempo.
     */
    private int minutes;

    /**
     * Os segundos do tempo.
     */
    private int seconds;

    /**
     * As horas por omissão.
     */
    private static final int OMITTED_HOURS = 0;

    /**
     * Os minutos por omissão.
     */
    private static final int OMITTED_MINUTES = 0;

    /**
     * Os segundos por omissão.
     */
    private static final int OMITTED_SECONDS = 0;

    /**
     * Constrói uma instância de Tempo recebendo as horas, minutos e segundos.
     *
     * @param hours    as horas do tempo.
     * @param minutes  os minutos do tempo.
     * @param seconds os segundos do tempo.
     */
    public Hour(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Constrói uma instância de Tempo recebendo as horas e os minutos e
     * assumindo os segundos por omissão.
     *
     * @param hours   as horas do tempo.
     * @param minutes os minutos do tempo.
     */
    public Hour(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
        seconds = OMITTED_SECONDS;
    }

    /**
     * Constrói uma instância de Tempo recebendo as horas e assumindo os minutos
     * e os segundos, por omissão.
     *
     * @param hours as horas do tempo.
     */
    public Hour(int hours) {
        this.hours = hours;
        minutes = OMITTED_MINUTES;
        seconds = OMITTED_SECONDS;
    }

    /**
     * Constrói uma instância de Tempo com as horas, minutos e segundos, por
     * omissão.
     */
    public Hour() {
        hours = OMITTED_HOURS;
        minutes = OMITTED_MINUTES;
        seconds = OMITTED_SECONDS;
    }

    /**
     * Constrói uma instância de Tempo com as mesmas caraterísticas do tempo
     * recebido por parâmetro.
     *
     * @param outroTempo o tempo com as características a copiar.
     */
    public Hour(Hour outroTempo) {
        hours = outroTempo.hours;
        minutes = outroTempo.minutes;
        seconds = outroTempo.seconds;
    }

    /**
     * Devolve as horas do tempo.
     *
     * @return horas do tempo.
     */
    public int getHours() {
        return hours;
    }

    /**
     * Devolve os minutos do tempo.
     *
     * @return minutos do tempo.
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Devolve os segundos do tempo.
     *
     * @return segundos do tempo.
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Modifica as horas do tempo.
     *
     * @param hours as novas horas do tempo.
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * Modifica os minutos do tempo.
     *
     * @param minutes os novos minutos do tempo.
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Modifica os segundos do tempo.
     *
     * @param seconds os novos segundos do tempo.
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Modifica as horas, os minutos e os segundos do tempo.
     *
     * @param horas    as novas horas do tempo.
     * @param minutos  os novos minutos do tempo.
     * @param segundos os novos segundos do tempo.
     */
    public void setTempo(int horas, int minutos, int segundos) {
        this.hours = horas;
        this.minutes = minutos;
        this.seconds = segundos;
    }

    /**
     * Devolve a descrição textual do tempo no formato: %02d:%02d:%02d AM/PM.
     *
     * @return caraterísticas do tempo.
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d %s",
                (hours == 12 || hours == 0) ? 12 : hours % 12,
                minutes, seconds, hours < 12 ? "AM" : "PM");
    }

    /**
     * Devolve o tempo no formato: %02d%02d%02d.
     *
     * @return caraterísticas do tempo.
     */
    public String toStringHHMMSS() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Compara o tempo com o objeto recebido.
     *
     * @param outroObjeto o objeto a comparar com o tempo.
     * @return true se o objeto recebido representar outro tempo equivalente ao
     *         tempo. Caso contrário, retorna false.
     */
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        Hour outroTempo = (Hour) outroObjeto;
        return hours == outroTempo.hours && minutes == outroTempo.minutes
                && seconds == outroTempo.seconds;
    }

    /**
     * Compara o tempo com o outro tempo recebido por parâmetro.
     *
     * @param outroTempo o tempo a ser comparado.
     * @return o valor 0 se o outroTempo recebido é igual ao tempo; o valor -1
     *         se o outroTempo for posterior ao tempo; o valor 1 se o outroTempo
     *         for anterior ao tempo.
     */
    @Override
    public int compareTo(Hour outroTempo) {
        return (outroTempo.isBigger(this)) ? -1 : (isBigger(outroTempo)) ? 1 : 0;
    }

    /**
     * Aumenta o tempo em um segundo.
     */
    public void tick() {
        seconds = ++seconds % 60;
        if (seconds == 0) {
            minutes = ++minutes % 60;
            if (minutes == 0) {
                hours = ++hours % 24;
            }
        }
    }

    /**
     * Devolve true se o tempo for maior do que o tempo recebido por parâmetro.
     * Se o tempo for menor ou igual ao tempo recebido por parâmetro, devolve
     * false.
     *
     * @param outroTempo o outro tempo com o qual se compara o tempo.
     * @return true se o tempo for maior do que o tempo recebido por parâmetro,
     *         caso contrário devolve false.
     */
    public boolean isBigger(Hour outroTempo) {
        return toSeconds() >= outroTempo.toSeconds();
    }

    /*
     * Solução alternativa
     * public boolean isMaior(Tempo outroTempo){
     *      if ( horas > outroTempo.horas ||
     *          (horas==outroTempo.horas && minutos>outroTempo.minutos) ||
     *          (horas==outroTempo.horas && minutos==outroTempo.minutos &&
     *           segundos > outroTempo.segundos) )
     *         return true;
     *      return false;
     * }
     */

    /**
     * Devolve true se o tempo for maior do que o tempo (horas, minutos e
     * segundos) recebido por parâmetro. Se o tempo for menor ou igual ao tempo
     * (horas, minutos e segundos) recebido por parâmetro, devolve false.
     *
     * @param horas    as outras horas do tempo com o qual se compara o tempo.
     * @param minutos  os outros minutos do tempo com o qual se compara o tempo.
     * @param segundos os outros segundos do tempo com o qual se compara o tempo.
     * @return true se o tempo for maior do que o tempo (horas, minutos e
     *         segundos) recebido por parâmetro, caso contrário devolve false.
     */
    public boolean isBigger(int horas, int minutos, int segundos) {
        Hour outroTempo = new Hour(horas, minutos, segundos);
        return this.toSeconds() > outroTempo.toSeconds();
    }

    /**
     * Devolve a diferença em segundos entre o tempo e o tempo recebido por
     * parâmetro.
     *
     * @param outroTempo o outro tempo com o qual se compara o tempo para
     *                   calcular a diferença em segundos.
     * @return diferença em segundos entre o tempo e o tempo recebido por
     *         parâmetro.
     */
    public int differenceInSeconds(Hour outroTempo) {
        return Math.abs(toSeconds() - outroTempo.toSeconds());
    }

    /**
     * Devolve uma instância Tempo representativa da diferença entre o tempo e
     * o tempo recebido por parâmetro.
     *
     * @param outroTempo o outro tempo com o qual se compara o tempo para obter
     *                   uma instãncia Tempo representativa da diferença entre
     *                   o tempo e o tempo recebido por parâmetro.
     * @return instância Tempo representativa da diferença entre o tempo e o
     *         tempo recebido por parâmetro.
     */
    public Hour differenceInTime(Hour outroTempo) {
        int dif = differenceInSeconds(outroTempo);
        int s = dif % 60;
        dif = dif / 60;
        int m = dif % 60;
        int h = dif / 60;
        return new Hour(h, m, s);
    }

    /**
     * Devolve o tempo atual do sistema.
     *
     * @return o tempo atual do sistema.
     */
    public static Hour currentTime() {
        Calendar now = Calendar.getInstance();
        int hora = now.get(Calendar.HOUR_OF_DAY);
        int minuto = now.get(Calendar.MINUTE);
        int segundo = now.get(Calendar.SECOND);
        return new Hour(hora,minuto,segundo);
    }

    /**
     * Devolve o número de segundos correspondente ao tempo.
     *
     * @return número de segundos correspondente ao tempo.
     */
    private int toSeconds() {
        return hours * 3600 + minutes * 60 + seconds;
    }

    public Hour addMinutes (int addedMinutes){
        int hours = this.hours;
        int minutes = this.minutes + addedMinutes;
        int seconds = this.seconds;



        if(minutes >= 60){

            while(minutes >= 60){

                minutes = minutes - 60;
                hours ++;

                if(hours == 24){
                    hours = 0;
                }

            }
        }

        return new Hour(hours,minutes,seconds);

    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes, seconds);
    }
}