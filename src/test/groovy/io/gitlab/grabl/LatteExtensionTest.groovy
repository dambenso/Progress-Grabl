package oe.espresso.latte

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

/**
 *
 */
class LatteExtensionTest extends Specification {
    Project project = ProjectBuilder.builder().build()
    LatteExtension extension = new LatteExtension(project)

    def "it provides sane defaults"() {
        expect: "default property values to use sane conventions"
        extension.rcodeDir == new File(
                project.buildDir, LatteExtension.DEFAULT_RCODE_DIR_NAME)
        extension.propath?.files == project.files('src/main/abl').files
        extension.dbConnections?.isEmpty()
        extension.pctTaskArgs == [:]

        when: "project buildDir is changed"
        project.buildDir = project.file('newBuildDir')

        then: "rcodeDir changes with it"
        extension.rcodeDir == new File(
                project.buildDir, LatteExtension.DEFAULT_RCODE_DIR_NAME)

        when: "rcodeDir is reset (set to a static value) and buildDir is changed"
        extension.rcodeDir = new File(project.buildDir, 'newRcode')

        then: "rcodeDir does not change"
        extension.rcodeDir == new File(project.buildDir, 'newRcode')
    }

    def "it provides configuration DSL to the project"() {
        given: "extension is added to the project"
        project.extensions.add(LatteExtension.NAME, extension)

        when: "configuration DSL is used"
        project.configure(project) {
            abl {
                rcodeDir "${buildDir}/newRcode"
                propath 'src'
                dbConnections('foodb', 'bardb')
                pctTaskArgs {
                    preprocess = true
                }
                pctTaskArgs.listing = true
            }
        }

        then: "values are changed"
        extension.rcodeDir == new File(project.buildDir, 'newRcode')
        extension.propath?.files == project.files('src').files
        extension.dbConnections.containsAll(['foodb', 'bardb'])
        extension.pctTaskArgs == [preprocess: true, listing: true]
    }

    def "it sets DlcHome "() {
        given: "extension is added to the project"
        project.extensions.add(LatteExtension.NAME, extension)

        when: "configuration DSL is used"
        project.configure(project) {
            abl {
                dlcHome = new File("testdlchome")
            }
        }

        then: "values are changed"
        extension.dlcHome == new File("testdlchome")
    }    
}
