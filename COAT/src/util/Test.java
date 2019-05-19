package util;

import java.text.DecimalFormat;

public class Test {
	/**
	    * 显示JVM总内存，JVM最大内存和总空闲内存
	    */
	    public void displayAvailableMemory() {
	        DecimalFormat df = new DecimalFormat("0.00") ;

	        //显示JVM总内存
	        long totalMem = Runtime.getRuntime().totalMemory();
	        System.out.println(df.format(totalMem/1000000F) + " MB");
	        //显示JVM尝试使用的最大内存
	        long maxMem = Runtime.getRuntime().maxMemory();
	        System.out.println(df.format(maxMem/1000000F) + " MB");
	        //空闲内存
	        long freeMem = Runtime.getRuntime().freeMemory();
	        System.out.println(df.format(freeMem/1000000F) + " MB");
	        
	        System.out.println(Runtime.getRuntime().availableProcessors());
	        
	    }

	    /**
	    * Starts the program
	    *
	    * @param args the command line arguments
	    */
	    public static void main(String[] args) {
	        new Test().displayAvailableMemory();
	    }
	    
	    
}
