package com.patch;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.ant.AntSample;
import com.db.PackageDAOImpl;
import com.db.Project;
import com.util.SysConfig;
public class SVNManager {
	public static String desPath=SysConfig.BASE_PATH;//补丁文件包存放路径 
	private static String ver="";//补丁版本
	private static String webContent="";//web应用文件夹名
	private static String javaContent="";//java应用文件夹名
	private static String javaParent="";
	private static String javaNoComplier="";
	public static String projectName="";//项目名
	private static String classPath="";
	private static String svn = "";
	private static String url = "";
	private static String name = SysConfig.SVN_NAME;
	private static String pwd = SysConfig.SVN_PWD;
	private static SVNRepository repository;
	
	public static void init(String pName) throws SVNException {
		Project project = PackageDAOImpl.getInstance().getProjectByName(pName);
		desPath = desPath + "/" + pName;
		projectName = project.getProjectName();
		webContent = project.getWebContent();
		javaContent = project.getJavaContent();
		javaParent = project.getJavaParent();
		classPath = project.getClassPath();
		javaNoComplier = project.getJavaNoCompiler();
		svn = project.getSvn();
		url = project.getBranchUrl();
		if ("Y".equals(project.getIsMaven())) {
			SysConfig.setMavenJar();
		}
		SysConfig.setVersion(project.getTargetVer(), project.getSourceVer());
		DAVRepositoryFactory.setup();
		repository = SVNRepositoryFactoryImpl.create(SVNURL.parseURIEncoded(svn+url));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name,pwd);
		repository.setAuthenticationManager(authManager);
	}
	
	public static void getFileFromSVN(String pName,int vstart, int vend) throws Exception{
		init(pName);
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		List<String> addfolders = new ArrayList<String>();
		final Map<String,Character> history = new HashMap<String,Character>();
		final long version = repository.getLatestRevision();
		String temp = vend==-1?String.valueOf(version):String.valueOf(vend);
		ver = new SimpleDateFormat("yyyyMMdd").format(new Date())+"_"+vstart+"_"+temp;
		repository.log(new String[]{""}, vstart, vend, true, true, new ISVNLogEntryHandler() {  
	                   @Override  
	                   public void handleLogEntry(SVNLogEntry svnlogentry) throws SVNException { 
	                	   Map<String, SVNLogEntryPath> svnMap = svnlogentry.getChangedPaths();
	                	   for(String item: svnMap.keySet()){
	                		   SVNLogEntryPath svnlep = svnMap.get(item);
                			   if (item.indexOf(webContent)!=-1 || item.indexOf(javaContent)!=-1) {
                				   history.put(item, svnlep.getType());
                			   }
	                	   }
	                   }  
        });  
		
		for (String path: history.keySet()) {
			if (history.get(path).equals(Character.valueOf(SVNLogEntryPath.TYPE_DELETED))){
				continue;
			}
			if(path.indexOf(webContent) != -1){//对webContent处理  
				if (path.indexOf(".")==-1 && history.get(path).equals(Character.valueOf(SVNLogEntryPath.TYPE_ADDED))){
					List<Resource> list = getAllChildren(path);
					for (Resource resource: list) {
						downloadFile(resource.getPath(),version,false);
					}
				} else if (path.indexOf(".")!=-1) {
					downloadFile(path,version,false);
				}
	            
            } else if (path.indexOf(javaContent) != -1) {//对javaContent处理 
            	if (path.indexOf(".")==-1 && history.get(path).equals(Character.valueOf(SVNLogEntryPath.TYPE_ADDED))){
            		addfolders.add(desPath+"/temp/classes"+path.replace(url+"/"+javaContent, ""));
            	} else if (path.indexOf(".")!=-1) {
            		if (path.indexOf(".java")!=-1) {
            			String name = path.substring(path.lastIndexOf("/")+1,path.indexOf(".java"));
            			String targetfolder = desPath+"/temp/classes"+path.substring(0,path.indexOf(name)-1).replace(url+"/"+javaContent, "");
            			List<String> fileNames = map.get(targetfolder);
            			if (fileNames == null) {
            				fileNames = new ArrayList<String>();
            				fileNames.add(name);
            				map.put(targetfolder, fileNames);
            			} else {
            				fileNames.add(name);
            			}
            		} else {
            			String[] nocs = javaNoComplier.split("\\|");
            			for (String noc: nocs) {
            				if (path.indexOf(noc)!=-1) {
            					downloadFile(path,version,true);
            					break;
            				}
            			}
            		}
            	}
            }
		}
		if (!map.isEmpty()||!addfolders.isEmpty()) {
			transferJavaSrc();
			transferJars();
			AntSample.exec();
		}
		for (String key: map.keySet()) {
			File f = new File(key);
			FilenameFilter filter = new MyFileFilter(map.get(key));
			File[] files = f.listFiles(filter);
			for (File file: files) {
				File target = new File(key.replace("temp/classes", ver+"/"+projectName+"/"+classPath));
				if(!target.exists()){  
					target.mkdirs();  
				}
				AntSample.copy(file, target);
				System.out.println(target.getPath().replace("\\", "/") + "/" + file.getName() + " success");
			}
		}
		for (String folder: addfolders) {
			File file = new File(folder);
			File target = new File(folder.replace("temp/classes", ver+"/"+projectName+"/"+classPath));
			if(!target.exists()){  
				target.mkdirs();  
			}
			AntSample.copyfolder(file, target);
			System.out.println(target.getPath().replace("\\", "/") + " success");
		}
		AntSample.zip(ver);
		AntSample.clean(ver);
	}
	
	private static void downloadFile(String path, long version, boolean isJava) throws Exception{
		path = path.substring(path.indexOf(url),path.length());
		String local = path.replace(url+"/"+webContent, desPath+"/"+ver+"/"+projectName);
		if (isJava) {
			local = path.replace(url+"/"+javaContent, desPath+"/"+ver+"/"+projectName+"/"+classPath);
		}
		File folder = new File(local.substring(0,local.lastIndexOf("/")));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(local);
		SVNProperties properties = new SVNProperties();
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		repository.getFile(path, version, properties, out);
		out.close();
		System.out.println(local + " success");
	}
	
	public static void transferJavaSrc() throws Exception{
		long version = repository.getLatestRevision();
		List<Resource> list = new ArrayList<Resource>();
		for (String jp: javaParent.split("\\|")) {
			list.addAll(getAllChildren(url+"/"+javaContent+"/"+jp));
		}
		for (Resource resource: list) {
			String path = resource.getPath();
            path = path.substring(path.indexOf(url),path.length());
            String local = path.replace(url+"/"+javaContent, desPath+"/temp/src");
            File folder = new File(local.substring(0,local.lastIndexOf("/")));
            if (!folder.exists()) {
            	folder.mkdirs();
            }
            File file = new File(local);
            SVNProperties properties = new SVNProperties();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            repository.getFile(path, version, properties, out);
            out.close();
		}
	}
	
	public static void transferJars() throws Exception{
		long version = repository.getLatestRevision();
		List<Resource> list = getAllChildren(url+"/"+webContent+"/WEB-INF/lib");
		for (Resource resource: list) {
			String path = resource.getPath();
            path = path.substring(path.indexOf(url),path.length());
            String local = path.replace(url+"/"+webContent+"/WEB-INF", desPath+"/temp");
            File folder = new File(local.substring(0,local.lastIndexOf("/")));
            if (!folder.exists()) {
            	folder.mkdirs();
            }
            File file = new File(local);
            SVNProperties properties = new SVNProperties();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            repository.getFile(path, version, properties, out);
            out.close();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	private static List<Resource> getAllChildren(String path) throws Exception {
        Collection<SVNDirEntry> entries;
        entries = repository.getDir(path, -1, null, (Collection) null);
        List<Resource> result = new ArrayList<Resource>();
        for (SVNDirEntry entry : entries) {
            if (entry.getKind() == SVNNodeKind.FILE) {
                Resource resource = new Resource();
                resource.setName(entry.getName());
                resource.setPath(entry.getURL().getPath());
                resource.setFile(entry.getKind() == SVNNodeKind.FILE);
                result.add(resource);
            } else if (entry.getKind() == SVNNodeKind.DIR) {
                String path1 = entry.getURL().getPath();
                path1 = path1.substring(path1.indexOf(url),path1.length());
                result.addAll(getAllChildren(path1));
            }
        }
        return result;
    }
	
	private static class MyFileFilter implements FilenameFilter {
        private List<String> names;
		public MyFileFilter(List<String> names) {
			this.names = names;
		}

		@Override
		public boolean accept(File dir, String name) {
			for (String n: names) {
				if (name.equals(n+".class")){//类本身
					return true;
				}
				if((name.startsWith(n) && name.indexOf("$")!=-1)) {//匿名类、内部类
					return true;
				}
			}
			return false;
		}
    	
    }
	
}
