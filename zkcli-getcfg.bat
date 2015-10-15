set ZKCLI_HOME=%solr_home%\server\scripts\cloud-scripts
call %solr_home%\server\scripts\cloud-scripts\zkcli -zkhost localhost:9983 -cmd list >tmp.txt

echo - look over the config to find various config names, then...
echo mkdir temp
echo %zkcli_home%\zkcli -zkhost localhost:9983 -cmd downconfig -confdir temp -confname <config name>
