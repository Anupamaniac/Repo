package com.rosettastone.succor;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import org.drools.guvnor.client.rpc.PackageConfigData;
import org.drools.guvnor.client.rpc.RepositoryService;
import org.drools.guvnor.client.rpc.SecurityService;

import com.gdevelop.gwt.syncrpc.SyncProxy;

/**
 * This class invokes package builder after upload.
 * 
 * @author Igor Yarkov
 * 
 */
public final class PackageBuilderInvoker {

    private String login;
    private String password;
    private String gwtRpcUrl;

    private PackageBuilderInvoker() {
    }

    public static void main(String[] args) throws Exception {
        PackageBuilderInvoker builder = new PackageBuilderInvoker();
        builder.loadProperties();
        builder.initAuthenticator();
        builder.login();
        builder.buildPackage();
    }

    private void loadProperties() throws Exception {
        final Properties properties = new Properties();
        properties.load(PackageBuilderInvoker.class.getResourceAsStream("/application.properties"));
        login = properties.getProperty("gunvor.userName");
        password = properties.getProperty("gunvor.password");
        gwtRpcUrl = properties.getProperty("gunvor.server.urlBase") + "/org.drools.guvnor.Guvnor";
    }

    private void initAuthenticator() throws Exception {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password.toCharArray());
            }
        });
    }

    private void login() {
        SecurityService ss = (SecurityService) SyncProxy.newProxyInstance(SecurityService.class, gwtRpcUrl,
                "/securityService");
        ss.login(login, password);
    }

    private void buildPackage() throws Exception {
        RepositoryService service = (RepositoryService) SyncProxy.newProxyInstance(RepositoryService.class, gwtRpcUrl,
                "/guvnorService");
        PackageConfigData[] packages = service.listPackages();
        for (PackageConfigData packageConfigData : packages) {
            if ("com.rosettastone.succor".equals(packageConfigData.name)) {
                service.buildPackage(packageConfigData.uuid, true, "buildWholePackage", null, null, false, null, null,
                        false, null);
                System.out.println("Package rebuilded");
                return;
            }
        }
        System.err.println("Package not found");
        System.exit(1);
    }

}
