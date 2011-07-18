importPackage( Packages.com.openedit.util );
importPackage( Packages.java.util );
importPackage( Packages.java.lang );
importPackage( Packages.com.openedit.modules.update );

var war = "http://dev.entermediasoftware.com/jenkins/job/extension-rhozet/lastSuccessfulBuild/artifact/deploy/extension-rhozet.zip";

var root = moduleManager.getBean("root").getAbsolutePath();
var web = root + "/WEB-INF";
var tmp = web + "/tmp";

log.add("1. GET THE LATEST WAR FILE");
var downloader = new Downloader();
downloader.download( war, tmp + "/entermedia-rhozet.zip");

log.add("2. UNZIP WAR FILE");
var unziper = new ZipUtil();
unziper.unzip(  tmp + "/entermedia-rhozet.zip",  tmp );

log.add("3. REPLACE LIBS");
var files = new FileUtils();
files.deleteMatch( web + "/lib/entermedia-rhozet*.jar");

files.copyFileByMatch( tmp + "/lib/entermedia-rhozet*.jar", web + "/lib/");

//files.deleteMatch( web + "/entermedia/catalog/components/rhozet/")
files.copyFiles( tmp + "/base/entermedia/catalog/events/conversions/rhozet/", web + "/base/entermedia/catalog/events/scripts/conversions/rhozet/");

log.add("5. CLEAN UP");
files.deleteAll(tmp);

log.add("6. UPGRADE COMPLETED");