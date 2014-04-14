package ru.smirnov.anton;

import java.util.Comparator;

public class CompareCPU implements Comparator<Record>{
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
}
