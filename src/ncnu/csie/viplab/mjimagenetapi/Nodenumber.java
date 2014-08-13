package ncnu.csie.viplab.mjimagenetapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class Nodenumber {
	public static int choice(int num)
	{
		
		
		if(num<1664492)
		{
			num=1;
		}
		else if(num<203096)
		{
			num=2;
		}
		else if(num<2310585)
		{
			num=3;
		}
		else if(num<2610980)
		{
			num=4;
		}
		else if(num<2881757)
		{
			num=5;
		}
		else if(num<3116767)
		{
			num=6;
		}
		else if(num<3370387)
		{
			num=7;
		}
		else if(num<3609147)
		{
			num=8;
		}
		else if(num<3871860)
		{
			num=9;
		}
		else if(num<4120489)
		{
			num=10;
		}
		else if(num<4359217)
		{
			num=11;
		}
		else if(num<4608923)
		{
			num=12;
		}
		else if(num<7733217)
		{
			num=13;
		}
		else if(num<7916183)
		{
			num=14;
		}
		else if(num<9833751)
		{
			num=15;
		}
		else if(num<10239928)
		{
			num=16;
		}
		else if(num<11844892)
		{
			num=17;
		}
		else if(num<12201938)
		{
			num=18;
		}
		
		else if(num<12586725)
		{
			num=19;
		}
		else if(num<12947544)
		{
			num=20;
		}
		else if(num<15102455)
		{
			num=21;
		}
		else 
			num=-1;
		
		return num;
	}
	
}
