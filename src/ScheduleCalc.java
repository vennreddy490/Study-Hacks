public class ScheduleCalc {

    private int[] minutesArr;

    public ScheduleCalc() {
        minutesArr = new int[1440];
        for (int i = 0; i < minutesArr.length; i++) {
            minutesArr[i] = i;
        } // for
    } // Constructor

    public int[] getMinutesArr() {
        return minutesArr;
    } // getMinutesArr

    private int getHoursFromTime(String condensedTime) {
        int hours = Integer.parseInt(condensedTime.substring(0, condensedTime.indexOf(':')));
        if (hours <= 0 || hours > 12)
            throw new IllegalArgumentException();
        return hours;
    } // getHoursFromTime

    private int getMinutesFromTime(String condensedTime) {
        int pIndex = condensedTime.indexOf('p');
        int aIndex = condensedTime.indexOf('a');
        int mIndex = condensedTime.indexOf('m');
        if ((pIndex == -1 && aIndex == -1) || mIndex == -1)
            throw new IllegalArgumentException();

        char noonLetter = pIndex != -1 ? 'p' : 'a';
        int minutes = Integer
                .parseInt(condensedTime.substring(condensedTime.indexOf(':') + 1, condensedTime.indexOf(noonLetter)));
        if (minutes < 0 || minutes >= 60)
            throw new IllegalArgumentException();
        return minutes;
    } // getMinutesFromTime

    private String getTimeOfDay(String condensedTime) {
        char noonLetter = condensedTime.indexOf('p') != -1 ? 'p' : 'a';
        String timeOfDay = condensedTime.substring(condensedTime.indexOf(noonLetter));
        if (!timeOfDay.equals("pm") && !timeOfDay.equals("am"))
            throw new IllegalArgumentException();
        return timeOfDay;
    } // getTimeOfDay

    private boolean invalidTime(String condensedTime) {
        int colonIndex = condensedTime.indexOf(':');
        if (colonIndex == -1)
            return true;

        try {
            getHoursFromTime(condensedTime);
            getMinutesFromTime(condensedTime);
            getTimeOfDay(condensedTime);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        } // try-catch
    } // invalidTime

    public int convertTimeToMinutes(String time) {
        String condensedTime = "";
        for (int i = 0; i < time.length(); i++) {
            char c = Character.toLowerCase(time.charAt(i));
            if (c != ' ') {
                condensedTime += c;
            } // if
        } // for
        if (invalidTime(condensedTime)) {
            throw new IllegalArgumentException("Invalid Time");
        } else {
            int hours = getHoursFromTime(condensedTime);
            String timeOfDay = getTimeOfDay(condensedTime);

            int extraHours = timeOfDay.equals("pm") && hours != 12 ? 12 : 0;
            if (hours == 12 && timeOfDay.equals("am")) {
                hours = 0;
            } // if
            return (hours + extraHours) * 60 + getMinutesFromTime(condensedTime);
        } // if-else
    } // convertTimeToMinutes

    public String convertMinutesToTime(int timeMinutes) {
        int hours = timeMinutes / 60;
        int minutes = timeMinutes % 60;
        String formattedMinutes = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        String timeOfDay = hours >= 12 ? "pm" : "am";

        if (hours == 0) {
            hours = 12;
        } else if (hours > 12) {
            hours -= 12;
        } // if-else
        return hours + ":" + formattedMinutes + timeOfDay;
    } // convertMinutesToTime

} // ScheduleCalc
