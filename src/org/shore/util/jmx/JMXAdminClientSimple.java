package org.shore.util.jmx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.ObjectName;

import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.AdminClientFactory;
import com.ibm.websphere.management.Session;
import com.ibm.websphere.management.application.AppManagement;
import com.ibm.websphere.management.application.AppManagementProxy;
import com.ibm.websphere.management.async.client.AsyncCommandClient;
import com.ibm.websphere.management.cmdframework.AdminCommand;
import com.ibm.websphere.management.cmdframework.CommandMgr;
import com.ibm.websphere.management.cmdframework.CommandResult;
import com.ibm.websphere.management.configservice.ConfigService;
import com.ibm.websphere.management.configservice.ConfigServiceHelper;
import com.ibm.websphere.management.configservice.ConfigServiceProxy;
import com.ibm.websphere.management.configservice.tasks.ServerClusterTasks;
import com.ibm.ws.scripting.adminCommand.AsyncCmdHandler;

public class JMXAdminClientSimple {

	// 单元
	public static String CELL = "Cell";

	// 节点
	public static String NODE = "Node";

	// 服务器
	public static String SERVER = "Server";

	// 应用服务器
	public static String APPLICATION_SERVER = "ApplicationServer";

	// 集群
	public static String SERVERCLUSTER = "ServerCluster";

	// 集群成员
	public static String CLUSTERMEMBER = "ClusterMember";

	// 名称属性
	public static String ATTRIB_NAME = "name";

	// 集群成员属性-节点名
	public static String ATTRIB_NODENAME = "nodeName";

	// 集群成员属性-成员名
	public static String ATTRIB_MEMBERNAME = "memberName";

	// ServerEntry
	public static String SERVER_ENTRY = "ServerEntry";

	// serverEntry属性-名称
	public static String SERVER_NAME = "serverName";

	// serverEntry属性-类型
	public static String SERVER_TYPE = "serverType";

	// 端口namedEndPoint
	public static String NAMED_END_POINT = "NamedEndPoint";

	// namedEndPoint属性-名称
	public static String ATTRIB_END_POINT_NAME = "endPointName";

	// 端口
	public static String END_POINT = "EndPoint";

	// 端口属性-端口
	public static String ATTRIB_PORT = "port";

	// 端口属性-主机
	public static String ATTRIB_HOST = "host";

	public static String VIRTUAL_HOST = "VirtualHost";

	public static String HOST_ALIAS = "HostAlias";

	// 获取属性时关键字
	public static String PROPERTY = "Property";

	// 创建属性时关键字
	public static String PROTERTIES = "properties";

	public static String JAVA_VIRTUAL_MACHINE = "JavaVirtualMachine";

	public static String INITIALHEAPSIZE = "initialHeapSize";

	public static String MAXIMUMHEAPSIZE = "maximumHeapSize";

	// 集群
	public static String SERVER_CLUSTER = "ServerCluster";

	public static String CLUSTER_MEMBER = "ClusterMember";

	// ds
	public static String DATA_SOURCE = "DataSource";

	// 部署
	public static final String DEPLOYMENT = "Deployment";
	public static final String WEBMODULE_DEPLOYMENT = "WebModuleDeployment";
	public static final String WEBMODULE_CLASSLOADER_MODE = "classloaderMode";
	public static final String PARENT_LAST_MODE = "PARENT_LAST";
	public static final String PARENT_FIRST_MODE = "PARENT_FIRST";
	public static final String ATTRIB_URI = "uri";

	public static String host = "localhost";

	public static String port = "8880";

	public static void main(String[] args) throws Exception {
		// redirect();
		// createCluster();
		// listCluster();
		// listNodes();
		// getNodeAgent();
		// getDs();
		// testListener();
		// urlRewriting();
		// listSSL();
		childFirst();

		// list();

	}

	private static void redirect() throws Exception {
		System.setOut(new PrintStream(new FileOutputStream(
				new File("c:/a.txt"), true)));
	}

	private static void list() throws Exception {
		Set<ObjectName> s = getMBean(QUERY_KEYSTORE);
		if (s != null && s.size() > 0) {
			Iterator<ObjectName> iter = s.iterator();
			while (iter.hasNext()) {
				ObjectName o = iter.next();
				System.out.println(o);
			}
		}
	}

