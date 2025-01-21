package henson.midp.Model;

import henson.midp.*;
import java.util.*;

public class Astro
{
  final double tpi = 2.0 * Math.PI;
  final double degs = 180.0/Math.PI;
  final double rads = Math.PI/180.0;
  final double AirRefr = 34.0/60.0; // athmospheric refraction degrees //

  double L,RA,daylen,delta,x,y,z;

  public String decly;
  public String dayly;
  public String risy;
  public String sety;
  public String maxhy;
  public String minhy;
  public String noonty;
  public String nighty;
  public String azimy;
  public String altity;


  public Astro()
  {
  }

//   Get the days to J2000
//   h is UT in decimal hours
//   FNday only works between 1901 to 2099 - see Meeus chapter 7
  float Round2d3(double x)
  {
    double z = (1000.0 * x + 0.499);
    int i = (int) (z);
    z = ( (float) i / 1000.0);
    return (float) z;
  }

  public double FNday (int y, int m, int d, double h)
  {
    long luku = -7 * (y + (m + 9) / 12) / 4 + 275 * m / 9 + d;
// type casting necessary on PC DOS and TClite to avoid overflow
    luku += (long) y * 367;
    return (double) luku - 730530.0 + h / 24.0;
  };

//   the function below returns an angle in the range
//   0 to 2*pi

  public double FNrange(double x)
  {
      double b = x / tpi;
      double a = tpi * (b - (long)(b));
      if (a < 0) a = tpi + a;
      return a;
  };

// Calculating the hourangle

  public double f0(double lat, double declin)
  {
    double fo, dfo;
    double SunDia = 0.53; // Sunradius degrees

    dfo = rads * (0.5 * SunDia + AirRefr);
    if (lat < 0.0) dfo = -dfo; // Southern hemisphere
    fo = Math.tan(declin + dfo) * Math.tan(lat * rads);
    if (fo > 0.99999) fo = 1.0; // to avoid overflow //
    fo = Float11.asin(fo) + Math.PI / 2.0;
    return fo;
  };

//   Find the ecliptic longitude of the Sun

  public double FNsun (double d)
  {
    double w,M,v,r,g;
//   mean longitude of the Sun
    w = 282.9404 + 4.70935E-5 * d;
    M = 356.047 + 0.9856002585 * d;
// Sun's mean longitude
    L = FNrange(w * rads + M * rads);

//   mean anomaly of the Sun

    g = FNrange(M * rads);

// eccentricity
    double ecc = 0.016709 - 1.151E-9 * d;

//   Obliquity of the ecliptic

    double obliq = 23.4393 * rads - 3.563E-7 * rads * d;
    double E = M + degs * ecc * Math.sin(g) * (1.0 + ecc * Math.cos(g));
    E = degs * FNrange(E * rads);
    x = Math.cos(E * rads) - ecc;
    y = Math.sin(E * rads) * Math.sqrt(1.0 - ecc * ecc);
    r = Math.sqrt(x * x + y * y);
    v = Float11.atan2(y, x) * degs;
// longitude of sun
    double lonsun = v + w;
    if (lonsun > 360.0) lonsun -= 360.0;

// sun's ecliptic rectangular coordinates
    x = r * Math.cos(lonsun * rads);
    y = r * Math.sin(lonsun * rads);
    double yequat = y * Math.cos(obliq);
    double zequat = y * Math.sin(obliq);
    RA = Float11.atan2(yequat, x);
    delta = Float11.atan2(zequat, Math.sqrt(x * x + yequat * yequat));
    RA *= degs;

//   Ecliptic longitude of the Sun

    return FNrange(L + 1.915 * rads * Math.sin(g) + .02 * rads * Math.sin(2 * g));
  };

// Display decimal hours in hours and minutes
  String showhrmn(double dhr)
  {
    int hr, mn;
    hr = (int) dhr;
    String hrs, mns;
    mn = (int) ( (dhr - (double) hr) * 60);
    hrs = " " + hr;
    mns = ":" + mn;
    if (hr < 10) hrs = "0" + hr;
    if (mn < 10) mns = ":0" + mn;
    return (hrs + mns);
  };

