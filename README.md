# clazz-parse
反编译小工具
  该工具反编译核心依赖于jd-gui, 工具本身仅实现了jd-gui的程序化对接；
  jd-gui参见：https://github.com/leiax00/jd-gui


1. 安装lib下的 jd-gui-1.4.0.jar 到本地仓库：
命令：mvn install:install-file -Dfile=%jarPath% -DgroupId=%groupId% -DartifactId=%artifactId% -Dversion=%version% -Dpackaging=jar

2. 修改pom中依赖的jd-gui-1.4.0.jar版本及配置为步骤一设置的url

3. 打包为jar, 注意需要包含依赖包, xxx.jar

4. 执行： java -jar xxx.jar 参数1 参数2 参数3
注：支持参数个数[1, 3], 
  参数1：扫描的路径（目录）；
  参数2：输出的结果文件（文件）， 可选， 不填时默认放置在和jar包同路径下，文件名为 result.txt；
  参数3：clazz解压路径，可选，不填时默认与jar包同路径下的temp文件夹中；