	public static void childFirst() throws Exception {
		
//		Vector apps = getAppManagement().listApplications(null, null);
//		Vector apps = getAppManagement().getModuleInfo("nc63", null, "uapws.war", null);
		Object a = getAppManagement().listModules("nc63", null, null);
		System.out.println(a);
//		for (Object app : apps) {
//			System.out.println(app);
//		}
		
		String appName = "nc63";
		ObjectName app = null;
		app = getConfigServiceProxy().resolve(session,
				DEPLOYMENT + "=" + appName)[0];

		ObjectName[] wmds = findConfigMBeans(app, WEBMODULE_DEPLOYMENT);
		if (wmds != null && wmds.length > 0) {
			for (ObjectName wmd : wmds) {
				System.out.println(getAttribute(wmd, ATTRIB_URI));
				// AttributeList attrs = new AttributeList();
				// attrs.add(new Attribute(WEBMODULE_CLASSLOADER_MODE,
				// PARENT_LAST_MODE));
				// getConfigServiceProxy().setAttributes(getSession(), wmd,
				// attrs);
				// save();
			}
		}

		// ObjectName o = getMBean(query6).iterator().next();
		// System.out.println(o);

		// System.out.println(getAttribute(o, "maximumHeapSize"));

		// Set<ObjectName> s = getMBean(QUERY_KEYSTORE);
		// if (s != null && s.size() > 0) {
		// Iterator<ObjectName> iter = s.iterator();
		// while (iter.hasNext()) {
		// ObjectName o = iter.next();
		// if (o.toString().toLowerCase().indexOf("uapws") > -1) {
		// System.out.println(o);
		// try {
		// System.out.println(getAttribute(o, "classLoaderOrder"));
		// } catch (Exception e) {
		// System.err.println("error occured");
		// }
		// }
		// }
		// }
	}

	@SuppressWarnings("unchecked")
	public static void listSSL() throws Exception {
		// ObjectName cell = findConfigMBeans(null, CELL)[0];
		// ObjectName[] ons = findConfigMBeans(cell, "KeyStore");
		// String name = "NodeDefaultKeyStore";
		// ObjectName on = getMBeanByName(ons, name);
		// System.out.println(on);

		//
		// for (String p:on.getKeyPropertyList().keySet()) {
		// System.out.println(p);
		// }

		// OutputStream oos = new FileOutputStream(new File("c:/a.txt"));
		// PrintWriter pw = new PrintWriter(new OutputStreamWriter(oos));
		// Set<ObjectName> ss = getMBean(QUERY_KEYSTORE);
		// for (ObjectName s : ss) {
		// pw.println(s);
		// }
		// pw.close();

		// Set<ObjectName> ss = getMBean("WebSphere:*,type=SSLAdmin");
		// if(ss.iterator().hasNext()) {
		// ObjectName sss = ss.iterator().next();
		// System.out.println(sss);
		//
		// for (String p:sss.getKeyPropertyList().keySet()) {
		// System.out.println(p);
		// }
		//
		// }

		AdminClient adminClient = getAdminClient();
		CommandMgr cmdMgr = CommandMgr.getCommandMgr(adminClient);
		Session configSession = new Session();
		AsyncCmdHandler listener = new AsyncCmdHandler();
		AsyncCommandClient asyncCmdClientHelper = new AsyncCommandClient(
				configSession, listener);

		String cmdName = "listPersonalCertificates";
		AdminCommand cmd = cmdMgr.createCommand(cmdName);
		cmd.setConfigSession(configSession);
		cmd.setParameter("keyStoreName", "NodeDefaultKeyStore");
		// cmd.setParameter("keyStoreScope",
		// "(cell):activateNode01Cell:(node):activateNode01");
		asyncCmdClientHelper.execute(cmd);

		CommandResult result = cmd.getCommandResult();
		List<String> aliass = new ArrayList<String>();
		if (result != null) {
			if (result.isSuccessful()) {
				Object obj = result.getResult();
				List<List<Attribute>> cers = (List<List<Attribute>>) obj;
				System.out.println("*****list certificates******");
				for (List<Attribute> cer : cers) {
					System.out.println(cer);
					aliass.add(getAlias(cer));
				}
			} else {
			}
		}

		cmdName = "importCertificate";
		cmd = cmdMgr.createCommand(cmdName);
		cmd.setConfigSession(configSession);
		cmd.setParameter("keyStoreName", "NodeDefaultKeyStore");
		cmd.setParameter("keyFilePath", "D:/ssl/was/yonyouserver.jks");
		cmd.setParameter("keyFilePassword", "yonyouserver");
		cmd.setParameter("keyFileType", "JKS");
		cmd.setParameter("certificateAliasFromKeyFile", "yonyouserver");
		cmd.setParameter("certificateAlias", "lisi");
		asyncCmdClientHelper.execute(cmd);
		System.out.println("*****import new certificate******");

		for (String alias : aliass) {
			cmdName = "deleteCertificate";
			cmd = cmdMgr.createCommand(cmdName);
			cmd.setConfigSession(configSession);
			cmd.setParameter("keyStoreName", "NodeDefaultKeyStore");
			cmd.setParameter("certificateAlias", alias);
			asyncCmdClientHelper.execute(cmd);
		}
		System.out.println("*****delete old certificates******");

		ConfigService configService = new ConfigServiceProxy(adminClient);
		configService.save(configSession, false);

	}

