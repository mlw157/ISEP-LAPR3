package utils;


import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 *Class to build date
 */
public class Date {

    private final int day;
    private final int month;
    private final int year;
    private final HashMap<Integer,Integer> months;

    /**
     * Constructor of the class
     * @param day
     * @param month
     * @param year
     *
     * @author Pedro Campos <1211511@isep.ipp.pt>
     */
    public Date(int day, int month, int year) {
        months = generateMonths(Year.isLeap(year));
        validateDate(day,month);
        this.day = day;
        this.month = month;
        this.year = year;

    }

    /**
     * Method to generate all days inside a given month
     * @param leap boolean to tell if the year is leap or not
     * @return map with all the days of the given months
     *
     * @author Diogo Teixeira <1200904@isep.ipp.pt>
     */
    public static HashMap<Integer,Integer> generateMonths (boolean leap){

        HashMap<Integer,Integer> months = new HashMap<>();
        int [] month = {1,2,3,4,5,6,7,8,9,10,11,12};

        List<Integer> month31 = new ArrayList<>(){{
            add(1);
            add(3);
            add(5);
            add(7);
            add(8);
            add(10);
            add(12);
        }
        };
        List<Integer> month30 = new ArrayList<>(){{
            add(4);
            add(6);
            add(9);
            add(11);
        }
        };
        if(leap){
            for (Integer s : month) {
                if (month31.contains(s)) {
                    months.put(s, 31);

                } else if (month30.contains(s)) {
                    months.put(s, 30);
                } else {
                    months.put(s, 29);
                }
            }
        } else {
            for (Integer s : month) {
                if (month31.contains(s)) {
                    months.put(s, 31);

                } else if (month30.contains(s)) {
                    months.put(s, 30);
                } else {
                    months.put(s, 28);
                }
            }
        }
        return months;
    }

    /**
     * Method to validate the date inputted
     * @param day
     * @param month
     *
     * @author Diogo Teixeira <1200904@isep.ipp.pt>
     */
    private void validateDate(int day,int month){

        if (month > 12 || month < 0){
            throw new IllegalArgumentException("Month incorrect.");
        }


        if(day > months.get(month)){
            throw new IllegalArgumentException("Invalid day");
        }
    }

    /**
     * Method to get the day
     * @return day
     *
     * @author Nuno Cunha <1211689@isep.ipp.pt>
     */
    public int getDay() {
        return day;
    }

    /**
     * Method to get the month
     * @return day
     *
     * @author Nuno Cunha <1211689@isep.ipp.pt>
     */
    public int getMonth() {
        return month;
    }

    /**
     * Method to get the year
     * @return day
     *
     * @author Nuno Cunha <1211689@isep.ipp.pt>
     */
    public int getYear() {
        return year;
    }

    /**
     * Method equals of the class date
     * @param o object
     * @return boolrsn with the result
     *
     * @author Nuno Cunha <1211689@isep.ipp.pt>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return day == date.day && month == date.month && year == date.year && Objects.equals(months, date.months);
    }

    /**
     * Method to obtain the hashcode of the class date
     *
     * @return hashcode
     *
     * @author Nuno Cunha <1211689@isep.ipp.pt>
     */
    @Override
    public int hashCode() {
        return Objects.hash(day, month, year, months);
    }

    public static Date getCurrentDate(){

        LocalDateTime dateTime = LocalDateTime.now();

        return new Date(dateTime.getDayOfMonth(),dateTime.getMonthValue() , dateTime.getYear());
    }


    public boolean isWithinTimeInterval(Date date1,Date date2){
        if(this.year>date1.getYear()||(this.year==date1.getYear() && this.month>date1.getMonth())||(this.year==date1.getYear() && this.month==date1.getMonth() && this.day>date1.getDay())){
            return this.year < date2.getYear() || (this.year == date2.getYear() && this.month < date2.getMonth()) || (this.year == date2.getYear() && this.month == date2.getMonth() && this.day < date2.getDay());
        }
        return false;
    }

    public boolean moreRecent(Date date2){
        return this.year > date2.getYear() || (this.year == date2.getYear() && this.month > date2.getMonth()) || (this.year == date2.getYear() && this.month == date2.getMonth() && this.day > date2.getDay());
    }



}