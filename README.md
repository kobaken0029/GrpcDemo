# GrpcDemo
This is demo fore gRPC + ProtoBuffers on Android.

# Procedure
## Step1
Edit build.gradle.

`build.gradle`
```gradle
buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.2'
        classpath 'com.google.protobuf:protobuf-gradle-plugin:0.8.0'
    }
}
```

`app/build.gradle`
```gradle
apply plugin: "com.google.protobuf"

android {
    …

    configurations.all {
        resolutionStrategy.force "com.google.code.findbugs:jsr305:2.0.1"
    }
}

def grpc_version = "1.3.0"

dependencies {
    …

    compile "io.grpc:grpc-okhttp:$grpc_version"
    compile "io.grpc:grpc-protobuf-lite:$grpc_version"
    compile "io.grpc:grpc-stub:$grpc_version"
    compile "javax.annotation:javax.annotation-api:1.2"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.1.0"
    }
    plugins {
        javalite {
            artifact = "com.google.protobuf:protoc-gen-javalite:3.0.0"
        }
        grpc {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpc_version"
        }
    }
    generateProtoTasks {
        all().each { task ->
            task.plugins {
                javalite {}
                grpc {
                    option 'lite'
                }
            }
        }
    }
}
```

## Step2
Put proto file on `app/src/main/proto`.

## Step3
Try to build(or ./gradlew protobuf)

## Step4
You can see generated files from proto on `app/build/generated/source/proto`.

## Step5
Let's gRPC!
