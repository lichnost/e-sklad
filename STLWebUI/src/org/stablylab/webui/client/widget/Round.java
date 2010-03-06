package org.stablylab.webui.client.widget;

public class Round {

	public static double round(double val, int places){
		double p = Math.pow(10,places);
		val = val * p;
		double tmp = Math.round(val);
		return tmp/p;
	}
}
