name := "graal-scala-example"
version := "0.1.0"

// by default build locally and need to make sure you have native-image executable
// using docker image to build binary
// graalVMNativeImageGraalVersion := Some("19.2.0.1")

enablePlugins(GraalVMNativeImagePlugin)
