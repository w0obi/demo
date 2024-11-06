package com.example.demo;

public class DemoMain {
	public static void main(String[] args) {
		DemoModel model1 = DemoModel.builder()
		                             .id("ABC123")
		                             .build();
		System.out.println("DemoModel 1 : " + model1.getId());//ABC123
		
		DemoModel model2 = new DemoModel("CBA456");    
		System.out.println("DemoModel 2 : " + model2);
		
		DemoModel model3 = new DemoModel(null);//예외 발생
	}
}