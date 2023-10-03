package Controller;


import Domain.Model.IrrigationCalendar;
import Domain.Model.IrrigationController;
import Domain.Model.Plot;
import utils.Hour;
import utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Controller class for importing irrigation data from files (US306)
 * @author : Pedro Campos <1211511@isep.ipp.pt>
 */
public class ImportIrrigationDataController {

    public ImportIrrigationDataController() {

    }

    /**
     * Imports the irrigation information from a file
     * @param file the file to import from
     * @throws FileNotFoundException if the file is not found
     */
    public void importIrrigationController(String file) throws FileNotFoundException {
        IrrigationController irrigationController = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationController();
        Scanner sc = new Scanner(new File(file));
        ArrayList<Hour> irrigationHours = new ArrayList<>();
        int lineNumber = 0;
        String firstLine = sc.nextLine();
        String[] hours = firstLine.split(",");
        for (String s : hours) {
            String[] separatedHour = s.split(":");
            Hour hour = new Hour(Integer.parseInt(separatedHour[0]), Integer.parseInt(separatedHour[1]));
            irrigationHours.add(hour);
        }
        irrigationController.setIrrigationHours(irrigationHours);
        HashMap<Plot, Pair<Integer, String>> irrigationValuesMap = new HashMap<>();
        while (sc.hasNextLine()) {
            try {
                String line = sc.nextLine();
                String[] elements = line.split(",");
                String plotName = elements[0];
                Plot plot = new Plot(plotName);
                int irrigationTime = Integer.parseInt(elements[1]);
                String irrigationType = elements[2];
                Pair<Integer, String> irrigationValues = new Pair<>(irrigationTime, irrigationType);
                irrigationValuesMap.put(plot, irrigationValues);

            } catch (Exception e) {
                System.out.printf("Error in line: %d %s ", lineNumber, e.getCause());
            }
            lineNumber++;
        }
        irrigationController.setIrrigationValuesMap(irrigationValuesMap);
    }

    public void fillIrrigationCalendar(){
        IrrigationCalendar irrigationCalendar = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationCalendar();
        IrrigationController irrigationController = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationController();
        HashMap<Plot, Pair<Integer, String>> irrigationValuesMap = irrigationController.getIrrigationValuesMap();
        ArrayList<Hour> irrigationHours = irrigationController.getIrrigationHours();
        TreeMap<Date, LinkedList<Pair<Plot, Hour>>> daysIrrigationMap = irrigationCalendar.getDaysIrrigationMap();
        for (Date date : daysIrrigationMap.keySet()){
            int day = date.getDate();
            if (day % 2 == 0){
            addToDay(date, "p");
            } else {
                addToDay(date, "i");
            }
        }

    }


    public void addToDay(Date date, String dayType){
        int offsetTime = 0;
        IrrigationCalendar irrigationCalendar = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationCalendar();
        IrrigationController irrigationController = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationController();
        HashMap<Plot, Pair<Integer, String>> irrigationValuesMap = irrigationController.getIrrigationValuesMap();
        ArrayList<Hour> irrigationHours = irrigationController.getIrrigationHours();
        TreeMap<Date, LinkedList<Pair<Plot, Hour>>> daysIrrigationMap = irrigationCalendar.getDaysIrrigationMap();
        LinkedList<Pair<Plot, Hour>> irrigationForDayList =  daysIrrigationMap.get(date);
        for (Plot plot : irrigationValuesMap.keySet()){
            Pair<Integer, String> irrigationValues = irrigationValuesMap.get(plot);
            if (irrigationValues.second().equals(dayType) || irrigationValues.second().equals("t")){
                for (Hour hour : irrigationHours){
                    irrigationForDayList.add(new Pair<>(plot, hour.addMinutes(offsetTime)));
                }
                offsetTime += irrigationValues.first();
            }
        }
    }


    public void addThirtyDays(){
        IrrigationCalendar irrigationCalendar = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationCalendar();
        Date currentDate = Calendar.getInstance().getTime();
        for (int i = 0; i < 31; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, i);
            Date date = c.getTime();
            irrigationCalendar.getDaysIrrigationMap().put(date, new LinkedList<>());
        }
    }

}