package henson.midp.Model;

import henson.midp.*;

public class Sun
{
  public static double ZENITH_OFFICIAL=90.83333;
  public static double ZENITH_CIVIL=96.0;
  public static double ZENITH_NAUTICAL=102.0;
  public static double ZENITH_ASTRONOMICAL=108.0;

  public Sun()
  {
  }

  public static double calculate(boolean sunrise, Place place, int day, int month, int year, double zenith)
  {
    // 1. first calculate the day of the year
    int N1=(int)Math.floor(275.0*month/9.0);
    int N2=(int)Math.floor((month+9.0)/12.0);
    int N3=(int)(1+(int)Math.floor((year - 4 * Math.floor(year / 4) + 2) / 3));
    int N = N1 - (N2 * N3) + day - 30;
    // 2. convert the longitude to hour value and calculate an approximate time
    double lngHour = place.Longitude / 15;
    double t;
    if(sunrise)
    //if rising time is desired:
      t = N + ((6 - lngHour) / 24);
    else
    //if setting time is desired:
      t = N + ((18 - lngHour) / 24);
    // 3. calculate the Sun's mean anomaly
    double M = (0.9856 * t) - 3.289;
    // 4. calculate the Sun's true longitude
    double L = Util.normalize(M + (1.916 * Math.sin(Math.toRadians(M))) + (0.020 * Math.sin(Math.toRadians(2*M))) + 282.634, 360);
    // 5a. calculate the Sun's right ascension
    double RA = Util.normalize(Math.toDegrees(Float11.atan(0.91764 * Math.tan(Math.toRadians(L)))), 360);
    // 5b. right ascension value needs to be in the same quadrant as L
    int Lquadrant  = ((int)Math.floor(L/90)) * 90;
    int RAquadrant = ((int)Math.floor(RA/90)) * 90;
    RA += (Lquadrant - RAquadrant);
    // 5c. right ascension value needs to be converted into hours
    RA /= 15;
    // 6. calculate the Sun's declination
    double sinDec = 0.39782 * Math.sin(Math.toRadians(L));
    double cosDec = Math.cos(Float11.asin(sinDec));
    // 7a. calculate the Sun's local hour angle
    double cosH = (Math.cos(Math.toRadians(zenith)) - (sinDec * Math.sin(Math.toRadians(place.Latitude)))) / (cosDec * Math.cos(Math.toRadians(place.Latitude)));
    if (cosH >  1)
      //the sun never rises on this location (on the specified date)
      return Double.MIN_VALUE;
    if (cosH < -1)
      //the sun never sets on this location (on the specified date)
      return Double.MAX_VALUE;
    // 7b. finish calculating H and convert into hours
    double H;
    if(sunrise)
    //if if rising time is desired:
      H = 360 - Math.toDegrees(Float11.acos(cosH));
    else
    //if setting time is desired:
      H = Math.toDegrees(Float11.acos(cosH));
    H = H / 15;
    // 8. calculate local mean time of rising/setting
    double T = H + RA - (0.06571 * t) - 6.622;
    // 9. adjust back to UTC
    double UT = Util.normalize(T - lngHour, 24);
    // 10. convert UT value to local time zone of latitude/longitude
    return Util.normalize(UT + place.TimeZone, 24);
  }
}
