package de.dhbw.stress_yourself.extend;

import java.util.LinkedList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Test {

	private static final String[] arithmeticOperators = { "+", "-", "*", "/", "%" };
	private static final String[] compareOperators = { "==", "!=", "<", ">", "<=", ">=" };
	private static final String[] incdecOperators = { "++", "--" };
	private static final String[] logicalOperators = { "!", "&&", "&", "||", "|", "^" };
	private static final String[] bool = {"==","!="};

	
	
	
	public static void main(String args[]) {
		System.out.println("result " + evaluateTest(generateTest(1)));
		System.out.println("result " + evaluateTest(generateTest(2)));
		System.out.println("result " + evaluateTest(generateTest(3)));
	}
	
	public static int randomNumber(int high, int low){
		return (int) (Math.random() * (high - low) + low);
	}

	public static String generateTest(int difficulty) {
		
		String test = "";
		test += String.valueOf(randomNumber(9, 0));
		test += arithmeticOperators[randomNumber(arithmeticOperators.length -1, 0)];
		test += String.valueOf(randomNumber(9, 0));
		test += bool[randomNumber(bool.length -1, 0)];
		test += String.valueOf(randomNumber(9, 0));
		test += arithmeticOperators[randomNumber(arithmeticOperators.length -1, 0)];
		test += String.valueOf(randomNumber(9, 0));
		
		String result = "";
		switch (difficulty) {
		case 1:
			result = test;
			break;
		case 2:
			result = "1+1 && 2-1";
			break;
		case 3:
			result = "1==5*1-4";
			break;
		}

		System.out.println("generated " + result);
		return result;
	}

	public static Object evaluateTest(String test) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		try {
			System.out.println("eval " + test);
			return engine.eval(test);
		} catch (ScriptException e) {
			System.err.println("Couldn't evaluate the String " + e);
		}
		return null;
	}
}
