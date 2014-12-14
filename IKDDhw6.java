/*HW6 ²Ä¤»²Õ ¼B¥°°¶ ¶À¬f·ì
 * input:command line OR Scanner
 * output:two cluster file
 * 
 * 
 *
 * 
 * environment:eclipse in windows
 * 
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;

public class IKDDhw6 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String instr;
		if(args.length==0)
		{
			Scanner sc=new Scanner(System.in);
			System.out.println("input filename:");
			instr=sc.nextLine();
			sc.close();
		}
		else
		{
			instr=args[0];
		}
		try
		{
			int num=16;
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(instr)));
			Vector<double[]> data =new Vector<double[]>();
			String temp;
			while((temp=in.readLine())!=null)
			{
				double ddata[]=new double[num];
				for(int i=0;i<num;++i)
				{
					temp=temp.substring(temp.indexOf(',')+1);
					switch(temp.charAt(0))
					{
					case 'n':
						ddata[i]=0;
						break;
					case 'y':
						ddata[i]=1;
						break;
					case '?':
						ddata[i]=0;
						break;
					}
					data.add(ddata);
				}
			}
			in.close();
			double[] cluster1=new double[num];
			double[] cluster2=new double[num];
			double[] zero=new double[num];
			int length=data.size()/num;
			Random ran=new Random();
			for(int i=0;i<num;++i)
			{
				cluster1[i]=ran.nextFloat();
				cluster2[i]=ran.nextFloat();
				zero[i]=0;
			}
			boolean endloop=true;
			int time=0;
			while(endloop&&time<100)
			{
				double[] c1=zero.clone();
				double[] c2=zero.clone();
				int l1=0;
				int l2=0;
				time++;
				for(int i=0;i<length;++i)
				{
					double d1;
					double d2;
					d1=distance(cluster1,data.get(i),num);
					d2=distance(cluster2,data.get(i),num);
					if(d1<d2)
					{
						l1++;
						for(int i1=0;i1<num;++i1)
						{
							c1[i1]+=data.get(i)[i1];
						}
					}
					else
					{

						l2++;
						for(int i1=0;i1<num;++i1)
						{
							c2[i1]+=data.get(i)[i1];
						}
					}
				}
				for(int i1=0;i1<num;++i1)
				{
					c1[i1]/=l1;
					c2[i1]/=l2;
				}
				if(distance(cluster1,c1,num)==0)
				{
					endloop=false;
				}
				cluster1=c1.clone();
				cluster2=c2.clone();
			}

			FileWriter ops1=new FileWriter("cluster1.csv");
			FileWriter ops2=new FileWriter("cluster2.csv");
			

			for(int i=0;i<length;++i)
			{
				double d1;
				double d2;
				d1=distance(cluster1,data.get(i),num);
				d2=distance(cluster2,data.get(i),num);
				if(d1<d2)
				{
					ops1.write(i+1+"\n");
				}
				else
				{
					ops2.write(i+1+"\n");
				}
			}
			ops1.close();
			ops2.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
			
	}
	private static double distance(double[] d1,double[] d2,int num)
	{
		double result=0;
		for(int i=0;i<num;++i)
		{
			result+=((d1[i]-d2[i])*(d1[i]-d2[i]));
		}
		result=Math.sqrt(result);
		return result;
	}
	
}
