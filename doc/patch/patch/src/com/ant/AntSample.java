package com.ant;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.taskdefs.Javac.ImplementationSpecificArgument;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.ZipFileSet;

import com.patch.SVNManager;
import com.util.SysConfig;

public class AntSample {
	private static String ROOT = SVNManager.desPath+"/temp";
	private static String MAVEN_JAR = SysConfig.MAVEN_JAR;
    
    public static void copy(File source, File targetDir) {
    	Project currentProject = new Project();
    	Copy copy = new Copy();
    	copy.setProject(currentProject);
    	copy.setTodir(targetDir);
    	copy.setOverwrite(true);
    	copy.setFile(source);
    	copy.execute();
    }
    
    public static void copyfolder(File sourceDir, File targetDir) {
    	Project currentProject = new Project();
    	Copy copy = new Copy();
    	copy.setProject(currentProject);
    	copy.setTodir(targetDir);
    	copy.setOverwrite(true);
    	FileSet fileset = new FileSet();
    	fileset.setDir(sourceDir);
    	fileset.setIncludes("**/*.class");
		copy.addFileset(fileset);
    	copy.execute();
    }
    
    public static void clean(String zipPack) {
    	System.gc();
    	//删除项目目录
        Delete del1 = new Delete();
    	del1.setDir(new File(ROOT.substring(0, ROOT.lastIndexOf("/")+1)+zipPack));
    	del1.execute();
    	File file = new File(ROOT);
    	if (file.exists()) {
    		//删除ROOT目录
    		Delete del = new Delete();
    		del.setDir(new File(ROOT));
    		del.execute();
    	}
    }
    
    public static void exec(){
    	Project currentProject = new Project();
        //填写工程的绝对目录，这样后面可以使用相对目录
        currentProject.setBaseDir(new File(ROOT));
    	//创建classes目录
    	Mkdir dir = new Mkdir();
    	dir.setProject(currentProject);
    	dir.setDir(new File(ROOT+"/classes"));
    	dir.execute();
        Javac compileJava = new Javac();
        compileJava.setProject(currentProject);
        //调用JDT编译器
        compileJava.setCompiler("org.eclipse.jdt.core.JDTCompilerAdapter");
        Path classPath = new Path(currentProject);
        FileSet fs = new FileSet();
        fs.setDir(new File(ROOT+"/lib"));
        fs.setIncludes("*.jar");
        classPath.addFileset(fs);
        if (null != MAVEN_JAR && !"".equals(MAVEN_JAR)) {
        	FileSet fs1 = new FileSet();
        	fs1.setDir(new File(MAVEN_JAR));
        	fs1.setIncludes("**/*.jar");
        	classPath.addFileset(fs1);
        }
        compileJava.setClasspath(classPath);
        compileJava.setEncoding("UTF-8");
        //填写相对目录SRC
        compileJava.setSrcdir(new Path(currentProject, "src"));
        //编译后的结果.class输出到哪里
        compileJava.setDestdir(new File(ROOT+"/classes"));
        compileJava.setTarget(SysConfig.TARGET_VER);
        compileJava.setSource(SysConfig.SOURCE_VER);
        compileJava.setNowarn(true);
        compileJava.setDebug(true);
        compileJava.setIncludeantruntime(false);
        compileJava.setMemoryMaximumSize("512m");

        ImplementationSpecificArgument arg = compileJava.createCompilerArg();
        arg.setLine(" -Xlint");

        compileJava.execute();
    }
    
    public static void zip(String zipPack) {
    	Project currentProject = new Project();
        currentProject.setBaseDir(new File(ROOT.substring(0, ROOT.lastIndexOf("/")+1)+zipPack));
        //删除ZIP
        Delete del = new Delete();
    	del.setProject(currentProject);
    	del.setFile((new File(ROOT.substring(0, ROOT.lastIndexOf("/")+1)+"/"+SVNManager.projectName+"_"+zipPack+".zip")));
    	del.execute();
        Zip zip = new Zip();
    	zip.setProject(currentProject);
    	zip.setDestFile(new File(ROOT.substring(0, ROOT.lastIndexOf("/")+1)+"/"+SVNManager.projectName+"_"+zipPack+".zip"));
    	ZipFileSet zipFileSet = new ZipFileSet();
    	zipFileSet.setDir(new File(ROOT.substring(0, ROOT.lastIndexOf("/")+1)+zipPack));
		zip.addZipfileset(zipFileSet);
		zip.execute();
    }
    
}

