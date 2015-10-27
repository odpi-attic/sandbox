//*************************************************************
// https://github.com/odpi/specs/blob/master/ODPi-Runtime.md
//
// This is a simple example of validating the ODP spec on a 
// running hadoop system. The only requirement is to have 
// groovy installed and a functional hadoop+yarn command.
//
//*************************************************************


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * Java: ODPi Platforms SHOULD support both JRE 7 and JRE 8 runtime environments (64-bit only). 
 * ODPi Applications SHOULD work in at least one of these, and SHOULD be clear when they don’t 
 * support both.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////

def cmd = "java -d64 -version"
def res = cmd.execute()
res.waitForOrKill(60)
if ( res.exitValue() == 0 ) {
    println "JRE is 64bit compatible"
} else {
    println "ERROR: JRE is not 64bit compatible"
}


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * Shell scripts: ODPi Platforms and Applications SHOULD use either POSIX sh or GNU bash with the 
 * appropriate bang path configured for that operating system. GNU bash usage SHOULD NOT require 
 * any version of GNU bash later than 3.2.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////

/* This is not a cross platform compliant specification. */

// [GNU, bash,, version, 4.2.46(1)-release, (x86_64-redhat-linux-gnu)]
cmd = "bash -version"
def so = new StringBuffer()
def se = new StringBuffer()
res = cmd.execute()
res.consumeProcessOutput(so, se)
res.waitForOrKill(60)
if ( res.exitValue() == 0 ) {
    def line = so.toString().split("\n")[0]
    line = line.replaceAll("\\s\\s", "\\s")
    def lparts = line.tokenize()
    bash_version = lparts[3]
    def vtokens = bash_version.tokenize("\\.")
    println "BASH MAJOR VERSION: " + vtokens[0]
    println "BASH MINOR VERSION: " + vtokens[1]
} else {
    println "ERROR: bash -version failed"
}


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * In order to fulfil the goals of this specification, the discovery and content of several key 
 * environment variables are covered. This enables applications the capability to locate where the 
 * various Apache Hadoop components are located (user-level binaries and Java JAR files) in an ODPi 
 * Platform consistent way.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////

def evars = ['JAVA_HOME', 
             'HADOOP_COMMON_HOME', 
             'HADOOP_COMMON_DIR', 
             'HADOOP_COMMON_LIB_JARS_DIR',
             'HADOOP_CONF_DIR',
             'HADOOP_HDFS_HOME',
             'HDFS_DIR',
             'HDFS_LIB_JARS_DIR',
             'HADOOP_YARN_HOME',
             'YARN_DIR',
             'YARN_LIB_JARS_DIR',
             'HADOOP_MAPRED_HOME',
             'MAPRED_DIR',
             'MAPRED_LIB_JARS_DIR',
             'HADOOP_TOOLS_PATH' ]

evars.each{ x ->
    def setvalue = System.getenv(x)
    if ( setvalue == null ) {
        println "ERROR: " + x + " is unset"
    } else {
        println "" + x ": " + setvalue
    }
}


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * All previously named environment variables mentioned in this section MUST be either explicitly 
 * set or readable via running the appropriate bin command with the envvars parameter. In the 
 * situation where these variables are not explicitly set, the appropriate commands MUST be 
 * available on the path.
 *  https://issues.apache.org/jira/browse/HADOOP-11010
 *  https://issues.apache.org/jira/browse/HADOOP-9902
 *  https://issues.apache.org/jira/browse/HADOOP-12366
 *  https://issues.apache.org/jira/browse/HADOOP-10787
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////

cmd = "hadoop envvars"
so = new StringBuffer()
se = new StringBuffer()
res = cmd.execute()
res.consumeProcessOutput(so, se)
res.waitForOrKill(60)
if ( res.exitValue() == 0 ) {
    // TBD
} else {
    println "ERROR: hadoop envvars failed"
}



///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * An ODPi Platform MUST either explicitly set JAVA_HOME or configure it in hadoop-env.sh and 
 * yarn-env.sh. In a future specification, yarn-env.sh will be removed.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * An ODPi Platform MUST set the HADOOP_CONF_DIR environment variable to point to Apache Hadoop’s 
 * configuration directory if config files aren’t being stored in *_HOME/etc/hadoop.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * The location of the tools jars and other miscellaneous jars SHOULD be set to the 
 * HADOOP_TOOLS_PATH environment variable. This is used as input for setting Java class paths, 
 * therefore this MUST be an absolute path. It MAY contain additional content above and beyond what 
 * ships with Apache Hadoop and the reference implementation. The entire directory SHOULD NOT be 
 * included in the default hadoop class path. Individual jars MAY be specified.
 *  https://issues.apache.org/jira/browse/HADOOP-10787
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * ODPi Platforms MUST have all of the base Apache Hadoop components installed.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * ODPi Platforms MUST pass the Apache Big Top 1.0.0 Hadoop smoke tests.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * ODPi Platforms MUST NOT change public APIs, where an API is defined as either a Java API 
 * (aka "Apache Hadoop ABI") or a REST API. See the Apache Hadoop Compatibility guidelines for 
 * more information.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * ODPi Platforms MUST modify the version string output by Hadoop components, such as those 
 * displayed in log files, or returned via public API's such that they contain -(vendor string) 
 * where (vendor string) matches the regular expression [A-Za-z_0-9]+ and appropriately identifies 
 * the ODPi Platform vendor in the output.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * An ODPi Platform MUST keep the same basic directory layout with regards to directory and 
 * filenames as the equivalent Apache component. Changes to that directory layout MUST be enabled 
 * by the component itself with the appropriate configurations for that layout configured. For 
 * example, if Apache Hadoop YARN's package distribution contains a libexec directory with content, 
 * then that libexec directory with the equivalent content must be preset. 
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * It MUST be possible to determine key Hadoop configuration values by using  
 * ${HADOOP_HDFS_HOME}/bin/hdfs getconf so that directly reading the XML via Hadoop’s Configuration 
 * object SHOULD NOT be required.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * The native compression codecs for gzip and snappy MUST be available and enabled by default.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * A common application-architecture ... They interact with Hadoop using client-libraries and 
 * cluster-config files installed locally on the client host. These apps tend to have a lot of 
 * requirements in terms of the packages installed locally. A good ODPi Platform implementation 
 * SHOULD NOT get in the way: at most, they SHOULD care about the version of Java and and Bash and 
 * nothing else.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * ODPi Platforms MUST define the APPS log4j appender to allow for ISV and user applications a 
 * common definition to log output. The actual definition, location of output, cycling requirements, 
 * etc of this appender is not defined by this specification and is ODPi Platform or user- defined
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * ODPi Platforms MUST define the APPS log4j appender to allow for ISV and user applications a 
 * common definition to log output. The actual definition, location of output, cycling requirements, 
 * etc of this appender is not defined by this specification and is ODPi Platform or user- defined
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * ODPi Platforms SHOULD publish all modified (i.e., not-default) Apache Hadoop configuration 
 * entries, regardless of client, server, etc applicability to all nodes unless it is known to be 
 * node hardware specific, private to a service, security-sensitive, or otherwise problematic.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////


