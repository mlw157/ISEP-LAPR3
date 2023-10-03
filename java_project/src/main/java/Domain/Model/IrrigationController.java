package Domain.Model;

import utils.Hour;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class IrrigationController {
    private ArrayList<Hour> irrigationHours;

    private HashMap<Plot, Pair<Integer, String>> irrigationValuesMap;

    public IrrigationController(){
    }

    public ArrayList<Hour> getIrrigationHours() {
        return irrigationHours;
    }

    public HashMap<Plot, Pair<Integer, String>> getIrrigationValuesMap() {
        return irrigationValuesMap;
    }

    public void setIrrigationHours(ArrayList<Hour> irrigationHours) {
        this.irrigationHours = irrigationHours;
    }

    public void setIrrigationValuesMap(HashMap<Plot, Pair<Integer, String>> irrigationValuesMap) {
        this.irrigationValuesMap = irrigationValuesMap;
    }
    
    @Override
    public String toString() {
        return "IrrigationController{" +
                "irrigationHours=" + irrigationHours +
                ", irrigationValuesMap=" + irrigationValuesMap +
                '}';
    }
}