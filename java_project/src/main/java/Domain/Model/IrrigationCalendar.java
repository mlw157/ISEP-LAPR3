package Domain.Model;

import com.sun.source.tree.Tree;
import utils.Hour;
import utils.Pair;

import java.time.LocalDate;
import java.util.*;

public class IrrigationCalendar {

    public TreeMap<Date, LinkedList<Pair<Plot, Hour>>> daysIrrigationMap = new TreeMap<>();


    public IrrigationCalendar() {

    }

    public TreeMap<java.util.Date, LinkedList<Pair<Plot, Hour>>> getDaysIrrigationMap() {
        return daysIrrigationMap;
    }
}
