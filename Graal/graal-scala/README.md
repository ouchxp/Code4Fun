# Graal sbt-native-packager example

# Project setup
## Install Graal 
```bash
brew cask install graalvm/tap/graalvm-ce-java8
gu install native-image # install native-image command
```

## If you use jenv
```bash
jenv add /Library/Java/JavaVirtualMachines/graalvm-ce-java8-x.x.x/Contents/Home/
jenv local <version>
jenv rehash # make sure native-image is in the path
```

## Build
```make build```

## Run
```make run```

## Build with docker
add this line to build.sbt
```
graalVMNativeImageGraalVersion := Some("19.2.0.1")
```
