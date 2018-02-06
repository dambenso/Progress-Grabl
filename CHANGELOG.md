# ChangeLog

All notable changes to this project will be documented in this file.

The format is inspired by
[git changelog](https://github.com/tj/git-extras/blob/master/Commands.md#git-changelog)
and [Keep a Changelog](http://keepachangelog.com/en/1.0.0/).  This project
adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).


## v0.1.0 / 2017-12-29

See [grabl/grabl%"v0.1.0"](https://gitlab.com/grabl/grabl/milestones/2).

### Added

  * Add support for build scans

### Changed

  * Split plugin into base and convention
  * Upgrade gradle wrapper to 4.4.1

### Fixed

  * Make sure release runs before publishing
  * Configure nebula.release to produce versions compatible with Gradle
    Plugin Portal
  * Avoid eagerly resolving `pct` configuration

## v0.0.0 / 2017-11-22

See [grabl/grabl%"v0.0.0"](https://gitlab.com/grabl/grabl/milestones/1).

### Added

  * Add release management using nebula.release plugin
  * Publish plugin to the Gradle Plugins Portal as
    [grabl](https://plugins.gradle.org/plugin/io.gitlab.grabl.grabl)
  * Build in GitLab-CI so bad commits / MRs are spotted early
  * Provide CompileAblTask, grabl extension (configuration point)
  * Add PCT tasks and types to project it's applied to
  * Initial release of Gradle plugin
