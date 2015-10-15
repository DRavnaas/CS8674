rem cmd -k (absolute path to this batch file)
rem be sure to mark as admin, solr seems to need that

rem download 64 bit 1.8 java, hadoop, solr, spark, maven, solr-spark - put them in c:\program files
rem set JAVA_HOME=C:\Progra~1\Java\jdk1.7.0_72
rem set JAVA_Home=c:\progra~1\java\jdk1.8.0_60
set ANT_HOME=C:\Progra~1\ant\apache-ant-1.9.4
set SOLR_HOME=c:\OpenSource\solr-5.3.0
set MAVEN_HOME=c:\OpenSource\apache-maven-3.3.3
set SPARK_HOME=c:\OpenSource\spark-1.5.0-bin-hadoop2.6
set HADOOP_HOME=c:\OpenSource\hadoop-2.6.0

set PATH=%java_home%\bin;C:\windows\system32;C:\windows;C:\windows\System32\Wbem;C:\windows\System32\WindowsPowerShell\v1.0\;%ant_home%\bin;C:\Python27;c:\progra~1\phantomjs\phantomjs-1.9.8;c:\users\malcolm\AppData\local\GitHub\PortableGit_c2ba306e536fdf878271f7fe636a147ff37326ad\bin;C:\Progra~1\eclipse;%maven_home%\bin;%hadoop_home%\bin;%spark_home%\bin;%solr_home%\bin;

