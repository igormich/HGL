package jglsl;
import java.util.Collection;

import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.entities.ClassFile;
import org.benf.cfr.reader.entities.Method;
import org.benf.cfr.reader.relationship.MemberNameResolver;
import org.benf.cfr.reader.state.ClassFileSourceImpl;
import org.benf.cfr.reader.state.DCCommonState;
import org.benf.cfr.reader.state.TypeUsageCollector;
import org.benf.cfr.reader.util.CannotLoadClassException;
import org.benf.cfr.reader.util.ConfusedCFRException;
import org.benf.cfr.reader.util.ListFactory;
import org.benf.cfr.reader.util.getopt.GetOptParser;
import org.benf.cfr.reader.util.getopt.Options;
import org.benf.cfr.reader.util.getopt.OptionsImpl;
import org.benf.cfr.reader.util.getopt.PermittedOptionProvider;
import org.benf.cfr.reader.util.output.DumperFactory;
import org.benf.cfr.reader.util.output.DumperFactoryImpl;
import org.benf.cfr.reader.util.output.IllegalIdentifierDump;
import org.benf.cfr.reader.util.output.NopSummaryDumper;
import org.benf.cfr.reader.util.output.ToStringDumper;


public class Decompiler {
	//copied from decompiled CFR jar, only output replaced
	private static String doClass(DCCommonState dcCommonState, String path, DumperFactory dumperFactory,String methname) {
        Options options = dcCommonState.getOptions();
        ToStringDumper d = new ToStringDumper();
        try {
            ClassFile c = dcCommonState.getClassFileMaybePath(path);
            dcCommonState.configureWith(c);
            try {
                c = dcCommonState.getClassFile(c.getClassType());
            }
            catch (CannotLoadClassException e) {
                // empty catch block
            }
            c.analyseTop(dcCommonState);
            TypeUsageCollector collectingDumper = new TypeUsageCollector(c);
            c.collectTypeUsages(collectingDumper);
            // original code commented
            //d = dumperFactory.getNewTopLevelDumper(options, c.getClassType(), (SummaryDumper)summaryDumper, collectingDumper.getTypeUsageInformation(), illegalIdentifierDump);
            //String methname = (String)options.getOption((PermittedOptionProvider.ArgumentParam)OptionsImpl.METHODNAME);
            if (methname == null) {
                c.dump(d);
            } else {
                try {
                  // not reachable code, methname is null
                    for (Method method : c.getMethodByName(methname)) {
                      method.dump(d, true);
                    }
                }
                catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException("No such method '" + methname + "'.");
                }
            }
            d.print("");
        }
        catch (ConfusedCFRException e) {
            System.err.println(e.toString());
            for (StackTraceElement x : e.getStackTrace()) {
                System.err.println(x);
            }
        }
        catch (CannotLoadClassException e) {
            System.out.println("Can't load the class specified:");
            System.out.println(e.toString());
        }
        catch (RuntimeException e) {
            System.err.println(e.toString());
            for (StackTraceElement x : e.getStackTrace()) {
                System.err.println(x);
            }
        }
        finally {
            if (d != null) {
              //old code only close Dumper
            	String result=d.toString();
              d.close();
              return result;
            }
        }
		return null;
    }
	//copied from decompiled CFR jar
	public static String decompile(String pathToFile,String methname) {
		GetOptParser getOptParser = new GetOptParser();
		Options options = (Options)getOptParser.parse(new String[]{pathToFile}, OptionsImpl.getFactory());
		ClassFileSourceImpl classFileSource = new ClassFileSourceImpl(options);
		DCCommonState dcCommonState = new DCCommonState(options, (ClassFileSource)classFileSource);
		DumperFactoryImpl dumperFactory = new DumperFactoryImpl();
		return doClass(dcCommonState, pathToFile, (DumperFactory)dumperFactory,methname);
	}
	public static String decompile(Class<?> clazz,String methname) {
		return decompile("bin/"+clazz.getCanonicalName().replace('.', '/')+".class",methname);
	}
	public static String decompile(Class<?> clazz) {
		return decompile(clazz,null);
	}
	/*public static void main(String[] arg){
	  //decompile this class file
		System.out.println(decompile(Decompiler.class));
	}*/
}