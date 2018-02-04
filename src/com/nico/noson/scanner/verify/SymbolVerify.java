package com.nico.noson.scanner.verify;

/** 
 * Noson符号验证者
 * @author nico
 * @version 创建时间：2017年11月25日 上午1:16:20
 */

public class SymbolVerify {

	//大括号当前闭合状态
	private int brace = 0;

	//中括号当前闭合状态	
	private int bracket = 0;

	//单引号当前闭合状态
	private int singleQuote = 0;

	//双引号当前闭合状态
	private int doubleQuote = 0;
	
	//读取value标记的前提是前方存在冒号
	private int colon = 0;

	//临时参数
	private int temp = 0;

	/**
	 * 验证当前扫描节点全部符号是否闭合
	 * @return 验证结果
	 */
	public boolean safetyAll(){
		return brace == 0 && bracket == 0 && singleQuote == 0 && doubleQuote == 0 && temp == 0;
	}
	
	/**
	 * 验证当前扫描节点引号（单/双）是否闭合
	 * @return 验证结果
	 */
	public boolean safetyQuote(){
		return singleQuote == 0 && doubleQuote == 0;
	}
	
	/**
	 * 验证当前扫描节点大括号是否闭合
	 * @return 验证结果
	 */
	public boolean safetyBrace(){
		return brace == 0;
	}
	
	/**
	 * 验证当前扫描节点中括号是否闭合
	 * @return 验证结果
	 */
	public boolean safetyBracket(){
		return bracket == 0;
	}
	
	/**
	 * 验证当前扫描节点单引号是否闭合
	 * @return 验证结果
	 */
	public boolean safetySingleQuote(){
		return singleQuote == 0;
	}
	
	/**
	 * 验证当前扫描节点双引号是否闭合
	 * @return 验证结果
	 */
	public boolean safetyDoubleQuote(){
		return doubleQuote == 0;
	}
	
	/**
	 * 验证当前扫描结点是否在读取value的状态</br>
	 * <b>introduce：</b>
	 * 当扫描到‘:’时说明准备开始读取value了
	 * @return
	 */
	public boolean turnValue(){
		return colon == 1;
	}
	
	
	/**
	 * 对json敏感符号扫描，命中则返回true
	 * @param target
	 * @return
	 */
	public boolean verify(char target){
		int sum = brace + bracket + singleQuote + doubleQuote;
		switch(target){
		case '{': brace++; break;
		case '}': brace--; break;
		case '[': bracket++; break;
		case ']': bracket--; break;
		case '\'': if(doubleQuote == 0) temp = (singleQuote == 1) ? --singleQuote : ++singleQuote; break;
		case '\"': if(singleQuote == 0) temp = (doubleQuote == 1) ? --doubleQuote : ++doubleQuote; break;
		case ':': if( singleQuote + doubleQuote == 0) colon = 1; break;
		}
		return sum != (brace + bracket + singleQuote + doubleQuote);
	}
	
	/**
	 * 检查json字符串是否合法
	 * @param json
	 * @return
	 */
	public boolean check(String json){
		char[] chars = json.toCharArray();
		int index = 0;
		for(char ch: chars){
			verify(ch);
		}
		return safetyAll();
	}

	/**
	 * 判断符号是否为特殊符号
	 * <b>example：</b>引号闭合的情况下扫描到‘:’就返回true
	 * @param c 
	 * @return
	 */
	public boolean isSpecial(char c){
		if(singleQuote + doubleQuote != 0) return false;
		return c == ':';
	}
	
	public int getBrace() {
		return brace;
	}

	public void setBrace(int brace) {
		this.brace = brace;
	}

	public int getBracket() {
		return bracket;
	}

	public void setBracket(int bracket) {
		this.bracket = bracket;
	}

	public int getSingleQuote() {
		return singleQuote;
	}

	public void setSingleQuote(int singleQuote) {
		this.singleQuote = singleQuote;
	}

	public int getDoubleQuote() {
		return doubleQuote;
	}

	public void setDoubleQuote(int doubleQuote) {
		this.doubleQuote = doubleQuote;
	}

	public int getColon() {
		return colon;
	}

	public void setColon(int colon) {
		this.colon = colon;
	}
	
}
