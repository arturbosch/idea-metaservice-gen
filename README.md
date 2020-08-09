# idea-metaservice-gen

Using Java's ServiceLoader pattern requires a lot of boilerplate.
You can automate generating `Meta-Inf/services` with an AnnotationProcessor like [AutoService](https://github.com/google/auto/tree/master/service)
or just use this plugin and call the `Generate MetaInf Service Entry` action when editing a file.

### Install

- run `gradle build`
- in IntelliJ run Action `install Plugin from disk`
- choose `build/distributions/idea-metaservice-gen-x.x.x.zip`

### Limitations

- Works only for Kotlin for now. Java support is planned.
- Works only for the first super type. If there are multiple super types, the full qualified name of the first super type will be used.
- `src/main/resources` source root must exist.
- Refreshing of the module might take some time and you won't see the generated file immediately.

