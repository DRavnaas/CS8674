rem 64 bit 1.8 java
rem set JAVA_HOME=C:\Progra~1\Java\jdk1.7.0_72
set JAVA_Home=c:\progra~1\java\jdk1.8.0_60
set ANT_HOME=C:\Progra~1\ant\apache-ant-1.9.4
set SOLR_HOME=c:\progra~1\solr-5.3.0
set MAVEN_HOME=c:\progra~1\apache-maven-3.3.3
set SPARK_HOME=c:\progra~1\spark-1.5.0-bin-hadoop2.6

set PATH=C:\windows\system32;C:\windows;C:\windows\System32\Wbem;C:\windows\System32\WindowsPowerShell\v1.0\;%java_home%\bin;%ant_home%\bin;C:\Python27;c:\progra~1\phantomjs\phantomjs-1.9.8;c:\users\malcolm\AppData\local\GitHub\PortableGit_c2ba306e536fdf878271f7fe636a147ff37326ad\bin;C:\Progra~1\eclipse;%maven_home%\bin;

rem set PATH=C:\windows\system32;C:\windows;C:\windows\System32\Wbem;C:\windows\System32\WindowsPowerShell\v1.0\;C:\Progra~1\Java\jdk1.7.0_72\bin;C:\Progra~1\ant\apache-ant-1.9.4\bin;C:\Python27;c:\progra~1\phantomjs\phantomjs-1.9.8;C:\progra~1\android\sdk\platform-tools;c:\users\malcolm\AppData\local\GitHub\PortableGit_c2ba306e536fdf878271f7fe636a147ff37326ad\bin;C:\Progra~1\eclipse;

cd %0\..

@echo Ctrl+C OR press enter to continue and change to the solr home directory
@pause
cd %solr_home%
