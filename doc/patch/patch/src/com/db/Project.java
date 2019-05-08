package com.db;

public class Project {
    private String projectName;
    private String webContent;
    private String javaContent;
    private String javaParent;
    private String javaNoCompiler;
    private String classPath;
    private String svn;
    private String branchUrl;
    private String isMaven;
    private String targetVer;
    private String sourceVer;
    
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getWebContent() {
		return webContent;
	}
	public void setWebContent(String webContent) {
		this.webContent = webContent;
	}
	public String getJavaContent() {
		return javaContent;
	}
	public void setJavaContent(String javaContent) {
		this.javaContent = javaContent;
	}
	public String getJavaParent() {
		return javaParent;
	}
	public void setJavaParent(String javaParent) {
		this.javaParent = javaParent;
	}
	public String getClassPath() {
		return classPath;
	}
	public String getJavaNoCompiler() {
		return javaNoCompiler;
	}
	public void setJavaNoCompiler(String javaNoCompiler) {
		this.javaNoCompiler = javaNoCompiler;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public String getSvn() {
		return svn;
	}
	public void setSvn(String svn) {
		this.svn = svn;
	}
	public String getBranchUrl() {
		return branchUrl;
	}
	public void setBranchUrl(String branchUrl) {
		this.branchUrl = branchUrl;
	}
	public String getIsMaven() {
		return isMaven;
	}
	public void setIsMaven(String isMaven) {
		this.isMaven = isMaven;
	}
	public String getTargetVer() {
		return targetVer;
	}
	public void setTargetVer(String targetVer) {
		this.targetVer = targetVer;
	}
	public String getSourceVer() {
		return sourceVer;
	}
	public void setSourceVer(String sourceVer) {
		this.sourceVer = sourceVer;
	}
	
	@Override
	public String toString() {
		return "Project [projectName=" + projectName + ", webContent=" + webContent + ", javaContent=" + javaContent
				+ ", javaParent=" + javaParent + ", javaNoCompiler=" + javaNoCompiler + ", classPath=" + classPath
				+ ", svn=" + svn + ", branchUrl=" + branchUrl + ", isMaven=" + isMaven + ", targetVer=" + targetVer
				+ ", sourceVer=" + sourceVer + "]";
	}
    
}
