package com.attilax.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.attilax.collection.listUtil;
import com.attilax.util.securyInt;

 
public class strUtil1 {
	
	public static Object pipeidu="pipeidu";

	public static boolean isStr(Object obj)
	{
		if(obj instanceof String)
			return true;
		else
			return false;
	}

	public static List<String> toList(String extword) {
		List<String> li=new  ArrayList<String>();
		String[] a=extword.split(",");
		for(String str : a)
		{
			li.add(str);
		}
		return li;
	}
	

	public static boolean isFind(String s, String dweiu) {
		//孩[^,，�?\.]*?�?
		String regEx = dweiu.replaceAll("\\*", "[^,，�?\\.]{0,10}?"); //表示a或F  
	//	System.out.println("\r\n"+regEx);
		Pattern pat = Pattern.compile(regEx);  
		Matcher mat = pat.matcher(s);  
		boolean rs = mat.find();
		return rs;
	}
	public static List<String> toList(String[] strArr) {
		List<String> li=new  ArrayList<String>();
		 
		for(String str : strArr)
		{
			li.add(str);
		}
		return li;
	}

	public static String trimx(String trimedChar, String tonitsi) {
		//tonitsi.trim()
		//StringUtils.trim(str)
	    String a =tonitsi;
	    a = a.trim();  
	    while(a.startsWith(trimedChar)){  
	       a = a.substring(1,a.length()).trim();  
	    }  
	    while(a.endsWith(trimedChar)){  
	       a = a.substring(0,a.length()-1).trim();  
	    }  
	   
		return a;
	}

	public static Set toSet(String linex) {
		Set<String> li=new  HashSet<String>();
		String[] a=linex.split(",");
		for(String str : a)
		{
			li.add(str);
		}
		return li;
	}

	public static int chwsyeNum(String str, String typeKeywords) {
	//	String[] a=str.split("\\|");
		String[] a=strUtilO33.splitx(str, "\\|");
	 
		int n=0;
		Set set=listUtil.toSet(typeKeywords);
		for(String word:a)
		{
			
		//	if(typeKeywords.contains(word))
			if(set.contains(word))
			{
				n++;
			//	System.out.println("-------------chwsyeNum:"+word);
			}
		}
		return n;
	}
	
	public static void main(String[] args) {
	String s="	005	孩子教育	儿子 女儿 孩子, 宝宝 小孩 子女";
	 String[] a=splitx(s, "\t");
	//	System.out.println(a);
	 String dweiu="";
	// System.out.println(isFind(s, dweiu));
	 
	// System.out.println(getListSqlFmt("啊挨癌岸凹傲扒拔把掰百拜稗瓣邦绑蚌胞堡爆碑奔绷嘣蹦彼笔闭裨扁便遍镖憋别濒冰兵柄播帛泊不埠擦猜财舱藏操糙嘈槽草册测恻层曾蹭叉茶查拆�?��蝉尝怅潮扯撤趁呈乘池尺冲仇处揣踹喘疮窗床创吹捶锤瓷雌此刺葱从凑粗促窜催脆存寸搓达逮蛋裆岛到得蹬等瞪堤敌嫡电刁凋吊跌叠丁钉定丢动栋洞兜毒端短断堆吨蹲跺恩�?二贰发伐帆烦返方防仿纺放飞非菲肥肺废费氛坟焚粉风封烽蜂佛否扶负覆该钙盖杆港杠高搁格给根�?梗更弓汞拱供苟沽瓜刮挂乖拐关馆光广�?瑰鬼刽贵滚国涵行航�?合鹤黑嘿很红吼琥花划化�?淮坏换慌黄晃悔烩混即佳家颊假尖茧姜讲奖嚼觉解斤金浸颈窘救就举剧锯捐卷决绝掘军君菌俊开刊砍槛看扛铐靠嗑磕瞌咳渴克课啃孔抠夸垮跨块快宽款狂矿框亏�?��困阔赖澜涝勒了垒肋里脸恋良凉俩亮辆量聊列劣临铃领流拢卵乱抡论旅铝绿略马买卖瞒蔓茫猫冒枚酶美闷们猛眯迷免面苗秒妙灭泯名鸣冥谋某亩那纳捺蝻囊攮挠脑嫩尼�?霓鲵拟你腻溺拈蔫黏撵碾念娘酿鸟捏啮镍孽您宁狞拧牛纽拗农弄奴努暖虐挪女欧殴偶啪排牌潘判盼畔旁膀刨胚抨嘭朋坯披匹癖偏篇漂瞟瞥撇拼贫品聘平凭坡泼颇叵破朴谱沏气迄砌掐恰扦牵黔呛跷锹桥俏翘撬鞘妾怯惬秦擒噙青轻擎请磬穷琼求泅球裘祛蛆渠去圈权蜷犬券缺瘸阙群燃染饶绕惹人纫扔日熔揉乳褥软蕊润若弱仨洒撒卅腮散搔骚缫扫臊色涩沙筛膻扇擅伤晌尚勺舍社申沈渗笙圣施湿石识恃舐释嗜噬螫售�?赎熟漱刷耍摔甩闩栓涮双霜谁顺舜司四寺肆松宋颂艘苏俗粟虽隋随穗孙损缩索锁她塌塔沓榻苔太滩瘫坛塘涛梯题蹄体屉替天甜舔挑帖铁听停佟童捅透凸图湍�?��屯砣跎妥椭拓剜挽�?��伪委闻翁毋捂习峡辖霞下吓衔险相香湘镶萧邪携谢锌星醒凶胸雄熊修徐许婿宣悬�?癣削薛穴雪熏旬训压哑烟腌演雁秧羊�?��掖爷倚忆荫音殷引应涌用友有幼于余御员怨愿月粤晕匀允郓恽栽宰咱暂葬枣澡灶择贼�?增赠轧闸债栈张涨掌赵折褶浙枕争蒸郑指志质窒痣置轴绉朱住筑抓拽转篆庄装幢追椎准捉浊啄子紫字邹钻嘴最醉尊"));
	  String string = "�?�?�?你大;哥�?";
	  System.out.println(delPunctualion(string));
	
	}
		
