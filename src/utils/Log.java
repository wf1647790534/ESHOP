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
	 * 1.��һ�ֹ���������־Ĭ���������ǰ�����ļ����µ�log.txt�ļ��У���־��ǩΪLog���hash��
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
	 * 2.�����������ļ����������outָ�����ļ��У���־��ǩΪĬ��
	 * @param out	���������
	 */
	public Log(PrintStream out){
		writer = out;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		tag = this.toString();
	}
	/**
	 * 3.ͬ��������
	 * @param os	���������
	 */
	public Log(OutputStream os){
		
		writer = new PrintStream(os);
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		tag = this.toString();
		
		
	}
	/**
	 * 
	 * 4.�������ģ������־���ù�����Ŀ�ļ����µ�log.txt�ļ��У���ǩΪtag
	 * @param tag	��־��ǩ
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
	 * ��־�����out���������ָ�����ļ��У���־��ǩΪtagָ���ı�ǩ
	 * @param tag	��־��ǩ
	 * @param out	���������
	 */
	public Log(String tag,PrintStream out){
		writer = out;
		this.tag = tag;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
	/**
	 * ͬLog(String tag,PrintStream out)
	 * @param tag
	 * @param os
	 */
	public Log(String tag,OutputStream os){
		writer = new PrintStream(os);
		
		this.tag = tag;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
	
	/**
	 * ����ļ���pathָ����·�������ļ��У���־��ǩΪtag
	 * @param tag	��־��ǩ
	 * @param path	��־�ļ�����·��
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
	 * @param tag ��־��ǩ	
	 * @param f		��־����ļ�
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
	 * ������־�ļ����λ��
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
	 * �����ļ����λ��
	 * @param path ��־�ļ����λ��
	 */
	public void setLog(String path){
		File log = new File(path);
		setLog(log);
	}
	
	/**
	 * @return	������־�ļ�����Ŀ¼
	 */
	public String getDir(){
		return log.getAbsolutePath();
	}
	
	/**
	 * ���ô˷���д����־
	 * @param target	��־��Ϣ		
	 */
	public void writer(String target){
		writer.append(tag).append('\t').append(sdf.format(new Date())).append('\t').append(target).append("\r\n");
		writer.flush();
	}
	/**
	 * �ر����������
	 */
	public void close(){
		writer.close();
	}
	
	/**
	 * ����־�ļ���д����Ϣ
	 * @param tag	��־��ǩ
	 * @param target��־��Ϣ
	 */
	public static void writer(String tag,String target)
	{
		Log log2 = new Log(tag);
		log2.writer(target);
		log2.close();
	}
	/**
	 * ��ָ���������Ӧ���ļ���д����־��Ϣ
	 * @param tag	��־��ǩ
	 * @param out	���������
	 * @param target��־��Ϣ
	 */
	public static void writer(String tag,OutputStream out,String target){
		Log log2 = new Log(tag,out);
		log2.writer(target);
		log2.close();
	}
	/**
	 * ͬwriter(String,OutputStream,String)
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
	 * ��ָ���ļ���д����־��Ϣ
	 * @param tag		��־��ǩ
	 * @param path		�ļ�·�������ļ���
	 * @param target	��־��Ϣ
	 */
	public static void writer(String tag,String path,String target){
		Log log2 = new Log(tag,path);
		log2.writer(target);
	}
}
