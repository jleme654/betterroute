package com.wallmart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainTest {

	static Map<Integer, String> map, map2, map3, map4, map5, map6;
	static ArrayList<Map<Integer, String>> listMaps;

	private static ArrayList<Map<Integer, String>> populateMaps() {
		map = new HashMap<Integer, String>();
		map2 = new HashMap<Integer, String>();
		map3 = new HashMap<Integer, String>();
		map4 = new HashMap<Integer, String>();
		map5 = new HashMap<Integer, String>();
		map6 = new HashMap<Integer, String>();
		map.put(10, "AB");
		map2.put(15, "BD");
		map3.put(20, "AC");
		map4.put(30, "CD");
		map5.put(50, "BE");
		map6.put(30, "DE");
		listMaps = new ArrayList<Map<Integer, String>>();
		listMaps.add(map);
		listMaps.add(map2);
		listMaps.add(map3);
		listMaps.add(map4);
		listMaps.add(map5);
		listMaps.add(map6);
		return listMaps;
	}

	static Map<Integer, String> getFirstBetterRote(String start, ArrayList<Map<Integer, String>> listMaps) {
		ArrayList<Integer> listNum = new ArrayList<Integer>();
		Map<Integer, String> mapResult = new HashMap<Integer, String>();
		for (Map map : listMaps) {
			String comp = map.values().toString().substring(1, 2);
			// compara os pontos de partida das rotas
			if (start.equals(comp)) {
				// pega os valores da chave
				Integer num = Integer.parseInt(map.keySet().toString().substring(1, 3));
				listNum.add(num);
				// System.out.println(num);

				// seta o menor valor
				Integer[] arr = (Integer[]) listNum.toArray(new Integer[listNum.size()]);
				int smaller = num;
				for (int i = 0; i < arr.length; i++) {
					if (arr[i] <= smaller) {
						smaller = arr[i];
						// System.out.println("o menor valor: "+smaller);

						for (Map map2 : listMaps) {
							if (map2.containsKey(smaller)) {
								//System.out.println("first better rote: " + map2);
								mapResult = map2;
								return map2;
							}
						}
					}
				}
			}
		}
		return mapResult;

	}
	
	static Map<Integer, String> getLastBetterRote(String inter, String end, ArrayList<Map<Integer, String>> listMaps) {
		ArrayList<Integer> listNum = new ArrayList<Integer>();
		Map<Integer, String> mapResult = new HashMap<Integer, String>();
		for (Map map : listMaps) {
			String comp = map.values().toString().substring(2, 3);
			//System.out.println(comp);
			// compara os pontos de partida das rotas
			if (end.equals(comp)) {
				// pega os valores da chave
				Integer num = Integer.parseInt(map.keySet().toString().substring(1, 3));
				listNum.add(num);
				// System.out.println(num);

				// seta o menor valor
				Integer[] arr = (Integer[]) listNum.toArray(new Integer[listNum.size()]);
				int smaller = num;
				for (int i = 0; i < arr.length; i++) {
					if (arr[i] <= smaller) {
						smaller = arr[i];
						// System.out.println("o menor valor: "+smaller);

						for (Map map2 : listMaps) {
							if (map2.containsKey(smaller)) {
								//System.out.println("last better rote: " + map2);
								mapResult = map2;
								return map2;
							}
						}
					}
				}
			}
		}
		return mapResult;

	}
	
	static double getCostFinal(int autonomy, double priceGas, int distance){
		double priceFinal = 0;
		double autoDouble = (double)autonomy;
		double distDouble = (double)distance;
		double litros = distDouble/autoDouble;
		priceFinal = priceGas*litros;
		return priceFinal;
	}
	
	static boolean verifyIni(String route , ArrayList listMaps){
		for (Object object : listMaps) {
			String routeComp = object.toString().substring(4, 6);
			//System.out.println(routeComp);
			if (route.equals(routeComp)) {
				return true;
			}
		}	
		return false;
	}

	static String getRouteMoreCheap(String name, String start, String end, int autonomy, double priceGas) {
		listMaps = populateMaps();
		              
//		System.out.println(start);
//		System.out.println(end);
//		System.out.println("-------");
		
		StringBuilder sbIni = new StringBuilder();
		sbIni.append(start);sbIni.append(end);
		
		if (start.equals(end)) {
			return "Route: "+sbIni+". The point of arrival and the starting point is the same.";
		}
		
		boolean exists = verifyIni(sbIni.toString(), listMaps); 
		if (exists) {
			return "Route: "+sbIni+". This route "+sbIni.toString()+" already exists.";
		}
		
		Map map = getFirstBetterRote(start, listMaps);
		if (map.isEmpty()) {
			return "Route: "+sbIni+". Dont exists this starting point.";
		}
		//System.out.println(map);
		if (!map.toString().subSequence(4, 5).equals(start)) {
			return "Route: "+sbIni.toString()+". This route dont exists.";
		}
		String inter = map.toString().substring(5, 6);
	
		Map map2 = getLastBetterRote(inter, end, listMaps);
		if (map2.isEmpty()) {
			return "Route: "+sbIni+". Dont exists this point of arrival.";
		}
		//System.out.println(map2);
		
		StringBuilder sb= new StringBuilder();
		String route = map.toString().substring(4, 6);//System.out.println(route);
		String route2 = map2.toString().substring(5, 6);//System.out.println(route2);
		sb.append(route);sb.append(route2);
		String route3 = sb.toString();
		//System.out.println(route3);
		
		String dist = map.toString().substring(1, 3);
		String dist2 = map2.toString().substring(1, 3);
		Integer distNum = Integer.parseInt(dist);
		Integer distNum2 = Integer.parseInt(dist2);
		Integer distNum3 = distNum+distNum2;
		//System.out.println(distNum3);
		
		double costFinal = getCostFinal(autonomy, priceGas, distNum3);	
		//System.out.println(costFinal);
		
		return "Better Route: " + route3 + ". \nCost: " + costFinal;
	}

	public static void main(String[] args) {
		System.out.println(getRouteMoreCheap("name", "D", "C", 10, 2.5));
	}

}
