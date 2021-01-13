package com.hearing.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginImpl implements Plugin<Project> {

    def nestClouser = {
        def innnerClouser = {
            // 代表闭包定义处的类
            println "scriptClouser this:" + this
            // 代表闭包定义处的类或者对象
            println "scriptClouser this:" + owner
            // 代表任意对象，默认与 ownner 一致
            println "scriptClouser this:" + delegate
        }

        innnerClouser.call()
    }



    void apply(Project project) {
//        nestClouser.call()
        def name = 'Android'
//        println(name)
        def sayHello = "Hello: ${name}"
//        println(sayHello)
        def sum = "The sum of 2 and 3 equals ${ 2 + 3}"
//        println(sum)

        println "hello gradle plugin!";
        project.task('CustomPluginTask') {
            doLast {
                println "大家好,我是一个自定义插件，在这里写下你的具体功能"
            }
        }

        def stu = new Student()
        def tea = new Teacher()
    /*    stu.pretty.delegate = tea
        stu.pretty.resolveStrategy = Closure.DELEGATE_ONLY*/
//        println(stu.toString())
        def result = ""
        [a:1, b:2].each { key, value ->
            result += "$key$value"
        }
//        println(result)

    }
}
