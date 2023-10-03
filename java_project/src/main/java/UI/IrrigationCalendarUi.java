package UI;

import Controller.App;
import Domain.Model.IrrigationCalendar;
import Domain.Model.Plot;
import utils.Hour;
import utils.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class IrrigationCalendarUi implements Runnable {

    @Override
    public void run() {

        IrrigationCalendar irrigationCalendar = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationCalendar();
        HashMap<Plot, Pair<Integer, String>> irrigationValuesMap = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationController().getIrrigationValuesMap();
        TreeMap<Date, LinkedList<Pair<Plot, Hour>>> daysIrrigationMap = irrigationCalendar.getDaysIrrigationMap();
        for (Date date : daysIrrigationMap.keySet()) {
            System.out.println("Day " + date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900));
            LinkedList<Pair<Plot, Hour>> irrigationList = daysIrrigationMap.get(date);
            for (Pair<Plot, Hour> pair : irrigationList) {
                System.out.println("Plot " + pair.first().  designation + " will get irrigated from " + pair.second().toString() + " to " + pair.second().addMinutes(irrigationValuesMap.get(pair.first()).first()).toString());
            }
            System.out.println();
        }
    }
}


