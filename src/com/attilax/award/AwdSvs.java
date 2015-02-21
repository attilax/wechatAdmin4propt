package com.attilax.award;

import java.util.ArrayList;
import java.util.List;

import com.attilax.core;
import com.attilax.util.Funcx;
import com.attilax.util.IdefaultMethod;
import com.attilax.util.randomx;

public class AwdSvs {

	public AwdSvs() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

//testFirst();
		TestSvs.forx(10,new IdefaultMethod(){

			@Override
			public Object $(Object param) {
				// 上午09:27:55   2014-4-29 
				int rdmAwdIndex_may=randomx.random(100000);
				System.out.println(rdmAwdIndex_may);
				return null;
			}});
		
		
		

	}

	private static void testFirst() {
		String awds="1,'平板电脑',7@2,'数码相机',5@3,'音箱设备',10";
		String[] a=awds.split("@");
		List li=new ArrayList();
		for(String s : a)
		{
			String[] a2=s.split(",");
			Awardx awd=new Awardx();
			awd.id=Integer.parseInt(a2[0]);
			awd.name=a2[1];
			awd.prbblt=Integer.parseInt(a2[2]);
			li.add(awd);
		}
		
		 for(int i=0;i<20;i++)
		 {
		 Awardx  bingoAwd=getBingoAwd(li);
		 if(bingoAwd!=null)
		 System.out.println(bingoAwd.name);
		 else
			 System.out.println("null");
		 }
	}
/**
 * o429 
 * @param li
 * @return
 */
	public static Awardx getBingoAwd(List<Awardx> li) {
		int rdmAwdIndex_may=randomx.random(li.size()-1);
		System.out.println("may index::"+rdmAwdIndex_may);
		Awardx awd=(Awardx) li.get(rdmAwdIndex_may);
		int rdm=randomx.random(100000);
		core.log("--o42910: rdmAwdIndex_may--rdm--awd.prbblt"+String.valueOf(rdmAwdIndex_may)+"--"+String.valueOf(rdm)+"--"+String.valueOf(awd.prbblt));
		if(rdm<awd.prbblt)
		{
			//bingo
			return awd;
			
		}
		return null;
	}

}