  public void Calculate(Place p, Calendar cal)
  {
    int year=cal.get(Calendar.YEAR);
    int month=cal.get(Calendar.MONTH)+1;
    int day=cal.get(Calendar.DAY_OF_MONTH);
    double h=cal.get(Calendar.HOUR_OF_DAY)+cal.get(Calendar.MINUTE)/60.0+cal.get(Calendar.SECOND)/3600.0;
    double UT = h - p.TimeZone;  // universal time
    double jd = FNday(year, month, day, UT);

//   Use FNsun to find the ecliptic longitude of the
//   Sun
    double lambda = FNsun(jd);
//   Obliquity of the ecliptic

    double obliq = 23.4393 * rads - 3.563E-7 * rads * jd;

// Sidereal time at Greenwich meridian
    double GMST0 = L * degs / 15.0 + 12.0; // hours
    double SIDTIME = GMST0 + UT + p.Longitude / 15.0;
// Hour Angle
    double ha = 15.0 * SIDTIME - RA; // degrees
    ha = FNrange(rads * ha);
    x = Math.cos(ha) * Math.cos(delta);
    y = Math.sin(ha) * Math.cos(delta);
    z = Math.sin(delta);
    double xhor = x * Math.sin(p.Latitude * rads) - z * Math.cos(p.Latitude * rads);
    double yhor = y;
    double zhor = x * Math.cos(p.Latitude * rads) + z * Math.sin(p.Latitude * rads);
    double azim = Float11.atan2(yhor, xhor) + Math.PI;
    azim = FNrange(azim);
    //
    double altit = Float11.asin(zhor) * degs;
// Include Air refraction if altitude less than 30 degrees
    if (altit < 30.0) altit += AirRefr;

    double alpha = Float11.atan2(Math.cos(obliq) * Math.sin(lambda),
                                 Math.cos(lambda));

//   Find the Equation of Time in minutes
    double equation = 1440.0 - (L - alpha) * degs * 4.0;

    ha = f0(p.Latitude, delta);

// Conversion of angle to hours and minutes //
    daylen = degs * ha / 7.5;
    if (daylen < 0.0001) {
      daylen = 0.0;
    }
// arctic winter     //
    String se = " (S)";
    double riset = 12.0 - 12.0 * ha / Math.PI + p.TimeZone - p.Longitude / 15.0 +
        equation / 60.0;
    double settm = 12.0 + 12.0 * ha / Math.PI + p.TimeZone - p.Longitude / 15.0 +
        equation / 60.0;
    double noont = riset + 12.0 * ha / Math.PI;
    double midnt = noont - 12.0;
    double altmax = 90.0 + delta * degs - p.Latitude;
    double altmin = altmax + 2.0 * p.Latitude - 180.0;
    if (altmax > 90.0) {
      altmax = 180.0 - altmax;
      se = " (N)";
    } // around the equator and in south
    if (altmin < -90.0) altmin = - (altmin + 180.0);
    if (altmax < 30.0) altmax += AirRefr; // Airrefraction included at small altitudes
    if (altmin > -30.0) altmin += AirRefr;

    if (noont > 24.0) noont -= 24.0;
    if (midnt < 0.0) midnt += 24.0;
    if (riset > 24.0) riset -= 24.0;
// sometimes Sunrise may take place before midnight
// We must correct the negative decimal hour
    if (riset < 0.0) riset += 24.0;
    if (settm > 24.0) settm -= 24.0;
    //
    decly=String.valueOf(Round2d3(delta * degs));
    dayly=showhrmn(daylen);
    risy=showhrmn(riset);
    sety=showhrmn(settm);
    maxhy=String.valueOf(Round2d3(altmax)) + se;
    minhy=String.valueOf(Round2d3(altmin));
    noonty=showhrmn(noont);
    nighty=showhrmn(midnt);
    azimy=String.valueOf(Round2d3(azim * degs));
    altity=String.valueOf(Round2d3(altit));
  }
}
