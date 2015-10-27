//*************************************************************
// https://github.com/odpi/specs/blob/master/ODPi-Runtime.md
//
// This is a simple example of validating the ODP spec on a 
// running hadoop system. The only requirement is to have 
// groovy installed and a functional hadoop+yarn command.
//
//*************************************************************

import java.io.File


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * For this version of the specification, ODPi Platforms MUST be a descendent of the 
 * Apache Hadoop 2.7 branch. Future versions MAY increase the base Apache Hadoop version.
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////

def cmd = "hadoop version"
def res = cmd.execute().text
def hadoop_version = res.split('\n')[0].split()[1]
def hadoop_version_parts = hadoop_version.split("\\.")
println "Hadoop Version: " + hadoop_version
println "hadoop Version: " + hadoop_version_parts
assert (hadoop_version_parts[0].toInteger() >= 2)
assert (hadoop_version_parts[1].toInteger() >= 7)

/*
The Apache components in an ODPi reference release MUST have their source be 100% 
committed to an Apache source tree.
*/

// this is untestable


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * hadoop-common-project MUST be built with:
 *   * -Pnative or -Pnative-win = build libhadoop.so, 
 *          which also enables ZLib|gzip compression codec
 *   * -Drequire.snappy = enables Snappy compression
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////

// https://svn.apache.org/repos/asf/hadoop/common/tags/release-2.1.0-beta-rc0/BUILDING.txt
//  All Maven goals are the same as described above with the exception that
//  native code is built by enabling the 'native-win' Maven profile. -Pnative-win 
//  is enabled by default when building on Windows since the native components 
//  are required (not optional) on Windows. 

def jars = []
cmd = "hadoop classpath"
res = cmd.execute().text
hadoop_classpath = res.split(File.pathSeparator)

// Find all jars based on classpath behavior
hadoop_classpath.each{ x ->
    x = x.trim()
    if ( x.endsWith("\\.jar") ) {
        jars << x
    }

    if ( x.endsWith("*") ) {
        def dirpath = x.getAt(0..(x.length() - 2))
        //println dirpath
        new File(dirpath).eachFile{ xf ->
            //println xf
            jars << xf
        }
    }
}

println "Total hadoop classpath jars: " + jars.size()
jars.each{ x ->
    if ( x.getName().contains("hadoop-common") ) {
       
    }
}


///////////////////////////////////////////////////////////////////////////////////////////////////
/**************************************************************************************************
 * hadoop-hdfs-project MUST be built with:
 *   * -Pnative or -Pnative-win = enable libhdfs.so
 *************************************************************************************************/
///////////////////////////////////////////////////////////////////////////////////////////////////

// ... ?
