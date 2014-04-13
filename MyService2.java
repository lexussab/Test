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
	Map<String, Map<String,List<Record>>> stateMap = new HashMap<String, Map<String,List<Record>>>();
	
    public MyService2() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Чтение параметров запроса
		String param = request.getParameter("sort");

		switch(param){	
<<<<<<< HEAD
			case "date":
				ArrayList<Record> listUp = new ArrayList<Record>();
				
				//Отсекаем по статусу
				//проходим по основной карте
				for(Entry<String, Map<String,List<Record>>> entryOut: stateMap.entrySet()){
					//проходим по вложенной карте
					for(Entry<String,List<Record>> entryIn: entryOut.getValue().entrySet()){
						int i = entryIn.getValue().size();
						List<Record> list = entryIn.getValue();
						//записываем во внешний лист последнее значение инстанса с заданным условием UP
						while(i != 0){
							if (list.get(i).getState().equals("UP")){
								listUp.add(list.get(i));
								break;
							}else{
								i--;
							}
						}
						
							
					
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
				
				PrintWriter out = response.getWriter();
		     	out.print(listUp);
		     	out.flush();
		     	out.close();
=======
			case "date":			
				PrintWriter out = response.getWriter();
				String json = gson.toJson(sortDate(stateMap));
		     		out.print(json);
		     		out.flush();
		     		out.close();
>>>>>>> 845d21c6eba8f6e47963ea0699c745ee213a8cd4
				break;
				
			case "cpu" :
				String minC = request.getParameter("minCpu");
				int minCpu = Integer.parseInt(minC);
				String maxC = request.getParameter("maxCpu");
				int maxCpu = Integer.parseInt(maxC);
				ArrayList<Record> listCpu = new ArrayList<Record>();
				
				//Отсекаем по заданному интервалу
				for(Entry<String, Map<String,List<Record>>> entryOut : stateMap.entrySet()){
					for(Entry<String,List<Record>> entryIn : entryOut.getValue().entrySet()){
						for(Record rec : entryIn.getValue()){
							if (rec.getCpu() >= minCpu && rec.getCpu() <= maxCpu)
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
				
				PrintWriter out2 = response.getWriter();
<<<<<<< HEAD
		     	out2.print(listCpu);
		     	out2.flush();
		     	out2.close();
=======
				String json2 = gson.toJson(sortCpu(stateMap,minCpu,maxCpu));
		     		out2.print(json2);
		     		out2.flush();
		     		out2.close();
		     	
		     		System.out.println("-----------------");
		     		for(Record r:listCpu)
		     			System.out.println(r.toString());
				
>>>>>>> 845d21c6eba8f6e47963ea0699c745ee213a8cd4
				break;
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Создание READER
		BufferedReader reader = request.getReader();
		
		//Создания объекта JSON
		Gson gson = new Gson();
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
		synchronized(this){
			if(stateMap.containsKey(obj.getName())){
				for(Entry<String,Map<String, List<Record>>> entry : stateMap.entrySet()){
					if (entry.getKey() == obj.getName()){
						if (entry.getValue().containsKey(obj.getUuid())){
							for(Entry<String, List<Record>> entry2: entry.getValue().entrySet()){
								if (entry2.getKey() == obj.getUuid()){
									entry2.getValue().add(obj);
								}
							}
						}else{
							ArrayList<Record> list = new ArrayList<Record>();
							Map<String,List<Record>> mapIn = new HashMap<String, List<Record>>();
							mapIn.put(obj.getUuid(), list);
							entry.setValue(mapIn);
						}
					}
				}	
			} else{
				Map<String,List<Record>> mapIn = new HashMap<String, List<Record>>();
				ArrayList<Record> list = new ArrayList<Record>();
				list.add(obj);
				mapIn.put(obj.getUuid(), list);
				stateMap.put(obj.getName(), mapIn);
				}	
		}
		
		PrintWriter out = response.getWriter();
		out.print("OK");
		out.flush();
		out.close();		
	}
}
