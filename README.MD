# 使用 Github Action 构建 spring boot native image demo

### 踩坑

`
<buildArgs>

macos 不支持静态链接
<arg>-H:+StaticExecutableWithDynamicLibC</arg>

</buildArgs>
`
