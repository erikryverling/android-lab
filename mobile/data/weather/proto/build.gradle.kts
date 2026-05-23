import com.google.protobuf.gradle.GenerateProtoTask

plugins {
    alias(libs.plugins.java.library)
    alias(libs.plugins.protobuf)
}

dependencies {
    implementation(libs.protobuf)
}

protobuf {
    protoc {
        artifact = libs.versions.protobufCompilerArtifact.get()
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                named<GenerateProtoTask.PluginOptions>("java") {
                    option("lite")
                }
            }
        }
    }
}
