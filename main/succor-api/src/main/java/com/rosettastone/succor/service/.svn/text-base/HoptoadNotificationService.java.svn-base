package com.rosettastone.succor.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hoptoad.HoptoadNotice;
import hoptoad.HoptoadNoticeBuilder;
import hoptoad.HoptoadNotifier;

public class HoptoadNotificationService {
	 private static final Log LOG = LogFactory.getLog(HoptoadNotificationService.class);
	
	  private String hoptoadServerUrl; //= hoptoadDetails.getString("hoptoad.server.url");
	  private String hoptoadProxyHost;// = hoptoadDetails.getString("hoptoad.server.proxy.host");
	  private String hoptoadProxyPort;// = hoptoadDetails.getString("hoptoad.server.proxy.port");
	  private String projectRoot;// = hoptoadDetails.getString("hoptoad.project.root");
	  private String env;// = hoptoadDetails.getString("hoptoad.project.env");
	  private String url;// = hoptoadDetails.getString("hoptoad.project.url");
	  private String component;// = hoptoadDetails.getString("hoptoad.project.component");
	  private String apiKey;// = hoptoadDetails.getString("hoptoad.api.key");

	
	public void notifyingHopToad(Throwable error){
		LOG.debug("Inside notifyingHopToad  method===============>>>>>>>>>>>>");
		HoptoadNotice notice = new HoptoadNoticeBuilder(apiKey, error, env){{
			    setHoptoadServerUrl(hoptoadServerUrl);
			    setHoptoadServerProxy(hoptoadProxyHost, hoptoadProxyPort);
			    setRequest(url, component); //optional
			    projectRoot(projectRoot); //optional
			    filteredSystemProperties(); //optional
			}}.newNotice();
			
			int status = new HoptoadNotifier().notify(notice);
			LOG.debug("====>>>>enjoy"+status+">>>>>>>>>"+hoptoadServerUrl+">>>>>>>>>"+apiKey);
	}
	
	public void notifyingHopToad(String json, Throwable error){
		LOG.debug("Inside notifyingHopToad  method===============>>>>>>>>>>>>");
		Throwable errorWithJSON = null;
		if(json != null){
			errorWithJSON = new Throwable((error.getMessage()+"**JSON:"+json+"**"), error);
		}else{
			errorWithJSON = error;
		}
		HoptoadNotice notice = new HoptoadNoticeBuilder(apiKey, errorWithJSON, env){{
			    setHoptoadServerUrl(hoptoadServerUrl);
			    setHoptoadServerProxy(hoptoadProxyHost, hoptoadProxyPort);
			    setRequest(url, component); //optional
			    projectRoot(projectRoot); //optional
			    filteredSystemProperties(); //optional
			}}.newNotice();
			
			int status = new HoptoadNotifier().notify(notice);
			LOG.debug("====>>>>enjoy"+status+">>>>>>>>>"+hoptoadServerUrl+">>>>>>>>>"+apiKey);
	}
	
	  public String getHoptoadServerUrl() {
			return hoptoadServerUrl;
		}


		public void setHoptoadServerUrl(String hoptoadServerUrl) {
			this.hoptoadServerUrl = hoptoadServerUrl;
		}


		public String getHoptoadProxyHost() {
			return hoptoadProxyHost;
		}


		public void setHoptoadProxyHost(String hoptoadProxyHost) {
			this.hoptoadProxyHost = hoptoadProxyHost;
		}


		public String getHoptoadProxyPort() {
			return hoptoadProxyPort;
		}


		public void setHoptoadProxyPort(String hoptoadProxyPort) {
			this.hoptoadProxyPort = hoptoadProxyPort;
		}


		public String getProjectRoot() {
			return projectRoot;
		}


		public void setProjectRoot(String projectRoot) {
			this.projectRoot = projectRoot;
		}


		public String getEnv() {
			return env;
		}


		public void setEnv(String env) {
			this.env = env;
		}


		public String getUrl() {
			return url;
		}


		public void setUrl(String url) {
			this.url = url;
		}


		public String getComponent() {
			return component;
		}


		public void setComponent(String component) {
			this.component = component;
		}


		public String getApiKey() {
			return apiKey;
		}


		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}
}