	private static String getListSqlFmt(String string) {
		String s="";
		char[] a=string.toCharArray();
		for(char c : a )
		{
			String s2="'"+  String.valueOf(c)+"'";
			s=s+","+s2;
			
		}
		 s=strUtilO33.trimx(",", s);
		return s;
	}

	/**
	 *  StringTokenizer 是出于兼容�?的原因�?被保留的遗留�?虽然在新代码中并不鼓励使用它)。建议所有寻求此功能的人使用 String �?split 方法
	 * @param str
	 * @param splitor
	 * @return
	 */
	public static String[] splitx(String str, String splitor) {
		
	
	 String s = new String("The=Java=platform=is=the=ideal=platform=for=network=computing");
	 s=str;
	StringTokenizer st = new StringTokenizer(s,splitor);
	int len=st.countTokens();
	String[] arr=new String[len];
	//System.out.println( "Token Total: " + st.countTokens() );
	int n=0;
	while( st.hasMoreElements() ){
//	System.out.println( st.nextToken() );
	
		arr[n]=st.nextToken();
		n++;
	}
	return arr;
	
	}

	public static int chwsyeNum(String str, String typeKeywords,
			Map eventListOnlyKeyword) {
//		String[] a=str.split("\\|");
		String[] a=strUtilO33.splitx(str, "\\|");
	 
		int n=0;
		Set set=(Set) eventListOnlyKeyword.get(typeKeywords);
		for(String word:a)
		{
			
		//	if(typeKeywords.contains(word))
			if(set.contains(word))
			{
				n++;
			//	System.out.println("-------------chwsyeNum:"+word);
			}
		}
		return n;
	}

	public static int chwsyeNum(String str, String typeKeywords,
			Map eventListOnlyKeyword, Map fileLineMap) {
	//	String[] a=strUtil.splitx(str, "\\|");
		String[] a= (String[]) fileLineMap.get(str);
		int n=0;
		Set set=(Set) eventListOnlyKeyword.get(typeKeywords);
		for(String word:a)
		{
			
		//	if(typeKeywords.contains(word))
			if(set.contains(word))
			{
				n++;
			//	System.out.println("-------------chwsyeNum:"+word);
			}
		}
		return n;
	}

	public static String remove65279(String typeShortformat) {
		char[] c=typeShortformat.toCharArray();	 
		char c1=c[0];
		int c1num=c1;
		if(c1num==65279)
		{
			char[] ca2=new char[c.length-1];
			for(int i=0;i<ca2.length;i++)
			{
				ca2[i]=c[i+1];
			}
			return new String(ca2);
		}
		else
		return typeShortformat;
	}

	public static List<String> splitByLen(String w1,int len) {
		
	List<String> list = new ArrayList<String>();
		
	String str=w1;
		while(str.length() > len){
			list.add(str.substring(0, len));
			str = str.substring(len);
		}
		
		if(str.length() > 0){
			list.add(str);
		}
		return list;
		
	}

	public static List<String> toList(String strx, String splitor) {
		List<String> li=new  ArrayList<String>();
		if(strx==null || strx.trim().length()==0)
			return li;
		String[] a=strx.split(splitor);
		for(String str : a)
		{
			li.add(str);
		}
		return li;
	}

	public static String right(String moodx, int i) {
		return	 moodx.substring(moodx.length()-i);
		 
	}
	public static String left(String moodx, int i) {
		return	 moodx.substring(0,i);
		 
	}

	public static String delPunctualion(String string) {
		string=securyInt.getString(string);
	    //先去掉标�?再合并空�?  
		  Pattern   p=Pattern.compile("[(.|,|\"|\\?|!|:|;|'|。|，|”|？|！|：|；|�?]");//这边增加�?��的符�?例如要加�?��'则变成[(.|,|\"|\\?|!|:|')],如果是特殊符号要加转�?  \   
		  Matcher   m=p.matcher(string);//这为要整理的字符�?  
		  String   first=m.replaceAll("");   
		  System.out.println(first+"*******");   
		  p=Pattern.compile("   {2,}");   
		  m=p.matcher(first);   
		  String   second=m.replaceAll("");   
		  System.out.println(second);//second就是你要的字符串�? 
		
		return second;
	}
	
	public   static   void   testReg(){   
		
		 
}  
	
	
 
	
	

}
