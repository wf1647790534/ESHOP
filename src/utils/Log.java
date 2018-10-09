package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Log {
	public static void main(String[] args) {
		Log log = new Log();
		log.writer("xxxxxxx");
		Date date = new Date();
		System.out.println(date);
		log.close();
		Log.writer("XXX", "utils.Log.writer test!");
	}
	
	/**
	 * 1.第一种构造器，日志默认输出到当前工程文件夹下的log.txt文件中，日志标签为Log类的hash码
	 */
	public Log() {
		log = new File("log.txt");
		try {
			writer = new PrintStream(new FileOutputStream(log,true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		tag = this.toString();
	}
	
	/**
	 * 2.构造器二，文件出到输出流out指定的文件中，日志标签为默认
	 * @param out	输出流对象
	 */
	public Log(PrintStream out){
		writer = out;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		tag = this.toString();
	}
	/**
	 * 3.同构造器二
	 * @param os	输出流对象
	 */
	public Log(OutputStream os){
		
		writer = new PrintStream(os);
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		tag = this.toString();
		
		
	}
	/**
	 * 
	 * 4.构造器四，输出日志到该工程项目文件夹下的log.txt文件中，标签为tag
	 * @param tag	日志标签
	 */
	public Log(String tag){
		log = new File("log.txt");
		try {
			writer = new PrintStream(new FileOutputStream(log,true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.tag = tag;
	}
	/**
	 * 日志输出到out输出流对象指定的文件中，日志标签为tag指定的标签
	 * @param tag	日志标签
	 * @param out	输出流对象
	 */
	public Log(String tag,PrintStream out){
		writer = out;
		this.tag = tag;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
	/**
	 * 同Log(String tag,PrintStream out)
	 * @param tag
	 * @param os
	 */
	public Log(String tag,OutputStream os){
		writer = new PrintStream(os);
		
		this.tag = tag;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
	
	/**
	 * 输出文件到path指定的路径或者文件中，日志标签为tag
	 * @param tag	日志标签
	 * @param path	日志文件或者路径
	 */
	public Log(String tag,String path){
		try {
			File f1 = new File(path);
			File f2 = null;
			if(f1.exists()){
				if(f1.isDirectory())
					f2 = new File(f1,"log.txt");
				else
					f2 = f1;
			}else{
				f1.mkdirs();
				f2 = new File(f1,"log.txt");
			}
			writer = new PrintStream(new FileOutputStream(f2,true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.tag = tag;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
	
	/**
	 * @param tag 日志标签	
	 * @param f		日志存放文件
	 */
	public Log(String tag,File f){
		try {
			if(f.exists()){
				if(f.isDirectory())
					f = new File(f,"log.txt");
				writer = new PrintStream(new FileOutputStream(f,true));
			}else{
				f.mkdirs();
				f = new File(f,"log.txt");
				writer = new PrintStream(new FileOutputStream(f,true));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.tag = tag;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
	
	private String tag;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public SimpleDateFormat getSdf() {
		return sdf;
	}
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
	private PrintStream writer;
	
	private File log;

	private SimpleDateFormat sdf;

	public PrintStream getWriter() {
		return writer;
	}

	public void setWriter(PrintStream writer) {
		this.writer = writer;
	}

	public File getLog() {
		return log;
	}

	/**
	 * 设置日志文件存放位置
	 * @param log
	 */
	public void setLog(File log) {
		this.log = log;
		try {
			if(log.exists()){
				if(log.isDirectory())
					log = new File(log,"log.txt");
			}else{
				log.mkdirs();
				log = new File(log,"log.txt");
			}
			writer = new PrintStream(new FileOutputStream(log));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置文件存放位置
	 * @param path 日志文件存放位置
	 */
	public void setLog(String path){
		File log = new File(path);
		setLog(log);
	}
	
	/**
	 * @return	返回日志文件绝对目录
	 */
	public String getDir(){
		return log.getAbsolutePath();
	}
	
	/**
	 * 调用此方法写入日志
	 * @param target	日志信息		
	 */
	public void writer(String target){
		writer.append(tag).append('\t').append(sdf.format(new Date())).append('\t').append(target).append("\r\n");
		writer.flush();
	}
	/**
	 * 关闭输出流对象
	 */
	public void close(){
		writer.close();
	}
	
	/**
	 * 向日志文件中写入信息
	 * @param tag	日志标签
	 * @param target日志信息
	 */
	public static void writer(String tag,String target)
	{
		Log log2 = new Log(tag);
		log2.writer(target);
		log2.close();
	}
	/**
	 * 向指定流对象对应的文件中写入日志信息
	 * @param tag	日志标签
	 * @param out	输出流对象
	 * @param target日志信息
	 */
	public static void writer(String tag,OutputStream out,String target){
		Log log2 = new Log(tag,out);
		log2.writer(target);
		log2.close();
	}
	/**
	 * 同writer(String,OutputStream,String)
	 * @param tag
	 * @param out
	 * @param target
	 */
	public static void writer(String tag,PrintStream out,String target){
		Log log2 = new Log(tag,out);
		log2.writer(target);
		log2.close();
		
	}
	
	/**
	 * 向指定文件中写入日志信息
	 * @param tag		日志标签
	 * @param path		文件路径或者文件名
	 * @param target	日志信息
	 */
	public static void writer(String tag,String path,String target){
		Log log2 = new Log(tag,path);
		log2.writer(target);
	}
}
