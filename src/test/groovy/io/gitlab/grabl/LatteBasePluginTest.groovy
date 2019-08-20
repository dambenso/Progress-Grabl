// Copyright © 2019 Progress Software Corporation and/or its subsidiaries or affiliates. All Rights Reserved.
package oe.espresso.latte

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class LatteBasePluginTest extends Specification {
    Project project

    def setup() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'oe.espresso.latte.latte-base'
    }

    def 'can be applied'() {
        expect:
        project.plugins.hasPlugin(LatteBasePlugin)
    }

    def 'does not create tasks'() {
        given:
        def taskTypes = [CompileAblTask]

        expect:
        project.tasks.findAll { taskTypes.contains(it.class) }.isEmpty()
    }

    def 'adds pct configuration'() {
        expect:
        null != project.configurations.pct

        /* Ensure configuration is added but not resolved eagerly as
         * this can cause problems where it will be unable to download
         * dependencies for PCT as repositories may not be configured
         * at that point (see #10).
         */
        Configuration.State.UNRESOLVED == project.configurations.pct.state
    }

    def 'adds maven central repository'() {
        expect:
        null != project.repositories.maven
    }

    def 'adds dependency on PCT'() {
        given:
        def deps = project.configurations.pct.dependencies

        expect:
        deps.size() > 0
        deps.findAll { it.name == 'pct' }.size() == 1
    }

    def 'adds PCT Ant tasks and types'() {
        // TODO: not quite sure how to actually test that; can't seem
        //   to check if types or tasks were added to AntBuilder as
        //   they are implemented using dynamic calls; the only
        //   property it seems to affect is 'references' where it puts
        //   the 'loaderRef' with value of the class loader used to
        //   load the tasks/types; alternatively just mock AntBuilder
        //   and test that it called 'taskdef' / 'typedef'

        given:
        // needed to resolve dependencies of PCT, normally it is
        // assumed project has some repos
        project.repositories { jcenter() }

        when:
        // NOTE: `project.evaluate` is internal but there is currently
        //   no better supported way to trigger lifecycle events like
        //   `project.afterEvaluate` from tests
        project.evaluate()

        then:
        null != project.ant.references.pct
    }

    def 'adds latte extension'() {
        expect:
        project.abl instanceof LatteExtension
    }
}
