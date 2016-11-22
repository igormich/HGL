package jglsl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import materials.Shader;

public class ShaderProcessor {

	
	private static String operations="add|sub|mul|div";
	private static Pattern operationPattern = Pattern.compile(operations);
	private static Map<String,Character> operationsCharacters=new HashMap<String, Character>();
	static {{
		operationsCharacters.put("add", '+');
		operationsCharacters.put("sub", '-');
		operationsCharacters.put("mul", '*');
		operationsCharacters.put("div", '/');
	}}
	private static String process(Class<? extends JShader> clazz) {
		
		StringBuilder uniform=new StringBuilder();
		StringBuilder varying=new StringBuilder();
		for(Field field:clazz.getDeclaredFields()){
			try{
				StringBuilder stringBuilder=null;
				if(field.isAnnotationPresent(Uniform.class)){
					stringBuilder=uniform;
					stringBuilder.append("uniform ");
				}
				if(field.isAnnotationPresent(Varying.class)){
					stringBuilder=varying;
					stringBuilder.append("varying ");
				}
				stringBuilder.append(firstCharToLowerCase(field.getType().getSimpleName()));
				stringBuilder.append(" ");
				stringBuilder.append(field.getName());
				stringBuilder.append(";\n");
			}
			catch(NullPointerException exception){
				throw new IllegalStateException("field "+field.getName()+" is not @Uniform or @Varying");
			}
		}
		
		String mainFunction = processMain(clazz);
		return uniform.toString()+"\n"+varying.toString()+mainFunction;
	}
	private static Object firstCharToLowerCase(String string) {
		return string.substring(0, 1).toLowerCase()+string.substring(1);
	}
	private static String processMain(Class<? extends JShader> clazz) {
		String code=Decompiler.decompile(clazz,"main");
		
		String packageName="jglsl";
		code=code.replaceAll("@Override", "");
		code=code.replaceAll("public ", "");
		//remove casting
		code=code.replaceAll("\\("+packageName+".Vec\\d?\\)", "");
		code=code.replaceAll("\\("+packageName+".Sampler\\d?D\\)", "");
		code=code.replaceAll("\\("+packageName+".Mat\\d?\\)", "");
		//remove "this."
		code=code.replaceAll("this\\.", "");
		//remove Float boxing
		code=code.replaceAll("java.lang.Float.valueOf", "");
		code=code.replaceAll("\\(int\\)", "");
		code=code.replaceAll("\\.floatValue\\(\\)", "");
		
		//remove class.path and replace upper Letter
		code=code.replaceAll(packageName+"\\.Vec", "vec");
		code=code.replaceAll(packageName+"\\.Mat", "mat");	
		code=code.replaceAll("\\.get\\((\\d+?)\\)", "[$1]");
		code=code.replaceAll("\\(java\\.lang\\..+?\\)", "");
		code=code.replaceAll("java\\.lang\\.Float", "float");
		code=code.replaceAll("\\(float\\)", "");
		
		code=parseAll(code);
		
		return code;
	}
	private static String parseAll(String code) {
		Matcher matcher = operationPattern.matcher(code);
		while(matcher.find()){
			int start=matcher.start();
			int position=start+matcher.group().length()+1;
			int splitter = 0;
			int count=1;
			while((code.charAt(position)!=')')||(count>1)){
				if	(code.charAt(position)=='(') count++;
				if	(code.charAt(position)==')') count--;
				if	((code.charAt(position)==',')&&(count==1)) splitter=position;
				position++;
			}
			String firstPart = code.substring(start+matcher.group().length()+1, splitter).trim();
			String secondPart = code.substring(splitter+1, position).trim();
			StringBuilder sb=new StringBuilder(code);
			sb.replace(start, position+1, "("+firstPart+operationsCharacters.get(matcher.group())+secondPart+")");
			code=sb.toString();
			matcher = operationPattern.matcher(code);
		}
		return code;
	}
	public static Shader build(Class<? extends JVertexShader> vertexShader,Class<? extends JFragmentShader> fragmentShader){
		String vertCode=process(vertexShader);
		System.out.println(vertCode);
		String fragCode=process(fragmentShader);
		System.out.println(fragCode);
		Shader result=new Shader();
		result.init(vertCode, fragCode);
		return result;
	}
	
	public static void main(String[] args) {
		String vertCode=process(SimpleVertexShader.class);
		System.out.println(vertCode);
		String fragCode=process(MaskFragmentShader.class);
		System.out.println(fragCode);
	}

}
