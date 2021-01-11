package com.hearing.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginImpl implements Plugin<Project> {
    void apply(Project project) {
        println "hello gradle plugin!";
        project.task('CustomPluginTask') {
            doLast {
                println "大家好,我是一个自定义插件，在这里写下你的具体功能"
            }
        }
    }
}
