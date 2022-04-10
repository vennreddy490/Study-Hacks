import java.util.ArrayList;

public class ScheduleCalc {

    private boolean[] busy;
    private static final int WAKEUP = 480;
    private static final int BEDTIME = 1320;

    public ScheduleCalc() {
        busy = new boolean[1440];
        for (int i = BEDTIME; i < 1440; i++) {
            busy[i] = true;
        } // for
        for (int i = 0; i < WAKEUP; i++) {
            busy[i] = true;
        } // for
    } // Constructor

    public boolean[] getBusy() {
        return busy;
    } // getBusy

    public void printBusy() {
        for (int i = 0; i < busy.length; i++) {
            System.out.println(i + " minutes --> " + busy[i]);
        } // for
    } // printBusy

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

    public void fillSchedule(int start, int end) {
        for (int i = start; i < end; i++) {
            busy[i] = true;
        } // for
    } // fillSchedule

    public ArrayList<ArrayList<String>> check75Min() {
        int noClass = 0;
        final int STUDY_TIME = 25;
        final int BREAK_TIME = 5;
        final int FLEX_TIME = 10;
        ArrayList<ArrayList<String>> availableTimes = new ArrayList<>();
        for (int i = WAKEUP; i <= BEDTIME; i++) {
            if (!busy[i]) {
                noClass++;
            } else {
                if (noClass >= (FLEX_TIME * 2) + (STUDY_TIME * 2) + BREAK_TIME) {
                    ArrayList<String> studyBlocks = new ArrayList<>();
                    int startOfFreeTime = i - noClass + FLEX_TIME;
                    int endOfFreeTime = i - FLEX_TIME;
                    int numOfStudyBlocks = (endOfFreeTime - startOfFreeTime) / (BREAK_TIME + STUDY_TIME);

                    int nextStartOfStudyBlock = startOfFreeTime;
                    for (int j = 0; j < numOfStudyBlocks; j++) {
                        String startOfStudyBlock = convertMinutesToTime(nextStartOfStudyBlock);
                        String startOfBreak = convertMinutesToTime(nextStartOfStudyBlock + STUDY_TIME);
                        String studyBlock = startOfStudyBlock + "-" + startOfBreak;
                        studyBlocks.add(studyBlock);
                        nextStartOfStudyBlock += (BREAK_TIME + STUDY_TIME);
                    } // for
                    
                    int remainingTime = (endOfFreeTime - startOfFreeTime) % (BREAK_TIME + STUDY_TIME);
                    String lastStartOfStudyBlock = convertMinutesToTime(nextStartOfStudyBlock);
                    String endOfLastStudyBlock = convertMinutesToTime(nextStartOfStudyBlock + remainingTime);
                    String lastStudyBlock = lastStartOfStudyBlock + "-" + endOfLastStudyBlock;
                    studyBlocks.add(lastStudyBlock);

                    availableTimes.add(studyBlocks);
                } // if
                noClass = 0;
            } // if-else
        } // for
        return availableTimes;
    } // check75Min

    public ArrayList<String> getOptimalStudyBlock(ArrayList<ArrayList<String>> studyBlocks) {
        final int OPTIMAL_TIME = 900;
        int timeClosestToOptimalTime = Integer.MAX_VALUE;
        ArrayList<String> optimalStudyBlock = null;
        for (ArrayList<String> studyBlock : studyBlocks) {
            String firstBlock = studyBlock.get(0);
            String lastBlock = studyBlock.get(studyBlock.size() - 1);

            String begin = firstBlock.substring(0, firstBlock.indexOf('-'));
            String end = lastBlock.substring(lastBlock.indexOf('-') + 1);

            int beginInt = convertTimeToMinutes(begin);
            int endInt = convertTimeToMinutes(end);
            int middleTime = endInt - beginInt;
            if (Math.abs(beginInt + middleTime - OPTIMAL_TIME) < timeClosestToOptimalTime) {
                timeClosestToOptimalTime = Math.abs(beginInt + middleTime - OPTIMAL_TIME);
                optimalStudyBlock = studyBlock;
            } // if
        } // for

        return optimalStudyBlock;

        /*
        String firstBlock = optimalStudyBlock.get(0);
        String begin = firstBlock.substring(0, firstBlock.indexOf('-'));
        if (convertTimeToMinutes(begin) > OPTIMAL_TIME) {
            String secondBlock = optimalStudyBlock.get(1);
            String end = secondBlock.substring(secondBlock.indexOf('-') + 1);
            return begin + " to " + end;
        } else {
            firstBlock = optimalStudyBlock.get(optimalStudyBlock.size() - 3);
            String secondBlock = optimalStudyBlock.get(optimalStudyBlock.size() - 2);

            begin = firstBlock.substring(0, firstBlock.indexOf('-'));
            String end = secondBlock.substring(secondBlock.indexOf('-') + 1);
            return begin + " to " + end;
        } // if-else
        */
    } // getOptimalStudyBlock

} // ScheduleCalc