	private static String getAlias(List<Attribute> cer) {
		for (Attribute a : cer) {
			if ("alias".equals(a.getName())) {
				return a.getValue().toString();
			}
		}
		return null;
	}

	public static void testListener() throws Exception {
		while (true) {
			Set<ObjectName> objs = getMBean("WebSphere:*,type=RasLoggingService,process=master");
			for (ObjectName obj : objs) {
				System.out.println("**** " + obj);
			}
			Thread.sleep(300);
		}

	}

	public static void getDs() throws Exception {
		//

		String driverPath = "${NC_HOME_UFIDA_NCHOME602}/driver/sqlserver_2008/sqljdbc4.jar";
		ObjectName ds = findConfigMBeans(null, DATA_SOURCE)[0];
		ObjectName provider = (ObjectName) getAttribute(ds, "provider");
		AttributeList attrs = new AttributeList();
		List<String> l = new ArrayList<String>();
		l.add("zhangsan");
		l.add("lisil");
		attrs.add(new Attribute("classpath", l));
		getConfigServiceProxy().setAttributes(getSession(), provider, attrs);
		save();
		// Object o = getAttribute(provider, "classpath");

		// System.out.println(o);
		// getConfigServiceProxy().addElement(getSession(), provider,
		// "classpath",
		// "test driver path", -1);
	}

	public static void getNodeAgent() throws Exception {
		String query = "WebSphere:type=NodeAgent,*";
		Set<ObjectName> appMans = getMBean(query);
		Iterator<ObjectName> iter = appMans.iterator();
		while (iter.hasNext()) {
			ObjectName obj = iter.next();
			Object name = getAttribute(obj, "name");
			System.out.println(name);
		}

	}

	public static ObjectName getAppManObjName() throws Exception {
		String processName = "master";
		Set<ObjectName> appMans = getMBean("WebSphere:*,type=AppManagement");
		ObjectName appManObjName = appMans.iterator().next();
		return appManObjName;
	}

	public static void createCluster() throws Exception {

		AttributeList clusterAttrs = new AttributeList();
		clusterAttrs.add(new Attribute("name", "ncCluster"));
		clusterAttrs.add(new Attribute("preferLocal", true));
		clusterAttrs.add(new Attribute("description",
				"UFida NC created cluster."));
		clusterAttrs.add(new Attribute("serverType", "APPLICATION_SERVER"));
		clusterAttrs.add(new Attribute("nodeGroupName", "APPLICATION_SERVER"));

		ObjectName parentCell = findConfigMBeans(null, CELL)[0];

		ObjectName cluster = isExistent(
				findConfigMBeans(parentCell, SERVER_CLUSTER), "name",
				"ncCluster");
		if (cluster != null) {
			getConfigServiceProxy().deleteConfigData(getSession(), cluster);
		}
		cluster = getConfigServiceProxy().createConfigData(getSession(),
				parentCell, SERVER_CLUSTER, SERVER_CLUSTER, clusterAttrs);

		ObjectName[] nodes = findConfigMBeans(parentCell, NODE);
		for (ObjectName node : nodes) {
			String name = (String) getAttribute(node, ATTRIB_NAME);

			if ("zhaogbccNode01".equals(name)) {
				createClusterMember(parentCell, cluster, node, name, "master",
						10, 9996);
			} else if ("testnodename".equals(name)) {

			}
		}
		save();
		System.out.println("finished!");

	}

