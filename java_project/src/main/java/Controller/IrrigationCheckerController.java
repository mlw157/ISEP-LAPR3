    package Controller;

    import Domain.Model.IrrigationCalendar;
    import Domain.Model.IrrigationController;
    import Domain.Model.Plot;
    import utils.Date;
    import utils.Hour;
    import utils.Pair;

    import java.util.*;

    /**
     * Controller class for checking if a irrigation controller is currently irrigating (US306)
     * @author : Pedro Campos <1211511@isep.ipp.pt>
     */
    public class IrrigationCheckerController {


        private IrrigationController irrigationController;
        private IrrigationCalendar irrigationCalendar;

        public IrrigationCheckerController() {
            irrigationController = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationController();
            irrigationCalendar = App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationCalendar();
        }


        public Pair<Boolean, Integer> isPlotGettingIrrigated(Plot plot) {
            boolean isHourCorrect = false;
            boolean isDayCorrect = false;
            int timeLeft = -1;
            HashMap<Plot, Pair<Integer, String>> irrigationValuesMap = irrigationController.getIrrigationValuesMap();
            List<Hour> irrigationHours = irrigationController.getIrrigationHours();
            Pair<Integer, String> irrigationValues = irrigationValuesMap.get(plot);
            int irrigationTime = irrigationValues.first();
            String irrigationType = irrigationValues.second();
            Hour currentHour = Hour.currentTime();
            for (Hour hour : irrigationHours){
                Hour irrigationLimit = hour.addMinutes(irrigationTime);
                if (currentHour.isBigger(hour) && !currentHour.isBigger(irrigationLimit)){
                    isHourCorrect = true;
                    Hour irrigationHour = hour;
                    timeLeft = irrigationLimit.differenceInTime(currentHour).getMinutes();
                }
            }
            int currentDay = Date.getCurrentDate().getDay();
            if (irrigationType.equals("t")){
                isDayCorrect = true;
            } else if (irrigationType.equals("p") && currentDay % 2 == 0){
                isDayCorrect = true;
            } else if (irrigationType.equals("i") && currentDay % 2 != 0){
                isDayCorrect = true;
            }

            return new Pair<>(isHourCorrect && isDayCorrect, timeLeft);

        }

        public int getIrrigationTimeLeft(Plot plot){
            HashMap<Plot, Pair<Integer, String>> irrigationValuesMap = irrigationController.getIrrigationValuesMap();
            List<Hour> irrigationHours = irrigationController.getIrrigationHours();
            Pair<Integer, String> irrigationValues = irrigationValuesMap.get(plot);
            int irrigationTime = irrigationValues.first();
            String irrigationType = irrigationValues.second();
            Hour currentHour = Hour.currentTime();
            for (Hour hour : irrigationHours){
                Hour irrigationLimit = hour.addMinutes(irrigationTime);

            }
            return 0;
        }

        public List<Pair<Plot, Integer>> irrigatedPlots (){
            HashMap<Plot, Pair<Integer, String>> irrigationValuesMap = irrigationController.getIrrigationValuesMap();
            List<Pair<Plot,Integer>> irrigatedPlots = new ArrayList<>();
            for (Plot plot : irrigationValuesMap.keySet()){
                Pair <Boolean, Integer> isPlotIrrigated = isPlotGettingIrrigated(plot);
                if (isPlotIrrigated.first()){
                    irrigatedPlots.add(new Pair<>(plot, isPlotIrrigated.second()));
                }
            }
            return irrigatedPlots;
        }

        public Pair<Boolean, Pair<Plot, Integer>> isAnyPlotGettingIrrigated(java.util.Date date, Hour hour){
            HashMap<Plot, Pair<Integer, String>> irrigationValuesMap = irrigationController.getIrrigationValuesMap();
            TreeMap<java.util.Date, LinkedList<Pair<Plot, Hour>>> irrigationCalendarMap = irrigationCalendar.getDaysIrrigationMap();
            for (java.util.Date date1 : irrigationCalendarMap.keySet()){
                if (date1.getDate() == date.getDate() && date1.getMonth() == date.getMonth() && date1.getYear() == date.getYear()){
                    date = date1;
                    break;
                }
            }
            LinkedList<Pair<Plot, Hour>> plotsIrrigationList = irrigationCalendarMap.get(date);
            if (plotsIrrigationList == null){
                return new Pair<>(false, null);
            }
            for (Pair<Plot, Hour> plotHourPair : plotsIrrigationList){
                Plot plot = plotHourPair.first();
                Hour irrigationHour = plotHourPair.second();
                Pair<Integer, String> irrigationValues = irrigationValuesMap.get(plot);
                int irrigationTime = irrigationValues.first();
                String irrigationType = irrigationValues.second();
                Hour irrigationLimit = irrigationHour.addMinutes(irrigationTime);
                if (hour.isBigger(irrigationHour) && !hour.isBigger(irrigationLimit)){
                    return new Pair<>(true, new Pair<>(plot, irrigationLimit.differenceInTime(hour).getMinutes()));
                }
            }
            return new Pair<>(false, null);
        }

    }
