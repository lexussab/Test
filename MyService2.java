package ru.home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class MyService2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Создание хранилища данных
	ArrayList<Record> list = new ArrayList<Record>();
	Map<String,List<Record>> mapIn = new HashMap<String, List<Record>>();
	Map<String, Map<String,List<Record>>> stateMap = new HashMap<String, Map<String,List<Record>>>();
	ArrayList<Record> listUp = new ArrayList<Record>();
	ArrayList<Record> listCpu = new ArrayList<Record>();
	
    public MyService2() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Чтение параметров запроса
		String param = request.getParameter("sort");
		Gson gson = new Gson();
		switch(param){	
			case "date":			
				
				
				PrintWriter out = response.getWriter();
				String json = gson.toJson(sortDate(stateMap));
		     	out.print(json);
		     	out.flush();
		     	out.close();
				break;
			case "cpu" :
				String minC = request.getParameter("minCpu");
				int minCpu = Integer.parseInt(minC);
				String maxC = request.getParameter("maxCpu");
				int maxCpu = Integer.parseInt(maxC);

				PrintWriter out2 = response.getWriter();
				String json2 = gson.toJson(sortCpu(stateMap,minCpu,maxCpu));
		     	out2.print(json2);
		     	out2.flush();
		     	out2.close();
		     	
		     	System.out.println("-----------------");
		     	for(Record r:listCpu)
		     		System.out.println(r.toString());
				
				break;
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Создание READER
		BufferedReader reader = request.getReader();
		
		//Создания объекта JSON
		Gson gson = new Gson();
		Collections.synchronizedMap(stateMap);
		StringBuilder sb = new StringBuilder();
		String str = null;
		Date date = new Date();
		
		
		while ((str = reader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
		
		//Десериализайия данных и создание объекта статуса
		Record obj = gson.fromJson(sb.toString(), Record.class);
		obj.setDateReg(date);
		
		//Записываем данные в память
		list.add(obj);
		mapIn.put(obj.getUuid(), list);
		stateMap.put(obj.getName(), mapIn);
		
		PrintWriter out = response.getWriter();
		out.print("OK");
		out.flush();
		out.close();
				
	}
	
	//Сортировка по заданным условиям
	protected ArrayList<Record> sortDate (Map<String, Map<String,List<Record>>> map){
		//Отсекаем по статусу
		for(Entry<String, Map<String,List<Record>>> entryOut: map.entrySet()){
			for(Entry<String,List<Record>> entryIn: entryOut.getValue().entrySet()){
				for(Record rec:entryIn.getValue()){
					if (rec.getState().equals("UP"))
						listUp.add(rec);
				};
			}
		}
		
		Collections.sort(listUp, new Comparator<Record>(){
			@Override
		    public int compare(Record rec1, Record rec2){
				int cpu1 = rec1.getCpu();
				int cpu2 = rec2.getCpu();
				
				if (cpu1 > cpu2) return 1;
				else if (cpu1 < cpu2) return -1;
				else if (cpu1 == cpu2)
					return 0;
				return cpu2;
		    }
		});
	
		return listUp;
		
	}
	
	//Создание и сортировка списка в заданном интервале
	protected ArrayList<Record> sortCpu(Map<String, Map<String,List<Record>>> map, int min, int max){
		
		//Отсекаем по заданному интервалу
		for(Entry<String, Map<String,List<Record>>> entryOut : map.entrySet()){
			for(Entry<String,List<Record>> entryIn : entryOut.getValue().entrySet()){
				for(Record rec : entryIn.getValue()){
					if (rec.getCpu() >= min && rec.getCpu() <= max)
						listCpu.add(rec);
				};
			}
		}
		
		Collections.sort(listCpu, new Comparator<Record>(){
			@Override
		    public int compare(Record rec1, Record rec2){
				int cpu1 = rec1.getCpu();
				int cpu2 = rec2.getCpu();
				
				if (cpu1 > cpu2) return 1;
				else if (cpu1 < cpu2) return -1;
				else if (cpu1 == cpu2)
					return 0;
				return cpu2;
		    }
		});
		
		return listCpu;
	}

}
