package io.gitlab.hendosdo.grabl

import org.gradle.api.Project
import org.gradle.api.file.FileCollection

/**
 * Global default grabl plugin configuration.
 *
 * These settings are applied to relevant tasks created by grabl.
 */
class GrablExtension {
    /**
     * Name of the property on Project this instance should be bound to.
     */
    final static String NAME = 'abl'

    /**
     * The default name of the rcode directory for all compilations.
     * Relative to {@link org.gradle.api.Project#getBuildDir()} ({@value}).
     */
    final static String DEFAULT_RCODE_DIR_NAME = 'rcode'

    /**
     * The default rcode directory for all compilations.
     */
    Object rcodeDir

    /**
     * The default PROPATH for this project.
     */
    FileCollection propath

    /**
     * The default database connections for this project.
     */
    List<String> dbConnections = []

    /**
     * Default parameters to pass to ALL PCT tasks.
     */
    Map pctTaskArgs = [:]

    /**
     * Stores private reference to project so {@code rcodeDir} can be
     * resolved dynamically when accessed.
     */
    private Project project

    GrablExtension(Project project) {
        this.project = project

        /* Set initial value to a closure so that it is re-evaluated on
         * every access (see {@link #getRcodeDir} and therefore is
         * always relative to {@link Project#buildDir}.
         * It can still be set to a static string which will stop it
         * auto-updating.
         */
        rcodeDir = {
            new File(project.buildDir, this.DEFAULT_RCODE_DIR_NAME)
        }
        propath = project.files('src/main/abl')
    }

    File getRcodeDir() {
        return project.file(rcodeDir)
    }
}
