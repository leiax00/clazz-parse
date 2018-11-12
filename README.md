# clazz-parse
反编译小工具  
>该工具反编译核心依赖于jd-gui, 工具本身仅实现了jd-gui的程序化对接；  
  jd-gui参见：https://github.com/leiax00/jd-gui


1. 安装lib下的 jd-gui-1.4.0.jar 到本地仓库：
命令：mvn install:install-file -Dfile=%jarPath% -DgroupId=%groupId% -DartifactId=%artifactId% -Dversion=%version% -Dpackaging=jar

2. 修改pom中依赖的jd-gui-1.4.0.jar版本及配置为步骤一设置的url

3. 打包为jar, 注意需要包含依赖包(命令：mvn assembly:assembly), 生成的版本文件在target/version下  

4. 修改version/clazz-parse.yml, 修改方式参见注释

4. 执行：version/run.bat