	private static ObjectName createClusterMember(ObjectName parentCell,
			ObjectName parentCluster, ObjectName node, String nodeName,
			String memberName, Integer weight, Integer httpPort)
			throws Exception {
		ObjectName clusterMember = null;
		AttributeList clusterMemAttrs = new AttributeList();
		clusterMemAttrs.add(new Attribute("memberName", memberName));
		clusterMemAttrs.add(new Attribute("nodeName", nodeName));
		clusterMemAttrs.add(new Attribute("weight", weight));
		clusterMemAttrs.add(new Attribute("cluster", parentCluster));

		clusterMember = isExistent(
				findConfigMBeans(parentCluster, CLUSTER_MEMBER), "memberName",
				memberName);
		try {
			if (clusterMember == null) {
				ServerClusterTasks sct = new ServerClusterTasks(
						getConfigServiceProxy());
				clusterMember = sct.createClusterMember(getSession(),
						parentCluster, clusterMemAttrs, node, null);
			} else {
				getConfigServiceProxy().setAttributes(getSession(),
						clusterMember, clusterMemAttrs);
			}
			ObjectName serverEntry = getServerEntryByName(memberName);
			modifyServerPort(serverEntry, httpPort);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clusterMember;
	}

	private static void modifyServerPort(ObjectName serverEntry,
			Integer httpPort) throws Exception {

		String oldPort = getHttpPort(serverEntry);
		if (oldPort.equals(httpPort.toString())) {
			return;
		}
		ObjectName[] namedEndPoint = findConfigMBeans(serverEntry,
				NAMED_END_POINT);
		ObjectName wcDefaultHost = isExistent(namedEndPoint,
				ATTRIB_END_POINT_NAME, "WC_defaulthost");
		ObjectName endPoint = findConfigMBeans(wcDefaultHost, END_POINT)[0];
		AttributeList attList = new AttributeList();
		Attribute attr = new Attribute("port", httpPort);
		attList.add(attr);
		getConfigServiceProxy().setAttributes(getSession(), endPoint, attList);
		modifyVirtualHost(oldPort, String.valueOf(httpPort));
	}

	public static void addIOTimeOut() throws Exception {
		ObjectName cell = findConfigMBeans(null, CELL)[0];
		ObjectName node = findConfigMBeans(cell, NODE)[0];
		ObjectName server = findConfigMBeans(node, SERVER)[0];
		ObjectName appServer = findConfigMBeans(server, APPLICATION_SERVER)[0];
		System.out.println(getName(appServer));
		// findConfigMBeans(null, SERVER_ENTRY)
		// ObjectName[] chains = findConfigMBeans(server, "Chain");
		// ObjectName wcInboundDefault = getMBeanByName(chains,
		// "WCInboundDefault");
		// System.out.println(wcInboundDefault);
		ObjectName[] objs = findConfigMBeans(server, "HTTPInboundChannel");
		ObjectName httpInboundChannel = getMBeanByName(objs, "HTTP_2");
		if (httpInboundChannel == null) {
			return;
		}
		System.out.println(httpInboundChannel);
		// ObjectName[] properties = findConfigMBeans(httpInboundChannel,
		// PROPERTY);
		// if (properties != null && properties.length > 0) {
		// for (ObjectName property : properties) {
		// if ("CookiesConfigureNoCache".equals(getName(property))) {
		// System.out
		// .println("CookiesConfigureNoCache already exist!");
		// return;
		// }
		// }
		// }
		// AttributeList attr = new AttributeList();
		// attr.add(new Attribute("name", "CookiesConfigureNoCache"));
		// attr.add(new Attribute("value", "false"));
		// System.out.println("Adding property...");
		// getConfigServiceProxy().createConfigData(getSession(),
		// httpInboundChannel, "properties", "", attr);
		// save();
		// System.out.println("Add CookiesConfigureNoCache succeeded.");

	}

	public static void mofifyDmgrJvm() throws Exception {
		ObjectName cell = findConfigMBeans(null, CELL)[0];
		ObjectName[] nodes = findConfigMBeans(cell, NODE);
		for (ObjectName node : nodes) {
			ObjectName server = findConfigMBeans(node, SERVER)[0];
			if ("dmgr".equals(getName(server))) {
				ObjectName jvm = findConfigMBeans(server, JAVA_VIRTUAL_MACHINE)[0];
				Object maximumHeapSize = getAttribute(jvm, MAXIMUMHEAPSIZE);
				if ("512".equals(maximumHeapSize.toString())) {
					System.out.println("dmgr maximumHeapSize is 512");
					return;
				}
				AttributeList attrib = new AttributeList();
				attrib.add(new Attribute(INITIALHEAPSIZE, 128));
				attrib.add(new Attribute(MAXIMUMHEAPSIZE, 512));
				System.out.println("modify jvm args...");
				getConfigServiceProxy()
						.setAttributes(getSession(), jvm, attrib);
				save();
				break;
			}

		}
	}

	private static void listJvmArgs(ObjectName server) throws Exception {
		ObjectName[] jvms = findConfigMBeans(server, JAVA_VIRTUAL_MACHINE);
		if (null != jvms && 0 < jvms.length) {
			ObjectName jvm = jvms[0];
			Object initialHeapSize = getAttribute(jvm, INITIALHEAPSIZE);
			Object maximumHeapSize = getAttribute(jvm, MAXIMUMHEAPSIZE);
			System.out.println("initialHeapSize=" + initialHeapSize);
			System.out.println("maximumHeapSize=" + maximumHeapSize);
		}
	}

	public static void addCookiesConfigureNoCache() throws Exception {
		ObjectName cell = findConfigMBeans(null, CELL)[0];
		ObjectName node = findConfigMBeans(cell, NODE)[0];
		ObjectName server = findConfigMBeans(node, SERVER)[0];
		ObjectName appServer = findConfigMBeans(server, APPLICATION_SERVER)[0];
		System.out.println(getName(appServer));
		// findConfigMBeans(null, SERVER_ENTRY)
		// ObjectName[] chains = findConfigMBeans(server, "Chain");
		// ObjectName wcInboundDefault = getMBeanByName(chains,
		// "WCInboundDefault");
		// System.out.println(wcInboundDefault);
		ObjectName[] objs = findConfigMBeans(server, "HTTPInboundChannel");
		ObjectName httpInboundChannel = getMBeanByName(objs, "HTTP_2");
		if (httpInboundChannel == null) {
			return;
		}
		System.out.println(httpInboundChannel);
		// ObjectName[] properties = findConfigMBeans(httpInboundChannel,
		// PROPERTY);
		// if (properties != null && properties.length > 0) {
		// for (ObjectName property : properties) {
		// if ("CookiesConfigureNoCache".equals(getName(property))) {
		// System.out
		// .println("CookiesConfigureNoCache already exist!");
		// return;
		// }
		// }
		// }
		// AttributeList attr = new AttributeList();
		// attr.add(new Attribute("name", "CookiesConfigureNoCache"));
		// attr.add(new Attribute("value", "false"));
		// System.out.println("Adding property...");
		// getConfigServiceProxy().createConfigData(getSession(),
		// httpInboundChannel, "properties", "", attr);
		// save();
		// System.out.println("Add CookiesConfigureNoCache succeeded.");

	}

	public static ObjectName getMBeanByName(ObjectName[] objs, String name)
			throws Exception {
		if (objs != null && objs.length > 0) {
			for (ObjectName obj : objs) {
				if (getName(obj).equals(name)) {
					return obj;
				}
			}
		}
		return null;
	}

	public static String getVersion() throws Exception {
		ObjectName management = getMBean(query1).iterator().next();
		String version = management.getKeyProperty("version");
		return version;
	}

	public static void listApplicationServer() throws Exception {
		ObjectName cell = findConfigMBeans(null, CELL)[0];
		ObjectName[] nodes = findConfigMBeans(cell, NODE);
		for (ObjectName node : nodes) {
			System.out.println("nodeName=" + getAttribute(node, "name"));
			ObjectName[] appServers = findConfigMBeans(node, APPLICATION_SERVER);
			for (ObjectName appServer : appServers) {
				System.out.println(appServer);
				listJvmArgs(appServer);
			}
		}
	}

	public static void urlRewriting() throws Exception {
		ObjectName cell = findConfigMBeans(null, CELL)[0];
		ObjectName[] nodes = findConfigMBeans(cell, NODE);
		for (ObjectName node : nodes) {
			ObjectName[] appServers = findConfigMBeans(node, APPLICATION_SERVER);
			for (ObjectName appServer : appServers) {
				ObjectName[] ons = findConfigMBeans(appServer, "SessionManager");
				ObjectName sessionManager = ons[0];
				Object enableUrlRewriting = getAttribute(sessionManager,
						"enableUrlRewriting");
				System.out.println("enableUrlRewriting=" + enableUrlRewriting);
				AttributeList attrib = new AttributeList();
				attrib.add(new Attribute("enableUrlRewriting", true));
				attrib.add(new Attribute("enableProtocolSwitchRewriting", true));
				getConfigServiceProxy().setAttributes(getSession(),
						sessionManager, attrib);
				save();
				enableUrlRewriting = getAttribute(sessionManager,
						"enableUrlRewriting");
				System.out.println("enableUrlRewriting=" + enableUrlRewriting);
			}
		}
	}

	public static void listServerEntry() throws Exception {
		ObjectName[] serverEntries = findConfigMBeans(null, SERVER_ENTRY);
		for (ObjectName serverEntry : serverEntries) {
			System.out.println("@@@serverEntry="
					+ getAttribute(serverEntry, SERVER_NAME) + ", serverType="
					+ getAttribute(serverEntry, SERVER_TYPE));
			ObjectName[] namedEndPoints = findConfigMBeans(serverEntry,
					NAMED_END_POINT);
			for (ObjectName namedEndPoint : namedEndPoints) {
				String endPointName = (String) getAttribute(namedEndPoint,
						ATTRIB_END_POINT_NAME);
				ObjectName endPoint = findConfigMBeans(namedEndPoint, END_POINT)[0];
				String port = getAttribute(endPoint, "port").toString();
				System.out.println("endPointName=" + endPointName + ", port="
						+ port);
			}
		}
	}

	public static void listNodes() throws Exception {
		ObjectName cell = findConfigMBeans(null, CELL)[0];
		ObjectName[] nodes = findConfigMBeans(cell, NODE);
		for (ObjectName node : nodes) {
			System.out.println("node name=" + getAttribute(node, ATTRIB_NAME));
			System.out.println("node hostname="
					+ getAttribute(node, "hostName"));

			// for (String key : node.getKeyPropertyList().keySet()) {
			// System.out.println(key + "=" + node.getKeyProperty(key));
			// }
			// ObjectName[] servers = findConfigMBeans(node, SERVER);
			// if (servers != null) {
			// for (ObjectName on : servers) {
			// System.out.println(getAttribute(on, ATTRIB_NAME));
			// for (String key : on.getKeyPropertyList().keySet()) {
			// System.out.println("key=" + key);
			// System.out.println("value=" + on.getKeyProperty(key));
			// }
			// listJvmArgs(on);
			//
			// }
			// }
		}
	}

	public static void listCluster() throws Exception {
		ObjectName cell = findConfigMBeans(null, CELL)[0];
		ObjectName[] serverCluster = findConfigMBeans(cell, SERVERCLUSTER);
		for (ObjectName cluster : serverCluster) {
			System.out.println("clusterName=" + getAttribute(cluster, "name"));
			ObjectName[] serverMembers = findConfigMBeans(cluster,
					CLUSTERMEMBER);
			if (serverMembers != null) {
				for (ObjectName on : serverMembers) {
					String name = (String) getAttribute(on, ATTRIB_MEMBERNAME);
					String node = (String) getAttribute(on, ATTRIB_NODENAME);
					ObjectName serverEntry = getServerEntryByName(name);
					String httpPort = getHttpPort(serverEntry);
					String httpsPort = getHttpsPort(serverEntry);
					System.out.println("nodeName=" + node + ", name=" + name
							+ ", httPort=" + httpPort + ", httpsPort="
							+ httpsPort);
				}
			}
		}
	}

	public static ObjectName getServerEntryByName(String name) throws Exception {
		ObjectName[] serverEntries = findConfigMBeans(null, SERVER_ENTRY);
		return isExistent(serverEntries, new String[] { SERVER_NAME,
				SERVER_TYPE }, new String[] { name, "APPLICATION_SERVER" });
	}

	public static String getHttpPort(ObjectName serverEntry) throws Exception {
		ObjectName[] namedEndPoint = findConfigMBeans(serverEntry,
				NAMED_END_POINT);
		ObjectName wcDefaultHost = isExistent(namedEndPoint,
				ATTRIB_END_POINT_NAME, "WC_defaulthost");
		ObjectName endPoint = findConfigMBeans(wcDefaultHost, END_POINT)[0];
		return getAttribute(endPoint, "port").toString();
	}

	public static void modifyHttpPort() throws Exception {
		ObjectName cell = findConfigMBeans(null, CELL)[0];
		ObjectName[] serverCluster = findConfigMBeans(cell, SERVERCLUSTER);
		for (ObjectName cluster : serverCluster) {
			System.out.println("clusterName=" + getAttribute(cluster, "name"));
			ObjectName[] serverMembers = findConfigMBeans(cluster,
					CLUSTERMEMBER);
			if (serverMembers != null) {

				for (ObjectName on : serverMembers) {
					String name = (String) getAttribute(on, ATTRIB_MEMBERNAME);
					String node = (String) getAttribute(on, ATTRIB_NODENAME);
					ObjectName serverEntry = getServerEntryByName(name);
					String httpPort = getHttpPort(serverEntry);
					String httpsPort = getHttpsPort(serverEntry);
					System.out.println("nodeName=" + node + ", name=" + name
							+ ", httPort=" + httpPort + ", httpsPort="
							+ httpsPort);
				}

				for (ObjectName on : serverMembers) {
					String name = (String) getAttribute(on, ATTRIB_MEMBERNAME);
					ObjectName serverEntry = getServerEntryByName(name);
					String httpPort = getHttpPort(serverEntry);
					ObjectName[] namedEndPoint = findConfigMBeans(serverEntry,
							NAMED_END_POINT);
					ObjectName wcDefaultHost = isExistent(namedEndPoint,
							ATTRIB_END_POINT_NAME, "WC_defaulthost");
					ObjectName endPoint = findConfigMBeans(wcDefaultHost,
							END_POINT)[0];
					AttributeList attList = new AttributeList();
					Attribute attr = new Attribute("port",
							Integer.parseInt(httpPort) + 4);
					attList.add(attr);
					getConfigServiceProxy().setAttributes(getSession(),
							endPoint, attList);
					modifyVirtualHost(
							httpPort == null ? null : httpPort.toString(),
							String.valueOf(Integer.parseInt(httpPort) + 4));
				}

				for (ObjectName on : serverMembers) {
					String name = (String) getAttribute(on, ATTRIB_MEMBERNAME);
					String node = (String) getAttribute(on, ATTRIB_NODENAME);
					ObjectName serverEntry = getServerEntryByName(name);
					String httpPort = getHttpPort(serverEntry);
					String httpsPort = getHttpsPort(serverEntry);
					System.out.println("nodeName=" + node + ", name=" + name
							+ ", httPort=" + httpPort + ", httpsPort="
							+ httpsPort);
				}
			}
		}
		save();
	}

	public static void save() throws Exception {
		System.out.println("Saving session ...");
		getConfigServiceProxy().save(getSession(), false);
		getConfigServiceProxy().discard(getSession());
	}

	public static void modifyVirtualHost(String port, String toPort)
			throws Exception {
		if (port == toPort || toPort.equals(port)) {
			return;
		}
		ObjectName[] virtualHosts = findConfigMBeans(null, VIRTUAL_HOST);
		ObjectName virtualHost = isExistent(virtualHosts, "name",
				"default_host");
		boolean needCreate = true;
		if (port != null) {
			ArrayList obj = (ArrayList) getAttribute(virtualHost, "aliases");
			for (Iterator iter = obj.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				if (element instanceof AttributeList) {
					AttributeList attrList = (AttributeList) element;
					if (attrList.size() == 4) {
						Attribute attr = (Attribute) attrList.get(1);
						if (toPort.equals(attr.getValue())) {
							return;
						}
						if (port.equals(attr.getValue())) {
							attr = new Attribute(attr.getName(), toPort);
							attrList.set(1, attr);
							needCreate = false;
						}
					}
				}
			}
			if (!needCreate) {
				AttributeList attList = new AttributeList();
				Attribute attr = new Attribute("aliases", obj);
				attList.add(attr);
				getConfigServiceProxy().setAttributes(getSession(),
						virtualHost, attList);
			}
		}
		if (needCreate) {
			AttributeList aliasAttrs = new AttributeList();
			aliasAttrs.add(new Attribute("hostname", "*"));
			aliasAttrs.add(new Attribute("port", toPort));
			getConfigServiceProxy().createConfigData(getSession(), virtualHost,
					"aliases", HOST_ALIAS, aliasAttrs);
		}

	}

	public static String getHttpsPort(ObjectName serverEntry) throws Exception {
		ObjectName[] namedEndPoint = findConfigMBeans(serverEntry,
				NAMED_END_POINT);
		ObjectName wcDefaultHost = isExistent(namedEndPoint,
				ATTRIB_END_POINT_NAME, "WC_defaulthost_secure");
		ObjectName endPoint = findConfigMBeans(wcDefaultHost, END_POINT)[0];
		return getAttribute(endPoint, "port").toString();
	}

	public static ObjectName[] findConfigMBeans(ObjectName scope, String type)
			throws Exception {
		ObjectName pattern = ConfigServiceHelper.createObjectName(null, type);
		ObjectName[] matches = new ObjectName[0];
		matches = getConfigServiceProxy().queryConfigObjects(getSession(),
				scope, pattern, null);
		return matches;
	}

	public static ObjectName isExistent(ObjectName[] objNames,
			String[] attrNames, String[] attrValues) throws Exception {
		if (objNames == null || objNames.length == 0)
			return null;
		Object obj = null;
		int j = 0;
		for (int i = 0; i < objNames.length; i++) {
			for (j = 0; j < attrNames.length; j++) {
				obj = getConfigServiceProxy().getAttribute(getSession(),
						objNames[i], attrNames[j]);
				if (!attrValues[j].equals(obj.toString())) {
					break;
				}
			}
			if (j >= attrNames.length) {
				return objNames[i];
			}
		}
		return null;
	}

	public static ObjectName isExistent(ObjectName[] objNames, String attrName,
			String attrValue) throws Exception {
		if (objNames == null || objNames.length == 0)
			return null;
		Object obj = null;
		for (int i = 0; i < objNames.length; i++) {
			obj = getConfigServiceProxy().getAttribute(getSession(),
					objNames[i], attrName);
			if (obj != null && attrValue.equals(obj.toString())) {
				return objNames[i];
			}
		}

		return null;
	}

	public static ConfigServiceProxy getConfigServiceProxy() throws Exception {
		if (confServProxy == null) {
			confServProxy = new ConfigServiceProxy(getAdminClient());
		}
		return confServProxy;
	}

	public static Session getSession() {
		if (session == null) {
			session = new Session();
		}
		return session;
	}

	public static AppManagement getAppManagement() throws Exception {
		if (appManagement == null) {
			appManagement = AppManagementProxy
					.getJMXProxyForClient(getAdminClient());
		}
		return appManagement;
	}

	@SuppressWarnings("unchecked")
	public static Set<ObjectName> getMBean(String query) throws Exception {
		ObjectName queryName = new ObjectName(query);
		Set<ObjectName> s = getAdminClient().queryNames(queryName, null);
		if (!s.isEmpty()) {
			Iterator it = s.iterator();
			while (it.hasNext()) {
				ObjectName on = (ObjectName) it.next();
				// System.out.println("\t" + on);
				// System.out.println("\t" + on.getKeyProperty("name"));
			}
		}
		return s;
	}

	public static Object getAttribute(ObjectName objName, String attributeName)
			throws Exception {
		return getConfigServiceProxy().getAttribute(getSession(), objName,
				attributeName);
	}

	public static String getName(ObjectName objName) throws Exception {
		return (String) getAttribute(objName, "name");
	}

	public static AdminClient getAdminClient() throws Exception {
		if (adminClient == null) {
			Properties adminProps = new Properties();
			adminProps.setProperty("type", AdminClient.CONNECTOR_TYPE_SOAP);
			adminProps.setProperty("host", host);
			adminProps.setProperty("port", port);
			adminClient = AdminClientFactory.createAdminClient(adminProps);
		}
		return adminClient;
	}

	private static AdminClient adminClient;

	private static ConfigServiceProxy confServProxy = null;

	private static AppManagement appManagement = null;

	private static Session session = null;

	static String query1 = "WebSphere:type=AppManagement,*";

	static String query2 = "WebSphere:type=NodeAgent,*";

	static String query3 = "WebSphere:type=Server,*";

	static String query4 = "WebSphere:type=SessionManager,*";

	static String query5 = "WebSphere:type=J2EEApplication,*";

	static String query6 = "WebSphere:type=WebModule,*";

	static String QUERY_KEYSTORE = "WebSphere:*";
	static String QUERY_KEYSTORE1 = "Websphere:type=WebModuleDeployment,*";
}
