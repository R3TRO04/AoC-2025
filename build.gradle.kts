plugins {
    kotlin("jvm") version "2.3.0-RC2"
}

group = "com.r3tro04"
version = "1.0"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "9.3.0-rc-1"
        distributionType = Wrapper.DistributionType.ALL
    }
}
