package ru.smirnov.anton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class Service extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Создание хранилища данных. Ключом для внешней карты будет служить имя приложения
	//ключом для второй UUID инстанса.
	Map<String, Map<String,Record>> stateMap = new HashMap<String, Map<String,Record>>();
	
    public Service() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Чтение параметров запроса
		String param = request.getParameter("sort");
		switch(param){	
			case "date":		
				ArrayList<Record> listUp = new ArrayList<Record>();
				
				//Выбираем записи со статусом UP
				//проходим по основной карте
				for(Entry<String, Map<String,Record>> entry: stateMap.entrySet()){
					//проходим по вложенной карте
					for(Entry<String,Record> entryIn: entry.getValue().entrySet()){
						//записываем во внешний лист значение инстанса с заданным условием
						Record rec = entryIn.getValue();
						if (rec.getState().equals("UP")){
								listUp.add(rec);
						}
					}
				}
				//Сортируем коллекцию
				Collections.sort(listUp,new CompareCPU());
				PrintWriter out = response.getWriter();
				int n = 1;
				for(Record rec: listUp){
		     		out.printf("%d, %s\n", n, rec.toString());
		     		n++;
		     		}
		     		out.flush();
		     		out.close();
				break;
				
			case "cpu" :
				String minC = request.getParameter("minCpu");
				int minCpu = Integer.parseInt(minC);
				String maxC = request.getParameter("maxCpu");
				int maxCpu = Integer.parseInt(maxC);
				ArrayList<Record> listCpu = new ArrayList<Record>();
				
				//Выбираем записи со статусом UP
				//проходим по основной карте
				for(Entry<String, Map<String,Record>> entry: stateMap.entrySet()){
					//проходим по вложенной карте
					for(Entry<String,Record> entryIn: entry.getValue().entrySet()){
						//записываем во внешний лист значение инстанса с заданным условием
						Record rec = entryIn.getValue();
						if (rec.getCpu() >= minCpu && rec.getCpu() <= maxCpu){
							listCpu.add(rec);
						}
					}
				}
				Collections.sort(listCpu,new CompareCPU());
				PrintWriter out2 = response.getWriter();
		     		int i = 1;
				for(Record rec: listCpu){
		     		out2.printf("%d, %s\n", i, rec.toString());
		     		i++;
		     		}
		     		out2.flush();
		     		out2.close();
				break;
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Считаем, что данные передаются в формате JSON например:
		//{"name":"WAVE","uuid":"7b2f75b2-d17a-4e38-8b8f-10","state":"UP","cpu":70,"timeWork":23,"quantity":19} 
		
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
			//Проверка на наличие приложения с таким же именем
			if(stateMap.containsKey(obj.getName())){
				for(Entry<String,Map<String, Record>> entry : stateMap.entrySet()){
					if (entry.getKey().equals(obj.getName())){	
						entry.getValue().put(obj.getUuid(), obj);
					}
				}
			} else{
				Map<String,Record> mapIn = new HashMap<String, Record>();
				mapIn.put(obj.getUuid(), obj);
				stateMap.put(obj.getName(), mapIn);
				}	
		}
		
		PrintWriter out = response.getWriter();
		out.print("OK");
		out.flush();
		out.close();			
	}			
}
