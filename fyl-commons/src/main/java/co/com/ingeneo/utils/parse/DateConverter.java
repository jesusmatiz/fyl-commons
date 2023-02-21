package co.com.ingeneo.utils.parse;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter
{
  public static enum Day
  {
    MONDAY,  TUESDAY,  WEDNESDAY,  THURSDAY,  FRIDAY,  SATURDAY,  SUNDAY;
    
    private Day() {}
  }
  
  public static String getDate(Long timeMilliSeconds, String simpleDateFormat)
  {
    Format formatter = new SimpleDateFormat(simpleDateFormat);
    return formatter.format(new Date(timeMilliSeconds.longValue())) + "";
  }
  
  public static String getDateWithInitialHour(Long timeMilliSeconds, String simpleDateFormat)
  {
    Format formatter = new SimpleDateFormat(simpleDateFormat);
    return formatter.format(new Date(timeMilliSeconds.longValue())) + " 000000";
  }
  
  public static String getDateWithFinalHour(Long timeMilliSeconds, String simpleDateFormat)
  {
    Format formatter = new SimpleDateFormat(simpleDateFormat);
    return formatter.format(new Date(timeMilliSeconds.longValue())) + " 235959";
  }
  
  public static Day getDayFromDate(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int day = calendar.get(7);
    switch (day)
    {
    case 2: 
      return Day.MONDAY;
    case 3: 
      return Day.TUESDAY;
    case 4: 
      return Day.WEDNESDAY;
    case 5: 
      return Day.THURSDAY;
    case 6: 
      return Day.FRIDAY;
    case 7: 
      return Day.SATURDAY;
    }
    return Day.SUNDAY;
  }
}
