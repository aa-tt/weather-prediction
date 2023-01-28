import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
    kotlin("kapt") version "1.7.22"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.serialization") version "1.7.22"
}

group = "com.aa"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-cassandra-reactive")
	implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
//	implementation("org.springframework.boot:spring-boot-starter-hateoas")
//	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // okhttp
	implementation("com.squareup.okhttp3:okhttp:4.10.0")

    // moshi
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-adapters:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    // open api
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.0.2")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    // caching - redis
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")


//    implementation("com.amazonaws.aws-java-sdk-dynamodb:1.12.389")
//	implementation("software.amazon.awssdk.dynamodb:2.18.2")
//	implementation("software.amazon.awssdk.dynamodb-enhanced:2.18.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
//	testImplementation("io.projectreactor:reactor-test")
//	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
