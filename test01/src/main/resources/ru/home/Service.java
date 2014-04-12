package ru.home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;






import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import com.google.gson.Gson;

public class MyService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Создадим коллекции для хранения списков статусов
	ArrayList<Record> list = new ArrayList<Record>();
	ArrayList<Record> listUp = new ArrayList<Record>();
	ArrayList<Record> listCpu = new ArrayList<Record>();
	
    public MyService() {
        // TODO Auto-generated constructor stub
    	
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Чтение параметров запроса
		String param = request.getParameter("sort");
		
		
		switch(param){
			
		case "date":
						
			PrintWriter out = response.getWriter();
//			Gson gson = new Gson();
			
	     	out.print(sortDate(list));
	     	out.flush();
	     	out.close();
	     	System.out.println("-----------------");
	     	for(Record r:listUp)
	     		System.out.println(r.toString());
			break;
			
		case "cpu" :
			String minC = request.getParameter("minCpu");
			int minCpu = Integer.parseInt(minC);
			String maxC = request.getParameter("maxCpu");
			int maxCpu = Integer.parseInt(maxC);
			
			PrintWriter out2 = response.getWriter();
//			Gson gson2 = new Gson();
			
	     	out2.print(sortCpu(list,minCpu,maxCpu));
	     	out2.flush();
	     	out2.close();
	     	
	     	System.out.println("-----------------");
	     	for(Record r:listCpu)
	     		System.out.println(r.toString());
			
			break;
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Создаем READER
		BufferedReader reader = request.getReader();
		
		//Создания объекта JSON
		Gson gson = new Gson();
		
		StringBuilder sb = new StringBuilder();
		String str = null;
		Date date = new Date();
		
		//Чтение данных
		while ((str = reader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
		
		//Десериализайия данных и создание объекта статуса
		Record obj = gson.fromJson(sb.toString(), Record.class);
		obj.setDateReg(date);
		
		//Записываем данные в память
		list.add(obj);
				
	}
	
	//Сортировка по заданным условиям
	protected ArrayList<Record> sortDate (ArrayList<Record> list){
		//Отсекаем по статусу
		for(Record rec:list){
			if (rec.getState().equals("UP"))
				listUp.add(rec);
		};
		
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
	protected ArrayList<Record> sortCpu(ArrayList<Record> list, int min, int max){
		for(Record rec:list){
			if (rec.getCpu() >= min && rec.getCpu() <= max)
				listCpu.add(rec);
		};
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
		
		return listCpu;
	}

}