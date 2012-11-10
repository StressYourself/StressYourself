package de.dhbw.stress_yourself.extend;

import java.util.LinkedList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Test {

	private static final String[] arithmeticOperators = { "+", "-", "*", "/",
			"%" };
	private static final String[] compareOperators = { "==", "!=", "<", ">",
			"<=", ">=" };
	private static final String[] incdecOperators = { "++", "--" };
	private static final String[] logicalOperators = { "&&", "&", "||", "|",
			"^" };

	public static void main(String args[]) {
		String test = "7*4+2*8<=1+3*5*4";
		System.out.println("result " + evaluateTest(generateTest(1)));
		System.out.println("result " + evaluateTest(generateTest(2)));
		System.out.println("result " + evaluateTest(generateTest(3)));

	}

	public static String generateNumberWithIncDec(int high, int low, int incdec) {
		String number = String.valueOf(randomNumber(high, low));
		if (incdec == 1) {
			return incdecOperators[randomNumber(incdecOperators.length - 1, 0)]
					+ number;
		} else if (incdec == 2) {
			return number
					+ incdecOperators[randomNumber(incdecOperators.length - 1,
							0)];
		} else {
			return number;
		}
	}

	public static int randomNumber(int high, int low) {
		return (int) (Math.random() * (high - low) + low);
	}

	public static String generateCalculation(int number) {
		String result = "";
		for (int i = 1; i <= number; i++) {
			result += String.valueOf(randomNumber(9, 1));
			result += arithmeticOperators[randomNumber(
					arithmeticOperators.length - 1, 0)];
		}
		result += String.valueOf(randomNumber(9, 1));
		return result;
	}

	public static String generateCalculationComparison(int number) {
		String result = "";
		result += generateCalculation(number);
		result += compareOperators[randomNumber(compareOperators.length, 0)];
		result += generateCalculation(number);
		return result;
	}

	public static String generateEasyCalculation() {
		return generateCalculationComparison(2);
	}

	public static String generateMediumCalculation() {
		String result = "";
		result += generateCalculationComparison(1);
		result += logicalOperators[randomNumber(logicalOperators.length - 1, 0)];
		result += generateCalculationComparison(1);
		return result;
	}

	public static String generateHardCalculation() {
		String result = "";
		result += generateCalculationComparison(2);
		result += logicalOperators[randomNumber(logicalOperators.length - 1, 0)];
		result += generateCalculationComparison(2);
		return result;
	}

	public static String generateTest(int difficulty) {
		String result = "";
		String solution = "";
		do {
			switch (difficulty) {
			case 1:
				result = generateEasyCalculation();
				break;
			case 2:
				result = generateMediumCalculation();
				break;
			case 3:
				result = generateHardCalculation();
				break;
			}
			solution = String.valueOf(evaluateTest(result));
		} while ((solution != "false") && (solution != "true"));
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
