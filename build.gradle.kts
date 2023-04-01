import com.baimsg.plugin.Dep

plugins {
    id("qstool-dep")
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    kotlin("android") version "1.8.10" apply false
    kotlin("jvm") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.43.2" apply false
}

subprojects {
    group = Dep.group
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

