package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public enum test{
		a(1),b(2);
		// 定义私有变量
		private int nCode;

		// 构造函数，枚举类型只能为私有

		private test(int _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			test.values();
			return String.valueOf(this.nCode);
		}

		public int getCode(){
			return this.nCode;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		for(test test1 : test.values()){
			System.out.println(test1+"---"+test1.getCode()+"---"+test1.toString());
		}
	}
}
