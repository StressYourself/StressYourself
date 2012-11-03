package de.dhbw.stress_yourself.extend;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Test {

	private String[] arithmeticOperators = { "+", "-", "*", "/", "%" };
	private String[] compareOperators = { "==", "!=", "<", ">", "<=", ">=" };
	private String[] incdecOperators = { "++", "--" };
	private String[] logicalOperators = { "!", "&&", "&", "||", "|", "^" };
	

	public static void main(String args[]) {
		System.out.println("result " + evaluateTest(generateTest(1)));
		System.out.println("result " + evaluateTest(generateTest(2)));
		System.out.println("result " + evaluateTest(generateTest(3)));
	}

	public static String generateTest(int difficulty) {
		String result = "";
		switch (difficulty) {
		case 1:
			result = "1 && 1";
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
