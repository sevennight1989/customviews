package com.hearing.plugin

import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ReleaseInfoTask extends DefaultTask {

    ReleaseInfoTask() {
        group = 'version_manager'
        description = 'release info update'
    }

    @TaskAction
    void doAction() {
        updateVersionInfo();
    }

    private void updateVersionInfo() {
        // 3、从 realeaseInfo Extension 属性中获取相应的版本信息
        def versionCodeMsg = project.extensions.releaseInfo.versionCode;
        def versionNameMsg = project.extensions.releaseInfo.versionName;
        def versionInfoMsg = project.extensions.releaseInfo.versionInfo;
        def fileName = project.extensions.releaseInfo.fileName;
        println("versionCodeMsg = ${versionCodeMsg}")
        println("versionNameMsg = ${versionNameMsg}")
        println("versionInfoMsg = ${versionInfoMsg}")
        println("fileName = ${fileName}")
        def file = project.file(fileName)
        if(file.exists()){
            file.delete()
        }
        file.createNewFile()
        // 4、将实体对象写入到 xml 文件中
        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)
        if (file.text != null && file.text.size() <= 0) {
            //没有内容
            xmlBuilder.releases {
                release {
                    versionCode(versionCodeMsg)
                    versionName(versionNameMsg)
                    versionInfo(versionInfoMsg)
                }
            }
            //直接写入
            file.withWriter { writer -> writer.append(sw.toString())
            }
        } else {
            //已有其它版本内容
            xmlBuilder.release {
                versionCode(versionCodeMsg)
                versionName(versionNameMsg)
                versionInfo(versionInfoMsg)
            }
            //插入到最后一行前面
            def lines = file.readLines()
            def lengths = lines.size() - 1
            file.withWriter { writer ->
                lines.eachWithIndex { line, index ->
                    if (index != lengths) {
                        writer.append(line + '\r\n')
                    } else if (index == lengths) {
                        writer.append('\r\r\n' + sw.toString() + '\r\n')
                        writer.append(lines.get(tlengths))
                    }
                }
            }
        }
    }

}