package ng.codeinn.med_manager.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jer on 31/03/2018.
 */

public class MonthsAssets {

    private static final List<String> months = new ArrayList<String>(){{
        add("JAN");
        add("FEB");
        add("MAR");
        add("APR");
        add("MAY");
        add("JUN");
        add("JUL");
        add("AUG");
        add("SEP");
        add("OCT");
        add("NOV");
        add("DEC");

    }};

    public static List<String> getMonths() {
        return months;
    }
}
