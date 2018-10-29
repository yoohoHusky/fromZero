如何向jcenter提交maven
1. 翻墙
2. 在jcenter注册账号
3. 在项目根目录的build.gradle中添加(maven上传脚本)：
```
	classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.0'
	classpath 'com.github.dcendents:android-maven-gradle-plugin:1.4.1'
	// apply from: 'config.gradle'
```
4. 在库目录添加：
```
apply plugin: 'com.github.dcendents.android-maven'
apply plugin: 'com.jfrog.bintray'

group = "com.example.demotest.mylibrary3" // 一般为包名
Properties properties = new Properties()
properties.load(project.rootProject.file('local.properties').newDataInputStream())
bintray {
    user = deps.jcenter_maven.user	// 自己写user信息
    key = deps.jcenter_maven.key	// 从jcenter中获取key
    configurations = ['archives']
    pkg {
        repo = "maven" 				// 发布到Maven库
        name = "test_store5" 		// 发布到JCenter上的项目名字
        websiteUrl = siteUrl
        version '1.0.3'
        vcsUrl = 'www.husky.com'
        licenses = ["Apache-2.0"]
        publish = true
    }
}
```
下方的信息不知道是否有用
```
def siteUrl = 'https://github.com/smuyyh/BankCardFormat'    // Git项目主页
def gitUrl = 'https://github.com/smuyyh/BankCardFormat.git' // Git仓库url

//install {
//    repositories.mavenInstaller {
//        // 生成POM.xml
//        pom {
//            project {
//                packaging 'aar'
//                name 'Android_name' // 项目描述
//                url siteUrl
//                licenses {
//                    license {
//                        name 'The Apache Software License, Version 2.0'
//                        url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
//                    }
//                }
//                developers {
//                    developer { // 开发者个人信息
//                        id 'megayooho'
//                        name 'husky_yooho'
//                        email 'megayooho@outlook.com'
//                    }
//                }
//                scm {
//                    connection gitUrl
//                    developerConnection gitUrl
//                    url siteUrl
//                }
//            }
//        }
//    }
//}
//task sourcesJar(type: Jar) {
//    from android.sourceSets.main.java.srcDirs
//    classifier = 'sources'
//}
//task javadoc(type: Javadoc) {
//    options.encoding = "UTF-8" // 设置编码，否则中文可能会提示出错
//    source = android.sourceSets.main.java.srcDirs
//    classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
//}
//task javadocJar(type: Jar, dependsOn: javadoc) {
//    classifier = 'javadoc'
//    from javadoc.destinationDir
//}
//artifacts {
//    archives javadocJar
//    archives sourcesJar
//}
```





另外一种方法，可以在本地使用，可以在自己搭的nexus中使用
```
//apply plugin: 'maven'
//
//
//uploadArchives {
//    repositories.mavenDeployer {
//        repository(url: deps.out_maven.URL) {
//            authentication(userName: deps.out_maven.user, password: deps.out_maven.password)
//        }
//        pom.groupId = deps.out_maven.groupId
//        pom.artifactId = deps.out_maven.artifactId
//        pom.version = deps.out_maven.version
//        pom.packaging = 'aar'
//    }
//}
```







