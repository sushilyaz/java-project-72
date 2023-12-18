plugins {
    id("java")
    application
    checkstyle
    jacoco
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass = "hexlet.code.Main"
}

repositories {
    mavenCentral()
}
tasks.withType<JavaCompile> {
    options.release.set(20)
}
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}
tasks.jacocoTestReport {
    reports {
        xml.required = true
    }
}
